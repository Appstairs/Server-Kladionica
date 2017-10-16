/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poslovnaLogika.so;

import domen.Tiket;

/**
 *
 * @author KORISNIK
 */
public class SOStornirajTiket extends OpstaSO{



    
    
    @Override
    protected void proveriPreduslove(Object obj) throws Exception {

    }

    @Override
    protected void izvrsi(Object obj) throws Exception {
        Tiket tiket=(Tiket)obj;
        tiket.setParametar("storniranje");
        
        dbbr.izmeni(tiket);
    }
    
}

