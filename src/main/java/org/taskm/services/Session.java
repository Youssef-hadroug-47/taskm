package org.taskm.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.taskm.cli.Result;
import org.taskm.models.Storage;
import org.taskm.models.Topic;

public class Session {

    private static Session session ;
    private Topic current_topic ;
    private String topicAbsolutePath ;
    private Storage currentStorage ;
    private List<File> availableStorages ;
    private File currentFile;

    private final String home = System.getenv("HOME");
    private final String globalStoragePath = home+"/.taskm/resources" ;
    private final String fileName = "/storage.json";

    private Session (){
        this.topicAbsolutePath = "/";
        current_topic = null;
        currentStorage = null;
        currentFile = null;
        availableStorages = new ArrayList<>();

        try {
            Files.createDirectories(Path.of(globalStoragePath));
            if (Files.notExists(Path.of(globalStoragePath+fileName))){
                Files.createFile(Path.of(globalStoragePath+fileName));
            }
            File homeFile = new File(globalStoragePath+fileName);
            if (homeFile.length() == 0){
                JSONStorage.initStorage(homeFile, "Home");
            }
            availableStorages.add(homeFile);

            String current_dir = System.getProperty("user.dir"); 
            if (Files.exists(Path.of(current_dir+"/.taskm/storage.json"), LinkOption.NOFOLLOW_LINKS) ){
                availableStorages.add(new File(current_dir+"/.taskm/storage.json"));
            }
        }
        catch (IOException e){
            System.out.println("Global storage directory error !");
            return;
        }
        
    }
    public static Session getSession(){
        if (session == null)
            session = new Session();
        return session;
    }

    public void setTopic(Topic newTopic){this.current_topic = newTopic;} 
    public void setAbsolutePath(String path){this.topicAbsolutePath = path;}
    public void setStorage(File storage){
        this.currentFile = storage;
        Result<Storage> res = JSONStorage.load(storage);
        if(res.getSuccess()) this.currentStorage = res.getValue();
        else System.out.println(res.getMessage());
    }
    public void setAvailableStorages(List<File> availableStorages){this.availableStorages = availableStorages;}

    public File getFile(){return this.currentFile;}
    public Topic getTopic(){return this.current_topic;}
    public String getPath(){return this.topicAbsolutePath;}
    public Storage getStorage(){return this.currentStorage;}
    public List<File> getAvailableStorages(){return this.availableStorages;}
    
}
