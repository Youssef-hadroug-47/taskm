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
         

        if (expression.isEmpty() && ( commandSpecs.hasRequiredOptions() || commandSpecs.hasArgument() ))
            return new Result<ArrayList<Token>>(false, "No arguments provided please consult help page", null);

        ArrayList<Token> tokens = new ArrayList<>();

        int nb_requiredOptions = commandSpecs.getRequiredOptions().size();
        Set<String> requiredOptionsGot = new HashSet<>();
        Map<String,OptionSpecs> optionsByName = commandSpecs.getOptions().stream().collect(Collectors.toUnmodifiableMap(
                    OptionSpecs::getName, 
                    Function.identity())
                );

        int i = 0;
        boolean isExpectedOptionArgument = false ;
        OptionSpecs lastOption = null;
        boolean isExpectedCommandArgument = commandSpecs.hasArgument();
        boolean hasArgument = commandSpecs.hasArgument();
        while (i<expression.size()){
            
            if (expression.get(i).startsWith("--") || expression.get(i).startsWith("-") ){

                if ( isExpectedOptionArgument )
                    return new Result<ArrayList<Token>>(false, "Missing argument for :"+expression.get(i-1), null);
               
                OptionSpecs optionSpecs = null; 

                optionSpecs = optionsByName.get(expression.get(i));

                if (optionSpecs == null)
                    return new Result<ArrayList<Token>>(false , "Invalid option :"+expression.get(i), null);

                if ( isExpectedCommandArgument && optionSpecs.hasValue())
                    return new Result<ArrayList<Token>>(false, "Missing argument for :" + commandSpecs.getSubCommand(), null);
 
                if (optionSpecs.isRequired())
                    requiredOptionsGot.add(optionSpecs.getName());
                
                tokens.add(new Token(Token.TokenType.OPTIONS, expression.get(i++)));
                if (optionSpecs.hasValue()){
                    isExpectedOptionArgument = true;
                    lastOption = optionSpecs;
                }
                else isExpectedOptionArgument = false;
                hasArgument = false;
                continue;
            }

            if (!isExpectedOptionArgument && isExpectedCommandArgument) {
                tokens.add(new Token(Token.TokenType.COMMAND_ARGUMENT , expression.get(i++)));
                isExpectedCommandArgument = false;
                continue;
            }
            if (!isExpectedOptionArgument && !isExpectedCommandArgument && lastOption == null && !hasArgument)
                return new Result<ArrayList<Token>>(false, "Invalid argument :"+expression.get(i), null);
            
            if (hasArgument){
                tokens.add(new Token(Token.TokenType.COMMAND_ARGUMENT , expression.get(i++)));
                continue;
            }

            if ( lastOption != null && ( lastOption.getArguments() == null || lastOption.getArguments().isEmpty() )){
                tokens.add(new Token(Token.TokenType.OPTION_ARGUMENT, expression.get(i++)));
                isExpectedOptionArgument = false;
                continue;
            }
            
            boolean validArg = lastOption.getArguments().contains(expression.get(i));
            if (validArg){
                tokens.add(new Token(Token.TokenType.OPTION_ARGUMENT, expression.get(i++)));
                isExpectedOptionArgument = false;
            }
            else 
                return new Result<ArrayList<Token>>(false , "Invalid argument :"+expression.get(i), null);

        }
        if (isExpectedOptionArgument)
            return new Result<ArrayList<Token>>(false, "Missing argument for :"+lastOption.getName(), null);
        if (nb_requiredOptions != requiredOptionsGot.size() )
            return new Result<ArrayList<Token>>(false, "missing options :\n"+commandSpecs.getFormat(), null);
        
        return new Result<ArrayList<Token>>(true, "", tokens);
    }


}
