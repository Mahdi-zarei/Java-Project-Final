package sample;

import java.util.ArrayList;
import java.util.Random;

public class BOTMANAGER {
    static double BotElixir=5;
    private final ArrayList<Character> defense=new ArrayList<>();
    private final ArrayList<Character> attack=new ArrayList<>();
    private static final ArrayList<Character> UNITS=new ArrayList<>();
    private final gameMap Logicer;
    private final double tokenMode;

    public static void getTowers(Character character) {
        UNITS.add(character);
    }

    public static void addUnit(Character c) {
        UNITS.add(c);
    }

    public BOTMANAGER(double tokenMode) {
        UNITS.clear();
        defense.add(new Canon(null));
        defense.add(new Inferno(null));
        attack.add(new FireBall(null));
        attack.add(new Arrows(null));
        attack.add(new Barber(null));
        attack.add(new Archer(null));
        attack.add(new Pekka(null));
        attack.add(new Giant(null));
        attack.add(new Wizard(null));
        attack.add(new Valkyrie(null));
        attack.add(new Dragon(null));
        Logicer=gameMap.getInstance();
        gameMap.isBot =true;
        Logicer.botmanager=this;
        this.tokenMode=tokenMode;
    }

    private Character getRand(ArrayList<Character> tmp) {
        Random rand=new Random();
        int x= rand.nextInt(tmp.size());
        return tmp.get(x);
    }

    public void DecisionMaker(int mult) {
        BotElixir+=tokenMode*mult*0.5;
        if (BotElixir>10) BotElixir=10;
        Character tmp;
        double y1 = 200;
        double x1 = 70;
        double x2 = 370;
        if (Logicer.ShouldDef()==3) {
            tmp=getRand(defense);
            Logicer.BotDeploy(tmp,70,225);
            tmp=getRand(attack);
            Logicer.BotDeploy(tmp,70,225);
            return;
        } else if (Logicer.ShouldDef()==2) {
            tmp=getRand(defense);
            Logicer.BotDeploy(tmp, y1, x2);
            tmp=getRand(attack);
            Logicer.BotDeploy(tmp, y1, x2);
            return;
        } else if (Logicer.ShouldDef()==1) {
            tmp=getRand(defense);
            Logicer.BotDeploy(tmp, y1, x1);
            tmp=getRand(attack);
            Logicer.BotDeploy(tmp, y1, x1);
            return;
        }
        tmp=getRand(attack);
        if (tmp.isSpell) Logicer.BotDeploy(tmp,Logicer.Frontier().getCenterY(),Logicer.Frontier().getCenterX());
        else {
            double x=Logicer.CrowdedSide();
            if (x==1) x= x1;
            else x= x2;
            Logicer.BotDeploy(tmp, y1,x);
        }
    }
}
