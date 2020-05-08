A. HOW THE PROGRAM WORKS:
1) Command Line Interface
	a.) Load Input file that contains the terms to be searched.
	b.) Set the output file which will contain the search result
	c.) Perform Online search using the terms found in the input file.
	d.) Print result to CLI and the outputfile
	e.) if email is provided, email the output file to that email.
2) Graphical User Interface
	a.) Guest Account: perform online search and access the account login window.
	b.) User Account: perform online search, offline search, and store online search results to user database.
			: modify/remove items stored in the database.
			: can load previously user stored items in the Main Database back to the User Database
	c.) Admin account: has same function availability as User Account.
			 : uses the Main Database (a database that stores all search items from all users)
			 : user can restored items from Main Database back to their User Database
-----------------------------------------------------------------------------------------------------
B. STARTING THE PROGRAM:
1) Place the input text file, jar files, and the java files in the same directory.
	a. The jar files will be inside the jar.zip. Unzip it and place it in the same directory as the rest.
2) Open Command Prompt and go to the directory where the files are located.
	a. You will need to use the command 'cd' to change current directory.
		e.g. 'cd Users\Me\eclipse-workspace\370\src'
3) Once you are in the desired directory, run the command ‘javac -cp “.;mail.jar;activation.jar” Java_file_Name.java’  to create the class files.
	a. Run this command for both CLI.java and GUI.java
	b. This is mandatory in order for the program to work.
4) There are two interfaces you can use to start the program:
	a. Command Line Interface: use ‘java -cp “.;mail.jar;activation.jar” CLI -i input_file -o output_file -p optional_email
		i) -i: specifies the input file that contains the key terms
		ii) -o: specifies the output file for the search results
		iii) -p: perform online search and print results to output text
			- if an email address is specified after '-p', send an email to that address with the output file.
	b. Graphical User Interface: use ‘java -cp “.;mail.jar;activation.jar” GUI
		i) Online Queries: performs online search using user-entered search term
			- Advance Searching: filter search result by type of Medium
			- Add to DB: registered user can add search results directly to their User Database.
		ii) Offline Queries: performs offline search from the User Database/Main Database.
			- Hover to Display Image: user can hover from item rows to display the corresponding item image
			- Email Reporting: upon exit, user has the option to receive output file through email.
		iii) Check Search DB: browse User Database/Main Database
			- Item removal: allow registered user to remove item from their User Database
			- Item modification: allow registered user to modify existing item from their User Database.
			- Load Backup: user can retrieve previously stored items from the Main Database into their User Database.
		iv) Account Login: performs user account log-in
			- Create account: register new user account to the system.
			- Guest Login: revert back to Guest access.
-----------------------------------------------------------------------------------------------------
C. How to add search term in input file (CLI)
1) Open input.txt file
2) Enter the word you want to add in a new line.
