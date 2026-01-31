package org.taskm.models;

import java.time.LocalDate;

public class Task extends Node {
    
    private TaskStatus status;
    private String description;
   
    public Task(int id){
        this.id = id;
    }
    public Task(int id , LocalDate dateOfCreation , LocalDate dateOfUpdate , Topic parent , TaskStatus status , String description){
        super(id , parent , dateOfCreation , dateOfUpdate);
        this.status = status;
        this.description = description;
    }
    
    public TaskStatus getStatus(){return status;}
    public String getDescription(){return description;}

    public void setStatus(TaskStatus status){this.status = status;}
    public void setDescription(String description){this.description = description;}
}
