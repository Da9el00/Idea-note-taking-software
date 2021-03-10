package domain;

public class VideoIdea {
    private final String topic;
    private String idea;

    public VideoIdea(String topic, String idea) {
        this.topic = topic;
        this.idea = idea;
    }

    public String getTopic() {
        return topic;
    }

    @Override
    public String toString() {
        return "Topic: " + topic + " \t Idea: " + idea + "\n";
    }

    public String getVideoIdeaCSV(){
        return topic + "," + idea + "\n";
    }
}
