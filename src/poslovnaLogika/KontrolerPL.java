/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poslovnaLogika;

import db.DatabaseBroker;
import domen.Kvota;
import domen.OpstiDomenskiObjekat;
import domen.Radnik;
import domen.StavkaTiketa;
import domen.Tiket;
import domen.Tip;
import domen.UplatnoMesto;
import domen.Utakmica;
import domen.Zahtev;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import poslovnaLogika.so.OpstaSO;
import poslovnaLogika.so.SOIsplatiTiket;
import poslovnaLogika.so.SOIzmeniUtakmicu;
import poslovnaLogika.so.SOKreirajNoviTiket;
import poslovnaLogika.so.SOKreirajNovogRadnika;
import poslovnaLogika.so.SOKreirajNovuUtakmicu;
import poslovnaLogika.so.SOObradiTiket;
import poslovnaLogika.so.SOPretraziTiket;
import poslovnaLogika.so.SOPronadjiRadnika;
import poslovnaLogika.so.SOPronadjiTikete;
import poslovnaLogika.so.SOPronadjiUtakmice;
import poslovnaLogika.so.SOSacuvajRadnika;
import poslovnaLogika.so.SOStornirajTiket;
import poslovnaLogika.so.SOVratiSvaUplatnaMesta;
import poslovnaLogika.so.SOVratiSveRadnike;
import poslovnaLogika.so.SOVratiSveTipove;
import poslovnaLogika.so.SOVratiSveUtakmice;
import poslovnaLogika.so.SOZapamtiRezultat;
import poslovnaLogika.so.SOZapamtiTiket;
import poslovnaLogika.so.SOZapamtiUtakmicu;
import provera.proveraTiketa.PromeniStatusTiketa;
import provera.proveraTiketa.VratiSveTikete;
import provera.proveraUtakmica.KreirajNoviZahtev;
import provera.proveraUtakmica.ObradiZahtev;
import provera.proveraUtakmica.VratiSveZahteve;

/**
 *
 * @author student
 */
public class KontrolerPL {

    DatabaseBroker dbbr;

    private static KontrolerPL instanca;

    public static KontrolerPL vratiInstancu() {

        if (instanca == null) {
            instanca = new KontrolerPL();
        }

        return instanca;
    }

    private KontrolerPL() {

        dbbr = new DatabaseBroker();

    }

    public List<OpstiDomenskiObjekat> vratiSveTipove() throws Exception {
        SOVratiSveTipove so = new SOVratiSveTipove();
        so.opsteIzvrsenje(new Tip());
        return so.getTipovi();
    }

    public List<OpstiDomenskiObjekat> vratiSvaUplatnaMesta() throws Exception {
        SOVratiSvaUplatnaMesta so = new SOVratiSvaUplatnaMesta();
        so.opsteIzvrsenje(new UplatnoMesto());
        return so.getUplatnaMesta();
    }

    public List<OpstiDomenskiObjekat> vratiSveUtakmice() throws Exception {
        SOVratiSveUtakmice so = new SOVratiSveUtakmice();
        so.opsteIzvrsenje(new Utakmica());
        return so.getUtakmice();
    }

    public List<OpstiDomenskiObjekat> vratiSveRadnike() throws Exception {
        SOVratiSveRadnike so = new SOVratiSveRadnike();
        so.opsteIzvrsenje(new Radnik());
        return so.getRadnici();
    }

    public List<OpstiDomenskiObjekat> vratiSveTiketee() throws Exception {
        VratiSveTikete vst = new VratiSveTikete();
        vst.opsteIzvrsenje(new Tiket());
        return vst.getTiketi();
    }

    public List<OpstiDomenskiObjekat> vratiSveZahteve() throws Exception {
        VratiSveZahteve vsz = new VratiSveZahteve();
        vsz.opsteIzvrsenje(new Zahtev());
        return vsz.getZahtevi();
    }

    public List<OpstiDomenskiObjekat> pretraziTiket(Tiket t) throws Exception {
        SOPretraziTiket so = new SOPretraziTiket();
        so.opsteIzvrsenje(t);
        return so.getTiketi();
    }

