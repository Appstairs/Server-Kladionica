/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poslovnaLogika.so;

import domen.OpstiDomenskiObjekat;
import domen.Radnik;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author KORISNIK
 */
public class SOKreirajNovogRadnika extends OpstaSO{
  List<OpstiDomenskiObjekat> radnici; 
    List<OpstiDomenskiObjekat> radnicii=new ArrayList<>();
    @Override
    protected void proveriPreduslove(Object obj) throws Exception {
        //nema preduslova
    }

    @Override
    protected void izvrsi(Object obj) throws Exception {
        Radnik radnik=(Radnik)obj;
        radnik.setParametar("kreirajNovog");
        dbbr.ubaci((Radnik)obj);
        //ovaj uslov da mi order by po idu//i to mi je valjda na prvom taj novi
        
   //  radnici=dbbr.vratiSveSaUslovom((Tiket)obj,tiket);
        radnici=dbbr.vratiSveSaUslovom(radnik);
       radnicii.add(radnici.get(0));
        System.out.println(((Radnik)radnicii.get(0)).getIdRadnika());
    }
    
    public List<OpstiDomenskiObjekat> getRadnici(){
        return radnicii;
    }
    
    
}
