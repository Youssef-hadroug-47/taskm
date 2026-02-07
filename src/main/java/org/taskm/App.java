package org.taskm;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.terminal.Terminal;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.taskm.cli.Parser;
import org.taskm.cli.ParserResult;
import org.taskm.cli.Result;
import org.taskm.commands.*;
import org.taskm.commandsInfo.*;
import org.taskm.services.Session;



public class App {
    public static ArrayList<String> splitExpression(String expression){
        
        ArrayList<String> tokens = new ArrayList<>(); 
        boolean openQuotes = false;
        String token = new String();
        for (int i = 0 ;i<expression.length(); i++){
            char c = expression.charAt(i);
            if (c == ' ' )
                if (openQuotes){
                    token = token + c;
                    continue;
                }
                else  {
                    if (!token.isEmpty()) tokens.add(token);
                    token = "";
                    continue;
                }
            if (c == '\"'){
                openQuotes = !openQuotes;
                if (!openQuotes){
                    if (!token.isEmpty()) tokens.add(token);
                    token = "";
                }
                continue;
            }
            token = token + c;
            if (!openQuotes && !token.isEmpty() && i == expression.length() - 1)
                tokens.add(token);
        }
        if (openQuotes)
            return null;
        return tokens ;
    }
         
    public static void main(String[] blabla) {
       
        CommandsList.addCommand(Set.of(
            new CommandSpecs(
                "use",
                Set.of(),
                Set.of(),
                "local",
                false,
                "Use a local storage",null,new UseCommand() 
                ),
            new CommandSpecs(
                "use",
                Set.of(),
                Set.of(),
                "global",
                false ,
                "Use a global storage",
                null, new UseCommand()),
            new CommandSpecs(
                "init", 
                Set.of(),
                Set.of(), 
                null,
                true,
                "initialize a local storage",null, new InitCommand()
                ), 
            new CommandSpecs(
                "exit",
                Set.of(),
                Set.of(),
                null,
                false,
                "exit the taskm",null, new ExitCommand()
                ),
            new CommandSpecs(
                "add",
                Set.of(),
                Set.of(),
                "topic",
                true,
                "add a new subtopic to current topic",null , new AddTopicCommand()
                ),
            new CommandSpecs(
                "add",
                Set.of(),
                Set.of(),
                "task",
                true,
                "add a new task to current topic" , null , new AddTaskCommand() 
                ),
            new CommandSpecs(
                "ct",
                Set.of(),
                Set.of(),
                null,
                true ,
                "change current topic" , null , new ChangeTopicCommand() 
                ),
            new CommandSpecs(
                "pwt",
                Set.of(),
                Set.of(),
                null,
                false,
                "print current working topic" , null , new PWTCommand()
                ),
            new CommandSpecs(
                "ls",
                Set.of(),
                Set.of(
                        new OptionSpecs("-a", false, false, Set.of())
                    ),
                "task",
                false,
                "list all tasks of current topic" , null , new LsTaskCommand()
                ),
            new CommandSpecs(
                "ls",
                Set.of("ls"),
                Set.of(),
                "topic",
                false,
                "list all topics of current topic" , null , new LsTopicCommand()
                ),
            new CommandSpecs(
                "clear",
                Set.of(),
                Set.of(),
                null,
                false,
                "Clear screen" , null , new ClearCommand()
                ),
            new CommandSpecs(
                "rm",
                Set.of("rm"),
                Set.of(
                    new OptionSpecs("-r", false, false, Set.of()),
                    new OptionSpecs("-a" , false, false, Set.of())
                    ),
                "topic",
                true ,
                "Delete subTopic in current topic" , null , new RmTopicCommand()
                ),
            new CommandSpecs(
                "rm",
                Set.of(),
                Set.of(
                    new OptionSpecs("-a" , false, false, Set.of())
                    ),
                "task",
                true,
                "Delete task in current working topic" , null , new RmTaskCommand()
                )
            ));  

        try {
            Terminal terminal = Session.getSession().getTerminal();
            

            Completer completer = new StringsCompleter(CommandsList.getCommands().
                stream().
                map( (command) -> {
                    return command.getName();
                }).
                toList()
            );
            LineReader lineReader = LineReaderBuilder.builder().terminal(terminal).completer(completer).build();
            

            AttributedStringBuilder notifBuilder = new AttributedStringBuilder();
            Session.getSession();
            AttributedString tmp ;
            if (Session.getSession().getAvailableStorages().size() == 2){
                tmp = notifBuilder.style(AttributedStyle.DEFAULT.foreground(17, 117, 12))
                       .append("✓ Local storage is detected").toAttributedString();
                System.out.println(tmp.toAnsi());
            }

 
            while(true) {
                AttributedStringBuilder builder = new AttributedStringBuilder();
                AttributedString prompt = builder
                        .style(AttributedStyle.DEFAULT.foreground(90, 90, 90).bold())
                        .append("╭──" )
                        .style(AttributedStyle.DEFAULT.foreground(209, 124, 4).italic())
                        .append( "≺" + Session.getSession().getPath() + "≻" )
                        .style(AttributedStyle.DEFAULT.foreground(226, 223, 209).bold())
                        .append(Session.getSession().getStorage()== null  ? 
                                "-≺None≻" :
                                "-≺" +  Session.getSession().getStorage().getName() + "≻" )
                        .style(AttributedStyle.DEFAULT.foreground(90, 90, 90).bold())
                        .append("\n╰─⮞ ")
                        .toAttributedString();
                String expression = lineReader.readLine(prompt.toAnsi());

                if (expression.isEmpty())
                    continue;
                ArrayList<String> args = splitExpression(expression);
                       
                if (args == null){
                    Result<Void> error = new Result<Void>(false,"invalid command :"+expression , null);
                    error.printMessage();
                    continue;
                }
                Parser parser = new Parser(args);
                Result<ParserResult> result = parser.parse();
                if (!result.getSuccess()){ 
                    result.printMessage();
                    continue;
                }
                Command command = result.getValue().getCommandSpecs().getCommand();
                Result<Void> res =  command.execute(result.getValue().getTokens());
                res.printMessage();
                
            }
        }
        catch (UserInterruptException interruptedException){
            ExitCommand exitCommand = new ExitCommand();
            exitCommand.execute(List.of());
        }
    }
}
