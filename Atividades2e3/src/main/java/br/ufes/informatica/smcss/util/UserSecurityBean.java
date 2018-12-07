package br.ufes.informatica.smcss.util;

import java.io.Serializable;
import java.security.Principal;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import br.ufes.informatica.smcss.core.application.PessoaService;
import br.ufes.informatica.smcss.core.domain.Pessoa;

@Named
@SessionScoped
public class UserSecurityBean implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private transient Pessoa user;

    @EJB
    private PessoaService pessoaServiceBean;

    private ExternalContext getContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    public Pessoa getUser() {
        if (user == null) {
            ExternalContext context = getContext();
            Principal userPrincipal = context.getUserPrincipal();
            if (userPrincipal != null) {
                user = pessoaServiceBean.retrievePessoaByEmail(userPrincipal.getName());
            }
        }
        return user;
    }
}
