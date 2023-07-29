package it.crs4.stt.questions.text;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import it.crs4.stt.questions.model.Document;

public class FaQReaderTest {
	
	@Test
	public void readTest() throws IOException{
		FaQReader faqreader = new FaQReader();
		File file= new File("src/test/resources/faqps4.txt");
		List<Document> list = faqreader.read(file, "utf8");
		assertEquals(56, list.size());
	}

}
