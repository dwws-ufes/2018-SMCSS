package br.ufes.informatica.smcss.core.converter;

import java.util.function.BiFunction;
import java.util.function.Function;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

import br.ufes.informatica.smcss.core.domain.Disciplina;
import br.ufes.informatica.smcss.core.persistence.DisciplinaDAO;

public abstract class DisciplinaConverter implements Converter {

    private static final String NAME="disciplinaConverterBean";

    private final Function<Disciplina, String> convertToString;
    private final BiFunction<DisciplinaDAO, String, Disciplina> findFromString;

    protected DisciplinaConverter(
            Function<Disciplina, String> convertToString,
            BiFunction<DisciplinaDAO, String, Disciplina> findFromString) {
        this.convertToString = convertToString;
        this.findFromString = findFromString;
    }

    /**
     * Classe utilitária para injeção de dependências do conversor.
     *
     * @author luciano
     * @see PessoaNomeConverter#getPessoaDAO(FacesContext)
     */
    @Named(NAME)
    @RequestScoped
    public static class Bean {
        @Inject
        private DisciplinaDAO disciplinaDAO;

        public DisciplinaDAO getDisciplinaDAO() {
            return disciplinaDAO;
        }
    }

    /**
     * Extrai DAO de pessoa do contexto. Na versão 2.2 do JSF, utilizada neste trabalho,
     * não é possível injetar a dependência diretamente no conversor. Na versão 2.3 é possível
     * utilizar a anotação @EJB nos campos do conversor, desde que o parâmetro managed da anotação
     * FacesConverter seja true.
     *
     * @param context Contexto para realizar extração
     * @return Referência ao DAO de pessoa
     *
     * @see {@link https://stackoverflow.com/a/7665768}
     */
    private DisciplinaDAO getDisciplinaDAO(FacesContext context) {
        Application app = context.getApplication();
        Bean bean = app.evaluateExpressionGet(context, "#{" + NAME + "}", Bean.class);
        DisciplinaDAO disciplinaDAO = bean.getDisciplinaDAO();
        return disciplinaDAO;
    }

    @Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
        DisciplinaDAO disciplinaDAO = getDisciplinaDAO(context);
        return (value == null || value.isEmpty()) ? null : findFromString.apply(disciplinaDAO, value);
    }

    @Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return (value == null) ? null : convertToString.apply((Disciplina) value);
    }

    @FacesConverter("disciplinaCodigoConverter")
    public static class DisciplinaCodigoConverter extends DisciplinaConverter {
        public DisciplinaCodigoConverter() {
			super(Disciplina::getCodigo, DisciplinaDAO::retrieveByCodigo);
        }
    }

    @FacesConverter("disciplinaNomeConverter")
    public static class DisciplinaNomeConverter extends DisciplinaConverter {
        public DisciplinaNomeConverter() {
            super(Disciplina::getNome, DisciplinaDAO::findByNome);
        }
    }
}
