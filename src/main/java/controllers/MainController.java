package controllers;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
<<<<<<< HEAD
import java.util.Arrays;
=======
>>>>>>> e5e10bc4f7640b4406c7c429bbb3b794153b3e96
import java.util.List;

import formatter.JavaFormatter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import parser.JavaParser;

public class MainController {

	private List<String> _inFileContent;

	private JavaParser _parser;

	private JavaFormatter _formatter;

	@FXML
	private TextArea _inputTextArea;

	@FXML
	private TextArea _outputTextArea;

	@FXML
	private TextField _inFileTxtField;

	@FXML
	private TextField _outFileTxtField;

	@FXML
	private Label _errorLabel;

	@FXML
	void handleOnSubmit(ActionEvent event) {
		String outFileName = _outFileTxtField.getText().strip();

		if (outFileName.isBlank()) {
			return;
		}
<<<<<<< HEAD
		
		if (!_inputTextArea.getText().isBlank()) {
			final String input = _inputTextArea.getText().strip();
			final String[] tokens = input.split("\n");
			_inFileContent = Arrays.asList(tokens);
		}
=======
>>>>>>> e5e10bc4f7640b4406c7c429bbb3b794153b3e96

		_parser = new JavaParser(_inFileContent);

		List<String> formattedContent = _parser.getFormattedContent();

		_formatter = new JavaFormatter(formattedContent);

		formattedContent = _formatter.getFormattedContent();

		final String output = String.join("\n", formattedContent);

		_outputTextArea.setText(output);

		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outFileName), StandardOpenOption.CREATE)) {

			writer.write(output);

		} catch (IOException e) {
			_errorLabel.setText("uh oh ... sad sad panda ...");
		}

	}

	@FXML
	void handleSearch(ActionEvent event) {

		final String fileName = _inFileTxtField.getText().strip();

		final Path filePath = Paths.get(fileName);

		try {
			_errorLabel.setText("");

			_inputTextArea.clear();

			_inFileContent = Files.readAllLines(filePath);

			_inputTextArea.setText(String.join("\n", _inFileContent));

		} catch (IOException e) {
			_inputTextArea.clear();

			String errorMessage = String.format(
					"\"%s\" does not exist.\nPlease try searching for another file or copy & paste your code in here.",
					fileName);

			_errorLabel.setText(errorMessage);
		}

	}
}
