package sample;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;

import java.io.File;

public class Rage extends Character{
    static MediaPlayer mode1;
    static MediaPlayer mode3;
    public Rage (Circle place) {
        name="Rage";
        Damage=40;
        hitSpeed=40;
        speedS=40;
        Range=100;
        Cost=3;
        Place=place;
        isSpell=true;
        Traverse=false;
        LifeTime=5.5+level*0.5;
        img=new Image("file:snakeLava.png");
        DeathSound=new Media(new File("Death.mp3").toURI().toString());
        DeploySound=new Media(new File("RAGE.mp3").toURI().toString());
        if (mode1==null) mode1=new MediaPlayer(DeploySound);
        if (mode3==null) mode3=new MediaPlayer(DeathSound);
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
        return mode3;
    }
}
