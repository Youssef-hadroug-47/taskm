package org.taskm.commands;

import java.util.List;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.taskm.cli.Result;
import org.taskm.cli.Token;
import org.taskm.models.Storage;
import org.taskm.models.Topic;
import org.taskm.services.Session;

public class TreeCommand implements Command {
    private String padding(Topic topic){
        Topic tmp = new Topic(topic);
        String padding = "" ;
        while(tmp.hasPrev()){
            tmp = tmp.prev();
            padding = padding + String.valueOf("└──").repeat(tmp.getTitle().length());
        }
        return padding;

    }
    @Override
    public Result<Void> execute(List<Token> tokens){
        System.out.println("   Under construction !");
        return new Result<Void>(true, "" , null);

        //Storage storage = Session.getSession().getStorage();
        //if (storage == null)

        //Topic topic = Session.getSession().getTopic();
        //if (topic == null){
            //storage.DFS(t ->{
                //System.out.print(padding(t));
                //System.out.println(
                    //t.getTitle()
                //);
            //});  
        //}
        //else {
            //topic.DFS(t ->{
                    //System.out.print(padding(t));
                    //t.print();
            //});
        //}
        //
        //
    }
}
