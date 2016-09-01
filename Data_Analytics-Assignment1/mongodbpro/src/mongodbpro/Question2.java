package mongodbpro;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.TreeSet;
import org.bson.Document;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Question2 {
	
	public static void main(String[] args){
		
		final TreeSet<String> userSet = new TreeSet<String>();
		try{
		    System.out.println("Please enter the userID with whom similar users you are trying to find");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			final String userID = br.readLine();
			System.out.println("You entered "+userID);
			
			MongoClient mongoClient = new MongoClient("localhost",27017);
			MongoDatabase db = mongoClient.getDatabase("test");
			final MongoCollection<Document> rating = db.getCollection("rating");
			
			FindIterable<Document> findUser = rating.find(new Document("UserID", userID));
			
			findUser.forEach(new Block<Document>(){				
			  @Override
			  public void apply(final Document document1){
				//get the id of those movies this user rated
				 FindIterable<Document> findMovie = rating.find(new Document("MovieID",document1.get("MovieID")));
					
					findMovie.forEach(new Block<Document>(){
						@Override
						public void apply(final Document document2){		
							
							userSet.add(document2.get("UserID").toString());
						 }
				     });	

					System.out.println(userSet);
			 }
		 });
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
