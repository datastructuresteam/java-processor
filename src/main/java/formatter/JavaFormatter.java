package formatter;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import main.IntDeque;
import main.Scope;

public class JavaFormatter {

	private final IntDeque _leftCurlyDeque = new IntDeque();
	private final IntDeque _rightCurlyDeque = new IntDeque();
	private final Scope _root;
	private List<String> _formattedContent = new ArrayList<>();

	public JavaFormatter(List<String> fileContent) {
		Objects.requireNonNull(fileContent);
		_root = new Scope(0, fileContent.size());
		_formattedContent = initFileScopes(_root, insertNewLines(fileContent));
		applyIndentation(_root, _formattedContent);
	}

	public List<String> getFormattedContent() {
		return _formattedContent;
	}

	private List<String> insertNewLines(List<String> content) {
		return insertNewLines(String.join("", content));
	}

	private List<String> insertNewLines(String line) {
		List<String> file = new ArrayList<>();
		final int length = line.length();
		String temp;
		int start = 0, end = 0, lineNumber = 0;
		char c;

		for (int i = 0; i < length; i++) {
			c = line.charAt(i);
			// [A,Z] [a,z]
			// [65,90] [97,122]
			if (c == ' ' || Character.isAlphabetic((int) c)) {
				continue;
			}

			// check for left curly and right curly
			if (c == '{') {
				end = i;
				temp = line.substring(start, end).strip();
				file.add(temp);
				lineNumber++;
				_leftCurlyDeque.push(lineNumber);
				file.add(String.valueOf(c)); // {
				lineNumber++;
				start = end + 1;
			} else if (c == '}') {
				end = i;
				temp = line.substring(start, end).strip();
				if (!temp.isBlank()) {
					file.add(temp);
					lineNumber++;
				}
				file.add(String.valueOf(c)); // '}'
				_rightCurlyDeque.push(lineNumber);
				lineNumber++;
				start = end + 1;
			} else if (c == ';') {
				end = i;
				temp = line.substring(start, end + 1).strip();
				if (!temp.contains("for")) {
					// go ahead and create a newline
					file.add(temp);
					start = end + 1;
					lineNumber++;
				}
			} else if (c == '\r') {
				// new line on windows, "\r\n"
				// since we're adding lines to a list we have to
				// check if the previous character is a new line
				c = line.charAt(i - 1);
				if (c == '\n') {
					lineNumber++;
					file.add("");
				}
				// that means the next character is '\n', so skip it
				i++;
				// once again, since we're adding to a list, we're done here
				// if we were writing out to a file then
				// writer.write(newLine);
			} else if (c == '\n') {
				// new line on unix, "\n"
				lineNumber++;
				// once again, since we're adding to a list, we're done here
				// if we were writing out to a file then
				// writer.write(newLine);
			}
		} // end of for loop

		return file;
	}

	/**
	 * Scans the file and creates a "Scope Tree".
	 * 
	 * @param root        the root scope, i.e. the .java file itself.
	 * @param fileContent a list of strings that containt the file content.
	 * @return a list of indented content.
	 */
	private List<String> initFileScopes(Scope root, List<String> fileContent) {
		final List<String> file = fileContent;
		final Deque<Scope> stack = new LinkedList<>();
		final int fileLength = file.size();

		Scope currentRoot = root;
		IntDeque rcd = new IntDeque(); // right curly deque
		String lineBefore = null, line = null;
		int lineNumber = 0, index = -1;

		for (int i = 0; i < fileLength; i++) {
			line = file.get(i);
			index = line.indexOf("{");
			if (index != -1) {
				Scope scope = new Scope(lineNumber);
				stack.push(scope);
				lineBefore = file.get(lineNumber - 1);

				// this is brittle, will break under pressure ...
				if (lineBefore.contains("if") || lineBefore.contains("else if") || lineBefore.contains("else")
						|| lineBefore.contains("while") || lineBefore.contains("do")) {
					// this is a child of the current root
					currentRoot.addChild(scope);
				} else {
					currentRoot.addChild(scope);
					currentRoot = scope;
				}
				lineNumber++;
				continue;
			}

			index = line.indexOf("}");
			if (index != -1) {
				rcd.push(lineNumber);
				Scope mostRecent = stack.pop();
				mostRecent.setEnd(lineNumber);
			}

			lineNumber++;
		}

		return file;
	}

	/**
	 * Uses the depth first traversal to set the depth of each node in the tree and
	 * formats the file content. The depth will be used along with the default
	 * indention size to calculate the indention size of each line.
	 * 
	 * @param root    the root of the scope tree.
	 * @param content a list of strings that contain the file content.
	 */
	private void applyIndentation(Scope root, List<String> content) {
		// https://en.wikipedia.org/wiki/Depth-first_search#Pseudocode
		Deque<Scope> stack = new LinkedList<>();
		Scope scope = root;
		stack.push(root);
		int depth = 0;

		while (!stack.isEmpty()) {
			scope = stack.pop();
			indent(content, scope);
			if (scope.hasChildren()) {
				depth++;
				for (Scope child : scope.getChildren()) {
					child.setDepth(depth);
					stack.push(child);
				}
			}
		}
	}

	private void indent(List<String> content, Scope scope) {
		if (scope.getDepth() <= 0) {
			return;
		}
		final int indent = 4;
		int start = scope.getStart() + 1;
		int end = scope.getEnd();
		int indentSize = indent * scope.getDepth();
		String line;

		while (start < end) {
			line = content.get(start).strip();
			String formattedLine = String.format("%s%s", createPadding(indentSize), line);
			content.set(start, formattedLine);
			start++;
		}
	}

	private String createPadding(int size) {
		StringBuilder sb = new StringBuilder(size);
		for (int i = 0; i < size; i++) {
			sb.append(" ");
		}
		return sb.toString();
	}
}
