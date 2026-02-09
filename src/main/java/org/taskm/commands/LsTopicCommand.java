package org.taskm.commands;

import java.util.List;

import org.taskm.cli.Result;
import org.taskm.cli.Token;
import org.taskm.models.Topic;
import org.taskm.services.Session;

public class LsTopicCommand implements Command {
    
    @Override
    public Result<Void> execute(List<Token> tokens){
        if (Session.getSession().getStorage() == null)
            return new Result<Void>(false, "Use a storage !", null);
        if (Session.getSession().getTopic() == null){
            for (Topic t : Session.getSession().getStorage().getTopics()){
                t.print();
            }

        }
        else {
            for (Topic t : Session.getSession().getTopic().getChildrenTopics()){
                t.print(); 
            }
    
        }
        return new Result<Void>(true, "" , null);

    }
    
}
