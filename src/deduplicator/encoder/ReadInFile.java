package deduplicator.encoder;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;


/**
 * Read at a specified path and return contents of file or folder
 * @author Yuteng Pan, Hansen Zhang
 *
 */
public class ReadInFile
{
    public ArrayList<SaveLet> ss;
    
    /**
     * Default constructor
     */
    public ReadInFile() {
        // Do something here?
    }
    
    /**
     * Overloaded constructor
     * @param file
     * @throws ClassNotFoundException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public ReadInFile(String file,String type) throws ClassNotFoundException, NoSuchAlgorithmException, IOException {
        ss = readInMain(file,type);
    }
   
    /**
     * Open file and read by byte or folder
     * @param name
     * @return
     * @throws ClassNotFoundException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public ArrayList<SaveLet> readInMain(String name, String type) throws ClassNotFoundException, NoSuchAlgorithmException, IOException {
        File file = new File(name);
        if(type=="line")
        	return readInByLine(name);
        if (file.isFile())
            return readInByByte(name, file);
        else
            return readInByFolder(name, file);
    }
    
    /**
     * Read in file by byte
     * @param filename
     * @param file
     * @return
     * @throws IOException
     */
    public ArrayList<SaveLet> readInByByte(String filename, File file) throws IOException {
        ArrayList<SaveLet> save = new ArrayList<SaveLet>();
        FileInputStream fin = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        new DataInputStream(fin).readFully(buffer);
        
        fin.close();
        
        String result = new String(buffer, "UTF-8");
        SaveLet sa =  new SaveLet(filename, result);
        
        save.add(sa);
        
        return save;
    }
    public ArrayList<SaveLet> readInByLine(String filename) throws IOException {
        ArrayList<SaveLet> save = new ArrayList<SaveLet>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String text;
			while( (text=reader.readLine())!=null)
			{
				save.add(new SaveLet(filename,text));
			}
		} catch (FileNotFoundException e) {
			System.err.println("Could not open file " + filename);
			System.exit(-1);
		}
        
        return save;
    }
    
    
    /**
     * Read in contents of a folder
     * @param foldername
     * @param folder
     * @return
     * @throws IOException
     */
    public ArrayList<SaveLet> readInByFolder(String foldername, File folder) throws IOException {
        File[] listoffiles = folder.listFiles();
        ArrayList<SaveLet> result = new ArrayList<SaveLet>();
        
        for (File file: listoffiles) {
            result.add(readInByByte(file.getName(),file).get(0));
        }
        
        return result;
    }
    
    /**
     * Container for file contents
     * @author Yuteng Pan, Hansen Zhang
     *
     */
    public class SaveLet
    {
        public String filename, filecontent;
        
        public SaveLet()
        {
        	
        }
        public SaveLet(String filenames, String filecontents) {
            filename = filenames;
            filecontent = filecontents;
        }
        
        public String getFileName() {
            return filename;
        }
        
        public String getFileContent() {
            return filecontent;
        }
        
        public void setFileName(String filenames) {
            filename = filenames;
        }
        
        public void setfilecontent(String filecontents) {
            filecontent = filecontents;
        }
    }
}
