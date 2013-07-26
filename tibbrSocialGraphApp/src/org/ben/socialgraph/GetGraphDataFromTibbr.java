package org.ben.socialgraph;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import java.net.URLEncoder;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


//helper class to store each tibbr user data
 class TibbrUser {	
	public String login;
	public String id;
	public String[]  idols= new String[50]; // declares and allocates memory (not init)

	public TibbrUser () {
		this.login = ""; 
		this.id = "";
		
		for (int i = 0; i < idols.length ; i++)
			  this.idols[i] = "";
	}
	
	public TibbrUser (String xlogin, String xid) {
		this.login = xlogin; 
		this.id = xid; 
		
		for (int i = 0; i < idols.length ; i++)
			  idols[i] = "";
		
	}
}

 
public class GetGraphDataFromTibbr {

	private String username;
	private String password;
	private String urlBase;
	private String auth_token=null;
	private String client_key=null;

	// declare and allocate the member variable	
	public TibbrUser[] myUsers = new TibbrUser[200]; 
	
	// contructor
	public GetGraphDataFromTibbr (String _urlbase, String _username, String _password){
			this.urlBase=_urlbase;
			this.username=_username;
			this.password=_password;
			client_key=UUID.randomUUID().toString();
			// properly initialise here:
			for (int i = 0; i < myUsers.length ; i++)
				myUsers[i] = new TibbrUser();
	}

