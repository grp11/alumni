package id.ac.sgu.utility.database;

import id.ac.sgu.base.BoxBean;
import id.ac.sgu.base.ConditionBean;
import id.ac.sgu.utility.Cons;

import java.util.Vector;

import org.apache.log4j.Logger;

public class BoxDB implements IBaseBox {

	private static Logger logger = Logger.getLogger(BoxDB.class);

	private Vector<BoxBean> box = null;

	private Vector<ConditionBean> clause = null;

	// Types
	public final static int BIGINT = java.sql.Types.BIGINT;

	public final static int INT = java.sql.Types.INTEGER;

	public final static int STRING = java.sql.Types.VARCHAR;

	public final static int BINARY = java.sql.Types.BINARY;

	public final static int URL = java.sql.Types.VARCHAR;

	public final static int DOUBLE = java.sql.Types.DOUBLE;

	public final static int TIMESTAMP = java.sql.Types.TIMESTAMP;

	public final static int TIME = java.sql.Types.TIME;

	public final static int TINYINT = java.sql.Types.TINYINT;

	public final static int EMAIL = java.sql.Types.VARCHAR;

	public final static int DECIMAL = java.sql.Types.DECIMAL;

	public final static int DATALINK = java.sql.Types.DATALINK;

	public final static int ARRAY = java.sql.Types.ARRAY;

	public final static int FLOAT = java.sql.Types.FLOAT;

	public final static int DISTINCT = java.sql.Types.DISTINCT;

	public final static int BIT = java.sql.Types.BIT;

	public final static int BLOB = java.sql.Types.BLOB;

	public final static int OBJECT = java.sql.Types.JAVA_OBJECT;

	public final static int BOOLEAN = java.sql.Types.BOOLEAN;

	// Conditions
	public final static String AND = " AND ";

	public final static String OR = " OR ";

	public final static String EQUALS = " = ";

	public final static String LEFT_JOIN = " LEFT JOIN ";

	public final static String JOIN = " JOIN ";

	// Commons
	private final static String _SELECT = " SELECT ";

	private final static String _DISTINCT = " DISTINCT ";

	private final static String _FROM = " FROM ";

	private final static String _WHERE = " WHERE ";

	private final static String _INSERT = " INSERT INTO ";

	private final static String _DELETE = " DELETE FROM ";

	private final static String _VALUES = " VALUES ";

	private final static String _UPDATE = " UPDATE ";

	private final static String _SET = " SET ";

	private final static String _END = ";";

	public final static int ASC = 1;

	public final static int DESC = 2;

	public final static int ORDER_BY = 3;

	// Actions
	public final static int INSERT = 1000;

	public final static int UPDATE = 1001;

	public final static int DELETE = 1002;

	public final static int SELECT = 1003;

	public static int OFFSET = -1;

	public static int LIMIT = 99;

	private StringBuffer boxCondition = null;

	private StringBuffer boxTable = null;

	private StringBuffer boxVar = null;

	private StringBuffer boxOrderBy = null;

	private StringBuffer LimitAndOffset = null;

	private final static int BOX_SIZE = 2;

	private final static int BOX_ACTION_BUFFER = 10;

	private final static int BOX_CONDITION_BUFFER = 45;

	private final static int BOX_TABLE_BUFFER = 48;

	private final static int BOX_VAR_BUFFER = 48;

	private final static int STR_MAX = 10;

	private final static String QUOTES = "\'";

	private final static String STRIPPER = ", ";

	public final static String OPEN_CLAUSE = "(";

	public final static String CLOSED_CLAUSE = ")";

	private static int clause_counter = -1;

	public BoxDB() {
		init(BOX_SIZE);
	}

	public BoxDB(int boxSize) {
		init(boxSize);
	}

	public void init(int size) {
		box = new Vector<BoxBean>(size);
		this.boxTable = new StringBuffer(BOX_TABLE_BUFFER);
		this.boxCondition = new StringBuffer(BOX_CONDITION_BUFFER);
		this.boxVar = new StringBuffer(BOX_VAR_BUFFER);
	}

	@Override
	public void clear()
	{
		clause_counter = -1;

		clearBox();
		clearBoxCondition();
		clearBoxTable();
		clearBoxVar();
		clearClause();
	}

