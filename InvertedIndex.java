
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InvertedIndex extends IO {
	
	public String[]  args;
	public static HashMap<String, HashMap<Integer, Vector<Integer>>> map1; 
	public static HashMap<String, Boolean> sw ;
	public InvertedIndex(String [] arr,boolean indexCreated) {
		args=arr;
		map1 = new HashMap<>();
		sw = new HashMap<>();
		setIO(args,indexCreated);
		
	}
	//creates inverted index if one is not already created
	public void createIndex() {
		
		String line;
		String pattern = "\\<[^>]*>";

		Pattern p = Pattern.compile(pattern);
		while ((line = getLine(stopList)) != null) {

			sw.put(line, true);

		}

		for (int i = 0; i < 200; i++) {
			char c1;
			int a1;
			String t1 = "";

			int wc = 0;
			try {

				a1 = corpus_[i].read();
				StringBuilder ex = new StringBuilder();
				while (a1 != -1) {

					c1 = (char) a1;
					ex.append(c1);
					if (c1 == '>') {
						t1 = ex.toString();

						Matcher m = p.matcher(t1);

						if (m.find()) {
							ex.delete(0, ex.length());
							t1 = t1.replaceAll("\\<[^>]*>", "");
							String t2 = t1;

							StringBuilder s2 = new StringBuilder();

							for (int l = 0; l < t2.length(); ++l) {

								char ch = t2.charAt(l);

								if (Character.isLetterOrDigit(ch)) {

									s2.append(ch);

								} else {

									if (s2.length() != 0 && map1.containsKey(s2.toString())
											&& !s2.toString().matches("\\s")) {

										wc++;

										HashMap<Integer, Vector<Integer>> tempDocs = map1.get(s2.toString());
										if (tempDocs.containsKey(i)) {

											tempDocs.get(i).add(wc);
										} else {
											//stores location of word
											Vector<Integer> loc = new Vector();
											loc.add(wc);
											tempDocs.put(i, loc);

										}

										s2.delete(0, s2.length());
									} else if (s2.length() != 0 && !s2.toString().matches("\\s*")) {
										wc++;

										if (!sw.containsKey(s2.toString())) {

											HashMap<Integer, Vector<Integer>> tempDocs = new HashMap<>();
											Vector<Integer> loc = new Vector();
											loc.add(wc);
											tempDocs.put(i, loc);
											map1.put(s2.toString(), tempDocs);

										}

										// s = "";
										s2.delete(0, s2.length());

									}
								}
							}

							t1 = "";
							t2 = "";

						}
					}
					a1 = getChar(corpus_[i]);

				}

			} catch (IOException e) {

			}

		}

		
		//serializes inverted ndex
		try {
			FileOutputStream fos = new FileOutputStream("inverted.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(map1);
			oos.close();
			fos.close();
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		//writes to invertedIndex to outputfile
		Iterator<String> itr_ = map1.keySet().iterator();

		while (itr_.hasNext()) {
			String word = itr_.next();

			Iterator<Integer> itr = map1.get(word).keySet().iterator();
			displayln("< " + word + " >", invertedIndex);
			while (itr.hasNext()) {
				int docNum = itr.next();
				Vector<Integer> vec = map1.get(word).get(docNum);

				displayln("Document " + docNum + " , location= " + vec, invertedIndex);
			}

		}

		
		
		
		try {
			corpus.close();
			
			invertedIndex.close();
			stopList.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void deser() {
		
		
	      try
	      {
	         FileInputStream fis = new FileInputStream("inverted.ser");
	         ObjectInputStream ois = new ObjectInputStream(fis);
	         map1 = (HashMap) ois.readObject();
	         ois.close();
	         fis.close();
	      }catch(IOException e)
	      {
	         e.printStackTrace();
	         return;
	      }catch(ClassNotFoundException c)
	      {
	        
	         c.printStackTrace();
	         return;
	      }
	      
	      /*
	      Iterator<String> itr_ = map1.keySet().iterator();

			while (itr_.hasNext()) {
				String word = itr_.next();

				Iterator<Integer> itr = map1.get(word).keySet().iterator();
				System.out.println("< " + word + " >");
				while (itr.hasNext()) {
					int docNum = itr.next();
					Vector<Integer> vec = map1.get(word).get(docNum);

					System.out.println("Document " + docNum + " , location= " + vec);
				}

			}
			
			*/
	      
		

	
	}
	
	
	
	

}
