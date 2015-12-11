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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.PrintStream;
import java.util.concurrent.ExecutionException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
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
	
	private static JLabel statusLabel;
	  
	private static String[] colours = {"red","orange","yellow","green","blue","white","gray","pink", "cyan"};
	 
	private static boolean fCluster;
	private static boolean fConnected;
	
	public MainFrame(){
		
		tibbr_url = "";
		tibbr_usr = "";
		tibbr_pwd = "";
		// just some defaults for testing
		txtURL = new JTextField("https://jivedemo-ben-demo.jiveon.com"); 
		txtURL.setToolTipText("Enter the Jive URL e.g. https://jivedemo-myserver.jiveon.com ");
		txtUserID = new JTextField("adminuser");
		txtUserID.setToolTipText("Enter Jive login username" );
		txtPassword = new JPasswordField("jive123");
		txtPassword.setToolTipText("Enter Jive login password" );
		
		
	}
	
	
	public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });

	}
	
	 private static void createAndShowGUI() {
         
		 	// call constructor to setup jive creds.
		 	mf = new MainFrame();
		 	
	        frame = new JFrame("Jive Social Graph");
	        //frame.setSize(new Dimension(500, 500));
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setTitle("Jive Social Graph Analyzer v1.0");

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
		
        
		// add pane for the graph to RHS. 
        graphPane = new JPanel();
        graphPane.setPreferredSize(new Dimension(1000, 1100));
        graphPane.setBackground(Color.GRAY);
        contentPane.add(graphPane,BorderLayout.CENTER);
		        
		        
		//jive URL input
		JLabel lblTibbrUrl = new JLabel("Jive URL:");
		
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
		JLabel lblTibbrUserid = new JLabel("Jive Username:");
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
		JLabel lblTibbrPassword = new JLabel("Jive Password:");
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
		JLabel lblNewLabel = new JLabel("User-Centric (enter username):");
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
		
	//Graph Options 
		 JLabel lblGraphOptions = new JLabel("Graph Options:");
		 GridBagConstraints gbc_lblGraphOptions = new GridBagConstraints();
		 gbc_lblGraphOptions.anchor = GridBagConstraints.EAST;
		 gbc_lblGraphOptions.insets = new Insets(0, 0, 5, 5);
		 gbc_lblGraphOptions.gridx = 0;
		 gbc_lblGraphOptions.gridy = 5;
		 controlPanel.add(lblGraphOptions, gbc_lblGraphOptions);	
		
//  //Graph Settings label
//		 JLabel lblGraphSettings = new JLabel("Graph Settings:");
//		 GridBagConstraints gbc_lblGraphSettings = new GridBagConstraints();
//		 gbc_lblGraphSettings.anchor = GridBagConstraints.EAST;
//		 gbc_lblGraphSettings.insets = new Insets(0, 0, 5, 5);
//		 gbc_lblGraphSettings.gridx = 0;
//		 gbc_lblGraphSettings.gridy = 5;
//		 controlPanel.add(lblGraphSettings, gbc_lblGraphSettings);
		
		 // Edge curved
		 chckbxEdgeCurved = new JCheckBox("Edge Curved");
		 chckbxEdgeCurved.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_chckbxEdgeCurved = new GridBagConstraints();
		gbc_chckbxEdgeCurved.anchor = GridBagConstraints.WEST;
		gbc_chckbxEdgeCurved.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxEdgeCurved.gridx = 1;
		gbc_chckbxEdgeCurved.gridy = 5;
		chckbxEdgeCurved.setSelected(true);
		controlPanel.add(chckbxEdgeCurved, gbc_chckbxEdgeCurved);
		 
		// Node repulsion
		JLabel lblNodeRepulsion = new JLabel("Node Repulsion:");
		GridBagConstraints gbc_lblNodeRepulsion = new GridBagConstraints();
		gbc_lblNodeRepulsion.anchor = GridBagConstraints.EAST;
		gbc_lblNodeRepulsion.insets = new Insets(0, 0, 5, 5);
		gbc_lblNodeRepulsion.gridx = 1;
		gbc_lblNodeRepulsion.gridy = 6;
		controlPanel.add(lblNodeRepulsion, gbc_lblNodeRepulsion);
		
		SpinnerModel model = new SpinnerNumberModel(3000,500,90000,500);
		 repulseSpinner = new JSpinner(model);

		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.anchor = GridBagConstraints.WEST;
		gbc_spinner.insets = new Insets(0, 0, 5, 5);
		gbc_spinner.gridx = 2;
		gbc_spinner.gridy = 6;
		controlPanel.add(repulseSpinner, gbc_spinner);
		
		 // Graph Radios
		
		JLabel lblAnalysis = new JLabel("Graph Analysis Type:");
		GridBagConstraints gbc_lblAnalysis = new GridBagConstraints();
		gbc_lblAnalysis.anchor = GridBagConstraints.EAST;
		gbc_lblAnalysis.insets = new Insets(0, 0, 5, 5);
		gbc_lblAnalysis.gridx = 0;
		gbc_lblAnalysis.gridy = 7;
		controlPanel.add(lblAnalysis, gbc_lblAnalysis);
		
		
	      final JRadioButton radConnected = new JRadioButton("Connectedness", true);
	      final JRadioButton radCluster = new JRadioButton("Cluster");
	      radConnected.setMnemonic(KeyEvent.VK_C);
	      radCluster.setMnemonic(KeyEvent.VK_M);
	      
	      radConnected.addItemListener(new ItemListener() {
	         public void itemStateChanged(ItemEvent e) {   
	        	 fConnected = (e.getStateChange()==1?true:false);
	        	 fCluster = !fConnected;
	        	 lowColorComboBox.setEnabled(fConnected);
	        	 medColorComboBox.setEnabled(fConnected);
	        	 highColorComboBox.setEnabled(fConnected);
	         }
	         });

	      radCluster.addItemListener(new ItemListener() {
		         public void itemStateChanged(ItemEvent e) {   
		        	 fCluster = (e.getStateChange()==1?true:false);
		        	 fConnected = !fCluster;
		        	 lowColorComboBox.setEnabled(fConnected);
		        	 medColorComboBox.setEnabled(fConnected);
		        	 highColorComboBox.setEnabled(fConnected);
		         }
		         });	      

	      //Group the radio buttons.
	      ButtonGroup group = new ButtonGroup();
	      group.add(radConnected);
	      group.add(radCluster);
	
	      GridBagConstraints gbc_radConnected = new GridBagConstraints();
	      gbc_radConnected.anchor = GridBagConstraints.EAST;
	      gbc_radConnected.insets = new Insets(0, 0, 5, 5);
	      gbc_radConnected.gridx = 1;
	      gbc_radConnected.gridy = 7;
			 controlPanel.add(radConnected, gbc_radConnected);	
			 
		 GridBagConstraints gbc_radCluster = new GridBagConstraints();
		 gbc_radCluster.anchor = GridBagConstraints.EAST;
		 gbc_radCluster.insets = new Insets(0, 0, 5, 5);
		 gbc_radCluster.gridx = 2;
		 gbc_radCluster.gridy = 7;
			 controlPanel.add(radCluster, gbc_radCluster);


		
		// Communities
//		 chckbxCommunities = new JCheckBox("Highlight Communities");
//		chckbxCommunities.setHorizontalAlignment(SwingConstants.LEFT);
//		GridBagConstraints gbc_chckbxHighlightCommunities = new GridBagConstraints();
//		gbc_chckbxHighlightCommunities.anchor = GridBagConstraints.WEST;
//		gbc_chckbxHighlightCommunities.insets = new Insets(0, 0, 5, 5);
//		gbc_chckbxHighlightCommunities.gridx = 1;
//		gbc_chckbxHighlightCommunities.gridy = 7;
//		chckbxCommunities.setSelected(true);
//		controlPanel.add(chckbxCommunities, gbc_chckbxHighlightCommunities);
		
		JLabel lblColourNodesBy = new JLabel("Colour nodes by no. of connections");
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
		
		
		
		
		
		//get graph
		JButton btnGetGraph = new JButton("Build Social Graph..");
		GridBagConstraints gbc_btnGetGraph = new GridBagConstraints();
		gbc_btnGetGraph.gridheight = 2;
		gbc_btnGetGraph.insets = new Insets(0, 0, 5, 5);
		gbc_btnGetGraph.gridx = 1;
		gbc_btnGetGraph.gridy = 11;
		controlPanel.add(btnGetGraph, gbc_btnGetGraph);
		btnGetGraph.addActionListener(mf.new HandlerClass());
	
		//---------------------------------------------------
		
		// Open as PDF
		JButton btnOpenPdf = new JButton("Open as PDF...");
		btnOpenPdf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tsb.openPDF();
			}
		});
		GridBagConstraints gbc_btnOpenPdf = new GridBagConstraints();
		gbc_btnOpenPdf.insets = new Insets(0, 0, 5, 5);
		gbc_btnOpenPdf.gridx = 2;
		gbc_btnOpenPdf.gridy = 14;
		controlPanel.add(btnOpenPdf, gbc_btnOpenPdf);
		
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
		gbc_btnZoomIn.gridy = 15;
		controlPanel.add(btnZoomIn, gbc_btnZoomIn);
				
		//Zoom out button
		JButton btnZoomOut = new JButton("Zoom Out");
		btnZoomOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				target.zoomMinus();
			}
		});
		GridBagConstraints gbc_btnZoomOut = new GridBagConstraints();
		gbc_btnZoomOut.insets = new Insets(0, 0, 5, 5);
		gbc_btnZoomOut.gridx = 1;
		gbc_btnZoomOut.gridy = 16;
		controlPanel.add(btnZoomOut, gbc_btnZoomOut);
		
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
		gbc_btnResetZoom.gridy = 17;
		controlPanel.add(btnResetZoom, gbc_btnResetZoom);
	
		

        

        
 //--–------ output console ----------       
        
        // setup text area for the output console
        JTextArea consoleTextArea = new JTextArea("");
        consoleTextArea.setVisible(true);
        
        //set font size
        float newSize = (float) 10.0;
		Font biggerFont = consoleTextArea.getFont().deriveFont(newSize );
		consoleTextArea.setFont(biggerFont);
        
        // use our OutputStream helper class to write to the textarea
        TextAreaOutputStream taos = new TextAreaOutputStream( consoleTextArea,   20 ); // max lines = 100        
        ps = new PrintStream( taos );

        // reassign standard output streams to our new print stream
        System.setOut( ps );
        //System.setErr( ps );

		GridBagConstraints gbc_scrollBar = new GridBagConstraints();
		gbc_scrollBar.weightx = 1;
		gbc_scrollBar.weighty = 1;
		gbc_scrollBar.fill = GridBagConstraints.BOTH;
		gbc_scrollBar.gridheight = 1;
		gbc_scrollBar.gridwidth = 3;
		gbc_scrollBar.gridx = 0;
		gbc_scrollBar.gridy = 23;
		
		// add the textarea to a scroll window
		JScrollPane sp = new JScrollPane (consoleTextArea);
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		          
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
	        		 JOptionPane.showMessageDialog(null, "Jive credentials are required...");
	        		 return;
	        	 }
	        	 
	        	 // get rid of any slash at end
	        	 if (tibbr_url.endsWith("/"))
	        	 {
	        		 tibbr_url =tibbr_url.substring(0, tibbr_url.length() -1 );
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
		 
		
		 tsb.setEnableCommunities(fCluster);
		 
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

            
            
//			@Override
//			protected void process(List<Integer> chunks) {
//				
//				for (Integer chunk : chunks) 
//		            progressBar.setValue(chunk);
//		            
//			}



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
	
	//----------------------- 
	

}
