package org.taskm;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.AttributedString;
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
                "local",
                false,
                "Use a local storage",null,new UseCommand() 
                ),
            new CommandSpecs(
                "use",
                Set.of(),
                "global",
                false ,
                "Use a global storage",
                null, new UseCommand()),
            new CommandSpecs(
                "init", 
                Set.of(
                    new OptionSpecs("--name", true, true , new HashSet<>())
                    ), 
                null,
                false,
                "initialize a local storage",null, new InitCommand()
                ), 
            new CommandSpecs(
                "exit",
                Set.of(),
                null,
                false,
                "exit the taskm",null, new ExitCommand()
                ),
            new CommandSpecs(
                "add",
                Set.of(
                    new OptionSpecs("--title", true ,true , new HashSet<>())
                    ),
                "topic",
                false,
                "add a new subtopic to current topic",null , new AddTopicCommand()
                ),
            new CommandSpecs(
                "add",
                Set.of(
                    new OptionSpecs("--content", true, true ,new HashSet<>())
                    ),
                "task",
                false,
                "add a new task to current topic" , null , new AddTaskCommand() 
                ),
            new CommandSpecs(
                "ct",
                Set.of(),
                null,
                true ,
                "change current topic" , null , new ChangeTopicCommand() 
                ),
            new CommandSpecs(
                "pwt",
                Set.of(),
                null,
                false,
                "print current working topic" , null , new PWTCommand()
                ),
            new CommandSpecs(
                "ls",
                Set.of(),
                null,
                false,
                "list all topics and tasks" , null , new LsCommand()
                ),
            new CommandSpecs(
                "clear",
                Set.of(),
                null,
                false,
                "list all topics and tasks" , null , new ClearCommand()
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
            

            Session.getSession();
            while(true) {
                String prompt = new AttributedString(
                        "taskm "+Session.getSession().getPath() + " >> ",
                        AttributedStyle.DEFAULT.foreground(AttributedStyle.BLUE).bold()
                        ).toAnsi();
                String expression = lineReader.readLine(prompt);

                if (expression.isEmpty())
                    continue;
                ArrayList<String> args = splitExpression(expression);
                       
                if (args == null){
                    System.out.println("invalid command :"+expression);
                    continue;
                }
                Parser parser = new Parser(args);
                Result<ParserResult> result = parser.parse();
                if (!result.getSuccess()){ 
                    result.showFailure();
                    continue;
                }
                Command command = result.getValue().getCommandSpecs().getCommand();
                Result<Void> res =  command.execute(result.getValue().getTokens());
                if (res.getMessage() != null && !res.getMessage().isEmpty())
                    System.out.println(res.getMessage());
                
            }
        }
        catch (UserInterruptException interruptedException){
            ExitCommand exitCommand = new ExitCommand();
            exitCommand.execute(List.of());
        }
    }
}
