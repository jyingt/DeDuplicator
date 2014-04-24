package deduplicator.client;

import java.awt.EventQueue;
import java.awt.List;
import java.awt.Toolkit;
import java.io.*;
import java.awt.event.*;

import javax.swing.*;

import java.beans.*;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import deduplicator.main.ReceiveFile;
import deduplicator.main.StoreFile;
import deduplicator.main.ReadInFile;

/**
 * GUI
 * @author Chengxi Yang
 */

public class TestGUI extends JPanel implements ActionListener,PropertyChangeListener
{
    private static final long serialVersionUID = 1L; // for serialization
    private static final String NAMEPATH = "db/database/name.txt" ;
    private static boolean falsesave = false;

    public JFrame frmTestgui;
    private JTextField textField;
    private JFileChooser fc;
    private JButton btnSave, btnOpen, btnRetrieve;
    private DefaultComboBoxModel model;
    private JComboBox comboBox;
    private JProgressBar progressBar ;
    private JLabel storageInfo;
    private ProgressMonitor progressMonitor;
    private File file;
    private Task taskSave, taskRetrieve;
 
    class Task extends SwingWorker<Void, Void> {
        @Override
        public Void doInBackground() {
            Random random = new Random();
            int progress = 0;
            setProgress(0);
            try {
                Thread.sleep(100);
                while (progress < 100 && !isCancelled()) {
                    //Sleep for up to one second.
                    Thread.sleep(random.nextInt(10));
                    //Make random progress.
                    progress += random.nextInt(10);
                    setProgress(Math.min(progress, 100));
                }
            } catch (InterruptedException ignore) {}
            return null;
        }
 
        @Override
        public void done() {
        	btnSave.setEnabled(true);
        	btnRetrieve.setEnabled(true);
        	btnOpen.setEnabled(true);
            progressMonitor.close();
        }
    }
    
