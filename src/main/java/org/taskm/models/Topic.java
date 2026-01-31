package org.taskm.models;

import java.time.LocalDate;
import java.util.List;

public class Topic extends Node implements TopicIterator,TopicOperator{
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

    public Topic(int id , Topic parent , LocalDate dateOfCreation , LocalDate dateOfUpdate ,
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
    public void createTopic(){}
    @Override
    public void readTopic(){}
    @Override
    public void updateTopic(String title){}
    @Override
    public void deleteTopic(){}
}
