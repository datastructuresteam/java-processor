package util;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public final class StringUtil {
	private StringUtil() {
		throw new AssertionError("utility classes should not be instantiated");
	}

	public static boolean isInteger(String value) {
		Objects.requireNonNull(value, "value is null");

		int length = value.length();

		for (int i = 0; i < length; i++) {
			if (!Character.isDigit(value.charAt(i))) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Tokenizes the given string by whitespace.<br>
	 * <br>
	 * For example<br>
	 * <br>
	 * <code>"The quick brown fox jumped the fence"</code><br>
	 * <br>
	 * returns the list <code>
	 * ["The", "quick", "brown", "fox", "jumped", "the", "fence"]
	 * </code>
	 * 
	 * @param line the line to tokenize (must be non-null).
	 * @return a list of tokens.
	 */
	public static List<String> tokenize(String line) {
		Objects.requireNonNull(line, "can not tokenize a null String object");

		List<String> tokens = new LinkedList<String>();
		int beginIndex, endIndex, i = 0;
		int lineLength = line.length();

		while (i < lineLength) {
			beginIndex = i;

			// find all non-whitespace characters
			while (i < lineLength && !Character.isWhitespace(line.charAt(i))) {
				i++;
			}

			endIndex = i;
			tokens.add(line.substring(beginIndex, endIndex));

			// now skip over all whitespaces
			while (i < lineLength && Character.isWhitespace(line.charAt(i))) {
				i++;
			}
		}

		return tokens;
	}

}