    public static long folderSize(File directory) {
        long length = 0;
        for (File file : directory.listFiles()) {
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
        EventQueue. invokeLater(new Runnable() {
            public void run() {
                try {
                    new TestGUI();
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
        frmTestgui.setVisible(true);
    }

    /**
    * Initialize the contents of the frame.
    */
    private void initialize() {
        frmTestgui = new JFrame();
        frmTestgui .setTitle("TestGUI");
        frmTestgui .setBounds(100, 100, 450, 300);
        frmTestgui .setDefaultCloseOperation(JFrame. EXIT_ON_CLOSE);
        SpringLayout springLayout = new SpringLayout();
        frmTestgui .getContentPane().setLayout(springLayout);
      
        btnSave = new JButton("Save");
        btnSave.addActionListener(this);
        springLayout.putConstraint(SpringLayout. NORTH, btnSave, 76, SpringLayout.NORTH, frmTestgui.getContentPane());
        springLayout.putConstraint(SpringLayout. SOUTH, btnSave, 99, SpringLayout.NORTH, frmTestgui.getContentPane());
        frmTestgui .getContentPane().add(btnSave);
      
        btnRetrieve = new JButton("Retrieve");
        btnRetrieve.addActionListener( this);
        frmTestgui.getContentPane().add(btnRetrieve );
        
        ReadInFile listFiles;
        
        try {
            File ff = new File(NAMEPATH);
            if (ff.exists()) {
                listFiles = new ReadInFile( NAMEPATH, "byte");
                String[] filenames = listFiles.ss .get(0).getFileContent().split("\n");
                model = new DefaultComboBoxModel(filenames);
            } else {
                model = new DefaultComboBoxModel();
            }
            comboBox = new JComboBox(model );
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
        
        springLayout.putConstraint(SpringLayout. NORTH, btnRetrieve , -1, SpringLayout. NORTH, comboBox);
        springLayout.putConstraint(SpringLayout. WEST, btnRetrieve , 67, SpringLayout. EAST, comboBox);
        springLayout.putConstraint(SpringLayout. EAST, btnRetrieve , 148, SpringLayout. EAST, comboBox);
        springLayout.putConstraint(SpringLayout. WEST, comboBox, 70, SpringLayout. WEST, frmTestgui .getContentPane());
        springLayout.putConstraint(SpringLayout. EAST, comboBox, -264, SpringLayout. EAST, frmTestgui .getContentPane());
        comboBox.setModel(model);
        comboBox.setEditable(true);
        frmTestgui.getContentPane().add(comboBox);
      
        progressBar = new JProgressBar();
        springLayout.putConstraint(SpringLayout.SOUTH, comboBox, -39, SpringLayout. NORTH, progressBar );
        springLayout.putConstraint(SpringLayout.NORTH, progressBar , 198, SpringLayout. NORTH, frmTestgui .getContentPane());
        springLayout.putConstraint(SpringLayout.WEST, progressBar , 143, SpringLayout. WEST, frmTestgui .getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, progressBar , -145, SpringLayout. EAST, frmTestgui .getContentPane());
        progressBar.setValue(0);
        frmTestgui.getContentPane().add(progressBar );
      
        storageInfo = new JLabel();
		springLayout.putConstraint(SpringLayout.NORTH, storageInfo, 10, SpringLayout.SOUTH, progressBar);
		springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, storageInfo, 0, SpringLayout.HORIZONTAL_CENTER, frmTestgui.getContentPane());
		File dbFolder = new File("db/database/");
		if (dbFolder.exists()) {
	        long sss = folderSize(new File("db/database/"));
			String dir = Double.toString((double)sss/(1024*1024));
			storageInfo.setText("Storage Usage is: " + dir + " MB");
			progressBar.setValue((int)((double)sss*5/(1024*1024)));
		}
		else {
			storageInfo.setText("Storage Usage is: 0 MB");
		}
        frmTestgui.getContentPane().add(storageInfo);
      
        textField = new JTextField();
        springLayout.putConstraint( SpringLayout. EAST, textField , -264, SpringLayout. EAST, frmTestgui .getContentPane());
        springLayout.putConstraint( SpringLayout. NORTH, comboBox, 40, SpringLayout. SOUTH, textField );
        springLayout.putConstraint( SpringLayout. SOUTH, textField , 98, SpringLayout. NORTH, frmTestgui .getContentPane());
        springLayout.putConstraint( SpringLayout. NORTH, textField , 77, SpringLayout. NORTH, frmTestgui .getContentPane());
        springLayout.putConstraint( SpringLayout. WEST, textField , 70, SpringLayout. WEST, frmTestgui .getContentPane());
        frmTestgui.getContentPane().add(textField);
        textField.setColumns(10);
      
        // Create a file chooser
        fc = new JFileChooser();
   
        btnOpen = new JButton("Open" );
        btnOpen.addActionListener( this );
        springLayout.putConstraint( SpringLayout. EAST, btnSave, 99, SpringLayout. EAST, btnOpen);
        springLayout.putConstraint( SpringLayout. NORTH, btnRetrieve , 38, SpringLayout. SOUTH, btnOpen);
        springLayout.putConstraint( SpringLayout. WEST, btnSave, 18, SpringLayout. EAST, btnOpen);
        springLayout.putConstraint( SpringLayout. WEST, btnOpen, 19, SpringLayout. EAST, textField );
        springLayout.putConstraint( SpringLayout. EAST, btnOpen, 100, SpringLayout. EAST, textField );
        springLayout.putConstraint( SpringLayout. SOUTH, btnOpen, 99, SpringLayout. NORTH, frmTestgui .getContentPane());
        springLayout.putConstraint( SpringLayout. NORTH, btnOpen, 76, SpringLayout. NORTH, frmTestgui .getContentPane());
        frmTestgui.getContentPane().add(btnOpen );
    }
       
    public void actionPerformed(ActionEvent arg0) {
        //Handle open button action.
        if (arg0.getSource() == btnOpen ) {
            int returnVal = fc.showOpenDialog(TestGUI.this);
 
            if (returnVal == JFileChooser. APPROVE_OPTION) {
                file = fc.getSelectedFile();
                textField .setText(file .getName());
            } else {
                /*Error Window*/
                JOptionPane. showMessageDialog( frmTestgui, "An error occured. Please try again.");
            }
        //Handle save button action.
        } else if (arg0.getSource() == btnSave) {
            if (textField.getText().length() < 1)
                JOptionPane.showMessageDialog( frmTestgui, "Filename cannot be empty.");
            else {
                try {
                    String a = file.getAbsolutePath();
        			String[] b = a.split(":");
        			StoreFile readFile = new StoreFile();
        			
        			progressMonitor = new ProgressMonitor(TestGUI.this,
                            "Running a Long Task",
                            "", 0, 100);
					progressMonitor.setProgress(0);
					taskSave = new Task();
					taskSave.addPropertyChangeListener(this);
					taskSave.execute();
		        	btnSave.setEnabled(false);
		        	btnRetrieve.setEnabled(false);
		        	btnOpen.setEnabled(false);

        			if (b.length>1) {
    			        String [] c = b[1].split("\\\\");
        				
    			        String path="";
    			        for(String d:c)
    			            path += "/" + d;
    			       // path=path.substring(1,path.length());
    			        System.out.println(c[c.length-1]);
    			        
    			        File ff = new File(NAMEPATH);
    			        if (ff.exists()) {
    			             ReadInFile listFiles = new ReadInFile( NAMEPATH, "byte");
    			             String[] filenames = listFiles.ss.get(0).getFileContent().split("[\r\n]");
    			             ArrayList<String> fns = new ArrayList(Arrays.asList(filenames));
    			             if (!fns.contains(c[c.length-1]))
    			             {
    			            	 readFile = new StoreFile(path);
    			             }
    			             else
    			             {
    			            	 falsesave=true;
    			            	 JOptionPane. showMessageDialog( frmTestgui, "File already in the storage.");
    			            	
    			             }
    			            	 
    			        } 
    			        else 
    			        {
    			            	 readFile = new StoreFile(path);
    			            }
    			        
//    			        readFile = new StoreFile(path);
        			}
        			else
        			{
        			    readFile = new StoreFile(file.getAbsolutePath());
        			}
        			
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
            }
        //Handle retrieve button action.
        } else if (arg0.getSource() == btnRetrieve) {
            try {
            	progressMonitor = new ProgressMonitor(TestGUI.this,
                        "Running a Long Task",
                        "", 0, 100);
				progressMonitor.setProgress(0);
				taskRetrieve = new Task();
				taskRetrieve.addPropertyChangeListener(this);
				taskRetrieve.execute();
	        	btnSave.setEnabled(false);
	        	btnRetrieve.setEnabled(false);
	        	btnOpen.setEnabled(false);
	        	
                ReceiveFile retrieveFile = new ReceiveFile("retrievefolder" , model .getSelectedItem().toString());
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
        }
    }
 
    /**
     * Invoked when task's progress property changes.
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName() ) {
            int progress = (Integer) evt.getNewValue();
            progressMonitor.setProgress(progress);
            String message =
                String.format("Completed %d%%.\n", progress);
            progressMonitor.setNote(message);
            Task task = (Task) evt.getSource();
            
            if (progressMonitor.isCanceled() || task.isDone()) {
                Toolkit.getDefaultToolkit().beep();
                if (progressMonitor.isCanceled()) {
                    task.cancel(true);
                    if (task == taskSave)
                    	JOptionPane. showMessageDialog( frmTestgui, "File save failed.");
                    else if (task == taskRetrieve)
                    	JOptionPane. showMessageDialog( frmTestgui, "File retrieval failed.");
                }
                else {
                    if (task == taskSave) {
                    	System.out.println(falsesave);
                    	if (!falsesave){
                    	JOptionPane. showMessageDialog( frmTestgui, "File save completed.");
            			
            			long sss = folderSize(new File("db/database/"));
            			String dir = Double.toString((double)sss/(1024*1024));
            			storageInfo.setText("Storage Usage is: "+ dir +" MBs");
            			progressBar.setValue((int)((double)sss*5/(1024*1024)));

                        model.addElement(textField.getText());
                        //falsesave=false;
                    	}
                    	else
                    	{
                    		falsesave=false;
                    	}
                    	
                    }
                    else if (task == taskRetrieve)
                    	JOptionPane.showMessageDialog( frmTestgui, "File retieval completed.");
                }
            	btnSave.setEnabled(true);
            	btnRetrieve.setEnabled(true);
            	btnOpen.setEnabled(true);
            }
        }
 
    }
}