package org.taskm.models;

import java.util.ArrayList;

public class Storage {
    
    private ArrayList<Topic> topics;
    private int maxId;
    private String name ;

    public Storage(int maxId , ArrayList<Topic> topics , String name){
        this.maxId = maxId ;
        this.topics = topics;
        this.name = name;
    }
        
    public ArrayList<Topic> getTopics(){return topics;} 
    public int getMaxId(){return maxId;}
    public String getName(){return name;}
    public void setName(String name){this.name = name;}
    public void setTopics(ArrayList<Topic> topics){this.topics = topics;}
    public void setMaxId(int maxId){this.maxId = maxId;}

}
