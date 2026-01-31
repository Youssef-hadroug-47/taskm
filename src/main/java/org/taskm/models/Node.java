package org.taskm.models;

import java.time.LocalDate;

public class Node {

    protected int id ;
    protected Topic parent ;
    protected LocalDate dateOfCreation;
    protected LocalDate dateOfUpdate;

    protected Node(){}
    protected Node(int id ,Topic parent , LocalDate dateOfCreation , LocalDate dateOfUpdate){
        this.id = id;
        this.parent = parent;
        this.dateOfCreation = dateOfCreation;
        this.dateOfUpdate = dateOfUpdate;
    }

    public int getId(){return id;}
    public Topic getParent(){return parent;}
    public LocalDate getCreationDate(){return dateOfCreation;}
    public LocalDate getUpdateDate(){return dateOfUpdate;}
    
    public void setParent(Topic parent){this.parent = parent;}
    public void setDateOfCreation(LocalDate dateOfCreation){this.dateOfCreation = dateOfCreation;}
    public void setDateOfUpdate (LocalDate dateOfUpdate){this.dateOfUpdate = dateOfUpdate;}

}