	// logs in as specified user and sets the class member "auth_token" which is needed for all subsequent API calls
	public void loginUser() throws UnsupportedEncodingException{
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		System.out.println("URL: "+urlBase+"/a/users/login.xml");  // THIS IS A POST
		System.out.println("client_key: " + this.client_key); 
		HttpPost postRequest = new HttpPost(urlBase+"/a/users/login.xml");
		
		
		StringEntity input = new StringEntity("params[login]=" + URLEncoder.encode(this.username,"UTF-8") + 
	            							  "&params[password]=" + URLEncoder.encode(this.password,"UTF-8") + 
	            							  "&client_key=" + URLEncoder.encode(client_key,"UTF-8"));
	
		input.setContentType("application/x-www-form-urlencoded");
		postRequest.setEntity(input); // attach params to request
		
		String body;
		try {
			HttpResponse response = httpClient.execute(postRequest); // execute POST
			body = readStream(response.getEntity().getContent());
			Document user = parseXml(body);
		    Node auth_token_node = firstElementByTag(user, "auth-token");
			this.auth_token=auth_token_node.getNodeValue();
			System.out.println("auth_token: " + this.auth_token); 
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	// one time pass to get all users into array.
	public void getAllUsers(){
		
		System.out.println("Retrieving Social Graph Data from tibbr..."); 
		DefaultHttpClient httpClient = new DefaultHttpClient();
		String params = "?client_key="+this.client_key + "&auth_token="+this.auth_token;
		// ****** CHANGE THIS NUMBER TO INCREASE USERS
		params = params + "&params[per_page]=80";
		HttpGet getRequest = new HttpGet(urlBase+"/a/users/1/search_users.xml"+params);
		// set the cookie policy
		getRequest.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.IGNORE_COOKIES);
			
		
		try {
			HttpResponse response = httpClient.execute(getRequest);
			String body = readStream(response.getEntity().getContent());
		    Document tibbXML = parseXml(body);
		    getAllUserElements(tibbXML);
		    
	        
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	// this is run for each user 
	public void getIDOLS(int index){
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		

		String params = "?client_key="+this.client_key + "&auth_token="+this.auth_token;
		params = params + "&[params]user_id=" + this.myUsers[index].id + "&params[per_page]=50";
		HttpGet getRequest = new HttpGet(urlBase+"/a/users/1/idols.xml"+params);
		
		// set the cookie policy
		getRequest.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.IGNORE_COOKIES);
		
		
		System.out.println("\nAdding IDOLs for: " + this.myUsers[index].login); 
		
		try {
			HttpResponse response = httpClient.execute(getRequest);
			String body = readStream(response.getEntity().getContent());
		    Document tibbXML = parseXml(body);
		    getIdolElements(index, tibbXML);

	        
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	


	// Extra user data from XML
	void getAllUserElements(Document d){
	    NodeList nodes = d.getElementsByTagName("user");
	    TibbrUser tu1 = new TibbrUser();
	    
	    System.out.println("There are this many nodes:" + nodes.getLength());
	    for (int i = 0; i < nodes.getLength(); i++) {
	    	 
	        Element element = (Element) nodes.item(i);
	    
	        NodeList login = element.getElementsByTagName("login");
	        Element line = (Element) login.item(0);
	        String messageA = "Login name: " + line.getFirstChild().getTextContent();
	        tu1.login = line.getFirstChild().getTextContent();
	        
	        NodeList id = element.getElementsByTagName("id");
	        line = (Element) id.item(0);
	        System.out.println(messageA + " ID: " + line.getFirstChild().getTextContent());
	        tu1.id = line.getFirstChild().getTextContent();
	        
	        this.myUsers[i] = new TibbrUser (tu1.login, tu1.id);   
	    }
	    	    
	}
	
	
	// Need to get the list of IDOLS (users followed by our current user) and store into TibbrUser array.
	void getIdolElements(int indx, Document d){
	    NodeList nodes = d.getElementsByTagName("user");
	    
	    System.out.println("There are this many IDOLs: " + nodes.getLength());
	    for (int i = 0; i < nodes.getLength(); i++) {
	    	 
	        Element element = (Element) nodes.item(i);
	    
	        NodeList login = element.getElementsByTagName("login");

	        Element line = (Element) login.item(0);
	        String name = line.getFirstChild().getTextContent();
	        System.out.print(" " +name );
	        this.myUsers[indx].idols[i] =  name;
		    
	    }
	    	    
	}
	
	public String readStream(InputStream is) throws IOException {
	    BufferedReader br = new BufferedReader(new InputStreamReader(is));
	
	         StringBuffer sb = new StringBuffer();
	         String output;
	         while ((output = br.readLine()) != null) {
	             sb.append(output);
	         }   
	         return sb.toString();
	 }

	//parse XML into ??
	public Document parseXml(String xml) {
		
	    InputSource is = new InputSource(new StringReader(xml));
	    DocumentBuilderFactory builderFactory =  DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = null;
	    
	    try {
	            builder = builderFactory.newDocumentBuilder();
	    } catch (ParserConfigurationException e) {
	            e.printStackTrace();  
	    }
	
	    try {
	            return  builder.parse(is);
	    } catch (SAXException e) {
	            e.printStackTrace();
	    } catch (IOException e) {
	            e.printStackTrace();
	    }
	    return null;
	}

	public void getTibbrUserData(){
		
		
		// first get list of all users
		getAllUsers();
		
		// then for each user, grab the users that they follow...
		for (int i=0; i < this.myUsers.length; i++) {
			if (this.myUsers[i].id != "") {
				getIDOLS(i);
			}
		}
		System.out.println("------- Done getting data from tibbr--------"); 

	}

	// utility Method
	public Node firstElementByTag(Document d, String tag) {
		
	    NodeList list = d.getElementsByTagName(tag);
	    if (list == null) {
	            return null;
	    }
	    Element e = (Element)list.item(0);
	    list = e.getChildNodes();
	    if (list == null) {
	            return null;
	    }
	    return list.item(0);
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrlBase() {
		return urlBase;
	}

	public void setUrlBase(String urlBase) {
		this.urlBase = urlBase;
	}

	public String getAuth_token() {
		return auth_token;
	}

	public void setAuth_token(String auth_token) {
		this.auth_token = auth_token;
	}

	public String getClient_key() {
		return client_key;
	}

	public void setClient_key(String client_key) {
		this.client_key = client_key;
	}

}
