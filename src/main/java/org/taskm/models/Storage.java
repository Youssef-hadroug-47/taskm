package org.taskm.models;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    
    private List<Topic> topics;
    private int maxId;
    private String name ;

    public Storage(int maxId , List<Topic> topics , String name){
        this.maxId = maxId ;
        this.topics = topics;
        this.name = name;
    }
        
    public List<Topic> getTopics(){return topics;} 
    public int getMaxId(){return maxId;}
    public String getName(){return name;}
    public void setName(String name){this.name = name;}
    public void setTopics(ArrayList<Topic> topics){this.topics = topics;}
    public void setMaxId(int maxId){this.maxId = maxId;}

}
