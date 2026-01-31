package org.taskm.services;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.taskm.cli.Result;
import org.taskm.models.Node;
import org.taskm.models.Storage;
import org.taskm.models.Task;
import org.taskm.models.TaskStatus;
import org.taskm.models.Topic;



public class JSONStorage {
        

    private static Result<Task> jsonObjectToTask(Topic parent , JSONObject jsonObject){
        Number id ; String description;
        LocalDate creationDate , updateDate;
        TaskStatus status;
        try {
            id = (Number)jsonObject.get("id");
            description = (String)jsonObject.get("description");
            creationDate = LocalDate.parse((String)jsonObject.get("creation_date"));
            updateDate = LocalDate.parse((String)jsonObject.get("update_date"));
            status = TaskStatus.valueOf((String)jsonObject.get("status"));
            return new Result<Task>(true , "" , new Task(id.intValue() , creationDate, updateDate, parent, status, description));
        }
        catch(DateTimeParseException | IllegalArgumentException | ClassCastException e){
            return new Result<Task>(false , "Invalid file structure ", null);
        }
    } 
    private static Result<Topic> jsonObjectToTopic(Topic parent , JSONObject jsonObject){
        Number id , id_max ;
        LocalDate creationDate , updateDate ;
        String title;
        try{
            id = (Number)jsonObject.get("id");
            Topic topic = new Topic(id.intValue());
            creationDate = LocalDate.parse((String)jsonObject.get("creation_date"));
            updateDate = LocalDate.parse((String)jsonObject.get("update_date"));
            id_max = (Number)jsonObject.get("id_max");
            title = (String)jsonObject.get("title");
            
            
            JSONArray childrenTopicsJSON = (JSONArray)jsonObject.get("children_topics");
            if (childrenTopicsJSON == null) 
                return new Result<Topic>(false, "Invalid file structure inside topic of id "+id.intValue(), null);
            List<Topic> childrenTopics = new ArrayList<>();
            for (Object obj : childrenTopicsJSON){
                JSONObject topicJson = (JSONObject)obj;
                Result<Topic> res = jsonObjectToTopic(topic, topicJson);
                if (res.getSuccess()) childrenTopics.add(res.getValue());
                else return new Result<Topic>(false, res.getMessage() , null);
            }

            JSONArray childrenTasksJSON = (JSONArray)jsonObject.get("children_tasks");
            if (childrenTasksJSON == null)
                return new Result<Topic>(false, "Invalid file structure inside topic of id "+id.intValue() , null);
            List<Task> childrenTasks = new ArrayList<>();
            for (Object obj : childrenTasksJSON){
                JSONObject taskJson = (JSONObject)obj;
                Result<Task> res = jsonObjectToTask(topic, taskJson);
                if (res.getSuccess()) childrenTasks.add(res.getValue());
                else return new Result<Topic>(false, res.getMessage(), null);
            }
            List<Node> children = new ArrayList<>(childrenTasks);
            children.addAll(childrenTopics);
            
            topic.setMaxId(id_max.intValue());topic.setParent(parent);topic.setChildren(children);topic.setDateOfUpdate(updateDate);
            topic.setTitle(title);topic.setDateOfCreation(creationDate);topic.setChildrenTasks(childrenTasks);
            topic.setChildrenTopics(childrenTopics);
            return new Result<Topic>(true, "", topic);
        }
        catch (DateTimeParseException | IllegalArgumentException | ClassCastException e){
            return new Result<Topic>(false, "Invalid file structure ", null);
        }

    }

