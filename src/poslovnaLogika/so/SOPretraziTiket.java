/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poslovnaLogika.so;

import domen.OpstiDomenskiObjekat;
import domen.Tiket;
import java.util.List;

/**
 *
 * @author KORISNIK
 */
public class SOPretraziTiket extends OpstaSO{
   
   List<OpstiDomenskiObjekat> tiketi;
    
    @Override
    protected void proveriPreduslove(Object obj) throws Exception {
        //nema preduslova
    }

    @Override
    protected void izvrsi(Object obj) throws Exception {
      Tiket tiket = (Tiket)obj;
      tiket.setParametar("pretrazi");
     tiketi=dbbr.vratiSveSaUslovom((Tiket)obj);
       
    }
    
    public List<OpstiDomenskiObjekat> getTiketi(){
        return tiketi;
    }
     public void setTiketi(List<OpstiDomenskiObjekat> tiketi) {
        this.tiketi=tiketi;
    }
    
}
