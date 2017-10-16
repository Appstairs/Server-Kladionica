/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poslovnaLogika.so;

import domen.OpstiDomenskiObjekat;
import domen.Radnik;
import domen.Tip;
import java.util.List;

/**
 *
 * @author KORISNIK
 */
public class SOVratiSveTipove  extends OpstaSO{
    private List<OpstiDomenskiObjekat> tipovi;
    
    @Override
    protected void proveriPreduslove(Object obj) throws Exception {
        //nema preduslova
    }

    @Override
    protected void izvrsi(Object obj) throws Exception {
      tipovi=dbbr.vratiSve((Tip) obj);
    }
    
    public List<OpstiDomenskiObjekat> getTipovi(){
        return tipovi;
    }
     public void setTipovi(List<OpstiDomenskiObjekat> tipovi) {
        this.tipovi=tipovi;
    }
    
}
