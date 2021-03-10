package domain;

import java.io.*;
import java.util.*;

public class FileConnectionManager {
    private final File file;

    public FileConnectionManager(String fileName) {
        String filePath = "src\\filesCSV\\";
        file = new File(filePath + fileName);
    }

    public void writeToFile(Map<String, VideoIdeaList> output) throws IOException {
        writeToFileMaster(output, file);
    }

    public void writeToFile(Map<String, VideoIdeaList> output, File newFile) throws IOException {
        writeToFileMaster(output, newFile);
    }

    public void writeToFileMaster(Map<String, VideoIdeaList> output, File newFile) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(newFile));
        Set<String> keySet= output.keySet();
        ArrayList<String> arrayListKeys = new ArrayList<>();
        keySet.forEach((String topic)->{
            arrayListKeys.add(topic);
        });

        for (int i = 0; i < arrayListKeys.size(); i++) {
            try {
                writer.write(arrayListKeys.get(i));
                if(i !=arrayListKeys.size()-1){
                    writer.write(",");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        writer.write("\n");
        keySet.forEach((String topic)->{
            try {
                writer.write(output.get(topic).getVideoIdeasCSV());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        writer.close();
    }

    public HashMap<String, VideoIdeaList> readFile() throws FileNotFoundException {
        return getStringVideoIdeaListHashMap(file);
    }

    public HashMap<String, VideoIdeaList> readFile(File newFile) throws FileNotFoundException {
        return getStringVideoIdeaListHashMap(newFile);
    }

    private HashMap<String, VideoIdeaList> getStringVideoIdeaListHashMap(File newFile) throws FileNotFoundException {
        Scanner inputStream = new Scanner(newFile);
        HashMap<String, VideoIdeaList> topicsMap = new HashMap<>();
        String[] topicsArray = separatorCSV(inputStream.nextLine());
        for (String topic: topicsArray) {
            topicsMap.put(topic,new VideoIdeaList());
        }

        while(inputStream.hasNextLine()){
            String[] lineInformation = separatorCSV(inputStream.nextLine());
            topicsMap.get(lineInformation[0]).addVideoIdea(new VideoIdea(lineInformation[0], lineInformation[1]));
        }

        if(topicsMap.isEmpty()){
            topicsMap.put("test", new VideoIdeaList());
        }

        inputStream.close();
        return topicsMap;
    }

    public String[] separatorCSV(String line){
        String separator = ",";
        return line.split(separator);
    }
}
