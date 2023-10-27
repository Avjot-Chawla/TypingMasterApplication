package com.example.typingmaster;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class PlayGame_Controller implements Initializable {

    @FXML
    private Text Invalid;

    @FXML
    private Text Total;

    @FXML
    private Text WPM;

    @FXML
    private ImageView Icon;

    private static String username;
    private final Alert alert = new Alert(Alert.AlertType.ERROR);

    public void SetUsername(String user_name) {
        username = user_name;
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        File IconImageFile = new File("Images/icon.png");
        Image IconImage = new Image(IconImageFile.toURI().toString());
        Icon.setImage(IconImage);

        String jdbcURL = "jdbc:sqlite:/C:\\Users\\avjot\\IdeaProjects\\TypingMaster\\Database\\TypingMasterDB.db";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL);
            try {
                String query = "Select * from AppData where username='%s'";
                query = String.format(query,username);

                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(query);

                int TotalEntered = result.getInt("TotalEntered");
                int AverageWPM = result.getInt("AverageWPM");
                int invalid = result.getInt("Invalid");

                Total.setText(String.valueOf(TotalEntered));
                WPM.setText(String.valueOf(AverageWPM));
                Invalid.setText(String.valueOf(invalid));

                result.close();
            }catch (SQLException e){
                alert.setTitle("ERROR!!");
                alert.setHeaderText("SQL Error");
                alert.setContentText("An unknown error has occurred.");
                alert.showAndWait();
            }

            connection.close();
        }catch (SQLException e){
            alert.setTitle("ERROR!!");
            alert.setHeaderText("SQL Error");
            alert.setContentText("An error has occurred while connecting to database.");
            alert.showAndWait();
        }
    }

    @FXML
    void Play(ActionEvent event) throws IOException {
        Typing_Master_Main m = new Typing_Master_Main();
        m.changeScene("Game.fxml");
    }

    @FXML
    void LogOut(ActionEvent event) throws IOException {
        Typing_Master_Main m = new Typing_Master_Main();
        m.changeScene("Login.fxml");
    }
}
