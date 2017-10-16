/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poslovnaLogika.so;

import domen.OpstiDomenskiObjekat;
import domen.UplatnoMesto;
import java.util.List;

/**
 *
 * @author KORISNIK
 */
public class SOVratiSvaUplatnaMesta extends OpstaSO{
    private List<OpstiDomenskiObjekat> uplatnaMesta;
    
    @Override
    protected void proveriPreduslove(Object obj) throws Exception {
        //nema preduslova
    }

    @Override
    protected void izvrsi(Object obj) throws Exception {
      uplatnaMesta=dbbr.vratiSve((UplatnoMesto) obj);
    }
    
    public List<OpstiDomenskiObjekat> getUplatnaMesta(){
        return uplatnaMesta;
    }
   
    
}
