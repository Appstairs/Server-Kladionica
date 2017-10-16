/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poslovnaLogika.so;

import domen.Radnik;
import domen.StavkaTiketa;
import domen.Tiket;

/**
 *
 * @author KORISNIK
 */
public class SOSacuvajRadnika extends OpstaSO {

    @Override
    protected void proveriPreduslove(Object obj) throws Exception {
        //nema preduslova
    }

    @Override
    protected void izvrsi(Object obj) throws Exception {
        Radnik radnik = (Radnik) obj;
        dbbr.obrisi((Radnik) obj);
        dbbr.ubaci((Radnik) obj);

    }

}
