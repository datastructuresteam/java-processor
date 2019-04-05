package format;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class JavaFormatter {
	
	private final List<String> _fileContent;
	private List<String> _formattedContent = new LinkedList<String>();

	public JavaFormatter(List<String> fileContent) {
		_fileContent = new LinkedList<String>(Objects.requireNonNull(fileContent));
	}

	public void format() {
		List<String> formattedFile = new LinkedList<String>();
		int openCurlyIndex, closingCurlyIndex, semicolonIndex, forIndex;
		final List<String> file = _fileContent;
		boolean lineIsModified;

		// careful, ahead lies madness
		for (String line : file) {
			if (line.isBlank()) {
				continue;
			}

			do {
				lineIsModified = false;
				// returns -1 if String does NOT exist in line
				openCurlyIndex = line.indexOf("{");
				closingCurlyIndex = line.indexOf("}");
				semicolonIndex = line.indexOf(";");
				forIndex = line.indexOf("for");

				// case 1: only semicolon
				if (semicolonIndex != -1 && openCurlyIndex == -1 && closingCurlyIndex == -1 && forIndex == -1) {
					line = handleSemicolon(semicolonIndex, line, formattedFile);
					lineIsModified = true;
				}
				// case 2: only left and semicolon
				else if (forIndex == -1 && closingCurlyIndex == -1 && openCurlyIndex != -1 && semicolonIndex != -1) {
					// { int n = 1;
					if (openCurlyIndex < semicolonIndex) {
						line = handleOpenCurly(openCurlyIndex, line, formattedFile);
					}
					// int n = 1; {
					else {
						line = handleSemicolon(semicolonIndex, line, formattedFile);
					}
					lineIsModified = true;
				}
				// case 3: only right and semicolon
				else if (forIndex == -1 && openCurlyIndex == -1 && closingCurlyIndex != -1 && semicolonIndex != -1) {
					// System.out.println(); }}
					if (semicolonIndex < closingCurlyIndex) {
						line = handleSemicolon(semicolonIndex, line, formattedFile);
					}
					// } int n = 1;
					else {
						line = handleClosingCurly(closingCurlyIndex, line, formattedFile);
					}
					lineIsModified = true;
				}
				// case 4: only left
				else if (forIndex == -1 && semicolonIndex == -1 && openCurlyIndex != -1 && closingCurlyIndex == -1) {
					line = handleOpenCurly(openCurlyIndex, line, formattedFile);
					lineIsModified = true;
				}
				// case 5: only right
				else if (forIndex == -1 && semicolonIndex == -1 && openCurlyIndex == -1 && closingCurlyIndex != -1) {
					line = handleClosingCurly(closingCurlyIndex, line, formattedFile);
					lineIsModified = true;
				}
				// case 6: no for loop, has left bracket, right bracket, and semicolon
				else if (forIndex == -1 && openCurlyIndex != -1 && closingCurlyIndex != -1 && semicolonIndex != -1) {
					// (1) } int n = 1; while (true) { break; }
					if (closingCurlyIndex < semicolonIndex && semicolonIndex < openCurlyIndex) {
						line = handleClosingCurly(closingCurlyIndex, line, formattedFile);
						lineIsModified = true;
					}
					// (2) int n = 1; while (true) { break; }
					if (semicolonIndex < openCurlyIndex && openCurlyIndex < closingCurlyIndex) {
						line = handleSemicolon(semicolonIndex, line, formattedFile);
						lineIsModified = true;
					}
					// (3) while (true) { break; }
					if (openCurlyIndex < semicolonIndex && semicolonIndex < closingCurlyIndex) {
						line = handleOpenCurly(openCurlyIndex, line, formattedFile);
						lineIsModified = true;
					}
				}
				// case 7: no for loop, no semicolon, has left and right bracket
				else if (forIndex == -1 && semicolonIndex == -1 && openCurlyIndex != -1 && closingCurlyIndex != -1) {
					// (1) } public static void main(String[] args) {
					if (openCurlyIndex < closingCurlyIndex) {
						line = handleOpenCurly(openCurlyIndex, line, formattedFile);
					} else {
						line = handleClosingCurly(closingCurlyIndex, line, formattedFile);
					}
					lineIsModified = true;
				}

				// case 8: contains for loop, semicolon, left bracket, and right bracket
				else if (forIndex != -1 && semicolonIndex != -1 && openCurlyIndex != -1 && closingCurlyIndex != -1) {
					semicolonIndex = line.indexOf(";", line.indexOf(")"));
					// (1) for (int i = 0; i < arr.length; i++) { break; } OR { for (int i = 0; i <
					// arr.length; i++) { break; } }
					if (openCurlyIndex < semicolonIndex && semicolonIndex < closingCurlyIndex
							&& openCurlyIndex < closingCurlyIndex) {
						line = handleOpenCurly(openCurlyIndex, line, formattedFile);
						lineIsModified = true;
					}
					// (2) } for (int i = 0; i < arr.length; i++) { break; }
					else if (closingCurlyIndex < openCurlyIndex) {
						line = handleClosingCurly(closingCurlyIndex, line, formattedFile);
						lineIsModified = true;
					}
					// (4) int n = 1; for (int i = 0; i < arr.length; i++) { break; }
					else if (semicolonIndex < openCurlyIndex && semicolonIndex < closingCurlyIndex) {
						line = handleSemicolon(semicolonIndex, line, formattedFile);
						lineIsModified = true;
					}
				}

				if (line.isBlank()) {
					break;
				}
			} while (lineIsModified);

			if (!lineIsModified) {
				formattedFile.add(line);
			}
		}

		_formattedContent = formattedFile;
	}

	private String handleClosingCurly(int i, String line, List<String> file) {
		String str = line.substring(0, i);

		if (!str.isBlank()) {
			file.add(str);
		}

		file.add("}");

		return line.substring(i + 1, line.length());
	}

	private String handleOpenCurly(int i, String line, List<String> file) {
		String str = line.substring(0, i);

		if (!str.isBlank()) {
			file.add(str);
		}

		file.add("{");

		return line.substring(i + 1, line.length());
	}

	private String handleSemicolon(int i, String line, List<String> file) {
		file.add(line.substring(0, i + 1));

		return line.substring(i + 1, line.length()).strip();
	}

	public void printFormattedFileToConsole() {
		_formattedContent.stream().forEach(System.out::println);
	}
}
