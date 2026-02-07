package org.taskm.models;

import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import org.taskm.cli.Result;
import org.taskm.services.Session;

public class Topic extends Node implements TopicOperator , TopicIterator , TaskOperator{
    private int maxId;
    private String title;
    private int nbOfTasks;
    private int nbOfTopics;
    private List<Node> children ;
    private List<Task> childrenTasks;
    private List<Topic> childrenTopics;
   
    public Topic(int id ){
        this.id = id;
    }
    
    public Topic (Topic topic){
        super(topic.getId() , topic.getParent() , topic.getCreationDate() , topic.getUpdateDate() );
        this.maxId = topic.getMaxId();
        this.title = new String(topic.getTitle());
        this.children = new ArrayList<>(topic.getChildren());
        this.childrenTasks = new ArrayList<>(topic.getChildrenTasks());
        this.childrenTopics = new ArrayList<>(topic.getChildrenTopics());
        this.nbOfTasks = this.childrenTasks.size();
        this.nbOfTopics = this.childrenTopics.size();
    }

    public Topic(Topic parent , LocalDate dateOfCreation , LocalDate dateOfUpdate ,
            List<Task> childrenTasks , List<Topic> childrenTopics  , List<Node> children ,
            String title,
            int maxId){
        super(parent,dateOfCreation,dateOfCreation);
        this.title = title ;
        this.childrenTasks = childrenTasks;
        this.childrenTopics = childrenTopics;
        this.children = children;
        this.maxId = maxId;
        this.nbOfTasks = childrenTasks.size();
        this.nbOfTopics = childrenTopics.size();
    }
    public Topic(int id ,Topic parent , LocalDate dateOfCreation , LocalDate dateOfUpdate ,
            List<Task> childrenTasks , List<Topic> childrenTopics  , List<Node> children ,
            String title,
            int maxId){
        super(id , parent,dateOfCreation,dateOfCreation);
        this.title = title ;
        this.childrenTasks = childrenTasks;
        this.childrenTopics = childrenTopics;
        this.children = children;
        this.maxId = maxId;
        this.nbOfTasks = childrenTasks.size();
        this.nbOfTopics = childrenTopics.size();
    }
    public String getTitle(){return title;}
    public int getNbOfTasks(){return nbOfTasks;}
    public int getNbOfTopics(){return nbOfTopics;}
    public List<Node> getChildren(){return children;}
    public List<Task> getChildrenTasks(){return childrenTasks;}
    public List<Topic> getChildrenTopics(){return childrenTopics;}
    public int getMaxId(){return maxId;}

    public void setTitle(String title){this.title = title;}
    public void setChildren (List<Node> children){this.children = children;}
    public void setChildrenTasks(List<Task> childrenTasks ){
        this.childrenTasks = childrenTasks; 
        this.nbOfTasks = childrenTasks.size();}
    public void setChildrenTopics(List<Topic> childrenTopics ){
        this.childrenTopics = childrenTopics;
        this.nbOfTopics = childrenTopics.size();}
    public void setMaxId(int id){this.maxId = id;}

    @Override
    public void addTopic(List<Topic> children){
        this.childrenTopics.addAll(children.stream().filter( child -> {
            return childrenTopics.stream().allMatch( task -> {
               return !child.getTitle().equals(task.getTitle()); 
            });
        }).toList());
        this.children.addAll(children);
        this.nbOfTopics += children.size();
    }
    @Override
    public void addTask(List<Task> children){
        this.childrenTasks.addAll(children);
        this.nbOfTasks += children.size();
        this.children.addAll(children);
    }
    @Override
    public void updateTopic(String title){}
    @Override
    public void updateTask(String description){}
    @Override
    public void deleteTopic(List<String> topics){
        for (String t : topics){
            Topic topic = getTopic(t);
            if (topic == null){
                Result<Void> result = new Result<Void>(false, "Unfound topic name :" + t, null);
                result.printMessage();
            }
            else {
                childrenTopics.remove(topic);         
                nbOfTopics--;
            }
        }
    }
    @Override
    public void deleteTask(List<Integer> tasks){
        for (Integer t : tasks){
            Task task = getTask(t);
            if (task == null){
                Result<Void> result = new Result<Void>(false, "Unfound task id :" + t, null);
                result.printMessage();
            }
            else {
                childrenTasks.remove(task);
                nbOfTasks--;
            }
        }
    }

    @Override
    public void markTask(Task.TaskStatus status){}

    @Override
    public boolean hasNext(){
        return (nbOfTopics != 0);
    }
    @Override 
    public boolean hasPrev(){
        return parent != null;
    }
    @Override
    public Topic next(String name){
        if (!hasNext())
            return null;
        List<Topic> topics = childrenTopics.stream().filter( topic -> {
                return topic.getTitle().equals(name);
        }).toList(); 
        return topics == null || topics.isEmpty() ? null : topics.getFirst();
    }
    @Override
    public Topic prev(){
        if (!hasPrev())
            return null;
        return parent;
    }
    @Override
    public void BFS(Consumer<Topic> consumer){
        Topic head = this;

        Deque<Topic> queue = new ArrayDeque<>();
        queue.add(head);
        Set<Topic> visited = new HashSet<>();

        while (!queue.isEmpty()){
            Topic current = queue.getFirst();
            queue.pollFirst();
            if (visited.contains(current))
                continue;
            visited.add(current);
            queue.addAll(current.getChildrenTopics());
            if (current.parent == null){
                queue.addAll(Session.getSession().getStorage().getTopics().stream().filter( a -> a == this).toList());
            }
            else 
                queue.add(parent);

            consumer.accept(current);
        }

    }
    @Override
    public void DFS(Consumer<Topic> consumer){
        Topic head = this;

        Deque<Topic> stack = new ArrayDeque<>();
        stack.add(head);
        Set<Topic> visited = new HashSet<>();

        while (!stack.isEmpty()){
            Topic current = stack.getLast();
            stack.pollLast();
            if (visited.contains(current))
                continue;
            visited.add(current);
            stack.addAll(current.getChildrenTopics());
            if (current.parent == null){
                stack.addAll(Session.getSession().getStorage().getTopics().stream().filter( a -> a == this).toList());
            }
            else 
                stack.add(parent);

            consumer.accept(current);
        }

    }

    @Override
    public Task getTask(int id){
        List<Task> tmp = childrenTasks.stream().filter(task -> task.getId() == id).toList();
        return tmp.isEmpty() ? null : tmp.getFirst();
    }
    @Override
    public Topic getTopic(String name){
        List<Topic> tmp = childrenTopics.stream().filter(topic -> topic.getTitle().equals(name)).toList();
        return tmp.isEmpty() ? null : tmp.getFirst();
    }
}
