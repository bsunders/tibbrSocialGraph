package org.ben.socialgraph;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
//import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
//import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.io.PrintStream;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import org.gephi.preview.api.ProcessingTarget;

import processing.core.PApplet;












//import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;


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
	
	public MainFrame(){
		
		tibbr_url = "";
		tibbr_usr = "";
		tibbr_pwd = "";
		// just some defaults for testing
		txtURL = new JTextField("https://malaysiaair.tibbr.com"); 
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
         
	        //Create and set up the window.
		 
		 	// call constructor to setup tibbr creds.
		 	mf = new MainFrame();
		 	
	        frame = new JFrame("tibbr Social Graph");
	        frame.setSize(new Dimension(500, 500));
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setTitle("Social Graph v1.0");

			
			contentPane = (JPanel) frame.getContentPane();
			contentPane.setBackground(Color.WHITE);
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPane.setLayout(new BorderLayout(0, 0));
			
			
	        //Set up the content pane.
	        addComponentsToPane();
	        
	        //contentPane.revalidate();
	        
	        frame.pack();
	       // frame.setLocationRelativeTo( null );
	        frame.setVisible(true);
	}
	 
	public static void addComponentsToPane(  ) {
        
		
		
		// add local panel to WEST zone of frames content pane.
		controlPanel = new JPanel( new GridBagLayout());
		contentPane.add(controlPanel, BorderLayout.WEST);
		
		//set up the grid layout
		//GridBagLayout gbl_panel = new GridBagLayout();
		
		
		//gbl_panel.columnWidths = new int[]{40, 40, 40, 0};
		//gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 29, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		//gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		//gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		
		
		//controlPanel.setLayout(gbl_panel);
		
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
		gbc_txtURL.insets = new Insets(0, 0, 5, 0);
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
		gbc_txtUserID.insets = new Insets(0, 0, 5, 0);
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
		gbc_txtPassword.insets = new Insets(0, 0, 5, 0);
		gbc_txtPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPassword.gridx = 1;
		gbc_txtPassword.gridy = 2;
		controlPanel.add(txtPassword, gbc_txtPassword);
		txtPassword.setColumns(10);
		
		//get graph
		JButton btnGetGraph = new JButton("Get Graph..");
		GridBagConstraints gbc_btnGetGraph = new GridBagConstraints();
		gbc_btnGetGraph.insets = new Insets(0, 0, 5, 0);
		gbc_btnGetGraph.gridx = 2;
		gbc_btnGetGraph.gridy = 3;
		controlPanel.add(btnGetGraph, gbc_btnGetGraph);
		btnGetGraph.addActionListener(mf.new HandlerClass());
	
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
		gbc_btnZoomIn.gridy = 4;
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
		gbc_btnNewButton_1.gridy = 5;
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
		gbc_btnResetZoom.gridy = 6;
		controlPanel.add(btnResetZoom, gbc_btnResetZoom);
		
		//Move Up button
		JButton btnMoveUp = new JButton("Move Up");
		btnMoveUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
			}
		});
		GridBagConstraints gbc_btnMoveUp = new GridBagConstraints();
		gbc_btnMoveUp.insets = new Insets(0, 0, 5, 5);
		gbc_btnMoveUp.gridx = 1;
		gbc_btnMoveUp.gridy = 8;
		controlPanel.add(btnMoveUp, gbc_btnMoveUp);
		
		//Move Down button
		JButton btnMoveDown = new JButton("Move Down");
		btnMoveDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		GridBagConstraints gbc_btnMoveDown = new GridBagConstraints();
		gbc_btnMoveDown.insets = new Insets(0, 0, 5, 5);
		gbc_btnMoveDown.gridx = 1;
		gbc_btnMoveDown.gridy = 10;
		controlPanel.add(btnMoveDown, gbc_btnMoveDown);
		
		//Move Left button
		JButton btnNewButton_2 = new JButton("Move Left");
		GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
		gbc_btnNewButton_2.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_2.gridx = 0;
		gbc_btnNewButton_2.gridy = 9;
		controlPanel.add(btnNewButton_2, gbc_btnNewButton_2);
		
		//Move RIght button
		JButton btnMoveRight = new JButton("Move Right");
		GridBagConstraints gbc_btnMoveRight = new GridBagConstraints();
		gbc_btnMoveRight.insets = new Insets(0, 0, 5, 0);
		gbc_btnMoveRight.gridx = 2;
		gbc_btnMoveRight.gridy = 9;
		controlPanel.add(btnMoveRight, gbc_btnMoveRight);
		
		
        
		// add pane for the graph to RHS. 
        graphPane = new JPanel();
        graphPane.setPreferredSize(new Dimension(1000, 900));
        graphPane.setBackground(Color.GRAY);
        contentPane.add(graphPane,BorderLayout.EAST);
        
        
        
        // setup text area for the output console
        JTextArea consoleTextArea = new JTextArea("");
        // set desired size of text area (and let layout manager work it out).
        consoleTextArea.setPreferredSize(new Dimension(300, 400));
        
        float newSize = (float) 10.0;
		Font biggerFont = consoleTextArea.getFont().deriveFont(newSize );
        
		consoleTextArea.setFont(biggerFont);
        
        // use our OutputStream helper class to write to the textarea
        TextAreaOutputStream taos = new TextAreaOutputStream( consoleTextArea, 35 ); // max lines = 100
        
        ps = new PrintStream( taos );
  
  
        // reassign standard output streams to our new print stream
        System.setOut( ps );
        System.setErr( ps );


		GridBagConstraints gbc_scrollBar = new GridBagConstraints();
		gbc_scrollBar.gridheight = 2;
		gbc_scrollBar.gridwidth = 4;
		gbc_scrollBar.insets = new Insets(0, 0, 5, 5);
		gbc_scrollBar.gridx = 0;
		gbc_scrollBar.gridy = 12;
		
		// add the console to a scroll window
		JScrollPane sp = new JScrollPane(consoleTextArea);

		
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
	        	
	        	start();
	        	//controlPanel.repaint();
	        	//addGraphToContainer();

	        }
	    }
	

	private static  void addGraphToContainer()
	{
		RenderGraph tsb;
		
		
		
		
		if ((tibbr_url == "") || (tibbr_usr == "")	|| (tibbr_pwd == ""))
			tsb = new RenderGraph();
		else
			tsb = new RenderGraph(MainFrame.tibbr_url, MainFrame.tibbr_usr, MainFrame.tibbr_pwd);
		
		//System.out.println("Graph Complete - rendering to UI......" );
		
		
		target = tsb.buildGraph();
		PApplet applet = target.getApplet();
		applet.setPreferredSize(new Dimension(1000, 900));
		applet.mousePressed();
		
		graphPane.removeAll();
		graphPane.add(applet, BorderLayout.EAST);
        
		applet.mousePressed();
		
	}
	
	
	private void start() {
        
        // Use SwingWorker<Void, Void> and return null from doInBackground if
        // you don't want any final result and you don't want to update the GUI
        // as the thread goes along.
        // First argument is the thread result, returned when processing finished.
        // Second argument is the value to update the GUI with via publish() and process()
        SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
 
            @Override
            /*
             * Note: do not update the GUI from within doInBackground.
             */
            protected Boolean doInBackground() throws Exception {
                 
                // Simulate useful work
            	addGraphToContainer();
                
                 
                return false;
            }
 
            @Override
            // This will be called if you call publish() from doInBackground()
            // Can safely update the GUI here.
            protected void process(List<Integer> chunks) {
                //Integer value = chunks.get(chunks.size() - 1);
                 
                //countLabel1.setText("Current value: " + value);
            }
 
            @Override
            // This is called when the thread finishes.
            // Can safely update GUI here.
            protected void done() {
                 
                try {
                    Boolean status = get();
                    //statusLabel.setText("Completed with status: " + status);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                 
            }
             
        };
         
        worker.execute();
    }
	
	
}
