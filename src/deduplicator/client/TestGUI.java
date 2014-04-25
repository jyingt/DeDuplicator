package deduplicator.client;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.security.*;
import java.util.*;

import javax.swing.*;

import deduplicator.main.*;

/**
 * Front-end GUI for DeDuplicator
 * @author Chengxi Yang
 */
public class TestGUI extends JPanel implements ActionListener
{
    // For serialization
    private static final long serialVersionUID = 1L;
    
    // Constants
    private final String NAMEPATH = "database/name.txt";

    // GUI components
    public  JFrame                  frameGui;
    private JTextField              textFieldFile;
    private JFileChooser            fileChooser;
    private JButton                 btnSave;
    private JButton                 btnOpen;
    private JButton                 btnRetrieve;
    private DefaultComboBoxModel    comboBoxModel;
    private JComboBox               comboBoxFiles;
    private JProgressBar            progressBar;
    private JLabel                  labelStorage;
    private File                    file;
    
    private boolean falseSave = false;
    
    /**
     * Constructor
     */
    public TestGUI() {
        initialize();
        frameGui.setVisible(true);
    }

    /**
     * Initialize the contents of the frame
     */
    private void initialize() {
        // Frame
        frameGui = new JFrame();
        frameGui.setTitle("TestGUI");
        frameGui.setBounds(100, 100, 550, 350);
        frameGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SpringLayout springLayout = new SpringLayout();
        frameGui.getContentPane().setLayout(springLayout);
      
        // Save button
        btnSave = new JButton("Save");
        btnSave.addActionListener(this);
        springLayout.putConstraint(SpringLayout. NORTH, btnSave, 76, SpringLayout.NORTH, frameGui.getContentPane());
        springLayout.putConstraint(SpringLayout. SOUTH, btnSave, 99, SpringLayout.NORTH, frameGui.getContentPane());
        frameGui.getContentPane().add(btnSave);
        
        // Retrieve button
        btnRetrieve = new JButton("Retrieve");
        btnRetrieve.addActionListener(this);
        frameGui.getContentPane().add(btnRetrieve);
        
        // File list combo box
        try {
            if (new File(NAMEPATH).exists()) {
                // Get list of files currently stored in locker
                ReadInFile listFiles = new ReadInFile(NAMEPATH, "byte");
                String[] filenames = listFiles.ss.get(0).getFileContent().split("\n");
                comboBoxModel = new DefaultComboBoxModel(filenames);
            }
            else {
                // Empty combo box
                comboBoxModel = new DefaultComboBoxModel();
            }
            comboBoxFiles = new JComboBox(comboBoxModel);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
        springLayout.putConstraint(SpringLayout.NORTH, btnRetrieve,     -1, SpringLayout.NORTH, comboBoxFiles);
        springLayout.putConstraint(SpringLayout.WEST,  btnRetrieve,     67, SpringLayout.EAST,  comboBoxFiles);
        springLayout.putConstraint(SpringLayout.EAST,  btnRetrieve,    148, SpringLayout.EAST,  comboBoxFiles);
        springLayout.putConstraint(SpringLayout.WEST,  comboBoxFiles,   70, SpringLayout.WEST,  frameGui.getContentPane());
        springLayout.putConstraint(SpringLayout.EAST,  comboBoxFiles, -264, SpringLayout.EAST,  frameGui.getContentPane());
        comboBoxFiles.setModel(comboBoxModel);
        comboBoxFiles.setEditable(true);
        frameGui.getContentPane().add(comboBoxFiles);
        
        // Progress bar
        progressBar = new JProgressBar();
        springLayout.putConstraint(SpringLayout.SOUTH, comboBoxFiles,  -39, SpringLayout.NORTH, progressBar);
        springLayout.putConstraint(SpringLayout.NORTH, progressBar,    198, SpringLayout.NORTH, frameGui.getContentPane());
        springLayout.putConstraint(SpringLayout.WEST,  progressBar,    143, SpringLayout.WEST,  frameGui.getContentPane());
        springLayout.putConstraint(SpringLayout.EAST,  progressBar,   -145, SpringLayout.EAST,  frameGui.getContentPane());
        progressBar.setValue(0);
        frameGui.getContentPane().add(progressBar);
        
        // Storage usage label
        labelStorage = new JLabel();
		springLayout.putConstraint(SpringLayout.NORTH, labelStorage, 10, SpringLayout.SOUTH, progressBar);
		springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, labelStorage, 0, SpringLayout.HORIZONTAL_CENTER, frameGui.getContentPane());
		File dbFolder = new File("database/");
		if (dbFolder.exists()) {
	        long sss = folderSize(new File("database/"));
			String dir = Double.toString((double)((int)(sss*10/(1024*1024)))/10);
//			String dir = Double.toString((double) sss / (1024*1024));
			labelStorage.setText("Storage Usage is: " + dir + " MB");
			progressBar.setValue((int) ((double) sss*5 / (1024*1024)));
		}
		else {
			labelStorage.setText("Storage Usage is: 0 MB");
		}
        frameGui.getContentPane().add(labelStorage);
        
        // File name text field
        textFieldFile = new JTextField();
        springLayout.putConstraint(SpringLayout.NORTH, comboBoxFiles,   40, SpringLayout.SOUTH, textFieldFile);
        springLayout.putConstraint(SpringLayout.EAST,  textFieldFile, -264, SpringLayout.EAST,  frameGui.getContentPane());
        springLayout.putConstraint(SpringLayout.SOUTH, textFieldFile,   98, SpringLayout.NORTH, frameGui.getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, textFieldFile,   77, SpringLayout.NORTH, frameGui.getContentPane());
        springLayout.putConstraint(SpringLayout.WEST,  textFieldFile,   70, SpringLayout.WEST,  frameGui.getContentPane());
        frameGui.getContentPane().add(textFieldFile);
        textFieldFile.setColumns(10);
      
        // File chooser
        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        
        // Open button
        btnOpen = new JButton("Open");
        btnOpen.addActionListener(this);
        springLayout.putConstraint(SpringLayout.EAST,  btnSave,      99, SpringLayout.EAST,  btnOpen);
        springLayout.putConstraint(SpringLayout.NORTH, btnRetrieve,  38, SpringLayout.SOUTH, btnOpen);
        springLayout.putConstraint(SpringLayout.WEST,  btnSave,      18, SpringLayout.EAST,  btnOpen);
        springLayout.putConstraint(SpringLayout.WEST,  btnOpen,      19, SpringLayout.EAST,  textFieldFile);
        springLayout.putConstraint(SpringLayout.EAST,  btnOpen,     100, SpringLayout.EAST,  textFieldFile);
        springLayout.putConstraint(SpringLayout.SOUTH, btnOpen,      99, SpringLayout.NORTH, frameGui.getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, btnOpen,      76, SpringLayout.NORTH, frameGui.getContentPane());
        frameGui.getContentPane().add(btnOpen);
    }

