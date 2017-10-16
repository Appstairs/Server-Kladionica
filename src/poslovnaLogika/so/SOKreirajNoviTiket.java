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
public class SOKreirajNoviTiket extends OpstaSO{
  List<OpstiDomenskiObjekat> tiketi; 
    List<OpstiDomenskiObjekat> tiketii=new ArrayList<>();
    @Override
    protected void proveriPreduslove(Object obj) throws Exception {
        //nema preduslova
    }

    @Override
    protected void izvrsi(Object obj) throws Exception {
        Tiket tiket=(Tiket)obj;
        tiket.setParametar("kreirajNovi");
        dbbr.ubaci((Tiket)obj);
        //ovaj uslov da mi order by po idu//i to mi je valjda na prvom taj novi
        
   //  tiketi=dbbr.vratiSveSaUslovom((Tiket)obj,tiket);
        tiketi=dbbr.vratiSveSaUslovom(tiket);
       tiketii.add(tiketi.get(0));
        System.out.println(((Tiket)tiketii.get(0)).getIdTiketa());
    }
    
    public List<OpstiDomenskiObjekat> getTiketi(){
        return tiketii;
    }
     public void setTiketi(List<OpstiDomenskiObjekat> tiketii) {
        this.tiketii=tiketii;
    }
    
}

