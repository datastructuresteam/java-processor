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

		JavaParser parser = new JavaParser(fileContent);

		List<String> formattedContent = parser.getFormattedContent();

		JavaFormatter formatter = new JavaFormatter(fileContent);

		formattedContent = formatter.getFormattedContent();

		formattedContent.stream().forEach(System.out::println);

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
