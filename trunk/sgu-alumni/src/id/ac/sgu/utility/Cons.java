package id.ac.sgu.utility;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * @author exodream
 * */
public class Cons {

	//private static Logger logger = Logger.getLogger(Cons.class);
	public final static PropertyResourceBundle databaseResourceBundle = (PropertyResourceBundle) ResourceBundle
	.getBundle("id.ac.sgu.utility.database.databaseConnection");

	public final static String USER_NAME = databaseResourceBundle.getString("username");
	public final static String PASSWORD = databaseResourceBundle.getString("password");
	public final static String URL = databaseResourceBundle.getString("url");
	public final static String DRIVER = databaseResourceBundle.getString("driverName");
	public final static String DB_NAME = databaseResourceBundle.getString("dbName");
	public final static String SERVER_NAME = databaseResourceBundle.getString("serverName");
	public final static String SCHEMA = databaseResourceBundle.getString("schema");

	//public final static String DB_METHOD_CONNECT = databaseResourceBundle.getString("methodConnect");
//	public final static int INTIAL_CONNECTION = Integer.parseInt(databaseResourceBundle.getString("initialConnection"));
//	public final static int MAX_CONNECTION = Integer.parseInt(databaseResourceBundle.getString("maxConnection"));

//	public final static int DATETIME = java.sql.Types.TIMESTAMP;
//	public final static int DATE = java.sql.Types.DATE;
//	public final static int TIME = java.sql.Types.TIME;

	// Already exists
	public final static int IS_EXIST = 1;
	public final static int NOT_EXIST = -1;

	// Roles
	public final static int ADMIN = 1;
	public final static int REGULAR_USER = 2;
	public final static int ANONYMOUS = 3;

	// Views
	public final static String USERS_VIEW = "users_view";
	public final static String COMMENTS_VIEW = "comments_view";
	public final static String BATCH_MAPPING_VIEW = "batch_mapping_view";
	public final static String LOCATION_VIEW = "location_view";

	public final static boolean IS_MATCH = true;
	public final static boolean NOT_MATCH = false;

	public final static String NOW = "now()";

	// Login Services
	public final static int LOGIN_SUCCESS = 11;
	public final static int LOGIN_FAILED = 10;

	//== BEGIN VALIDATION
	// Faculty Validation
	public final static int MAX_FCT_NAME_LENGTH = 30;
	public final static int MIN_FCT_NAME_LENGTH = 30;
	public final static int MAX_FCT_ALIAS_LENGTH = 30;

	// Department Validation
	public final static int MAX_DEP_NAME_LENGTH = 30;
	public final static int MIN_DEP_NAME_LENGTH = 30;
	public final static int MAX_DEP_ALIAS_LENGTH = 30;

	// Batch Validation
	public final static int MAX_BTCH_YEAR = 2050;
	public final static int MIN_BTCH_YEAR = 1990;

	// Begin User Validation
	public final static int MAX_USR_NAME_LENGTH = 30;
	public final static int MIN_USR_NAME_LENGTH = 30;

	public final static int MAX_USR_PASS_LENGTH = 30;
	public final static int MIN_USR_PASS_LENGTH = 5;

	public final static int MAX_USR_FIRST_LENGTH = 30;
	public final static int MIN_USR_FIRST_LENGTH = 5;

	public final static int MAX_USR_LAST_LENGTH = 30;
	public final static int MIN_USR_LAST_LENGTH = 0;

	public final static int MAX_YEAR = 60;
	public final static int MIN_YEAR = 20;
	// End user validation

	// Country Validation
	public final static int MAX_CTRY_NAME_LENGTH = 30;
	public final static int MIN_CTRY_NAME_LENGTH = 30;

	// City Validation
	public final static int MAX_CTY_NAME_LENGTH = 30;
	public final static int MIN_CTY_NAME_LENGTH = 3;
	//-- End validation

	//-- BEGIN BL
	// City BL
	public final static int CREATE_CITY_SUCCESS = 130;
	public final static int CREATE_CITY_FAILURE = 131;

	public final static int DELETE_CITY_SUCCESS = 133;
	public final static int DELETE_CITY_FAILURE = 134;

	// Country BL
	public final static int CREATE_CTRY_SUCCESS = 135;
	public final static int CREATE_CTRY_FAILURE = 136;

	public final static int DELETE_CTRY_SUCCESS = 137;
	public final static int DELETE_CTRY_FAILURE = 138;

	// User BL
	public final static int CREATE_USR_SUCCESS = 140;
	public final static int CREATE_USR_FAILURE = 141;

	public final static int DELETE_USR_SUCCESS = 143;
	public final static int DELETE_USR_FAILURE = 144;

	// Faculty BL
	public final static int CREATE_FCT_SUCCESS = 150;
	public final static int CREATE_FCT_FAILURE = 151;

	public final static int DELETE_FCT_SUCCESS = 153;
	public final static int DELETE_FCT_FAILURE = 154;

	// Department BL
	public final static int CREATE_DEP_SUCCESS = 150;
	public final static int CREATE_DEP_FAILURE = 151;

	public final static int UPDATE_DEP_SUCCESS = 1001;
	public final static int UPDATE_DEP_FAILURE = 1000;

	public final static int DELETE_DEP_SUCCESS = 153;
	public final static int DELETE_DEP_FAILURE = 154;

	// Batch BL
	public final static int CREATE_BATCH_SUCCESS = 155;
	public final static int CREATE_BATCH_FAILURE = 156;

	public final static int UPDATE_BATCH_SUCCESS = 1003;
	public final static int UPDATE_BATCH_FAILURE = 1004;

	public final static int DELETE_BATCH_SUCCESS = 157;
	public final static int DELETE_BATCH_FAILURE = 158;

	// Batch Mapping
	public final static int CREATE_BDF_SUCCESS = 1005;
	public final static int CREATE_BDF_FAILURE = 1006;

	public final static int UPDATE_BDF_SUCCESS = 1007;
	public final static int UPDATE_BDF_FAILURE = 1008;

	public final static int DELETE_BDF_SUCCESS = 1009;
	public final static int DELETE_BDF_FAILURE = 1010;

	// Comment BL
	public final static int POST_COMMENT_SUCCESS = 160;
	public final static int POST_COMMENT_FAILURE = 161;

	public final static int DELETE_COMMENT_SUCCESS = 163;
	public final static int DELETE_COMMENT_FAILURE = 164;
	//-- END BL


	public final static PropertyResourceBundle guiResources = (PropertyResourceBundle) ResourceBundle
	.getBundle("id.ac.sgu.utility.guiProp");

	public final static String CHOOSE = guiResources.getString("top-menu");

	public final static String ALL = guiResources.getString("allchoices");

}
