/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package niti;

import domen.OpstiDomenskiObjekat;
import domen.Tiket;
import forme.JPanelServer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import poslovnaLogika.KontrolerPL;

public class NitMail extends Thread {

    Date prviDatum = null;
    Date drugiDatum = null;
    Calendar cal1 = Calendar.getInstance();
    Calendar cal2 = Calendar.getInstance();
    List<OpstiDomenskiObjekat> lo;
    List<Tiket> listaTiketa = new ArrayList<>();
    double ukupnaUplata = 0;
    double ukupnaIsplata = 0;
    double stanje = 0;

    @Override
    public void run() {

        while (!isInterrupted()) {
            try {

                drugiDatum = new Date();
                cal2.setTime(drugiDatum);
                cal1=cal2;
                cal1.add(Calendar.MONTH, -1);
                prviDatum=cal1.getTime();
                if (cal2.get(Calendar.DAY_OF_MONTH)==1) {

                    if (prviDatum != null) {

                        //pozovi da vrati od prvog do drugog datuma
                        lo = KontrolerPL.vratiInstancu().vratiTikete(prviDatum, drugiDatum);
                        for (int i = 0; i < lo.size(); i++) {
                            listaTiketa.add((Tiket) lo.get(0));
                        }

                        for (Tiket tiket : listaTiketa) {
                            if (tiket.getVremeUplate() != null) {
                                ukupnaUplata += tiket.getUplata();
                                if (tiket.getStatus().equals("isplacen")) {
                                    ukupnaIsplata += tiket.getDobitak();
                                }

                            }
                        }
                        stanje = ukupnaUplata - ukupnaIsplata;

                        posaljiMail(stanje);

                    }
                   
                    //sleep na 25h, da prebaci da ne ulazi non stop da salje mail kad je prvi
                    sleep(90000000);
                }

            } catch (Exception e) {
                JPanelServer.nm.interrupt();
            }
        }

    }

    public void posaljiMail(double stanje) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("maki.dimitrijevic007@gmail.com", "maki2212994710081");
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("maki.dimitrijevic007@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("maki.dimitrijevic007@gmail.com"));
            message.setSubject("Stanje kladionice");
            message.setText("Postovani direktore,"
                    + "\n\n stanje za prethodni mesec je: " + stanje);

            Transport.send(message);

            System.out.println("Poslat mail");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

}
