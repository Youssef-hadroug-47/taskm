package org.taskm.cli;

import java.util.ArrayList;

import org.taskm.commands.Command;


public class CommandFactory {
    
    private final ArrayList<Token<String>> tokens ;

    public CommandFactory(ArrayList<Token<String>> tokens){
        this.tokens = tokens;
    }

    public Command resolveCommand(){

    }

}
