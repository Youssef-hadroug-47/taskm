package org.taskm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.taskm.cli.CommandFactory;
import org.taskm.cli.Parser;
import org.taskm.cli.Token;
import org.taskm.cli.commandValidators.*;
import org.taskm.commands.*;
import org.taskm.commandsInfo.CommandSpecs;
import org.taskm.commandsInfo.CommandsList;
import org.taskm.commandsInfo.OptionSpecs;
import org.taskm.services.JSONStorage;
import org.taskm.services.Session;

public class App {
         
    public static void main(String[] blabla) {
       
        CommandsList.addCommand(List.of(
            new CommandSpecs(
                "use",
                List.of(
                        new OptionSpecs("--name", true, true, new ArrayList<>()) 
                    ),
                "Use a storage",new UseCommand() , new UseCommandValidator()
                ),
            new CommandSpecs(
                "init", 
                List.of(
                    new OptionSpecs("--name", true, true, new ArrayList<>())
                    ), 
                "initialize a local storage", new InitCommand(), new InitCommandValidator()
                ), 
            new CommandSpecs(
                "exit",
                List.of(),
                "exit the taskm", new ExitCommand(), new ExitCommandValidator())
            ));  


        Session session = Session.getSession();
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("taskm >> ");
            String expression = scanner.nextLine();
            if (expression.isEmpty())
                continue;
            ArrayList<String> args = new ArrayList<>(Arrays.asList(
                        expression.split("[\\s]")
                        )) ;
                    
            if (expression.equals("q")) break;
            //Parser parser = new Parser(args);
            //ArrayList<Token<String>> tokens = parser.parse();
            //CommandFactory commandFactory = new CommandFactory(tokens);
            //Command command = commandFactory.resolveCommand();
            //command.execute();
        }
        //scanner.close();
    }
}
