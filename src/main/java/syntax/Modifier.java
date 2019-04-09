package syntax;

import java.util.Objects;

public enum Modifier {
	
	PUBLIC("public"), PRIVATE("private"), PACKAGE("package"), PROTECTED("protected"), ABSTRACT("abstract"),
	FINAL("final"), STATIC("static");

	private final String _value;

	private Modifier(String value) {
		_value = value;
	}

	public boolean equals(String value) {
		return _value.equals(value);
	}

	public static Modifier create(String value) {
		final String str = Objects.requireNonNull(value).toLowerCase();

		switch (str) {
			case "public":
				return Modifier.PUBLIC;
			case "private":
				return Modifier.PRIVATE;
			case "package":
				return Modifier.PACKAGE;
			case "protected":
				return Modifier.PROTECTED;
			case "abstract":
				return Modifier.ABSTRACT;
			case "final":
				return Modifier.FINAL;
			case "static":
				return Modifier.STATIC;
			default:
				return null; // value is not a modifier
		}
	}

	@Override
	public String toString() {
		return _value;
	}
}
