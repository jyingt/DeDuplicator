package deduplicator.client;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import deduplicator.compare.StringComparison;
import deduplicator.compare.StringComparison.Change;
import deduplicator.decoder.MainRetrieving;
import deduplicator.encoder.MainSaving;
import deduplicator.encoder.ReadInFile;
import deduplicator.encoder.ReadInFile.SaveLet;

/**
 * Main block
 * @author John Ying
 */
public class DeDuplicator
{   
    public static void main(String[] args) throws ClassNotFoundException, NoSuchAlgorithmException, IOException
    {
        /*
        File file;
        FileReader fileReader;
        
        // Command line arguments
        if (args.length != 2) {
            // Leave program
            System.out.println("usage: store -file [file]");
            //System.out.println("usage: store -file [file] -locker [locker location]");
            return;
        }
        else {
            // Reads file name from argument
            if (args[0].equals("-file")) {
                file = new File(args[1]);
                fileReader = new FileReader(file);
            }
            else {
                return;
            }
        }
        */
        
        //ReadInFile rr1 =new ReadInFile("/Users/ssec/Documents/GitHub/DeDuplicator/file1.txt","line");
       // log(rr1.ss.get(0).getFileContent());
        //ReadInFile rr2 =new ReadInFile("file4.txt");
        
        //ArrayList<SaveLet> sl1 = rr1.ss;
        //ArrayList<SaveLet> sl2 = rr2.ss;
        
        //StringComparison sc = new StringComparison(sl1.get(0).getFileContent(),sl2.get(0).getFileContent());
        //sc.show();
  
        //stepcheckV2(file1.get(0).toString(),file2.get(0).toString());
        //readinfile.readinmain("test");
     // MainSaving m3 = new MainSaving("file2.txt");
      MainSaving m1 = new MainSaving("file3.txt");
      
        MainSaving m2 = new MainSaving("testsavingfolder");
       MainSaving m3 = new MainSaving("testsavingfolder2");
//        MainSaving m2 = new MainSaving("file2.txt");
//        MainSaving m3 = new MainSaving("file3.txt");
//        MainSaving m4 = new MainSaving("file4.txt");
    	//MainRetrieving mr1 = new MainRetrieving("retrievefolder", "file1.txt");
    	//MainRetrieving mr2 = new MainRetrieving("retrievefolder", "file2.txt");
    	//MainRetrieving mr3 = new MainRetrieving("retrievefolder", "file3.txt");
    	//MainRetrieving mr4 = new MainRetrieving("retrievefolder", "file4.txt");
    	
        //initialhashcodegenerator.writefile("test");
    }
    
    /**
     * Example for serializing an object
     */
    public void serializeExample() {
        Hashtable<Integer, String> hashtable = new Hashtable<Integer, String>();
        
        // Populate hash table
        for (int ii = 0; ii < 100; ii++)
            hashtable.put(ii, "bubba");
        
        // Serialize object
        try {
            FileOutputStream fileOut = new FileOutputStream("hashtable.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            
            out.writeObject(hashtable);
            out.close();
            fileOut.close();
            
            System.out.println("Data serialized into hashtable.ser!");
        }
        catch (IOException i) {
            i.printStackTrace();
        }
        
        // Deserialize 
        Hashtable<Integer, String> result;
        
        try {
            FileInputStream fileIn = new FileInputStream("hashtable.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            
            result = (Hashtable<Integer, String>) in.readObject();
            in.close();
            fileIn.close();
            
            System.out.println("Data deserialized from hashtable.ser!");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * File write/read example
     * @throws IOException
     */
    public void fileExample() {
        // WRITE FILE EXAMPLE
        File file = new File("sample.txt");
        try {
            file.createNewFile();
            
            // Write to file
            FileWriter writer = new FileWriter(file);
            
            // Write 10MB of ASCII (each char is 2 bytes)
            // # of iterations = 10 * 1024B / 2B = 10 * 512B = 10B * 2^9
            for (int ii = 0; ii < 10 * (2 << 9); ii++) {
                if (ii % 27 == 26)
                    writer.write("\n");
                else
                    writer.write(Character.toString((char) ('a' + ii % 27)));
            }
            
            writer.flush();
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
        // READ FILE EXAMPLE
        file = new File("sample.txt");
        try {
            // Reader for test file
            FileReader fileReader = new FileReader(file);
            
            // EXAMPLE: Read file line by line
            // Reference: http://www.mkyong.com/java/how-to-read-file-from-java-buffReader-example/
            System.out.println("Reading " + file.getName() + " line by line...");
            
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            String str;
            while((str = bufferedReader.readLine()) != null)
                System.out.println(str);
            
            bufferedReader.close();
            fileReader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void log(String a) {
    	System.out.println(a);
    }
    
    public static void log(Integer a) {
    	System.out.println(a);
    }
}
