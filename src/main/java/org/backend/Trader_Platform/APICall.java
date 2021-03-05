package org.backend.Trader_Platform;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Scanner;

public class APICall{
	
	public static String getHistoricData(String key, String ticker, String startDay, String endDay) throws IOException {
		String url = "http://api.marketstack.com/v1/eod?access_key=" + key + "&symbols=" + ticker + "&date_from=" + startDay + "&date_to=" + endDay;
		return getURLResult(url);
	}
	
	private static String getURLResult(String url) throws IOException {
		try(Scanner scanner = new Scanner(new URL(url).openStream(), StandardCharsets.UTF_8.toString())){
			scanner.useDelimiter("\\A");
			return scanner.hasNext() ? scanner.next() : "";
		}
	}
	
	
	
   public static void main(String[] argv) {
      try{ 
         Scanner s = new Scanner(System.in);
         //get base url
         System.out.print("Enter Base URL:");
         String user_url = s.nextLine();
         //get endpoint
         System.out.print("Enter End Points:");
         String user_endpoint = s.nextLine();
         
         //construct the url for api call
         String api_url = (user_url + "/" + user_endpoint);
         
         //get user api key and construct it into usable string
         System.out.print("End Your API Acess Key:");
         String key = s.nextLine();
         String user_key= ("?access_key="+ key);
         
         //get call symbol and construct it into usable string
         System.out.print("End Call Symbol:");
         String symbol = s.nextLine();
         String user_symbol = ("&symbols="+ symbol);
   
         
         //final url for api call
         String api_call_url = (api_url + user_key + user_symbol);

         //make http connection and return data
         URL url = new URL(api_call_url);
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
      
   