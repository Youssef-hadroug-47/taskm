package org.taskm.commands;

import java.util.List;
import java.util.function.Consumer;

import org.taskm.cli.Result;
import org.taskm.cli.Token;
import org.taskm.models.Topic;
import org.taskm.services.Session;

public class FindTaskCommand implements Command {

    @Override
    public Result<Void> execute(List<Token> tokens){
        if (Session.getSession().getStorage() == null)
            return new Result<Void>(false, "Use a storage !", null);
        if (Session.getSession().getTopic() == null)
            return new Result<Void>(false, "Tasks does not feature in the root topic !", null);
        Topic topic = Session.getSession().getTopic();

        boolean isRecursive = tokens.stream().anyMatch( token -> 
                    token.getType().equals(Token.TokenType.OPTIONS) && token.getVal().equals("-r")
                );
        String argument = tokens.stream().filter( token ->
                    token.getType().equals(Token.TokenType.COMMAND_ARGUMENT)
                ).toList().getFirst().getVal();
            
        if (isRecursive){
            topic.BFS( t -> 
                t.getChildrenTasks().forEach( (task) -> { 
                            if (task.getDescription().contains(argument))
                                task.miniPrint();
                        })
                );
        }
        else {
            topic.getChildrenTasks().forEach( task -> {
                if (task.getDescription().contains(argument))
                    task.miniPrint();
            }); 
        }
        return new Result<Void>(true, "" , null);
    }



}
