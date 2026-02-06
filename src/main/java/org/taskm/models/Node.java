package org.taskm.models;

import java.time.LocalDate;

import org.taskm.services.Session;

public class Node {

    protected int id ;
    protected Topic parent ;
    protected LocalDate dateOfCreation;
    protected LocalDate dateOfUpdate;

    protected Node(){}
    protected Node(Topic parent , LocalDate dateOfCreation , LocalDate dateOfUpdate){
        
        if (parent != null) {
            this.id = parent.getMaxId();
            parent.setMaxId(this.id+1);
        }
        else {
            this.id = Session.getSession().getStorage().getMaxId();
            Session.getSession().getStorage().setMaxId(this.id+1);
        }
        this.parent = parent;
        this.dateOfCreation = dateOfCreation;
        this.dateOfUpdate = dateOfUpdate;
    }
    protected Node(int id ,Topic parent , LocalDate dateOfCreation , LocalDate dateOfUpdate){
        this.id = id ;
        this.parent = parent;
        this.dateOfCreation = dateOfCreation;
        this.dateOfUpdate = dateOfUpdate;
    }
    public int getId(){return id;}
    public Topic getParent(){return this.parent;}
    public LocalDate getCreationDate(){return dateOfCreation;}
    public LocalDate getUpdateDate(){return dateOfUpdate;}
    
    public void setParent(Topic parent){this.parent = parent;}
    public void setDateOfCreation(LocalDate dateOfCreation){this.dateOfCreation = dateOfCreation;}
    public void setDateOfUpdate (LocalDate dateOfUpdate){this.dateOfUpdate = dateOfUpdate;}

}
