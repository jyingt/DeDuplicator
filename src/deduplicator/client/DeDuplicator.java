package deduplicator.client;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import deduplicator.compare.StringComparison;
import deduplicator.encoder.MainSaving;

/**
 * Main block
 * @author John Ying
 *
 */
public class DeDuplicator
{   
    public static void main(String[] args) throws ClassNotFoundException, NoSuchAlgorithmException, IOException {
        //ArrayList<String> file1 = readinfile.readinmain("test/plain.txt");
        //ArrayList<String> file2 = readinfile.readinmain("test/change.txt");
        
        //stepcheckV2(file1.get(0).toString(),file2.get(0).toString());
        //readinfile.readinmain("test");
        MainSaving mm = new MainSaving("test/plain.txt", null, "write");
        //initialhashcodegenerator.writefile("test");
        //readinfile.readinbybyte("plain.txt");
        StringComparison tt = new StringComparison();
        tt.findDiff("abcdefghijkl", "defghijk", 0);
        tt.show();
    
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
}
