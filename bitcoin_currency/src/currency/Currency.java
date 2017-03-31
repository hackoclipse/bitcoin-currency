/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package currency;

import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import sun.applet.Main;

/**
 *
 * @author mh123hack
 */
public class Currency {

    /**
     * @param args the command line arguments
     */
    static Timer timer = new Timer();
    static SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
    static double amountbtc_bitstamp = 0.02591176; // amount BTC on bitstamp
    static double amountbtc_btc_e = 0; // amount BTC on btc-e
    static double amounteur_btce = 0; // amount euro's on btc-e
    static double amounteur_bitstamp = 10.01; // amount euro's on bitstamp
    static double amounteur_beginworth = 39.16; // amount before you started like you begun with 40 euro.
    static double minimal_btcworth = 1060; // amount before you think you have enough. if its on this amount make a beep.
    //static double gekost = 0 - (amounteur_btce + amounteur_bitstamp);

    public static void main(String[] args) throws MalformedURLException, IOException {
        // TODO code application logic here
        timer.schedule(new SayHello(), 0, 5500);

    }

    static class SayHello extends TimerTask {

        public void run() {
            try {
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                URL btceur = new URL("https://www.bitstamp.net/api/v2/ticker/btceur/");
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        btceur.openStream()));
                String[] a = in.readLine().split(",");
                double amount = Double.parseDouble(a[7].substring(7).replace("\"", "").trim()) * amountbtc_bitstamp;

                URL etheur = new URL("https://btc-e.com/api/3/ticker/btc_eur");
                BufferedReader in2 = new BufferedReader(new InputStreamReader(
                        etheur.openStream()));
                String[] b = in2.readLine().split(",");
                double amount2 = Double.parseDouble(b[7].substring(7).replace("\"", "").trim()) * amountbtc_btc_e;

                double winst = amount + amount2 + amounteur_bitstamp + amounteur_btce - amounteur_beginworth;
                double winstprocent = (amount + amount2 + amounteur_bitstamp + amounteur_btce) / amounteur_beginworth * 100 - 100;
//                if (winst > 0) {
//                    try {
//                        Clip clip = AudioSystem.getClip();
//                        AudioInputStream inputStream = AudioSystem.getAudioInputStream(
//                                Main.class.getResourceAsStream("/path/to/sounds/" + url));
//                        clip.open(inputStream);
//                        clip.start();
//                    } catch (Exception e) {
//                        System.err.println(e.getMessage());
//                    }
//                }
                double btcworth = Double.parseDouble(a[7].substring(7).replace("\"", "").trim());
                if (btcworth > minimal_btcworth){
                    Toolkit.getDefaultToolkit().beep();
                }
                System.out.println("profit: " + winst);
                System.out.println("profit in %: %" + winstprocent);
                System.out.println("btc bitstamp worth: " + a[7].substring(7).replace("\"", "").trim());
                System.out.println("btc btc-e worth: " + b[7].substring(7).replace("\"", "").trim());
                System.out.println("time on the momend: " + sdf.format(cal.getTime()));
                System.out.println("___________________");
            } catch (Exception e) {
                System.err.println("faild to connect will retry in 5.5 sec");
            }
        }
    }
}
