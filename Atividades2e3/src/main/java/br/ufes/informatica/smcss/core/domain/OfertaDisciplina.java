package br.ufes.informatica.smcss.core.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;

@Entity
public class OfertaDisciplina extends PersistentObjectSupport implements Comparable<OfertaDisciplina>{
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@NotNull
	@ManyToOne
	private Disciplina disciplina;

	@NotNull
	@ManyToOne
	private Professor professor;

	@NotNull
	@ManyToOne
	private PeriodoLetivo periodoLetivo;

	@OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL, targetEntity = Aula.class, mappedBy = "ofertaDisciplina")
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

    @Override
    public int compareTo(OfertaDisciplina other) {
        int periodoLetivo = this.periodoLetivo.compareTo(other.periodoLetivo);
        return (periodoLetivo != 0) ? periodoLetivo :
            this.disciplina.getCodigo().compareTo(other.disciplina.getCodigo());
    }
}
