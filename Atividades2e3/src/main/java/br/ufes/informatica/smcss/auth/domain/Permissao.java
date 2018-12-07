package br.ufes.informatica.smcss.auth.domain;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;

@Entity
public class Permissao extends PersistentObjectSupport {

    private static final long serialVersionUID = 1L;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @NotNull
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public enum Tipo {
        A;
    }
}
