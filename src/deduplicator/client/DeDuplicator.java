package deduplicator.client;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import deduplicator.compare.StringComparison;
import deduplicator.compare.StringComparison.Change;
import deduplicator.encoder.MainSaving;
import deduplicator.encoder.ReadInFile;
import deduplicator.encoder.ReadInFile.SaveLet;

/**
 * Main block
 * @author John Ying
 *
 */
public class DeDuplicator
{   
    public static void main(String[] args) throws ClassNotFoundException, NoSuchAlgorithmException, IOException {
//    	ReadInFile rr1 =new ReadInFile("file1.txt");
//    	ReadInFile rr2 =new ReadInFile("file4.txt");
//        ArrayList<SaveLet> sl1 = rr1.ss;
//       ArrayList<SaveLet> sl2 = rr2.ss;
//       StringComparison sc = new StringComparison(sl1.get(0).getFileContent(),sl2.get(0).getFileContent());
//       sc.show();
  
        //stepcheckV2(file1.get(0).toString(),file2.get(0).toString());
        //readinfile.readinmain("test");
        MainSaving m1 = new MainSaving("file1.txt");
       MainSaving m2 = new MainSaving("file2.txt");
        MainSaving m3 = new MainSaving("file3.txt");
        MainSaving m4 = new MainSaving("file4.txt");
        //initialhashcodegenerator.writefile("test");

        /*
        // Test file
        File file;
        
        // Reader for test file
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
        
        // EXAMPLE: Read file line by line
        // Reference: http://www.mkyong.com/java/how-to-read-file-from-java-buffReader-example/
        System.out.println("Reading " + file.getName() + " line by line...");
        
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        
        String str;
        while((str = bufferedReader.readLine()) != null)
            System.out.println(str);
        
        bufferedReader.close();
        fileReader.close();
        
        // STORE
        // convert to char array?
        // send to compress
        // then store
        
        // RETRIEVE
        // get file info from database
        // decompress
        // deliever to client
        
        /*
        // Create file
        File file = new File("test_file_01.txt");
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
        */
    }
    public static void log(String a)
    {
    	System.out.println(a);
    }
    public static void log(Integer a)
    {
    	System.out.println(a);
    	
    }
}
