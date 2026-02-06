package org.taskm.commands;



import java.time.format.DateTimeFormatter;
import java.util.List;


import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
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
        List<Task> tasks = topic.getChildrenTasks();

        for (Task task : tasks){
            int statusColorR = 0;
            int statusColorB = 0;
            int statusColorG = 0;
            switch (task.getStatus()) {
                case done:
                    statusColorR = 16;
                    statusColorB = 185;
                    statusColorG = 129; 
                    break;
                case inprogress:
                    statusColorR = 245;
                    statusColorB = 158;
                    statusColorR = 11;
                    break;
                case todo:
                    statusColorR = 59;
                    statusColorB = 130;
                    statusColorG = 246;
                    break;
            }
            AttributedStringBuilder builder = new AttributedStringBuilder();
            if (tokens.size() == 3){
                System.out.println( builder
                        .style(AttributedStyle.DEFAULT)
                        .append("#" + task.getId())
                        .style(AttributedStyle.DEFAULT.foreground(100, 116, 139).bold())
                        .append(" • " + task.getDescription() + "\n")
                        .style(AttributedStyle.DEFAULT.foreground(statusColorR,statusColorB,statusColorG).italic())
                        .append("    " + task.getStatus().toString().toUpperCase())
                        .style(AttributedStyle.DEFAULT.foreground(156, 163, 175).italic())
                        .append(" • Created " 
                            + task.getCreationDate().format(DateTimeFormatter.ofPattern("MMM d")).toString() 
                            + " →  Updated " 
                            + task.getUpdateDate().format(DateTimeFormatter.ofPattern("MMM d")).toString())
                        .toAnsi()
                        );
            }
            else {
                System.out.println( builder
                        .style(AttributedStyle.DEFAULT.foreground(statusColorR,statusColorB,statusColorG).italic())
                        .append("  • [" + task.getStatus().toString().toUpperCase() + "] " )
                        .style(AttributedStyle.DEFAULT.foreground(100, 116, 139).bold())
                        .append(task.getDescription() + "\n")
                        .toAnsi()
                        );

            }
            
        }
        return new Result<Void>(true, "" , null);
    }


}
