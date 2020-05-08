/*IMPORT SECTION*/
import java.sql.Timestamp; // Timestamp will be utilized as a placeholder for the current timestamp when item is created/inserted in the hash table database.

/*ITEM CLASS SECTION*/
public class Item
{
	  public  Timestamp timeCreated; // holds the timestamp when item is inserted/created in the database
	  public  String User;         // holds the user name value of the user who inserted/created the item.
	  public  String bibID;          // holds library key value of the item
	  public  String Medium;         // holds type of medium of the item (e.g. Movie, Books, Music, etc.)
	  public  String Title;          // holds the title/name of the item
	  public  String Creator;        // holds the name of the item's creator/author/artist for now
	  public  String Publisher;       // holds the Publisher of the item (e.g. eBooks, CD, PDF, Online Streaming)
	  public  String ISBN;    // holds the ISBN of the item
	  
	  Item(Timestamp timeCreated, String User, String bibID, String Medium, String Title, String Creator, String Publisher, String ISBN)
	  {
		  this.timeCreated = timeCreated;
		  this.User=User;
		  this.bibID = bibID;
		  this.Medium = Medium;
		  this.Title = Title;
		  this.Creator = Creator;
		  this.Publisher = Publisher;
		  this.ISBN = ISBN;
	  }
	  
	  
	  //Constructor for Item array
	  Item( String bibID, String Medium, String Title, String Creator, String Publisher, String ISBN)
	  {

		  this.bibID = bibID;
		  this.Medium = Medium;
		  this.Title = Title;
		  this.Creator = Creator;
		  this.Publisher = Publisher;
		  this.ISBN = ISBN;
	  }
	  
}
