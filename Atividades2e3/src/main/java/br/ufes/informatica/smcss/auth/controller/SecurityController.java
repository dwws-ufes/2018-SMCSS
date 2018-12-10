package br.ufes.informatica.smcss.auth.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@SessionScoped
public class SecurityController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/index.xhtml?faces-redirect=true";
	}
}
