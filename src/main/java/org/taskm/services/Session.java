package org.taskm.services;

import java.io.File;
import java.util.ArrayList;

import org.taskm.models.Storage;
import org.taskm.models.Topic;

public class Session {

    private Storage storage ;
    private static Session session ;
    private Topic current_topic ;
    private String topicAbsolutePath ;
    private ArrayList<File> availableStorages ;

    private Session (){
        this.topicAbsolutePath = "/";
        current_topic = null;
        storage = null;
        // Detect available storages ;
    }
    public static Session getSession(){
        if (session == null)
            session = new Session();
        return session;
    }

    public void setTopic(Topic newTopic){this.current_topic = newTopic;} 
    public void setAbsolutePath(String path){this.topicAbsolutePath = path;}
    public void setStorage(Storage storage){this.storage = storage;}
    public void setAvailableStorages(ArrayList<File> availableStorages){this.availableStorages = availableStorages;}

    public Topic getTopic(){return this.current_topic;}
    public String getPath(){return this.topicAbsolutePath;}
    public Storage getStorage(){return this.storage;}
    public ArrayList<File> getAvailableStorages(){return this.availableStorages;}
    
}
