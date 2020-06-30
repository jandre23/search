

//This class defines I/O variables and functions used by the compiler/interpreter.

import java.io.*;
import java.util.*;

public abstract class IO {
	public static BufferedReader corpus;
	public static File[][] html;
	public static BufferedReader [] corpus_;
	public static PrintWriter invertedIndex;
	public static BufferedReader stopList;
	public static BufferedReader queries;
	public static PrintWriter results;
	public static boolean  stemming=false;

	public static int a; // the current input character on "inStream"
	public static char c; // used to convert the variable "a" to the char type whenever necessary
	public static String line;

	public static int getNextChar(BufferedReader b)

	// Returns the next character on the input stream.

	{
		try {
			return b.read();
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public static int getChar(BufferedReader b)

	// Returns the next non-whitespace character on the input stream.
	// Returns -1, end-of-stream, if the end of the input stream is reached.

	{
		int i = getNextChar(b);

		return i;
	}

	public static void display(String s, PrintWriter p) {
		p.print(s);
	}

	public static void displayln(String s, PrintWriter p) {
		p.println(s);
	}

	public static String getLine(BufferedReader b) {
		try {
			return b.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static void setIO(String[] args,boolean indexCreated)

	// Sets the input and output streams to "inFile" and "outFile", respectively.
	// Sets the current input character "a" to the first character on the input
	// stream.

	{
		String[] comm = parse(args);
		
		try {
			
			
			if(indexCreated==false) {
			File file= new File( comm[0]);
			File [] q= file.listFiles() ;
			html= new File[10][20];
			corpus_= new BufferedReader[200];
			
			int j=-1;
			
			for( File i: q) {
				if(j>-1)
				{html[j]= i.listFiles();
				;
				}
				j++;
				
			}
			
			try { for( int i=0; i< 10;i++) {
				for( int k=0; k< 20; k++) {
				
					corpus_[ 20*i+k]= new BufferedReader(new FileReader(html[i][k]));
				}
			}
				
				
			}
			catch(IOException io ) {}
	
			
			
		//
			
			//corpus = new BufferedReader(new FileReader(comm[0]));
			corpus = new BufferedReader(new FileReader("/Users/andre_cruz/Desktop/search/weblinks/q1/a2.html"));
			stopList = new BufferedReader(new FileReader(comm[2]));
			queries = new BufferedReader(new FileReader(comm[3]));
			invertedIndex = new PrintWriter(new FileOutputStream(comm[1]));
			results = new PrintWriter(new FileOutputStream(comm[4]));
			stemming=Boolean.parseBoolean(comm[5]);

			}
			else {
				queries = new BufferedReader(new FileReader(comm[3]));
				results = new PrintWriter(new FileOutputStream(comm[4]));
				stemming=Boolean.parseBoolean(comm[5]);
				
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	
	public static String[] parse(String[] args) {

		int i = 0;
		String arg;
		String[] outputfile = new String[6];

		while (i < args.length && args[i].startsWith("-")) {
			arg = args[i++];

			if (arg.equals("-CorpusDir")) {
				if (i < args.length)
					outputfile[0] = args[i++];
				else
					System.err.println("-output requires a filename");

			}

			else if (arg.equals("-InvertedIndex")) {
				if (i < args.length)
					outputfile[1] = args[i++];
				else
					System.err.println("-output requires a filename");

			} else if (arg.equals("-StopList")) {
				if (i < args.length)
					outputfile[2] = args[i++];
				else
					System.err.println("-output requires a filename");

			} else if (arg.equals("-Queries")) {
				if (i < args.length)
					outputfile[3] = args[i++];
				else
					System.err.println("-output requires a filename");

			} else if (arg.equals("-Results")) {
				if (i < args.length)
					outputfile[4] = args[i++];
				else
					System.err.println("-output requires a filename");

			}
			 else if (arg.equals("-Stemming")) {
					if (i < args.length)
						outputfile[5] = args[i++];
					else
						System.err.println("-output requires a filename");

				}

			// use this type of check for a series of flag arguments

		}

		return outputfile;

	}

	public static void closeAll() {
		try {
			corpus.close();
			queries.close();
			invertedIndex.close();
			stopList.close();
			results.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}