	public void setTable(String tableName)
	{
		this.boxTable.append(Cons.SCHEMA + "." + tableName + " ");
	}

	// Where condition
	public void addCondition(int size)
	{
		clause = new Vector<ConditionBean>(size);
	}

	public synchronized void addCondition(String left, String clause, String right)
	{
		this.clause.addElement(new ConditionBean(left, clause, right));
		clause_counter++;
//		logger.debug("clause_counter in addCondition: " + clause_counter);
	}

	public synchronized void addConditionConjunction(String conjunction)
	{
//		logger.debug("clause_counter in addConditionConjuction: " + clause_counter + " | clause_size: " + clause.size());
		this.clause.get(clause_counter).setConjunction(conjunction);
	}

	private int getClauseCapacity()
	{
		return (this.clause != null) ? this.clause.capacity() : 0;
	}

	private void clearClause()
	{
		if (this.clause != null)
			this.clause.clear();
	}

	private void addBoxCondition(int index) {
	//	this.boxCondition.append(QUOTES);
		this.boxCondition.append(this.clause.get(index).getLeft());
	//	this.boxCondition.append(QUOTES);
		this.boxCondition.append(this.clause.get(index).getClause());
		this.boxCondition.append(QUOTES);
		this.boxCondition.append(this.clause.get(index).getRight());
		this.boxCondition.append(QUOTES);
	}

	private void finalizeCondition()
	{
		if (this.clause != null)
		{
			int i = 0;

			this.clause.trimToSize();

			if (getClauseCapacity() != 1)
			{
				for (; i < getClauseCapacity(); i++)
				{
					addBoxCondition(i);

					if (i != getClauseCapacity() - 1)
						this.boxCondition.append(this.clause.get(i).getConjunction());
					else
						break;
				}
				i = 0;
			}
			else
			{
				addBoxCondition(i);
			}
			clearClause();
		}
	}

	public void addColumn(String name) {
		box.addElement(new BoxBean(name, null, null));
	}

	public void addColumn(String name, String val) {
		box.addElement(new BoxBean(name, val, STRING));
	}

	public void addColumn(String name, double val) {
		box.addElement(new BoxBean(name, val, DOUBLE));
	}

	public void addColumn(String name, int val) {
		box.addElement(new BoxBean(name, val, INT));
	}

	private void unpackInsertBox() {

		this.box.trimToSize();
		this.boxVar.append(OPEN_CLAUSE);

		int i = 0;
		if (this.box.capacity() != 1)
		while (i < this.box.capacity()) {
			this.boxVar.append(this.box.get(i).getBoxName().toString());

			if (i != this.box.capacity() -1)
				this.boxVar.append(", ");
			else
				break;

			i++;
		}
		else
			this.boxVar.append(box.get(i).getBoxName().toString());

		this.boxVar.append(CLOSED_CLAUSE);
		this.boxVar.append(_VALUES);
		this.boxVar.append(OPEN_CLAUSE);

		i = 0;
		if (this.box.capacity() != 1)
		while (i < this.box.capacity()) {
			this.boxVar.append(QUOTES);
			this.boxVar.append(box.get(i).getBoxValue().toString());
			this.boxVar.append(QUOTES);

			if (i != box.capacity() -1)
				this.boxVar.append(", ");
			else
				break;

			i++;
		}
		else {
			this.boxVar.append(QUOTES);
			this.boxVar.append(box.get(i).getBoxName().toString());
			this.boxVar.append(QUOTES);
		}
		this.boxVar.append(CLOSED_CLAUSE);
		i = 0;
		this.clearBox();
	}

	private void clearBox() {
		this.box.clear();
	}

	private void clearBoxCondition() {
		this.boxCondition.delete(0, this.boxCondition.length());
	}

	private void clearBoxVar() {
		this.boxVar.delete(0, this.boxVar.length());
	}

	private void clearBoxTable() {
		this.boxTable.delete(0, this.boxTable.length());
	}

