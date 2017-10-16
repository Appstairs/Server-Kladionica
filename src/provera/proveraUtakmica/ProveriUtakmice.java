/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package provera.proveraUtakmica;

import domen.Kvota;
import domen.OpstiDomenskiObjekat;
import domen.Tip;
import domen.Utakmica;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javniApi.ApiUtakmice;
import org.json.JSONArray;
import org.json.JSONObject;
import poslovnaLogika.KontrolerPL;

/**
 *
 * @author KORISNIK
 */
public class ProveriUtakmice {

    Logger logger = Logger.getLogger("MyLog");
    public static Timer timer;
    public static TimerTask task;

    public void proveriSvakih24h() {

        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 1);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 1);

        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {

                try {
                    Thread.sleep(30000);
                    System.out.println("usao u ovaj ran/tajmer za proveru");
                    Calendar cal1 = Calendar.getInstance();
                    Calendar cal2 = Calendar.getInstance();
                    cal1.setTime(new Date());
                    boolean imaUtakmica = false;
                    List<OpstiDomenskiObjekat> lo = KontrolerPL.vratiInstancu().vratiSveUtakmice();
                    System.out.println("lo size: " + lo.size());
                    List<Utakmica> listaUtakmica = new ArrayList<>();
                    for (int i = 0; i < lo.size(); i++) {
                        listaUtakmica.add((Utakmica) lo.get(i));
                    }
                    for (int i = 0; i < listaUtakmica.size(); i++) {
                        cal2.setTime(listaUtakmica.get(i).getPocetak());
                        if (cal1.get(cal1.DAY_OF_MONTH) == cal2.get(cal2.DAY_OF_MONTH) && cal1.get(cal1.MONTH) == cal2.get(cal2.MONTH) && cal1.get(cal1.YEAR) == cal2.get(cal2.YEAR)) {
                            imaUtakmica = true;
                        }
                    }
                    if (imaUtakmica == false || ApiUtakmice.daLiJeObradjenZaDanas() == false) {
                        System.out.println("uso u if za ponovo");
                        unesiPonovo();
                        ApiUtakmice.obradiZahtev();
                    }

                } catch (Exception ex) {
                    Logger.getLogger(ProveriUtakmice.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        };
        timer.schedule(task, today.getTime(), 86400000L);
        //svaki dan u 00:01:01

    }

    private Utakmica postaviUtakmicu(Utakmica utakmica, String domacin, String gost, int goloviDomacin, int goloviGost, Date datum) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(datum);
        cal.add(Calendar.HOUR_OF_DAY, 2); // adds one hour
        datum = cal.getTime();
        utakmica.setDomacin(domacin);
        utakmica.setGost(gost);
        utakmica.setGoloviDomacin(goloviDomacin);
        utakmica.setGoloviGost(goloviGost);
        utakmica.setPocetak(datum);
        return utakmica;
    }

    private void unesiPonovo() {
        System.out.println("ponovo unosi");
        int idUtakmice = 1;
        String domacin = null;
        String gost = null;
        String stringDatum = null;

        int goloviDomacin = -1;
        int goloviGost = -1;

        try {
            JSONObject json = new JSONObject(uzmiHtml("http://localhost:8080/WebServiceUtakmice/webresources/webserviceutakmice"));

            JSONArray fixtures = json.getJSONArray("fixtures");
            for (int i = 0; i < json.getInt("count"); i++) {

                Utakmica utakmica = new Utakmica(new ArrayList<>());
                utakmica = (Utakmica) (KontrolerPL.vratiInstancu().kreirajNovuUtakmicu(utakmica)).get(0);

                JSONObject jsonFixtures = fixtures.getJSONObject(i);

                stringDatum = jsonFixtures.getString("date");
                stringDatum = stringDatum.replace('T', ' ');
                stringDatum = stringDatum.replace('Z', ' ');
                stringDatum = stringDatum.trim();

                Date datum = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(stringDatum);

                domacin = jsonFixtures.getString("homeTeamName");
                gost = jsonFixtures.getString("awayTeamName");

                utakmica = postaviUtakmicu(utakmica, domacin, gost, goloviDomacin, goloviGost, datum);

                double brojKvote1 = 0.0;
                double brojKvote2 = 0.0;
                double brojKvote3 = 0.0;
                double brojKvote4 = 0.0;
                double brojKvote5 = 0.0;

                Tip tip1 = new Tip(1, "1");
                Tip tip2 = new Tip(2, "x");
                Tip tip3 = new Tip(3, "2");
                Tip tip4 = new Tip(4, "0-2");
                Tip tip5 = new Tip(5, "3+");

                Kvota kvota1 = new Kvota(utakmica, brojKvote1, tip1);
                Kvota kvota2 = new Kvota(utakmica, brojKvote2, tip2);
                Kvota kvota3 = new Kvota(utakmica, brojKvote3, tip3);
                Kvota kvota4 = new Kvota(utakmica, brojKvote4, tip4);
                Kvota kvota5 = new Kvota(utakmica, brojKvote5, tip5);

                utakmica.getListaKvota().add(kvota1);
                utakmica.getListaKvota().add(kvota2);
                utakmica.getListaKvota().add(kvota3);
                utakmica.getListaKvota().add(kvota4);
                utakmica.getListaKvota().add(kvota5);

                KontrolerPL.vratiInstancu().zapamtiUtakmicu(utakmica);
                logger.info("uspesno unete utakmice nakon provere");
            }

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "greska prilikom unosenja utakmica preko API-ja nakon provere");
        }
    }

    private String uzmiHtml(String urlToRead) throws Exception {
        System.out.println("provera.proveraUtakmica.ProveriUtakmice.uzmiHtml()");
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        System.out.println(result.toString());
        return result.toString();
    }
}
