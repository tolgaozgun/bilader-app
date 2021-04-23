package database.adapters;

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
