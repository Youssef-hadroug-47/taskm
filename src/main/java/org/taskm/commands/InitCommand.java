package org.taskm.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.taskm.cli.Result;
import org.taskm.cli.Token;
import org.taskm.cli.TokenType;
import org.taskm.models.Storage;
import org.taskm.services.JSONStorage;
import org.taskm.services.Session;

public class InitCommand implements Command {


    @Override
    public Result<Void> execute(List<Token> tokens){

        String name = tokens.getLast().getVal(); 
        
            
        if (Session.getSession().getAvailableStorages().size() == 2)
            return new Result<Void>(false, "Local storage already exists:", null);
        Path current_dir = Path.of(System.getProperty("user.dir")+"/.taskm");
        try {
            Files.createDirectories(current_dir);
            current_dir = Path.of(current_dir.toString()+"/storage.json");
            Files.createFile(current_dir);
            JSONObject obj = new JSONObject();
            obj.put("name",name);
            obj.put("max_id",0);
            obj.put("storage", new JSONArray());
            File currentFile = new File(current_dir.toString());
            Session.getSession().setAvailableStorages(List.of(
                    Session.getSession().getAvailableStorages().getFirst(),
                    currentFile
                    ));
            Result<Storage> res = JSONStorage.initStorage(currentFile, name); 
            return new Result<Void>(res.getSuccess(), res.getMessage(), null);
        }
        catch (IOException ioException){
            return new Result<Void>(false, "IOexception while trying to initialize local storage", null);
        }

    }
    
}