    public List<OpstiDomenskiObjekat> kreirajNovuUtakmicu(Utakmica u) throws Exception {
        SOKreirajNovuUtakmicu so = new SOKreirajNovuUtakmicu();
        so.opsteIzvrsenje(u);
        return so.getUtakmice();
    }

    public void kreirajNoviZahtev() throws Exception {
        KreirajNoviZahtev so = new KreirajNoviZahtev();
        so.opsteIzvrsenje(new Zahtev());

    }

    public List<OpstiDomenskiObjekat> kreirajNoviTiket(Tiket t) throws Exception {
        SOKreirajNoviTiket so = new SOKreirajNoviTiket();
        so.opsteIzvrsenje(t);
        return so.getTiketi();
    }

    public List<OpstiDomenskiObjekat> kreirajNovogRadnika(Radnik r) throws Exception {
        SOKreirajNovogRadnika so = new SOKreirajNovogRadnika();
        so.opsteIzvrsenje(r);
        return so.getRadnici();
    }

    public void zapamtiTiket(Tiket tiket) throws Exception {

        SOZapamtiTiket so = new SOZapamtiTiket();
        so.opsteIzvrsenje(tiket);
    }

    public void sacuvajRadnika(Radnik radnik) throws Exception {

        SOSacuvajRadnika so = new SOSacuvajRadnika();
        so.opsteIzvrsenje(radnik);
    }

    public void zapamtiUtakmicu(Utakmica utakmica) throws Exception {

        SOZapamtiUtakmicu so = new SOZapamtiUtakmicu();

        so.opsteIzvrsenje(utakmica);
    }

    public void obradiTiket(Tiket tiket) throws Exception {

        SOObradiTiket so = new SOObradiTiket();
        so.opsteIzvrsenje(tiket);

    }

    public void stornirajTiket(Tiket tiket) throws Exception {
        SOStornirajTiket so = new SOStornirajTiket();
        so.opsteIzvrsenje(tiket);
    }

    public void isplatiTiket(Tiket tiket) throws Exception {
        SOIsplatiTiket so = new SOIsplatiTiket();
        so.opsteIzvrsenje(tiket);
    }

    

    public void promeniStatusTiketaa(Tiket tiket) throws Exception {
        PromeniStatusTiketa pst = new PromeniStatusTiketa();
        pst.opsteIzvrsenje(tiket);
    }

    public void obradiZahtev(Zahtev z) throws Exception {
        ObradiZahtev oz = new ObradiZahtev();
        oz.opsteIzvrsenje(z);
    }

    public void izmeniUtakmicu(Utakmica utakmica) throws Exception {
        SOIzmeniUtakmicu so = new SOIzmeniUtakmicu();
        so.opsteIzvrsenje(utakmica);

    }

    public void zapamtiRezultat(Utakmica utakmica) throws Exception {
        SOZapamtiRezultat so = new SOZapamtiRezultat();
        so.opsteIzvrsenje(utakmica);
    }

    public List<OpstiDomenskiObjekat> pronadjiRadnika(Radnik r) throws Exception {
        SOPronadjiRadnika so = new SOPronadjiRadnika();
        so.opsteIzvrsenje(r);
        return so.getRadnik();
    }

    public List<OpstiDomenskiObjekat> vratiUtakmice(Date prviDatum, Date drugiDatum, Utakmica u) throws Exception {
        SOPronadjiUtakmice so = new SOPronadjiUtakmice(prviDatum, drugiDatum);
        if (u == null) {
            so.opsteIzvrsenje(new Utakmica());
        } else {
            so.opsteIzvrsenje(u);
        }
        return so.getUtakmice();
    }

    public List<OpstiDomenskiObjekat> vratiTikete(Date prviDatum, Date drugiDatum) throws Exception {
        SOPronadjiTikete so = new SOPronadjiTikete(prviDatum, drugiDatum);
        so.opsteIzvrsenje(new Tiket());
        return so.getTiketi();
    }

}
