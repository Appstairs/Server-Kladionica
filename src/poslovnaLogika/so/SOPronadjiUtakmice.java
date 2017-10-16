/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poslovnaLogika.so;

import domen.OpstiDomenskiObjekat;
import domen.Utakmica;
import java.util.Date;
import java.util.List;

/**
 *
 * @author KORISNIK
 */
public class SOPronadjiUtakmice  extends OpstaSO{
    private List<OpstiDomenskiObjekat> utakmice;
   private Date prviDatum;
   private Date drugiDatum;
   
    public SOPronadjiUtakmice(Date prviDatum, Date drugiDatum) {
        this.prviDatum = prviDatum;
        this.drugiDatum = drugiDatum;
    }
    
    @Override
    protected void proveriPreduslove(Object obj) throws Exception {
        //nema preduslova
    }

    @Override
    protected void izvrsi(Object obj) throws Exception {
        Utakmica u = (Utakmica)obj;
        if(prviDatum==null){
         u.setParametar("vratiPoIDu");
        } else{
         u.setPrviDatum(prviDatum);
        u.setDrugiDatum(drugiDatum);
        u.setParametar("vratiPoDatumu");
        }
       
       
      
        
        
       
    utakmice= dbbr.vratiSveSaUslovom(u);
       
    }
    
     public List<OpstiDomenskiObjekat> getUtakmice(){
        return utakmice;
    }
    
   
    
}
