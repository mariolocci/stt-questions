package it.crs4.stt.questions.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import it.crs4.stt.questions.model.Document;

public class DocumentServiceTest {
	
	@Test
	public void searchText() throws InterruptedException{
		 Document doc = new Document("andare in montagna","ciao sono andato al mare");
		 DocumentService docservice = new DocumentService();
		 docservice.addDocument(doc);
		 List<Document> docs = docservice.searchDocument("mare");
		 assertTrue(!docs.isEmpty());
	}

}
