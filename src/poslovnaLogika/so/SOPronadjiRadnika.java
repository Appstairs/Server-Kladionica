/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poslovnaLogika.so;

import domen.OpstiDomenskiObjekat;
import domen.Radnik;
import domen.Tiket;
import java.util.List;

/**
 *
 * @author KORISNIK
 */
public class SOPronadjiRadnika extends OpstaSO{
    private List<OpstiDomenskiObjekat> radnik;
   
    
    @Override
    protected void proveriPreduslove(Object obj) throws Exception {
        //nema preduslova
    }

    @Override
    protected void izvrsi(Object obj) throws Exception {
        Radnik r = (Radnik)obj;
   
    radnik= dbbr.vratiSveSaUslovom(r);
       
    }
    
     public List<OpstiDomenskiObjekat> getRadnik(){
        return radnik;
    }
    
   
    
}
