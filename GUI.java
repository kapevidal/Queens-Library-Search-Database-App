import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.Component;

import javax.swing.SwingConstants;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.Insets;

import javax.swing.JTable;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/*Graphical User Interface CLASS SECTION*/
public class GUI {

	private JFrame frmQueensLibraryDb;
	private LibraryDatabase mainDB= new LibraryDatabase();
	LibraryDatabase LibraryAccount= new LibraryDatabase();
	LibraryDatabase onlineSearchDB= new LibraryDatabase();
	LibraryDatabase offlineSearchDB= new LibraryDatabase();
	private JTextField textField;
	private JTable resultTable;
	
	private String userDB="Output.txt"; // specifies user database/output text file. The default is "output.txt".
	private String currentUser="Guest"; // specifies current user. The default is "Guest".
	
	private int enable=0; /* integer value enabler, value=1: grant access to all search types and database checking.
						                            value=0: allows only online search. */
	
	private int activityCount=0; // activity count use for writing output file.
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frmQueensLibraryDb.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws IOException 
	 */
	public GUI() throws IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize() throws IOException
	{
		/*MAIN MENU*/
		LibraryDatabase start= new LibraryDatabase();
		//Create Main Menu window
		frmQueensLibraryDb = new JFrame();
		frmQueensLibraryDb.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e)
			{
				try {
					start.endProgram();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			@Override
			public void windowClosed(WindowEvent e)
			{
				try {
					start.endProgram();;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
				
		frmQueensLibraryDb.setTitle("Queens Library DB (Guest Mode)");
		frmQueensLibraryDb.setBounds(100, 100, 413, 382);
		GridBagConstraints c = new GridBagConstraints();

		frmQueensLibraryDb.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frmQueensLibraryDb.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblNewLabel = new JLabel("Queens Library Search Database");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(10, 10, 20, 5);
		gbc_lblNewLabel.gridx = 5;
		gbc_lblNewLabel.gridy = 1;
		frmQueensLibraryDb.getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		
		/*ONLINE SEARCH BUTTON*/
		JButton onlineSearchbtn = new JButton("Online Queries");
		//onlineSearchbtn.setEnabled(false);
		onlineSearchbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				JFrame os = new JFrame("Online Queries");
				os.setVisible(true);
				os.setBounds(100,100,420,240);
				GridBagLayout dd = new GridBagLayout();
				dd.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
				dd.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
				dd.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
				dd.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
				os.getContentPane().setLayout(dd);
				
				JLabel lblNewLabel = new JLabel("Search Term");
				lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
				GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
				gbc_lblNewLabel.insets = new Insets(10, 10, 5, 5);
				gbc_lblNewLabel.gridx = 2;
				gbc_lblNewLabel.gridy = 1;
				os.getContentPane().add(lblNewLabel, gbc_lblNewLabel);
				
				textField = new JTextField();
				GridBagConstraints gbc_textField = new GridBagConstraints();
				gbc_textField.insets = new Insets(10, 5, 5, 55);
				gbc_textField.fill = GridBagConstraints.HORIZONTAL;
				gbc_textField.gridx = 3;
				gbc_textField.gridy = 1;
				os.getContentPane().add(textField, gbc_textField);
				textField.setColumns(10);
				
				
				//Use for Advance search: user can filter search result by type of medium
				JRadioButton book = new JRadioButton("book");
				JRadioButton movie = new JRadioButton("movies");
				JRadioButton music = new JRadioButton("music");
				JRadioButton periodicals = new JRadioButton("periodicals");
				
				//put the type of choices to a buttongroup (meaning one option can only be selected)
				    Box sizeBox = Box.createHorizontalBox();
				    ButtonGroup mediumGrp = new ButtonGroup();
				    mediumGrp.add(book);
				    mediumGrp.add(movie);
				    mediumGrp.add(music);
				    mediumGrp.add(periodicals);
				    sizeBox.add(book);
				    sizeBox.add(movie);
				    sizeBox.add(music);
				    sizeBox.add(periodicals);
				    sizeBox.setBorder(BorderFactory.createTitledBorder("Medium"));
				    
				    GridBagConstraints gbc_mediumGrp = new GridBagConstraints();
				    gbc_mediumGrp.insets = new Insets(0, 0, 5, 55);
				    gbc_mediumGrp.fill = GridBagConstraints.HORIZONTAL;
				    gbc_mediumGrp.gridx = 3;
				    gbc_mediumGrp.gridy = 2;
				    
				    os.getContentPane().add(sizeBox,gbc_mediumGrp);
				
				    
				/*SEARCH BUTTON: CONDUCT ONLINE SEARCH QUERY USING THE KEY TERM 
				 *               RETREIVE FROM THE TEXTBOX
				 * */
				JButton searchBtn = new JButton("search");
				searchBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e)
					{
						os.setTitle("Searching..");
						onlineSearchDB= new LibraryDatabase(); // temporary online search database
						
						/*SET DATABASE TO BE USED FOR INSERTING DEPENDING ON THE CURRENT USER.*/
						try {
							if(userDB.equalsIgnoreCase("output.txt")&&activityCount==0)
								onlineSearchDB.setOutputFileOneTime("output.txt",currentUser);
							else if(userDB.equalsIgnoreCase("output.txt")&&activityCount!=0)
								onlineSearchDB.setOutputFile("output.txt", currentUser);
							else
								onlineSearchDB.setOutputFile("output.txt",currentUser);
							
							activityCount++;
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					    String searchTerm = textField.getText();	

						
						//CLOSE ALL INPUT AND OUTPUT STREAM UPON JFRAME DISPOSAL
						JFrame onlineSearchResultFrame = new JFrame();
						onlineSearchResultFrame.addWindowListener(new WindowAdapter()
						{
							@Override
							public void windowClosing(WindowEvent e)
							{
								try {
									onlineSearchDB.printDatabase();
									onlineSearchDB.endProgram();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
							
							@Override
							public void windowClosed(WindowEvent e)
							{
								try {
									onlineSearchDB.printDatabase();
									onlineSearchDB.endProgram();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						});
						

						onlineSearchResultFrame.setBounds(100,100,800,300);
						GridBagLayout ds = new GridBagLayout();
						ds.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
						ds.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
						ds.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
						ds.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
						onlineSearchResultFrame.getContentPane().setLayout(ds);
						
						JLabel searchResultlbl = new JLabel("Search Result");
						searchResultlbl.setHorizontalAlignment(SwingConstants.CENTER);
						GridBagConstraints gbc_searchResultlbl= new GridBagConstraints();
						gbc_searchResultlbl.insets = new Insets(10, 0, 10, 5);
						gbc_searchResultlbl.gridx = 5;
						gbc_searchResultlbl.gridy = 1;
						onlineSearchResultFrame.getContentPane().add(searchResultlbl, gbc_searchResultlbl);
						String mediumCh;
						
						//ADVANCE SEARCH: if a medium type is selected
						if(book.isSelected())
							{mediumCh=book.getText();
							onlineSearchResultFrame.setTitle("Online Search Result: "+searchTerm +" ("+mediumCh+")");
							}
						else if(movie.isSelected())
							{mediumCh=movie.getText();
							onlineSearchResultFrame.setTitle("Online Search Result: "+searchTerm +" ("+mediumCh+")");
							}
						else if(music.isSelected())
							{mediumCh=music.getText();
							onlineSearchResultFrame.setTitle("Online Search Result: "+searchTerm +" ("+mediumCh+")");
							}
						else if(periodicals.isSelected())
							{
							mediumCh=periodicals.getText();
							onlineSearchResultFrame.setTitle("Online Search Result: "+searchTerm +" ("+mediumCh+")");
							}
						else {
							mediumCh="";
							onlineSearchResultFrame.setTitle("Online Search Result: "+searchTerm);
							}
						
						
							//perform online search given the search term (also filter search result type of medium if one is selected)
							String[] results = new String[11];
							try {
								// call the URLSearch search result method to perform online search
								results=URLSearch.searchResults(searchTerm,mediumCh);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							   
							/* collect all results and insert each to the online search database*/
							   Queue<String> words=null;
							try {
								words = URLSearch.fillIn(results,new LinkedList<>());
							} catch (IOException e1) {
								e1.printStackTrace();
							}
								onlineSearchDB.insertAll(results,words, onlineSearchDB);
								
								os.dispose();
								onlineSearchResultFrame.setVisible(true);
						/*Create Table to display search result*/
						DefaultTableModel model = new DefaultTableModel() {
							  @Override
							    public boolean isCellEditable(int row, int column) {
							       return false;
							    }
						};				
					    model.addColumn("Time");
					    model.addColumn("User");
					    model.addColumn("Bib ID");
					    model.addColumn("Medium");
					    model.addColumn("Title");
					    model.addColumn("Author");
					    model.addColumn("Publisher");
					    model.addColumn("ISBN");
					    resultTable = new JTable(model);
					    
					    DefaultTableModel modelAdd = (DefaultTableModel) resultTable.getModel();
					    
					    /*READ EACH ITEM IN THE ONLINE SEARCH DATABASE*/
					    Hashtable<String, Item> tempDB=onlineSearchDB.duplicateDB();
					    String temp; // placeholder for the Key value of an item
						Enumeration items = tempDB.keys(); // Holds the list of items in the hashtable
						
						   while(items.hasMoreElements()) // if hashtable database not empty, print all items
					        {
						        temp = (String) items.nextElement(); // holds the key of the current item, the key is a String datatype
						        Item holdtemp = tempDB.get(temp); // get values of the current item using the get(key) method
						        modelAdd.addRow(new Object[]{holdtemp.timeCreated, holdtemp.User, holdtemp.bibID, holdtemp.Medium, holdtemp.Title, holdtemp.Creator, holdtemp.Publisher, holdtemp.ISBN});

					        }
						
					    resultTable.setModel(modelAdd);
					    resultTable.setPreferredScrollableViewportSize(onlineSearchResultFrame.getPreferredSize());		
					    resultTable.setSize(1000,1000);
					    resultTable.setFillsViewportHeight(true);
						GridBagConstraints gbc_table_1 = new GridBagConstraints();
						gbc_table_1.insets = new Insets(0, 10, 10,10);
						gbc_table_1.fill = GridBagConstraints.BOTH;
						gbc_table_1.gridx = 5;
						gbc_table_1.gridy = 5;
						//JScrollPane jsp = new JScrollPane(resultTable);
						
						onlineSearchResultFrame.getContentPane().add(new JScrollPane(resultTable), gbc_table_1);

						/*EXIT BUTTON*/
						JButton exitBtn = new JButton("Exit");
						exitBtn.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								onlineSearchResultFrame.dispose();
							}
						});
			
						GridBagConstraints gbc_exitBtn = new GridBagConstraints();
						gbc_exitBtn.insets = new Insets(0, 10, 10, 5);
						gbc_exitBtn.gridx = 5;
						gbc_exitBtn.gridy = 7;
						onlineSearchResultFrame.getContentPane().add(exitBtn, gbc_exitBtn);
						
						//USER ACCOUNT ONLY: (OPTIONAL) INSERT ONLINE SEARCH RESULT TO USER DATABASE
						JButton insertBtn = new JButton("Add to DB");
						if(enable==0)
						insertBtn.setEnabled(false);
						else if(enable==1)
						insertBtn.setEnabled(true);
						
						/*FUNCTION TO INSERT ONLINE SEARCH RESULT TO USER DATABASE
						 * 			  SAVE ITEM IMAGES
						 *            INSERT SEARCH RESULT TO MAIN DATABASE (An ADMIN DATABASE that HOLDS ALL STORED SEARCH ITEMS FOR ALL USERS)*/
						insertBtn.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								try {
									
									if(userDB.equalsIgnoreCase("DB.txt"))
									{
									Queue<String> as = URLSearch.readFile(new LinkedList<String>(), "DB\\"+userDB);
									LibraryDatabase mainDB=new LibraryDatabase();
									URLSearch.saveImage(onlineSearchDB);
									mainDB.loadDB(userDB,currentUser);
									mainDB.reload(as,mainDB);
									mainDB.verifyDatabase();
									mainDB.insertToMain(onlineSearchDB,userDB);
									mainDB.saveDB();
									mainDB.endProgram();
									}
									else
									{
										//load User and Main Database
										Queue<String> as = URLSearch.readFile(new LinkedList<String>(), "DB\\"+userDB);
										Queue<String> df = URLSearch.readFile(new LinkedList<String>(), "DB\\DB.txt");
										
										//insert to User database
										System.out.println("Inserting User DB");
										LibraryDatabase currDB=new LibraryDatabase();
										URLSearch.saveImage(onlineSearchDB);
										currDB.loadDB(userDB,currentUser);
										currDB.reload(as,currDB);
										currDB.verifyDatabase();
										currDB.insertToMain(onlineSearchDB,userDB);
										currDB.saveDB();
										currDB.endProgram();

										//insert to Main Database
										System.out.println("Inserting to MAIN DB");
										LibraryDatabase mainDB=new LibraryDatabase();
										mainDB.loadDB("DB.txt",currentUser);
										mainDB.reload(df,mainDB);
										mainDB.verifyDatabase();
										mainDB.insertToMain(onlineSearchDB,"DB.txt");
										mainDB.saveDB();
										mainDB.endProgram();
									}
									JOptionPane.showMessageDialog(null, "Items added to Main DB successfully");
									
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}

							}
						});
			
						GridBagConstraints gbc_insertBtn = new GridBagConstraints();
						gbc_insertBtn.insets = new Insets(0, 10, 10, 5);
						gbc_insertBtn.gridx = 5;
						gbc_insertBtn.gridy = 6;
						onlineSearchResultFrame.getContentPane().add(insertBtn, gbc_insertBtn);
						
						
					}});
				GridBagConstraints gbc_searchbtn = new GridBagConstraints();
				gbc_searchbtn.insets = new Insets(0, 10, 5, 5);
				gbc_searchbtn.gridx = 2;
				gbc_searchbtn.gridy = 3;
				os.getContentPane().add(searchBtn, gbc_searchbtn);
				
				//close the window
				JButton exitBtn = new JButton("Exit");
				exitBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						os.dispose();
					}
				});
	
				GridBagConstraints gbc_exitBtn = new GridBagConstraints();
				gbc_exitBtn.insets = new Insets(0, 250, 0, 5);
				gbc_exitBtn.gridx = 3;
				gbc_exitBtn.gridy = 3;
				os.getContentPane().add(exitBtn, gbc_exitBtn);
	//			os.pack();
			}
		});
		
		onlineSearchbtn.setFocusPainted(false);
		onlineSearchbtn.setContentAreaFilled(false);
		GridBagConstraints gbc_onlineSearchbtn = new GridBagConstraints();
		gbc_onlineSearchbtn.insets = new Insets(0, 0, 5, 5);
		gbc_onlineSearchbtn.gridx = 5;
		gbc_onlineSearchbtn.gridy = 3;
		frmQueensLibraryDb.getContentPane().add(onlineSearchbtn, gbc_onlineSearchbtn);
		
		
		/*OFFLINE SEARCH BUTTON: CONDUCT USER DATABASE SEARCH USING USER PROVIDED KEY TERM*/
		JButton offlineSearchbtn = new JButton("Offline Queries");
		offlineSearchbtn.setEnabled(false);
		offlineSearchbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame os = new JFrame("Offline Queries");
				os.setVisible(true);
				os.setBounds(100,100,300,300);
				GridBagLayout dd = new GridBagLayout();
				dd.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
				dd.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
				dd.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
				dd.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
				os.getContentPane().setLayout(dd);
				
				JLabel lblNewLabel = new JLabel("Search Term");
				lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
				GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
				gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
				gbc_lblNewLabel.gridx = 2;
				gbc_lblNewLabel.gridy = 1;
				os.getContentPane().add(lblNewLabel, gbc_lblNewLabel);
					
				//textfield for the search term
				textField = new JTextField();
				GridBagConstraints gbc_textField = new GridBagConstraints();
				gbc_textField.insets = new Insets(0, 0, 5, 5);
				gbc_textField.fill = GridBagConstraints.HORIZONTAL;
				gbc_textField.gridx = 3;
				gbc_textField.gridy = 1;
				os.getContentPane().add(textField, gbc_textField);
				textField.setColumns(10);
				
				JLabel lblNewLabels = new JLabel("(OPTIONAL) Time");
				lblNewLabels.setHorizontalAlignment(SwingConstants.CENTER);
				GridBagConstraints gbc_lblNewLabels = new GridBagConstraints();
				gbc_lblNewLabels.insets = new Insets(0, 0, 5, 5);
				gbc_lblNewLabels.gridx = 2;
				gbc_lblNewLabels.gridy = 2;
				os.getContentPane().add(lblNewLabels, gbc_lblNewLabels);
					
				//textfield for a given time
				JTextField textFields = new JTextField();
				GridBagConstraints gbc_textFields = new GridBagConstraints();
				gbc_textFields.insets = new Insets(0, 0, 5, 5);
				gbc_textFields.fill = GridBagConstraints.HORIZONTAL;
				gbc_textFields.gridx = 3;
				gbc_textFields.gridy = 2;
				os.getContentPane().add(textFields, gbc_textFields);
				textFields.setColumns(10);
				
				/*RETRIEVE SEARCH TERM FROM SEARCH TERM TEXTFIELD, THEN CONDUCT DATABASE SEARCH*/
				JButton searchBtn = new JButton("search");
				searchBtn.addActionListener(new ActionListener() 
				{
					public void actionPerformed(ActionEvent e)
					{
						Queue<String> as=null;
						//SELECT USER DATABASE TO BE SEARCHED FOR
						if(userDB.equalsIgnoreCase("output.txt"))
							try {
								as = URLSearch.readFile(new LinkedList<String>(), "DB\\DB.txt");
							} catch (IOException e3) {
								// TODO Auto-generated catch block
								e3.printStackTrace();
							}
						else
							try {
								as = URLSearch.readFile(new LinkedList<String>(), "DB\\"+userDB);
							} catch (IOException e3) {
								// TODO Auto-generated catch block
								e3.printStackTrace();
							}
						
						offlineSearchDB= new LibraryDatabase(); //TEMPORARY DATABASE TO HOLD SEARCH RESULT
						try {
							
							if(activityCount!=0)
							offlineSearchDB.setOutputFile("Output.txt",currentUser);
							else
							offlineSearchDB.setOutputFileOneTime("Output.txt",currentUser);
							
							activityCount++;
							
							if(!userDB.equalsIgnoreCase("output.txt"))
							//mainDB.loadDB("DB.txt");
							{mainDB.loadDB(userDB,currentUser);
							mainDB.reload(as,mainDB);
							mainDB.verifyDatabase();
							}
							else
							{mainDB.loadDB("DB.txt",currentUser);
							mainDB.reload(as,mainDB);
							mainDB.verifyDatabase();
							}
								
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						
						//performs offline search using search term (and timeframe, if given)
					    String searchTerm = textField.getText();
					    String time = textFields.getText();
					    try {
							offlineSearchDB.offlineSearch(searchTerm,time, offlineSearchDB, mainDB);
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}

						
						os.dispose();
						JFrame sr = new JFrame("Offline Search Result: "+searchTerm);
						//CLOSE ALL INPUT AND OUTPUT STREAM UPON EXIT
						sr.addWindowListener(new WindowAdapter() {
							@Override
							public void windowClosing(WindowEvent e)
							{
								
								try {
									offlineSearchDB.printDatabase();
								//mainDB.insertToMain(offlineSearchDB);
									mainDB.saveDB();
									mainDB.endProgram();
									offlineSearchDB.endProgram();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								/*Show a prompt asking a user if he/she wants an output copy through email.*/
								String email=JOptionPane.showInputDialog("Do you want an email report? Enter email:");
								Email test = new Email(email,"Offline Query: Here is your requested copy of the output.","Here is your requested copy of your output.");
								
							}
							
							@Override
							public void windowClosed(WindowEvent e)
							{
								
								try {
									offlineSearchDB.printDatabase();
								//mainDB.insertToMain(offlineSearchDB);
									mainDB.saveDB();
									mainDB.endProgram();
									offlineSearchDB.endProgram();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								
								//upon closing the window, give the user the option to receive a copy of the current output logs through email.
								String email=JOptionPane.showInputDialog("Do you want an email report? Enter email:");
								//if email is specified, perform email reporting
								if(email!=null)
								{
									try {
											Email sendEmail = new Email(email,"Hey " + currentUser+"! Here is the requested copy of your output log.","Here is your requested copy of your output.");
										}
									catch(Exception s)
										{
											System.out.println("No email specified");
										}
								}
								
							}
						});
						sr.setVisible(true);
						sr.setBounds(100,100,800,300);
						GridBagLayout ds = new GridBagLayout();
						ds.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
						ds.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
						ds.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
						ds.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
						sr.getContentPane().setLayout(ds);
						
						JLabel searchResultlbl = new JLabel("Search Result");
						searchResultlbl.setHorizontalAlignment(SwingConstants.CENTER);
						GridBagConstraints gbc_searchResultlbl= new GridBagConstraints();
						gbc_searchResultlbl.insets = new Insets(10, 0, 10, 5);
						gbc_searchResultlbl.gridx = 5;
						gbc_searchResultlbl.gridy = 1;
						sr.getContentPane().add(searchResultlbl, gbc_searchResultlbl);


						/*CREATE TABLE TO HOLD SEARCH RESULT*/
						DefaultTableModel model = new DefaultTableModel() {
							  @Override
							    public boolean isCellEditable(int row, int column) {
							       return false;
							    }
						};				
					    model.addColumn("Time");
					    model.addColumn("User");
					    model.addColumn("Bib ID");
					    model.addColumn("Medium");
					    model.addColumn("Title");
					    model.addColumn("Author");
					    model.addColumn("Publisher");
					    model.addColumn("ISBN");
					    
					    resultTable = new JTable(model){
				            @Override
				            public Component prepareRenderer(TableCellRenderer renderer,
				                    int row, int col) {

				                Component c = super.prepareRenderer(renderer, row, col);
				                if (c instanceof JComponent) {
				                    JComponent jc = (JComponent)c;
				                    String name = getValueAt(row, 2).toString(); //RETRIEVE ROW VALUE IN BIBID COLUMN
				                    String html = getHtml(name);
				                   //System.out.println(html);
				                    jc.setToolTipText(html);
				                }   
				                return c;
				            }
				        };
					    		
					    		
					    
					    DefaultTableModel modelAdd = (DefaultTableModel) resultTable.getModel();
					    
					    //GET ITEMS IN TEMP OFFLINE SEARCH DATABSE AND PRINT IT TO THE TABLE
					    Hashtable<String, Item> tempDB=offlineSearchDB.duplicateDB();
					    String temp; // placeholder for the Key value of an item
						Enumeration items = tempDB.keys(); // Holds the list of items in the hashtable
						
						   while(items.hasMoreElements()) // if hashtable database not empty, print all items
					        {
						        temp = (String) items.nextElement(); // holds the key of the current item, the key is a String datatype
						        Item holdtemp = tempDB.get(temp); // get values of the current item using the get(key) method
						        modelAdd.addRow(new Object[]{holdtemp.timeCreated, holdtemp.User, holdtemp.bibID, holdtemp.Medium, holdtemp.Title, holdtemp.Creator, holdtemp.Publisher, holdtemp.ISBN});

					        }
						
					    resultTable.setModel(modelAdd);
					    resultTable.setPreferredScrollableViewportSize(sr.getPreferredSize());		
					    resultTable.setSize(1000,1000);
					    resultTable.setFillsViewportHeight(true);
						GridBagConstraints gbc_table_1 = new GridBagConstraints();
						gbc_table_1.insets = new Insets(0, 10, 10,10);
						gbc_table_1.fill = GridBagConstraints.BOTH;
						gbc_table_1.gridx = 5;
						gbc_table_1.gridy = 5;
						JScrollPane jsp = new JScrollPane(resultTable);
						
						sr.getContentPane().add(new JScrollPane(resultTable), gbc_table_1);
						
						//EXIT BUTTON
						JButton exit2Btn = new JButton("Exit");
						exit2Btn.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
							
								sr.dispose();
							}});
								GridBagConstraints gbc_exit2Btn = new GridBagConstraints();
								gbc_exit2Btn.insets = new Insets(0, 10, 10, 5);
								gbc_exit2Btn.gridx = 5;
								gbc_exit2Btn.gridy = 6;
								sr.getContentPane().add(exit2Btn, gbc_exit2Btn);

				}});
				
				GridBagConstraints gbc_searchbtn = new GridBagConstraints();
				gbc_searchbtn.insets = new Insets(0, 0, 5, 5);
				gbc_searchbtn.gridx = 3;
				gbc_searchbtn.gridy = 3;
				os.getContentPane().add(searchBtn, gbc_searchbtn);
				
				//EXIT BUTTON
				JButton exitBtn = new JButton("Exit");
				exitBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						os.dispose();
					}
				});
	
				GridBagConstraints gbc_exitBtn = new GridBagConstraints();
				gbc_exitBtn.insets = new Insets(0, 0, 0, 5);
				gbc_exitBtn.gridx = 4;
				gbc_exitBtn.gridy = 3;
				os.getContentPane().add(exitBtn, gbc_exitBtn);
				os.pack();
					
			}
		});
		
		offlineSearchbtn.setFocusPainted(false);
		offlineSearchbtn.setContentAreaFilled(false);
		GridBagConstraints gbc_offlineSearchbtn = new GridBagConstraints();
		gbc_offlineSearchbtn.insets = new Insets(0, 0, 5, 5);
		gbc_offlineSearchbtn.gridx = 5;
		gbc_offlineSearchbtn.gridy = 4;
		frmQueensLibraryDb.getContentPane().add(offlineSearchbtn, gbc_offlineSearchbtn);
		
		//EXIT BUTTON
		JButton exitBtn = new JButton("Exit");
		exitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmQueensLibraryDb.dispose();
				try {
					mainDB.endProgram();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		//CHECK USER DATABASE BUTTON: BROWSE RESULTS SAVED IN THE USER DATABASE
		JButton checkDB = new JButton("Check Search DB");
		checkDB.setEnabled(false);
		checkDB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Queue<String> as=null;
				try {
					as = URLSearch.readFile(new LinkedList<String>(), "DB\\"+userDB);
				} catch (IOException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
				LibraryDatabase mainDB=new LibraryDatabase();
				try {
					mainDB.loadDB(userDB,currentUser);

					mainDB.reload(as,mainDB);
					mainDB.verifyDatabase();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				
				JFrame sr = new JFrame("User Database");
				sr.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent e)
					{
						try {
							mainDB.saveDB();
							mainDB.endProgram();
							
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					public void windowClosed(WindowEvent e)
					{
						try {
							mainDB.saveDB();
							mainDB.endProgram();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				sr.setVisible(true);
				sr.setBounds(100,100,800,500);
				GridBagLayout ds = new GridBagLayout();
				ds.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
				ds.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
				ds.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
				ds.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
				sr.getContentPane().setLayout(ds);
				
				JLabel searchResultlbl = new JLabel("Search Database");
				searchResultlbl.setHorizontalAlignment(SwingConstants.CENTER);
				GridBagConstraints gbc_searchResultlbl= new GridBagConstraints();
				gbc_searchResultlbl.insets = new Insets(10, 0, 10, 5);
				gbc_searchResultlbl.gridx = 5;
				gbc_searchResultlbl.gridy = 1;
				sr.getContentPane().add(searchResultlbl, gbc_searchResultlbl);
				
				/*TABLE TO DISPLAY ITEMS STORED IN USER DATABASE*/
				DefaultTableModel model = new DefaultTableModel() {
					  @Override
					    public boolean isCellEditable(int row, int column) {
					       return false;
					    }
				};				
			    model.addColumn("Time");
			    model.addColumn("User");
			    model.addColumn("Bib ID");
			    model.addColumn("Medium");
			    model.addColumn("Title");
			    model.addColumn("Author");
			    model.addColumn("Publisher");
			    model.addColumn("ISBN");
			    
			    /*OVERRIDES JTABLE COLUMN TOOLTIP TO DISPLAY CORRESPONDING ITEM IMAGE.*/
			    resultTable = new JTable(model) {
		            @Override
		            public Component prepareRenderer(TableCellRenderer renderer,
		                    int row, int col) {

		                Component c = super.prepareRenderer(renderer, row, col);
		                if (c instanceof JComponent) {
		                    JComponent jc = (JComponent)c;
		                    String name = getValueAt(row, 2).toString(); //RETRIEVE ROW VALUE IN BIBID COLUMN
		                    String html = getHtml(name);
		                   //System.out.println(html);
		                    jc.setToolTipText(html);
		                }   
		                return c;
		            }
		        };

			    DefaultTableModel modelAdd = (DefaultTableModel) resultTable.getModel();
			    
			    Hashtable<String, Item> tempDB=mainDB.duplicateDB();
			    String temp; // placeholder for the Key value of an item
				Enumeration items = tempDB.keys(); // Holds the list of items in the hashtable
				
				   while(items.hasMoreElements()) // if hashtable database not empty, print all items
			        {
				        temp = (String) items.nextElement(); // holds the key of the current item, the key is a String datatype
				        Item holdtemp = tempDB.get(temp); // get values of the current item using the get(key) method
				        modelAdd.addRow(new Object[]{holdtemp.timeCreated, holdtemp.User, holdtemp.bibID, holdtemp.Medium, holdtemp.Title, holdtemp.Creator, holdtemp.Publisher, holdtemp.ISBN});

			        }
				
			    resultTable.setModel(modelAdd);
			    resultTable.setPreferredScrollableViewportSize(sr.getPreferredSize());		
			    resultTable.setSize(1500,1500);
			    resultTable.setFillsViewportHeight(true);
				GridBagConstraints gbc_table_1 = new GridBagConstraints();
				gbc_table_1.insets = new Insets(0, 10, 10,10);
				gbc_table_1.fill = GridBagConstraints.BOTH;
				gbc_table_1.gridx = 5;
				gbc_table_1.weightx=2;
				gbc_table_1.weighty=2;
				gbc_table_1.gridy = 5;
		
				sr.getContentPane().add(new JScrollPane(resultTable), gbc_table_1);
				
				//EXIT BUTTON
				JButton exitBtn = new JButton("Exit");
				exitBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						 for (int i = modelAdd.getRowCount()-1; i > -1; i--) 
						       modelAdd.removeRow(i);
						sr.dispose();
					}
					
				});
	
				GridBagConstraints gbc_exitBtn = new GridBagConstraints();
				gbc_exitBtn.insets = new Insets(0, 10, 10, 5);
				gbc_exitBtn.gridx = 5;
				gbc_exitBtn.gridy = 9;
				sr.getContentPane().add(exitBtn, gbc_exitBtn);
				
				//Load Backup button: load stored search item from the Main Database if the item 'User' value matches the current user.
				JButton loadBtn = new JButton("Load Backup");
				loadBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {

						Queue<String> df = URLSearch.readFile(new LinkedList<String>(), "DB\\DB.txt");
						mainDB.loadDB(df, mainDB);
						
						//update the table by re-adding rows
						 for (int i = modelAdd.getRowCount()-1; i > -1; i--) 
						       modelAdd.removeRow(i);
						 
						 Hashtable<String, Item> tempDB=mainDB.duplicateDB();
						    String temp; // placeholder for the Key value of an item
							Enumeration items = tempDB.keys(); // Holds the list of items in the hashtable
							
							   while(items.hasMoreElements()) // if hashtable database not empty, print all items
						        {
							        temp = (String) items.nextElement(); // holds the key of the current item, the key is a String datatype
							        Item holdtemp = tempDB.get(temp); // get values of the current item using the get(key) method
							        modelAdd.addRow(new Object[]{holdtemp.timeCreated, holdtemp.User, holdtemp.bibID, holdtemp.Medium, holdtemp.Title, holdtemp.Creator, holdtemp.Publisher, holdtemp.ISBN});

						        }
						
						} catch(Exception e1)
						{e1.printStackTrace();}
						JOptionPane.showMessageDialog(null, "Load Backup complete.");
						
					}
					
				});
	
				GridBagConstraints gbc_loadBtn = new GridBagConstraints();
				gbc_loadBtn.insets = new Insets(0, 10, 10, 5);
				gbc_loadBtn.gridx = 5;
				gbc_loadBtn.gridy = 8;
				sr.getContentPane().add(loadBtn, gbc_loadBtn);
				
				/*REMOVE BUTTON: REMOVE AN ITEM USING BibID ENTERED BY THE USER*/
				JButton removeBtn = new JButton("Remove Item");
				removeBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					
						JFrame os = new JFrame("Remove Item");
						os.setVisible(true);
						os.setBounds(100,100,300,100);
						GridBagLayout dd = new GridBagLayout();
						dd.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
						dd.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
						dd.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
						dd.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
						os.getContentPane().setLayout(dd);
						
						JLabel lblNewLabel = new JLabel("Enter Item ID:");
						lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
						GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
						gbc_lblNewLabel.insets = new Insets(0, 5, 5, 5);
						gbc_lblNewLabel.gridx = 1;
						gbc_lblNewLabel.gridy = 1;
						os.getContentPane().add(lblNewLabel, gbc_lblNewLabel);
						

						textField = new JTextField();
						GridBagConstraints gbc_textField = new GridBagConstraints();
						gbc_textField.insets = new Insets(0, 0, 5, 5);
						gbc_textField.fill = GridBagConstraints.HORIZONTAL;
						gbc_textField.gridx = 2;
						gbc_textField.gridy = 1;
						os.getContentPane().add(textField, gbc_textField);
						textField.setColumns(10);
						
						//PERFORM DATABASE ITEM REMOVAL METHOD: RETREIEVE BIB ID VALUE FROM THE TEXTFIELD
						JButton searchBtn = new JButton("remove");
						searchBtn.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e)
							{
								try {
									if(mainDB.checkID(textField.getText()))
									{
									 mainDB.removeItem(textField.getText());
									 
									 //UPDATE TABLE AFTER ITEM REMOVAL
									 for (int i = modelAdd.getRowCount()-1; i > -1; i--) 
									       modelAdd.removeRow(i);
									 
									 Hashtable<String, Item> tempDB=mainDB.duplicateDB();
									    String temp; // placeholder for the Key value of an item
										Enumeration items = tempDB.keys(); // Holds the list of items in the hashtable
										
										   while(items.hasMoreElements()) // if hashtable database not empty, print all items
									        {
										        temp = (String) items.nextElement(); // holds the key of the current item, the key is a String datatype
										        Item holdtemp = tempDB.get(temp); // get values of the current item using the get(key) method
										        modelAdd.addRow(new Object[]{holdtemp.timeCreated, holdtemp.User, holdtemp.bibID, holdtemp.Medium, holdtemp.Title, holdtemp.Creator, holdtemp.Publisher, holdtemp.ISBN});

									        }
									 
									    
										JOptionPane.showMessageDialog(null, "Item deleted successfully");
										os.dispose();
									}
									else
										JOptionPane.showMessageDialog(null, "Item not found");
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								
								
							}});
						GridBagConstraints gbc_searchBtn= new GridBagConstraints();
						gbc_searchBtn.insets = new Insets(0, 5, 5, 5);
						gbc_searchBtn.gridx = 1;
						gbc_searchBtn.gridy = 2;
						os.getContentPane().add(searchBtn, gbc_searchBtn);
						
						
						//EXIT BUTTON
						JButton exitBtn = new JButton("Exit");
						exitBtn.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								
								os.dispose();
							}
						});
			
						GridBagConstraints gbc_exitBtn = new GridBagConstraints();
						gbc_exitBtn.insets = new Insets(0, 0, 0, 5);
						gbc_exitBtn.gridx = 2;
						gbc_exitBtn.gridy = 2;
						os.getContentPane().add(exitBtn, gbc_exitBtn);
					}
				});
	
				GridBagConstraints gbc_removeBtn = new GridBagConstraints();
				gbc_removeBtn.insets = new Insets(0, 10, 10, 5);
				gbc_removeBtn.gridx = 5;
				gbc_removeBtn.gridy = 7;
				sr.getContentPane().add(removeBtn, gbc_removeBtn);
				
