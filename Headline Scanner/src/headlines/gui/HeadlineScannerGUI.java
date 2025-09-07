package headlines.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.*;

public class HeadlineScannerGUI {
	private JFrame mainFrame;
	private ActionListener listener;
	private CardLayout cd = new CardLayout();
	private JPanel screens = new JPanel(cd);
	
	private static final Font FONT_OBJ = new Font("Arial", Font.PLAIN, 30);

	public HeadlineScannerGUI() {
		mainFrame = new JFrame("Today's News");
		setupFrame();
	}

	private void setupFrame() {
		screens.add(setupHomeView(), "home");
		screens.add(setupHistoryView(), "history");
		screens.add(setupHelpView(), "help");
		screens.add(setupResultsView(), "results");
		
		mainFrame.add(screens, BorderLayout.CENTER);

		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cd.show(screens, "home");
		mainFrame.pack();
		mainFrame.setVisible(true);
	}
	
	private JPanel setupHomeView() {
		JPanel home = new JPanel();
		
		// home JPanel set to BoxLayout
		home.setLayout(new BorderLayout());
		
		//Search bar on top
		JPanel searchPane = new JPanel();
		searchPane.setLayout(new BoxLayout(searchPane, BoxLayout.PAGE_AXIS));
		searchPane.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		
		searchPane.add(Box.createVerticalGlue());
		
		ImageIcon img = new ImageIcon("src/headlines/resources/newspaper.png");
		
		JLabel imageLabel = new JLabel(img);
		imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		searchPane.add(imageLabel);
		
		searchPane.add(Box.createRigidArea(new Dimension(0, 15)));
		
		JTextField search = new JTextField("Search for keywords...");
		search.setMaximumSize(new Dimension(600, 40));
		search.setActionCommand("search");
		search.setFont(FONT_OBJ);
		search.setAlignmentX(Component.CENTER_ALIGNMENT);
		searchPane.add(search);
		
		searchPane.add(Box.createVerticalGlue());
		home.add(searchPane, BorderLayout.CENTER);

		//JPanel in left-right boxlayout for buttons
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.X_AXIS));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
		
		buttonPane.add(Box.createHorizontalGlue());
		
		JButton history = new JButton("Search History");
		history.setActionCommand("history");
		history.setFont(FONT_OBJ);
		buttonPane.add(history);
		
		buttonPane.add(Box.createRigidArea(new Dimension(15, 0)));

		JButton help = new JButton("Usage Instructions");
		help.setActionCommand("help");
		help.setFont(FONT_OBJ);
		buttonPane.add(help);
		
		buttonPane.add(Box.createHorizontalGlue());
		
		home.add(buttonPane, BorderLayout.PAGE_END);
		
		return home;
	}
	
	private JPanel setupResultsView() {
		JPanel results = new JPanel(new BorderLayout());

		// history pane top-bottom as well
		JPanel resultPane = new JPanel();
		resultPane.setLayout(new BoxLayout(resultPane, BoxLayout.PAGE_AXIS));
		resultPane.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		
		JLabel title = new JLabel("Headlines Results");
		title.setFont(FONT_OBJ);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		resultPane.add(title);
		
		resultPane.add(Box.createRigidArea(new Dimension(0, 15)));
		
		JList<String> headlines = new JList<>();
		headlines.setMaximumSize(new Dimension(1000, 500));
		headlines.setPreferredSize(new Dimension(1000, 500));
		headlines.setFont(new Font("Arial", Font.PLAIN, 18));
		resultPane.add(headlines);
		
		results.add(resultPane, BorderLayout.CENTER);
		
		// button pane
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.X_AXIS));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
		
		buttonPane.add(Box.createHorizontalGlue());
		
		JButton back = new JButton("Home");
		back.setActionCommand("back");
		back.setFont(FONT_OBJ);
		buttonPane.add(back);
		
		buttonPane.add(Box.createHorizontalGlue());
		
		results.add(buttonPane, BorderLayout.PAGE_END);
		
		return results;
	}
	
	private JPanel setupHistoryView() {
		JPanel history = new JPanel(new BorderLayout());
		
		// history pane top-bottom as well
		JPanel historyPane = new JPanel();
		historyPane.setLayout(new BoxLayout(historyPane, BoxLayout.PAGE_AXIS));
		historyPane.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		
		JLabel title = new JLabel("Search history");
		title.setFont(FONT_OBJ);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		historyPane.add(title);
		
		historyPane.add(Box.createRigidArea(new Dimension(0, 15)));
		
		JList<String> keywords = new JList<>();
		keywords.setName("history_list");
		keywords.setMaximumSize(new Dimension(1000, 500));
		keywords.setPreferredSize(new Dimension(1000, 500));
		keywords.setFont(new Font("Arial", Font.PLAIN, 18));
		historyPane.add(keywords);
		
		history.add(historyPane, BorderLayout.CENTER);
		
		// button pane left-right
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.X_AXIS));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
		
		buttonPane.add(Box.createHorizontalGlue());
		
		JButton clear = new JButton("Clear History");
		clear.setActionCommand("clear history");
		clear.setFont(FONT_OBJ);
		buttonPane.add(clear);
		
		buttonPane.add(Box.createRigidArea(new Dimension(15, 0)));
				
		JButton back = new JButton("Home");
		back.setActionCommand("back");
		back.setFont(FONT_OBJ);
		buttonPane.add(back);
		
		buttonPane.add(Box.createHorizontalGlue());
		
		history.add(buttonPane, BorderLayout.PAGE_END);
		
		return history;
	}
	
	private JPanel setupHelpView() {
		JPanel help = new JPanel(new BorderLayout());
		
		// text pane
		JPanel textPane = new JPanel();
		textPane.setLayout(new BoxLayout(textPane, BoxLayout.PAGE_AXIS));
		textPane.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		
		JLabel title = new JLabel("How to use the app");
		title.setFont(FONT_OBJ);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		textPane.add(title);
		
		textPane.add(Box.createRigidArea(new Dimension(0, 15)));
		
		String descrip = "Type a keyword in the search bar and press 'Enter' to retrieve a list of "
				+ "headlines containing the keyword.\n\n"
				+ "To search for multiple keywords, separate the keywords using the words 'OR' or 'AND'.\n\n"
				+ "Using 'OR' will retrieve any headlines containing any of the keywords.\n"
				+ "Using 'AND' will retrieve only headlines containing all of the keywords.";
		JTextArea description = new JTextArea(descrip);
		description.setEditable(false);
		description.setFont(new Font("Arial", Font.PLAIN, 18));
		description.setMaximumSize(new Dimension(1000, 500));
		description.setPreferredSize(new Dimension(1000, 500));
		textPane.add(description);
		
		help.add(textPane, BorderLayout.CENTER);
		
		// button pane
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.X_AXIS));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
		
		buttonPane.add(Box.createHorizontalGlue());
		
		JButton back = new JButton("Home");
		back.setActionCommand("back");
		back.setFont(FONT_OBJ);
		buttonPane.add(back);
		
		buttonPane.add(Box.createHorizontalGlue());
		
		help.add(buttonPane, BorderLayout.PAGE_END);
		
		return help;
	}

	public void setListener(ActionListener al) {
		listener = al;
	}

	public void addListeners() {
		ArrayList<Component> allComponents = new ArrayList<>();
		allComponents.add(mainFrame.getContentPane().getComponent(0));
		
		for (int i = 0; i < allComponents.size(); i++) {
			Component c = allComponents.get(i);
			
			if (c instanceof JPanel) {
				for (Component d : ((JPanel) c).getComponents()) {
					allComponents.add(d);
				}
			} else if (c instanceof JTextField) {
				((JTextField) c).addActionListener(listener);
			} else if (c instanceof JButton) {
				((JButton) c).addActionListener(listener);
			}
		}
	}
	
	public void displayResults(ArrayList<String> headlines) {
		
		((JList) ((JPanel) ((JPanel) screens.getComponent(3)).getComponent(0)).getComponent(2)).setListData(headlines.toArray());
		cd.show(screens, "results");
	}
	
	public void displayHistory(ArrayList<String> history) {
		// find the history list component and call .setListData(history.toArray()) on it
		((JList) ((JPanel) ((JPanel) screens.getComponent(1)).getComponent(0)).getComponent(2)).setListData(history.toArray());
		
		cd.show(screens, "history");
	}
	
	public void displayHome() {
		cd.show(screens, "home");
	}
	
	public void displayHelp() {
		cd.show(screens, "help");
	}
}
