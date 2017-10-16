/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package provera.proveraUtakmica;

import domen.OpstiDomenskiObjekat;
import domen.Utakmica;
import domen.Zahtev;
import java.util.ArrayList;
import java.util.List;
import poslovnaLogika.so.OpstaSO;

/**
 *
 * @author KORISNIK
 */
public class KreirajNoviZahtev extends OpstaSO {

    @Override
    protected void proveriPreduslove(Object obj) throws Exception {

    }

    @Override
    protected void izvrsi(Object obj) throws Exception {
        Zahtev z = (Zahtev) obj;

        dbbr.ubaci(z);

    }

}
