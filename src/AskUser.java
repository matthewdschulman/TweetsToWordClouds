import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;


public class AskUser {

	public static LinkedList<String> getQueries() {
		LinkedList<String> userQueries = new LinkedList<String>();
		Scanner user_input = new Scanner(System.in);		
		//determine user's interest
		System.out.println("What is your first search term?");
		userQueries.add(user_input.nextLine());
		while(true) {
			System.out.println("Do you want to include an additional search term? Please enter 'Y' or 'N'");
			if (user_input.nextLine().equals("Y")) {
				userQueries = getTerm(userQueries, user_input);
			}
			else {
				//user is finished entering search terms
				break;
			}			
		}
		//inform the user of his search terms
		System.out.print("Using Twitter's API to search for the following terms:");
		System.out.println(Arrays.toString(userQueries.toArray()));
		
		//return the users' queries
		return userQueries;
	}
	
	public static LinkedList<String> getTerm(LinkedList<String> userQueries, Scanner user_input) {
		System.out.println("What is your next search term?");
		userQueries.add(user_input.nextLine());
		return userQueries;
	}

}
