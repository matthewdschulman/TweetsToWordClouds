import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.LinkedList;
import java.util.List;


public class TwitterScraper {
    /**
     * Usage: java twitter4j.examples.search.SearchTweets [query]
     *
     * @param args search query
     */
    public static void main(String[] args) {
        System.out.println("Welcome to the Word Cloud Generator!");
        LinkedList<String> userQueries = AskUser.getQueries();
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
          .setOAuthConsumerKey("BUO4hXJ8rtdgVLMXr68e5rRTg")
          .setOAuthConsumerSecret("e5mgJJU4BiJAaojXUjOzHgaDWSwrYa1S3qMAWms8M00IE4uRps")
          .setOAuthAccessToken("2218482720-13VX6k7BWyoa2VPf3FB6CT5MuIoEHDnIH0Njs0C")
          .setOAuthAccessTokenSecret("tR6Dg2gjopjkVWetLWFKlsYrNc8dsLzSxrPo7B0ZzRlRE");
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        //iterates through every one of the user's search terms
        while (userQueries != null) {
	        try {
	            Query query = new Query(userQueries.getFirst());
	            QueryResult result;
	            do {
	                result = twitter.search(query);
	                List<Status> tweets = result.getTweets();
	                for (Status tweet : tweets) {
	                    System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
	                }
	            } while ((query = result.nextQuery()) != null);
	            System.exit(0);
	        } catch (TwitterException te) {
	            te.printStackTrace();
	            System.out.println("Failed to search tweets: " + te.getMessage());
	            System.exit(-1);
	        }
	        userQueries.remove();
        }
    }
}