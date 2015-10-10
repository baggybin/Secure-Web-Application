/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprise.jsf_jpa_war;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Jonathan
 * 
 * Class for Hashing a password
 * does not use a SALT
 */
public class hashPass{

    // defaut constructor
     void hashpass(){}
 
     // not using a salt, which would be much better
     public String start(String pass) throws NoSuchAlgorithmException
     {
    	String password = pass;  
        // use SHA 256
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        // get byte data
        md.update(password.getBytes());
        byte byteData[] = md.digest();
        //convert the byte to hex format method 2
        StringBuffer hexString = new StringBuffer();
        
        // sue byte data to alter the password and convert to hash
    	for (int i=0;i<byteData.length;i++) {
    		String hex=Integer.toHexString(0xff & byteData[i]);
   	     	if(hex.length()==1) hexString.append('0');
   	     	hexString.append(hex);
    	}
        return hexString.toString();
    }
}



