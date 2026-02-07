package org.taskm.commands;

import java.time.LocalDate;
import java.util.List;

import org.taskm.cli.Result;
import org.taskm.cli.Token;
import org.taskm.models.Task;
import org.taskm.models.Topic;
import org.taskm.services.Session;

public class MarkTaskCommand implements Command {

    @Override
    public Result<Void> execute(List<Token> tokens){
        if (Session.getSession().getStorage() == null)
            return new Result<Void>(false, "Use a storage !", null);
        Topic topic = Session.getSession().getTopic();
        if (topic == null)
            return new Result<Void>(false , "Can't operate in root topic", null);
        String stringId = null, status = null;
        for (int i = 1 ; i<tokens.size() ; i++){
            if (tokens.get(i).getType().equals(Token.TokenType.OPTIONS) && tokens.get(i).getVal().equals("-s")){
               status = tokens.get(++i).getVal(); 
            }
        }
        stringId = tokens.stream().filter(token -> token.getType().equals(Token.TokenType.COMMAND_ARGUMENT)).toList().getFirst().getVal();
        int id = 0;
        try{
            id = Integer.parseInt(stringId);
        }
        catch (NumberFormatException numberFormatException){
            return new Result<Void>(false, "Invalid argument :" + stringId, null);
        }

        Task task = topic.getTask(id); 
        if (task == null)
            return new Result<Void>(false, "Unfound task id :" + id, null);
        task.markTask(Task.TaskStatus.valueOf(status));
        task.setDateOfUpdate(LocalDate.now());

        return new Result<Void>(true, "" , null);
    }
}
