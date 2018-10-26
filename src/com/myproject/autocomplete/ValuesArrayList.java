/*
 *-------------------------------------------------------------------------------------
 * CLASS - VALUESARRAYLIST		 AUTOCOMPLETE BROWSER				DATE - 18/09/2015 
 *-------------------------------------------------------------------------------------						
 * THIS CLASS CONTAINS TWO DATA MEMBERS - 
 * 1) ATTR WHICH STORES THE TYPE OF INPUT FIELD (FIRSTNAME, LASTNAME, USERNAME) 
 * 2) VAL WHICH STORES THE VALUE OF INPUT FIELD
 *  
 */


package com.myproject.autocomplete;

public class ValuesArrayList{
	private String attr;
	private String val;
	private String regex;
	
	public ValuesArrayList(String attrib, String value, String reg) {
		attr=attrib;
		val=value;
		regex=reg;
	}
	
	
	public String getAttr() {
		return attr;
	}
	
	public String getVal() {
		return val;
	}	
	
	public String getRegex()
	{
		return regex;
	}
	
}