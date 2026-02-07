package org.taskm.models;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import org.taskm.cli.Result;


public class Storage {
    
    private List<Topic> topics;
    private int maxId;
    private String name ;

    public Storage(int maxId , List<Topic> topics , String name){
        this.maxId = maxId ;
        this.topics = topics;
        this.name = name;
    }
    public Storage(Storage storage){ 
        this.name = storage.getName();
        this.maxId = storage.getMaxId();
        this.topics = storage.getTopics();
    }
        
    public List<Topic> getTopics(){return topics;} 
    public int getMaxId(){return maxId;}
    public String getName(){return name;}
    public void setName(String name){this.name = name;}
    public void setTopics(ArrayList<Topic> topics){this.topics = topics;}
    public void setMaxId(int maxId){this.maxId = maxId;}
    
    public Topic getTopic(String name){
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
    public void deleteTopic(List<String> names){
        for (String name : names){
            Topic tmp = getTopic(name);
            if (tmp == null){
                Result<Void> result = new Result<Void>(false, "Unfound topic name :" + name, null);
                result.printMessage();
            }
            else {
               topics.remove(tmp); 
            }
        }
    }
    public void updateTopic(String name){}
    
    //@Override
    public void BFS(Consumer<Topic> consumer){
        Storage head = new Storage(this);
        
        Deque<Topic> queue = new ArrayDeque<>();  
        queue.addAll(head.getTopics());
        Set<Topic> visited = new HashSet<>();

        while(!queue.isEmpty()){
            Topic current = queue.getFirst();
            queue.pollFirst();
            if (visited.contains(current))
                continue;
            visited.add(current);
            queue.addAll(current.getChildrenTopics());
           
            consumer.accept(current);

        }

    } 
    public void DFS(Consumer<Topic> consumer){
        Storage head = this;

        Deque<Topic> stack = new ArrayDeque<>();
        stack.addAll(head.getTopics());
        Set<Topic> visited = new HashSet<>();

        while (!stack.isEmpty()){
            Topic current = stack.getLast();
            stack.pollLast();
            if (visited.contains(current))
                continue;
            visited.add(current);
            stack.addAll(current.getChildrenTopics());

            consumer.accept(current);
        }

    }

}
