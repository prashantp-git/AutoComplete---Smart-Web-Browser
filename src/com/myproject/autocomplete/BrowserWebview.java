/*
 *-------------------------------------------------------------------------------------
 * CLASS - BROWSERWEBVIEW		 AUTOCOMPLETE BROWSER				DATE - 18/09/2015 
 *-------------------------------------------------------------------------------------						
 * THIS IS THE ACTIVITY WHICH LAUNCHES THE BROWSER. THE LAYOUT (WEBVIEW_UI.XML) SET FOR 
 * THIS ACTIVITY CONTAINS WEBVIEW. WEBVIEW IN LAYOUT DISPLAYS THE BROWSER.
 *  
 */

package com.myproject.autocomplete;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class BrowserWebview extends Activity {
	private Context context;
	private WebView webView;  /* Instance of the class WebView */
	public static ArrayList<ValuesArrayList> profileParam; /* ArrayList of class 'ValuesArrayList' defined separately*/
	private EditText urlEditText;
	private Button searchButton;
	private Button clearButton;
	private Button goButton;
	private RelativeLayout webViewLayout;
	private Boolean isSearchButtonTranslated;
	private ImageView progressIcon;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		
		setContentView(R.layout.webview_ui);   /* Set layout webview_ui*/
		
		webViewLayout = (RelativeLayout) findViewById(R.id.webview_ui);
		
		urlEditText = (EditText) findViewById(R.id.webAddrEditText);
		urlEditText.setVisibility(View.GONE);
		
		searchButton = (Button) findViewById(R.id.searchButton);
		isSearchButtonTranslated = false;
		
		clearButton = (Button) findViewById(R.id.clearButton);
		clearButton.setVisibility(View.GONE);
		
		goButton = (Button) findViewById(R.id.goButton);
		goButton.setVisibility(View.GONE);
		
		progressIcon = (ImageView) findViewById(R.id.progressIcon);
		progressIcon.setVisibility(View.GONE);
		
		
		webView = (WebView) findViewById(R.id.webView); /* Get the WebView object from layout */
		webView.getSettings().setJavaScriptEnabled(true); /* Enable javascript */
		webView.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT");  /* Set MyJavascriptInterface class (defined separately) as 
																				    JavascriptInterface with name 'HTMLOUT' */ 
		webView.setWebViewClient(new MyWebViewClient());   /* Set MyWebViewClient class (defined separately) as WebViewClient */
		webView.setWebChromeClient(new WebChromeClient(){  /* Set WebChromeClient (defined below) */
			/*
			 * This is an overridden method of parent method 'onJsAlert' of class WebChromeClient. 
			 * This method handles the result (message) returned from javascript method 'processHTML'
			 * of class MyJavaScriptInterface. The message is again a javascript which fills up the 
			 * input fields.
			 *  
			 * */ 
			@Override
		        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
				 	Log.d("LogTag", message);
		            result.confirm();
		            if(message.equals("javascript:")) /* If the returned script contains nothing */
		            {
		            	Log.d("LogTag", "Do Nothing");
		            }
		            else				/* If the returned result contains the javascript */
		            {
		            	Log.d("LogTag", "Run JS");
		            	view.loadUrl(message);		/* Run the javascript */
		            }
		            return true;
		        }
			
			public void onProgressChanged(WebView view, int progress)   
	        {
				if(progress == 0)
				{		
					progressIcon.setVisibility(View.VISIBLE);
					final Animation progressIconAnim = AnimationUtils.loadAnimation(context, R.anim.progressicon_rotate);
					progressIcon.startAnimation(progressIconAnim);
				}
				//Log.d("progress",""+progress);
				
	            if(progress == 100)
	            {
	            	progressIcon.clearAnimation();
	            	progressIcon.setVisibility(View.GONE);
	            }
	         }
	        
			
		});
		webView.loadUrl("http://www.google.com");  /* Load this URL */
		
			
		
		searchButton.setOnClickListener(new OnClickListener() {
			
			@TargetApi(Build.VERSION_CODES.HONEYCOMB) @Override
			public void onClick(View arg0) {
				if(!isSearchButtonTranslated)
				{
					float searchButtonTranslateX = (webViewLayout.getWidth()/2)-40;
					ObjectAnimator object = ObjectAnimator.ofFloat(searchButton, "translationX", 0, -searchButtonTranslateX);
					object.setDuration(200);
					object.start();	
			
				
					Animation urlEditTextAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.urledittext_expand);
					urlEditText.setVisibility(View.VISIBLE);
					urlEditText.startAnimation(urlEditTextAnim);
					urlEditText.requestFocus();
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.showSoftInput(urlEditText, InputMethodManager.SHOW_IMPLICIT);
			
					clearButton.setVisibility(View.VISIBLE);
					
					goButton.setVisibility(View.VISIBLE);
					float goButtonTranslateX = webViewLayout.getWidth()+20;
					ObjectAnimator goObject = ObjectAnimator.ofFloat(goButton, "translationX", goButtonTranslateX, 0);
					goObject.setDuration(200);
					goObject.start();
					
					//goButton.setVisibility(View.VISIBLE);
				
					isSearchButtonTranslated = true;
				}
			}
		});
		
		
		webView.setOnTouchListener(new OnTouchListener() {
			@TargetApi(Build.VERSION_CODES.HONEYCOMB)
			@Override
			public boolean onTouch(View view, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN && isSearchButtonTranslated)
				{
	        		Animation urlEditTextAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.urledittext_collapse);
					urlEditText.startAnimation(urlEditTextAnim);
					urlEditText.setVisibility(View.GONE);
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(urlEditText.getWindowToken(), 0);
					
					
	            	ObjectAnimator object = ObjectAnimator.ofFloat(searchButton, "translationX",0);
	            	object.setDuration(200);
					object.start();	
					
					clearButton.setVisibility(View.GONE);	
					
					float goButtonTranslateX = webViewLayout.getWidth()+20;
					ObjectAnimator goObject = ObjectAnimator.ofFloat(goButton, "translationX", 0 ,goButtonTranslateX);
					goObject.setDuration(200);
					goObject.start();
					//goButton.setVisibility(View.GONE);
					
					
					isSearchButtonTranslated = false;
				}
				return false;
			}
		});
		
		goButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Log.d("BrowserWebView","goButton Clicked");
				String url = urlEditText.getText().toString();
				String httpRegex = "^(http|https)://";
				if(!url.isEmpty())
				{
					webView.loadUrl(url);
				}
				
			}
		});
		
		profileParam = new ArrayList<ValuesArrayList>();		/* Initialize the ArrayList */
		new Thread(new Runnable() {		/* This is a background thread to get the profile values
		 								   stored on External Storage in a file. This thread runs
		 								   separately from the main UI thread  */
			
			@Override
			public void run() {
				getProfile();				/* This method is executed in thread (defined below) */
			}
		}).start();
	}
	
	
	/*
	 * This method handles the back button press event. If there is a page (history) to which webview
	 * can go back, then that page is loaded on back button press. Else browser will close.
	 * 
	 * */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
			webView.goBack();
			return true;
		} else {
			//super.onBackPressed();
			System.exit(0);
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
	public void callAnimate()
	{
		//mHoloCircularProgressBar.
		
	}
	
	/*
	 * This method reads the profile data stored on external storage. The data is kept in ArrayList
	 * to check the input fields (firstname, lastname, username etc) against this ArrayList and to 
	 * fill them respectively. 
	 * 
	 * */
	public void getProfile()
	{
		BufferedReader br = null;
		String fname = "profile";
		try {
			String fpath = Environment.getExternalStorageDirectory().getPath() + "/" +fname + ".txt";  /* File Path*/
			br = new BufferedReader(new FileReader(fpath));
			String line = "";
			while ((line = br.readLine()) != null) {
				String[] split = line.split(" : ");  /* As profile is saved as 'Firstname:XYZ', 
													  attributes and their values need to be separated */
				profileParam.add(new ValuesArrayList(split[0], split[1], split[2])); /* Data is saved in ArrayList */
				//Log.d("getProfile",split[0]+" : "+split[1]+" : "+split[2]);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	}
