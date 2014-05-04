import java.io.IOException;
import java.util.Scanner;


public class Reset {

	public static void reset() throws IOException {
		System.out.println();
		// TODO Auto-generated method stub
		System.out.println("Would you like to do another search? Please enter 'Y' or 'N'");
		Scanner user_input = new Scanner(System.in);		
		//determine user's interest
		if (user_input.nextLine().equals("Y")) {
			TwitterScraper.main(null);
		}
		else {
			System.out.println("Thank you for using the Word Cloud Generator!");
			System.exit(-1);
		}
	}
}
