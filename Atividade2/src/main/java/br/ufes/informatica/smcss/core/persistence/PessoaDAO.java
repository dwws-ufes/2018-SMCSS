package br.ufes.informatica.smcss.core.persistence;

import java.util.List;

import javax.ejb.Local;

import br.ufes.inf.nemo.jbutler.ejb.persistence.BaseDAO;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.nemo.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;
import br.ufes.informatica.smcss.core.domain.Pessoa;

@Local
public interface PessoaDAO extends BaseDAO<Pessoa> {

    public List<Pessoa> findByNome(String nome);

    public Pessoa retrieveByNome(String nome);
    public Pessoa retrieveByCpf(String cpf)
        throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException;
}
