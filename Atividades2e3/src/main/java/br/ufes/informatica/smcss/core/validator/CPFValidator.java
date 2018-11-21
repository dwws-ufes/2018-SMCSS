package br.ufes.informatica.smcss.core.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("cpfValidator")
public class CPFValidator implements Validator {

    private char modulo11(String text, int length) {
        int mul = 2;
        int result = 0;
        for (int index = length -1; index >= 0; index --) {
            result += mul++ * (text.charAt(index) - '0');
        }
        result = 11 - (result % 11);
        if (result >= 10) {
            result = 0;
        }
        return (char) (result + '0');
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        // Converte para String
    	String cpf = (String) value;
    	
    	// Remove "não dígitos" da string
        cpf = cpf.replaceAll("[^0-9]", "");

        if (cpf.length() != 11) {
            error("CPF length must be 11");
        }

        if ((cpf.charAt(9) != modulo11(cpf, 9)) || (cpf.charAt(10) != modulo11(cpf, 10))) {
            error("CPF is invalid");
        }
    }

    private void error(String summary) throws ValidatorException {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, "CPFF");
        throw new ValidatorException(message);
    }
}
