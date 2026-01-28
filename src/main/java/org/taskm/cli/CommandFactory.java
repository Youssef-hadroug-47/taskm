package org.taskm.cli;

import java.util.ArrayList;

import org.taskm.commands.Command;


public class CommandFactory {
    
    private final ArrayList<Token> tokens ;

    public CommandFactory(ArrayList<Token> tokens){
        this.tokens = tokens;
    }

    public Command resolveCommand(){

    }

}
