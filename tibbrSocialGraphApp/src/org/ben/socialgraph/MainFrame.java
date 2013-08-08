package org.ben.socialgraph;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.gephi.preview.api.PreviewProperty;
import org.gephi.preview.api.ProcessingTarget;

import processing.core.PApplet;

import javax.swing.JCheckBox;


public class MainFrame  {
	
	private static  ProcessingTarget  target;
	private static JTextField txtURL; 
	private static JTextField txtUserID;
	private static JPasswordField txtPassword;
	public String  txtURLvalue;
	public static String tibbr_url;
	public static String tibbr_usr;
	public static String tibbr_pwd;
	static JPanel contentPane;
	static Container contPane;
	static JFrame frame;
	static MainFrame mf;
	static JPanel controlPanel;
	static JPanel graphPane;
	public static PrintStream ps;
	private static JTextField txtUserCentric;
	private static JCheckBox chckbxEdgeCurved;
	private static HashMap<String, Object> prevProps = new HashMap<String, Object>();
	
	public MainFrame(){
		
		tibbr_url = "";
		tibbr_usr = "";
		tibbr_pwd = "";
		// just some defaults for testing
		txtURL = new JTextField("https://tibbrdemo.tibbr.com"); 
		txtUserID = new JTextField("tibbradmin");
		txtPassword = new JPasswordField("Tibbr2013");
		
	}
	
	
	public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });

	}
	
	 private static void createAndShowGUI() {
         
		 	// call constructor to setup tibbr creds.
		 	mf = new MainFrame();
		 	
	        frame = new JFrame("tibbr Social Graph");
	        //frame.setSize(new Dimension(500, 500));
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setTitle("Social Graph v1.0");

			contentPane = (JPanel) frame.getContentPane();
			contentPane.setBackground(Color.WHITE);
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPane.setLayout(new BorderLayout(0, 0));
			
	        //Set up the content pane.
	        addComponentsToPane();
	        //frame.setLocationRelativeTo(null);
	        frame.pack();
	        frame.setVisible(true);
	}
	 
	public static void addComponentsToPane(  ) {
        
		// add local panel to WEST zone of frames content pane.
		GridBagLayout gbl_controlPanel = new GridBagLayout();
		gbl_controlPanel.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0};
		controlPanel = new JPanel( gbl_controlPanel);
		contentPane.add(controlPanel, BorderLayout.WEST);
		
		//tibbr URL input
		JLabel lblTibbrUrl = new JLabel("tibbr URL:");
		
		GridBagConstraints gbc_lblTibbrUrl = new GridBagConstraints();
		gbc_lblTibbrUrl.anchor = GridBagConstraints.EAST;
		gbc_lblTibbrUrl.insets = new Insets(0, 0, 5, 5);
		gbc_lblTibbrUrl.gridx = 0;
		gbc_lblTibbrUrl.gridy = 0;
		controlPanel.add(lblTibbrUrl, gbc_lblTibbrUrl);
		
		//txtURL = new JTextField();
		GridBagConstraints gbc_txtURL = new GridBagConstraints();
		gbc_txtURL.gridwidth = 2;
		gbc_txtURL.insets = new Insets(0, 0, 5, 5);
		gbc_txtURL.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtURL.gridx = 1;
		gbc_txtURL.gridy = 0;
		controlPanel.add(txtURL, gbc_txtURL);
		txtURL.setColumns(10);
		
		
		//User ID
		JLabel lblTibbrUserid = new JLabel("tibbr UserID:");
		GridBagConstraints gbc_lblTibbrUserid = new GridBagConstraints();
		gbc_lblTibbrUserid.anchor = GridBagConstraints.EAST;
		gbc_lblTibbrUserid.insets = new Insets(0, 0, 5, 5);
		gbc_lblTibbrUserid.gridx = 0;
		gbc_lblTibbrUserid.gridy = 1;
		controlPanel.add(lblTibbrUserid, gbc_lblTibbrUserid);
		
		//txtUserID = new JTextField();
		GridBagConstraints gbc_txtUserID = new GridBagConstraints();
		gbc_txtUserID.gridwidth = 2;
		gbc_txtUserID.insets = new Insets(0, 0, 5, 5);
		gbc_txtUserID.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtUserID.gridx = 1;
		gbc_txtUserID.gridy = 1;
		controlPanel.add(txtUserID, gbc_txtUserID);
		txtUserID.setColumns(10);
		
		//password input
		JLabel lblTibbrPassword = new JLabel("tibbr Password:");
		GridBagConstraints gbc_lblTibbrPassword = new GridBagConstraints();
		gbc_lblTibbrPassword.anchor = GridBagConstraints.EAST;
		gbc_lblTibbrPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblTibbrPassword.gridx = 0;
		gbc_lblTibbrPassword.gridy = 2;
		controlPanel.add(lblTibbrPassword, gbc_lblTibbrPassword);
		
		//txtPassword = new JPasswordField();
		GridBagConstraints gbc_txtPassword = new GridBagConstraints();
		gbc_txtPassword.gridwidth = 2;
		gbc_txtPassword.insets = new Insets(0, 0, 5, 5);
		gbc_txtPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPassword.gridx = 1;
		gbc_txtPassword.gridy = 2;
		controlPanel.add(txtPassword, gbc_txtPassword);
		txtPassword.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("User-Centric Graph:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 3;
		controlPanel.add(lblNewLabel, gbc_lblNewLabel);
		
		txtUserCentric = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 2;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 3;
		controlPanel.add(txtUserCentric, gbc_textField);
		txtUserCentric.setColumns(10);
		
		 chckbxEdgeCurved = new JCheckBox("Edge Curved");
		GridBagConstraints gbc_chckbxEdgeCurved = new GridBagConstraints();
		gbc_chckbxEdgeCurved.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxEdgeCurved.gridx = 1;
		gbc_chckbxEdgeCurved.gridy = 4;
		controlPanel.add(chckbxEdgeCurved, gbc_chckbxEdgeCurved);
		
		//get graph
		JButton btnGetGraph = new JButton("Get Graph..");
		GridBagConstraints gbc_btnGetGraph = new GridBagConstraints();
		gbc_btnGetGraph.insets = new Insets(0, 0, 5, 5);
		gbc_btnGetGraph.gridx = 2;
		gbc_btnGetGraph.gridy = 5;
		controlPanel.add(btnGetGraph, gbc_btnGetGraph);
		btnGetGraph.addActionListener(mf.new HandlerClass());
	
		//---------------------------------------------------
		
		//Zoom in button
		JButton btnZoomIn = new JButton("Zoom In");
		btnZoomIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				target.zoomPlus();
			}
		});
		GridBagConstraints gbc_btnZoomIn = new GridBagConstraints();
		gbc_btnZoomIn.insets = new Insets(0, 0, 5, 5);
		gbc_btnZoomIn.gridx = 1;
		gbc_btnZoomIn.gridy = 6;
		controlPanel.add(btnZoomIn, gbc_btnZoomIn);
				
		//Zoom out button
		JButton btnNewButton_1 = new JButton("Zoom Out");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				target.zoomMinus();
			}
		});
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_1.gridx = 1;
		gbc_btnNewButton_1.gridy = 7;
		controlPanel.add(btnNewButton_1, gbc_btnNewButton_1);
		
		//Zoom Reset button
		JButton btnResetZoom = new JButton("Reset Zoom");
		btnResetZoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				target.resetZoom();
				target.zoomMinus();

			}
		});
		GridBagConstraints gbc_btnResetZoom = new GridBagConstraints();
		gbc_btnResetZoom.insets = new Insets(0, 0, 5, 5);
		gbc_btnResetZoom.gridx = 1;
		gbc_btnResetZoom.gridy = 8;
		controlPanel.add(btnResetZoom, gbc_btnResetZoom);
		
