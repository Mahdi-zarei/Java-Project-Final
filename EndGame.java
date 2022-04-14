package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class EndGame {
    private static ArrayList<Label> sttc=new ArrayList<>();
    @FXML
    private Label result;
    @FXML
    private Label Loots;
    @FXML
    private Label LEVELLER;
    public void initialize() {
        sttc.add(result);
        sttc.add(Loots);
        sttc.add(LEVELLER);
    }
    @FXML
    public void Exit() {
        Manager.setScene("Menu");
    }

    public static void setUp(boolean winner) {
        boolean lvlup;
        if (!gameMap.isBot) return;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        LocalDateTime now = LocalDateTime.now();
        if (winner) {
            sttc.get(0).setText("Congratulations! You won!");
            sttc.get(1).setText("You have gained 200 Experience!");
            lvlup=Manager.CalculateLevel(200);
            Manager.updHistory(  Login.getUser()+" "+"Bot"+" "+ dtf.format(now));
        } else {
            sttc.get(0).setText("You have been Defeated! too noob");
            sttc.get(1).setText("You have gained 50 Experience!");
            lvlup=Manager.CalculateLevel(50);
            Manager.updHistory( "Bot" +" "+Login.getUser()+" "+ dtf.format(now) );
        }
        if (lvlup) {
            sttc.get(2).setText("CONGRATULATIONS! YOU HAVE REACHED LEVEL "+Manager.getLevel());
            try {
                Manager.update();
            } catch(Exception e) {

            }
        }
    }
}
