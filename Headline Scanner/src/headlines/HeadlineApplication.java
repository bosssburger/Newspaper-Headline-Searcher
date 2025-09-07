package headlines;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import headlines.gui.*;

public class HeadlineApplication{
	
	public static void launch(String[] args) {
		HeadlineScannerGUI mainGUI = new HeadlineScannerGUI();
		HeadlineScanner storage = new HeadlineScanner();
		HeadlineController control = new HeadlineController(mainGUI, storage);
		
		
		mainGUI.setListener((ActionListener) control);
		mainGUI.addListeners();
	}

	public static void main(String[] args) {
		launch(args);
	}

	
}
