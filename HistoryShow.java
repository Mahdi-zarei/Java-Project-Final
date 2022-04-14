package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.ArrayList;

public class HistoryShow {
    @FXML
    private Label img0,img1,img2,img3,img4,img5;
    private static final ArrayList<Label> imgs=new ArrayList<>();
    public void initialize() {
        imgs.add(img0);
        imgs.add(img1);
        imgs.add(img2);
        imgs.add(img3);
        imgs.add(img4);
        imgs.add(img5);
    }

    public static void showUp() {
        ArrayList<String> history = Manager.getHistory();
        int ind=0;
        for (int i = 0; i< history.size(); i++) {
            imgs.get(ind).setText(history.get(i));
            ind++;
            if (ind==6) break;
        }
    }
    @FXML
    private void Back() {
        Manager.setScene("Menu");
    }
}
