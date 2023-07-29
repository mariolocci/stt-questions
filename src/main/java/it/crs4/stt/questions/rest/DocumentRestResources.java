package it.crs4.stt.questions.rest;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import it.crs4.stt.questions.model.Document;
import it.crs4.stt.questions.producers.DocumentProducerType;
import it.crs4.stt.questions.producers.ServiceType;
import it.crs4.stt.questions.service.DocumentService;
@Stateless
@Path("/semtech")
public class DocumentRestResources {

	@Inject
    @DocumentProducerType(ServiceType.DOCUMENT)
	private DocumentService documentService;

	@Inject
	private Logger log;

	@GET
	@Path("/searchText")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Document> searchText(@QueryParam("text") String text) throws IOException {

		log.info("classify text" + text);
		List<Document> docs = this.documentService.searchDocument(text);
		return docs;
	}

	

}
