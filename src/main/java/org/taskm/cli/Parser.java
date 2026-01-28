package org.taskm.cli;

import java.util.ArrayList;
import java.util.Set;

import org.taskm.cli.CommandValidator;
import org.taskm.commandsInfo.CommandSpecs;
import org.taskm.commandsInfo.CommandsList;

public class Parser {

    private ArrayList<String> expression;

    public Parser(ArrayList<String> expression){
        this.expression = expression;
    }
    
    public Result<ArrayList<Token>> parse(){
        
        if (expression.isEmpty())
            return new Result<ArrayList<Token>>(false, "No command provided :", null);

        ArrayList<Token> tokens = new ArrayList<>();

        // validates command
        Set<CommandSpecs> commands = CommandsList.getCommands()  ;
        CommandSpecs commandSpecs = null;
        
        for (CommandSpecs c : commands)
            if ( expression.getFirst().equals(c.getName()) ){
                if (c.hasSubCommand() && expression.size() > 1 && expression.get(1).equals(c.getSubCommand())){
                    commandSpecs = c;    
                    break;
                }
                if (!c.hasSubCommand()){
                    commandSpecs = c;
                    break; 
                }
            }
        if (commandSpecs == null) 
            return new Result<ArrayList<Token>>(false, "Invalid command :"+expression.getFirst(), null);

        tokens.add(new Token(TokenType.COMMAND , expression.getFirst())); 
        if (commandSpecs.hasSubCommand()) tokens.add(new Token(TokenType.SUBCOMMAND , expression.get(1)));
        
        CommandValidator commandValidator = new CommandValidator(commandSpecs);
        Result<ArrayList<Token>> result = commandValidator.validate(expression.subList(1,expression.size()));
        
        if (!result.getSuccess()) 
            return result;

        if (result.getValue() != null ) tokens.addAll(result.getValue());
        return new Result<ArrayList<Token>>(true, "", tokens);
    }

}
