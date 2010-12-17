package id.ac.sgu.testing;

import id.ac.sgu.utility.database.BoxDB;
import id.ac.sgu.utility.database.PostgresDB;

import java.sql.SQLException;
import java.util.HashMap;

public class DatabaseTesting {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		PostgresDB pdb = new PostgresDB();

	//	pdb.printResourceProperty();

		pdb.getConnection();
		
		
		BoxDB boxDB = new BoxDB();
		
		try {

			boxDB.setTable("department");

			
			boxDB.addColumn("department_name", "Life Science");
			boxDB.addColumn("department_alias", "LS");
			boxDB.addColumn("created", "now()");
			boxDB.addColumn("modified", "now()");
						
		/*	boxDB.addCondition(0);
					
			boxDB.addCondition("tfUsername", BoxDB.EQUALS, "admin");
			boxDB.addConditionConjunction(BoxDB.AND);
			boxDB.addCondition("tfUsername", BoxDB.EQUALS, "admin");
			boxDB.addConditionConjunction(BoxDB.AND);
			boxDB.addCondition("tfUsername", BoxDB.EQUALS, "admin");
			boxDB.addConditionConjunction(BoxDB.AND);
			boxDB.addCondition("tfUsername", BoxDB.EQUALS, "admin");
			boxDB.addConditionConjunction(BoxDB.AND);
			boxDB.addCondition("tfUsername", BoxDB.EQUALS, "admin");
			boxDB.addConditionConjunction(BoxDB.AND);
			boxDB.addCondition("tfUsername", BoxDB.EQUALS, "admin");
			*/
				
			
			
			
			System.out.println("sql: " + 	pdb.insert(boxDB));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try {
				pdb.closeConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("isConnected: " + pdb.getIsConnected());
		}
		
	}

	
	public void testHashMap() {
		
		HashMap<String, String> test = new HashMap();

		test.put("abc", "123");
		test.put("abc1", "124");
		test.put("abc2", "125");
		test.put("abc3", "126");
		
		
		for (int i = 0 ; i < test.size(); i++) {
			System.out.println("key: " + test.keySet());
			System.out.println("value: " + test.values());
			
		}
		
		
	}
	
}
