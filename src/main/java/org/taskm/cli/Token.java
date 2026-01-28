package org.taskm.cli;

public class Token  {
    
    private final TokenType type;
    private final String value ;
    
    public Token(TokenType type , String value){
        this.type = type;
        this.value = value;
    }
    
    public TokenType getType(){return this.type;}
    public String getVal(){return this.value;}

}
