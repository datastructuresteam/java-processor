package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

import javax.swing.JOptionPane;

import formatter.JavaFormatter;

public class JavaProcessor {

	public static void main(String[] args) throws IOException {

		List<String> fileContent = Files.readAllLines(Paths.get("parse.txt"));

		JavaFormatter formatter = new JavaFormatter(fileContent);

		List<String> formattedContent = formatter.getFormattedContent();

		formattedContent.stream().forEach(System.out::println);

	}

	// Method to fix comments(returns ArrayList)
	public static List<String> fixComment(List<String> list) {

		ListIterator<String> itr = list.listIterator();
		int count = 0;
		String str;

		// Loop thru ArrayList elements
		while (itr.hasNext()) {
			str = list.get(count);

			// Make sure the element is not empty...otherwise do not execute this block
			if (!str.isEmpty()) {
				if (str.charAt(0) == '/' && str.charAt(1) != '/') {
					str = "/" + str;
				}

				if (str.startsWith("//") && !str.endsWith(".")) {
					str = str + ".";
				}

				list.set(count, str);
			}
			count++;

			// Break out once the size of the ArrayList has been reached to avoid
			// IndexOutOfBounds Exception
			if (count == list.size()) {
				break;
			}
		}
		return list;
	}

	public void testCommentChecker() {
		try (Scanner scan = new Scanner(System.in)) {
			List<String> list = new ArrayList<>();

			System.out.println("Please enter the file name: ");
			String filename = scan.next();

			try (Scanner fileScanner = new Scanner(new File(filename))) {
				while (fileScanner.hasNextLine()) {
					list.add(fileScanner.nextLine());
				}
			} catch (FileNotFoundException ex) {
				JOptionPane.showMessageDialog(null, "File not found");
			}
		}
	}
}
