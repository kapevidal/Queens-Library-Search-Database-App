import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import javax.imageio.ImageIO;

/*URL CLASS SECTION*/
public class URLSearch {

	URLSearch(){}
	
	//test main class where initial URL search were conducted
	public static void main(String[] args) throws IOException
	{

		LibraryDatabase mainDB = new LibraryDatabase();
		Queue<String> as = readFile(new LinkedList<String>(), "DB\\DB.txt");
		mainDB.loadDB("DB.txt","Guest");
		mainDB.reload(as, mainDB);

		mainDB.verifyDatabase();
		
		
		System.out.println("Enter itemid: ");
		Scanner url = new Scanner(System.in);
		String searchTerm = url.nextLine();
		//mainDB.searchItem(searchTerm);
	   //mainDB.modifyItem(searchTerm, "999999", "CD" , "BLONDE", "FRANK POND", "CASA BLANCA", "347361");
		
		LibraryDatabase tempDB = new LibraryDatabase();
		tempDB.setOutputFileOneTime("Output.txt","Guest");
		 String[] searchResult=new String[11];

		searchResult=searchResults(searchTerm,"music");
		 Queue<String> words = fillIn(searchResult,new LinkedList<>());

		//tempDB.offlineSearch(searchTerm,tempDB,mainDB);
		 tempDB.insertAll(searchResult, words, tempDB);
		
		tempDB.printDatabase();
		mainDB.insertToMain(tempDB,"DB.txt");
		
		mainDB.saveDB();
		
		tempDB.endProgram();
		
		mainDB.endProgram();
		
		//saveImage("http://syndetics.com/index.php?isbn=9780755117130/lc.jpg");

	}
	
	//retrieve all necessary information as String files and store it in a Queue<string>
	public static Queue<String> fillIn(String[] searchResults, Queue<String> df) throws IOException
	{
		int i = 0;
		while(i<searchResults.length)
		{
			if(!(searchResults[i]==null))
			{
			df.add(getMedium(searchResults[i],"nullD"));
			df.add(getTitle(searchResults[i],"nullD"));
			df.add(getAuthor(searchResults[i],"nullD"));
			df.add(getPublisher(searchResults[i],"nullD"));
			df.add(getISBN(searchResults[i],"nullD"));	
			}
			i++;
		}		
		return df;
	}
	
	//retrieve item Medium value
	public static String getMedium(String url, String Medium) throws IOException
	{
		
		URL textUrl= null;
		try {
			textUrl= new URL("https://www.queenslibrary.org/bib/"+url);
		} catch(MalformedURLException e)
		{
			System.out.println("Invalid link..");
		}
		
		InputStream content = (InputStream) getURLInputStream(textUrl);
		BufferedReader reader = new BufferedReader (new InputStreamReader(content));

		String line = null;
		line = reader.readLine();

		while(line!=null)
		{
			if(line.contains("<span class=\"category item-media-type  \"><span>"))
			{

				//System.out.println("haha");
				Medium=(line.substring(line.indexOf("<span class=\\\"category item-media-type  \\\"><span>")+78, line.indexOf("</span>")));
			}
			line= reader.readLine();
		}
		if(Medium.contentEquals("nullD"))
		{
			Medium="No Medium";
		}
		
		return Medium;
	}
	
	//retrieve item Title value
	public static String getTitle(String url, String Title) throws IOException
	{
		
		URL textUrl= null;
		try {
			textUrl= new URL("https://www.queenslibrary.org/bib/"+url);
		} catch(MalformedURLException e)
		{
			System.out.println("Invalid link..");
		}
		
		InputStream content = (InputStream) getURLInputStream(textUrl);
		BufferedReader reader = new BufferedReader (new InputStreamReader(content));

		String line = null;
		line = reader.readLine();

		while(line!=null)
		{
			if(line.contains("<p class=\"item-bib-title title\"><span>"))
			{

				Title=(line.substring(line.indexOf("<span>")+6, line.indexOf("</span></p>")));
			}
			line= reader.readLine();
		}
		if(Title.contentEquals("nullD"))
		{
			Title="No Title";
		}
		
		return Title;
	}
	
	//retrieve item Author value
	public static String getAuthor(String url, String Author) throws IOException
	{
		
		URL textUrl= null;
		try {
			textUrl= new URL("https://www.queenslibrary.org/bib/"+url);
		} catch(MalformedURLException e)
		{
			System.out.println("Invalid link..");
		}
		
		InputStream content = (InputStream) getURLInputStream(textUrl);
		BufferedReader reader = new BufferedReader (new InputStreamReader(content));

		String line = null;
		line = reader.readLine();

		while(line!=null)
		{
			if(line.contains("<p class=\"item-author author\">By"))
			{	
				
				line=reader.readLine();
				if(line.contains("<span><a href=\"/search/everything?searchField="))
				Author=(line.substring(line.indexOf("<span><a href=\\\"/search/everything?searchField=")+107,line.indexOf("&category")));
			}
			line= reader.readLine();
		}
		if(Author.contentEquals("nullD"))
		{
			Author="No Author";
		}
		
		return Author;
	}
	