//		//Move Up button
//		JButton btnMoveUp = new JButton("Move Up");
//		btnMoveUp.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//			
//			}
//		});
//		GridBagConstraints gbc_btnMoveUp = new GridBagConstraints();
//		gbc_btnMoveUp.insets = new Insets(0, 0, 5, 5);
//		gbc_btnMoveUp.gridx = 1;
//		gbc_btnMoveUp.gridy = 8;
//		controlPanel.add(btnMoveUp, gbc_btnMoveUp);
//		
//		//Move Down button
//		JButton btnMoveDown = new JButton("Move Down");
//		btnMoveDown.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//			}
//		});
//		GridBagConstraints gbc_btnMoveDown = new GridBagConstraints();
//		gbc_btnMoveDown.insets = new Insets(0, 0, 5, 5);
//		gbc_btnMoveDown.gridx = 1;
//		gbc_btnMoveDown.gridy = 10;
//		controlPanel.add(btnMoveDown, gbc_btnMoveDown);
//		
//		//Move Left button
//		JButton btnNewButton_2 = new JButton("Move Left");
//		GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
//		gbc_btnNewButton_2.insets = new Insets(0, 0, 5, 5);
//		gbc_btnNewButton_2.gridx = 0;
//		gbc_btnNewButton_2.gridy = 9;
//		controlPanel.add(btnNewButton_2, gbc_btnNewButton_2);
//		
//		//Move RIght button
//		JButton btnMoveRight = new JButton("Move Right");
//		GridBagConstraints gbc_btnMoveRight = new GridBagConstraints();
//		gbc_btnMoveRight.insets = new Insets(0, 0, 5, 0);
//		gbc_btnMoveRight.gridx = 2;
//		gbc_btnMoveRight.gridy = 9;
//		controlPanel.add(btnMoveRight, gbc_btnMoveRight);
        
		// add pane for the graph to RHS. 
        graphPane = new JPanel();
        graphPane.setPreferredSize(new Dimension(1000, 1000));
        graphPane.setBackground(Color.GRAY);
        contentPane.add(graphPane,BorderLayout.CENTER);
        
        // setup text area for the output console
        JTextArea consoleTextArea = new JTextArea("");
        // set desired size of text area (and let layout manager work it out).
        consoleTextArea.setPreferredSize(new Dimension(400, 500));
        
        float newSize = (float) 10.0;
		Font biggerFont = consoleTextArea.getFont().deriveFont(newSize );
        
		consoleTextArea.setFont(biggerFont);
        
        // use our OutputStream helper class to write to the textarea
        TextAreaOutputStream taos = new TextAreaOutputStream( consoleTextArea, 35 ); // max lines = 100        
        ps = new PrintStream( taos );

        // reassign standard output streams to our new print stream
        System.setOut( ps );
        //System.setErr( ps );

		GridBagConstraints gbc_scrollBar = new GridBagConstraints();
		//gbc_scrollBar.gridheight = 2;
		gbc_scrollBar.gridwidth = 4;
		gbc_scrollBar.gridx = 0;
		gbc_scrollBar.gridy = 14;
		
		// add the console to a scroll window
		JScrollPane sp = new JScrollPane(consoleTextArea,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		// add the scroller (containing the console) to the control panel grid
		controlPanel.add(sp , gbc_scrollBar);

    }
	
	 private class HandlerClass implements ActionListener
	    {
	        @SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent event)
	        {
	        	
	        	tibbr_url = txtURL.getText();
	        	tibbr_usr = txtUserID.getText();
	        	tibbr_pwd = txtPassword.getText();
	        	System.out.println("--------------------------" );
	        	System.out.println("Building Graph......" );
	        	
	        	
	        	// Fill hashMap attribute based on UI
	        	getGraphSettings();
	        	// use worker thread to build and display graph
	        	displayGraph();

	        }
	    }

	 private void getGraphSettings(){
		 
		 
		 if (chckbxEdgeCurved.isSelected())
			 prevProps.put(PreviewProperty.EDGE_CURVED, Boolean.TRUE);
		 else
			 prevProps.put(PreviewProperty.EDGE_CURVED, Boolean.FALSE);
		 	
		 
		 
	 }
	
	private void displayGraph() {
        
        // First argument is the thread result, returned when processing finished.
        // Second argument is the value to update the GUI with via publish() and process()
        SwingWorker<ProcessingTarget, Integer> worker = new SwingWorker<ProcessingTarget, Integer>() {
 
            @Override
            protected ProcessingTarget doInBackground() throws Exception {

        		RenderGraph tsb;
        		if ((tibbr_url == "") || (tibbr_usr == "")	|| (tibbr_pwd == ""))
        			tsb = new RenderGraph();
        		else
        			tsb = new RenderGraph(tibbr_url, tibbr_usr, tibbr_pwd, txtUserCentric.getText(), prevProps);
        		
        		ProcessingTarget target1 = tsb.buildGraph();
				return target1;
                
            }

			@Override
			protected void done() {
			
				super.done();

				try {
					// Grab process target generated from background task and add to graph pane
					ProcessingTarget target2 = get();

					PApplet applet = target2.getApplet();
					graphPane.removeAll();
					graphPane.add(applet, BorderLayout.EAST);
					MainFrame.target = target2;
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
				

			}
 
        };
         
        worker.execute();
    }
	
	
}
