package br.ufes.informatica.smcss.core.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("cpfValidator")
public class CPFValidator implements Validator<String> {

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
    public void validate(FacesContext context, UIComponent component, String value) throws ValidatorException {
        // Remove "não dígitos" da string
        value = value.replaceAll("[^0-9]", "");

        if (value.length() != 11) {
            error("CPF length must be 11");
        }

        if ((value.charAt(9) != modulo11(value, 9)) || (value.charAt(10) != modulo11(value, 10))) {
            error("CPF is invalid");
        }
    }

    private void error(String summary) throws ValidatorException {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, "CPFF");
        throw new ValidatorException(message);
    }
}
