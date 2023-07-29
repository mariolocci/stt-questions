package it.crs4.stt.questions.producers;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import it.crs4.stt.questions.model.Document;
import it.crs4.stt.questions.service.DocumentService;
import it.crs4.stt.questions.text.FaQReader;
@ApplicationScoped
public class DocumentProducer {
	  
	
	  @Produces
	  @DocumentProducerType(ServiceType.DOCUMENT)
	  public DocumentService producer() throws IOException{
		FaQReader faqreader = new FaQReader();
		File file= new File("/Users/mariolocci/Documents/workspace/stt-questions/src/test/resources/faqps4.txt");
		List<Document> list = faqreader.read(file, "utf8");
		DocumentService docservice= new DocumentService();
		for(Document doc:list)
			try {
				docservice.addDocument(doc);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return docservice;
			 } 
	}


