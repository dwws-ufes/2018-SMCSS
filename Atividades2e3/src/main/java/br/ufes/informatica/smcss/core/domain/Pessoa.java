package br.ufes.informatica.smcss.core.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;

@Entity
public class Pessoa extends PersistentObjectSupport implements Comparable<Pessoa> {
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Size(max = 200)
	private String nome;

	@Size(max = 100)
	private String email;

	@NotNull
	@Size(min = 11)
	private String cpf;

	@NotNull
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	@Override
	@javax.persistence.Transient
	public String toString() {
		return "Pessoa{id:" + this.getId() + "}";
	}

	@Override
	public int compareTo(Pessoa other) {
		return this.nome.compareTo(other.nome);
	}
}
