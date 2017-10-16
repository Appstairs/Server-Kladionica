/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package provera.proveraTiketa;

import domen.OpstiDomenskiObjekat;
import domen.Tiket;
import java.util.List;
import poslovnaLogika.so.OpstaSO;

/**
 *
 * @author KORISNIK
 */
public class VratiSveTikete extends OpstaSO {

    private List<OpstiDomenskiObjekat> tiketi;

    @Override
    protected void proveriPreduslove(Object obj) throws Exception {
        //nema preduslova
    }

    @Override
    protected void izvrsi(Object obj) throws Exception {

        tiketi = dbbr.vratiSve((Tiket) obj);

    }

    public List<OpstiDomenskiObjekat> getTiketi() {
        return tiketi;
    }

    public void setTiketi(List<OpstiDomenskiObjekat> tiketi) {
        this.tiketi = tiketi;
    }

}
