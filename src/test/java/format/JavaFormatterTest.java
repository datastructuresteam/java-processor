package format;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class JavaFormatterTest
{
	@Test
	public void testFormat()
	{
		List<String> input = new ArrayList<>();
		input.add("for (int i = 0; i < arr.length; i++) { break; }");
		
		List<String> expected = new LinkedList<>();
		expected.add("for (int i = 0; i < arr.length; i++) ");
		expected.add("{");
		expected.add("break;");
		expected.add("}");
	}
}
