package database.adapters;
/**
 * A class that helps MySQL queries.
 * 
 * @see DatabaseAdapter
 * 
 * @author Tolga Özgün
 *
 */


public enum CompareType {
	
	BIGGER(">"),
	EQUAL("="),
	SMALLER("<");
	
	private String operator;

	CompareType(String operator){
		this.operator = operator;
	}
	
	public String getOperator() {
		return operator;
	}
	
}
