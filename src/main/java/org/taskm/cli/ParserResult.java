package org.taskm.cli;


import java.util.List;

import org.taskm.commandsInfo.CommandSpecs;

public class ParserResult {

    private List<Token> tokens;
    private CommandSpecs commandSpecs;
    
    public ParserResult(List<Token> tokens , CommandSpecs commandSpecs){
        this.commandSpecs = commandSpecs;
        this.tokens = tokens;
    }

    public List<Token> getTokens(){return tokens;}
    public CommandSpecs getCommandSpecs(){return commandSpecs;}

}
