package org.taskm.services;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import org.taskm.models.Storage;

public class JSONStorage {
        
    private File file ;
    private FileReader fileReader ;
    private FileWriter fileWriter ;
    private static JSONStorage jsonStorage ;
    
    public JSONStorage getInstance(){
        if (jsonStorage == null)
            jsonStorage = new JSONStorage();
        return jsonStorage;
    } 

    public void load(File file){}
    public void save(){}
}
