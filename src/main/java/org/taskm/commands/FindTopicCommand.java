package org.taskm.commands;

import java.util.List;
import java.util.function.Consumer;

import org.taskm.cli.Result;
import org.taskm.cli.Token;
import org.taskm.models.Storage;
import org.taskm.models.Topic;
import org.taskm.services.Session;

public class FindTopicCommand implements Command {
    @Override
    public Result<Void> execute(List<Token> tokens){
        Storage storage = Session.getSession().getStorage();
        if (storage == null)
            return new Result<Void>(false, "Use a storage ", null); 
        Topic topic = Session.getSession().getTopic();
        String text = tokens.getLast().getVal();
        Consumer<Topic> consumer = t -> {
            if (t.getTitle().contains(text))
                t.print();
        };
        if (topic == null){
            storage.BFS( consumer );
        }
        else {
            topic.BFS( consumer);
        }
        return new Result<Void>(true, "" , null);
    }

}