    /**
     * Action event handler
     */
    public void actionPerformed(ActionEvent event) {
        // Handle open button action
        if (event.getSource() == btnOpen) {
            int returnVal = fileChooser.showOpenDialog(TestGUI.this);
 
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                file = fileChooser.getSelectedFile();
                textFieldFile.setText(file.getName());
            }
            else {
                // Error Window
                JOptionPane.showMessageDialog(frameGui, "An error occured. Please try again.");
            }
        }
        
        // Handle save button action
        else if (event.getSource() == btnSave) {
            if (textFieldFile.getText().length() < 1) {
                JOptionPane.showMessageDialog(frameGui, "Filename cannot be empty.");
            }
            else {
                try {
                    String a = file.getAbsolutePath();
        			String[] b = a.split(":");
        			StoreFile readFile = new StoreFile();

        			if (b.length > 1) {
    			        String[] c = b[1].split("\\\\");
    			        
    			        String path = "";
    			        for(String str : c) {
    			            path += "/" + str;
    			        }

    			        
			            if (new File(NAMEPATH).exists()) {
			            	
    			             ReadInFile listFiles = new ReadInFile( NAMEPATH, "byte");
    			             
    			             String[] filenames = listFiles.ss.get(0).getFileContent().split("[\r\n]");
    			             ArrayList<String> listFilenames = new ArrayList(Arrays.asList(filenames));
    			             
    			             if (!listFilenames.contains(c[c.length-1])) {

    			            	readFile = new StoreFile(path);
    			            	 if (!readFile.flag)
    	    			            {
    	    			            	 JOptionPane.showMessageDialog(frameGui, "File type error.");
    	    			            	 return;
    	    			            }
    			            	 
    			             }
    			             else {
    			            	 falseSave = true;
    			            	 JOptionPane.showMessageDialog(frameGui, "File already in the storage.");
    			            	 return;
    			             }
    			        } 
    			        else {

    			            readFile = new StoreFile(path);

    			            if (!readFile.flag)
    			            {
    			            	 JOptionPane.showMessageDialog(frameGui, "File type error.");
    			            	 return;
    			            }
			            }
        			}
        			else {

        			    readFile = new StoreFile(file.getAbsolutePath());

			            if (!readFile.flag)
			            {
			            	 JOptionPane.showMessageDialog(frameGui, "File type error.");
			            	 return;
			            }
        			}
        	        long sss = folderSize(new File("database/"));
        			String dir = Double.toString((double)((int)(sss*10/(1024*1024)))/10);
        			labelStorage.setText("Storage Usage is: " + dir + " MB");
        			progressBar.setValue((int) ((double) sss*5 / (1024*1024)));
        			
        			comboBoxModel.removeAllElements();
                    ReadInFile listFiles = new ReadInFile(NAMEPATH, "byte");
                    String[] filenames = listFiles.ss.get(0).getFileContent().split("\n");
                    for (String s: filenames)
                    	comboBoxModel.addElement(s);
        			JOptionPane.showMessageDialog(frameGui, "File save completed.");
                }
                catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        // Handle retrieve button action
        else if (event.getSource() == btnRetrieve) {
            try {
                new ReceiveFile("retrievefolder", comboBoxModel.getSelectedItem().toString());
                JOptionPane.showMessageDialog(frameGui, "File retrieval completed.");
            }
            catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Get total size of directory
     * @param dir the directory to measure size of
     * @return size of directory
     */
    public static long folderSize(File dir) {
        long length = 0;
        
        for (File file : dir.listFiles()) {
            if (file.isFile())
                length += file.length();
            else
                length += folderSize(file);
        }
        
        return length;
    }
    
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new TestGUI();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}