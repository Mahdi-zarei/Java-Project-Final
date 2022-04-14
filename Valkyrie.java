package sample;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;

import java.io.File;

public class Valkyrie extends Character{
    static MediaPlayer mode1;
    static MediaPlayer mode2;
    static MediaPlayer mode3;
    public Valkyrie (Circle place) {
        name="Valkyrie";
        count=1;
        HP=780+level*100;
        Damage=106+level*14;
        hitSpeed=1.5;
        speedS=1;
        HitType="Ground";
        State="Ground";
        Range=30;
        Splash=true;
        Cost=4;
        isBuilding=false;
        Place=place;
        img=new Image("file:spider.png");
        Attack=new Image("file:spider_walk1.png");
        DeathSound=new Media(new File("Death.mp3").toURI().toString());
        DeploySound=new Media(new File("Deploy.mp3").toURI().toString());
        AttackSound=new Media(new File("ValkAttack.mp3").toURI().toString());
        if (mode1==null) mode1=new MediaPlayer(DeploySound);
        if (mode2==null) mode2=new MediaPlayer(AttackSound);
        if (mode3==null) mode3=new MediaPlayer(DeathSound);
    }
    @Override
    public MediaPlayer getMode1() {
        return mode1;
    }

    @Override
    public void getMode2() {
        mode2.stop();
        mode2.play();
    }

    @Override
    public MediaPlayer getMode3() {
        return mode3;
    }
}
