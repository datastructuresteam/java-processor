package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

import formatter.JavaFormatter;
import parser.JavaParser;

public class JavaProcessor {

	public static void main(String[] args) throws IOException {

		List<String> fileContent = Files.readAllLines(Paths.get("syntax-test.txt"));

		JavaFormatter formatter = new JavaFormatter(fileContent);

		List<String> formattedContent = formatter.getFormattedContent();

		JavaParser parser = new JavaParser(formattedContent);

		formattedContent = parser.getFormattedContent();

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
