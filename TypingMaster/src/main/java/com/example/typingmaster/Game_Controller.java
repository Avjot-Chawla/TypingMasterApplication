package com.example.typingmaster;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Game_Controller implements Initializable{
    @FXML
    private Text Accuracy;

    @FXML
    private ImageView Correct;

    @FXML
    private Button PlayAgain;

    @FXML
    private Text ProgramWord1;

    @FXML
    private Text ProgramWord2;

    @FXML
    private Text Seconds;

    @FXML
    private TextField UserWord;

    @FXML
    private Text WordsPerMin;

    @FXML
    private ImageView Wrong;

    private int WordCounter = 0;
    private int first = 1;
    private int timer = 60;
    private int TotalCount = 0;
    private int CorrectCount = 0;
    private int Acc = 0;
    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    private static String username;
    private final Alert alert = new Alert(Alert.AlertType.ERROR);
    ArrayList<String> words = new ArrayList<>();

    public void SetUsername(String user_name) {
        username = user_name;
    }

    public void addToList() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("Words.txt"));
            String line = reader.readLine();
            while (line != null) {
                words.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize(URL url, ResourceBundle resourceBundle){
        File CorrectImageFile = new File("Images/check.png");
        Image CorrectImage = new Image(CorrectImageFile.toURI().toString());
        Correct.setImage(CorrectImage);

        File WrongImageFile = new File("Images/wrong.png");
        Image WrongImage = new Image(WrongImageFile.toURI().toString());
        Wrong.setImage(WrongImage);

        PlayAgain.setVisible(false);
        PlayAgain.setDisable(true);
        addToList();
        Collections.shuffle(words);
        ProgramWord1.setText(words.get(WordCounter));
        ProgramWord2.setText(words.get(WordCounter+1));
        WordCounter++;
    }

    Runnable r = new Runnable() {
        @Override
        public void run() {
            if (timer > -1) {
                Seconds.setText(String.valueOf(timer));
                timer -= 1;
            } else if (timer == -1) {
                UserWord.setDisable(true);
                UserWord.setText("Game Over");

                String jdbcURL = "jdbc:sqlite:/C:\\Users\\avjot\\IdeaProjects\\TypingMaster\\Database\\TypingMasterDB.db";
                try{
                    Connection connection = DriverManager.getConnection(jdbcURL);
                    try {
                        String query = "Select * from AppData where username = '%s'";
                        query = String.format(query, username);

                        Statement statement = connection.createStatement();
                        ResultSet result = statement.executeQuery(query);

                        int totalPlayed = result.getInt("TotalPlayed");
                        int totalEntered = result.getInt("TotalEntered");
                        int AverageWPM = result.getInt("AverageWPM");
                        int Invalid = result.getInt("Invalid");
                        int inv = TotalCount - CorrectCount;

                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                        LocalDateTime now = LocalDateTime.now();
                        String DateTime = dtf.format(now);

                        query = "Insert into History values ('%s','%s',%d,%d,%d)";
                        query = String.format(query, username, DateTime, CorrectCount, Acc, inv);
                        statement.executeUpdate(query);

                        int NewTotalPlayed = totalPlayed+1;
                        int NewTotalEntered = totalEntered+TotalCount;
                        int NewAverageWPM = (AverageWPM*totalPlayed + CorrectCount)/(NewTotalPlayed);
                        int NewInvalid = Invalid+inv;

                        query = "Update AppData set TotalPlayed=%d, TotalEntered=%d, AverageWPM=%d, Invalid=%d where username='%s'";
                        query = String.format(query, NewTotalPlayed , NewTotalEntered, NewAverageWPM, NewInvalid, username);
                        statement.executeUpdate(query);
                    }catch (SQLException e) {
                        alert.setTitle("ERROR!!");
                        alert.setHeaderText("SQLError.");
                        alert.setContentText("An error was occurred while entering data into database.");
                        alert.showAndWait();
                        System.out.println(e.getMessage());
                    }
                    connection.close();
                } catch(SQLException e) {
                    alert.setTitle("ERROR!!");
                    alert.setHeaderText("SQL Error.");
                    alert.setContentText("Error in connecting to database.");
                    alert.showAndWait();
                }

                PlayAgain.setVisible(true);
                PlayAgain.setDisable(false);
                executor.shutdown();
            }
        }
    };

    Runnable DisplayCorrect = new Runnable() {
        @Override
        public void run() {
            Correct.setOpacity(0);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Correct.setOpacity(50);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Correct.setOpacity(100);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Correct.setOpacity(0);
        }
    };

    Runnable DisplayWrong = new Runnable() {
        @Override
        public void run() {
            Wrong.setOpacity(0);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Wrong.setOpacity(50);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Wrong.setOpacity(100);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Wrong.setOpacity(0);
        }
    };

    @FXML
    void startGame(KeyEvent keyEvent) {

        if (first == 1) {
            first = 0;
            executor.scheduleAtFixedRate(r, 0, 1, TimeUnit.SECONDS);
        }

        if ((keyEvent.getCode().equals(KeyCode.ENTER)) || (keyEvent.getCode().equals(KeyCode.SPACE))) {
            String Uword = UserWord.getText().trim();
            String Pword = ProgramWord1.getText();
            TotalCount++;

            if (Uword.equals(Pword)) {
                CorrectCount++;
                WordsPerMin.setText(String.valueOf(CorrectCount));

                Thread t = new Thread(DisplayCorrect);
                t.start();
            }
            else{
                Thread t = new Thread(DisplayWrong);
                t.start();
            }

            Acc = (int)(Math.round((CorrectCount * 1.0/ TotalCount) * 100));
            UserWord.setText("");
            Accuracy.setText(String.valueOf(Acc));
            ProgramWord1.setText(words.get(WordCounter));
            ProgramWord2.setText(words.get(WordCounter+1));
            WordCounter++;
        }
    }

    @FXML
    void toMainMenu(ActionEvent event) throws IOException {
        executor.shutdown();
        Typing_Master_Main m = new Typing_Master_Main();
        m.changeScene("PlayGame.fxml");
    }
}
