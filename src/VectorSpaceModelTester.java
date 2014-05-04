

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * the tester class.
 * @author swapneel
 */
public class VectorSpaceModelTester {

	public static void main(LinkedList<String> listOfDocs) {	
					
		LinkedList<Document> documents = new LinkedList<Document>();
		Iterator<String> iterStrings = listOfDocs.iterator();
		while (iterStrings.hasNext()) {
			String curDocName = iterStrings.next().replace(" ", "");
			Document curDoc = new Document(curDocName.replace(" ",""));
			documents.add(curDoc);
		}		
		
		ArrayList<Document> documentList = new ArrayList<Document>();
		Iterator<Document> iterDocs = documents.iterator();
		
		while (iterDocs.hasNext()) {
			Document curDoc = iterDocs.next();
			documentList.add(curDoc);
		}		
		
		Corpus corpus = new Corpus(documentList);
		
		VectorSpaceModel vectorSpace = new VectorSpaceModel(corpus);
		
		for (int i = 0; i < documentList.size(); i++) {
			for (int j = i + 1; j < documents.size(); j++) {
				Document doc1 = documents.get(i);
				Document doc2 = documents.get(j);
				System.out.println("\nComparing " + doc1 + " and " +  doc2);
				System.out.println(vectorSpace.cosineSimilarity(doc1, doc2));
			}
		}		
	}
}
