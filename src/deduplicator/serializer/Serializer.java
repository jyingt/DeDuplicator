package deduplicator.serializer;

import java.io.*;

/**
 * Object serialization
 * @author John Ying
 */
public class Serializer
{
    /**
     * Write object to file specified at path
     * @param object the object to serialize
     * @param path the path to write data to
     * @return serialization success or failure
     */
    public static boolean serializeObjectToPath(Object object, String path) {
        try {
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(object);
            objectOut.close();
            fileOut.close();
            System.out.println("Serialized object to path: " + path);
            
            return true;
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        
        System.out.println("FAILED to serialize object to path: " + path);
        
        return false;
    }
    
    /**
     * Read object from file specified at path
     * @param path the path to read object from
     * @return the object to be read from file
     */
    public static Object deserializeObjectFromPath(String path) {
        try {
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            
            Object object = in.readObject();
            in.close();
            fileIn.close();
            System.out.println("Deserialized object from path: " + path);
            
            return object;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        System.out.println("FAILED to deserialize object from path: " + path);
        
        return null;
    }
}
