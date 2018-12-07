package br.ufes.informatica.smcss.auth.application;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.security.DeclareRoles;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextListener;
import javax.transaction.Transactional;

import br.ufes.informatica.smcss.auth.domain.Permissao;
import br.ufes.informatica.smcss.auth.domain.Usuario;
import br.ufes.informatica.smcss.auth.persistence.PermissaoDAO;
import br.ufes.informatica.smcss.auth.persistence.UsuarioDAO;

/**
 * Classe utilitária para gravar lista de permissões válidas no banco.
 *
 * @author luciano
 *
 */
@LocalBean
@Stateless
@ApplicationScoped
@DeclareRoles({ "Admin", "Candidato", "Aluno", "Professor" })
public class PermissaoInit implements ServletContextListener {

    @EJB
    PermissaoDAO permissaoDAO;

    @EJB
    UsuarioDAO usuarioDAO;

    @Transactional
    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) ServletContext context) {
        Set<String> valores = new HashSet<String>();

        DeclareRoles annotation = PermissaoInit.class.getAnnotation(DeclareRoles.class);
        Collections.addAll(valores, annotation.value());

        for (Permissao permissao : permissaoDAO.retrieveAll()) {
            valores.remove(permissao.getNome());
        }

        for (String nomePermissao : valores) {
            Permissao permissao = new Permissao();
            permissao.setNome(nomePermissao);
            permissaoDAO.save(permissao);
        }

        if (usuarioDAO.retrieveCount() == 0) {
            Usuario usuario = new Usuario();
            usuario.setLogin("Admin");
            usuario.setPassword("Admin");
            Set<Permissao> permissoes = new HashSet<Permissao>();
            permissoes.addAll(permissaoDAO.retrieveAll());
            usuario.setPermissoes(permissoes);
            usuarioDAO.save(usuario);
        }
    }
}
