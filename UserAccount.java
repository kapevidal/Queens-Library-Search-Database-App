/*USER ACOUNT CLASS SECTION*/
public class UserAccount
{

	    private String un; // Username value
	    private String pw; // Password value
	    
	    UserAccount(String username, String password){
	        un = username;
	        pw = password;
	    }
	    
	    public String getUsername(){ return un; } // return username
	    public String getPassword(){ return pw; } // return password

	    //check if user-entry password matches the account password.
		public Boolean verify(String user, String pass)
		{
			return(un.equals(user)&&pw.equals(pass));
		}
	    


}
