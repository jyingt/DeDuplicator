package deduplicator.client;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

import java.beans.*;
import java.security.*;
import java.util.*;

import deduplicator.main.*;

/**
 * Front-end GUI for DeDuplicator
 * @author Chengxi Yang
 */
public class TestGUI extends JPanel implements ActionListener, PropertyChangeListener
{
<<<<<<< HEAD
    private static final long serialVersionUID = 1L;
=======
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
>>>>>>> a69254f87b4e5e4d28ae954c9bbb71bba0998163
    
    private final String NAMEPATH = "db/database/name.txt";
    private boolean falseSave = false;

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
    private ProgressMonitor         progressMonitor;
    private File                    file;
    private Task                    taskSave;
    private Task                    taskRetrieve;
    
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
        frameGui.setBounds(100, 100, 450, 300);
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
		File dbFolder = new File("db/database/");
		if (dbFolder.exists()) {
	        long sss = folderSize(new File("db/database/"));
			String dir = Double.toString((double) sss / (1024*1024));
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
        			
        			progressMonitor = new ProgressMonitor(TestGUI.this, "Running a long task", "", 0, 100);
					progressMonitor.setProgress(0);
					taskSave = new Task();
					taskSave.addPropertyChangeListener(this);
					taskSave.execute();
		        	btnSave.setEnabled(false);
		        	btnRetrieve.setEnabled(false);
		        	btnOpen.setEnabled(false);

        			if (b.length > 1) {
    			        String[] c = b[1].split("\\\\");
    			        
    			        String path = "";
    			        for(String str : c) {
    			            path += "/" + str;
    			        }
    			        // path = path.substring(1,path.length());
    			        System.out.println(c[c.length-1]);
    			        
			            if (new File(NAMEPATH).exists()) {
    			             ReadInFile listFiles = new ReadInFile( NAMEPATH, "byte");
    			             String[] filenames = listFiles.ss.get(0).getFileContent().split("[\r\n]");
    			             ArrayList<String> listFilenames = new ArrayList(Arrays.asList(filenames));
    			             
    			             if (!listFilenames.contains(c[c.length-1])) {
    			            	 readFile = new StoreFile(path);
    			             }
    			             else {
    			            	 falseSave = true;
    			            	 JOptionPane.showMessageDialog(frameGui, "File already in the storage.");
    			             }
    			        } 
    			        else {
    			            readFile = new StoreFile(path);
			            }
        			}
        			else {
        			    readFile = new StoreFile(file.getAbsolutePath());
        			}
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
            	progressMonitor = new ProgressMonitor(TestGUI.this, "Running a long task", "", 0, 100);
				progressMonitor.setProgress(0);
				taskRetrieve = new Task();
				taskRetrieve.addPropertyChangeListener(this);
				taskRetrieve.execute();
	        	btnSave.setEnabled(false);
	        	btnRetrieve.setEnabled(false);
	        	btnOpen.setEnabled(false);
	        	
                new ReceiveFile("retrievefolder", comboBoxModel.getSelectedItem().toString());
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
     * Invoked when task's progress property changes
     */
    public void propertyChange(PropertyChangeEvent event) {
        if ("progress" == event.getPropertyName()) {
            int progress = (Integer) event.getNewValue();
            progressMonitor.setProgress(progress);
            
            String message = String.format("Completed %d%%.\n", progress);
            progressMonitor.setNote(message);
            
            Task task = (Task) event.getSource();
            
            if (progressMonitor.isCanceled() || task.isDone()) {
                Toolkit.getDefaultToolkit().beep();
                
                if (progressMonitor.isCanceled()) {
                    task.cancel(true);
                    
                    if (task == taskSave)
                    	JOptionPane.showMessageDialog(frameGui, "File save failed.");
                    else if (task == taskRetrieve)
                    	JOptionPane.showMessageDialog(frameGui, "File retrieval failed.");
                }
                else {
                    if (task == taskSave) {
                    	System.out.println(falseSave);
                    	
                    	if (!falseSave) {
                        	JOptionPane.showMessageDialog(frameGui, "File save completed.");
                			
                			long sss = folderSize(new File("db/database/"));
                			String dir = Double.toString((double) sss / (1024*1024));
                			labelStorage.setText("Storage Usage is: "+ dir +" MBs");
                			progressBar.setValue((int) ((double) sss*5 / (1024*1024)));
    
                            comboBoxModel.addElement(textFieldFile.getText());
                            // falseSave = false;
                    	}
                    	else {
                    		falseSave = false;
                    	}
                    	
                    }
                    else if (task == taskRetrieve) {
                    	JOptionPane.showMessageDialog(frameGui, "File retrieval completed.");
                    }
                }
                
            	btnSave.setEnabled(true);
            	btnRetrieve.setEnabled(true);
            	btnOpen.setEnabled(true);
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
            if (file.isFile()) {
                length += file.length();
            }
            else {
                length += folderSize(file);
            }
        }
        
        return length;
    }
    
    /**
     * Worker class for progress bar
     * @author Chengxi Yang
     */
    class Task extends SwingWorker<Void, Void> {
        public Void doInBackground() {
            Random random = new Random();
            int progress = 0;
            setProgress(0);
            
            try {
                Thread.sleep(1000);
                
                while (progress < 100 && !isCancelled()) {
                    Thread.sleep(random.nextInt(10));   // sleep for up to one second
                    progress += random.nextInt(10);     // make random progress
                    setProgress(Math.min(progress, 100));
                }
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            return null;
        }
 
        public void done() {
            btnSave.setEnabled(true);
            btnRetrieve.setEnabled(true);
            btnOpen.setEnabled(true);
            progressMonitor.close();
        }
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