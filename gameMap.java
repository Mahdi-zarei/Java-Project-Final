package sample;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class gameMap {
    private static double SideY1,SideY2,Bridge1_Mid,Bridge2_Mid;
    private Character selected;
    private static Character Pre;
    private double MX,MY;
    private boolean area1=false;
    private boolean area2=false;
    private final Rectangle2D Area1=new Rectangle2D(0,220,230,150);
    private final Rectangle2D Area2=new Rectangle2D(225,220,230,150);
    private static final Character[] used=new Character[4];
    private static ArrayList<Character> Deck=new ArrayList<>();
    private final HashMap<Character,ImageView> linker=new HashMap<>();
    private static final ArrayList<Character> AllUnits=new ArrayList<>();
    private final HashMap<Character,Integer> cleanUp=new HashMap<>();
    private final ArrayList<Character> concurrentFixer=new ArrayList<>();
    @FXML
    private Label info;
    @FXML
    private ImageView Plane;
    @FXML
    private ImageView IMG1,IMG2,IMG3,IMG4,TowerLeft1,TowerLeft2,King1,King2,TowerRight1,TowerRight2;
    @FXML
    private Pane PANE;
    @FXML
    private ImageView PreView;
    @FXML
    private ProgressBar elixirBar;
    double ELIXIR=5;
    @FXML
    private Label timmy;
    public static boolean isBot=false;
    public BOTMANAGER botmanager;
    private PrinceTower pt1,pt2,pte1,pte2;
    private KingTower kt1,kte1;
    private static final ArrayList<ImageView> imgs=new ArrayList<>();
    private static final ArrayList<Pane> sttc=new ArrayList<>();
    private static final HashMap<Shape,Long> projectile=new HashMap<>();
    private static final ArrayList<Shape> concFixProj=new ArrayList<>();
    public static boolean isEnded,Winner;
    private static gameMap instance;
    private boolean tst=true;
    private MediaPlayer Speeder;
    public void initialize() {
        instance=this;
        isEnded=false;
        AllUnits.clear();
        imgs.clear();
        sttc.clear();
        projectile.clear();
        concFixProj.clear();
        for (int i=0;i<4;i++) {
            used[i]=null;
        }
        sttc.add(PANE);
        imgs.add(IMG1);
        imgs.add(IMG2);
        imgs.add(IMG3);
        imgs.add(IMG4);
        imgs.add(PreView);
        Deck=Manager.getDeck();
        SideY1=459;
        SideY2=368;
        Bridge1_Mid=60;
        Bridge2_Mid=390;
        Character.level=Manager.getLevel();
        Speeder=new MediaPlayer(new Media(new File("SpeedUp.mp3").toURI().toString()));
        PANE.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if (tst) RUN();
            tst=false;
            MX=mouseEvent.getX();
            MY=mouseEvent.getY();
            if (MY>=800) return;
            if (MX>=430) MX=430;
            if (MX<=30) MX=30;
            if (MY>=760) MY=760;
            Deploy();
            mouseEvent.consume();
        });
    }

    public static gameMap getInstance() {
        return instance;
    }
    public void setTarget(Character c) {
        Character past=c.target;
        for (int i=0;i<AllUnits.size();i++) {
            Character tmp=AllUnits.get(i);
            if (buggyBridgy(c,tmp)) continue;
            if (!tmp.isSpell && c.canHit(tmp) && c.isEnemy!=tmp.isEnemy) {
                if (c.target==null || c.target.dead || buggyBridgy(c,c.target)) c.target = tmp;
                else {
                    if (c.Distance(tmp)<=c.Distance(c.target)) c.target=tmp;
                    //attacking the nearest valid target
                }
            }
        }
        if (past!=c.target) c.COOLER();
    }

    private boolean buggyBridgy(Character c1,Character c2) {
        return sideOf(c1) == sideOf(c2) && sideOf(c1) == 3 && RL(c1) != RL(c2);
    }
    public int RL(Character c) {
        if (c.Place.getCenterX()>=225) return 2;
        return 1;
    }

    public void setSpeed(Character c) {
        if (c.Distance(c.target)-30<=c.Range) {
            c.SpeedX=0;
            c.SpeedY=0;
            c.Update(true);
            return;
        }
        double deltaY,deltaX;
        double Slope;
        double TargetX,TargetY=0;
        if (sideOf(c)==sideOf(c.target)) {
            TargetX=c.target.Place.getCenterX();
            TargetY=c.target.Place.getCenterY();
        } else {
            if (RL(c)==1) TargetX=Bridge1_Mid;
            else TargetX=Bridge2_Mid;
            if (sideOf(c)==1) TargetY=SideY1-2;
            if (sideOf(c)==2) TargetY=SideY2+2;
            if (sideOf(c)==3 && sideOf(c.target)==2) TargetY=SideY2-2;
            if (sideOf(c)==3 && sideOf(c.target)==1) TargetY=SideY1+2;
        }
        if (Math.abs(TargetX-c.Place.getCenterX())<=25) TargetX=c.Place.getCenterX();
        deltaY=TargetY-c.Place.getCenterY();
        deltaX=TargetX-c.Place.getCenterX();
        if (deltaX==0) Slope=858569.6985;
        else Slope=deltaY/deltaX;
        if (Slope==858569.6985) {
            c.SpeedY=c.speedS;
            c.SpeedX=0;
        }
        else {
            c.SpeedX = Math.sqrt((c.speedS * c.speedS) / (Slope * Slope + 1));
            c.SpeedY = Math.sqrt((c.speedS * c.speedS * Slope * Slope) / (Slope * Slope + 1));
        }
        if (c.Place.getCenterX()>TargetX) c.SpeedX*=-1;
        if (c.Place.getCenterY()>TargetY) c.SpeedY*=-1;
        updateLocation(c);
        c.Update(false);
    }

    public boolean intersect(Character c1,Character c2) {
        return c1.Distance(c2) < 20 && c1 != c2;
    }

    public int sideOf(Character c) {
        if (c.Place.getCenterY()>SideY1) return 1;
        if (c.Place.getCenterY()<SideY2) return 2;
        return 3;
    }

    public int Turn(Circle Actor,Circle Obstacle) {
        if (Actor.getCenterX() >= Obstacle.getCenterX()) return -2;
        return 2;
    }

    private void CDHANDLER() {
        for (int i=0;i<AllUnits.size();i++) {
            Character tmp=AllUnits.get(i);
            if (!tmp.isSpell) tmp.CdHandler();
        }
    }
    public void updateLocation(Character c) {
        int sign=1;
        if (c.SpeedY>0) sign=-1;
        double x=c.Place.getCenterX();
        double y=c.Place.getCenterY();
        c.Place.setCenterX(x+c.SpeedX*0.5);
        c.Place.setCenterY(y+c.SpeedY*0.5);
        for (int i=0;i<AllUnits.size();i++) {
            Character tmp=AllUnits.get(i);
            if (intersect(c,tmp) && ((c.Place.getCenterY()>=tmp.Place.getCenterY() && c.SpeedY<0) ||
                    (c.Place.getCenterY()<=tmp.Place.getCenterY() && c.SpeedY>0))) {
                int sgn=Turn(c.Place,tmp.Place);
                sgn*=sign;
                c.Place.setCenterX(x+sgn*c.SpeedY*0.5);
                if (!tmp.isBuilding) tmp.Place.setCenterX(tmp.Place.getCenterX()-sgn*c.SpeedY*0.5);
                break;
            }
        }
        if (c.Place.getCenterX()<=350 && c.Place.getCenterX()>=100 && c.Place.getCenterY()<=SideY1 && c.Place.getCenterY()>=SideY2)
            System.out.println(sideOf(c)+" "+sideOf(c.target));
    }

    public void deadCollector(int secs) {
        for (int i=0;i<AllUnits.size();i++) {
            Character c=AllUnits.get(i);
            if (c.dead) {
                if (c.name.equals("Prince")) {
                    if (c.isEnemy) {
                        kte1.isActive = true;
                        if (c.Place.getCenterX()<250) area1=true;
                        else area2=true;
                    }
                    else kt1.isActive=true;
                }
                PANE.getChildren().remove(c.hBar);
                AllUnits.remove(c);
                if (c.isSpell && !c.name.equals("Rage")) linker.get(c).setImage(Character.getImage(c.name));
                else linker.get(c).setImage(Character.death);
                cleanUp.put(c,secs);
                concurrentFixer.add(c);
            }
        }
        for (int i=0;i<concurrentFixer.size();i++) {
            if (cleanUp.get(concurrentFixer.get(i))-secs>=3) {
                PANE.getChildren().remove(linker.get(concurrentFixer.get(i)));
                cleanUp.remove(concurrentFixer.get(i));
                linker.remove(concurrentFixer.get(i));
                concurrentFixer.remove(i);
            }
        }
        for (int i=0;i<concFixProj.size();i++) {
            Shape tmp=concFixProj.get(i);
            if (projectile.get(tmp)-System.currentTimeMillis()<=-250) {
                PANE.getChildren().remove(tmp);
                projectile.remove(tmp);
                concFixProj.remove(tmp);
            }
        }
    }

    public void UPDATE(int secs) {
        CDHANDLER();
        for (int i=0;i<AllUnits.size();i++) {
            Character tmp=AllUnits.get(i);
            if (tmp.isBuilding) tmp.DURATION();
            if (tmp.isSpell) SpellHandler(tmp);
            else {
                setTarget(AllUnits.get(i));
                setSpeed(AllUnits.get(i));
            }
        }
        deadCollector(secs);
    }

    private boolean isPermitted(Character c) {
        if (c.isSpell) return true;
        if (MY>=SideY1) return true;
        if (MY>=SideY2 && ((Math.abs(MX-Bridge1_Mid)<32) || (Math.abs(MX-Bridge2_Mid)<32))) return true;
        if (area1 && Area1.contains(MX,MY)) return true;
        return area2 && Area2.contains(MX, MY);
    }

    private static void getRand() {
        Random rand=new Random();
        while (true) {
            int x=rand.nextInt(8);
            if (isAvailable(Deck.get(x))) {
                Pre = Deck.get(x);
                break;
            }
        }
        imgs.get(4).setImage(Pre.img);
    }

    private static boolean isAvailable(Character c) {
        for (int i=0;i<4;i++) {
            if (used[i]!=null && used[i].name.equals(c.name)) return false;
        }
        return true;
    }

    public static void RefreshIMG() {
        if (Pre==null) Pre=Character.cloner(Deck.get(3));
        for (int i=0;i<4;i++) {
            if (used[i]==null) {
                used[i]=Pre;
                imgs.get(i).setImage(used[i].img);
                getRand();
            }
        }
    }

    public static void SplashAttack(Character hitter,Circle c) {
        for (int i=0;i<AllUnits.size();i++) {
            Character tmp=AllUnits.get(i);
            if (tmp.isSpell) continue;
            if (tmp.Distance(c)-20<=40 && hitter.isEnemy!=tmp.isEnemy && hitter.canHit(tmp)) {
                tmp.GetHit(hitter.Damage);
                gameMap.ProjectileAnimation(hitter,tmp);
            }
        }
    }

    private void SpellHandler(Character c) {
        if (c.Traverse) {
            for (int i=0;i<AllUnits.size();i++) {
                Character tmp=AllUnits.get(i);
                if (c.isEnemy!=tmp.isEnemy && c.Distance(tmp)<=c.Range) c.Attack(tmp);
            }
            c.dead=true;
        } else {
            c.DURATION();
            c.Update(false);
            for (int i=0;i<AllUnits.size();i++) {
                Character tmp=AllUnits.get(i);
                tmp.RAGER(c.isEnemy == tmp.isEnemy && c.Distance(tmp) <= c.Range && !c.dead);
            }

        }
    }

    private void Deploy() {
        if (selected==null || selected.Cost>ELIXIR || !isPermitted(selected)) return;
        selected.getMode1().stop();
        selected.getMode1().play();
        ELIXIR-=selected.Cost;
        for (int i=0;i<selected.count;i++) {
            ImageView tmp=new ImageView(selected.img);
            tmp.setX(MX+(i%2)*20);
            if (i<2) tmp.setY(MY);
            else tmp.setY(MY+20);
            Circle asd=new Circle(tmp.getX()+20,tmp.getY()+20,20);
            Character notNull=Character.cloner(selected);
            AllUnits.add(notNull);
            linker.put(notNull,tmp);
            ProgressBar ttmp=new ProgressBar();
            notNull.Setup(tmp,asd,false,ttmp);
            tmp.setPreserveRatio(true);
            tmp.setFitWidth(30);
            PANE.getChildren().add(ttmp);
            PANE.getChildren().add(tmp);
        }
        for (int i=0;i<4;i++) {
            if (used[i]==selected) used[i]=null;
        }
        selected=null;
        info.setText("");
        RefreshIMG();
    }
    @FXML
    public void select1() {
        selected=used[0];
        info.setText(selected.name);
    }

    @FXML
    public void select2() {
        selected=used[1];
        info.setText(selected.name);
    }

    @FXML
    public void select3() {
        selected=used[2];
        info.setText(selected.name);
    }

    @FXML
    public void select4() {
        selected=used[3];
        info.setText(selected.name);
    }

    public int CrowdedSide() {
        int s1=0,s2=0;
        for (int i=0;i<AllUnits.size();i++) {
            if (!AllUnits.get(i).isEnemy) {
                if (AllUnits.get(i).Place.getCenterX()<=225) s1++;
                else s2++;
            }
        }
        if (s1>=s2) return 1;
        else return 2;
    }

    public int ShouldDef() {
        for (int i=0;i<AllUnits.size();i++) {
            if (AllUnits.get(i).isSpell) continue;
            if (AllUnits.get(i).target==pte1 && !AllUnits.get(i).isBuilding) return 1;
        }
        for (int i=0;i<AllUnits.size();i++) {
            if (AllUnits.get(i).isSpell) continue;
            if (AllUnits.get(i).target==pte2 && !AllUnits.get(i).isBuilding) return 2;
        }
        for (int i=0;i<AllUnits.size();i++) {
            if (AllUnits.get(i).isSpell) continue;
            if (AllUnits.get(i).target==kte1 && !AllUnits.get(i).isBuilding) return 3;
        }
        return 0;
    }

    public void BotDeploy(Character tdp,double y,double x) {
        if (tdp==null || tdp.Cost>BOTMANAGER.BotElixir) return;
        BOTMANAGER.BotElixir-=tdp.Cost;
        tdp.getMode1().stop();
        tdp.getMode1().play();
        for (int i=0;i<tdp.count;i++) {
            ImageView tmp=new ImageView(tdp.img);
            tmp.setX(x+(i%2)*20);
            if (i<2) tmp.setY(y);
            else tmp.setY(y+20);
            Circle asd=new Circle(tmp.getX()+20,tmp.getY()+20,20);
            Character notNull=Character.cloner(tdp);
            AllUnits.add(notNull);
            BOTMANAGER.addUnit(notNull);
            linker.put(notNull,tmp);
            ProgressBar ttmp=new ProgressBar();
            notNull.Setup(tmp,asd,true,ttmp);
            tmp.setPreserveRatio(true);
            tmp.setFitWidth(30);
            PANE.getChildren().add(ttmp);
            PANE.getChildren().add(tmp);
        }
    }

    public Circle Frontier() {
        Character tmp=AllUnits.get(0);
        for (int i=0;i<AllUnits.size();i++) {
            if (tmp.Place.getCenterY()>AllUnits.get(i).Place.getCenterY() && !AllUnits.get(i).isEnemy && !AllUnits.get(i).isSpell) {
                tmp = AllUnits.get(i);
            }
        }
        return tmp.Place;
    }
    public static void ProjectileAnimation(Character c1,Character c2) {
        Pane tmp=sttc.get(0);
        Line proj=new Line(c1.Place.getCenterX(),c1.Place.getCenterY(),c2.Place.getCenterX(),c2.Place.getCenterY());
        Circle circle=new Circle(c2.Place.getCenterX(),c2.Place.getCenterY(),5);
        tmp.getChildren().add(proj);
        tmp.getChildren().add(circle);
        projectile.put(proj,System.currentTimeMillis());
        projectile.put(circle,System.currentTimeMillis());
        concFixProj.add(proj);
        concFixProj.add(circle);
    }


    private void setUpTowers() {
        pt1=new PrinceTower(new Circle(97,677,20));
        pt2=new PrinceTower(new Circle(353,677,20));
        pte1=new PrinceTower(new Circle(97,113,20));
        pte2=new PrinceTower(new Circle(353,113,20));
        kt1=new KingTower(new Circle(225,758,20));
        kte1=new KingTower(new Circle(225,56,20));
        linker.put(pt1,TowerLeft1);
        linker.put(pt2,TowerRight1);
        linker.put(kt1,King1);
        linker.put(kte1,King2);
        linker.put(pte1,TowerLeft2);
        linker.put(pte2,TowerRight2);
        PANE.getChildren().add(pt1.hBar);
        PANE.getChildren().add(pt2.hBar);
        PANE.getChildren().add(pte1.hBar);
        PANE.getChildren().add(pte2.hBar);
        PANE.getChildren().add(kt1.hBar);
        PANE.getChildren().add(kte1.hBar);
        kte1.isEnemy=true;
        pte1.isEnemy=true;
        pte2.isEnemy=true;
        AllUnits.add(pt1);
        AllUnits.add(pt2);
        AllUnits.add(kt1);
        AllUnits.add(pte1);
        AllUnits.add(pte2);
        AllUnits.add(kte1);
        if (isBot) {
            BOTMANAGER.getTowers(kte1);
            BOTMANAGER.getTowers(pte1);
            BOTMANAGER.getTowers(pte2);
        }
    }

    private void Postgame() {
        Manager.setScene("End");
        EndGame.setUp(Winner);
    }

    public void RUN() {
        setUpTowers();
        String musicFile = "GameMusic.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        new AnimationTimer() {
            int counter=0;
            String timer;
            int secs=180;
            long keeper=0;
            int multiPlyer=1;
            @Override
            public void handle(long l) {
                if (l-keeper<15000000) return;
                else keeper=l;
                if (isEnded) {
                    Postgame();
                    mediaPlayer.stop();
                    this.stop();
                }
                if (secs==60) {
                    multiPlyer=2;
                    timer="RAPID MODE";
                    Speeder.play();
                }
                if (secs==0) {
                    isEnded=true;
                    mediaPlayer.stop();
                    Winner= pt1.HP + pt2.HP + kt1.HP >= pte1.HP + pte2.HP + kte1.HP;
                    Postgame();
                    this.stop();
                }
                counter++;
                UPDATE(secs);
                if (counter%30==0) {
                    if (isBot) botmanager.DecisionMaker(multiPlyer);
                    ELIXIR += multiPlyer*0.5;
                    if (ELIXIR>10) ELIXIR=10;
                }
                timmy.setText(timer);
                timer="0"+(secs/60)+":";
                if (secs%60<10) timer+="0";
                timer+=(secs%60);
                if (counter%60==0) {
                    secs-=1;
                }
                elixirBar.setProgress(ELIXIR/10);

            }
        }.start();
    }
}