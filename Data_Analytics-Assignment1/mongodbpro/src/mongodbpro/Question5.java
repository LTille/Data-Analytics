package mongodbpro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

//List the movie title and corresponding genre one user rated
public class Question5 {
	
	public static void main(String[] args){
		
		List<String> userList = new ArrayList<String>();
		StringBuffer buffer = new StringBuffer();
		try{
		    System.out.println("Enter the userID to see all the movies he/she rated");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			final String userID = br.readLine();
			System.out.println("You entered "+userID);
			
			MongoClient mongoClient = new MongoClient("localhost",27017);
			MongoDatabase db = mongoClient.getDatabase("test");
			MongoCollection<Document> rating = db.getCollection("rating");
			MongoCollection<Document> movie = db.getCollection("movie");
			
			FindIterable<Document> findUser = rating.find(new Document("UserID", userID));
			
			findUser.forEach(new Block<Document>(){				
			  @Override
			  public void apply(final Document document1){
				//get the id of those movies this user rated
				 FindIterable<Document> findMovie = movie.find(new Document("MovieID",document1.get("MovieID")));
					
					findMovie.forEach(new Block<Document>(){
						@Override
						public void apply(final Document document2){	

							buffer.append("Title: "+document2.get("Title")).append("   ");
							buffer.append("Genres: "+document2.get("Genres")).append("\n");
						
						//write the result into collection
						 //db.getCollection("Movie"+userID+"Rated").insertOne(new Document("Title",document2.get("Title"))
								 //.append("Genres", document2.get("Genres")));
						 }
				    });						
			 }
		 });
			   System.out.println(buffer.toString());
		}catch(Exception e){
			
		  e.printStackTrace();
		}
	}
}
