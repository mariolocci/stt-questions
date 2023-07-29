package it.crs4.stt.questions.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Indexed
@Analyzer(impl=StandardAnalyzer.class)
public class Document {
	
	@Id
	@GeneratedValue
	private int id;
	
	
   @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	public String title;
	
   @Lob
   @Column( length = 100000 )
   @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	public String body;
	
	public Document(){
		
	}

	public Document(String title, String body) {
		this.setTitle(title);
		this.setBody(body);
	}

}
