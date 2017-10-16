/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poslovnaLogika.so;

import domen.OpstiDomenskiObjekat;
import domen.StavkaTiketa;
import domen.Tiket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author KORISNIK
 */
public class SOZapamtiTiket extends OpstaSO{
  
    @Override
    protected void proveriPreduslove(Object obj) throws Exception {
        //nema preduslova
    }

    @Override
    protected void izvrsi(Object obj) throws Exception {
        Tiket tiket=(Tiket)obj;
        dbbr.obrisi((Tiket)obj);
        dbbr.ubaci((Tiket)obj);
        
        for (StavkaTiketa stavkaTiketa : tiket.getStavkeTiketa()) {
            dbbr.ubaci(stavkaTiketa);
        }
       
    }
    
   
    
}

