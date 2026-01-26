package org.taskm.cli;

public class Token <T> {
    
    private final TokenType type;
    private final T value ;
    
    public Token(TokenType type , T value){
        this.type = type;
        this.value = value;
    }
    
    public TokenType getType(){return this.type;}
    public T getVal(){return this.value;}

}
