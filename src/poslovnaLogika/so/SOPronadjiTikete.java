/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poslovnaLogika.so;

import domen.OpstiDomenskiObjekat;
import domen.Tiket;
import java.util.Date;
import java.util.List;

/**
 *
 * @author KORISNIK
 */
public class SOPronadjiTikete extends OpstaSO{
    private List<OpstiDomenskiObjekat> tiketi;
   private Date prviDatum;
   private Date drugiDatum;

    public SOPronadjiTikete(Date prviDatum, Date drugiDatum) {
        this.prviDatum = prviDatum;
        this.drugiDatum = drugiDatum;
    }
    
    @Override
    protected void proveriPreduslove(Object obj) throws Exception {
        //nema preduslova
    }

    @Override
    protected void izvrsi(Object obj) throws Exception {
        Tiket t = (Tiket)obj;
        t.setPrviDatum(prviDatum);
        t.setDrugiDatum(drugiDatum);
        t.setParametar("vratiPoDatumu");
    tiketi= dbbr.vratiSveSaUslovom(t);
       
    }
    
     public List<OpstiDomenskiObjekat> getTiketi(){
        return tiketi;
    }
    
   
    
}
