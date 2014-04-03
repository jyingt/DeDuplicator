package deduplicator.client;

import java.awt.EventQueue;

import java.io.*;
import java.security.NoSuchAlgorithmException;

import javax.swing.*;
import java.awt.event.*;

import deduplicator.compare.StringComparison;
import deduplicator.compare.StringComparison.Change;
import deduplicator.decoder.MainRetrieving;
import deduplicator.encoder.MainSaving;
import deduplicator.encoder.ReadInFile;
import deduplicator.encoder.ReadInFile.SaveLet;

/**
 * GUI
 * @author Chengxi Yang
 */

public class TestGUI extends JPanel
implements ActionListener {
	
	private static final long serialVersionUID = 1L; // for serialization
	private static final String NAMEPATH = "db/key/name.txt";

	private JFrame frmTestgui;
	private JTextField textField;
    private JFileChooser fc;
    private JButton btnSave, btnOpen, btnDelete, btnRetrieve;
    private DefaultComboBoxModel model;
    private JComboBox comboBox;
    private JProgressBar progressBar;
    private File file;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestGUI window = new TestGUI();
					window.frmTestgui.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TestGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTestgui = new JFrame();
		frmTestgui.setTitle("TestGUI");
		frmTestgui.setBounds(100, 100, 450, 300);
		frmTestgui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frmTestgui.getContentPane().setLayout(springLayout);
		
		btnSave = new JButton("Save");
		btnSave.addActionListener(this);
		springLayout.putConstraint(SpringLayout.NORTH, btnSave, 76, SpringLayout.NORTH, frmTestgui.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, btnSave, 99, SpringLayout.NORTH, frmTestgui.getContentPane());
		frmTestgui.getContentPane().add(btnSave);
		
		btnRetrieve = new JButton("Retrieve");
		btnRetrieve.addActionListener(this);
		frmTestgui.getContentPane().add(btnRetrieve);
		
		ReadInFile listFiles;
		try {
			File ff = new File(NAMEPATH);
			if(ff.exists()) {
				listFiles = new ReadInFile(NAMEPATH, "byte");
				String[] filenames = listFiles.ss.get(0).getFileContent().split("\n");
				model = new DefaultComboBoxModel(filenames);
			} else {
				model = new DefaultComboBoxModel();
			}
			comboBox = new JComboBox(model);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		springLayout.putConstraint(SpringLayout.WEST, comboBox, 70, SpringLayout.WEST, frmTestgui.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, comboBox, -264, SpringLayout.EAST, frmTestgui.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, btnRetrieve, 19, SpringLayout.EAST, comboBox);
		springLayout.putConstraint(SpringLayout.EAST, btnRetrieve, 100, SpringLayout.EAST, comboBox);
		comboBox.setModel(model);
		comboBox.setEditable(true);
		frmTestgui.getContentPane().add(comboBox);
		
		progressBar = new JProgressBar();
		springLayout.putConstraint(SpringLayout.SOUTH, btnRetrieve, -38, SpringLayout.NORTH, progressBar);
		springLayout.putConstraint(SpringLayout.SOUTH, comboBox, -39, SpringLayout.NORTH, progressBar);
		springLayout.putConstraint(SpringLayout.NORTH, progressBar, 198, SpringLayout.NORTH, frmTestgui.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, progressBar, 143, SpringLayout.WEST, frmTestgui.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, progressBar, -145, SpringLayout.EAST, frmTestgui.getContentPane());
		progressBar.setValue(0);
		frmTestgui.getContentPane().add(progressBar);
		
		textField = new JTextField();
		springLayout.putConstraint(SpringLayout.EAST, textField, -264, SpringLayout.EAST, frmTestgui.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, comboBox, 40, SpringLayout.SOUTH, textField);
		springLayout.putConstraint(SpringLayout.SOUTH, textField, 98, SpringLayout.NORTH, frmTestgui.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, textField, 77, SpringLayout.NORTH, frmTestgui.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, textField, 70, SpringLayout.WEST, frmTestgui.getContentPane());
		frmTestgui.getContentPane().add(textField);
		textField.setColumns(10);
		
		//Create a file chooser
        fc = new JFileChooser();
        
		btnOpen = new JButton("Open");
		btnOpen.addActionListener(this);
		springLayout.putConstraint(SpringLayout.EAST, btnSave, 99, SpringLayout.EAST, btnOpen);
		springLayout.putConstraint(SpringLayout.NORTH, btnRetrieve, 38, SpringLayout.SOUTH, btnOpen);
		springLayout.putConstraint(SpringLayout.WEST, btnSave, 18, SpringLayout.EAST, btnOpen);
		springLayout.putConstraint(SpringLayout.WEST, btnOpen, 19, SpringLayout.EAST, textField);
		springLayout.putConstraint(SpringLayout.EAST, btnOpen, 100, SpringLayout.EAST, textField);
		springLayout.putConstraint(SpringLayout.SOUTH, btnOpen, 99, SpringLayout.NORTH, frmTestgui.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, btnOpen, 76, SpringLayout.NORTH, frmTestgui.getContentPane());
		frmTestgui.getContentPane().add(btnOpen);
		
		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(this);
		springLayout.putConstraint(SpringLayout.NORTH, btnDelete, 38, SpringLayout.SOUTH, btnSave);
		springLayout.putConstraint(SpringLayout.WEST, btnDelete, 18, SpringLayout.EAST, btnRetrieve);
		springLayout.putConstraint(SpringLayout.SOUTH, btnDelete, -38, SpringLayout.NORTH, progressBar);
		springLayout.putConstraint(SpringLayout.EAST, btnDelete, 99, SpringLayout.EAST, btnRetrieve);
		frmTestgui.getContentPane().add(btnDelete);
	}
	
	public void actionPerformed(ActionEvent arg0) {

        //Handle open button action.
        if (arg0.getSource() == btnOpen) {
            int returnVal = fc.showOpenDialog(TestGUI.this);
 
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                file = fc.getSelectedFile();
                textField.setText(file.getName());
                //This is where a real application would open the file.
            } else {
                /*Error Window*/
            	JOptionPane.showMessageDialog(frmTestgui, "An error occured. Please try again.");
            }
 
        //Handle save button action.
        } else if (arg0.getSource() == btnSave) {
        	
        	try {
				MainSaving readFile = new MainSaving(textField.getText());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
        	if(textField.getText().length() < 1)
        		JOptionPane.showMessageDialog(frmTestgui, "Filename cannot be empty.");
        	else {
	        	model.addElement(textField.getText());
	        	progressBar.setValue(100);
	        	JOptionPane.showMessageDialog(frmTestgui, "File save completed.");
        	}
 
        //Handle retrieve button action.
        } else if (arg0.getSource() == btnRetrieve) {
        	try {
        		System.out.println(model.getSelectedItem().toString());
				MainRetrieving retrieveFile = new MainRetrieving("retrievefolder", model.getSelectedItem().toString());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	progressBar.setValue(100);
        	JOptionPane.showMessageDialog(frmTestgui, "File retieval completed.");
 
        //Handle delete button action.
        } else if (arg0.getSource() == btnDelete) {
        	/* Deletes the filename from combobox
        	model.removeElement(model.getSelectedItem());
        	*/
        	progressBar.setValue(0);
        	JOptionPane.showMessageDialog(frmTestgui, "Feature not available.");
        }
	}

}