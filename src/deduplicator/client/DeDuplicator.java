package deduplicator.client;

import java.awt.EventQueue;
import java.io.*;
import java.security.*;
import java.util.*;

import deduplicator.main.*;

/**
 * Main client
 * @author John Ying
 */
public class DeDuplicator
{   
    /**
     * Client
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, NoSuchAlgorithmException, IOException
    {
        // Command line arguments
        if (args.length == 4 && args[0].equals("-file") && args[2].equals("-locker")) {
            // Check if specified file exists
            File file = new File(args[1]);
            if (!file.exists()) {
                System.out.println("File " + args[1] + " does not exist!");
                return;
            }

            // Store file
            new StoreFile(args[1]);
        }
        else if (args.length == 1 && args[0].equals("-gui")) {
            // Launch GUI
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
        else {
            // Leave program
            System.out.println("Invalid command line arguments!");
            System.out.println("Usage: store -file [file] -locker [locker location]");
            System.out.println("Usage: store -gui");
            return;
        }
    }
    
    /**
     * Example for serializing an object
     */
    public static void serializeExample() {
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
    public static void fileExample() {
        // WRITE FILE EXAMPLE
        File file = new File("sample.txt");
        try {
            file.createNewFile();
            
            // Write to file
            FileWriter writer = new FileWriter(file);
            
            // Write 10MB of ASCII (each char is 2 bytes)
            // # of iterations = 10 * 2^20 B / 2B = 10B * 2^19
            for (int ii = 0; ii < 10 * (2 << 19); ii++) {
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
}
