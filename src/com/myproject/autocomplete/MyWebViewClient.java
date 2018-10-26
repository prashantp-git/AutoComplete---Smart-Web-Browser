/*
 *-------------------------------------------------------------------------------------
 * CLASS - MYWEBVIEWCLIENT		 AUTOCOMPLETE BROWSER				DATE - 18/09/2015 
 *-------------------------------------------------------------------------------------						
 * THIS CLASS EXTENDS WEBVIEWCLIENT TO INHERIT THE REQUIRED METHODS FOR LOADING WEBPAGES 
 * AND PROCESSING THEM. 
 *  
 */

package com.myproject.autocomplete;
import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebViewClient extends WebViewClient {
	
	/*
	 * This method is called once page is started loading.
	 *  
	 */

	 @Override
     public void onPageStarted(WebView view, String url, Bitmap favicon) {
         super.onPageStarted(view, url, favicon);
         Log.d("onPageStarted", url);
     }
	 
	 /*
	  * This method overrides loading of web pages from native browser to this application.
	  * When set to return true, webview takes the control of urls that are to be loaded 
	  * in the current WebView instead of native browser taking control.
	  *  
	  * */
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {    
        view.loadUrl(url);
        //Log.d("shouldOverrideUrlLoading", webUrl);
        return true;
    }
  
    /*
     *  This method is called once page has finished loading. In this method, javascript
     *  is ran to find out input fields in the page and to fill them up.  
     * 
     * */
    @Override
    public void onPageFinished(WebView view, String url) {
    	super.onPageFinished(view, url);
    	Log.d("onPageFinished", "In onPageFinished");
    	
        
    	//view.loadUrl("javascript:window.HTMLOUT.processHTML(document.getElementsByTagName('html')[0].innerHTML);");
    	
    	
    	/* To run JS, 'javascript:' is placed before the script. Our JS interface is named as HTMLOUT and it contains processHTML 
    	 * method which processes web page to find out input fields and do other things. Using window object, methods of JS interface
    	 * can be executed. Same is done here. Here HTML code of the current loaded web page is passed as argument to the method.
    	 * As our method (processHTML) returns some result, it is executed/passed as argument to alert method which listens for 
    	 * alerts (result) from the method. Alert method then calls onJsAlert method of WebChromeClient overrode in BrowserWebview class */
    	view.loadUrl("javascript:alert(window.HTMLOUT.processHTML(document.getElementsByTagName('html')[0].innerHTML))");
    	
    	
    	//view.loadUrl("javascript:document.getElementById('FirstName').value ='Prashant';void(0);");
    }
    
    
    
}