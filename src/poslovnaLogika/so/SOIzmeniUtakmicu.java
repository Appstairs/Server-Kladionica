/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poslovnaLogika.so;

import domen.Kvota;
import domen.OpstiDomenskiObjekat;
import domen.Utakmica;

import java.util.HashMap;



/**
 *
 * @author KORISNIK
 */
public class SOIzmeniUtakmicu extends OpstaSO{



    
    
    @Override
    protected void proveriPreduslove(Object obj) throws Exception {

    }

    @Override
    protected void izvrsi(Object obj) throws Exception {
      Utakmica utakmica=(Utakmica)obj;
       
        utakmica.setParametar("izmenaUtakmice");
        dbbr.izmeni(utakmica);
         
                for (Kvota kv :utakmica.getListaKvota()) {
                   
                    dbbr.izmeni(kv);
                }
    }
    
}

