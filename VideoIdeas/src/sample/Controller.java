package sample;

import domain.FileConnectionManager;
import domain.TopicManager;
import domain.VideoIdea;
import domain.VideoIdeaList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public class Controller implements Initializable {

    //Attributes
    String fileName = "videoIdeas.csv";
    File file = null;
    TopicManager topicManager;
    FileConnectionManager fileConnectionManager;
    FileChooser fileChooser = new FileChooser();

    //List
    @FXML
    private ListView<VideoIdea> videoIdeaList;

    //ChoiceBoxes
    @FXML
    private ChoiceBox<String> topicChoiceBox;

    @FXML
    private ChoiceBox<String> topicChoiceBox1;

    //TextFields
    @FXML
    private TextField newVideoIdea;

    @FXML
    private TextField newTopic;

    ////Buttons
    //Video Ideas
    @FXML
    void saveVideoIdeas(MouseEvent event) throws IOException {
        topicManager.getTodaysVideoIdeas().forEach((VideoIdea videoIdea) -> topicManager.addNewVideoIdea(videoIdea.getTopic(),videoIdea));
        if(file == null){
            fileConnectionManager.writeToFile(topicManager.getTopicsMap());
        } else{
            fileConnectionManager.writeToFileMaster(topicManager.getTopicsMap(),file);
        }
    }

    @FXML
    void removeVideoIdea(MouseEvent event) {
        VideoIdea selectedIdea = videoIdeaList.getSelectionModel().getSelectedItem();
        videoIdeaList.getItems().remove(selectedIdea);
        topicManager.deleteIdea(selectedIdea);
    }

    @FXML
    void findNewFile(MouseEvent event) {
        file = fileChooser.showOpenDialog(new Stage());
        if(file != null){
            try {
                topicManager = new TopicManager(fileConnectionManager.readFile(file));
            } catch (FileNotFoundException e) {
                System.out.println("no file picked");
            }
            updateBothChoiceBoxes();
        }
    }

    @FXML
    void addToTodaysWork(MouseEvent event) {
        VideoIdea selectedIdea = videoIdeaList.getSelectionModel().getSelectedItem();
        videoIdeaList.getItems().remove(selectedIdea);
        topicManager.deleteIdea(selectedIdea);
        topicManager.addTodaysVideoIdea(selectedIdea);
        listViewTodaysIdeas.getItems().clear();
        listViewTodaysIdeas.getItems().addAll(topicManager.getTodaysVideoIdeas());
    }

    //Add Video Ideas
    @FXML
    void addNewVideoIdea(MouseEvent event) {
        String topic = topicChoiceBox1.getValue();
        String newVideoIdea = this.newVideoIdea.getText();
        topicManager.addNewVideoIdea(topic,newVideoIdea);
        this.newVideoIdea.setText("");
        videoLoading();
    }

    @FXML
    void addNewTopic(MouseEvent event) {
        String newTopic = this.newTopic.getText();
        topicManager.addNewTopic(newTopic);
        updateBothChoiceBoxes();
        this.newTopic.setText("");
    }

    @FXML
    void deleteTopic(MouseEvent event) {
        String selectedTopic = topicChoiceBox1.getValue();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + selectedTopic +" topic?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            topicManager.deleteTopic(selectedTopic);
            updateBothChoiceBoxes();
        }
    }

    //Today's Work
    @FXML
    private ListView<VideoIdea> listViewTodaysIdeas;

    @FXML
    void notToday(MouseEvent event) {
        VideoIdea selectedIdea = listViewTodaysIdeas.getSelectionModel().getSelectedItem();
        topicManager.removeTodaysVideoIdea(selectedIdea);
        listViewTodaysIdeas.getItems().clear();
        listViewTodaysIdeas.getItems().addAll(topicManager.getTodaysVideoIdeas());
        topicManager.addNewVideoIdea(selectedIdea.getTopic(),selectedIdea);
        videoLoading();
    }

    @FXML
    void completedVideoIdea(MouseEvent event) {
        VideoIdea selectedIdea = listViewTodaysIdeas.getSelectionModel().getSelectedItem();
        topicManager.removeTodaysVideoIdea(selectedIdea);
        listViewTodaysIdeas.getItems().clear();
        listViewTodaysIdeas.getItems().addAll(topicManager.getTodaysVideoIdeas());
    }

    //Initialize
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        fileConnectionManager = new FileConnectionManager(fileName);

        try {
            topicManager = new TopicManager(fileConnectionManager.readFile());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        updateBothChoiceBoxes();

        topicChoiceBox.setOnAction(event -> { videoLoading(); });
    }

    /** Method setting the topics at a given ChoiceBox */
    public void setTopics(Map<String, VideoIdeaList> topicMap,ChoiceBox<String> choiceBox){
        Set<String> topicSet = topicMap.keySet();
        choiceBox.getItems().clear();
        topicSet.forEach((String topic) -> choiceBox.getItems().add(topic));
    }
    /** Updating the two choice boxes */
    public void updateBothChoiceBoxes(){
        setTopics(topicManager.getTopicsMap(),topicChoiceBox);
        setTopics(topicManager.getTopicsMap(),topicChoiceBox1);
        topicChoiceBox1.setValue("Topics");
        topicChoiceBox.setValue("Topics");
    }

    public void videoLoading(){
        String chosenTopic = topicChoiceBox.getValue();
        try {
            VideoIdeaList videoIdeaList = topicManager.getVideoIdeaListFromTopic(chosenTopic);
            this.videoIdeaList.getItems().clear();
            for (VideoIdea videoIdea : videoIdeaList.getVideoIdeasArray()) {
                this.videoIdeaList.getItems().add(videoIdea);
            }
        } catch (Exception e) {
            System.out.println("No topic picked!");
        }
    }

    //UI
    @FXML
    void close(MouseEvent event) throws IOException {
        Stage stage  = (Stage) ((Button) event.getSource()).getScene().getWindow();

        topicManager.getTodaysVideoIdeas().forEach((VideoIdea videoIdea) -> topicManager.addNewVideoIdea(videoIdea.getTopic(),videoIdea));
        if(file == null){
            fileConnectionManager.writeToFile(topicManager.getTopicsMap());
        } else{
            fileConnectionManager.writeToFileMaster(topicManager.getTopicsMap(),file);
        }
        stage.close();
    }
}
