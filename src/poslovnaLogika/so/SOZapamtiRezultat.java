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
public class SOZapamtiRezultat extends OpstaSO{



    
    
    @Override
    protected void proveriPreduslove(Object obj) throws Exception {

    }

    @Override
    protected void izvrsi(Object obj) throws Exception {
      Utakmica utakmica=(Utakmica)obj;
      utakmica.setParametar("izmenaRezultata");
         dbbr.izmeni((Utakmica)obj);
                
    }
    
}

