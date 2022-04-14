package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class DeckPick {
    @FXML
    private ImageView img1,img2,img3,img4,img5,img6,img7,img8;
    private static ArrayList<ImageView> imgs=new ArrayList<>();
    private static ArrayList<String> pre=new ArrayList<>();
    private static int ind=0;
    private String holder;
    @FXML
    private Label answer;
    @FXML
    private Label LevelShow;
    @FXML
    private ProgressBar prog;
    private static ArrayList<Label> sttc=new ArrayList<>();
    private static ArrayList<ProgressBar> sttc2=new ArrayList<>();
    public void initialize() {
        imgs.add(img1);
        imgs.add(img2);
        imgs.add(img3);
        imgs.add(img4);
        imgs.add(img5);
        imgs.add(img6);
        imgs.add(img7);
        imgs.add(img8);
        sttc.add(LevelShow);
        sttc2.add(prog);
    }

    public static void LastDeck(ArrayList<Character> tmp) {
        ind=0;
        for (int i=0;i<8;i++) {
            if (tmp.get(i)==null) return;
            imgs.get(i).setImage(tmp.get(i).img);
            pre.add(tmp.get(i).name);
            ind++;
        }
        sttc.get(0).setText("Level "+String.valueOf(Manager.getLevel()));
        sttc2.get(0).setProgress(Manager.getEXP());
    }

    @FXML
    public void Reset() {
        ind=0;
        for (int i=0;i<8;i++) {
            imgs.get(i).setImage(null);
        }
        pre.clear();
        answer.setText("Cleared The deck");
    }
    @FXML
    public void Adder() {
        if (!pre.contains(holder) && ind<8) {
            pre.add(holder);
            imgs.get(ind).setImage(Character.getImage(holder));
            ind++;
            answer.setText(holder+" is added!");
        } else answer.setText("Error adding to deck");
    }
    @FXML
    public void Drag() {
        holder="Dragon";
    }
    @FXML
    public void Barb() {
        holder="Barber";
    }
    @FXML
    public void Arch() {
        holder="Archer";
    }
    @FXML
    public void Can() {
        holder="Canon";
    }
    @FXML
    public void Arr() {
        holder="Arrows";
    }
    @FXML
    public void Inf() {
        holder="Inferno";
    }
    @FXML
    public void Fire() {
        holder="FireBall";
    }
    @FXML
    public void Gi() {
        holder="Giant";
    }
    @FXML
    public void pek() {
        holder="Pekka";
    }
    @FXML
    public void Valk() {
        holder="Valkyrie";
    }
    @FXML
    public void Rag() {
        holder="Rage";
    }
    @FXML
    public void Wiz() {
        holder="Wizard";
    }
    @FXML
    public void Finish() {
        if (ind!=8) {
            answer.setText("Please complete the deck!");
            return;
        }
        ind=1;
        Manager.ClearDeck();
        for (int i=0;i<8;i++) {
            Character.addToDeck(pre.get(i));
        }
        Manager.setScene("Menu");
    }
}
