package com.example.typingmaster;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Signup_Controller {

    @FXML
    private PasswordField ConfirmPassword;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    private final Alert alert = new Alert(Alert.AlertType.ERROR);

    @FXML
    void Login(ActionEvent event) throws IOException {
        Typing_Master_Main m = new Typing_Master_Main();
        m.changeScene("Login.fxml");
    }

    @FXML
    void SignUp(ActionEvent event) throws IOException {
        String user = username.getText();
        String passwd = password.getText();
        String confirmPasswd = ConfirmPassword.getText();

        if ((user.isEmpty()) || (passwd.isEmpty())){
            alert.setTitle("ERROR!!");
            alert.setHeaderText("Input Error");
            alert.setContentText("Username and Password field should not be empty.");
            alert.showAndWait();
        } else if (!passwd.equals(confirmPasswd)) {
            alert.setTitle("ERROR!!");
            alert.setHeaderText("Input Error");
            alert.setContentText("Password and Confirm password do not match.");
            alert.showAndWait();
        } else{
            String jdbcURL = "jdbc:sqlite:/C:\\Users\\avjot\\IdeaProjects\\TypingMaster\\Database\\TypingMasterDB.db";

            try{
                Connection connection = DriverManager.getConnection(jdbcURL);
                try{
                    String query = "Insert into Users values ('%s','%s')";
                    query = String.format(query,user,passwd);
                    Statement statement = connection.createStatement();
                    statement.executeUpdate(query);

                    query = "Insert into AppData(Username) values ('%s')";
                    query = String.format(query,user);
                    statement.executeUpdate(query);

                    connection.close();

                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setTitle("Note");
                    alert.setHeaderText("User details saved.");
                    alert.setContentText("Please login to continue.");
                    alert.showAndWait();
                    alert.setAlertType(Alert.AlertType.ERROR);

                    Typing_Master_Main m = new Typing_Master_Main();
                    m.changeScene("Login.fxml");
                } catch(SQLException e) {
                    alert.setTitle("ERROR!!");
                    alert.setHeaderText("Username Already exists.");
                    alert.setContentText("Please enter a different username.");
                    alert.showAndWait();
                }
            } catch(SQLException e) {
                alert.setTitle("ERROR!!");
                alert.setHeaderText("SQL Error.");
                alert.setContentText("Error in connecting to database.");
                alert.showAndWait();
            }
        }
    }
}
