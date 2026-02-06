package org.taskm.commands;

import java.util.List;

import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.taskm.cli.Result;
import org.taskm.cli.Token;
import org.taskm.services.Session;

public class PWTCommand implements Command{
    
    @Override
    public Result<Void> execute(List<Token> tokens){
        if (Session.getSession().getStorage() == null)
            return new Result<Void>(false, "use a storage !", null);
        String pwt = "";
        if (Session.getSession().getTopic() == null)
            pwt = "Root";
        else 
            pwt = Session.getSession().getTopic().getTitle();

        AttributedStringBuilder builder = new AttributedStringBuilder();
        System.out.println(builder.style(AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN).bold())
                                  .append( "  " + pwt )
                                  .toAnsi());

        return new Result<Void>(true, "", null);

    }
    
}
