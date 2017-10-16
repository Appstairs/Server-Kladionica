/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package provera.proveraUtakmica;

import domen.OpstiDomenskiObjekat;
import domen.Tiket;
import domen.Zahtev;
import java.util.List;
import poslovnaLogika.so.OpstaSO;

/**
 *
 * @author KORISNIK
 */
public class VratiSveZahteve extends OpstaSO {

    private List<OpstiDomenskiObjekat> zahtevi;

    @Override
    protected void proveriPreduslove(Object obj) throws Exception {
        //nema preduslova
    }

    @Override
    protected void izvrsi(Object obj) throws Exception {

        zahtevi = dbbr.vratiSve((Zahtev) obj);

    }

    public List<OpstiDomenskiObjekat> getZahtevi() {
        return zahtevi;
    }

}
