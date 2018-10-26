/*
 *---------------------------------------------------------------------------------------
 * CLASS - MYJAVASCRIPTINTERFACE    AUTOCOMPLETE BROWSER				DATE - 18/09/2015 
 *---------------------------------------------------------------------------------------					
 * THIS CLASS CONTAINS METHODS WHICH CAN BE USED IN JAVASCRIPT CODE. THIS CLASS IS SET AS
 * JAVASCRIPT INTERFACE FOR WEBVIEW. TO MAKE AVAILABLE THE METHODS TO JAVASCRIPT CODE, 
 * THEY MUST BE FOLLOWED BY ANNOTATION '@JavascriptInterface'. IN JAVASCRIPT CODE, METHODS 
 * THEN CAN BE CALLED USING WINDOW OBJECT. 
 *  
 */


package com.myproject.autocomplete;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;
import android.webkit.JavascriptInterface;

public class MyJavaScriptInterface {
	private String formFillScript;
	
	
	/*
	 * This method processes the HTML code of current loaded web page to find the input fields.
	 * HTML code is passed as string argument to this method. After the input fields are identified,
	 * they are filled with appropriate information (profile values).
	 * 
	 * */
	@JavascriptInterface
    public String processHTML(String html)
    {
		Log.d("MyJavaScriptInterface","Process Html");
		Document mDoc=Jsoup.parse(html);           /* Parse method converts string HTML code into a document*/
	    Elements elements = mDoc.getElementsByTag("input");  /* Get those elements whose tag is 'input' in HTML
	     														Those are the input fields */
	    String inputID;
	    String inputName;
	    String inputVal;
	    formFillScript="javascript:";					/* formFillScript will contain the JS to fill up the input fields*/
	    for (Element element : elements) {  			/* Iterate through all the input elements*/
	    	inputID=element.attr("id");					/* Get the 'id' of input elements*/
	    	inputName=element.attr("name");				/* Get the 'name' of input elements*/
	    	Log.d("htmlParser", "inputID : "+inputID+" inputName : "+inputName);
	    	
	    	if(inputID.isEmpty() && inputName.isEmpty())
	    	{
	    		continue;
	    	}
	    	
	    	else if(inputID.isEmpty() && !inputName.isEmpty())
	    	{
	    		inputVal=checkAttrib(inputName);
	    		if(!inputVal.equals("NULL"))
	    		{
	    			/* This is JS code to fill the input field using getElementsByName method  */
	    			formFillScript+="document.getElementsByName('"+inputName+"')[0].value ='"+inputVal+"';void(0);";
	    		}	    		
	    	}
	    	
	    	else if(!inputID.isEmpty())
	    	{
	    		inputVal=checkAttrib(inputID);
	    		if(!inputVal.equals("NULL"))
	    		{
	    			formFillScript+="document.getElementById('"+inputID+"').value ='"+inputVal+"';void(0);";
	    		}
	    	}
	    }
	    //Log.d("formFillScript", formFillScript);
	    return formFillScript;					/* Return JS code (formFillScript) */
    }
	
	
	/*
	 * This method checks the type of input field (firstname, lastname, username) by comparing it with  the attributes
	 * from profile file and then get the respective profile value. Input ID and Input Name are the two arguments passed
	 * to this method. This method returns the profile value if a match is found, else returns 'NULL'.
	 * 
	 * */
	
	public String checkAttrib(String inputAttr)
	{	
		String resultVal="";
	 for(ValuesArrayList list:BrowserWebview.profileParam)
	 {
		 if(inputAttr.matches(list.getRegex()))
		 {
			 	Log.d("Match Found",list.getAttr()+" : " +list.getVal());
			 	resultVal = list.getVal();
			 	break;
		 }
	 }
	 if(resultVal.isEmpty())	/* If no match is found resultVal will be empty, so return 'NULL' */
	 {
		 return "NULL";
	 }
	 return resultVal;			/* Return resultVal */
	}
}


