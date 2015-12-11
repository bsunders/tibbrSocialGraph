package org.ben.socialgraph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.*;

//helper class to store each Jive user data
 class TibbrUser {	
	public String login;
	public String id;
	private final Integer maxIdols=1000;
	public String[]  idols = new String[maxIdols]; // assumes a user will follow no more than 1000 others.

	
	
	public TibbrUser (String xlogin, String xid) {
		this.login = xlogin; 
		this.id = xid; 

		for (int i = 0; i < idols.length ; i++)
			  this.idols[i] = "";
		
	}
}

 
public class GetGraphDataFromTibbr {

	public Integer numUsers = 0;
	private String username;
	private String password;
	private String urlBase;
	private String auth;
	
	
	//private final Integer max_users=5000;  // just used for max number of results from HTTP request
	//private final Integer max_users_followed=1000; // just used for max number of results from HTTP request
	public TibbrUser[] myUsers; 
	
	// contructor
	public GetGraphDataFromTibbr (String _urlbase, String _username, String _password){
			this.urlBase=_urlbase;
			this.username=_username;
			this.password=_password;
			this.auth="";
			
			//client_key=UUID.randomUUID().toString();
			
	}

	
	// one time pass to get all users into array.
	/**
	 * getAllUsers
	 */
	private void getAllUsers(){
		
		System.out.println("Retrieving Social Graph Data from tibbr..."); 
		DefaultHttpClient httpClient = new DefaultHttpClient();

		String params = "?sort=firstNameAsc&fields=name,jive.username,-resources&count=5000&startIndex=0"; 
		HttpGet getRequest = new HttpGet(urlBase+"/api/core/v3/people/@all"+params);
		

			 
		getRequest.setHeader("Authorization", "Basic " + this.auth);
		
			
		try {
			HttpResponse response = httpClient.execute(getRequest);
			String jsonOut = readStream(response.getEntity().getContent());
		    // Remove throwline if present
			jsonOut = removeThrowLine(jsonOut);
		    getAllUserElements(jsonOut);
		    
	        
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	
	private String removeThrowLine(String strResponse)
	{
		
		
		if (strResponse.contains("allowIllegalResourceCall"))
		{
			
			int posOfFirstParenthesis = strResponse.indexOf("{");
			strResponse = strResponse.substring(posOfFirstParenthesis);
			
		}
		
		
		return strResponse;
		
		
		
		
	}
	// this is run for each user 
	/**
	 * getIDOLS
	 * @param index
	 */
	private void getIDOLS(int index){
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		

		String params = "?sort=firstNameAsc&fields=name,-resources,jive.username"; 
		String url = urlBase+"/api/core/v3/people/"+this.myUsers[index].id +"/@followers"+params;
		HttpGet getRequest = new HttpGet(url);
		
		
				 
		getRequest.setHeader("Authorization", "Basic " + this.auth);
		
		
		System.out.println("\nAdding IDOLs for: " + this.myUsers[index].login); 
		
		try {
			HttpResponse response = httpClient.execute(getRequest);
			String json_output = readStream(response.getEntity().getContent());
		    // Remove throwline if present
			json_output = removeThrowLine(json_output);
		    getIdolElements(index, json_output);

	        
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	


	// Extra user data from XML
	/**
	 * getAllUserElements
	 * @param d
	 */
	private void getAllUserElements(String jsonData){
	    
		
		
			
	
		
			try {
				JSONObject obj = new JSONObject(jsonData);

				JSONArray arr = obj.getJSONArray("list");
				
				myUsers = new TibbrUser[arr.length()];
				
				for (int i = 0; i < arr.length(); i++)
				{
				    String ID = arr.getJSONObject(i).getString("id");
				    String  login = arr.getJSONObject(i).getJSONObject("jive").getString("username");
				    myUsers[i] = new TibbrUser (login, ID); 
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		
	
	    	    
	}
	
	
	// Need to get the list of IDOLS (users followed by our current user) and store into TibbrUser array.
	/**
	 * getIdolElements
	 * @param indx
	 * @param d
	 */
	private void getIdolElements(int indx, String jsonData){
	    
	       
		try {
			JSONObject obj = new JSONObject(jsonData);
			JSONArray arr = obj.getJSONArray("list");

			
			for (int i = 0; i < arr.length(); i++)
			{
			    //String ID = arr.getJSONObject(i).getString("id");
			    String login = arr.getJSONObject(i).getJSONObject("jive").getString("username");
			    this.myUsers[indx].idols[i] =  login;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    	    
	}
	
	/**
	 * readStream
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public String readStream(InputStream is) throws IOException {
	    BufferedReader br = new BufferedReader(new InputStreamReader(is));
	
	         StringBuffer sb = new StringBuffer();
	         String output;
	         while ((output = br.readLine()) != null) {
	             sb.append(output);
	         }   
	         return sb.toString();
	 }

	
	
	/**
	 * getTibbrUserData
	 * @throws InterruptedException 
	 */
	public void getTibbrUserData() throws InterruptedException{
		
		String unhashedString = this.username + ":" + this.password;
		
		// Get bytes from string
		byte[] byteArray = Base64.encodeBase64(unhashedString.getBytes());
		this.auth = new String(byteArray);
		
		// first get list of all users
		getAllUsers();
		
		// then for each user, grab the users that they follow...
		for (int i=0; i < this.myUsers.length; i++) {
			if (this.myUsers[i].id != "") {
				getIDOLS(i);
				 if ((i % 10) == 0)
					 Thread.sleep(1000);    // every 10th user, sleep for 1 second.
			}		
					
		}
		System.out.println("\n------- Done getting graph data from Jive server--------\n"); 

	}


	




}
