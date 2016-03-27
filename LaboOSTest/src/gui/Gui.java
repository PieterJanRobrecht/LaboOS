package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;

import javax.swing.*;

import listeners.ListenerExport;
import data.Verwerker;
import listeners.XML1Listener;
import listeners.XML2Listener;
import listeners.XML3Listener;

public class Gui extends JFrame {
	private JFrame mijnFrame;
	private JPanel panel;
//	private JPanel cardPanel;
	private JPanel chartPanel;
	private JTextArea systemArea;
//	private ChartPanel chart;
//	private ChartPanel chartWait;
	private JPanel knoppenPanel;
	private JScrollPane systemScroll;
	private JButton xml1;
	private JButton xml2;
	private JButton xml3;
	private Verwerker verwerker;
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem menuItem;

	public Gui() {
		//Verwerker maken
		verwerker = new Verwerker();
		
		//panel waar alle gui elementen in moeten komen
		mijnFrame = new JFrame();
		mijnFrame.setTitle("Labo besturingssystemen");
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		//Text gebied voor de syso
		systemArea = new JTextArea(30,20);
		systemArea.setEditable(false);

		//Maken van de systemScroll
		systemScroll = new JScrollPane(systemArea);

		MessageConsole console = new MessageConsole(systemArea);
		console.redirectOut();
		console.redirectErr(Color.RED, null);
		
		//MenuBar maken
		menuBar = new JMenuBar();
		menu = new JMenu("Bestand");
		menu.setMnemonic(KeyEvent.VK_A);
		menuBar.add(menu);
		menuItem = new JMenuItem("Export to image", KeyEvent.VK_T);
		menuItem.addActionListener(new ListenerExport(verwerker));
		menu.add(menuItem);
		menuItem = new JMenuItem("Afsluiten", KeyEvent.VK_T);
		menuItem.addActionListener(e -> System.exit(0));
		menu.add(menuItem);
		mijnFrame.setJMenuBar(menuBar);

		//Init van chartPanel waar alle grafieken in gaan komen
		chartPanel = new JPanel();
		//chartPanel.setLayout(new BoxLayout(chartPanel,1) );
				
		//Init van cardPanel waar alles zich gedraagt als een kaartenstapel
		MyCardLayout cl = new MyCardLayout();
		JPanel cardPanelXml1 = new JPanel();
		JPanel cardPanelXml2 = new JPanel();
		JPanel cardPanelXml3 = new JPanel();
		chartPanel.setLayout(cl);
		
		//KnoppenPanel maken
		knoppenPanel = new JPanel();
		knoppenPanel.setLayout(new BoxLayout(knoppenPanel,1));
		xml1 = new JButton("Uitvoeren 10 000 processen");
		xml1.addActionListener(new XML1Listener(verwerker,cl,chartPanel,cardPanelXml1));
		xml2 = new JButton("Uitvoeren 20 000 processen");
		xml2.addActionListener(new XML2Listener(verwerker,cl,chartPanel,cardPanelXml2));
		xml3 = new JButton("Uitvoeren 50 000 processen");
		xml3.addActionListener(new XML3Listener(verwerker,cl,chartPanel,cardPanelXml3));
		knoppenPanel.add(xml1);
		knoppenPanel.add(xml2);
		knoppenPanel.add(xml3);
		knoppenPanel.add(systemScroll);
		panel.add(knoppenPanel, BorderLayout.WEST);
		
		//Toevoegen van grafieken aan panel
		panel.add(chartPanel,BorderLayout.CENTER);
		
		//Alles samen steken in 1 frame
		mijnFrame.setLayout(new BorderLayout());
		mijnFrame.add(panel,BorderLayout.CENTER);
		mijnFrame.pack();
		mijnFrame.setVisible(true);
		mijnFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}


