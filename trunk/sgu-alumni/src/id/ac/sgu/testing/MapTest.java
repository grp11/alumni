package id.ac.sgu.testing;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class MapTest
{

//	private static Logger logger = Logger.getLogger(MapTest.class);

	public static void main(String[] args)
	{
		 Map<String, Map<String, List<String>>> alumniMap = new HashMap<String, Map<String,List<String>>>();
		 Map<String, List<String>> departmentMap = new HashMap<String, List<String>>();
		 departmentMap.put("Department 1", Arrays.asList(new String[] { "CROWN", "ESCAPE", "EXPEDITION",
					"EXPLORER", "F-151" }));
		 departmentMap.put("Department 2", Arrays.asList(new String[] { "CROWN", "ESCAPE", "EXPEDITION",
					"EXPLORER", "F-152" }));
		 departmentMap.put("Department 3", Arrays.asList(new String[] { "CROWN", "ESCAPE", "EXPEDITION",
					"EXPLORER", "F-153" }));
		 departmentMap.put("Department 4", Arrays.asList(new String[] { "CROWN", "ESCAPE", "EXPEDITION",
					"EXPLORER", "F-154" }));

		 alumniMap.put("2009", departmentMap);

		 System.out.println("test 1 : key set: " + alumniMap.keySet());
		 System.out.println("test 2 : get map: " + alumniMap.get("2009").keySet());



	}
}
