/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poslovnaLogika.so;

import domen.OpstiDomenskiObjekat;
import domen.Tip;
import domen.Utakmica;
import java.util.List;

/**
 *
 * @author KORISNIK
 */
public class SOVratiSveUtakmice extends OpstaSO{
    private List<OpstiDomenskiObjekat> utakmice;
    
    @Override
    protected void proveriPreduslove(Object obj) throws Exception {
        //nema preduslova
    }

    @Override
    protected void izvrsi(Object obj) throws Exception {
        Utakmica u = (Utakmica)obj;
        u.setParametar("vratiSve");
      utakmice=dbbr.vratiSve(u);
    }
    
    public List<OpstiDomenskiObjekat> getUtakmice(){
        return utakmice;
    }
     public void setUtakmice(List<OpstiDomenskiObjekat> utakmice) {
        this.utakmice=utakmice;
    }
    
}

