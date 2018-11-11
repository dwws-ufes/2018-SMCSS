package br.ufes.informatica.smcss.core.converter;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

import br.ufes.informatica.smcss.core.domain.Pessoa;
import br.ufes.informatica.smcss.core.persistence.PessoaDAO;

@FacesConverter("pessoaNomeConverter")
public class PessoaNomeConverter implements Converter<Pessoa> {

    private static final String NAME="pessoaNomeConverterBean";

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
        private PessoaDAO pessoaDAO;

        public PessoaDAO getPessoaDAO() {
            return pessoaDAO;
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
    private PessoaDAO getPessoaDAO(FacesContext context) {
        Application app = context.getApplication();
        Bean bean = app.evaluateExpressionGet(context, "#{" + NAME + "}", Bean.class);
        PessoaDAO pessoaDAO = bean.getPessoaDAO();
        return pessoaDAO;
    }

    @Override
    public Pessoa getAsObject(FacesContext context, UIComponent component, String value) {
        PessoaDAO pessoaDAO = getPessoaDAO(context);
        return (value == null || value.isEmpty()) ? null : pessoaDAO.retrieveByNome(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Pessoa value) {
        return (value == null) ? null : value.getNome();
    }
}
