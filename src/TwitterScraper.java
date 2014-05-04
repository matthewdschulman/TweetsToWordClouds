import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;


public class TwitterScraper {
    /**
     * Usage: java twitter4j.examples.search.SearchTweets [query]
     *
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
    	int whileCounter = 0;
    	int getFirstCounter = 0;
    	int twitterSearchCounter = 0;
        System.out.println("Welcome to the Word Cloud Generator!");
        LinkedList<String> userQueries = AskUser.getQueries();
        int numOfQueries = userQueries.size();
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
          .setOAuthConsumerKey("BUO4hXJ8rtdgVLMXr68e5rRTg")
          .setOAuthConsumerSecret("e5mgJJU4BiJAaojXUjOzHgaDWSwrYa1S3qMAWms8M00IE4uRps")
          .setOAuthAccessToken("2218482720-13VX6k7BWyoa2VPf3FB6CT5MuIoEHDnIH0Njs0C")
          .setOAuthAccessTokenSecret("tR6Dg2gjopjkVWetLWFKlsYrNc8dsLzSxrPo7B0ZzRlRE");
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        //iterates through every one of the user's search terms
        String curTerm = "";
        boolean firstTimeThrough = true;
        while (numOfQueries > 0) {
        	numOfQueries--;
        	if (firstTimeThrough) {
        		System.out.print("Please wait one moment...");
        	}
        	firstTimeThrough = false;
        	whileCounter++;
        	PrintWriter out = null;
	        try {
	        		curTerm = userQueries.remove();
	        		getFirstCounter++;
	            try {
	                //create an output file	         
	                File logFile = new File(curTerm.replace(" ", ""));
	                out = new PrintWriter(new FileWriter(logFile)); 	
	                //test that out is working
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
	            
	            Query query = new Query(curTerm);	            
	            QueryResult result;	   
	            do {
	                result = twitter.search(query);
	                System.out.print(".");
	                twitterSearchCounter++;
	                List<Status> tweets = result.getTweets();
	                for (Status tweet : tweets) {
	                    out.print(tweet.getText() + " ");
	                }
	            } while ((query = result.nextQuery()) != null && twitterSearchCounter < 10);	 
	            
	        } catch (TwitterException te) {
	            te.printStackTrace();
	            System.out.println("Failed to search tweets: " + te.getMessage());
	            System.exit(-1);
	        }
	        out.close();
        }
    Reset.reset();    
    }
}