package br.ufes.informatica.smcss.core.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;

@Entity
public class OfertaDisciplina extends PersistentObjectSupport {
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@NotNull
	private Disciplina disciplina;

	@NotNull
	private Professor professor;

	@NotNull
	private PeriodoLetivo periodoLetivo;

	@OneToMany(targetEntity=Aula.class,mappedBy="ofertaDisciplina")
	private List<Aula> aulas;

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public PeriodoLetivo getPeriodoLetivo() {
		return periodoLetivo;
	}

	public void setPeriodoLetivo(PeriodoLetivo periodoLetivo) {
		this.periodoLetivo = periodoLetivo;
	}

	public List<Aula> getAulas() {
		return aulas;
	}

	public void setAulas(List<Aula> aulas) {
		this.aulas = aulas;
	}

	@Override
	@javax.persistence.Transient
	public String toString() {
		return "OfertaDisciplina{id:" + this.getId() + "}";
	}
}