	//retrieve item Publisher value
	public static String getPublisher(String url, String Publisher) throws IOException
	{
		
		URL textUrl= null;
		try {
			textUrl= new URL("https://www.queenslibrary.org/bib/"+url);
		} catch(MalformedURLException e)
		{
			System.out.println("Invalid link..");
		}
		
		InputStream content = (InputStream) getURLInputStream(textUrl);
		BufferedReader reader = new BufferedReader (new InputStreamReader(content));

		String line = null;
		line = reader.readLine();

		while(line!=null)
		{
			if(line.contains("<p class=\"item-published\"><span class=\"bold\">Published</span> <span class=\"nb\">"))
			{
				
				String s1=(line.substring(line.indexOf("<p class=\\\"item-published\\\"><span class=\\\"bold\\\">Published</span> <span class=\\\"nb\\\">")+120, line.indexOf("</span></p>")));
				String[] separated = s1.split("=\"nb\">");
				Publisher=(separated[1]);
			}
			line= reader.readLine();
		}
		if(Publisher.contentEquals("nullD"))
		{
			Publisher="No Publisher";
		}
		
		return Publisher;
	}
		
	//retrieve item ISBN value
	public static String getISBN(String url, String ISBN) throws IOException
	{
		
		URL textUrl= null;
		try {
			textUrl= new URL("https://www.queenslibrary.org/bib/"+url);
		} catch(MalformedURLException e)
		{
			System.out.println("Invalid link..");
		}
		
		InputStream content = (InputStream) getURLInputStream(textUrl);
		BufferedReader reader = new BufferedReader (new InputStreamReader(content));

		String line = null;
		line = reader.readLine();

		while(line!=null)
		{
			if(line.contains("ISBN"))
			{
				line=reader.readLine();
				if(line.contains("</div>"))
				{
					line=reader.readLine();
					if(line.contains("<div class=\"col-xs-12  col-md-5\">"))
					{
						line=reader.readLine();
						String hold=(line.substring(line.indexOf("<span>")+7,line.indexOf("</span>")));
						if(isNumeric(hold))
						{
							ISBN=(hold);
						}
						//ignores extra spaces
						else if(hold.contains("  "))
						{
							
							String[] ds = hold.split(" ");
							ISBN=(ds[0]);
						}
						else if(hold.contains(" $"))
						{
							
							String[] ds = hold.split(" $");
							ISBN=(ds[0]);
						}
						//ignores '(' character
						else if(hold.contains(" ("))
						{
							
							String[] ds = hold.split(" \\(");
							if(!isNumeric(ds[0]))
								ISBN="No ISBN";
							else
							ISBN=(ds[0]);
						}
						//ignores '$' character
						else if(hold.contains("$"))
						{
							
							ISBN="No ISBN";
						}

					}
				}
			}
			//if item contains UPC and has no ISBN. Use UPC as the ISBN value
			if(line.contains("UPC")&&(ISBN.equalsIgnoreCase("No ISBN")||ISBN.contentEquals("nullD")))
			{

					line=reader.readLine();
					if(line.contains("</div>"))
					{
						line=reader.readLine();
						if(line.contains("<div class=\"col-xs-12  col-md-5\">"))
						{
							line=reader.readLine();
							String hold=(line.substring(line.indexOf("<span>")+7,line.indexOf("</span>")));
							if(isNumeric(hold)||hold.contains("X"))
							{
								
								ISBN=(hold);
							}

							else if(hold.contains("  "))
							{
								
								String[] ds = hold.split(" ");
								if(!isNumeric(ds[0]))
									ISBN="No ISBN";
								else
								ISBN=(ds[0]);
							}
							else if(hold.contains(" $"))
							{
								
								String[] ds = hold.split(" $");
								ISBN=(ds[0]);
							}
							
							else if(hold.contains(" ("))
							{
								
								String[] ds = hold.split(" \\(");
								System.out.println("Hello");
								if(!isNumeric(ds[0]))
									ISBN="No ISBN";
								else
								ISBN=(ds[0]);
							}
							else if(hold.contains("$"))
							{
								ISBN="No ISBN";
							}

						}
					}
				}
			
			
			line= reader.readLine();
		}
		if(ISBN.contentEquals("nullD"))
		{
			ISBN="No ISBN";
		}
		
		return ISBN;
	}
	
