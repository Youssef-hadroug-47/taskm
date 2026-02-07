package org.taskm.models;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import org.taskm.services.Session;

public class TopicPath {
    public static List<String> splitPath(String path) {
        
        List<String> tokens = new ArrayList<>();
        String token = "";
        for (int i = 0 ;i<path.length() ;i++){
            char c = path.charAt(i);
            if (c == '/' ){
                if(!token.isEmpty()){
                    tokens.add(token);
                    token = "";
                }
                continue;
            }
            token = token + c;
            if (i == path.length()-1 && !token.isEmpty()){
                tokens.add(token);
            }            

        }
        return tokens;
    }

    public static boolean exists(String path) {
        
        List<String> topics = splitPath(path);
        if (topics.size() == 0)
            return true;
        
        Deque<Topic> topicStack = new ArrayDeque<>(Session.getSession().getTopicsStack());

        int i = 0;
        Topic tmp_topic ;
        while (i < topics.size()){

            String tmp = topics.get(i);
            
            if (tmp.equals("..")){
                if (topicStack == null || topicStack.isEmpty())
                    return false;
                topicStack.pop(); 
            }   
            else if (tmp.equals(".")){}
            else {
                if (topicStack == null || topicStack.isEmpty())
                    tmp_topic = Session.getSession().getStorage().getTopic(tmp);
                else 
                    tmp_topic = topicStack.getLast().next(tmp);

                if (tmp_topic != null)
                    topicStack.addLast(tmp_topic);
                else 
                    return false;
            }
            i++;
        }
        return true;
    }
    

    public static boolean isAbsolute(String path) {
        return path != null && !path.isEmpty() && path.startsWith("/");
    }
}
