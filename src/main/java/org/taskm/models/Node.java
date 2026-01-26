package org.taskm.models;

import java.time.LocalDate;

public class Node {

    private final int id ;
    private Node parent ;
    private LocalDate dateOfCreation;
    private LocalDate dateOfUpdate;

    protected Node(Node parent , LocalDate dateOfCreation , LocalDate dateOfUpdate){
        this.parent = parent;
        this.dateOfCreation = dateOfCreation;
        this.dateOfUpdate = dateOfUpdate;
        // id generation
    }

    public int getId(){return id;}
    public Node getParent(){return parent;}
    public LocalDate getCreationDate(){return dateOfCreation;}
    public LocalDate getUpdateDate(){return dateOfUpdate;}
    
    public void setParent(Node parent){this.parent = parent;}
    public void setDateOfCreation(LocalDate dateOfCreation){this.dateOfCreation = dateOfCreation;}
    public void setDateOfUpdate (LocalDate dateOfUpdate){this.dateOfUpdate = dateOfUpdate;}

}
