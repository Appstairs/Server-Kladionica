/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package provera.proveraTiketa;

import domen.OpstiDomenskiObjekat;
import domen.StavkaTiketa;
import domen.Tiket;
import domen.Utakmica;
import java.util.ArrayList;
import java.util.List;
import poslovnaLogika.KontrolerPL;
import poslovnaLogika.so.OpstaSO;
import transfer.ServerTransferObjekat;
import transfer.KlijentTransferObjekat;

/**
 *
 * @author KORISNIK
 */
public class ProveraTiketa {

    public void postaviStatusTiketa(List<OpstiDomenskiObjekat> lo, Utakmica utakmica) throws Exception {
        List<Tiket> listaTiketa = new ArrayList<>();

        for (int i = 0; i < lo.size(); i++) {
            Tiket t = (Tiket) lo.get(i);
            listaTiketa.add(t);
        }
        for (Tiket tiket : listaTiketa) {
            if (tiket.getStatus() != null) {
                if (tiket.getStatus().equals("aktivan")) {
                    for (StavkaTiketa sk : tiket.getStavkeTiketa()) {
                        if (sk.getUtakmica().getIdUtakmice() == utakmica.getIdUtakmice()) {
                            if (proveriUtakmicu(sk.getUtakmica().getGoloviDomacin() + ":" + sk.getUtakmica().getGoloviGost(), sk.getTip().getNazivTipa()) == 0) {
                                //postavi status tog tiketa na gubitan
                                KontrolerPL.vratiInstancu().promeniStatusTiketaa(tiket);

                            }
                        }
                    }
                }
            }
        }
    }

    private int proveriUtakmicu(String rezultat, String tip) {
        String[] nizRezultata = rezultat.split(":");
        int goloviDomacin = 0;
        int goloviGost = 0;
        try {
            goloviDomacin = Integer.parseInt(nizRezultata[0]);
            goloviGost = Integer.parseInt(nizRezultata[1]);
        } catch (Exception e) {
            return -1;

        }
        if (tip.equals("1")) {
            if (goloviDomacin > goloviGost) {
                return 1;
            }
        }
        if (tip.equals("x")) {
            if (goloviDomacin == goloviGost) {
                return 1;
            }
        }
        if (tip.equals("2")) {
            if (goloviDomacin < goloviGost) {
                return 1;
            }
        }
        if (tip.equals("0-2")) {
            if ((goloviDomacin + goloviGost) <= 2) {
                return 1;
            }
        }
        if (tip.equals("3+")) {
            if ((goloviDomacin + goloviGost) >= 3) {
                return 1;
            }
        }
        return 0;
    }
}
