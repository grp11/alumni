package id.ac.sgu.testing;

import id.ac.sgu.utility.database.BoxDB;
import id.ac.sgu.utility.database.PostgresDB;
import id.ac.sgu.utility.security.HashValues;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseSelectTesting {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		PostgresDB pdb = new PostgresDB();

	//	pdb.printResourceProperty();

		String password  = "pwPassword";
		String salt = "iasuQins?qw[AQLq,w.";
		
		System.out.println("Password: " + password);
		System.out.println("Password Length: " + password.length());

		System.out.println("Salt: " + salt);
		System.out.println("Salt Length: " + salt.length());
		
		String combination = password + salt;
		HashValues hv = new HashValues();
		String hashValue = hv.hashValue(combination);
		
		
		
		pdb.getConnection();
		
		
		BoxDB boxDB = new BoxDB();
		
		try {
			boxDB.setTable("users_auth");
			
			boxDB.addColumn("user_name");
			
			boxDB.addColumn("user_password");

			boxDB.addColumn("salt_hash");

			boxDB.addCondition(0);
			
			boxDB.addCondition("user_name", BoxDB.EQUALS, "admin");
		
//			boxDB.addCondition("user_password", BoxDB.EQUALS, hashValue );
						
		//	System.out.println("SELECT ++ " + boxDB.finalizeSQL(BoxDB.SELECT));
		
			
			
			
			ResultSet res = pdb.returnedSingleQuery(boxDB);
			
			
	//		System.out.println("res: " +pdb.querySize(boxDB));
			
			
			
			if (res.next())
			System.out.println("Password: " + res.getString("salt_hash"));
/*			
			if (res != null) {
				while (res.next()) {
					System.out.println("Password: " + pdb.returnedSingleQuery(boxDB).getString("user_password"));
					
				}
				
			}else
*/	//		System.out.println("isNull");

			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		catch(Exception e) {
			
			e.printStackTrace();
			
		}
		finally{
			try {
				pdb.closeStatement();
				pdb.closeResultSet();
				pdb.closeConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	
		System.out.println("isConnected: " + pdb.getIsConnected());

	}
}
