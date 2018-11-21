package br.ufes.informatica.smcss.core.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;

@Entity
public class PeriodoLetivo extends PersistentObjectSupport implements Comparable<PeriodoLetivo> {

	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@NotNull
	@Size(max=10)
	private String codigo;

	@NotNull
	@Embedded
	private Periodo duracao = new Periodo();

	@NotNull
	@Embedded
	@AttributeOverrides({
	    @AttributeOverride(name="inicio", column=@Column(name="inicio_matricula")),
	    @AttributeOverride(name="fim", column=@Column(name="fim_matricula"))
	})
	private Periodo periodoMatricula = new Periodo();

	@NotNull
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="inicio", column=@Column(name="inicio_inscricao")),
		@AttributeOverride(name="fim", column=@Column(name="fim_inscricao"))
	})
	private Periodo periodoInscricao = new Periodo();

	@NotNull
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="inicio", column=@Column(name="inicio_ajuste_matricula")),
		@AttributeOverride(name="fim", column=@Column(name="fim_ajuste_matricula"))
	})
	private Periodo periodoAjusteMatricula = new Periodo();

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Periodo getDuracao() {
		return duracao;
	}

	public void setDuracao(Periodo duracao) {
		this.duracao = duracao;
	}

	public Periodo getPeriodoMatricula() {
		return periodoMatricula;
	}

	public void setPeriodoMatricula(Periodo periodoMatricula) {
		this.periodoMatricula = periodoMatricula;
	}

	public Periodo getPeriodoInscricao() {
		return periodoInscricao;
	}

	public void setPeriodoInscricao(Periodo periodoInscricao) {
		this.periodoInscricao = periodoInscricao;
	}

	public Periodo getPeriodoAjusteMatricula() {
		return periodoAjusteMatricula;
	}

	public void setPeriodoAjusteMatricula(Periodo periodoAjusteMatricula) {
		this.periodoAjusteMatricula = periodoAjusteMatricula;
	}

	@Override
	@javax.persistence.Transient
	public String toString() {
		return "PeriodoLetivo{id:" + this.getId() + "}";
	}

    @Override
    public int compareTo(PeriodoLetivo other) {
        return this.codigo.compareTo(other.codigo);
    }
}
