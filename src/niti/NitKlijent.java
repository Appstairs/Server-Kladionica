/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package niti;

import domen.Kvota;
import domen.OpstiDomenskiObjekat;
import domen.Radnik;
import domen.Tiket;
import domen.Tip;
import domen.Utakmica;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import komponente.tabela.model.ModelTabeleKlijenti;
import poslovnaLogika.KontrolerPL;
import provera.proveraTiketa.ProveraTiketa;
import transfer.ServerTransferObjekat;
import transfer.KlijentTransferObjekat;

/**
 *
 * @author student
 */
public class NitKlijent extends Thread {

    private ModelTabeleKlijenti model;
    private Socket vezaSaKlijentom;
    private ObjectInputStream inOdKlijenta;
    private ObjectOutputStream outKaKlijentu;

    private String korisnickoIme = "";
    private int brojZahteva = 0;

    Logger logger = Logger.getLogger("MyLog");

    public NitKlijent(Socket socket, ModelTabeleKlijenti model) throws IOException {
        this.vezaSaKlijentom = socket;
        this.model = model;

    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public Socket getVezaSaKlijentom() {
        return vezaSaKlijentom;
    }

    public void setVezaSaKlijentom(Socket vezaSaKlijentom) {
        this.vezaSaKlijentom = vezaSaKlijentom;
    }

    public int getBrojZahteva() {
        return brojZahteva;
    }

    public void setBrojZahteva(int brojZahteva) {
        this.brojZahteva = brojZahteva;
    }

    @Override
    public void run() {
        try {
            inOdKlijenta = new ObjectInputStream(vezaSaKlijentom.getInputStream());
            outKaKlijentu = new ObjectOutputStream(vezaSaKlijentom.getOutputStream());

            while (true) {
                KlijentTransferObjekat zahtevKlijenta = (KlijentTransferObjekat) inOdKlijenta.readObject(); // blokira programsku nit dok god klijent ne posalje objekat
                obradiZahtevKlijenta(zahtevKlijenta);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void obradiZahtevKlijenta(KlijentTransferObjekat zahtevKlijenta) throws IOException, Exception {
        brojZahteva++;

        ServerTransferObjekat odgovorServera = new ServerTransferObjekat();

        switch (zahtevKlijenta.getOperacija()) {

            case KlijentTransferObjekat.VRATI_SVE_TIPOVE: {
                try {
                    List<OpstiDomenskiObjekat> lt = KontrolerPL.vratiInstancu().vratiSveTipove();
                    odgovorServera.setObjekatIzvrsenjaOperacije(lt);
                    odgovorServera.setSignal(1);
                    logger.info(korisnickoIme + " trazio sve tipove");

                } catch (Exception ex) {
                    ex.printStackTrace();
                    odgovorServera.setIzuzetak(ex);
                    odgovorServera.setPoruka("Sistem ne moze da ucita listu tipova");
                    odgovorServera.setSignal(-1);
                    logger.log(Level.SEVERE, "greska prilikom vracanja liste tipova");
                }
                break;

            }
            case KlijentTransferObjekat.VRATI_SVA_UPLATNA_MESTA: {
                try {
                    List<OpstiDomenskiObjekat> lt = KontrolerPL.vratiInstancu().vratiSvaUplatnaMesta();
                    odgovorServera.setObjekatIzvrsenjaOperacije(lt);
                    odgovorServera.setSignal(1);
                    logger.info(korisnickoIme + " trazio sva uplatna mesta");

                } catch (Exception ex) {
                    ex.printStackTrace();
                    odgovorServera.setIzuzetak(ex);
                    odgovorServera.setPoruka("Sistem ne moze da ucita listu uplatnih mesta");
                    odgovorServera.setSignal(-1);
                    logger.log(Level.SEVERE, "greska prilikom vracanja liste uplatnih mesta");
                }
                break;

            }

            case KlijentTransferObjekat.VRATI_SVE_UTAKMICE: {
                try {
                    List<OpstiDomenskiObjekat> lt = KontrolerPL.vratiInstancu().vratiSveUtakmice();
                    odgovorServera.setObjekatIzvrsenjaOperacije(lt);
                    odgovorServera.setSignal(1);
                    logger.info(korisnickoIme + " trazio sve utakmice");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    odgovorServera.setIzuzetak(ex);
                    odgovorServera.setPoruka("Sistem ne moze da ucita listu utakmica");
                    odgovorServera.setSignal(-1);
                    logger.log(Level.SEVERE, "greska prilikom vracanja liste utakmica");

                }
                break;

            }
            case KlijentTransferObjekat.VRATI_SVE_RADNIKE: {
                try {
                    List<OpstiDomenskiObjekat> lt = KontrolerPL.vratiInstancu().vratiSveRadnike();
                    odgovorServera.setObjekatIzvrsenjaOperacije(lt);
                    odgovorServera.setSignal(1);
                    logger.info(korisnickoIme + " trazio sve radnike");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    odgovorServera.setIzuzetak(ex);
                    odgovorServera.setPoruka("Sistem ne moze da ucita listu radnika");
                    odgovorServera.setSignal(-1);
                    logger.log(Level.SEVERE, "greska prilikom vracanja liste radnika");

                }
                break;

            }

            case KlijentTransferObjekat.PRETRAZI_TIKET: {
                try {
                    Tiket t = (Tiket) zahtevKlijenta.getObjekatOperacije();
                    List<OpstiDomenskiObjekat> lo = KontrolerPL.vratiInstancu().pretraziTiket(t);
                    if (lo.size() == 0) {
                        throw new Exception("Sistem ne moze da pronadje tiket po zadatoj vrednosti");
                    }
                    Tiket tik = (Tiket) lo.get(0);
                    odgovorServera.setObjekatIzvrsenjaOperacije(tik);
                    odgovorServera.setPoruka("Sistem je uspesno pronasao tiket");
                    odgovorServera.setSignal(1);
                    logger.info(korisnickoIme + " je pretrazio tiket: " + t.getIdTiketa());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    odgovorServera.setIzuzetak(ex);
                    odgovorServera.setSignal(-1);
                    logger.log(Level.SEVERE, "greska prilikom pretrazivanja tiketa");
                }
                break;
            }

            case KlijentTransferObjekat.KREIRAJ_NOVU_UTAKMICU: {
                try {
                    Utakmica u = (Utakmica) zahtevKlijenta.getObjekatOperacije();
                    List<OpstiDomenskiObjekat> lt = KontrolerPL.vratiInstancu().kreirajNovuUtakmicu(u);
                    odgovorServera.setObjekatIzvrsenjaOperacije(lt);
                    odgovorServera.setSignal(1);
                    logger.info(korisnickoIme + " je kreirao novu utakmicu");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    odgovorServera.setIzuzetak(ex);
                    odgovorServera.setPoruka("Sistem ne moze da kreira novu utakmicu");
                    odgovorServera.setSignal(-1);
                    logger.log(Level.SEVERE, "greska prilikom kreiranja nove utakmice");
                }
                break;

            }
            case KlijentTransferObjekat.KREIRAJ_NOVI_TIKET: {
                try {
                    Tiket t = (Tiket) zahtevKlijenta.getObjekatOperacije();
                    List<OpstiDomenskiObjekat> lt = KontrolerPL.vratiInstancu().kreirajNoviTiket(t);
                    Tiket tik = (Tiket) lt.get(0);
                    odgovorServera.setObjekatIzvrsenjaOperacije(tik);
                    odgovorServera.setSignal(1);
                    logger.info(korisnickoIme + " je kreirao novi tiket");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    odgovorServera.setIzuzetak(ex);
                    odgovorServera.setPoruka("Sistem ne moze da kreira novi tiket");
                    odgovorServera.setSignal(-1);
                    logger.log(Level.SEVERE, "greska prilikom kreiranja novog tiketa");
                }
                break;

            }

            case KlijentTransferObjekat.KREIRAJ_NOVOG_RADNIKA: {
                try {
                    Radnik r = (Radnik) zahtevKlijenta.getObjekatOperacije();
                    List<OpstiDomenskiObjekat> lt = KontrolerPL.vratiInstancu().kreirajNovogRadnika(r);
                    Radnik rad = (Radnik) lt.get(0);
                    odgovorServera.setObjekatIzvrsenjaOperacije(rad);
                    odgovorServera.setSignal(1);
                    logger.info(korisnickoIme + " je kreirao novog radnika");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    odgovorServera.setIzuzetak(ex);
                    odgovorServera.setPoruka("Sistem ne moze da kreira novog radnika");
                    odgovorServera.setSignal(-1);
                    logger.log(Level.SEVERE, "greska prilikom kreiranja novog radnika");
                }
                break;

            }

            case KlijentTransferObjekat.ZAPAMTI_TIKET_I_STAVKE_TIKETA: {
                try {
                    Tiket t = (Tiket) zahtevKlijenta.getObjekatOperacije();
                    KontrolerPL.vratiInstancu().zapamtiTiket(t);
                    odgovorServera.setPoruka("Sistem je uspesno zapamtio tiket");
                    odgovorServera.setSignal(1);
                    logger.info(korisnickoIme + " je zapamtio novi tiket");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    odgovorServera.setIzuzetak(ex);
                    odgovorServera.setPoruka("Sistem ne moze da zapamti tiket");
                    odgovorServera.setSignal(-1);
                    logger.log(Level.SEVERE, "greska prilikom pamcenja tiketa");
                }
                break;

            }

            case KlijentTransferObjekat.SACUVAJ_RADNIKA: {
                try {
                    Radnik r = (Radnik) zahtevKlijenta.getObjekatOperacije();
                    KontrolerPL.vratiInstancu().sacuvajRadnika(r);
                    odgovorServera.setPoruka("Sistem je uspesno sacuvao radnika");
                    odgovorServera.setSignal(1);
                    logger.info(korisnickoIme + " je sacuvao novog radnika: " + r.getKorisnickoIme());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    odgovorServera.setIzuzetak(ex);
                    odgovorServera.setPoruka("Sistem ne moze da sacuva radnika");
                    odgovorServera.setSignal(-1);
                    logger.log(Level.SEVERE, "greska prilikom cuvanja radnika");
                }
                break;

            }
            case KlijentTransferObjekat.ZAPAMTI_UTAKMICU_I_KVOTE: {
                try {
                    Utakmica u = (Utakmica) zahtevKlijenta.getObjekatOperacije();
                    KontrolerPL.vratiInstancu().zapamtiUtakmicu(u);
                    odgovorServera.setPoruka("Sistem je uspesno zapamtio novu utakmicu");
                    odgovorServera.setSignal(1);
                    logger.info(korisnickoIme + " je uneo novu utakmicu " + u.getDomacin() + " - " + u.getGost());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    odgovorServera.setIzuzetak(ex);
                    odgovorServera.setPoruka("Sistem ne moze da zapamti novu utakmicu");
                    odgovorServera.setSignal(-1);
                    logger.log(Level.SEVERE, "greska prilikom pamcenja utakmice");
                }
                break;

            }
            case KlijentTransferObjekat.OBRADI_TIKET: {
                try {
                    Tiket t = (Tiket) zahtevKlijenta.getObjekatOperacije();
                    KontrolerPL.vratiInstancu().obradiTiket(t);
                    odgovorServera.setPoruka("Sistem je obradio tiket");
                    odgovorServera.setSignal(1);
                    logger.info(korisnickoIme + " je obradio tiket:" + t.getIdTiketa());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    odgovorServera.setPoruka("Sistem ne moze da obradi tiket");
                    odgovorServera.setIzuzetak(ex);
                    odgovorServera.setSignal(-1);
                    logger.log(Level.SEVERE, "greska prilikom obrade tiketa");
                }
                break;

            }
            case KlijentTransferObjekat.STORNIRAJ_TIKET: {
                try {
                    Tiket t = (Tiket) zahtevKlijenta.getObjekatOperacije();
                    KontrolerPL.vratiInstancu().stornirajTiket(t);
                    odgovorServera.setSignal(1);
                    logger.info(korisnickoIme + " je stornirao tiket:" + t.getIdTiketa());
                } catch (Exception ex) {

                    ex.printStackTrace();
                    odgovorServera.setPoruka("Sistem ne moze da stornira tiket");
                    odgovorServera.setIzuzetak(ex);
                    odgovorServera.setSignal(-1);
                    logger.log(Level.SEVERE, "greska prilikom storniranja tiketa");
                }
                break;

            }
            case KlijentTransferObjekat.ISPLATI_TIKET: {
                try {
                    Tiket t = (Tiket) zahtevKlijenta.getObjekatOperacije();
                    KontrolerPL.vratiInstancu().isplatiTiket(t);
                    odgovorServera.setSignal(1);
                    logger.info(korisnickoIme + " je isplatio tiket:" + t.getIdTiketa());
                } catch (Exception ex) {

                    ex.printStackTrace();
                    odgovorServera.setPoruka("Sistem ne moze da isplati tiket");
                    odgovorServera.setIzuzetak(ex);
                    odgovorServera.setSignal(-1);
                    logger.log(Level.SEVERE, "greska prilikom isplate tiketa");
                }
                break;

            }

            case KlijentTransferObjekat.IZMENI_UTAKMICU_I_KVOTE: {
                try {
                    HashMap<String, Object> hmap = (HashMap) zahtevKlijenta.getObjekatOperacije();
                    Utakmica u = (Utakmica) hmap.get("utakmica");
                    List<Kvota> listaKv = (List<Kvota>) hmap.get("listaKvota");
                    System.out.println("U niti gost" + u.getGost());
                    System.out.println("U niti" + listaKv.size());
                    u.setListaKvota(listaKv);
                    KontrolerPL.vratiInstancu().izmeniUtakmicu(u);
                    odgovorServera.setPoruka("Sistem je uspesno zapamtio izmene o utakmici");
                    odgovorServera.setSignal(1);
                    logger.info(korisnickoIme + " je promenio utakmicu" + u.getDomacin() + " - " + u.getGost());
                } catch (Exception ex) {

                    ex.printStackTrace();
                    odgovorServera.setIzuzetak(ex);
                    odgovorServera.setPoruka("Sistem ne moze da zapamti podatke o utakmici");
                    odgovorServera.setSignal(-1);
                    logger.log(Level.SEVERE, "greska prilikom izmene utakmice");
                }
                break;

            }
            case KlijentTransferObjekat.ZAPAMTI_REZULTAT: {
                Utakmica utakmica = new Utakmica();
                try {
                    Utakmica u = (Utakmica) zahtevKlijenta.getObjekatOperacije();
                    utakmica = u;
                    KontrolerPL.vratiInstancu().zapamtiRezultat(u);
                    odgovorServera.setPoruka("Sistem je uspesno zapamtio rezultat utakmice");
                    odgovorServera.setSignal(1);
                    logger.info(korisnickoIme + " je promenio rezultat utakmice" + u.getDomacin() + " - " + u.getGost());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    odgovorServera.setIzuzetak(ex);
                    odgovorServera.setPoruka("Sistem ne moze da zapamti rezultat utakmice");
                    odgovorServera.setSignal(-1);
                    logger.log(Level.SEVERE, "greska prilikom unosa rezultata");
                }
                //promeni statuse tiketa
                List<OpstiDomenskiObjekat> lo = KontrolerPL.vratiInstancu().vratiSveTiketee();
                ProveraTiketa provera = new ProveraTiketa();
                provera.postaviStatusTiketa(lo, utakmica);

                break;

            }

            case KlijentTransferObjekat.PRIJAVA: {
                try {

                    Radnik r = (Radnik) zahtevKlijenta.getObjekatOperacije();
                    List<OpstiDomenskiObjekat> lt = KontrolerPL.vratiInstancu().pronadjiRadnika(r);
                    if (lt.size() == 0) {
                        throw new Exception("Sistem ne moze da nadje radnika na osnovu unetih vrednosti");
                    }
                    Radnik rad = (Radnik) lt.get(0);
                    if (lt.size() != 0) {

                        for (NitKlijent nk : repozitorijum.Repozitorijum.klijenti) {
                            if (nk.getKorisnickoIme().equals(rad.getKorisnickoIme())) {
                                throw new Exception("Radnik je vec ulogovan");
                            }
                        }
                        korisnickoIme = rad.getKorisnickoIme();
                        model.dodajKlijenta(this);

                        logger.info("Prijavio se: " + korisnickoIme);
                    }

                    odgovorServera.setObjekatIzvrsenjaOperacije(rad);
                    odgovorServera.setPoruka("Radnik je uspesno prijavljen");
                    odgovorServera.setSignal(1);
                } catch (Exception ex) {

                    ex.printStackTrace();
                    odgovorServera.setIzuzetak(ex);
                    odgovorServera.setSignal(-1);
                    logger.log(Level.SEVERE, "greska prilikom prijave radnika");
                }
                break;

            }
            case KlijentTransferObjekat.ODJAVA: {
                try {
                    Radnik r = (Radnik) zahtevKlijenta.getObjekatOperacije();

                    int rBr = -1;
                    interrupt();
                    for (int i = 0; i < repozitorijum.Repozitorijum.klijenti.size(); i++) {
                        if (repozitorijum.Repozitorijum.klijenti.get(i).getKorisnickoIme().equals(r.getKorisnickoIme())) {
                            repozitorijum.Repozitorijum.klijenti.get(i).getVezaSaKlijentom().close();
                            rBr = i;

                        }
                    }
                    if (rBr != -1) {
                        // brise i u listi klijenata
                        model.obrisiRed(rBr);
                    }
                    logger.info("odjavio se: " + korisnickoIme);
                    System.out.println("lista klijenata je: " + repozitorijum.Repozitorijum.klijenti.size());

                } catch (Exception ex) {
                    odgovorServera.setIzuzetak(ex);
                    odgovorServera.setSignal(-1);
                    logger.log(Level.SEVERE, "greska prilikom odjave radnika");
                }
                break;

            }

            case KlijentTransferObjekat.VRATI_UTAKMICE: {
                try {
                    Date prviDatum = null;
                    Date drugiDatum = null;
                    Utakmica u = null;
                    try {
                        HashMap<String, Date> hmap = (HashMap) zahtevKlijenta.getObjekatOperacije();
                        prviDatum = hmap.get("prviDatum");
                        drugiDatum = hmap.get("drugiDatum");
                    } catch (Exception e) {

                        u = (Utakmica) zahtevKlijenta.getObjekatOperacije();
                    }

                    List<OpstiDomenskiObjekat> lt = KontrolerPL.vratiInstancu().vratiUtakmice(prviDatum, drugiDatum, u);
                    System.out.println("u niti size liste je: " + lt.size());
                    odgovorServera.setObjekatIzvrsenjaOperacije(lt);
                    odgovorServera.setSignal(1);
                    logger.info(korisnickoIme + " zatrazio utakmice po zadatom kriterijumu");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    odgovorServera.setPoruka("Sitem ne moze da vrati utakmice po zadatom kriterijumu");
                    odgovorServera.setIzuzetak(ex);
                    odgovorServera.setSignal(-1);
                    logger.log(Level.SEVERE, "greska prilikom vracanja utakmica po kriterijumu");
                }
                break;

            }
            case KlijentTransferObjekat.VRATI_TIKETE: {
                try {

                    HashMap<String, Date> hmap = (HashMap) zahtevKlijenta.getObjekatOperacije();
                    Date prviDatum = hmap.get("prviDatum");
                    Date drugiDatum = hmap.get("drugiDatum");
                    List<OpstiDomenskiObjekat> lt = KontrolerPL.vratiInstancu().vratiTikete(prviDatum, drugiDatum);
                    System.out.println("u niti size liste je: " + lt.size());
                    odgovorServera.setObjekatIzvrsenjaOperacije(lt);
                    odgovorServera.setPoruka("Sistem je uspesno prikazao stanje");
                    odgovorServera.setSignal(1);

                    logger.info(korisnickoIme + " zatrazio tikete po zadatom kriterijumu");

                } catch (Exception ex) {
                    ex.printStackTrace();
                    odgovorServera.setPoruka("Sistem ne moze da prikaze stanje po zadatom kriterijumu");
                    odgovorServera.setIzuzetak(ex);
                    odgovorServera.setSignal(-1);
                    logger.log(Level.SEVERE, "greska prilikom vracanja tiketa po kriterijumu");
                }
                break;

            }

           

        }

        outKaKlijentu.writeObject(odgovorServera);

    }

}
