package sample;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;

import java.io.File;

public class PrinceTower extends Character{
    static MediaPlayer mode1;
    static MediaPlayer mode2;
    static MediaPlayer mode3;
    public PrinceTower(Circle place) {
        name="Prince";
        LifeTime=100000;
        isBuilding=true;
        HP=level*125+1275;
        Damage=46+level*5;
        Range=150;
        HitType="ALL";
        hitSpeed=0.8;
        isBuilding=true;
        State="Ground";
        Place=place;
        MaxHp=HP;
        hBar.setMaxWidth(40);
        hBar.setMaxHeight(13);
        DeathSound=new Media(new File("TowerFall.mp3").toURI().toString());
        DeploySound=new Media(new File("Deploy.mp3").toURI().toString());
        AttackSound=new Media(new File("TowerAttack.mp3").toURI().toString());
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
