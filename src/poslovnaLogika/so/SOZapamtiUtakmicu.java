/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poslovnaLogika.so;

import domen.Kvota;
import domen.Utakmica;

/**
 *
 * @author KORISNIK
 */
public class SOZapamtiUtakmicu  extends OpstaSO{
  
    @Override
    protected void proveriPreduslove(Object obj) throws Exception {
        //nema preduslova
    }

    @Override
    protected void izvrsi(Object obj) throws Exception {
        Utakmica utakmica =(Utakmica)obj;
        dbbr.obrisi((Utakmica)obj);
        dbbr.ubaci((Utakmica)obj);
        
        for (Kvota kv:utakmica.getListaKvota()) {
            dbbr.ubaci(kv);
        }
       
    }
    
   
    
}

