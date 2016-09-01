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

//list all the tags for one movie
public class Question4 {
	
	public static void main(String[] args){
		
		List<String> userList = new ArrayList<String>();
		StringBuffer buffer = new StringBuffer();
		try{
		    System.out.println("You can enter the movie title to see all of its tags");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			final String title = br.readLine();
			System.out.println("You entered "+title);
			
			MongoClient mongoClient = new MongoClient("localhost",27017);
			MongoDatabase db = mongoClient.getDatabase("test");
			MongoCollection<Document> movie = db.getCollection("movie");
			MongoCollection<Document> tag = db.getCollection("tag");
			
			FindIterable<Document> findMovie = movie.find(new Document("Title", title));
			
			findMovie.forEach(new Block<Document>(){				
			  @Override
			  public void apply(final Document document1){
				//get the id of those movies this user rated
				 FindIterable<Document> findComment = tag.find(new Document("MovieID",document1.get("MovieID")));
					
					findComment.forEach(new Block<Document>(){
						@Override
						public void apply(final Document document2){		
						
							buffer.append("UserID: "+document2.get("UserID")).append("   ");
							buffer.append("Tag: "+document2.get("Tag")).append("\n");
						
						 //db.getCollection("UserCommentfor"+title).insertOne(new Document("UserID",document2.get("UserID"))
								 //.append("Tag", document2.get("Tag")));
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
