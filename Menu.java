package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

public class Menu {
    private double levelToken=0;
    @FXML
    private Label Tag;
    @FXML
    private Label Mode;
    @FXML
    private void Profile() {
        Manager.setScene("Deck");
    }
    @FXML
    private void History() {
        Manager.setScene("History");
    }
    @FXML
    private void Bot() throws Exception{
        if (levelToken==0) {
            Mode.setText("Select a mode");
            return;
        }
        Manager.toShow.setRoot(FXMLLoader.load(getClass().getResource("gameMap.fxml")));
        gameMap.RefreshIMG();
        BOTMANAGER botmanager=new BOTMANAGER(levelToken);
    }
    @FXML
    private void OnlineV1() throws Exception{
        Manager.toShow.setRoot(FXMLLoader.load(getClass().getResource("gameMap.fxml")));
        gameMap.RefreshIMG();
    }
    @FXML
    private void MakeEasy() {
        levelToken=0.5;
        Mode.setText(" Mode: Easy");
    }
    @FXML
    private void MakeHard() {
        levelToken=1;
        Mode.setText(" Mode: Hard");
    }
    @FXML
    private void OUT() {
        System.exit(0);
    }
}
