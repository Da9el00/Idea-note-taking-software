package domain;

import java.util.ArrayList;
import java.util.Map;

public class TopicManager {

    private Map<String, VideoIdeaList> topics;
    private ArrayList<VideoIdea> todaysVideoIdeas;

    public TopicManager(Map<String, VideoIdeaList> topics) {
        this.topics = topics;
        todaysVideoIdeas = new ArrayList<>();
    }

    public Map<String, VideoIdeaList> getTopicsMap() {
        return topics;
    }

    public void addNewVideoIdea(String topic, String newVideoIdea){
        if(this.topics.containsKey(topic) && !(newVideoIdea.equals(""))){
            topics.get(topic).addVideoIdea(new VideoIdea(topic,newVideoIdea));
        }
    }
    public void addNewVideoIdea(String topic, VideoIdea videoIdea){
            topics.get(topic).addVideoIdea(videoIdea);
    }

    public VideoIdeaList getVideoIdeaListFromTopic(String topic){
        return this.topics.get(topic);
    }

    public void addNewTopic(String newTopic){
        if(!topics.containsKey(newTopic) || !newTopic.equals("")){
            topics.put(newTopic,new VideoIdeaList());
        }
    }

    public void deleteTopic(String topic){
        topics.remove(topic);
    }

    public void deleteIdea(VideoIdea videoIdea){
        this.topics.get(videoIdea.getTopic()).removeVideoIdea(videoIdea);
    }

    public void addTodaysVideoIdea(VideoIdea videoIdea){
        todaysVideoIdeas.add(videoIdea);
    }

    public void removeTodaysVideoIdea(VideoIdea videoIdea){
        todaysVideoIdeas.remove(videoIdea);
    }

    public ArrayList<VideoIdea> getTodaysVideoIdeas() {
        return todaysVideoIdeas;
    }
}
