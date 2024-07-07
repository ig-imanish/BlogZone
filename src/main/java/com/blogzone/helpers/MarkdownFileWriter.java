package com.blogzone.helpers;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MarkdownFileWriter {

    public static void saveMarkdownFile( String author,String fileName, String content) {
        try {
            
            String directoryPath = "src/main/resources/static/md-files/" + author;
            Path directory = Paths.get(directoryPath);
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }

            // Create file path
            Path filePath = Paths.get(directoryPath, fileName);

            // Write content to file
            FileWriter writer = new FileWriter(filePath.toString());
            writer.write(content);
            writer.close();

            System.out.println("Markdown file saved successfully: " + filePath.toString());

        } catch (IOException e) {
            System.err.println("Failed to save Markdown file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
