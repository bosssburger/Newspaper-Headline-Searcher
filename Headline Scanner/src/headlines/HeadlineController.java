package headlines;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JTextField;

import headlines.gui.HeadlineScannerGUI;

public class HeadlineController implements ActionListener {

	private HeadlineScannerGUI view;
	private HeadlineScanner model;

	public HeadlineController(HeadlineScannerGUI view, HeadlineScanner model) {
		this.view = view;
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case "history" -> fetchHistory();
			case "help" -> displayHelp();
			case "search" -> doSearch(e);
			case "back" -> goHome();
			case "clear history" -> clearHistory();
		}

	}

	private void fetchHistory() {
		ArrayList<String> searchHistory;
		
		try {
			searchHistory = model.getHistory();
		} catch (IOException e) {
			searchHistory = new ArrayList<>();
			
		}
		
		view.displayHistory(searchHistory);
	}

	private void displayHelp() {
		view.displayHelp();
	}

	private void doSearch(ActionEvent e) {
		JTextField src = (JTextField) e.getSource();
		String keywords = src.getText();
		
		ArrayList<String> headlines = model.search(keywords);
		view.displayResults(headlines);
	}
	
	private void goHome() {
		view.displayHome();
	}
	
	private void clearHistory() {
		model.clearHistory();
	}
}
