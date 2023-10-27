package com.example.typingmaster;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class Typing_Master_Main extends Application {
    private static Stage stg;
    @Override
    public void start(Stage stage) throws IOException {
        try {
            stg = stage;
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setTitle("Typing Master");
            Image image = new Image("C:\\Users\\avjot\\IdeaProjects\\TypingMaster\\Images\\icon.png");
            stage.getIcons().add(image);
            stage.setScene(scene);
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void changeScene(String fxml) throws IOException{
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stg.getScene().setRoot(pane);
    }

    public static void main(String[] args) {
        launch();
    }
}