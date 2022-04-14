package sample;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;

import java.io.File;

public class Inferno extends Character{
    static MediaPlayer mode1;
    static MediaPlayer mode2;
    static MediaPlayer mode3;
    public Inferno (Circle place) {
        name="Inferno";
        HP=710+level*90;
        Damage=350+level*50;
        currentDamage=20;
        hitSpeed=0.4;
        HitType="ALL";
        State="Ground";
        Range=120;
        Splash=false;
        Cost=5;
        isBuilding=true;
        Place=place;
        LifeTime=40;
        img=new Image("file:snakeSlime.png");
        Attack=new Image("file:snakeSlime.png");
        DeathSound=new Media(new File("Death.mp3").toURI().toString());
        DeploySound=new Media(new File("INFERNO.mp3").toURI().toString());
        AttackSound=new Media(new File("InfernoAttack.mp3").toURI().toString());
        if (mode1==null) mode1=new MediaPlayer(DeploySound);
        if (mode2==null) mode2=new MediaPlayer(AttackSound);
        if (mode3==null) mode3=new MediaPlayer(DeathSound);
    }
    @Override
    public void Attack(Character c) {
        if (!CoolDown) {
            mode2.stop();
            mode2.play();
            gameMap.ProjectileAnimation(this,c);
            currentDamage+=8;
            currentDamage=Math.min(currentDamage,Damage);
            c.GetHit((int)currentDamage);
            CDDuration = hitSpeed;
        }
    }
    @Override
    public void CdHandler() {
        CDDuration-=0.016;
        if (CDDuration<=0) CoolDown=false;
        else CoolDown=true;
        if (CDDuration<=-0.020) currentDamage=30;
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
