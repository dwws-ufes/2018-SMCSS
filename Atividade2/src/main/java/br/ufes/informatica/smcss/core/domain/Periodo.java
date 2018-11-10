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
}
