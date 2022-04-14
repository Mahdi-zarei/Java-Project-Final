package sample;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;

import java.io.File;

public class FireBall extends Character{
    static MediaPlayer mode1;
    static MediaPlayer mode3;
    public FireBall (Circle place) {
        name="FireBall";
        count=1;
        Damage=275+level*50;
        Range=50;
        Splash=false;
        Cost=4;
        Place=place;
        isSpell=true;
        img=new Image("file:spinner_hit.png");
        DeathSound=new Media(new File("Death.mp3").toURI().toString());
        DeploySound=new Media(new File("FireBall.mp3").toURI().toString());
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
