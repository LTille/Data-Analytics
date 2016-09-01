package mongodbpro;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FileTransform {
    
	public static void readFilebyLine(String fileName){
		
	    JSONObject jsonObject = null;
		File file = new File(fileName);
		BufferedReader reader = null;
		String str =null;
		try{
			  reader = new BufferedReader(new FileReader(file));
			 while((str=reader.readLine())!=null){
				  //Transform movies.dat
				  jsonObject = new JSONObject();
				  String[] each=str.split("::");
		          jsonObject.put("MovieID", each[0]);
		          jsonObject.put("Title", each[1]);
		          String [] genre = each[2].split("\\|");
		          String[] array = new String[genre.length];
		          for(int i=0;i<genre.length;i++){
		          
		        	  array[i]=genre[i];
		        	 
		          }     
		          jsonObject.put("Genres", array);
		          
		          //Transform ratings.dat
		         /* jsonObject = new JSONObject();
				  String[] each=str.split("::");
		          jsonObject.put("UserID", each[0]);
		          jsonObject.put("MovieID", each[1]);
		          jsonObject.put("Rating", each[2]);
		          jsonObject.put("Timestamp", each[3]);*/
		          
	             //Transform tags.dat
		         /* jsonObject = new JSONObject();
				  String[] each=str.split("::");
		          jsonObject.put("UserID", each[0]);
		          jsonObject.put("MovieID", each[1]);
		          jsonObject.put("Tag", each[2]);
		          jsonObject.put("Timestamp", each[3]);*/
		          
			     Write(jsonObject.toString()+"\n");
			}
			reader.close();
		}catch(IOException e){
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void Write(String each){
		  
		BufferedWriter  bw = null;
		  
		  try{
		      File f = new File("/Users/Tillie/Desktop/movie.json");
		     // File f = new File("/Users/Tillie/Desktop/rating.json");
		     // File f = new File("/Users/Tillie/Desktop/tag.json");
		      bw = new BufferedWriter(new FileWriter(f,true));
		      bw.write(each);
		      bw.close();
		  }catch(Exception e){
		    e.printStackTrace();
		  }
		   
		 }
	
	public static void main(String[] args){
		
		String fileName ="/Users/Tillie/Desktop/movies.dat";
		//String fileName ="/Users/Tillie/Desktop/ratings.dat";
		//String fileName ="/Users/Tillie/Desktop/tags.dat";
		readFilebyLine(fileName);
		System.out.println("Transformation complete");
	
	}
}
