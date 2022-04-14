package sample;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    private static final HashMap<String,Pane> pager=new HashMap<>();
    public static Scene toShow;
    private static final ArrayList<String> dataUser=new ArrayList<>();
    private static final ArrayList<String> dataPass=new ArrayList<>();
    private static final ArrayList<String> dataName=new ArrayList<>();
    private static final ArrayList<Character> Deck=new ArrayList<>();
    private static final ArrayList<String> Helper=new ArrayList<>();
    private static final ArrayList<Pair<String,String>> History=new ArrayList<>();
    private static final ArrayList<String> UserHistory=new ArrayList<>();
    private static final ArrayList<String> HistoryTime=new ArrayList<>();
    private static int level=1;
    private static int EXP=0;
    public Manager(Scene s) {
        toShow=s;
        try {
            BufferedReader br=new BufferedReader(new FileReader("Data.txt"));
            BufferedReader hr=new BufferedReader(new FileReader("History.txt"));
            while (true) {
                String tmp=br.readLine();
                if (tmp==null) break;
                Helper.add(tmp);
                String[] spl=tmp.split(" ");
                dataUser.add(spl[0]);
                dataPass.add(spl[1]);
                dataName.add(spl[2]);
            }
            while (true) {
                String tmp=hr.readLine();
                if (tmp==null) break;
                String[] spl=tmp.split(" ");
                History.add(new Pair(spl[0],spl[1]));
                spl[2]+=(" "+spl[3]);
                HistoryTime.add(spl[2]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static double getEXP() {
        double t=EXP;
        double v=100+level*30;
        return t/v;
    }
    public static void updHistory(String s) {
        try {
            FileWriter fw=new FileWriter("History.txt",true);
            fw.write(s);
            fw.write('\n');
            fw.flush();
            String[] spl=s.split(" ");
            History.add(new Pair(spl[0],spl[1]));
            spl[2]+=(" "+spl[3]);
            HistoryTime.add(spl[2]);
        } catch (Exception e) {

        }
    }

    public static ArrayList<String> getHistory() {
        UserHistory.clear();
        for (int i=0;i<History.size();i++) {
            if (History.get(i).getKey().equals(Login.getUser())) UserHistory.add(HistoryTime.get(i)+" : Won vs "+History.get(i).getValue());
            if (History.get(i).getValue().equals(Login.getUser())) UserHistory.add(HistoryTime.get(i)+" : Lost vs "+History.get(i).getKey());
        }
        return UserHistory;
    }

    public static boolean CalculateLevel(int x) {
        EXP+=x;
        boolean could=false;
        while (true) {
            if (EXP-(100+level*30)>=0) {
                EXP-=(100+level*30);
                level++;
                could=true;
            } else break;
        }
        return could;
    }

    public static void getProperty(String user) {
        for (int i=0;i<Helper.size();i++) {
            if (Helper.get(i).split(" ")[0].equals(user)) {
                String[] tmp=Helper.get(i).split(" ");
                for (int j=0;j<8;j++) {
                    Character.addToDeck(tmp[3+j]);
                }
                EXP=Integer.parseInt(tmp[11]);
                tmp[12]=tmp[12].replace("\n","");
                level=Integer.parseInt(tmp[12]);
            }
        }
    }

    public static void update() throws Exception{
        try (FileWriter fw = new FileWriter("Data.txt", false)) {
            for (int i = 0; i < Helper.size(); i++) {
                if (Login.getUser().equals(dataUser.get(i))) {
                    try {
                        fw.write(dataUser.get(i) + " " + dataPass.get(i) + " " + dataName.get(i) + " ");
                        for (int j = 0; j < 8; j++) {
                            fw.write(Deck.get(j).name + " ");
                        }
                        fw.write(EXP +" "+ level + '\n');
                        fw.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else fw.write(Helper.get(i)+'\n');
            }
        }
    }
    public void add(Pane pane,String name) {
        pager.put(name,pane);
    }

    public static void setScene(String name) {
        toShow.setRoot(pager.get(name));
        if (name.equals("Deck")) DeckPick.LastDeck(Deck);
        if (name.equals("History")) HistoryShow.showUp();
    }

    public static boolean userCheck(String s) {
        return !dataUser.contains(s);
    }

    public static boolean loginData(String user,String pass) {
        if (dataUser.contains(user)) {
            int id=dataUser.indexOf(user);
            return dataPass.get(id).equals(pass);
        }
        return false;
    }

    public static void addUser(String user,String pass,String name) {
        try {
            FileWriter fw=new FileWriter("Data.txt",true);
            String s;
            s = user + " " + pass + " " + name + " " + "null ".repeat(8);
            s+="0 1\n";
            fw.write(s);
            Helper.add(s);
            fw.flush();
            dataUser.add(user);
            dataName.add(name);
            dataPass.add(pass);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getLevel() {
        return level;
    }

    public static void Decker(Character c) {
        Deck.add(c);
        if (Deck.size()==8 && c!=null) {
            try {
                update();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String getNameOf(String user) {
        return dataName.get(dataUser.indexOf(user));
    }
    public static void ClearDeck() {
        Deck.clear();
    }

    public static boolean isDecked() {
        return Deck.size() == 8 && Deck.get(7) != null;
    }

    public static ArrayList<Character> getDeck() {
        return Deck;
    }
}
