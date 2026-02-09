package org.taskm.commands;



import java.util.List;


import org.taskm.cli.Result;
import org.taskm.cli.Token;
import org.taskm.models.Task;
import org.taskm.models.Topic;
import org.taskm.services.Session;


public class LsTaskCommand implements Command {

    @Override
    public Result<Void> execute(List<Token> tokens){
        if (Session.getSession().getStorage() == null)
            return new Result<Void>(false, "Use a storage !", null);
        if (Session.getSession().getTopic() == null)
            return new Result<Void>(false, "Tasks does not feature in the root topic !", null);
        Topic topic = Session.getSession().getTopic();
        boolean printAll = tokens.stream().anyMatch( token -> 
                token.getType().equals(Token.TokenType.OPTIONS) && token.getVal().equals("-a")
            );
        List<Task> tasks = topic.getChildrenTasks();

        for (Task task : tasks){
            if (printAll)
                task.verbosePrint();
            else 
                task.miniPrint();
        }
        return new Result<Void>(true, "" , null);
    }


}
