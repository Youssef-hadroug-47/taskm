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
    public Topic getTopicByName(String name){
        List<Topic> tmp = topics.stream().filter(topic -> {
            return name.equals(topic.getTitle());
        }).toList();
        return tmp != null && !tmp.isEmpty() ? tmp.getFirst() : null;
    }
    public void addTopic(List<Topic> children){
        this.topics.addAll(children.stream().filter( child -> {
            return topics.stream().allMatch( task -> {
               return !child.getTitle().equals(task.getTitle()); 
            });
        }).toList());
    }

}