//--------------------------------------------------------------------------------------------------------------
				//MODIFY BUTTON: MODIFY ITEM THAT CONTAINS THE USER SPECIFIED BIB ID
				JButton modifyBtn = new JButton("Modify Item");
				modifyBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					
						JFrame os = new JFrame("Modify Item");
						os.setVisible(true);
						os.setBounds(100,100,300,300);
						GridBagLayout dd = new GridBagLayout();
						dd.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
						dd.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
						dd.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
						dd.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,0.0};
						os.getContentPane().setLayout(dd);
						
						JLabel lblNewLabel = new JLabel("Enter Item ID:");
						lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
						GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
						gbc_lblNewLabel.insets = new Insets(0, 5, 5, 5);
						gbc_lblNewLabel.gridx = 1;
						gbc_lblNewLabel.gridy = 1;
						os.getContentPane().add(lblNewLabel, gbc_lblNewLabel);

						textField = new JTextField();
						GridBagConstraints gbc_textField = new GridBagConstraints();
						gbc_textField.insets = new Insets(0, 5, 5, 5);
						gbc_textField.fill = GridBagConstraints.HORIZONTAL;
						gbc_textField.gridx = 2;
						gbc_textField.gridy = 1;
						os.getContentPane().add(textField, gbc_textField);
						textField.setColumns(10);
						
						//ENTER NEW ID (OPTIONAL: RE-ENTER BIB ID TO RE-USE)
						JLabel lblNewLabel1 = new JLabel("Enter New ID:");
						lblNewLabel1.setHorizontalAlignment(SwingConstants.CENTER);
						GridBagConstraints gbc_lblNewLabel1 = new GridBagConstraints();
						gbc_lblNewLabel1.insets = new Insets(0, 5, 5, 5);
						gbc_lblNewLabel1.gridx = 1;
						gbc_lblNewLabel1.gridy = 2;
						os.getContentPane().add(lblNewLabel1, gbc_lblNewLabel1);

						JTextField textField1 = new JTextField();
						GridBagConstraints gbc_textField1 = new GridBagConstraints();
						gbc_textField1.insets = new Insets(0, 5, 5, 5);
						gbc_textField1.fill = GridBagConstraints.HORIZONTAL;
						gbc_textField1.gridx = 2;
						gbc_textField1.gridy = 2;
						os.getContentPane().add(textField1, gbc_textField1);
						textField1.setColumns(10);
						
						//ENTER NEW MEDIUM OF MODIFIED ITEM
						JLabel lblNewLabel2 = new JLabel("Enter Medium:");
						lblNewLabel2.setHorizontalAlignment(SwingConstants.CENTER);
						GridBagConstraints gbc_lblNewLabel2 = new GridBagConstraints();
						gbc_lblNewLabel2.insets = new Insets(0, 5, 5, 5);
						gbc_lblNewLabel2.gridx = 1;
						gbc_lblNewLabel2.gridy = 3;
						os.getContentPane().add(lblNewLabel2, gbc_lblNewLabel2);

						JTextField textField2 = new JTextField();
						GridBagConstraints gbc_textField2 = new GridBagConstraints();
						gbc_textField2.insets = new Insets(0, 5, 5, 5);
						gbc_textField2.fill = GridBagConstraints.HORIZONTAL;
						gbc_textField2.gridx = 2;
						gbc_textField2.gridy = 3;
						os.getContentPane().add(textField2, gbc_textField2);
						textField2.setColumns(10);
						
						
						//ENTER NEW TITLE OF MODIFIED ITEM
						JLabel lblNewLabel3 = new JLabel("Enter Title:");
						lblNewLabel3.setHorizontalAlignment(SwingConstants.CENTER);
						GridBagConstraints gbc_lblNewLabel3 = new GridBagConstraints();
						gbc_lblNewLabel3.insets = new Insets(0, 5, 5, 5);
						gbc_lblNewLabel3.gridx = 1;
						gbc_lblNewLabel3.gridy = 4;
						os.getContentPane().add(lblNewLabel3, gbc_lblNewLabel3);

						JTextField textField3 = new JTextField();
						GridBagConstraints gbc_textField3 = new GridBagConstraints();
						gbc_textField3.insets = new Insets(0, 5, 5, 5);
						gbc_textField3.fill = GridBagConstraints.HORIZONTAL;
						gbc_textField3.gridx = 2;
						gbc_textField3.gridy = 4;
						os.getContentPane().add(textField3, gbc_textField3);
						textField3.setColumns(10);
						
						
						//ENTER NEW AUTHOR OF MODIFIED ITEM
						JLabel lblNewLabel4 = new JLabel("Enter Author:");
						lblNewLabel4.setHorizontalAlignment(SwingConstants.CENTER);
						GridBagConstraints gbc_lblNewLabel4 = new GridBagConstraints();
						gbc_lblNewLabel4.insets = new Insets(0, 5, 5, 5);
						gbc_lblNewLabel4.gridx = 1;
						gbc_lblNewLabel4.gridy = 5;
						os.getContentPane().add(lblNewLabel4, gbc_lblNewLabel4);

						JTextField textField4 = new JTextField();
						GridBagConstraints gbc_textField4 = new GridBagConstraints();
						gbc_textField4.insets = new Insets(0, 5, 5, 5);
						gbc_textField4.fill = GridBagConstraints.HORIZONTAL;
						gbc_textField4.gridx = 2;
						gbc_textField4.gridy = 5;
						os.getContentPane().add(textField4, gbc_textField4);
						textField4.setColumns(10);
						
						
						//ENTER NEW PUBLISHER OF MODIFIED ITEM
						JLabel lblNewLabel5 = new JLabel("Enter Publisher:");
						lblNewLabel5.setHorizontalAlignment(SwingConstants.CENTER);
						GridBagConstraints gbc_lblNewLabel5 = new GridBagConstraints();
						gbc_lblNewLabel5.insets = new Insets(0, 5, 5, 5);
						gbc_lblNewLabel5.gridx = 1;
						gbc_lblNewLabel5.gridy = 6;
						os.getContentPane().add(lblNewLabel5, gbc_lblNewLabel5);

						JTextField textField5 = new JTextField();
						GridBagConstraints gbc_textField5 = new GridBagConstraints();
						gbc_textField5.insets = new Insets(0, 5, 5, 5);
						gbc_textField5.fill = GridBagConstraints.HORIZONTAL;
						gbc_textField5.gridx = 2;
						gbc_textField5.gridy = 6;
						os.getContentPane().add(textField5, gbc_textField5);
						textField5.setColumns(10);
						
						
						//ENTER NEW ISBN OF MODIFIED ITEM
						JLabel lblNewLabel6 = new JLabel("Enter ISBN:");
						lblNewLabel6.setHorizontalAlignment(SwingConstants.CENTER);
						GridBagConstraints gbc_lblNewLabel6 = new GridBagConstraints();
						gbc_lblNewLabel6.insets = new Insets(0, 5, 5, 5);
						gbc_lblNewLabel6.gridx = 1;
						gbc_lblNewLabel6.gridy = 7;
						os.getContentPane().add(lblNewLabel6, gbc_lblNewLabel6);

						JTextField textField6 = new JTextField();
						GridBagConstraints gbc_textField6 = new GridBagConstraints();
						gbc_textField6.insets = new Insets(0, 5, 5, 5);
						gbc_textField6.fill = GridBagConstraints.HORIZONTAL;
						gbc_textField6.gridx = 2;
						gbc_textField6.gridy = 7;
						os.getContentPane().add(textField6, gbc_textField6);
						textField6.setColumns(10);

						//PERFORM DATABASE ITEM MODIFICATION METHOD
						JButton searchBtn = new JButton("Modify");
						searchBtn.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e)
							{
								try {
									if(mainDB.checkID(textField.getText()))
									{
										mainDB.modifyItem(currentUser, textField.getText(), textField1.getText(), textField2.getText(), textField3.getText(), textField4.getText(), textField5.getText(), textField6.getText());
									 
										//UPDATE TABLE AFTER ITEM MODIFICATION
										for (int i = modelAdd.getRowCount()-1; i > -1; i--) 
									       modelAdd.removeRow(i);
									 
										 Hashtable<String, Item> tempDB=mainDB.duplicateDB();
										    String temp; // placeholder for the Key value of an item
											Enumeration items = tempDB.keys(); // Holds the list of items in the hashtable
											
											   while(items.hasMoreElements()) // if hashtable database not empty, print all items
										        {
											        temp = (String) items.nextElement(); // holds the key of the current item, the key is a String datatype
											        Item holdtemp = tempDB.get(temp); // get values of the current item using the get(key) method
											        modelAdd.addRow(new Object[]{holdtemp.timeCreated, holdtemp.User, holdtemp.bibID, holdtemp.Medium, holdtemp.Title, holdtemp.Creator, holdtemp.Publisher, holdtemp.ISBN});
	
										        }
										 
									    
									JOptionPane.showMessageDialog(null, "Item modified successfully");
									os.dispose();
									}
									else
										JOptionPane.showMessageDialog(null, "Item not found");
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								
								
							}});
						GridBagConstraints gbc_searchBtn= new GridBagConstraints();
						gbc_searchBtn.insets = new Insets(0, 5, 5, 5);
						gbc_searchBtn.gridx = 1;
						gbc_searchBtn.gridy = 8;
						os.getContentPane().add(searchBtn, gbc_searchBtn);
						
						JButton exitBtn = new JButton("Exit");
						exitBtn.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								
								os.dispose();
							}
						});
			
						GridBagConstraints gbc_exitBtn = new GridBagConstraints();
						gbc_exitBtn.insets = new Insets(0, 0, 0, 5);
						gbc_exitBtn.gridx = 2;
						gbc_exitBtn.gridy = 8;
						os.getContentPane().add(exitBtn, gbc_exitBtn);
					}
				});
	
				GridBagConstraints gbc_modifyBtn = new GridBagConstraints();
				gbc_modifyBtn.insets = new Insets(0, 10, 10, 5);
				gbc_modifyBtn.gridx = 5;
				gbc_modifyBtn.gridy = 6;
				sr.getContentPane().add(modifyBtn, gbc_modifyBtn);

				
			}
		});
		checkDB.setFocusPainted(false);
		checkDB.setContentAreaFilled(false);
		GridBagConstraints gbc_checkDB = new GridBagConstraints();
		gbc_checkDB.insets = new Insets(0, 0, 5, 5);
		gbc_checkDB.gridx = 5;
		gbc_checkDB.gridy = 5;
		frmQueensLibraryDb.getContentPane().add(checkDB, gbc_checkDB);
		
		exitBtn.setFocusPainted(false);
		exitBtn.setContentAreaFilled(false);
		GridBagConstraints gbc_exitBtn = new GridBagConstraints();
		gbc_exitBtn.insets = new Insets(0, 0, 20, 20);
		gbc_exitBtn.gridx = 5;
		gbc_exitBtn.gridy = 7;
		frmQueensLibraryDb.getContentPane().add(exitBtn, gbc_exitBtn);
		
		/* ACCOUNT LOGIN BUTTON: USER CAN SELECT WHETHER TO LOGIN INTO EXISTING ACCOUNT, 
								CREATE AN ACCOUNT, 
								OR USE GUEST ACCESS. */
		JButton loginBtn = new JButton("Account Login");
		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				LibraryAccount= new LibraryDatabase();
				JFrame jsd = new JFrame("Account Login");
				jsd.setVisible(true);
				//jsd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				jsd.setBounds(100, 100, 280, 250);
				GridBagLayout gbl_contentPane = new GridBagLayout();
				gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
				gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
				gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
				gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
				jsd.getContentPane().setLayout(gbl_contentPane);	
				
				//USERNAME FIELD
				JLabel lblNewLabel = new JLabel("Username");
				GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
				gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
				gbc_lblNewLabel.insets = new Insets(5, 10, 5, 5);
				gbc_lblNewLabel.gridx = 4;
				gbc_lblNewLabel.gridy = 1;
				jsd.getContentPane().add(lblNewLabel, gbc_lblNewLabel);
				
				textField = new JTextField();
				GridBagConstraints gbc_textField = new GridBagConstraints();
				gbc_textField.insets = new Insets(5, 0, 0, 20);
				gbc_textField.fill = GridBagConstraints.HORIZONTAL;
				gbc_textField.gridx = 5;
				gbc_textField.gridy = 1;
				jsd.getContentPane().add(textField, gbc_textField);
				textField.setColumns(10);
				
				//PASSWORD FIELD: USES JPASSWORDFIELD TO CENSOR PASSWORD ENTRY
				JLabel lblNewLabel_1 = new JLabel("Password");
				GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
				gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
				gbc_lblNewLabel_1.insets = new Insets(5, 10, 5, 5);
				gbc_lblNewLabel_1.gridx = 4;
				gbc_lblNewLabel_1.gridy = 2;
				jsd.getContentPane().add(lblNewLabel_1, gbc_lblNewLabel_1);
				
				JPasswordField textFields = new JPasswordField();
				GridBagConstraints gbc_textFields = new GridBagConstraints();
				gbc_textFields.insets = new Insets(5, 0, 0, 20);
				gbc_textFields.fill = GridBagConstraints.HORIZONTAL;
				gbc_textFields.gridx = 5;
				gbc_textFields.gridy = 2;
				jsd.getContentPane().add(textFields, gbc_textFields);
				textFields.setColumns(10);
				
				//GUEST LOGIN BUTTON: GRANTS GUEST ACCESS
				JButton guestBtn = new JButton("Guest Login");
				guestBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e)
					{
						LibraryDatabase reset= new LibraryDatabase();
						
						JOptionPane.showMessageDialog(null, "Guest Access granted..");
						frmQueensLibraryDb.setTitle("Queens Library DB (Guest Mode)");
						enable=0;
						offlineSearchbtn.setEnabled(false);
						checkDB.setEnabled(false);
						userDB="Output.txt";
						currentUser="Guest";
						try {
							reset.setOutputFileOneTime(userDB, currentUser);
							reset.endProgram();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						jsd.dispose();
						
					}
				});
				GridBagConstraints gbc_guestBtn = new GridBagConstraints();
				gbc_guestBtn.insets = new Insets(5, 10, 5, 55);
				gbc_guestBtn.gridx = 5;
				gbc_guestBtn.gridy = 5;
				jsd.getContentPane().add(guestBtn, gbc_guestBtn);
				
				
				//LOGIN BUTTON: LOGIN TO A USER ACCOUNT
				JButton loginBtn = new JButton("Login");
				loginBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					
							try {
								if(LibraryAccount.loginAcc(textField.getText(), textFields.getText()))
								{
									JOptionPane.showMessageDialog(null, "Login succesful..");
									LibraryDatabase reset= new LibraryDatabase();
									enable=1;
									if(textField.getText().equalsIgnoreCase("admin"))
									{	userDB="DB.txt";
									frmQueensLibraryDb.setTitle("Queens Library DB (Admin Mode)");
									}
									else
									{userDB="UserDB\\"+textField.getText()+"DB.txt";
									frmQueensLibraryDb.setTitle("Queens Library DB (User: "+textField.getText()+")");
									}
									
									System.out.println(userDB);
									offlineSearchbtn.setEnabled(true);
									checkDB.setEnabled(true);	
									currentUser=textField.getText();
									try {
										reset.setOutputFileOneTime("Output.txt", currentUser);
										reset.endProgram();
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									jsd.dispose();
									
								}
								else
								{
									JOptionPane.showMessageDialog(null, "Login failed..");
								}
								
							} catch (HeadlessException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						
						
					}
				});
				GridBagConstraints gbc_loginBtn = new GridBagConstraints();
				gbc_loginBtn.insets = new Insets(5, 10, 0, 55);
				gbc_loginBtn.gridx = 5;
				gbc_loginBtn.gridy = 3;
				jsd.getContentPane().add(loginBtn, gbc_loginBtn);
				
				//REGISTER BUTTON: CREATE NEW USER ACCOUNT
				JButton creatAccntBtn = new JButton("Create Account");
				creatAccntBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e)
					{
						JFrame jsd = new JFrame("Create User Account");
						jsd.setVisible(true);
						//jsd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						jsd.setBounds(100, 100, 300, 250);
						GridBagLayout gbl_contentPane = new GridBagLayout();
						gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
						gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
						gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
						gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
						jsd.getContentPane().setLayout(gbl_contentPane);
						
						JLabel lblNewLabel = new JLabel("Username");
						GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
						gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
						gbc_lblNewLabel.insets = new Insets(5, 10, 5, 5);
						gbc_lblNewLabel.gridx = 4;
						gbc_lblNewLabel.gridy = 1;
						jsd.getContentPane().add(lblNewLabel, gbc_lblNewLabel);
						
						textField = new JTextField();
						GridBagConstraints gbc_textField = new GridBagConstraints();
						gbc_textField.insets = new Insets(5, 0, 0, 20);
						gbc_textField.fill = GridBagConstraints.HORIZONTAL;
						gbc_textField.gridx = 5;
						gbc_textField.gridy = 1;
						jsd.getContentPane().add(textField, gbc_textField);
						textField.setColumns(10);
						
						JLabel lblNewLabel_1 = new JLabel("Password");
						GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
						gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
						gbc_lblNewLabel_1.insets = new Insets(5, 10, 5, 5);
						gbc_lblNewLabel_1.gridx = 4;
						gbc_lblNewLabel_1.gridy = 2;
						jsd.getContentPane().add(lblNewLabel_1, gbc_lblNewLabel_1);
						
						JPasswordField textFields = new JPasswordField();
						GridBagConstraints gbc_textFields = new GridBagConstraints();
						gbc_textFields.insets = new Insets(5, 0, 0, 20);
						gbc_textFields.fill = GridBagConstraints.HORIZONTAL;
						gbc_textFields.gridx = 5;
						gbc_textFields.gridy = 2;
						jsd.getContentPane().add(textFields, gbc_textFields);
						textFields.setColumns(10);
						
						JLabel lblNewLabel_2 = new JLabel("Confirm PW");
						GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
						gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
						gbc_lblNewLabel_2.insets = new Insets(5, 10, 5, 5);
						gbc_lblNewLabel_2.gridx = 4;
						gbc_lblNewLabel_2.gridy = 3;
						jsd.getContentPane().add(lblNewLabel_2, gbc_lblNewLabel_2);
						
						JPasswordField textFields2 = new JPasswordField();
						GridBagConstraints gbc_textFields2 = new GridBagConstraints();
						gbc_textFields2.insets = new Insets(5, 0, 0, 20);
						gbc_textFields2.fill = GridBagConstraints.HORIZONTAL;
						gbc_textFields2.gridx = 5;
						gbc_textFields2.gridy = 3;
						jsd.getContentPane().add(textFields2, gbc_textFields2);
						textFields2.setColumns(10);
						
						/*CALLS DATABASE USER CREATION METHOD: RETRIEVE USERNAME FROM USER TEXTFIELD
						 									   RETRIEVE PASSWORD FROM JPASSWORDFIELD AFTER PASSING CONFIRMATION
						 									   	*/
						JButton registerBtn = new JButton("Create Account");
						registerBtn.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e)
							{
								//CHECK FOR USERNAME AVAILABILITY
								if(textField.getText().equals("")||textField.getText().contains(" "))
								{JOptionPane.showMessageDialog(null, "Invalid Username: Username must not contain blank spaces");
								return;
								}
								
								else if(textField.getText().equalsIgnoreCase("admin"))
								{JOptionPane.showMessageDialog(null, "Invalid Username: Username not allowed");
								return;
								}
								
								//PASSWORD CONFIRMATION: CHECK IF PASSWORD AND CONFIRM PASSWORD MATCH
								if(textFields.getText().equals(textFields2.getText()))
								{
									try {
										if(LibraryAccount.createAcc(textField.getText(), textFields.getText()))
										{
										JOptionPane.showMessageDialog(null, "SUCCESS: User Account created..");
										jsd.dispose();
										}
										else
										JOptionPane.showMessageDialog(null, "Error: Username not available..");
									} catch (HeadlessException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									return;
								}
								else
								    JOptionPane.showMessageDialog(null, "Error: password confirmation does not match..");
								return;
								
							}

						});
						GridBagConstraints gbc_registernBtn = new GridBagConstraints();
						gbc_registernBtn.insets = new Insets(5, 10, 5, 55);
						gbc_registernBtn.gridx = 5;
						gbc_registernBtn.gridy = 4;
						jsd.getContentPane().add(registerBtn, gbc_registernBtn);

						JButton exitbtn2 = new JButton("Exit");
						exitbtn2.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								jsd.dispose();
								
							}});
						GridBagConstraints gbc_exitbtn2 = new GridBagConstraints();
						gbc_exitbtn2.insets = new Insets(0, 10, 5, 55);
						gbc_exitbtn2.gridx = 5;
						gbc_exitbtn2.gridy = 7;
						jsd.getContentPane().add(exitbtn2, gbc_exitbtn2);
					}});
				GridBagConstraints gbc_creatAccntBtn = new GridBagConstraints();
				gbc_creatAccntBtn.insets = new Insets(0, 10, 5, 55);
				gbc_creatAccntBtn.gridx = 5;
				gbc_creatAccntBtn.gridy = 6;
				jsd.getContentPane().add(creatAccntBtn, gbc_creatAccntBtn);
	
				//EXIT BUTTON
				JButton exitbtn2 = new JButton("Exit");
				exitbtn2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						jsd.dispose();
						
					}});
				GridBagConstraints gbc_exitbtn2 = new GridBagConstraints();
				gbc_exitbtn2.insets = new Insets(0, 10, 5, 55);
				gbc_exitbtn2.gridx = 5;
				gbc_exitbtn2.gridy = 7;
				jsd.getContentPane().add(exitbtn2, gbc_exitbtn2);
			}
			
		});
		loginBtn.setFocusPainted(false);
		loginBtn.setContentAreaFilled(false);
		GridBagConstraints gbc_loginBtn = new GridBagConstraints();
		gbc_loginBtn.insets = new Insets(0, 0, 20, 5);
		gbc_loginBtn.gridx = 5;
		gbc_loginBtn.gridy = 6;
		frmQueensLibraryDb.getContentPane().add(loginBtn, gbc_loginBtn);

	}
	   /*RETRIEVE FILE DIRECTORY OF DATABASE ITEMS CORRESPONDING SAVED IMAGE*/
	   private String getHtml(String name) {
		   // boolean isImg;
		    String hold;
		    Image image = new ImageIcon("IMG//"+name+".jpg").getImage();
		    if(image.getWidth(null) == -1){
		    	hold="file:/"+System.getProperty("user.dir")+"\\IMG\\"+name+".png";
		    }
		    else{
		    	hold="file:/"+System.getProperty("user.dir")+"\\IMG\\"+name+".jpg";
		    }

		   String html
	        = "<html><body>"
	        + "<img src='"+hold  
	        + "' width=200 height=200></body></html>";
	        //System.out.println(hold);
	        return html;

	    }
}


