package sample;

import com.sun.javafx.binding.StringFormatter;

public class TimeManager {
    public static boolean Cooler(double CoolDown) {
        while (true) {
            CoolDown-=100;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (CoolDown==0) return false;
        }
    }

    public static void SetTimer(String timer,int seconds) {
        while(true) {
            int min=seconds/60;
            int sec=seconds%60;
            timer="0"+min+":";
            if (sec<10) timer+="0";
            timer+=sec;
            System.out.println(timer);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            seconds--;
            if (seconds==-1) return;
        }
    }
}