	//open URLinpuStream with a specified user agent
	private static InputStream getURLInputStream(URL url) throws IOException {
	    URLConnection openConnection = null;
		try {
			openConnection = url.openConnection();	
			String USER_AGENT = "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.2) Gecko/20100115 Firefox/3.6"; 
			openConnection.setRequestProperty("User-Agent", USER_AGENT); 
			return openConnection.getInputStream(); 
		} catch (IOException e) {
			e.printStackTrace();
		}	
		return  openConnection.getInputStream();
	}
	
	//Second layer browsing: access each individual search result webpages
	public static String[] searchResults(String searchTerm, String category) throws IOException
	{
		String hold = searchTerm;
		String[] jumbo = new String[12];
		if(hold.contains((" ")))
		{
			hold=searchTerm.replace(" ", "+");
		}
		
		String advanceSearch;
		//if performing advance search
		switch(category)
		{
		 case("book"):
		 {
			 advanceSearch="&category=book&searchFilter=%20sm_format:book";
			 break;
		 }
		 case("movies"):
		 {
			 advanceSearch="&category=book&searchFilter=%20sm_format:movies";
			 break;
		 }
		 case("music"):
		 {
			 advanceSearch="&category=book&searchFilter=%20sm_format:sound_recording";
			 break;
		 }
		 case("periodicals"):
		 {
			 advanceSearch="&category=book&searchFilter=%20sm_format:periodicals";
		 }
		 default: advanceSearch="&category=everything&searchFilter=";
		 		  break;
		}
		
		System.out.println(hold);
		URL textUrl= null;
		try {
			textUrl= new URL("https://www.queenslibrary.org/search/everything?searchField="+hold+advanceSearch);
		} catch(MalformedURLException e)
		{
			System.out.println("Invalid link..");
		}

		
		InputStream content = (InputStream) getURLInputStream(textUrl);
		BufferedReader reader = new BufferedReader (new InputStreamReader(content));

		String line = null;
		line = reader.readLine();
		int i=0;
		while(line!=null)
		{
			//System.out.println(line);
			if(line.contains("<div class=\"cardWrapper\" id=\"wrapper_chamo_"))
			{
				//System.out.println((line.substring(line.indexOf("<div class=\"cardWrapper\" id=\"wrapper_chamo_")+43,line.lastIndexOf("_"))));
				String s1=(line.substring(line.indexOf("<div class=\"cardWrapper\" id=\"wrapper_chamo_")+43,line.lastIndexOf("_4")));
				s1.trim();
				String[] separated=s1.split("_");
				jumbo[i++] = separated[0];
			}

			line= reader.readLine();
		}

		
		return jumbo;
	}
	
	//load user database file and insert each database item in a queue
	public static Queue<String> readFile(Queue<String> urlQueue,String x) throws IOException
	{
			File dir=new File("DB\\userDB");
			if(!dir.exists()) dir.mkdirs();

			File gils=new File(x);
	        if(!gils.exists()) gils.createNewFile();
	        FileReader fr = null;
	        BufferedReader br = null;

	        try {
	            fr = new FileReader(gils);
	            br = new BufferedReader(fr);

	            String line;
	            //loop to read each URL per line, stop once an empty line is reach
	            while ((line = br.readLine()) != null) {
	                urlQueue.add(line);
	            }
	        }

	        catch (IOException e) {
	            e.printStackTrace();
	        }

	        // Close Input Reader once operation is done
	        finally {
	            try {
	                if (br == null)
	                    br.close();
	                if (fr == null)
	                    fr.close();
	            }
	            catch (IOException ex) {
	                ex.printStackTrace();
	            }
	        }
			return urlQueue;
	    }

	//return ISBN item value which will be used to access each individual search result items
	//format: https://www.queenslibrary.org/bib/"bib_id_goes_here"
	public static Queue<String> getISBN(String[] searchResults, Queue<String> isbn) throws IOException
	{
	
			return isbn;
	}
	
	
	 //check if String is numeric
	 public static boolean isNumeric(String strNum) {
		    if (strNum == null) {
		        return false;
		    }
		    try {
		        double d = Double.parseDouble(strNum);
		    } catch (NumberFormatException nfe) {
		        return false;
		    }
		    return true;
		}
	 
