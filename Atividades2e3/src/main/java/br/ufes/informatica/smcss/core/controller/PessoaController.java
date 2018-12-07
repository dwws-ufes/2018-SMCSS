package br.ufes.informatica.smcss.core.controller;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;

import br.ufes.inf.nemo.jbutler.ejb.application.CrudService;
import br.ufes.inf.nemo.jbutler.ejb.controller.CrudController;
import br.ufes.informatica.smcss.core.application.PessoaService;
import br.ufes.informatica.smcss.core.domain.Pessoa;
import br.ufes.informatica.smcss.core.domain.Publicacao;

@Named
@SessionScoped
public class PessoaController extends CrudController<Pessoa> {

	private static final long serialVersionUID = 1L;

	@EJB
	private PessoaService pessoaService;

	@Override
	public CrudService<Pessoa> getCrudService() {
		return pessoaService;
	}

	@Override
	public void initFilters() {

	}

	public void listarPublicacoes() {

		String query1 = "PREFIX swrc: <http://swrc.ontoware.org/ontology#>"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
				+ "PREFIX d2r: <http://sites.wiwiss.fu-berlin.de/suhl/bizer/d2r-server/config.rdf#>"
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" + "PREFIX dcterms: <http://purl.org/dc/terms/>"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
				+ "PREFIX map: <file:///home/diederich/d2r-server-0.3.2/dblp-mapping.n3#>"
				+ "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" + "PREFIX dc: <http://purl.org/dc/elements/1.1/> 	"
				+ "SELECT DISTINCT ?titulo WHERE {" 
				+ "			?autor a foaf:Agent;"
				+ "         		 foaf:name \"Giancarlo Guizzardi\"." 
				+ "  		?artigo foaf:maker ?autor;"
				+ "          dc:title ?titulo." + "}" 
				+ "LIMIT 50";

		QueryExecution queryExecution = QueryExecutionFactory.sparqlService("https://dblp.l3s.de/d2r/sparql", query1);
		ResultSet results = queryExecution.execSelect();
		ArrayList<Publicacao> listaPublicacoes = new ArrayList<Publicacao>();

		while (results.hasNext()) {
			QuerySolution soln = results.next();
			Publicacao publicacao = new Publicacao();
			Literal tituloPublicacao = soln.getLiteral("titulo");
			publicacao.setTitulo("" + tituloPublicacao.getValue() + "");
			listaPublicacoes.add(publicacao);
		}
		this.selectedEntity.setPublicacoes(listaPublicacoes);

	}
}
