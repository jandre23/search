

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

public class SearchEngine extends IO {

	public static void main(String[] args) {
		// parameter to constructor
		//true if index  was  already created and serialized
		//false if index was not created
		
		InvertedIndex index = new InvertedIndex(args, true);
		Stemmer s = new Stemmer();

		
		//if index was created call index.deser()
		//else call
		// index.createIndex();
		index.deser();

		//if stemmed index was created call index.deser()
				//else call
		 //createStemIndex(index);
		s.deser("stemmedIndex.ser");
		
		searchIndex(index,s);
		 //System.out.println(s.deser("stemmedIndex.ser").size());

	}

	// creates a stemmed index from your invertex index if one is already not
	// created
	public static void createStemIndex(InvertedIndex index) {

		
		HashMap<String, Vector<String>> stem = new HashMap<>();
		// if stemminig option is turn on(true) we then proceed

		char[] w = new char[501];
		Stemmer s = new Stemmer();
		Iterator<String> it = index.map1.keySet().iterator();
		while (it.hasNext()) {
			String word = it.next();
			// stemmedMap.put(word,index.map1.get(word));
			int j = 0;
			for (int i = 0; i < word.length(); i++) {
				char ch = word.charAt(i);
				if (Character.isLetter((char) ch)) {

					ch = Character.toLowerCase((char) ch);
					w[j] = (char) ch;
					if (j < 500)
						j++;
					if (i == word.length() - 1) {

						for (int c = 0; c < j; c++)
							s.add(w[c]);

						s.stem();
						{
							String u;

							u = s.toString();
							if (!stem.containsKey(u)) {
								Vector<String> v = new Vector<>();
								v.add(u);
								stem.put(word, v);
							} else {
								Vector<String> v = stem.get(u);
								if (!v.contains(word))
									v.add(word);

							}
							/*
							 * if( stemmedMap.containsKey(u)) { HashMap<Integer,Vector<Integer>>
							 * stemmedDoc=stemmedMap.get(u); HashMap<Integer,Vector<Integer>> currentDoc=
							 * index.map1.get(word);
							 * 
							 * Iterator<Integer> docIt= currentDoc.keySet().iterator(); while(
							 * docIt.hasNext()) { //if map1 contains the word in the given doc int docNum=
							 * docIt.next(); if(stemmedDoc.containsKey(docNum)) {
							 * 
							 * Vector<Integer> location= currentDoc.get(docNum); Iterator<Integer> locVec=
							 * location.iterator(); while( locVec.hasNext()) { int num= locVec.next();
							 * 
							 * if( !stemmedDoc.get(docNum).contains(num)) stemmedDoc.get(docNum).add(num);
							 * 
							 * 
							 * }
							 * 
							 * 
							 * } else stemmedDoc.put(docNum, currentDoc.get(docNum));
							 * 
							 * 
							 * } } else stemmedMap.put(u,index.map1.get(word));
							 */
							System.out.println(u);
						}
						break;

					}

					// System.out.println(ch);

				}

				else if (!Character.isLetter((char) ch)) {

					break;
				}

			}
		}

		// serializes stemmed index
		try {
			FileOutputStream fos = new FileOutputStream("stemmedIndex.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(stem);
			oos.close();
			fos.close();

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

	}
	
	//searches inverted index only if stemming is set to true on command line
	//else checks stemmed inverted index for all word containing stemm and outputs results of doc and locatoinx
public static void searchIndex(InvertedIndex index, Stemmer s) {
	HashMap<String, HashMap<Integer, Vector<Integer>>> map1=index.map1;
	HashMap<String, Vector<String>> stem = s.stem;
		while ((line = getLine(queries)) != null) {

			String type = line.replaceAll("\\<[^>]*>", "");
			String qw = line.replaceAll(".*<|>", "");
			if(stemming!=true)
			{
			if (map1.containsKey(qw)) {

				Iterator<Integer> itr = map1.get(qw).keySet().iterator();
				// System.out.println("type= " + type + " word= " + qw + " document # ");
				displayln("type= " + type + "  word= " + qw + " document # ", results);
				display("[", results);
				while (itr.hasNext()) {
					int docNum = itr.next();
					Vector<Integer> vec = map1.get(qw).get(docNum);
					if (!itr.hasNext()) {
						if (!type.matches("Frequency")) {
							// System.out.print(docNum);
							display(docNum + "]", results);
						} else {

							displayln("document # " + docNum + " frequency = " + vec.size(), results);
						}

					} else {
						if (!type.matches("Frequency")) {
							// System.out.print(docNum+",");
							display(docNum + ",", results);
						} else {

							// System.out.println("document # "+docNum + " frequency = "+ vec.size());
							displayln("document # " + docNum + " frequency = " + vec.size(), results);
						}
					}
				}
				displayln("", results);

			} else {
				displayln(qw + " does not appear in any documents", results);
			}
		}
			
			else {
					
				char[] w = new char[501];
				Stemmer x = new Stemmer();
				int j = 0;
				String word=qw;
				for (int i = 0; i < word.length(); i++) {
					char ch = word.charAt(i);
					if (Character.isLetter((char) ch)) {

						ch = Character.toLowerCase((char) ch);
						w[j] = (char) ch;
						if (j < 500)
							j++;
						if (i == word.length() - 1) {

							for (int c = 0; c < j; c++)
								x.add(w[c]);

							x.stem();
							{
								String u;

								u = x.toString();
								Vector<String> vecSt= stem.get(u);
								Iterator<String> it=  vecSt.iterator();
								while(it.hasNext()){
									String tempWord=it.next();
									
									
									if (!type.matches("Frequency")) {
										displayln("<"+tempWord+"> from stem word <"+u+">" ,results);
										displayln("location   "+map1.get(tempWord),results);
									} else {

										displayln("<"+tempWord+"> from stem word <"+u+">" ,results);
										Iterator<Integer> docIt=  map1.get(tempWord).keySet().iterator();
										int total=0;
										while(docIt.hasNext()) {
											int nextDoc= docIt.next();
											total+= map1.get(tempWord).get(nextDoc).size();
											
										}
										displayln("frequency is " + total, results);
									}
								
								
							}
							break;

						}

						

					}
					}
					else if (!Character.isLetter((char) ch)) break;
				}
					
				
			}

		}
		
		try {
			
			queries.close();
			
			
			results.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
