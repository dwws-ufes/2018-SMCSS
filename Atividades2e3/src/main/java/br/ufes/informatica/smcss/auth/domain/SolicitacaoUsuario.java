package br.ufes.informatica.smcss.auth.domain;

import java.util.Date;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import br.ufes.inf.nemo.jbutler.ejb.persistence.PersistentObjectSupport;
import br.ufes.informatica.smcss.util.EnumConverter;

@Entity
@NamedQueries({
    @NamedQuery(name="SolicitacaoUsuario.excluirAntigos", query="DELETE FROM SolicitacaoUsuario WHERE dataCriacao < :dataLimite"),
    @NamedQuery(name="SolicitacaoUsuario.deleteByLogin", query="DELETE FROM SolicitacaoUsuario WHERE login = :login")
})
public class SolicitacaoUsuario extends PersistentObjectSupport {

    private static final long serialVersionUID = 1L;

    @NotNull
    private String login;

    @NotNull
    private String codigo;

    @NotNull
    @Convert(converter = Tipo.Converter.class)
    private Tipo tipo;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao = new Date();

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @Transient
    public boolean isCriacao() {
        return this.tipo == Tipo.CRIACAO_CONTA;
    }

    @Transient
    public boolean isRecuperacaoSenha() {
        return this.tipo == Tipo.RECUPERACAO_SENHA;
    }

    public enum Tipo {
        CRIACAO_CONTA, RECUPERACAO_SENHA;

        @javax.persistence.Converter
        public static class Converter extends EnumConverter<Tipo> {}
    }
}
