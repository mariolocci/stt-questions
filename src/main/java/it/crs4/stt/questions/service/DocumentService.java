package it.crs4.stt.questions.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.service.ServiceRegistry;

import it.crs4.stt.questions.model.Document;


public class DocumentService {
	
	private static SessionFactory factory;

	public DocumentService() {
	
		Configuration configuration = new Configuration();
		//configuration.setProperty("hibernate.dialect","org.hibernate.dialect.PostgreSQLDialect");
		//configuration.setProperty("hibernate.connection.datasource","jdbc/test");
//		configuration.setProperty("hibernate.search.default.indexBase", "/Users/mariolocci/luceneindex");
//		configuration.setProperty("hibernate.search.default.directory_provider","filesystem");
		configuration=configuration.addAnnotatedClass(Document.class);
		configuration=configuration.configure();
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
				
		factory = configuration.buildSessionFactory(serviceRegistry);
	};
	
	public List<Document> searchDocument( String text){
		//EntityManager em = entityManagerFactory.createEntityManager();
		Session session=factory.openSession();
		FullTextSession fts =Search.getFullTextSession(session);
		Transaction tx = fts.beginTransaction();

	// create native Lucene query unsing the query DSL
	// alternatively you can write the Lucene query using the Lucene query parser
	// or the Lucene programmatic API. The Hibernate Search DSL is recommended though
	QueryBuilder qb = fts.getSearchFactory()
	    .buildQueryBuilder().forEntity(Document.class).get();
	org.apache.lucene.search.Query luceneQuery = qb
	  .keyword()
	  .onFields("title", "body")
	  .matching(text)
	  .createQuery();

	// wrap Lucene query in a javax.persistence.Query
	 FullTextQuery jpaQuery = fts.createFullTextQuery(luceneQuery, Document.class);

	// execute search
	List<Document> result = jpaQuery.list();

	tx.commit();
	session.close();
	return result;
	}
	
	public void addDocument(Document doc) throws InterruptedException{
		Session session=factory.openSession();
		//FullTextSession fts =Search.getFullTextSession(session);
		//fts.createIndexer().startAndWait();
		Transaction tx = session.beginTransaction();
		
		session.save(doc);
		tx.commit();
		session.close();
	}
	
	

}
