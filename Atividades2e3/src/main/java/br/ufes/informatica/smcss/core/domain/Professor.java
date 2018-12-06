package br.ufes.informatica.smcss.core.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;

@Entity
public class Professor extends PersistentObjectSupport implements Comparable<Professor> {
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@NotNull
	@ManyToOne
	private Pessoa pessoa;

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	@Override
	@javax.persistence.Transient
	public String toString() {
		return "Professor{id:" + this.getId() + "}";
	}

    @Override
    public int compareTo(Professor other) {
        return this.pessoa.compareTo(other.pessoa);
    }

    @Transient
    public String getNome() {
        return pessoa.getNome();
    }

    public void setNome(String nome) {
        pessoa.setNome(nome);
    }
}
