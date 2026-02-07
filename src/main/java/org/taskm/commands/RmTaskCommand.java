package org.taskm.commands;

import java.util.List;

import org.taskm.cli.Result;
import org.taskm.cli.Token;
import org.taskm.models.Storage;
import org.taskm.models.Topic;
import org.taskm.services.Session;

public class RmTaskCommand implements Command {

    @Override
    public Result<Void> execute (List<Token> tokens){
        
        Storage storage = Session.getSession().getStorage();
        if (storage == null)
            return new Result<Void>(false, "Use a storage !", null);
        
        Topic topic = Session.getSession().getTopic();
        if (topic == null)
            return new Result<Void>(false, "Can't operate on root !", null);
        
        List<Integer> arguments = tokens.stream()
            .filter( token -> 
                    token.getType().equals(Token.TokenType.COMMAND_ARGUMENT)
                )
            .map( token -> { 
                    try {
                        return Integer.parseInt(token.getVal());
                    }
                    catch (NumberFormatException numberFormatException){
                        return -1 ; 
                    }
                })
            .toList();

        topic.deleteTask(arguments);

        return new Result<Void>(true, "" , null);
    }
}
