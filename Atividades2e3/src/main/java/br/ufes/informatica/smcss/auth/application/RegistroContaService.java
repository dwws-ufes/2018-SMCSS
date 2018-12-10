package br.ufes.informatica.smcss.auth.application;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import br.ufes.informatica.smcss.auth.domain.Permissao;
import br.ufes.informatica.smcss.auth.domain.SolicitacaoUsuario;
import br.ufes.informatica.smcss.auth.domain.SolicitacaoUsuario.Tipo;
import br.ufes.informatica.smcss.auth.domain.Usuario;
import br.ufes.informatica.smcss.auth.mail.MailMessageLoader;
import br.ufes.informatica.smcss.auth.persistence.PermissaoDAO;
import br.ufes.informatica.smcss.auth.persistence.SolicitacaoUsuarioDAO;
import br.ufes.informatica.smcss.auth.persistence.UsuarioDAO;
import br.ufes.informatica.smcss.auth.util.Util;

@Stateless
@PermitAll
public class RegistroContaService {

    @EJB
    private UsuarioDAO usuarioDAO;

    @EJB
    private PermissaoDAO permissaoDAO;

    @EJB
    private SolicitacaoUsuarioDAO solicitacaoUsuarioDAO;

    @Resource(name = "java:jboss/mail/SMCSS")
    private Session messageSession;

    @Resource(name = "publicRootURL")
    private String publicURLRoot;

    @Resource(name = "senderMailAddress")
    private String senderMail;

    public boolean solicitarCriacaoConta(String nomeConta, Locale locale) {
        return criarSolicitacao(nomeConta, Tipo.CRIACAO_CONTA, locale);
    }

    public boolean solicitarRecuperacaoDeSenha(String nomeConta, Locale locale) {
        return criarSolicitacao(nomeConta, Tipo.RECUPERACAO_SENHA, locale);
    }

    @Transactional
    protected boolean criarSolicitacao(String nomeConta, Tipo tipo, Locale locale) {

        // Se é criação de conta, usuário não pode existir. Se é recuperação de senha, usuário tem que existir.
        Usuario usuario = usuarioDAO.retrieveByLogin(nomeConta);
        switch (tipo) {
            case CRIACAO_CONTA:
                if (usuario != null) return false;
                break;
            case RECUPERACAO_SENHA:
                if (usuario == null) return false;
                break;
        }
        // Gera chave aleatória para solicitação
        String token = Util.createPasswordHash("SHA-512", "BASE64", null, nomeConta,
                String.valueOf(System.currentTimeMillis()), String.valueOf(Math.random()))
                .replace('+', '-'); // Sinal de + é convertido em espaço nas URLs

        SolicitacaoUsuario solicitacao = new SolicitacaoUsuario();
        solicitacao.setLogin(nomeConta);
        solicitacao.setCodigo(getTokenHash(token));
        solicitacao.setTipo(tipo);
        solicitacaoUsuarioDAO.save(solicitacao);

        final String nomeMensagem;
        switch (tipo) {
            case CRIACAO_CONTA:
                nomeMensagem = "registrarUsuario";
                break;
            case RECUPERACAO_SENHA:
            default:
                nomeMensagem = "recuperarSenha";
        }
        sendMail(nomeConta, nomeMensagem, token, locale);
        return true;
    }


    private String getTokenHash(String key) {
        return Util.createPasswordHash("SHA-512", "BASE64", null, "SMCSS-", key, "-Key");
    }

    public SolicitacaoUsuario retrieveSolicitacaoByCodigo(String codigo) {
        return solicitacaoUsuarioDAO.findByCodigo(getTokenHash(codigo));
    }

    @Transactional
    public boolean criarUsuario(String codigo, String senha) {
        SolicitacaoUsuario solicitacao = solicitacaoUsuarioDAO.findByCodigo(getTokenHash(codigo));
        if (solicitacao == null) {
            return false;
        }
        Usuario usuario = new Usuario();
        usuario.setLogin(solicitacao.getLogin());
        usuario.setPassword(senha);
        Set<Permissao> permissoes = new HashSet<Permissao>();
        permissoes.add(permissaoDAO.retrieveByNome("Candidato"));
        usuario.setPermissoes(permissoes);
        usuarioDAO.save(usuario);
        solicitacaoUsuarioDAO.deleteByLogin(solicitacao.getLogin());
        return true;
    }

    @Transactional
    public boolean alterarSenha(String codigo, String senha) {
        SolicitacaoUsuario solicitacao = solicitacaoUsuarioDAO.findByCodigo(getTokenHash(codigo));
        if (solicitacao == null) {
            return false;
        }
        Usuario usuario = usuarioDAO.retrieveByLogin(solicitacao.getLogin());
        usuario.setPassword(senha);
        usuarioDAO.save(usuario);
        solicitacaoUsuarioDAO.deleteByLogin(solicitacao.getLogin());
        return true;
    }

    /**
     * Envia mensagem de e-mail para cliente. A mensagem será enviada no formato multipart,
     * com uma parte em texto e outra em html. Os modelos de mensagem deverão estar disponíveis
     * como recursos no classpath no caminho
     * br/ufes/informatica/smcss/auth/mail/{nomeMensagem}[_{locale}].[txt|html].
     *
     * O assunto da mensagem deverá estar na primeira linha do arquivo .txt. As duas primeiras
     * linhas do arquivo .txt não participarão do conteúdo da mensagem.
     *
     * @param recipient Destinatário da mensagem
     * @param messageName Nome da mensagem
     * @param token
     * @param locale
     */
    @Asynchronous
    public void sendMail(String recipient, String messageName, String token, Locale locale) {

        try {

            Message message = new MimeMessage(messageSession);
            message.setFrom(new InternetAddress(senderMail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));

            Map<String, String> vars = new HashMap<String, String>();
            vars.put("public.url.root", publicURLRoot);
            vars.put("auth.request.token", token);
            vars.put("auth.request.url", String.format("%sauth/request.xhtml?token=%s", publicURLRoot, token));
            MailMessageLoader.load(messageName, locale, vars, message);

            Transport.send(message);
        } catch (MessagingException e) {
            Logger.getLogger(RegistroContaService.class.getName()).log(Level.WARNING, "Cannot send mail", e);
        }
    }
}
