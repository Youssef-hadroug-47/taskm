package org.taskm.commands;

import java.util.List;

import org.taskm.cli.Result;
import org.taskm.cli.Token;
import org.taskm.models.Storage;
import org.taskm.models.Topic;
import org.taskm.services.Session;

public class RmTopicCommand implements Command {

    @Override
    public Result<Void> execute(List<Token> tokens){
        Storage storage = Session.getSession().getStorage();
        if (storage == null)
            return new Result<Void>(false, "Use a storage !", null);
        List<String> arguments = tokens.stream()
            .filter( token -> 
                    token.getType().equals(Token.TokenType.COMMAND_ARGUMENT)
                )
            .map( token -> token.getVal() ).toList();   

        boolean deleteAll = tokens.stream().anyMatch( token ->
                token.getType().equals(Token.TokenType.OPTIONS) && token.getVal().equals("-a")
                    );
        boolean isRecursive = tokens.stream().anyMatch(token -> 
                token.getType().equals(Token.TokenType.OPTIONS) && token.getVal().equals("-r")) ;
        if (deleteAll && isRecursive)
            deleteAll = false;
        Topic topic = Session.getSession().getTopic();
        if (topic == null){
            if (!isRecursive)
                for (String t : arguments){
                    Topic tmp = storage.getTopic(t);
                    if (tmp == null){
                        Result<Void> result = new Result<Void>(false, "Unfound topic name :" + t, null);
                        result.printMessage();
                    }
                    else if (tmp.getNbOfTasks() + tmp.getNbOfTopics() > 0){
                        Result<Void> result = new Result<Void>(false,
                                "rm : failed to remove " + t + " :Topic not empty !", null);
                        result.printMessage();
                    }
                    else {
                        storage.deleteTopic(List.of(t));
                    }
                }
            else {
                storage.deleteTopic(arguments);      
            }
        }
        else {
             if (!isRecursive)
                for (String t : arguments){
                    Topic tmp = topic.getTopic(t);
                    if (tmp == null){
                        Result<Void> result = new Result<Void>(false, "Unfound topic name :" + t, null);
                        result.printMessage();
                    }
                    else if (tmp.getNbOfTasks() + tmp.getNbOfTopics() > 0){
                        Result<Void> result = new Result<Void>(false,
                                "rm : failed to remove " + t + " :Topic not empty !", null);
                        result.printMessage();
                    }
                    else 
                        topic.deleteTopic(List.of(t));
                }
            else {
                topic.deleteTopic(arguments);
            }           
        }

        return new Result<Void>(true, "" , null);
    }


}
