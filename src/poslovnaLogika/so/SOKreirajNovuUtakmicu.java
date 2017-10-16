/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poslovnaLogika.so;

import domen.OpstiDomenskiObjekat;
import domen.Tiket;
import domen.Utakmica;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author KORISNIK
 */
public class SOKreirajNovuUtakmicu extends OpstaSO{
  List<OpstiDomenskiObjekat> utakmice; 
    List<OpstiDomenskiObjekat> utakmicee=new ArrayList<>();
    @Override
    protected void proveriPreduslove(Object obj) throws Exception {
        //nema preduslova
    }

    @Override
    protected void izvrsi(Object obj) throws Exception {
          Utakmica utakmica=(Utakmica)obj;
           utakmica.setParametar("kreirajNovi");
        dbbr.ubaci((Utakmica)obj);
        //ovaj uslov da mi order by po idu//i to mi je valjda na prvom taj novi
       
     utakmice=dbbr.vratiSveSaUslovom(utakmica);
       utakmicee.add(utakmice.get(0));
        System.out.println(((Utakmica)utakmicee.get(0)).getIdUtakmice());
    }
    
    public List<OpstiDomenskiObjekat> getUtakmice(){
        return utakmicee;
    }
     public void setUtakmice(List<OpstiDomenskiObjekat> utakmicee) {
        this.utakmicee=utakmicee;
    }
    
}

