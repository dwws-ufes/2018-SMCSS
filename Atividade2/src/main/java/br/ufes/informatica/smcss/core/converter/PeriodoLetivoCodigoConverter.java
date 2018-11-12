package br.ufes.informatica.smcss.core.converter;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

import br.ufes.informatica.smcss.core.domain.PeriodoLetivo;
import br.ufes.informatica.smcss.core.persistence.PeriodoLetivoDAO;

@FacesConverter("periodoLetivoCodigoConverter")
public class PeriodoLetivoCodigoConverter implements Converter {

	private static final String NAME = "periodoLetivoCodigoConverterBean";

	/**
	 * Classe utilitária para injeção de dependência do conversor.
	 *
	 * @author Gabriel
	 * @see PeriodoLetivoCodigoConverter#getPeriodoLetivoDAO(FacesContext)
	 * 
	 */
	@Named(NAME)
	@RequestScoped
	public static class Bean {
		@Inject
		private PeriodoLetivoDAO periodoLetivoDAO;

		public PeriodoLetivoDAO getPeriodoLetivoDAO() {
			return periodoLetivoDAO;
		}
	}

	/**
	 * @see {@link https://stackoverflow.com/a/7665768}
	 */
	private PeriodoLetivoDAO getPeriodoLetivoDAO(FacesContext context) {
		Application app = context.getApplication();
		Bean bean = app.evaluateExpressionGet(context, "#{" + NAME + "}", Bean.class);
		PeriodoLetivoDAO periodoLetivoDAO = bean.getPeriodoLetivoDAO();
		return periodoLetivoDAO;
	}

	@Override
	public PeriodoLetivo getAsObject(FacesContext context, UIComponent component, String value) {
		PeriodoLetivoDAO periodoLetivoDAO = getPeriodoLetivoDAO(context);
		return (value == null || value.isEmpty()) ? null : periodoLetivoDAO.retrieveByCodigo(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null) {
			return null;
		} else {
			PeriodoLetivo periodoLetivo = (PeriodoLetivo) value;
			return periodoLetivo.getCodigo();
		}
	}

}
