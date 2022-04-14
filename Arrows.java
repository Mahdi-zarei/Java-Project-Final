package sample;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;

import java.io.File;

public class Arrows extends Character{
    static MediaPlayer mode1;
    public Arrows (Circle place) {
        name="Arrows";
        Damage=130+level*17;
        Range=80;
        Cost=3;
        Place=place;
        isSpell=true;
        img=new Image("file:weapon_ballista_N.png");
        DeploySound=new Media(new File("Arrows.mp3").toURI().toString());
        if (mode1==null) mode1=new MediaPlayer(DeploySound);
    }
    @Override
    public MediaPlayer getMode1() {
        return mode1;
    }
    @Override
    public void getMode2() {
    }

    @Override
    public MediaPlayer getMode3() {
        return null;
    }
}
