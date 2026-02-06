package org.taskm.commands;

import java.util.List;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.taskm.cli.Result;
import org.taskm.cli.Token;
import org.taskm.models.Topic;
import org.taskm.services.Session;

public class LsCommand implements Command {
    
    @Override
    public Result<Void> execute(List<Token> tokens){
        if (Session.getSession().getStorage() == null)
            return new Result<Void>(false, "Use a storage !", null);
        if (Session.getSession().getTopic() == null){
            for (Topic t : Session.getSession().getStorage().getTopics()){
                System.out.println(new AttributedString(
                            t.getTitle(),
                            AttributedStyle.DEFAULT.foreground(AttributedStyle.RED)).toAnsi()
                        );
            }

        }
        else {
            for (Topic t : Session.getSession().getTopic().getChildrenTopics()){
                System.out.println(new AttributedString(
                            t.getTitle(),
                            AttributedStyle.DEFAULT.foreground(AttributedStyle.RED)).toAnsi()
                        );
            }
    
        }
        return new Result<Void>(true, "" , null);

    }
    
}
