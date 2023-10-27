package com.example.typingmaster;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.sql.*;

public class Login_Controller {

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    public String user;

    private final Alert alert = new Alert(Alert.AlertType.ERROR);

    @FXML
    void Login(ActionEvent event) throws IOException {
        user = username.getText();
        String passwd = password.getText();

        if ((user.isEmpty()) || (passwd.isEmpty())){
            alert.setTitle("ERROR!!");
            alert.setHeaderText("Input Error");
            alert.setContentText("Username and Password field should not be empty.");
            alert.showAndWait();
        }
        else{
            String jdbcURL = "jdbc:sqlite:/C:\\Users\\avjot\\IdeaProjects\\TypingMaster\\Database\\TypingMasterDB.db";

            try{
                Connection connection = DriverManager.getConnection(jdbcURL);
                try{
                    String query = "Select * from Users";

                    Statement statement = connection.createStatement();
                    ResultSet result = statement.executeQuery(query);

                    boolean counter = false;
                    String DatabaseUsername;
                    String DatabasePassword;

                    while (result.next()){
                        DatabaseUsername =  result.getString("Username");
                        DatabasePassword = result.getString("Password");
                        if ((user.equals(DatabaseUsername)) && (passwd.equals(DatabasePassword))){
                            counter = true;
                            break;
                        }
                    }
                    if (!counter){
                        alert.setTitle("ERROR!!");
                        alert.setHeaderText("Input Error");
                        alert.setContentText("Username or password Incorrect.");
                        alert.showAndWait();
                    }
                    else{
                        PlayGame_Controller p = new PlayGame_Controller();
                        p.SetUsername(user);

                        Game_Controller g = new Game_Controller();
                        g.SetUsername(user);

                        Typing_Master_Main m = new Typing_Master_Main();
                        m.changeScene("PlayGame.fxml");
                    }
                } catch(SQLException e) {
                    alert.setTitle("ERROR!!");
                    alert.setHeaderText("SQL Error");
                    alert.setContentText("An error occurred while logging you in. Please try again.");
                    alert.showAndWait();
                }
                connection.close();
            } catch(SQLException e) {
                alert.setTitle("ERROR!!");
                alert.setHeaderText("SQL Error.");
                alert.setContentText("Error in connecting to database.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    void SignupScreen(ActionEvent event) throws IOException {
        Typing_Master_Main m = new Typing_Master_Main();
        m.changeScene("Signup.fxml");
    }
}
