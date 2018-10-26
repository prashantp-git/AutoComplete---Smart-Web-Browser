/*
 * ------------------------------------------------------------------------------------
 * CLASS - USERPROFILEFORM		 AUTOCOMPLETE BROWSER				DATE - 18/09/2015 
 *------------------------------------------------------------------------------------							
 * THIS IS THE MAIN ACTIVITY WHICH RUNS ON THE LAUNCH OF APP. IT IS SET TO DISPLAY A
 * PROFILE FORM IN WHICH USER CAN FILL UP HIS/HER INFORMATION. ONCE USER FILL UP THE
 * INFORMATION AND HIT THE SAVE BUTTON, USER IS DIRECTED TO THE BROWSER. USER'S DATA
 * IS STORED IN A FILE NAMED 'PROFILE.TXT' ON THE EXTERNAL STORAGE.  
 *  
 */


package com.myproject.autocomplete;

import java.io.File;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class UserProfileForm extends Activity {
	private Button mSave;
	private Context mContext;
	private String mFilename;
	private String mFilecontent;
	private String mFname;
	private String mLname;
	private String mEAddr;
	private String mUsrName;
	private String mPassword;
	private String mBday;
	private String mCity;
	private String mCountry;
	private String mPhno;
	private EditText eFname;
	private EditText eLname;
	private EditText eEAddr;
	private EditText eUsrName;
	private EditText ePassword;
	private EditText eBday;
	private EditText eCity;
	private EditText eCountry;
	private EditText ePhno;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;		/* Store the context */  
		mCheckStatus();			/* method defined below */
		mSave = (Button) findViewById(R.id.saveButton); /* Get the button from layout */
		mSave.setOnClickListener(new OnClickListener() {  /* This is the listener method for click event of the button */
			@Override
			public void onClick(View arg0) {
				Log.d("onClick()", "In onClick");
				mGetProfile();		 			/* Get and save the profile (defined below) */
				Intent mIntent = new Intent(mContext, BrowserWebview.class); /* Intent to launch browser activity */ 
				startActivity(mIntent);  		/* start browser activity */
				finish();	    				/* finish() stops this activity here itself, so when user press back button
				   				   				   from browser, it will not load this activity as a result.  */
			}
		});
		
	}

	
	/*
	 *  This method checks whether the profile.txt file already exits or not.
	 *  If profile.txt file already exists, that means user has filled his profile
	 *  earlier and the application can skip to the browser. If file does not exits,
	 *  it means that user need to fill up his/her profile and then proceed to the
	 *  browser. 
	 */
	public void mCheckStatus() {
		mFilename = "profile";
		String fpath = Environment.getExternalStorageDirectory().getPath()+"/"+mFilename + ".txt";  /* file path */ 
		File file = new File(fpath);
		if (file.exists()) {												/* if file exists */
			Log.d("mCheckSatus", "File Exists");
			setContentView(R.layout.user_profile_ui);   				   /* this is the default view (profile form) need to be set before calling browser */
			Intent mIntent = new Intent(mContext, BrowserWebview.class);   /* create intent to launch browser activity */ 
			startActivity(mIntent);		/* start browser activity */
			finish();			/* finish() stops this activity here itself, so when user press back button
								   from browser, it will not load this activity as a result.  */
		} else {
			Log.d("mCheckSatus", "File Does Not Exists");
			setContentView(R.layout.user_profile_ui);		// display profile form
		}
	}
	
	
	/*
	 *  This method is set to execute when user clicks the 'Save' button. This 
	 *  method gets the information that the user has entered into the profile
	 *  form and writes it to profile.txt
	 *    
	 */
	

	public void mGetProfile() {
		
		/* Get all the edittext from the layout */
		eFname = (EditText) findViewById(R.id.fnameEdittext);
		eLname = (EditText) findViewById(R.id.lnameEdittext);
		eEAddr = (EditText) findViewById(R.id.emailEdittext);
		eUsrName = (EditText) findViewById(R.id.usrnameEdittext);
		ePassword = (EditText) findViewById(R.id.passwordEdittext);
		eBday = (EditText) findViewById(R.id.bdayEdittext);
		eCity = (EditText) findViewById(R.id.cityEdittext);
		eCountry = (EditText) findViewById(R.id.countryEdittext);
		ePhno = (EditText) findViewById(R.id.phonenoEdittext);

		/* Get the values from edittext */
		mFname = eFname.getText().toString();
		mLname = eLname.getText().toString();
		mEAddr = eEAddr.getText().toString();
		mUsrName = eUsrName.getText().toString();
		mPassword = ePassword.getText().toString();
		mBday = eBday.getText().toString();
		mCity = eCity.getText().toString();
		mCountry = eCountry.getText().toString();
		mPhno = ePhno.getText().toString();
		
		String fullNameRegex = "(?i:.*(full.*name|name.*full|name)+.*)";
		String firstNameRegex="(?i:.*(first.*name|name.*first)+.*)";
		String lastNameRegex="(?i:.*(last.*name|name.*last)+.*)";
		String emailRegex = "(?i:.*(mail)+.*)";
		String usrNameRegex = "(?i:.*(usr.*name|user.*name|name.*usr|name.*user|login)+.*)";
		String passwordRegex= "(?i:.*(pass)+.*)";
		String bdayRegex= "(?i:.*(b.*day|dob|birth)+.*)";
		String cityRegex= "(?i:.*(city)+.*)";
		String countryRegex= "(?i:.*(country)+.*)";
		String phnoRegex= "(?i:.*(phone|mob.*|ph.*no|ph.*nu|mob.*no|mob.*nu)+.*)";
		
		mFilecontent="Firstname : "+mFname+" : "+firstNameRegex+
				"\nLastname : "+mLname+" : "+lastNameRegex+
				"\nEmailAddress : "+mEAddr+" : "+emailRegex+
				"\nUsername : "+mUsrName+" : "+usrNameRegex+
				"\nPassword : "+mPassword+" : "+passwordRegex+
				"\nBirthday : "+mBday+" : "+bdayRegex+
				"\nCity : "+mCity+" : "+cityRegex+
				"\nCountry : "+mCountry+" : "+countryRegex+
				"\nPhoneno : "+mPhno+" : "+phnoRegex;
		
		FileOperations.write(mFilename, mFilecontent); /* Write the data to the file */
		Toast.makeText(mContext, "Profile Saved", Toast.LENGTH_LONG).show(); /* Show the toast */
	}

}
