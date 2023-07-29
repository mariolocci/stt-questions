package it.crs4.stt.questions.text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import it.crs4.stt.questions.model.Document;
import lombok.Data;
import lombok.extern.java.Log;

@Data
@Log
public class FaQReader {
	
	public void parse(String currentRecord, Document doc) {
		String fields [] = currentRecord.split("\n");
		for(String field:fields){
			if(field.endsWith("?"))
				doc.setTitle(field);
			else
				doc.setBody(field);
		}
	}
	
	
	public List<Document> read( File file,String charset) throws IOException{
		String sCurrentLine;
		List<Document> docs= new ArrayList<Document>();
		BufferedReader br = new BufferedReader(new FileReader(file));
        String currentRecord="";
        Document currentItem = new Document();
		while ((sCurrentLine = br.readLine()) != null) {
			if (sCurrentLine.isEmpty()) {
				this.parse(currentRecord,currentItem);
			    log.info(" found new item");
			    docs.add(currentItem);
			    currentRecord="";
			    currentItem = new Document();
			}
			else currentRecord+=sCurrentLine +"\n";
		
	}
		br.close();
		return docs;
		
	}
}
