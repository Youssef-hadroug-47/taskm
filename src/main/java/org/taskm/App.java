package org.taskm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import org.taskm.cli.CommandFactory;
import org.taskm.cli.Parser;
import org.taskm.cli.Token;
import org.taskm.commands.Command;
import org.taskm.services.JSONStorage;
import org.taskm.services.Session;

public class App {
         
    public static void main(String[] blabla) {
        //Session session = Session.getSession();
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("taskm >> ");
            String expression = scanner.nextLine();
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
