package org.taskm.commands;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import org.taskm.cli.Result;
import org.taskm.cli.Token;
import org.taskm.models.Storage;
import org.taskm.models.Topic;
import org.taskm.models.TopicPath;
import org.taskm.services.Session;

public class ChangeTopicCommand implements Command {

    private String reestablishPath(String oldPath , String newPath){
        List<String> splitNewPath = TopicPath.splitPath(newPath);
        List<String> splitOldPath = TopicPath.splitPath(oldPath);
        int i = 0;
        while (i < splitNewPath.size() && !splitOldPath.isEmpty() && splitNewPath.get(i).equals("..")){
            splitOldPath.removeLast();
            i++;
        }

        String res = "";
        for (String s : splitOldPath)
            res = res + "/" + s;

        for (int j = i ; j<splitNewPath.size() ; j++){
            if (j < splitNewPath.size() -1  && splitNewPath.get(j+1).equals("..") )
                j++;
            else res = res + "/" + splitNewPath.get(j);
        }

        return res.isEmpty() ? "/" : res ;
    }
    
    private String cleanPath(String path){

        int len = path.length();
        StringBuilder cleanerPath = new StringBuilder(len);

        for (int i = 0; i<len ;i++){
            if (i != 0 && path.charAt(i) == '/' && path.charAt(i-1) == '/'){
                continue;
            }
            if (len > 1 && i == len-1 && path.charAt(i) == '/')
                continue;
            cleanerPath.append(path.charAt(i));
        }    
    
        return cleanerPath.toString();
    } 
    
    @Override
    public Result<Void> execute (List<Token> tokens){
        // Check if storage is null i.e not selected
        if (Session.getSession().getStorage() == null)
            return new Result<Void>(false, "Use a storage ", null);

        // Clean path
        String cleanerPath = cleanPath(tokens.get(1).getVal());
        //Check if the path exists
        if (!TopicPath.exists(cleanerPath))
            return new Result<Void>(false, "Unfound path :" + cleanerPath , null);
        if (cleanerPath.isEmpty())
            return new Result<Void>(true, "" , null);
        
        // generate a list of topics from path 
        List<String> topics = TopicPath.splitPath(cleanerPath); 

        if (topics.isEmpty()){
            Session.getSession().setTopics(new ArrayDeque<>());
            Session.getSession().setAbsolutePath("/");
            return new Result<Void>(true, "" , null);
        }
       
        
        Storage storage = Session.getSession().getStorage();

        Deque<Topic> topicStack = Session.getSession().getTopicsStack();
        if (TopicPath.isAbsolute(cleanerPath))
            topicStack.clear();

        int i = 0;
        Topic tmp_topic = null;
        while(i < topics.size() ){
            String tmp = topics.get(i);

            if (tmp.equals("..") && !topicStack.isEmpty()){
                topicStack.pop();
            }
            else if (tmp.equals(".")){}
            else {
                if (topicStack == null || topicStack.isEmpty())
                    tmp_topic = storage.getTopic(tmp);
                else 
                    tmp_topic = topicStack.getLast().next(tmp);

                if (tmp_topic != null){ 
                    topicStack.addLast(tmp_topic);
                }
                else 
                    return new Result<Void>(false, "Error invalid topic passed exists function !", null);
            }
            i++;
        }

        if (TopicPath.isAbsolute(cleanerPath))
            Session.getSession().setAbsolutePath(cleanerPath);
        else {
            Session.getSession().setAbsolutePath(reestablishPath(Session.getSession().getPath(), cleanerPath));
        }

        return new Result<Void>(true, "", null);
    }
    
}
