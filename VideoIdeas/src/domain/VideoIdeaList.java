package domain;
import java.util.ArrayList;

public class VideoIdeaList {
    private ArrayList<VideoIdea> videoIdeas;

    public VideoIdeaList() {
        videoIdeas = new ArrayList<>();
    }

    public void addVideoIdea(VideoIdea videoIdea){
        videoIdeas.add(videoIdea);
    }

    public String getVideoIdeasCSV(){
        StringBuilder builder = new StringBuilder();
        for (VideoIdea videoIdea: videoIdeas) {
            builder.append(videoIdea.getVideoIdeaCSV());
        }
        return builder.toString();
    }

    public ArrayList<VideoIdea> getVideoIdeasArray(){
        return videoIdeas;
    }

    public void removeVideoIdea(VideoIdea videoIdea){
        videoIdeas.remove(videoIdea);
    }
}