    public static Result<Storage> load(File file ){
        try(FileReader fileReader = new FileReader(file)){
            
            JSONParser jsonParser = new JSONParser();
            JSONObject parsedFile = (JSONObject)jsonParser.parse(fileReader);

            Number max_id = (Number)parsedFile.get("max_id");
            String name = (String)parsedFile.get("name");
            JSONArray storage = (JSONArray)parsedFile.get("storage");
            if (storage == null)
                return new Result<Storage>(false , "Invalid file structure inside storage ",null );
            List<Topic> topics = new ArrayList<>();
            for (Object obj : storage ){
                JSONObject jsonObject = (JSONObject)obj;
                Result<Topic> res = jsonObjectToTopic(null, jsonObject);
                if (res.getSuccess()) topics.add(res.getValue());
            }
            return new Result<Storage>(true, "" , new Storage(max_id.intValue(), topics, name));
        }
        catch (IOException ioException){
            return new Result<Storage>(false, "Error while trying to read file !", null);
        }
        catch (ParseException parseException){
            return new Result<Storage>(false, "Error whilte trying to parse file !", null);
        }

    }
    private static Result<JSONObject> taskToJsonObj(Task task){
        JSONObject taskObj = new JSONObject(); 
        taskObj.put("id", task.getId());
        taskObj.put("description", task.getDescription());
        taskObj.put("creation_date",task.getCreationDate().toString());
        taskObj.put("update_date", task.getUpdateDate().toString());
        taskObj.put("status", task.getStatus().toString());
        return new Result<JSONObject>(true, "" , taskObj);
    }
    private static Result<JSONObject> topicToJsonObj(Topic topic){
        JSONObject topicObj = new JSONObject();
        topicObj.put("id", topic.getId());
        topicObj.put("id_max", topic.getMaxId());
        topicObj.put("creation_date", topic.getCreationDate().toString());
        topicObj.put("update_date", topic.getUpdateDate().toString());
        topicObj.put("title", topic.getTitle());
        JSONArray childrenTopicsJsonObj = new JSONArray();
        for (Topic topic2 : topic.getChildrenTopics()){
            Result<JSONObject> res = topicToJsonObj(topic2);
            JSONObject obj = new JSONObject();
            if (res.getSuccess()) obj = res.getValue();
            childrenTopicsJsonObj.add(obj);
        }
        JSONArray childrenTasksJsonObj = new JSONArray();
        for (Task task : topic.getChildrenTasks()){
            Result<JSONObject> res = taskToJsonObj(task);
            JSONObject obj = new JSONObject();
            if (res.getSuccess()) obj = res.getValue();
            childrenTasksJsonObj.add(obj);
        }

        topicObj.put("children_topics",childrenTopicsJsonObj);
        topicObj.put("children_tasks",childrenTasksJsonObj);
        return new Result<JSONObject>(true, "" , topicObj);
    }
    public static Result<Void> save(File file){
        try ( FileWriter fileWriter = new FileWriter(file)){ 
            Storage current_storage = Session.getSession().getStorage();
            JSONObject storageJsonObject = new JSONObject();
            storageJsonObject.put("max_id", current_storage.getMaxId());
            storageJsonObject.put("name", current_storage.getName());

            JSONArray jsonArray = new JSONArray();
            for (Topic topic : current_storage.getTopics()){
                Result<JSONObject> res = topicToJsonObj(topic);
                JSONObject topicJson = new JSONObject();
                if (res.getSuccess()) topicJson = res.getValue();
                jsonArray.add(topicJson);
            }
            storageJsonObject.put("storage",jsonArray);
            fileWriter.write(storageJsonObject.toString());

        } catch (IOException ioException) { 
            return new Result<Void>(false, "Error while trying to open file writer !", null);
        }   
        return new Result<Void>(true, "", null);
    }
    public static Result<Storage> initStorage(File file , String name){
        try (FileWriter fileWriter = new FileWriter(file)){
            JSONObject obj = new JSONObject();
            obj.put("name", name);
            obj.put("max_id",0);
            obj.put("storage", new JSONArray());
            fileWriter.write(obj.toString());
            return new Result<Storage>( true , "" , new Storage(0, null, name));
        } catch (IOException e) {
            return new Result<Storage>(false, "I/O error while trying to init storage ", null);
        }

    } 
}
