package mongodbpro;

import org.bson.Document;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

import static java.util.Arrays.asList;
import com.mongodb.Block;

public class Question1 {
	 
	
	 public static void main( String args[] ){

		 StringBuffer buffer = new StringBuffer();
		 try{   
	    	
	    	//To directly connect to a single MongoDb server
	        MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
	        //get the database test
	        MongoDatabase db = mongoClient.getDatabase("test");	
	        //get rating and movie collections from database
	        MongoCollection<Document> rating =db.getCollection("rating");
	        MongoCollection<Document> movie = db.getCollection("movie");

	        
	        //Retrieve the average rating for each movie from rating collection
	        AggregateIterable<Document> iterable = rating.aggregate(asList(
	           new Document("$group", new Document("_id", "$MovieID").append("avgRating", new Document("$avg", "$Rating")))));
	       
	        //Find the movie title for the corresponding movieId obtained in the previous phase
	        iterable.forEach(new Block<Document>(){
	           
	        	public void apply(final Document document1){
	        	  //Find the MovieID whose _id field is equal to that returned in the query on rating collection
	        	  FindIterable<Document> findMovie = movie.find(new Document("MovieID",document1.get("_id")));
	        
	        	  findMovie.forEach(new Block<Document>(){
	        		
	        		  //retrieve the corresponding movie titles for some specific movieID obtained
	        		  //put movie titles and average rating into a new collection
	        		  @Override
	        		  public void apply(final Document document2){
	        			
	        			    buffer.append("Title: "+document2.get("Title")).append("   ");
							buffer.append("AvgRating: "+document1.get("avgRating")).append("\n");
						
	        			//db.getCollection("AverageRating").insertOne(new Document("Title",
	        				//	 document2.get("Title")).append("AvgRating",document1.getDouble("avgRating")));
	        			 
	        		  }
	        		
	        	  });
	        	 }
	          });
	        
	           System.out.println(buffer.toString());
	        }catch(Exception e){
		     
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         
		  }finally{
			 
		  }
	  }
}
