package com.example.demo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ch {
	public static boolean checkString(String s) {
		if (s == null) {
			return false;
		} else if (s == "") {
			return false;
		}
		
		return true;
	}
	
	public static boolean checkFloat(float f) {
		if (f >= 0) {
			return true;
		}
		return false;
	}
	
	public static boolean checkEmail(String email) {
		if (email == null) {
			return false;
		} else if (email == "") {
			return false;
		}

    	String mailCheck = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
    	Pattern emailPattern = Pattern.compile(mailCheck,Pattern.CASE_INSENSITIVE);
    	Matcher match = emailPattern.matcher(email);
    
        return match.find();
    }
	
	public static boolean checkPassword(String Password) {
		
		
    	int mistakes = 0;
    	if (Password.length() > 7 && Password.length() < 255) {
    		for(int i = 0; i < Password.length(); i++) { 
                char CharOfPass = Password.charAt(i); 
                if ((CharOfPass >= 'A' && CharOfPass <= 'Z') || (CharOfPass >= 'a' && CharOfPass <= 'z') || (CharOfPass >= '0' && CharOfPass <= '9')) {
                  
                } else {
                  mistakes++; 
                }
            }
              if(mistakes > 0) {
              	return false;
              }else {
               	return true;
              }
    	} else {
    		return false;
        }
    }
}
