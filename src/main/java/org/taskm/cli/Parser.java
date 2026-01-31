package org.taskm.cli;

import java.util.ArrayList;
import java.util.Set;

import org.taskm.commandsInfo.CommandSpecs;
import org.taskm.commandsInfo.CommandsList;

public class Parser {

    private ArrayList<String> expression;

    public Parser(ArrayList<String> expression){
        this.expression = expression;
    }
    
    public Result<ParserResult> parse(){
        
        if (expression.isEmpty())
            return new Result<ParserResult>(false, "No command provided :", null);

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
            return new Result<ParserResult>(false, "Invalid command :"+expression.getFirst(), null);

        tokens.add(new Token(TokenType.COMMAND , expression.getFirst())); 
        int optionStartIndex = 1;
        if (commandSpecs.hasSubCommand()) {tokens.add(new Token(TokenType.SUBCOMMAND , expression.get(1)));optionStartIndex++;}
        
        CommandValidator commandValidator = new CommandValidator(commandSpecs);
        Result<ArrayList<Token>> result = commandValidator.validate(expression.subList(optionStartIndex,expression.size()));
        
        if (!result.getSuccess()) 
            return new Result<ParserResult>(false, result.getMessage(), null);

        if (result.getValue() != null ) tokens.addAll(result.getValue());
        return new Result<ParserResult>(true, "", new ParserResult(tokens, commandSpecs));
    }

}