	//save image associated with search item
	public static void saveImage(LibraryDatabase tempDB) throws IOException
	{
		
		//if image directory does not exist, create directory
		 if(!new File("IMG").exists())
		 {
		 new File("IMG").mkdir();
		 }
		 
		
		 Queue<String> BibID =new LinkedList<>(); // will be used to name the image file
		 Queue<String> ISBN =new LinkedList<>(); // will be used to access the Image URL
		 Queue<String> Medium =new LinkedList<>(); // will be used to select the default Image to be used if Image URL is not availlable. 
		
		 	String temp; // placeholder for the Key value of an item
			Enumeration items = tempDB.duplicateDB().keys(); // Holds the list of items in the hashtable
			 while(items.hasMoreElements()) // if hashtable database not empty, print all items
		        {
			        temp = (String) items.nextElement(); // holds the key of the current item, the key is a String datatype
			        Item holdtemp = tempDB.duplicateDB().get(temp);
			        BibID.add(holdtemp.bibID);
			        ISBN.add(holdtemp.ISBN);
			        Medium.add(holdtemp.Medium);
		        
		        }
			 //retrieve Item ISBN, MEDIUM, and Bib ID
			 while(!ISBN.isEmpty())
			 {

				 	String currISBN=(ISBN.remove());
				 	String currBibID=BibID.remove();
				 	String currMedium=Medium.remove();
				 	
				 	try { 
				 		//if item has ISBN and has a corresponding image
						java.lang.System.setProperty("https.protocols", "TLSv1");
				        URL urlImg = new URL("https://syndetics.com/index.php?isbn="+currISBN+"/lc.jpg&client=qubop");
				        BufferedImage img = ImageIO.read(urlImg.openStream());
				
				        File file = new File("IMG\\" +currBibID+".jpg");
				        ImageIO.write(img, "jpg", file);
				 	}
				 	catch(Exception e)
				 	{	
				 		try
				 		{ //if item has UPC and has a corresponding image
							java.lang.System.setProperty("https.protocols", "TLSv1");
					        URL urlImg = new URL("https://syndetics.com/index.php?isbn=/lc.jpg&upc="+currISBN+"&client=qubop");
					        BufferedImage img = ImageIO.read(urlImg.openStream());
					
					        File file = new File("IMG\\" +currBibID+".jpg");
					        ImageIO.write(img, "jpg", file);
				 		}
				 		//else use the default image based on item Medium value
				 		catch(Exception d)
				 		{
				 			
					 		String defaultURL=null;
					 		switch(currMedium)
					 		{
					 		case("Book"): defaultURL="https://www.queenslibrary.org/sites/default/files/img-node-level/CDAudiobook_259x396.png"; break;
					 		case("eBook"): defaultURL="https://www.queenslibrary.org/sites/default/files/img-node-level/eBook_259x396.png"; break;
					 		case("CD audiobook"): defaultURL="https://www.queenslibrary.org/sites/default/files/img-node-level/CDAudiobook_259x396.png"; break;
					 		case("Large Print Book"): defaultURL="https://www.queenslibrary.org/sites/default/files/img-node-level/LargePrintBook_259x396.png"; break;
					 		case("VHS"): defaultURL="https://www.queenslibrary.org/sites/default/files/img-node-level/VHS_259x396.png"; break;
					 		case("DVD"): defaultURL="https://queenslibrary.org/sites/default/files/img-node-level/DVD_259x396.png"; break;
					 		case("Blu-ray"): defaultURL="https://www.queenslibrary.org/sites/default/files/img-node-level/Bluray_259x396.png"; break;
					 		case("Video Game"): defaultURL="https://www.queenslibrary.org/sites/default/files/img-node-level/VideoGames_259x396.png"; break;
					 		case("Streaming Video"): defaultURL="https://www.queenslibrary.org/sites/default/files/img-node-level/StreamingVideo_259x396.png"; break;
					 		case("Periodical"): defaultURL="https://www.queenslibrary.org/sites/default/files/img-node-level/Periodical_259x396.png"; break;
					 		case("Microform Newspaper"): defaultURL="https://www.queenslibrary.org/sites/default/files/img-node-level/MicroformNewspaper_259x396.png"; break;
					 		case("Newspaper"): defaultURL="https://www.queenslibrary.org/sites/default/files/img-node-level/Newspaper_259x396.png"; break;
					 		case("CD"): defaultURL="https://www.queenslibrary.org/sites/default/files/img-node-level/Music_CD_259x396.png"; break;
					 		case("Music Score"): defaultURL="https://www.queenslibrary.org/sites/default/files/img-node-level/MusicScore_259x396.png"; break;
					 		default: defaultURL="https://www.queenslibrary.org/sites/default/files/img-node-level/everything_259x396.png";
					 		}
					 		
					 		java.lang.System.setProperty("https.protocols", "TLSv1");
					        URL urlImg = new URL(defaultURL);
					        BufferedImage img = ImageIO.read(urlImg.openStream());
					
					        File file = new File("IMG\\" +currBibID+".png");
					        ImageIO.write(img, "jpg", file);
				 		}
				 	}
			 }
		
	}
	
}



