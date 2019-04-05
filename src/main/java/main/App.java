package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

import format.JavaFormatter;

public class App {

	public static void main(String[] args) throws IOException {
		App app = new App();

		app.testFormatter();
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

	public void testFormatter() throws IOException {
		List<String> fileContent = readFile("bare-minimum.txt");

		JavaFormatter formatter = new JavaFormatter(fileContent);

		formatter.format();

		formatter.printFormattedFileToConsole();
	}

	public List<String> readFile(String filename) throws IOException {
		List<String> lines = new LinkedList<String>();

		try (BufferedReader reader = Files.newBufferedReader(Paths.get(filename))) {
			String line;

			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
		}

		return lines;
	}

	// Method to fix comments(returns ArrayList)
	public static List<String> fixComment(List<String> list) {

		Iterator itr = list.listIterator();
		int count = 0;
		String str;

		// Loop thru ArrayList elements
		while (itr.hasNext()) {
			str = list.get(count);

			// Make sure the element is not empty...otherwise do not execute this block
			if (!str.isEmpty()) {
				if (str.charAt(0) == '/' && str.charAt(1) != '/')
					str = "/" + str;

				if (str.startsWith("//") && !str.endsWith("."))
					str = str + ".";

				list.set(count, str);
			}
			count++;

			// Break out once the size of the ArrayList has been reached to avoid
			// IndexOutOfBounds Exception
			if (count == list.size())
				break;
		}
		return list;
	}
}
