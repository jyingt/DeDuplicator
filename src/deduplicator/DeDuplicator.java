package deduplicator;

import java.io.*;
import java.util.*;

public class DeDuplicator
{
    public static void main(String[] args) throws IOException
    {
        System.out.println("This is a deduplicator... yet.");
        System.out.println("Just Checking how clone works:)-Hansen");
        
        // Sample file I/O operations
        // Example: http://www.tutorialspoint.com/java/java_filereader_class.htm
        
        // Create file
        File file = new File("hello.txt");
        file.createNewFile();
        
        // Write to file
        FileWriter writer = new FileWriter(file);
        
        // Write 10MB of ASCII (one char is 16 bits)
        for (int ii = 0; ii < 10 * (2 << 9); ii++) {
            if (ii % 27 == 26)
                writer.write("\n");
            else
                writer.write(Character.toString((char) ('a' + ii % 26)));
        }
        
        writer.flush();
        writer.close();
        
        // Read from file
        FileReader reader = new FileReader(file);
        
        // Read character by character
        char[] a = new char[50];
        reader.read(a);
        
        for (char c: a)
            System.out.print(c);
        
        // Read line by line
        // Example: http://www.mkyong.com/java/how-to-read-file-from-java-bufferedreader-example/
        BufferedReader bReader = new BufferedReader(reader);
        String str;
        while ((str = bReader.readLine()) != null)
            System.out.println(str);
        
        reader.close();
    }

    public DeDuplicator()
    {
         //
    }
}
