import java.util.ArrayList;
import java.util.List;


public class WordsToExclude {

	public static List<String> createList() {
		List<String> wordsToExclude = new ArrayList<String>();
		wordsToExclude.add("this");
		wordsToExclude.add("the");
		wordsToExclude.add("rt");
		wordsToExclude.add("for");
		wordsToExclude.add("it");
		return wordsToExclude;
	}

}
