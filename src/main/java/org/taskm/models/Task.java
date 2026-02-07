package org.taskm.models;

import java.time.LocalDate;

public class Task extends Node implements TaskOperator{

     public enum TaskStatus {
        done,
        inprogress,
        todo,
    }   

    private TaskStatus status;
    private String description;
   
    public Task(int id){
        this.id = id;
    }
    public Task(LocalDate dateOfCreation , LocalDate dateOfUpdate , Topic parent , TaskStatus status , String description){
        super(parent , dateOfCreation , dateOfUpdate);
        this.status = status;
        this.description = description;
    }
    public Task(int id ,LocalDate dateOfCreation , LocalDate dateOfUpdate , Topic parent , TaskStatus status , String description){
        super(id , parent , dateOfCreation , dateOfUpdate);
        this.status = status;
        this.description = description;
    }   
    public TaskStatus getStatus(){return status;}
    public String getDescription(){return description;}
    
    @Override
    public void markTask(Task.TaskStatus status){this.status = status;}
    public void updateTask(String description){this.description = description;}
}
