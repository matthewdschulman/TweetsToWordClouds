import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


public class TwitterScraper {
    /**
     * Usage: java twitter4j.examples.search.SearchTweets [query]
     *
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
    	int twitterSearchCounter = 0;
        System.out.println("Welcome to the Word Cloud Generator!");
        LinkedList<String> userQueries = AskUser.getQueries();
        LinkedList<String> forIRAnalysis = new LinkedList<String>();
        Iterator<String> iter = userQueries.iterator();
        while (iter.hasNext()) {
        	forIRAnalysis.add(iter.next());
        }
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
        List<String> wordsToExclude = WordsToExclude.createList();
        while (numOfQueries > 0) {
        	String fullString = "";
        	LinkedHashMap<String, Integer> wordFreq = new LinkedHashMap<String, Integer>();
        	numOfQueries--;
        	if (firstTimeThrough) {
        		System.out.print("Please wait one moment...");
        	}
        	firstTimeThrough = false;
        	PrintWriter outText = null;
        	PrintWriter outHash = null;
	        try {
	        		curTerm = userQueries.remove();
	            try {
	                //create an output file	         
	                File logFileText = new File(curTerm.replace(" ", ""));
	                File logFileTextHash = new File(curTerm.replace(" ", "") + "Hash");
	                outText = new PrintWriter(new FileWriter(logFileText)); 	
	                outHash = new PrintWriter(new FileWriter(logFileTextHash));
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
	                	String curTweet = tweet.getText().toLowerCase() + " ";
	                	outText.print(curTweet);
	                	String[] parts = curTweet.replace(",","").split(" ");
	                	//add substrings to hashmap
	                	for (String subWord : parts) {
	                		if (!wordsToExclude.contains(subWord) && subWord.length() > 3) {
	                			fullString += (subWord + " ");
		                		if (wordFreq.containsKey(subWord)) {
		                			//increase value by one
		                			wordFreq.put(subWord, wordFreq.get(subWord) + 1);
		                		}
		                		else {
		                			//first occurence of the word
		                			wordFreq.put(subWord, 1);
		                		}
	                		}
	                	}	                    
	                }
	            } while ((query = result.nextQuery()) != null && twitterSearchCounter < 10);	 
	            
	        } catch (TwitterException te) {
	            te.printStackTrace();
	            System.out.println("Failed to search tweets: " + te.getMessage());
	            System.exit(-1);
	        }
	        outText.close();
	        LinkedHashMap<String, Integer> wordFreqSorted = sortHashMapByValues(wordFreq);

	        List<String> keyList = new ArrayList<String>(wordFreqSorted.keySet());
	        
		    for ( int i = keyList.size() - 1; i >= 0 ; i-- ) {
		    	String key = keyList.get(i);
		    	if (!wordsToExclude.contains(key) && key.length() > 3) {
			        String value = wordFreqSorted.get(key).toString();  	           
			        outHash.println(key + ", " + value);   
		    	}
		    	else {
		    		wordFreqSorted.remove(key);
		    	}
		    }        
	        outHash.close();
	        //String[] toPassToWordCloud = createStringArrFromHash(wordFreqSorted);
	        //WordCloud.main(toPassToWordCloud, curTerm);
	        WordCloud.main(fullString.split(" "), curTerm);
        }
    if (forIRAnalysis.size() > 1) {
    	VectorSpaceModelTester.main(forIRAnalysis);   
    }
    else {
    	System.out.println("No comparative word frequency analysis conducted, because you only entered 1 search term.");
    }
    }

   
	public static LinkedHashMap<String, Integer> sortHashMapByValues(HashMap<String, Integer> passedMap) {
	   List<String> mapKeys = new ArrayList<String>(passedMap.keySet());
	   List<Integer> mapValues = new ArrayList<Integer>(passedMap.values());
	   Collections.sort(mapValues);
	   Collections.sort(mapKeys);

	   LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();

	   Iterator<Integer> valueIt = mapValues.iterator();
	   while (valueIt.hasNext()) {
	       Object val = valueIt.next();
	       Iterator<String> keyIt = mapKeys.iterator();

	       while (keyIt.hasNext()) {
	           Object key = keyIt.next();
	           String comp1 = passedMap.get(key).toString();
	           String comp2 = val.toString();

	           if (comp1.equals(comp2)){
	               passedMap.remove(key);
	               mapKeys.remove(key);
	               sortedMap.put((String)key, (Integer)val);
	               break;
	           }
	       }
	   }
	   return sortedMap;
	}
    
}