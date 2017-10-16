/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package provera.proveraTiketa;

import domen.Tiket;
import poslovnaLogika.so.OpstaSO;

/**
 *
 * @author KORISNIK
 */
public class PromeniStatusTiketa extends OpstaSO {

    @Override
    protected void proveriPreduslove(Object obj) throws Exception {

    }

    @Override
    protected void izvrsi(Object obj) throws Exception {
        Tiket tiket = (Tiket) obj;
        tiket.setParametar("promenaStatusa");
        dbbr.izmeni(tiket);
    }

}
