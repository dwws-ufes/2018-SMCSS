package br.ufes.informatica.smcss.core.domain;

import java.util.List;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;

@Entity
public class SolicitacaoMatricula extends PersistentObjectSupport {

	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@NotNull
	private Aluno aluno;

	@Convert(converter = SituacaoSolicitacaoMatricula.Converter.class)
	private SituacaoSolicitacaoMatricula situacao;

	@NotNull
	@ManyToMany(targetEntity = OfertaDisciplina.class)
	private List<OfertaDisciplina> ofertas;

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public SituacaoSolicitacaoMatricula getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoSolicitacaoMatricula situacao) {
		this.situacao = situacao;
	}

	public List<OfertaDisciplina> getOfertas() {
		return ofertas;
	}

	public void setOfertas(List<OfertaDisciplina> ofertas) {
		this.ofertas = ofertas;
	}

	@Override
	@javax.persistence.Transient
	public String toString() {
		return "SolicitacaoMatricula{id:" + this.getId() + "}";
	}
}
