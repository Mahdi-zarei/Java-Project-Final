package sample;


import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Character {
    private static final ArrayList<Character> Cacher=new ArrayList<>();
    private static final HashMap<String,Image> linker=new HashMap<>();
    String name;
    Image img;
    Image Attack;
    static Image death;
    static int level;
    int count=1;
    int HP;
    int Damage;
    double hitSpeed;
    double CDDuration;
    boolean CoolDown;
    double speedS;
    double SpeedX,SpeedY;
    String HitType;
    String State;
    int Range;
    boolean Splash;
    int Cost;
    double LifeTime;
    boolean isBuilding;
    Circle Place;
    boolean isSpell=false;
    boolean isEnemy=false;
    boolean dead=false;
    Character target;
    boolean isActive=true;
    boolean Traverse=true;
    boolean isRaged=false;
    double currentDamage;
    ImageView Viewer=new ImageView();
    double MaxHp;
    double MaxDuration;
    ProgressBar hBar=new ProgressBar();
    Media DeathSound;
    Media AttackSound;
    Media DeploySound;

    public static void init() {
        death=new Image("file:ghost_dead.png");
        level=Manager.getLevel();
        Cacher.add(new Dragon(null));
        Cacher.add(new Canon(null));
        Cacher.add(new Arrows(null));
        Cacher.add(new Barber(null));
        Cacher.add(new Inferno(null));
        Cacher.add(new Pekka(null));
        Cacher.add(new Rage(null));
        Cacher.add(new Valkyrie(null));
        Cacher.add(new Wizard(null));
        Cacher.add(new Archer(null));
        Cacher.add(new FireBall(null));
        Cacher.add(new Giant(null));
        for (int i=0;i<12;i++) {
            linker.put(Cacher.get(i).name,Cacher.get(i).img);
        }
    }

    public static Character cloner(Character c) {
        if (c.name.equals("Barber")) return new Barber(null);
        if (c.name.equals("Archer")) return new Archer(null);
        if (c.name.equals("Canon")) return new Canon(null);
        if (c.name.equals("Dragon")) return new Dragon(null);
        if (c.name.equals("Inferno")) return new Inferno(null);
        if (c.name.equals("Giant")) return new Giant(null);
        if (c.name.equals("Valkyrie")) return new Valkyrie(null);
        if (c.name.equals("Wizard")) return new Wizard(null);
        if (c.name.equals("Rage")) return new Rage(null);
        if (c.name.equals("FireBall")) return new FireBall(null);
        if (c.name.equals("Arrows")) return new Arrows(null);
        if (c.name.equals("Pekka")) return new Pekka(null);
        return null;
    }

    public double Distance(Character c) {
        return Math.sqrt(Math.pow((Place.getCenterX()-c.Place.getCenterX()),2)+Math.pow(Place.getCenterY()-c.Place.getCenterY(),2));
    }

    public double Distance(Circle c) {
        return Math.sqrt(Math.pow((Place.getCenterX()-c.getCenterX()),2)+Math.pow(Place.getCenterY()-c.getCenterY(),2));
    }

    public void RAGER(boolean mode) {
        if (mode) {
            if (!isRaged) {
                isRaged=true;
                hitSpeed *= 0.6;
                Damage *= 1.4;
                speedS *= 1.4;
            }
        }
        else {
            if (isRaged) {
                isRaged=false;
                hitSpeed /= 0.6;
                Damage /= 1.4;
                speedS /= 1.4;
            }
        }
    }

    public void COOLER() {
        currentDamage=30;
    }
    public boolean canHit(Character c) {
        if (HitType.equals("Ground")) {
            return !c.State.equals("Air");
        }
        if (HitType.equals("Building")) {
            return c.isBuilding;
        }
        return true;
    }

    public abstract MediaPlayer getMode1() ;
    public abstract void getMode2() ;
    public abstract MediaPlayer getMode3() ;
    public void Setup(ImageView imageView,Circle circle,boolean side,ProgressBar hBar) {
        MaxHp=HP;
        MaxDuration=LifeTime;
        Viewer=imageView;
        Place=circle;
        isEnemy=side;
        this.hBar=hBar;
        hBar.setMaxWidth(30);
        hBar.setMaxHeight(13);
    }

    public void Update(boolean isAttack) {
        if (isSpell) {
            hBar.setLayoutX(Viewer.getX());
            hBar.setLayoutY(Viewer.getY()-8);
            hBar.setProgress(LifeTime/MaxDuration);
            return;
        }
        if (isAttack && isActive) {
            Viewer.setImage(Attack);
            Attack(target);
        }
        else Viewer.setImage(img);
        Viewer.setX(Place.getCenterX()-20);
        Viewer.setY(Place.getCenterY()-20);
        hBar.setLayoutX(Viewer.getX());
        hBar.setLayoutY(Viewer.getY()-8);
        hBar.setProgress(HP/MaxHp);
    }

    public void CdHandler() {
        CDDuration-=0.016;
        CoolDown= !(CDDuration <= 0);
    }

    public void Attack(Character c) {
        if (!CoolDown) {
            if (!isSpell) {
                this.getMode2();
            }
            gameMap.ProjectileAnimation(this,c);
            if (Splash) {
                Circle tmp=new Circle();
                if (Range<40) {
                    tmp.setCenterX(Place.getCenterX());
                    tmp.setCenterY(Place.getCenterY());
                    tmp.setRadius(Range+20);
                }
                else tmp= target.Place;
                CDDuration = hitSpeed;
                gameMap.SplashAttack(this,tmp);
            }
            else {
                c.GetHit(Damage);
                CDDuration = hitSpeed;
            }
        }
    }

    public void DURATION() {
        LifeTime-=0.016;
        if (LifeTime<=0) dead=true;
    }

    public void GetHit(int dmg) {
        if (isSpell) return;
        HP-=dmg;
        if (HP<=0) {
            dead = true;
            this.getMode3().stop();
            this.getMode3().play();
        }
    }

    public static void addToDeck(String name) {
        for (int i=0;i<Cacher.size();i++) {
            if (Cacher.get(i).name.equals(name)) {
                Manager.Decker(Cacher.get(i));
                return;
            }
        }
        Manager.Decker(null);
    }

    public static Image getImage(String s) {
        return linker.get(s);
    }
}
