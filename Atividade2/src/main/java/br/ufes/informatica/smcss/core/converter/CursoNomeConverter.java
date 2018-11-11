package br.ufes.informatica.smcss.core.converter;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

import br.ufes.informatica.smcss.core.domain.Curso;
import br.ufes.informatica.smcss.core.persistence.CursoDAO;

@FacesConverter("cursoNomeConverter")
public class CursoNomeConverter implements Converter {

    private static final String NAME="cursoNomeConverterBean";

    /**
     * Classe utilitário para injeção de dependências do conversor.
     *
     * @author luciano
     * @see CursoNomeConverter#getPessoaDAO(FacesContext)
     */
    @Named(NAME)
    @RequestScoped
    public static class Bean {
        @Inject
        private CursoDAO cursoDAO;

        public CursoDAO getCursoDAO() {
            return cursoDAO;
        }
    }

    /**
     * @see {@link https://stackoverflow.com/a/7665768}
     */
    private CursoDAO getCursoDAO(FacesContext context) {
        Application app = context.getApplication();
        Bean bean = app.evaluateExpressionGet(context, "#{" + NAME + "}", Bean.class);
        CursoDAO pessoaDAO = bean.getCursoDAO();
        return pessoaDAO;
    }

    @Override
    public Curso getAsObject(FacesContext context, UIComponent component, String value) {
        CursoDAO cursoDAO = getCursoDAO(context);
        return (value == null || value.isEmpty()) ? null : cursoDAO.retrieveByNome(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
    	if (value == null) {
    		return null;
    	} else {
    		Curso curso = (Curso) value;
    		return curso.getNome();
    	}
    }

}
