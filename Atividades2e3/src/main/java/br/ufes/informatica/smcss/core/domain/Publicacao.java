package br.ufes.informatica.smcss.core.domain;

import javax.persistence.Entity;
import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;

@Entity	
public class Publicacao extends PersistentObjectSupport {
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	private String titulo;
	private int ano;
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public int getAno() {
		return ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}

}
