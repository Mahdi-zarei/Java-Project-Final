package sample;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;

import java.io.File;

public class Giant extends Character{
    static MediaPlayer mode1;
    static MediaPlayer mode2;
    static MediaPlayer mode3;
    public Giant (Circle place) {
        name="Giant";
        count=1;
        HP=1780+level*230;
        Damage=111+level*15;
        hitSpeed=1.5;
        speedS=0.5;
        HitType="Building";
        State="Ground";
        Range=20;
        Splash=false;
        Cost=5;
        isBuilding=false;
        Place=place;
        img=new Image("file:alienBlue.png");
        Attack=new Image("file:alienBlue_jump.png");
        DeathSound=new Media(new File("Death.mp3").toURI().toString());
        DeploySound=new Media(new File("Deploy.mp3").toURI().toString());
        AttackSound=new Media(new File("GiantAttack.mp3").toURI().toString());
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
