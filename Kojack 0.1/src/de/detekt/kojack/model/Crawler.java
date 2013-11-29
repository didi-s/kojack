package de.detekt.kojack.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Crawler {
	String path;
	File f = null;
	boolean isDirectory = false;	//Pfad = Directory?
    File[] paths;
    List<File> daten = new ArrayList<File>();
	
	public List<File> search(File f) {
        
        try{      
           // returns pathnames for files and directory
           paths = f.listFiles();
           // for each pathname in pathname array
           for(File filePath:paths)
           {
              // prints file and directory paths
//              System.out.println(path);
              
              try{ 
 		         // true if the file path is directory, else false
 		         isDirectory = filePath.isDirectory();
 		         
 		         if(isDirectory){
 		        	// get the path
 			         path = filePath.getPath();
 			         search(filePath);
 		         }else{
 		        	path = filePath.getPath();
 		        	daten.add(filePath);
 		        	System.out.println(path);
 		         }
              }catch(Exception e){
 		         // if any error occurs
 		         e.printStackTrace();
              }
           }
        }catch(Exception e){
           // if any error occurs
           e.printStackTrace();
        }
		return daten;
	}
}
