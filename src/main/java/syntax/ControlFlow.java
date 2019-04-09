package syntax;

public enum ControlFlow {

	WHILE("while"), DO("do"), IF("if"), ELSE_IF("else if"), ELSE("else"), FOR("for"), SWITCH("switch");

	private final String _value;

	private ControlFlow(String value) {
		_value = value;
	}

	public boolean equals(String value) {
		return _value.equalsIgnoreCase(value);
	}
	
	// TODO: create(String value) method

	@Override
	public String toString() {
		return _value;
	}
}
