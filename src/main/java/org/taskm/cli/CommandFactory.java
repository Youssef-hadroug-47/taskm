package org.taskm.cli;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.taskm.commands.*;


public class CommandFactory {
    
    private ParserResult parserResult;

    public CommandFactory(ParserResult parserResult){
        this.parserResult = parserResult;
    }

}
