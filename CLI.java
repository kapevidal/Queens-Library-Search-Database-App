/* ID4:0404
 * 
 * How it Works Phase3:
 * 1) Load input file
 * 2) insert all key terms found in input file to a Queue
 * 3) Check the command-line args for procedure.
 * 4) If a '-p' keyword is read. Perform online search and print results to to output file
 * 5) After all command-line args are read, print log.txt (for admin use)
 * 
 * */

import java.io.IOException;  // IOException is needed for writing the transaction/activity log
import java.util.LinkedList; // Linkedlist will be used to implement a QUEUE list that will hold items in the input.txt
import java.util.Queue; 	 // Queue list will be used to hold key terms/words to be searched.

/*Command Line interface CLASS SECTION*/
public class CLI {

	public static void main(String[] args) throws IOException
	{
       LibraryDatabase db = new LibraryDatabase();
       Queue<String> searchTerms = new LinkedList<String>(); // hold key terms read in input file.

        /*String datatypes to hold input and output file name*/
	    String inputName = "";
	    String outputName = "";
	    
	    //Email datatype if email reporting is requested
	    Email sendOutput=null;
       
	   System.out.println();
	   String currentToken = ""; //holds the current command from the user input.

	   for( int l=0; l<args.length; l++)
	   {   
		   currentToken = args[l]; //get current word/token given by the user in the command-line
		   switch(currentToken)
		   {
			   case "-i": // get key terms in input file
			   {
				   System.out.println("Input File");
				    l++;
					inputName=args[l];
					System.out.println(inputName);
					searchTerms = URLSearch.readFile(new LinkedList<String>(), inputName); //read file for keywords
					break;

			   }
			   case "-o": // select output file	where search results will be saved
			   {
				   System.out.println("Output File");
				    l++;
					outputName=args[l];
					System.out.println(outputName);
					db.setOutputFileOneTime(outputName,"Guest"); //set output file.
					break;

			   }
			   case "-p": // perform online search
			   {
				   while(!searchTerms.isEmpty())
				   {
					   String[] results = new String[11];
					   results=URLSearch.searchResults(searchTerms.remove(),"");
					   Queue<String> words = URLSearch.fillIn(results,new LinkedList<>());
						db.insertAll(results,words, db);
			
				   }
				   
				   db.printDatabase(); // insert search result to out file
				   
				   l++;
				   String email=args[l];
				   
				   if(email!=null)
				   {
					   System.out.println("Sending output file to "+ email);
					
					   //if an email address is provided, perfom email reporting.
					   sendOutput = new Email(email,outputName,"Hey " + "Guest! Here is the requested copy of your output log.","Here is your requested copy of your output.");
					   
				   }
				   break;
				   
			   }

			   default: // invalid command flag
			   {
				   System.out.println(currentToken.toUpperCase());
				   db.invalidKeyword(currentToken);
				   break;
				   
			   }
		   }
	   }
	   db.endProgram(); // end input output stream.
       }
       
}
