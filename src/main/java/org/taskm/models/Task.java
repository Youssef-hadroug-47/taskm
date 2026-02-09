package org.taskm.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;

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
    @Override
    public void updateTask(String description){this.description = description;}
    
    public void miniPrint(){
        int statusColorR = 0;
        int statusColorB = 0;
        int statusColorG = 0;
        switch (this.getStatus()) {
            case done:
                statusColorR = 16;
                statusColorB = 185;
                statusColorG = 129; 
                break;
            case inprogress:
                statusColorR = 245;
                statusColorB = 158;
                statusColorR = 11;
                break;
            case todo:
                statusColorR = 59;
                statusColorB = 130;
                statusColorG = 246;
                break;
        }
        AttributedStringBuilder builder = new AttributedStringBuilder();

        System.out.println( builder
                .style(AttributedStyle.DEFAULT.foreground(statusColorR,statusColorB,statusColorG).italic())
                .append("  • [" + this.getStatus().toString().toUpperCase() + "] " )
                .style(AttributedStyle.DEFAULT.foreground(100, 116, 139).bold())
                .append(this.getDescription() + "\n")
                .toAnsi()
                );


    }
    public void verbosePrint(){
        int statusColorR = 0;
        int statusColorB = 0;
        int statusColorG = 0;
        switch (this.getStatus()) {
            case done:
                statusColorR = 16;
                statusColorB = 185;
                statusColorG = 129; 
                break;
            case inprogress:
                statusColorR = 245;
                statusColorB = 158;
                statusColorR = 11;
                break;
            case todo:
                statusColorR = 59;
                statusColorB = 130;
                statusColorG = 246;
                break;
        }
        AttributedStringBuilder builder = new AttributedStringBuilder();
        System.out.println( builder
                .style(AttributedStyle.DEFAULT)
                .append("#" + this.getId())
                .style(AttributedStyle.DEFAULT.foreground(100, 116, 139).bold())
                .append(" • " + this.getDescription() + "\n")
                .style(AttributedStyle.DEFAULT.foreground(statusColorR,statusColorB,statusColorG).italic())
                .append("    " + this.getStatus().toString().toUpperCase())
                .style(AttributedStyle.DEFAULT.foreground(156, 163, 175).italic())
                .append(" • Created " 
                    + this.getCreationDate().format(DateTimeFormatter.ofPattern("MMM d")).toString() 
                    + " →  Updated " 
                    + this.getUpdateDate().format(DateTimeFormatter.ofPattern("MMM d")).toString())
                .toAnsi()
                );

    }
}
