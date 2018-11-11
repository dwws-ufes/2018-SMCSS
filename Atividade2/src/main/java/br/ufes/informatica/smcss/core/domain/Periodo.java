package br.ufes.informatica.smcss.core.domain;

import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Embeddable
public class Periodo {

	@NotNull
	@Temporal(TemporalType.DATE)
	private Date inicio;

	@NotNull
	@Temporal(TemporalType.DATE)
	private Date fim;

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFim() {
        return fim;
    }

    public void setFim(Date fim) {
        this.fim = fim;
    }
}
