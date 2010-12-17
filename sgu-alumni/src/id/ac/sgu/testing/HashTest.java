package id.ac.sgu.testing;

import id.ac.sgu.utility.security.HashValues;

public class HashTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		HashValues hv = new HashValues();
		
		String password  = "pwPassword";
		String salt = "iasuQins?qw[AQLq,w.";
		
		System.out.println("Password: " + password);
		System.out.println("Password Length: " + password.length());

		System.out.println("Salt: " + salt);
		System.out.println("Salt Length: " + salt.length());
		
		String combination = password + salt;
		
		System.out.println("Combination: " + combination);
		System.out.println("Combination Length: " + combination.length());
		
		String hashValue = hv.hashValue(combination);
		
		System.out.println("Hash: " + hashValue);
		System.out.println("Hash Length: " + hashValue.length());
		
		
	}

}
