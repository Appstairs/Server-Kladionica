/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package provera.proveraUtakmica;

import domen.Tiket;
import domen.Zahtev;
import poslovnaLogika.so.OpstaSO;

/**
 *
 * @author KORISNIK
 */
public class ObradiZahtev extends OpstaSO {

    @Override
    protected void proveriPreduslove(Object obj) throws Exception {

    }

    @Override
    protected void izvrsi(Object obj) throws Exception {
        Zahtev z = (Zahtev) obj;

        dbbr.izmeni(z);
    }

}
