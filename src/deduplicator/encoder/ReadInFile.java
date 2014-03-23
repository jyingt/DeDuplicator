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
    public ArrayList<savelet> ss;
    
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
    public ReadInFile(String file) throws ClassNotFoundException, NoSuchAlgorithmException, IOException {
        ss = readinmain(file);
    }
    
    /**
     * Open file and read by byte or folder
     * @param name
     * @return
     * @throws ClassNotFoundException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public ArrayList<savelet> readinmain(String name) throws ClassNotFoundException, NoSuchAlgorithmException, IOException {
        File file = new File(name);
        
        if (file.isFile())
            return readinbybyte(name, file);
        else
            return readinfolder(name, file);
    }
    
    /**
     * Read in file by byte
     * @param filename
     * @param file
     * @return
     * @throws IOException
     */
    public ArrayList<savelet> readinbybyte(String filename, File file) throws IOException {
        ArrayList<savelet> save = new ArrayList<savelet>();
        FileInputStream fin = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        new DataInputStream(fin).readFully(buffer);
        
        fin.close();
        
        String result = new String(buffer, "UTF-8");
        savelet sa =  new savelet(filename, result);
        
        save.add(sa);
        
        return save;
    }
    
    /**
     * Read in contents of a folder
     * @param foldername
     * @param folder
     * @return
     * @throws IOException
     */
    public ArrayList<savelet> readinfolder(String foldername, File folder) throws IOException {
        File[] listoffiles = folder.listFiles();
        ArrayList<savelet> result = new ArrayList<savelet>();
        
        for (File file: listoffiles) {
            result.add(readinbybyte(file.getName(),file).get(0));
        }
        
        return result;
    }
    
    /**
     * Container for file contents
     * @author Yuteng Pan, Hansen Zhang
     *
     */
    protected class savelet
    {
        public String filename, filecontent;
        
        public savelet(String filenames, String filecontents) {
            filename = filenames;
            filecontent = filecontents;
        }
        
        public String getfilename() {
            return filename;
        }
        
        public String getfilecontent() {
            return filecontent;
        }
        
        public void setfilename(String filenames) {
            filename = filenames;
        }
        
        public void setfilecontent(String filecontents) {
            filecontent = filecontents;
        }
    }
}
