package br.ufes.informatica.smcss.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Messages {

	public static void message(String componentId, FacesMessage.Severity severity, String title, String message) {
		FacesContext.getCurrentInstance().addMessage(componentId, new FacesMessage(severity, title, message));
	}

	public static void message(FacesMessage.Severity severity, String title, String message) {
		message(null, severity, title, message);
	}

	public static void info(String title, String message) {
		info(null, title, message);
	}

	public static void info(String componentId, String title, String message) {
		message(componentId, FacesMessage.SEVERITY_INFO, title, message);
	}

	public static void warn(String title, String message) {
		warn(null, title, message);
	}

	public static void warn(String componentId, String title, String message) {
		message(componentId, FacesMessage.SEVERITY_WARN, title, message);
	}

	public static void error(String title, String message) {
		error(null, title, message);
	}

	public static void error(String componentId, String title, String message) {
		message(componentId, FacesMessage.SEVERITY_ERROR, title, message);
	}

	public static void fatal(String title, String message) {
		fatal(null, title, message);
	}

	public static void fatal(String componentId, String title, String message) {
		message(componentId, FacesMessage.SEVERITY_FATAL, title, message);
	}
}
