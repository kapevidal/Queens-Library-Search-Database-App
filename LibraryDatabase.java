/*ID4: 0808
 *ISBN-> Semester-long project to create a hashtable database that stores search results from a given website.
 *Phase3: Create a Search Website Database with GUI and CLI.
 *Website: QueensLibrary.org
 * */

/*IMPORT SECTION*/
import java.util.Date;
import java.util.Enumeration;  // Enumeration will be utilized to generate a list of items in the Hashtable Database.
import java.util.Hashtable;    // Hashtable will be utilized as a database to hold inserted items.
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.sql.Timestamp;	   // Timestamp will be utilized as a placeholder for the current timestamp.
import java.text.SimpleDateFormat;
import java.io.BufferedReader;
import java.io.BufferedWriter; // BufferedWriter, along with FileWriter and Writer, will be utilized to write the output and transaction log files
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;     // Read BufferedReader
import java.io.Writer;         // Read BufferedReader
import java.io.IOException;    // IOException will be utilized to monitor input and output operations


/*MAIN LIBRARY DATABASE CLASS*/
public class LibraryDatabase
{
	private Hashtable<String, Item> libraryDatabase = new Hashtable<String, Item>(); // Hashtable Database
	private Writer transactionLog; //will produced log.txt
	private Writer outputLog; //will produced output.txt
	private BufferedReader checkIfEmptyTextFile; //check if textfile empty
	private String outputFN; //output fill name
	private String currentUser="test"; //current user

