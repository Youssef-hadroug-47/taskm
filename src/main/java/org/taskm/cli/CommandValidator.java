package org.taskm.cli;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.taskm.commandsInfo.CommandSpecs;
import org.taskm.commandsInfo.OptionSpecs;


public class CommandValidator {
    
    private CommandSpecs commandSpecs ;

    public CommandValidator(CommandSpecs commandSpecs){
        this.commandSpecs = commandSpecs;
    }
    public Result<ArrayList<Token>> validate(List<String> expression ){
        
        if (!commandSpecs.hasOptions()){
            if (expression.isEmpty())
                return new Result<ArrayList<Token>>(true, "", null);
            return new Result<ArrayList<Token>>(false, "Invalid argument :"+expression.getFirst() , null );
        }

        if (expression.isEmpty())
            return new Result<ArrayList<Token>>(false, "No arguments provided please consult help page", null);

        ArrayList<Token> tokens = new ArrayList<>();

        int nb_requiredOptions = commandSpecs.getRequiredOptions().size();
        Set<String> requiredOptionsGot = new HashSet<>();
        Map<String,OptionSpecs> optionsByName = commandSpecs.getOptions().stream().collect(Collectors.toUnmodifiableMap(
                    OptionSpecs::getName, 
                    Function.identity())
                );
        int i = 0;
        boolean isExpectedTokenArgument = false ;
        OptionSpecs lastOption = null;
        while (i<expression.size()){
            
            if (expression.get(i).startsWith("--") || expression.get(i).startsWith("-") ){
                if ( isExpectedTokenArgument )
                    return new Result<ArrayList<Token>>(false, "Missing argument for :"+expression.get(i-1), null);

                OptionSpecs optionSpecs = null; 

                optionSpecs = optionsByName.get(expression.get(i));

                if (optionSpecs == null)
                    return new Result<ArrayList<Token>>(false , "Invalid option :"+expression.get(i), null);
                if (optionSpecs.isRequired())
                    requiredOptionsGot.add(optionSpecs.getName());
                
                tokens.add(new Token(TokenType.OPTIONS, expression.get(i++)));
                if (optionSpecs.hasValue()){
                    isExpectedTokenArgument = true;
                    lastOption = optionSpecs;
                }
                else isExpectedTokenArgument = false;
                continue;
            }

            if (!isExpectedTokenArgument) 
                return new Result<ArrayList<Token>>(false, "Invalid argument :"+expression.get(i), null);


            if ( lastOption != null && lastOption.getArguments() == null || lastOption.getArguments().isEmpty()){
                tokens.add(new Token(TokenType.ARGUMENT, expression.get(i++)));
                isExpectedTokenArgument = false;
                continue;
            }
            
            boolean validArg = lastOption.getArguments().contains(expression.get(i));
            if (validArg){
                tokens.add(new Token(TokenType.ARGUMENT, expression.get(i++)));
                isExpectedTokenArgument = false;
            }
            else 
                return new Result<ArrayList<Token>>(false , "Invalid argument :"+expression.get(i), null);

        }
        if (isExpectedTokenArgument)
            return new Result<ArrayList<Token>>(false, "Missing argument for :"+lastOption.getName(), null);
        if (nb_requiredOptions != requiredOptionsGot.size() )
            return new Result<ArrayList<Token>>(false, "missing options :\n"+commandSpecs.getFormat(), null);
        
        return new Result<ArrayList<Token>>(true, "", tokens);
    }


}
