package org.taskm.models;

import java.time.LocalDate;
import java.util.ArrayList;

public class Topic extends Node implements TaskOperator,TopicOperator{
    private int maxId;
    private String title;
    private int nbOfTasks;
    private int nbOfTopics;
    private ArrayList<Node> children ;
    private ArrayList<Task> childrenTasks;
    private ArrayList<Topic> childrenTopics;
    
    public Topic(Node parent , LocalDate dateOfCreation , LocalDate dateOfUpdate ,
            ArrayList<Task> childrenTasks , ArrayList<Topic> childrenTopics  , ArrayList<Node> children ,
            String title,
            int maxId){
        super(parent,dateOfCreation,dateOfCreation);
        this.title = title ;
        this.childrenTasks = childrenTasks;
        this.childrenTopics = childrenTopics;
        this.children = children;
        this.maxId = maxId;
    }

    public String getTitle(){return title;}
    public int getNbOfTasks(){return nbOfTasks;}
    public int getNbOfTopics(){return nbOfTopics;}
    public ArrayList<Node> getChildren(){return children;}
    public ArrayList<Task> getChildrenTasks(){return childrenTasks;}
    public ArrayList<Topic> getChildrenTopics(){return childrenTopics;}
    public int getMaxId(){return maxId;}

    public void setTitle(String title){this.title = title;}
    public void setChildren (ArrayList<Node> children){this.children = children;}
    public void setChildrenTasks(ArrayList<Task> childrenTasks ){
        this.childrenTasks = childrenTasks; 
        this.nbOfTasks = childrenTasks.size();}
    public void setChildrenTopics(ArrayList<Topic> childrenTopics ){
        this.childrenTopics = childrenTopics;
        this.nbOfTopics = childrenTopics.size();}
    public void setMaxId(int id){this.maxId = id;}

}