	int nullId=0; //id count for items with 'null' id
	// Constructor
	LibraryDatabase()
	{
		libraryDatabase = new Hashtable<String, Item>();		
		try {
			transactionLog = new BufferedWriter(new FileWriter("log.txt",true));
			File i= new File("log.txt");
			if(i.length()==0)
			transactionLog.append("TRANSACTION LOG");//write 'TRANSACTION LOG' at the top-most of the log.txt file
			else
			transactionLog.append("\nTRANSACTION LOG");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//create/check the following directories
        File dir = new File("DB");
        if (!dir.exists()) dir.mkdirs();
        
        File dir1 = new File("DB\\userDB");
        if (!dir.exists()) dir.mkdirs();
        
        File di2 = new File("IMG");
        if (!dir.exists()) dir.mkdirs();
        
        //check if login.txt exists, create file if it does not.
        File lg = new File("DB\\login.txt");
        if(!lg.exists())
			try {
				lg.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        
	}

	//set output file for multiple activities
	public void setOutputFile(String s, String usr) throws IOException
	{
		currentUser=usr;
		File file = new File(s);
 
		if(file.exists())
		{
		outputLog = new BufferedWriter(new FileWriter(file,true));
		checkIfEmptyTextFile = new BufferedReader(new FileReader(file)); 
		}
		else
		{
		outputLog = new BufferedWriter(new FileWriter(file));
		checkIfEmptyTextFile = new BufferedReader(new FileReader(file)); 
		}
	}
	
	//set output file for single activity
	public void setOutputFileOneTime(String outputfileName, String usr) throws IOException
	{
		currentUser=usr;
		outputFN=outputfileName;
		File file = new File(outputfileName);
		outputLog = new BufferedWriter(new FileWriter(file));
		checkIfEmptyTextFile = new BufferedReader(new FileReader(file)); 
	}
	
	//load user database
	public void loadDB(String x, String usr) throws IOException
	{	
		currentUser=usr;
        File dir = new File("DB");
        if (!dir.exists()) dir.mkdirs();    
        
        /*File dir2 = new File("DB\\userDB");
        if (!dir2.exists()) dir2.mkdirs();*/
        
		File file = new File("DB\\"+x);
		if(!file.exists())
		{
		outputLog = new BufferedWriter(new FileWriter(file,true));
		checkIfEmptyTextFile = new BufferedReader(new FileReader(file));
		}
		else
		{
			{
				outputLog = new BufferedWriter(new FileWriter(file,false));
				checkIfEmptyTextFile = new BufferedReader(new FileReader(file));
				}
				
		}
		
		
		transactionLog = new BufferedWriter(new FileWriter("DB\\logDB.txt",true));
		File i= new File("DB\\logDB.txt");
		if(i.length()==0)
		transactionLog.append("TRANSACTION LOG");//write 'TRANSACTION LOG' at the top-most of the log.txt file
		else
		transactionLog.append("\nTRANSACTION LOG");
	}
	
	//load items in the user database
	public void loadItem(String time, String User, String bibID, String Medium, String Title, String Creator, String Publisher, String ISBN) throws IOException
	{
		Timestamp currentTime = convertStringToTimestamp(time); //holds the current timestamp value.
	
		Item item = new Item(currentTime, User, bibID, Medium,Title,Creator, Publisher, ISBN); //create new Item class
	
		transactionLog.append("\nTimestamp-> "+  currentTime + " | User-> "+User+" | Keyword-> LOAD" + " | bibID-> " 
		+ bibID + " | Medium-> " + Medium + " | Title-> " + Title + " | Author-> " + Creator + " | Published-> " 
				+ Publisher + " | ISBN-> " + ISBN); //add a INSERT transaction procedure in the log.txt
		
		libraryDatabase.put(bibID, item); // insert item in the hash table database using put() method
	}
	
	//converts Timestampe into String value
	public static Timestamp convertStringToTimestamp(String strDate) {
		Timestamp timestamp=null;
		try {
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		    Date parsedDate = dateFormat.parse(strDate);
		    timestamp = new java.sql.Timestamp(parsedDate.getTime());
		} catch(Exception e) {
		    // look the origin of excption 
		}
		return timestamp;
	  }
	
	
    //insert item to the database
	public void insertItem(String User, String bibID,String Medium, String Title, String Creator, String Publisher, String ISBN) throws IOException
	{
		Timestamp currentTime = new Timestamp(System.currentTimeMillis()); //holds the current timestamp value.
		
		//if item is a null, reject insertion
		if(bibID.equalsIgnoreCase("null")&&Medium.equalsIgnoreCase("null")&&Title.equalsIgnoreCase("null")
				&&Creator.equalsIgnoreCase("null")&&Publisher.equalsIgnoreCase("null")&&ISBN.equalsIgnoreCase("null"))
			return;
		
		//if item ISBN=null, produce an ISBN and use it for the item.
		if(ISBN.equalsIgnoreCase("null"))
		{
			ISBN="NU"+String.valueOf((int)(Math.random() * 1000 + 1));
		}
		
		//if item ID=null, produce an ID and use it for the item.
		if(bibID.equalsIgnoreCase("null"))
		{
			bibID=String.valueOf(nullId++)+"UA"+ISBN;
		//	System.out.println(bibID);
		}
		
		//create the item
		Item item = new Item(currentTime, User, bibID, Medium,Title,Creator, Publisher, ISBN); //create new Item class
	
		//update transaction log
		transactionLog.append("\nTimestamp-> "+  currentTime + " | User-> "+currentUser+" | Keyword-> INSERT" + " | bibID-> " 
		+ bibID + " | Medium-> " + Medium + " | Title-> " + Title + " | Author-> " + Creator + " | Published-> " 
				+ Publisher + " | ISBN-> " + ISBN); //add a INSERT transaction procedure in the log.txt
		
		//insert item in database hashmap
		libraryDatabase.put(bibID, item); // insert item in the hash table database using put() method
	}
	
	
	//print database search results to output file, then close output file stream.
	public void printDatabase() throws IOException
	{
		String temp; // placeholder for the Key value of an item
		Enumeration items = libraryDatabase.keys(); // Holds the list of items in the hashtable
		
		Timestamp currentTimePrint = new Timestamp(System.currentTimeMillis());
		
		if (checkIfEmptyTextFile.readLine() == null) {
			outputLog.append("OUTPUT LOG");
		}
		 //write 'TRANSACTION LOG' at the top-most of the output.txt file
 
		if(libraryDatabase.isEmpty()) // if no items found in the hashtable database
		{
			System.out.println("Database Empty."); // database is empty
			
		}
			
        while(items.hasMoreElements()) // if hashtable database not empty, print all items
        {
	        temp = (String) items.nextElement(); // holds the key of the current item, the key is a String datatype
	        Item holdtemp = libraryDatabase.get(temp); // get values of the current item using the get(key) method
	        
	        /*Print format: timeCreated | bibID | Medium | Title | Creator | Published | ISBN */
	        System.out.println("Timestamp-> "+  holdtemp.timeCreated + " | User-> "+holdtemp.User+" | bibID-> " +holdtemp.bibID+ " | Medium-> " + holdtemp.Medium + " | Title-> " 
	        + holdtemp.Title + " | Author-> " + holdtemp.Creator + " | Published-> " + holdtemp.Publisher + " | ISBN-> " + holdtemp.ISBN);
	        
	        outputLog.append("\nTimestamp-> "+  holdtemp.timeCreated  +" |User-> "+holdtemp.User+ " | bibID-> " + holdtemp.bibID+ " | Medium-> " 
	        + holdtemp.Medium + " | Title-> " + holdtemp.Title + " | Author-> " + holdtemp.Creator + " | Published-> " 
	        		+ holdtemp.Publisher + " | ISBN-> " + holdtemp.ISBN); //add the item in the output.txt
			
        }
		outputLog.close();
		//update transaction log
		transactionLog.append("\nTimestamp-> "+  currentTimePrint + " User-> "+currentUser+" | Keyword-> PRINT" 
		+ " | Print all Items in the Database into "+outputFN ); //add a PRINT transaction procedure in the log.txt
        System.out.println();	
	}
	
	// check/verify user database for stored items.
	public void verifyDatabase() throws IOException
	{
		String temp; // placeholder for the Key value of an item
		Enumeration items = libraryDatabase.keys(); // Holds the list of items in the hashtable
		
		Timestamp currentTimePrint = new Timestamp(System.currentTimeMillis());
	
		 //write 'TRANSACTION LOG' at the top-most of the output.txt file
 
		if(libraryDatabase.isEmpty()) // if no items found in the hashtable database
		{
			System.out.println("Database Empty."); // database is empty
			
		}
			
        while(items.hasMoreElements()) // if hashtable database not empty, print all items
        {
	        temp = (String) items.nextElement(); // holds the key of the current item, the key is a String datatype
	        Item holdtemp = libraryDatabase.get(temp); // get values of the current item using the get(key) method
	        
        }
        
        //update transaction log
		transactionLog.append("\nTimestamp-> "+  currentTimePrint + " | Keyword-> VERIFY" 
		+ " | Verify Items in the Database"); //add a PRINT transaction procedure in the log.txt
        System.out.println();	
	}
	
	//insert temporary database stored items to USER/MAiN database
	public void insertToMain(LibraryDatabase transfer,String fileN) throws IOException
	{
		
		Timestamp currentTimePrint = new Timestamp(System.currentTimeMillis());
		String temp;
		Enumeration items = transfer.libraryDatabase.keys();
		while(items.hasMoreElements())
		{
		       temp = (String) items.nextElement();
		       if(checkID((temp)))
		       {
		    	   return;
		       }
		       else
		       {
			        Item holdtemp = transfer.libraryDatabase.get(temp);
			        libraryDatabase.put(temp, holdtemp);
		       }
		        	
		}
		//truncate database file
		File file = new File("DB\\"+fileN);
		if(file.exists()){
		    file.delete();
		}
		file.createNewFile();
		outputLog = new BufferedWriter(new FileWriter(file,false));	
		
		transactionLog.append("\nTimestamp-> "+  currentTimePrint + " | Keyword-> UPDATE DB" 
				+ " | UPDATE DB with new Search Results");
	}
	
	
	
	
	//print USER database stored items to USER database file.
	public void saveDB() throws IOException
	{
		String temp; // placeholder for the Key value of an item
		Enumeration items = libraryDatabase.keys(); // Holds the list of items in the hashtable
		
		Timestamp currentTimePrint = new Timestamp(System.currentTimeMillis());
			
	    //insert first item in current line
		if(items.hasMoreElements())
		{
			temp = (String) items.nextElement(); // holds the key of the current item, the key is a String datatype
	        Item holdtemp = libraryDatabase.get(temp); // get values of the current item using the get(key) method	
	        outputLog.append("Timestamp-> "+  holdtemp.timeCreated  + " | User-> "+holdtemp.User+ " | bibID-> " + holdtemp.bibID+ " | Medium-> " 
	    	        + holdtemp.Medium + " | Title-> " + holdtemp.Title + " | Author-> " + holdtemp.Creator + " | Published-> " 
	    	        		+ holdtemp.Publisher + " | ISBN-> " + holdtemp.ISBN); //add the item in the output.txt
		}
		
		//insert item in a new line.
        while(items.hasMoreElements()) // if hashtable database not empty, print all items
        {
	        temp = (String) items.nextElement(); // holds the key of the current item, the key is a String datatype
	        Item holdtemp = libraryDatabase.get(temp); // get values of the current item using the get(key) method
	        
	        outputLog.append("\nTimestamp-> "+  holdtemp.timeCreated  +" | User-> "+holdtemp.User+ " | bibID-> " + holdtemp.bibID+ " | Medium-> " 
	        + holdtemp.Medium + " | Title-> " + holdtemp.Title + " | Author-> " + holdtemp.Creator + " | Published-> " 
	        		+ holdtemp.Publisher + " | ISBN-> " + holdtemp.ISBN); //add the item in the output.txt
			
        }
		outputLog.close();
		
		//update transaction log.
		transactionLog.append("\nTimestamp-> "+  currentTimePrint +" | User-> "+currentUser+ " | Keyword-> UPDATE" 
		+ " | Updated MainDB."); //add a PRINT transaction procedure in the log.txt
        System.out.println();	
	}
	
	
	//modify existing database item
	public void modifyItem(String User, String bibID, String newID, String Medium, String Title, String Creator,String Publisher, String ISBN) throws IOException
	{
		Timestamp currentTime = new Timestamp(System.currentTimeMillis()); 
		//check if item exists in the database
		if(checkID(bibID))
		{	
			System.out.println(bibID +" item will be modified.");
			
			if(bibID.equals(newID))//if user wants to reuse the id
			{
				Item updatedItem = new Item(currentTime, User, bibID, Medium,Title,Creator, Publisher, ISBN);
				transactionLog.append("\nTimestamp-> "+  currentTime + " | User-> "+User+" | Keyword-> MODIFY" + " | bibID-> " + bibID+ " | NEW bidID: "
				+ newID +" | Medium-> " + Medium + " | Title-> " + Title + " | Author-> " + Creator + " | Published-> " + Publisher 
				+ " | ISBN-> " + ISBN); //add a MODIFY transaction procedure in the log.txt
				libraryDatabase.replace(bibID,updatedItem);	// update item in the hashtable database using replace() method
			}
			else//if user does NOT want to reuse the id, remove the replaced item and insert the new item
			{
				transactionLog.append("\nTimestamp-> "+  currentTime +" | User-> "+User+ " | Keyword-> MODIFY" + " | bibID-> " + 
						bibID+ " | NEW bidID: "+ newID +" | Medium-> " + Medium + " | Title-> " + Title + " | Author-> " + 
						Creator + " | Published-> " + Publisher + " | ISBN-> " + ISBN); //add a MODIFY transaction procedure in the log.txt			
				removeItem(bibID);
				
				if(ISBN.equalsIgnoreCase("null")||ISBN.equalsIgnoreCase(""))
				{
					ISBN="NU"+String.valueOf((int)(Math.random() * 1000 + 1));
				}
				
				if(newID.equalsIgnoreCase("")||newID==null)
					{newID=String.valueOf(nullId++)+"UA"+ISBN;}
				
				//insert modified item
				//user value will be updated to the user who performed modification
				insertItem(User, newID, Medium, Title, Creator, Publisher, ISBN);				
			}
			
		}
		else
		{
			System.out.println("Item not Found.");
			transactionLog.append("\nTimestamp-> "+  currentTime + " | User-> "+User+" | Keyword-> MODIFY" + " | Item Not Found.."); //add a MODIFY transaction procedure in the log.txt
			
		}
	}
	
	//remove an existing item in the database
	public void removeItem(String bibID) throws IOException
	{
		Timestamp currentTime = new Timestamp(System.currentTimeMillis()); 
		if(checkID(bibID))
		{	
			System.out.println(bibID +" item will be removed.");
			Item holdtemp = libraryDatabase.get(bibID);
			transactionLog.append("\nTimestamp-> "+  currentTime + " | User-> "+holdtemp.User+" | Keyword-> REMOVE" + " | bibID-> " + bibID+ " | Medium-> " 
					+ holdtemp.Medium + " | Title-> " + holdtemp.Title + " | Author-> " + holdtemp.Creator + " | Publisher: " 
					+ holdtemp.Publisher + " | ISBN-> " + holdtemp.ISBN); //add a REMOVE transaction procedure in the log.txt
			
			libraryDatabase.remove(bibID); // remove item in the hashtable database using remove(key) method		
		}
		else
		{
			System.out.println("Item not Found.");
			transactionLog.append("\nTimestamp-> "+  currentTime + " | User-> "+currentUser+" | Keyword-> REMOVE" + " | Item Not Found.."); //add a REMOVE transaction procedure in the log.txt
			
			
		}
	}
	
	//search for existing item in the database
	public void searchItem(String term) throws IOException
	{
		Timestamp currentTime2 = new Timestamp(System.currentTimeMillis()); 
		int searchCount=0;
		System.out.println("Search results: ");
		
			String temp; // placeholder for the Key value of an item
			Enumeration items = libraryDatabase.keys(); // Holds the list of items in the hashtable
			 while(items.hasMoreElements()) // if hashtable database not empty, print all items
		        {
			        temp = (String) items.nextElement(); // holds the key of the current item, the key is a String datatype
			        Item holdtemp = libraryDatabase.get(temp);
			        	
			        if(holdtemp.Medium.contains(term) || holdtemp.Title.contains(term) || holdtemp.Creator.contains(term) || holdtemp.Publisher.contains(term) || holdtemp.ISBN.contains(term))
			        {
				        System.out.println("timeCreated: "+  holdtemp.timeCreated + " | User-> "+holdtemp.User+ " | bibID-> " +holdtemp.bibID+ " | Medium-> " + holdtemp.Medium + " | Title-> " 
				    	        + holdtemp.Title + " | Authors: " + holdtemp.Creator + " | Published-> " + holdtemp.Publisher + " | ISBN-> " + holdtemp.ISBN);
				    	        
				    	        transactionLog.append("\nTimestamp-> "+  currentTime2 + " | User-> " + currentUser+" | Keyword-> SEARCH" + " | bibID-> " + term+ " | Medium-> " 
				    	        		+ holdtemp.Medium + " | Title-> " + holdtemp.Title + " | Author-> " + holdtemp.Creator + " | Published-> " 
				    	        		+ holdtemp.Publisher + " | ISBN-> " + holdtemp.ISBN); //add a SEARCH transaction procedure in the log.txt	
				    	        System.out.println();
				    	        searchCount++;
			        }			        
		        }
			 
			 if(searchCount==0)
			 {
					System.out.println("No matching item/s found.");
					transactionLog.append("\nTimestamp-> "+  currentTime2 + " | User-> "+currentUser+" | Keyword-> SEARCH" + " | No item/s Found.."); //add a SEARCH transaction procedure in the log.txt
			 }
			 else
				  System.out.println(searchCount +" item/s found.");
			
		
	}
	
	//search for existing item in the database, given a time range.
	public void searchItemWithTime(String term) throws IOException
	{
		Timestamp currentTime2 = new Timestamp(System.currentTimeMillis()); 
		int searchCount=0;
		System.out.println("Search results: ");


			String temp; // placeholder for the Key value of an item
			Enumeration items = libraryDatabase.keys(); // Holds the list of items in the hashtable
			 while(items.hasMoreElements()) // if hashtable database not empty, print all items
		        {
			        temp = (String) items.nextElement(); // holds the key of the current item, the key is a String datatype
			        Item holdtemp = libraryDatabase.get(temp);
			        	
			        if(holdtemp.Medium.contains(term) || holdtemp.Title.contains(term) || holdtemp.Creator.contains(term) || holdtemp.Publisher.contains(term) || holdtemp.ISBN.contains(term))
			        {
				        System.out.println("timeCreated: "+  holdtemp.timeCreated + " | User: " + holdtemp.User+" | bibID-> " +holdtemp.bibID+ " | Medium-> " + holdtemp.Medium + " | Title-> " 
				    	        + holdtemp.Title + " | Authors: " + holdtemp.Creator + " | Published-> " + holdtemp.Publisher + " | ISBN-> " + holdtemp.ISBN);
				    	        
				    	        transactionLog.append("\nTimestamp-> "+  currentTime2 + " | User: " + currentUser+" | Keyword-> SEARCH" + " | bibID-> " + term+ " | Medium-> " 
				    	        		+ holdtemp.Medium + " | Title-> " + holdtemp.Title + " | Author-> " + holdtemp.Creator + " | Published-> " 
				    	        		+ holdtemp.Publisher + " | ISBN-> " + holdtemp.ISBN); //add a SEARCH transaction procedure in the log.txt	
				    	        System.out.println();
				    	        searchCount++;
			        }			        
		        }
			 
			 if(searchCount==0)
			 {
					System.out.println("No matching item/s found.");
					transactionLog.append("\nTimestamp-> "+  currentTime2 + " | User: " + currentUser+" | Keyword-> SEARCH" + " | No item/s Found.."); //add a SEARCH transaction procedure in the log.txt
			 }
			 else
				  System.out.println(searchCount +" item/s found.");
			
		
	}
	
	
	
	//check database for a given ID
	public boolean checkID(String ID)
	{
		if(libraryDatabase.containsKey(ID))
		{
			return true;
		}
		else return false;
	}
	
	//return database Hashmap (generally use for GUI item table display)
	public Hashtable<String, Item> duplicateDB()
	{
		return libraryDatabase;
	}
	
	//update transaction log with invalid keyword (use in CLI)
	public void invalidKeyword(String keyword) throws IOException
	{
		Timestamp currentTime2 = new Timestamp(System.currentTimeMillis()); 
		System.out.println("Invalid Keyword.. Please enter a valid command\n");	
		transactionLog.append("\nTimestamp-> "+  currentTime2 + " | User-> " +currentUser+" | Keyword-> "+ keyword.toUpperCase() + " | Invalid Keyword...."); //add a INVALID transaction procedure in the log.txt
	}
	
	//procedure to close IO operations of transaction log
	public void endProgram() throws IOException
	{
		transactionLog.close(); 
	}

	//load all items from the USER database file to the USER database
	public void reload(Queue<String> as, LibraryDatabase mainDB) throws IOException {
		while(!as.isEmpty())
		{
			String currentLine =(as.remove());
			//System.out.println(ds);
			String[] itemVal = currentLine.split("-> ",9);
			mainDB.loadItem(
			itemVal[1].substring(0,itemVal[1].indexOf(" |")), // time
			itemVal[2].substring(0,itemVal[2].indexOf(" |")), // bibId
			itemVal[3].substring(0,itemVal[3].indexOf(" |")), // medium
			itemVal[4].substring(0,itemVal[4].indexOf(" |")), // title
			itemVal[5].substring(0,itemVal[5].indexOf(" |")), // Author
			itemVal[6].substring(0,itemVal[6].indexOf(" |")), // Publisher
			itemVal[7].substring(0,itemVal[7].indexOf(" |")), // Publisher
			itemVal[8]); // Publisher
			
		}
		
	}
	
	public void loadDB(Queue<String> as, LibraryDatabase mainDB) throws IOException {
		while(!as.isEmpty())
		{
			String currentLine =(as.remove());
			//System.out.println(ds);
			String[] itemVal = currentLine.split("-> ",9);
			
			if(itemVal[2].substring(0,itemVal[2].indexOf(" |")).equalsIgnoreCase(currentUser))
			{mainDB.loadItem(
			itemVal[1].substring(0,itemVal[1].indexOf(" |")), // time
			itemVal[2].substring(0,itemVal[2].indexOf(" |")), // user
			itemVal[3].substring(0,itemVal[3].indexOf(" |")), // bibid
			itemVal[4].substring(0,itemVal[4].indexOf(" |")), // medium
			itemVal[5].substring(0,itemVal[5].indexOf(" |")), // title
			itemVal[6].substring(0,itemVal[6].indexOf(" |")), // author
			itemVal[7].substring(0,itemVal[7].indexOf(" |")), // Publisher
			itemVal[8]); // ISBN
			}
			
		}
		
	}
	
	
	
    
	//insert retrieved search information from the URLsearch to the search database.
	public void insertAll(String[] searchResult, Queue<String> words, LibraryDatabase searchDB) {
		int i =0; 
		while(!words.isEmpty())
		 {
			 //System.out.println(words.remove());
			 String media=(words.remove());
			String title=words.remove();
			String author=words.remove();
			 String publisher=words.remove();
			 String isbn=words.remove();

			 
			 System.out.println();
			 try {
				 searchDB.insertItem(currentUser,searchResult[i++], media, title, author, publisher, isbn);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		 }
	}

	//conducts offline item search from the USER/MAIN DB, then add the resulting items into the tempDB/offlineSearchDB
	public void offlineSearch(String searchTerm, String time, LibraryDatabase tempDB, LibraryDatabase mainDB) throws IOException
	{
		Timestamp currentTime2 = new Timestamp(System.currentTimeMillis()); 
		int searchCount=0;
		System.out.println("Search results: ");
		{
			String temp; // placeholder for the Key value of an item
			Enumeration items = mainDB.libraryDatabase.keys(); // Holds the list of items in the hashtable
			 while(items.hasMoreElements()) // if hashtable database not empty, print all items
		        {
			        temp = (String) items.nextElement(); // holds the key of the current item, the key is a String datatype
			        Item holdtemp = mainDB.libraryDatabase.get(temp);
			        	
			        if(holdtemp.User.toLowerCase().contains(searchTerm) ||holdtemp.Medium.toLowerCase().contains(searchTerm) || holdtemp.Title.toLowerCase().contains(searchTerm) || holdtemp.Creator.toLowerCase().contains(searchTerm) || holdtemp.Publisher.toLowerCase().contains(searchTerm) || holdtemp.ISBN.toLowerCase().contains(searchTerm))
			        {
			        	System.out.println(holdtemp.timeCreated.toString());
			        	if(holdtemp.timeCreated.toString().contains(time))
			        	{
			        	libraryDatabase.put(temp, holdtemp);
				    	        transactionLog.append("\nTimestamp-> "+  currentTime2 + " | User-> " + currentUser+" | Keyword-> SEARCH" + " | bibID-> " + searchTerm+ " | Medium-> " 
				    	        		+ holdtemp.Medium + " | Title-> " + holdtemp.Title + " | Author-> " + holdtemp.Creator + " | Published-> " 
				    	        		+ holdtemp.Publisher + " | ISBN-> " + holdtemp.ISBN); //add a SEARCH transaction procedure in the log.txt	
				    	        System.out.println();
				    	        searchCount++;
			        	}
			        }			        
		        }
			 
			 if(searchCount==0)
			 {
					System.out.println("No matching item/s found.");
					transactionLog.append("\nTimestamp-> "+  currentTime2 + " | User-> "+currentUser+" | Keyword-> SEARCH" + " | No item/s Found.."); //add a SEARCH transaction procedure in the log.txt
			 }
			 else
			 {  System.out.println(searchCount +" item/s found.");}
			 mainDB.transactionLog.append("\nTimestamp-> "+  currentTime2 + " | User-> " +currentUser+" | Keyword-> Offline SEARCH" + " | Database searching for the term:   "+ searchTerm);
			 
			
		}
		
	}
	
	//performs user account login and returns boolean value for the GUI 'enabler'
	public boolean loginAcc(String user, String pass) throws IOException
	{
        Boolean verification=false;
		Queue<UserAccount> dada = new LinkedList<UserAccount>();
		Timestamp currentTime2 = new Timestamp(System.currentTimeMillis()); 
		BufferedWriter outputLog=null;
		BufferedReader checkIfEmptyTextFile=null;
        File dir = new File("DB");
        if (!dir.exists()) dir.mkdirs();
        
        Scanner scanner = new Scanner(new FileInputStream("DB\\login.txt"));
        scanner.useDelimiter("\\||\\n");

        
        UserAccount smap=null;
        while(scanner.hasNext())
        {
        	String usern=scanner.next();
        	String pw = scanner.next();
        	smap= new UserAccount(usern,pw);
        	dada.add(smap);
        }

        while(!dada.isEmpty())
        {
        	UserAccount hold = dada.remove();
        	 if(hold.verify(user,pass))
        		 verification=true;
        	
        	
        }
        
        if(user.equalsIgnoreCase("admin")&&pass.equalsIgnoreCase("admin"))
        	verification=true;
        if(verification)
        transactionLog.append("\nTimestamp-> "+  currentTime2 + " | User-> "+currentUser+" | Keyword-> LOGIN" + " | Login Attempt succesful for user: "+ user );
        else
        	transactionLog.append("\nTimestamp-> "+  currentTime2 + " | User-> " +currentUser+" | Keyword-> LOGIN" + " | Login Attempt unsuccessful");
        return verification;
        
       /* Writer registerAccnt = new BufferedWriter(new FileWriter("DB\\login.txt",true));
        registerAccnt.append("\nkirsten|test");
        registerAccnt.close();*/
	}
	
	//creates a new valid User account and returns boolean value to notify GUI user if creation is successful or not..
	public boolean createAcc(String user, String pass) throws IOException
	{
        Boolean verification=true;
		Queue<UserAccount> dada = new LinkedList<UserAccount>();
		Timestamp currentTime2 = new Timestamp(System.currentTimeMillis()); 
		BufferedWriter outputLog=null;
		BufferedReader checkIfEmptyTextFile=null;
        File dir = new File("DB");
        if (!dir.exists()) dir.mkdirs();
        
        File lg = new File("DB\\login.txt");
        if(!lg.exists()) lg.createNewFile();
        
        Scanner scanner = new Scanner(new FileInputStream("DB\\login.txt"));
        scanner.useDelimiter("\\||\\n");

        if(user.equalsIgnoreCase("admin")&&pass.equalsIgnoreCase("admin"))
        	{
        	transactionLog.append("\nTimestamp-> "+  currentTime2 + " | User-> "+currentUser+" | Keyword-> Create Account" + " | Register Attempt unsuccessful: Invalid username");
        	return false;
        	}
        	
        UserAccount smap=null;
        while(scanner.hasNext())
        {
        	String usern=scanner.next();
        	String pw = scanner.next();
        	smap= new UserAccount(usern,pw);
        	dada.add(smap);
        }

        while(!dada.isEmpty())
        {
        	UserAccount hold = dada.remove();
        	 if(hold.getUsername().equalsIgnoreCase(user))
        		 verification=false;
        	
        }

       /* Writer registerAccnt = new BufferedWriter(new FileWriter("DB\\login.txt",true));
        registerAccnt.append("\nkirsten|test");
        registerAccnt.close();*/
        
        if(verification)
        {
            Writer registerAccnt = new BufferedWriter(new FileWriter("DB\\login.txt",true));
            if(lg.length()==0)
            registerAccnt.append(user+"|"+pass);
            else
            registerAccnt.append("\n"+user+"|"+pass);
            registerAccnt.close();
        transactionLog.append("\nTimestamp-> "+  currentTime2 + " | User-> "+currentUser+" | Keyword-> Create Account" + " | Register Attempt succesful for user: "+ user );
        }
        else
        	transactionLog.append("\nTimestamp-> "+  currentTime2 + " | User-> "+currentUser+" | Keyword-> Create Account" + " | Register Attempt unsuccessful");
        
        return verification;
	}

}
