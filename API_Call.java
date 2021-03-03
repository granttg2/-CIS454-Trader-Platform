package org.backend.Trader_Platform;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.Scanner;

public class API_Call{
   public static void main(String[] argv) {
      try{ 
         Scanner s = new Scanner(System.in);
         System.out.print("Enter URL:");
         String user_url = s.nextLine();
         s.close();
         URL url=new URL(user_url);    
         HttpURLConnection conn = (HttpURLConnection)url.openConnection();
         conn.setRequestMethod("GET");
         conn.connect();
         
         Scanner sc = new Scanner(url.openStream());
         while(sc.hasNext()){
            System.out.println(sc.nextLine());
            }
            
         }
      
	   catch(Exception e){
         System.out.print("Error");
         
         }
	}
}
      
   