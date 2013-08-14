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
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.gephi.preview.api.ProcessingTarget;

import processing.core.PApplet;

import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.JSpinner;
import javax.swing.JComboBox;

@SuppressWarnings("rawtypes")
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
	private static RenderGraph tsb;
	// graph settings components
	private static JTextField txtUserCentric;
	private static JCheckBox chckbxEdgeCurved;
	private static JSpinner repulseSpinner;
	private static  JCheckBox chckbxCommunities;

	private static JComboBox lowColorComboBox;
	private static JComboBox medColorComboBox;
	private static JComboBox highColorComboBox;
	
	private static String[] colours = {"red","orange","yellow","green","blue","white","gray","pink", "cyan"};
	 
	 
	 
	public MainFrame(){
		
		tibbr_url = "";
		tibbr_usr = "";
		tibbr_pwd = "";
		// just some defaults for testing
		txtURL = new JTextField("http://172.16.101.129"); 
		txtUserID = new JTextField("tibbradmin");
		txtPassword = new JPasswordField("password");
		
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
	        frame.repaint();
	        //frame.setLocationRelativeTo(null);
	        frame.pack();
	        frame.setVisible(true);
	}

	@SuppressWarnings("unchecked")
	public static void addComponentsToPane(  ) {
        
		// add local panel to WEST zone of frames content pane.
		GridBagLayout gbl_controlPanel = new GridBagLayout();
		//gbl_controlPanel.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0};
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
		
		GridBagConstraints gbc_txtPassword = new GridBagConstraints();
		gbc_txtPassword.gridwidth = 2;
		gbc_txtPassword.insets = new Insets(0, 0, 5, 5);
		gbc_txtPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPassword.gridx = 1;
		gbc_txtPassword.gridy = 2;
		controlPanel.add(txtPassword, gbc_txtPassword);
		txtPassword.setColumns(10);
		
		// user centric 
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
		 
		//Graph Settings label
		 JLabel lblGraphSettings = new JLabel("Graph Settings:");
		 GridBagConstraints gbc_lblGraphSettings = new GridBagConstraints();
		 gbc_lblGraphSettings.anchor = GridBagConstraints.EAST;
		 gbc_lblGraphSettings.insets = new Insets(0, 0, 5, 5);
		 gbc_lblGraphSettings.gridx = 0;
		 gbc_lblGraphSettings.gridy = 6;
		 controlPanel.add(lblGraphSettings, gbc_lblGraphSettings);
		
		 // Edge curved
		 chckbxEdgeCurved = new JCheckBox("Edge Curved");
		 chckbxEdgeCurved.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_chckbxEdgeCurved = new GridBagConstraints();
		gbc_chckbxEdgeCurved.anchor = GridBagConstraints.WEST;
		gbc_chckbxEdgeCurved.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxEdgeCurved.gridx = 1;
		gbc_chckbxEdgeCurved.gridy = 6;
		controlPanel.add(chckbxEdgeCurved, gbc_chckbxEdgeCurved);
		
		// Communities
		 chckbxCommunities = new JCheckBox("Highlight Communities");
		chckbxCommunities.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_chckbxHighlightCommunities = new GridBagConstraints();
		gbc_chckbxHighlightCommunities.anchor = GridBagConstraints.WEST;
		gbc_chckbxHighlightCommunities.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxHighlightCommunities.gridx = 1;
		gbc_chckbxHighlightCommunities.gridy = 7;
		controlPanel.add(chckbxCommunities, gbc_chckbxHighlightCommunities);
		
		JLabel lblColourNodesBy = new JLabel("Colour nodes by most connected");
		GridBagConstraints gbc_lblColourNodesBy = new GridBagConstraints();
		gbc_lblColourNodesBy.gridheight = 3;
		gbc_lblColourNodesBy.insets = new Insets(0, 0, 5, 5);
		gbc_lblColourNodesBy.gridx = 0;
		gbc_lblColourNodesBy.gridy = 8;
		controlPanel.add(lblColourNodesBy, gbc_lblColourNodesBy);
		
		// colours : low
		JLabel lblLowestConnections = new JLabel("Low:");
		GridBagConstraints gbc_lblLowestConnections = new GridBagConstraints();
		gbc_lblLowestConnections.anchor = GridBagConstraints.EAST;
		gbc_lblLowestConnections.insets = new Insets(0, 0, 5, 5);
		gbc_lblLowestConnections.gridx = 1;
		gbc_lblLowestConnections.gridy = 8;
		controlPanel.add(lblLowestConnections, gbc_lblLowestConnections);
		lowColorComboBox = new JComboBox();
		 for (int i =0; i < colours.length;i++)
			 lowColorComboBox.addItem(colours[i]);
		 lowColorComboBox.setSelectedItem("blue");
		 
		GridBagConstraints gbc_lowColorComboBox = new GridBagConstraints();
		gbc_lowColorComboBox.insets = new Insets(0, 0, 5, 5);
		//gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_lowColorComboBox.gridx = 2;
		gbc_lowColorComboBox.gridy = 8;
		controlPanel.add(lowColorComboBox, gbc_lowColorComboBox);
		
		// colours : Medium
		JLabel lblMedium = new JLabel("Medium:");
		GridBagConstraints gbc_lblMedium = new GridBagConstraints();
		gbc_lblMedium.insets = new Insets(0, 0, 5, 5);
		gbc_lblMedium.anchor = GridBagConstraints.EAST;
		gbc_lblMedium.gridx = 1;
		gbc_lblMedium.gridy = 9;
		controlPanel.add(lblMedium, gbc_lblMedium);
		
		 medColorComboBox = new JComboBox();
		 
		 for (int i =0; i < colours.length;i++)
			 medColorComboBox.addItem(colours[i]);
		 medColorComboBox.setSelectedItem("orange");
		 
		GridBagConstraints gbc_medColorComboBox = new GridBagConstraints();
		gbc_medColorComboBox.insets = new Insets(0, 0, 5, 5);
		//gbc_medColorComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_medColorComboBox.gridx = 2;
		gbc_medColorComboBox.gridy = 9;
		controlPanel.add(medColorComboBox, gbc_medColorComboBox);
		
		
		// colours : High 
		JLabel lblHigh = new JLabel("High:");
		GridBagConstraints gbc_lblHigh = new GridBagConstraints();
		gbc_lblHigh.anchor = GridBagConstraints.EAST;
		gbc_lblHigh.insets = new Insets(0, 0, 5, 5);
		gbc_lblHigh.gridx = 1;
		gbc_lblHigh.gridy = 10;
		controlPanel.add(lblHigh, gbc_lblHigh);
		
		 highColorComboBox = new JComboBox();
		 for (int i =0; i < colours.length;i++)
			 highColorComboBox.addItem(colours[i]);
		 
		GridBagConstraints gbc_highColorComboBox = new GridBagConstraints();
		gbc_highColorComboBox.insets = new Insets(0, 0, 5, 5);
		//gbc_highColorComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_highColorComboBox.gridx = 2;
		gbc_highColorComboBox.gridy = 10;
		controlPanel.add(highColorComboBox, gbc_highColorComboBox);
		
		// Node repulsion
		JLabel lblNodeRepulsion = new JLabel("Node Repulsion:");
		GridBagConstraints gbc_lblNodeRepulsion = new GridBagConstraints();
		gbc_lblNodeRepulsion.anchor = GridBagConstraints.EAST;
		gbc_lblNodeRepulsion.insets = new Insets(0, 0, 5, 5);
		gbc_lblNodeRepulsion.gridx = 0;
		gbc_lblNodeRepulsion.gridy = 12;
		controlPanel.add(lblNodeRepulsion, gbc_lblNodeRepulsion);
		
		SpinnerModel model = new SpinnerNumberModel(5000,500,90000,500);
		 repulseSpinner = new JSpinner(model);

		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.anchor = GridBagConstraints.WEST;
		gbc_spinner.insets = new Insets(0, 0, 5, 5);
		gbc_spinner.gridx = 1;
		gbc_spinner.gridy = 12;
		controlPanel.add(repulseSpinner, gbc_spinner);
		
		//get graph
		JButton btnGetGraph = new JButton("Get Graph..");
		GridBagConstraints gbc_btnGetGraph = new GridBagConstraints();
		gbc_btnGetGraph.insets = new Insets(0, 0, 5, 5);
		gbc_btnGetGraph.gridx = 2;
		gbc_btnGetGraph.gridy = 13;
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
		gbc_btnZoomIn.gridy = 14;
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
		gbc_btnNewButton_1.gridy = 15;
		controlPanel.add(btnNewButton_1, gbc_btnNewButton_1);
		
		//Zoom Reset button
		JButton btnResetZoom = new JButton("Reset Zoom");
		btnResetZoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				target.resetZoom();
				//target.zoomMinus();

			}
		});
		GridBagConstraints gbc_btnResetZoom = new GridBagConstraints();
		gbc_btnResetZoom.insets = new Insets(0, 0, 5, 5);
		gbc_btnResetZoom.gridx = 1;
		gbc_btnResetZoom.gridy = 16;
		controlPanel.add(btnResetZoom, gbc_btnResetZoom);
		

        
		// add pane for the graph to RHS. 
        graphPane = new JPanel();
        graphPane.setPreferredSize(new Dimension(1250, 1100));
        graphPane.setBackground(Color.GRAY);
        contentPane.add(graphPane,BorderLayout.CENTER);
        
        // setup text area for the output console
        JTextArea consoleTextArea = new JTextArea("");
        // set desired size of text area (and let layout manager work it out).
        consoleTextArea.setPreferredSize(new Dimension(490, 500));
        
        float newSize = (float) 10.0;
		Font biggerFont = consoleTextArea.getFont().deriveFont(newSize );
        
		consoleTextArea.setFont(biggerFont);
        
        // use our OutputStream helper class to write to the textarea
        TextAreaOutputStream taos = new TextAreaOutputStream( consoleTextArea, 25 ); // max lines = 100        
        ps = new PrintStream( taos );

        // reassign standard output streams to our new print stream
        System.setOut( ps );
        //System.setErr( ps );

		GridBagConstraints gbc_scrollBar = new GridBagConstraints();
		//gbc_scrollBar.gridheight = 2;
		gbc_scrollBar.gridwidth = 4;
		gbc_scrollBar.gridx = 0;
		gbc_scrollBar.gridy = 22;
		
		// add the console to a scroll window
		JScrollPane sp = new JScrollPane(consoleTextArea,
						ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
						ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

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
	        	

	        	 if ((tibbr_url == "") || (tibbr_usr == "")	|| (tibbr_pwd == "")){
	        		 JOptionPane.showMessageDialog(null, "tibbr credentials are required...");
	        		 return;
	        	 }
	        	 
 
				tsb = new RenderGraph(tibbr_url, tibbr_usr, tibbr_pwd); // create graph object (lightweight call)
				 
				// Fill hashMap attribute based on UI
				getGraphSettings();
				// use worker thread to build and display graph
				displayGraph();
 

	        }
	    }

	 // get graph related settings and store in graphSettings Class
	 private void getGraphSettings(){
		 
		 tsb.setEdgeCurved(chckbxEdgeCurved.isSelected());
		 tsb.setUserCentric(txtUserCentric.getText());
		 tsb.setEnableCommunities(chckbxCommunities.isSelected());
		 tsb.setNodeRepulsion((Integer)repulseSpinner.getValue());
		 tsb.setLowColor((String)lowColorComboBox.getSelectedItem());
		 tsb.setMedColor((String)medColorComboBox.getSelectedItem());
		 tsb.setHighColor((String)highColorComboBox.getSelectedItem());
		 
	 }

	private void displayGraph() {
        
        // First argument is the thread result, returned when processing finished.
        // Second argument is the value to update the GUI with via publish() and process()
        SwingWorker<ProcessingTarget, Integer> worker = new SwingWorker<ProcessingTarget, Integer>() {
 
            @Override
            protected ProcessingTarget doInBackground() throws Exception {

	         
            	// actually go and build the graph - this take the time hence in worker thread.
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
