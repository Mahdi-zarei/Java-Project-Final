package sample;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;

import java.io.File;

public class KingTower extends Character{
    static MediaPlayer mode1;
    static MediaPlayer mode2;
    static MediaPlayer mode3;
    public KingTower(Circle place) {
        name="king";
        LifeTime=100000;
        isBuilding=true;
        HP=level*180+2200;
        Damage=46+level*4;
        Range=140;
        HitType="ALL";
        hitSpeed=1;
        isBuilding=true;
        State="Ground";
        isActive=false;
        Place=place;
        isActive=false;
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
    public void GetHit(int dmg) {
        isActive=true;
        HP-=dmg;
        if (HP<=0) {
            dead = true;
            gameMap.isEnded=true;
            gameMap.Winner=isEnemy;
        }
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
