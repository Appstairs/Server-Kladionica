/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package niti;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author KORISNIK
 */
public class NitLogger extends Thread {

    public static final Logger logger = Logger.getLogger("MyLog");
    public static FileHandler fh;
    Calendar cal = Calendar.getInstance();
    int prethodniDan = 0;

    @Override
    public void run() {
        try {

            while (!isInterrupted()) {
                Date danasnjiDatum = new Date();
                cal.setTime(danasnjiDatum);

                int mesec = (cal.get(Calendar.MONTH)) + 1;
                int danUMesecu = cal.get(Calendar.DAY_OF_MONTH);

                if (prethodniDan != danUMesecu) {
                    postaviLogger(mesec, danUMesecu);
                    prethodniDan = danUMesecu;
                }
            }
        } catch (IOException ex) {

            ex.printStackTrace();

        }

    }

    private void postaviLogger(int mesec, int danUMesecu) throws IOException {

        fh = new FileHandler("logger/LogFile-" + danUMesecu + "-" + mesec + ".log", true);

        logger.setLevel(Level.INFO);
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);
    }

}
