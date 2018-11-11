package br.ufes.informatica.smcss.core.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;

@Entity
public class Disciplina extends PersistentObjectSupport implements Comparable<Disciplina> {
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@NotNull
	@ManyToOne
	private Curso curso;

	@NotNull
	@Size(max = 10)
	private String codigo;

	@NotNull
	@Size(max = 100)
	private String nome;

	@NotNull
	@Min(1)
	private int cargaHoraria;

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(int cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}

	@Override
	@javax.persistence.Transient
	public String toString() {
		return "Curso{id:" + this.getId() + ",nome:" + this.nome+ "}";
	}

    @Override
    public int compareTo(Disciplina other) {
        return this.codigo.compareTo(other.codigo);
    }
}
