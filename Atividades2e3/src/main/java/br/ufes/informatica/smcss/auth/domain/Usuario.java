package br.ufes.informatica.smcss.auth.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;
import br.ufes.informatica.smcss.auth.util.Util;
import br.ufes.informatica.smcss.core.domain.Pessoa;

@Entity
public class Usuario extends PersistentObjectSupport {

    private static final long serialVersionUID = 1L;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @NotNull
    private String login;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = Permissao.class)
    @JoinTable(
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "permissao_id")
    )
    private Set<Permissao> permissoes;

    public Set<Permissao> getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(Set<Permissao> permissoes) {
        this.permissoes = permissoes;
    }

    @NotNull
    private String hashedPassword;

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public void setPassword(String password) {
        this.setHashedPassword(Util.createPasswordHash("SHA-512", "HEX", null, password));
    }

    @OneToOne(optional=true, targetEntity=Pessoa.class)
    private Pessoa pessoa;

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
}
