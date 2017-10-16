/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package komponente.tabela.model;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author KORISNIK
 */
public class JTableButtonMouseListener extends MouseAdapter {

    private final JTable table;
    private ModelTabeleKlijenti model;

    public JTableButtonMouseListener(JTable table, ModelTabeleKlijenti model) {
        this.table = table;
        this.model = model;
    }

    public void mouseClicked(MouseEvent e) {
        int column = table.getColumnModel().getColumnIndexAtX(e.getX()); // get the coloum of the button
        int row = e.getY() / table.getRowHeight(); //get the row of the button

        /*Checking the row or column is valid or not*/
        if (row < table.getRowCount() && row >= 0 && column < table.getColumnCount() && column >= 0) {
            Object value = table.getValueAt(row, column);
            if (value instanceof JButton) {
                /*kad kliknes sta ce da radi*/
                String ime = table.getValueAt(row, 1).toString();
                System.out.println("izvuceno ime je " + ime);
                System.out.println("size u klijenti pre: " + repozitorijum.Repozitorijum.klijenti.size());
                for (int i = 0; i < repozitorijum.Repozitorijum.klijenti.size(); i++) {
                    if (repozitorijum.Repozitorijum.klijenti.get(i).getKorisnickoIme().equals(ime)) {
                        repozitorijum.Repozitorijum.klijenti.get(i).interrupt();
                        try {
                            repozitorijum.Repozitorijum.klijenti.get(i).getVezaSaKlijentom().close();
                        } catch (IOException ex) {
                            Logger.getLogger(JTableButtonMouseListener.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        model.obrisiRed(row);
                        JOptionPane.showMessageDialog(table, "Uspesno ste odjavili klijenta: " + ime);
                        return;
                    }
                }

                JOptionPane.showMessageDialog(table, "Niste odjavili klijenta!");

                ((JButton) value).doClick();
            }
        }
    }
}