	public String finalizeSQL(int type) {

		switch (type) {
		case INSERT:
			unpackInsertBox();

		//	logger.info("INERT SQL: " + _INSERT + this.boxTable.toString() + this.boxVar.toString() + _END);

			return (_INSERT + this.boxTable.toString() + this.boxVar.toString() + _END);
		case SELECT:
			unpackSelectBox();
			finalizeCondition();

			if (clause_counter == -1) {
		//		logger.info("SELECT without CONDITION: " +_SELECT + this.boxVar.toString() + _FROM + this.boxTable.toString() + _END);

				return (_SELECT + this.boxVar.toString() + _FROM + this.boxTable.toString() + _END );
			} else {
		//		logger.info("SELECT with CONDITION: " +_SELECT + _DISTINCT + this.boxVar.toString() + _FROM + this.boxTable.toString() + _WHERE + this.boxCondition.toString() + _END);

				return (_SELECT + _DISTINCT + this.boxVar.toString() + _FROM + this.boxTable.toString() + _WHERE + this.boxCondition.toString() + _END);
			}
		case DELETE:
			finalizeCondition();

			if (clause_counter >= 0 ) {
		//		logger.info("DELETE SQL: " + _DELETE + this.boxTable.toString() + _WHERE + this.boxCondition.toString() + _END);
				return (_DELETE + this.boxTable.toString() + _WHERE + this.boxCondition.toString() + _END);
			}
		case UPDATE:
			unpackUpdateBox();
			finalizeCondition();

			if (clause_counter >= 0) {
			//	logger.info("UPDATE SQL: " + _UPDATE + this.boxTable.toString() + _SET + this.boxVar.toString() + _WHERE + this.boxCondition.toString() + _END);

				return (_UPDATE + this.boxTable.toString() + _SET + this.boxVar.toString() + _WHERE + this.boxCondition.toString() + _END);
			}
		}

		return null;
	}

	public String getLastIdFrom() {
		unpackSelectBox();
		String colId = this.boxVar.toString();

	//	logger.info("GET LAST ID: " +_SELECT + colId + _FROM + this.boxTable.toString() + " ORDER BY " +  colId + " DESC LIMIT 1 " + _END );
		return (_SELECT + colId + _FROM + this.boxTable.toString() + " ORDER BY " +  colId + " DESC LIMIT 1 " + _END );
	}

	private int getBoxVarCapacity() {
		return this.boxVar.capacity();
	}

	private void unpackSelectBox() {

		this.box.trimToSize();

//		logger.info("box capacity: " + box.capacity());

		int i = 0;
		if (this.box.capacity() != 1)
			while (i < this.box.capacity()) {
				this.boxVar.append(this.box.get(i).getBoxName().toString());
		//		logger.info("-- debug -- " + i + ": " + this.boxVar.toString());

				if (i != (this.box.capacity() - 1))
					this.boxVar.append(", ");
				else
					break;


				i++;
			}
			else
				this.boxVar.append(box.get(i).getBoxName().toString());

		i = 0;
	}

	private void unpackUpdateBox() {

		if (this.box != null) {

		this.box.trimToSize();

		int i = 0;
		if (this.box.capacity() != 1)
			while (i < this.box.capacity()) {

				this.boxVar.append(this.box.get(i).getBoxName().toString());
				this.boxVar.append(EQUALS);
				this.boxVar.append(QUOTES);
				this.boxVar.append(this.box.get(i).getBoxValue().toString());
				this.boxVar.append(QUOTES);

				if (i != this.box.capacity() - 1)
					this.boxVar.append(", ");
				else
					break;

				i++;
			}
		else  {
			this.boxVar.append(this.box.get(i).getBoxName().toString());
			this.boxVar.append(EQUALS);
			this.boxVar.append(QUOTES);
			this.boxVar.append(this.box.get(i).getBoxValue().toString());
			this.boxVar.append(QUOTES);
		}
		}
		else
			throw new NullPointerException("Null on UNPACK UPDATE BOX");
	}

	public String getOneByOneSQL() {

		logger.info("boxAction: " + boxVar.toString());
		logger.info("boxTable: " + boxTable.toString());
		logger.info("boxCondition: " + boxCondition.toString());

		return boxVar.toString() + boxTable.toString()
				+ boxCondition.toString();
	}

}
