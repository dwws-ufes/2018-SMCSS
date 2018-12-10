package br.ufes.informatica.smcss.auth.controller;

import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import br.ufes.informatica.smcss.auth.application.RegistroContaService;
import br.ufes.informatica.smcss.auth.domain.SolicitacaoUsuario;

@Named @RequestScoped
public class RegisterController {

    @EJB
    private RegistroContaService registroContaService;

    private String message;

    private boolean response;

    private String email;

    private String emailConfirmation;

    private String password;

    private String token;

    private SolicitacaoUsuario solicitacao;

    RegisterController() {
        password = "";
        solicitacao = new SolicitacaoUsuario();
    }

    @PostConstruct
    private void postConstruct() {

        token = FacesContext.getCurrentInstance().getExternalContext()
            .getRequestParameterMap()
            .get("token")
            ;

        if (token != null) {
            solicitacao = registroContaService.retrieveSolicitacaoByCodigo(token);
            if (solicitacao == null) {
                message = "auth.request.invalidToken";
                response = true;
            }
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailConfirmation() {
        return emailConfirmation;
    }

    public void setEmailConfirmation(String emailConfirmation) {
        this.emailConfirmation = emailConfirmation;
    }

    public void register() {
        if (email.equals(emailConfirmation)) {
            Locale browserLocale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
            boolean result = registroContaService.solicitarCriacaoConta(email, browserLocale);
            message = result ? "auth.register.success" : "auth.register.duplicate";
            response = true;
        } else {
            message = "auth.register.emailsDiffer";
        }
        email=message;
    }

    public void changePassword() {
        if (solicitacao != null) {
            if (registroContaService.alterarSenha(getToken(), password)) {
                message = "auth.request.change.success";
                response = true;
            }
        }
    }

    public void createUser() {
        if (solicitacao != null) {
            if (registroContaService.criarUsuario(getToken(), password)) {
                message = "auth.request.create.success";
                response = true;
            }
        }
    }

    public SolicitacaoUsuario getSolicitacao() {
        return solicitacao;
    }

    public void setSolicitacao(SolicitacaoUsuario solicitacao) {
        this.solicitacao = solicitacao;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isCriacao() {
        return solicitacao != null && solicitacao.isCriacao();
    }

    public boolean isRecuperacaoSenha() {
        return solicitacao != null && solicitacao.isRecuperacaoSenha();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
