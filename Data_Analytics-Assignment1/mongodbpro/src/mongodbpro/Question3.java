package mongodbpro;

import java.util.List;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;

public class Question3 {
	
	  static int num=0;

	public static void main(String[] args){
		
		
	  try{
		  Mongo mongo = new Mongo("localhost", 27017);
		  DB db = mongo.getDB("test");    
		  MongoClient mongoClient = new MongoClient("localhost", 27017);   
		  MongoDatabase db1 = mongoClient.getDatabase("test");
		  DBCollection movie = db.getCollection("movie");
		  List list = movie.distinct("Genres");
		  System.out.println(list.size());

		  for(int i= 0;i<list.size();i++){

			  FindIterable<Document> findGenre = db1.getCollection("movie").find(new Document("Genres",list.get(i)));
			  findGenre.forEach(new Block<Document>(){

				  @Override
				  public void apply(final Document document) {
					  num++;			
				  }		
			  });
			  db1.getCollection("Genre_num").insertOne(new Document("Genre", list.get(i)).append("Amount",num));
			  num=0;
		  }
        
	   }catch(Exception e){
		   e.printStackTrace();
	   }
	
		
	}
}
