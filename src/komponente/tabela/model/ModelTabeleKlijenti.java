/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package komponente.tabela.model;

import domen.StavkaTiketa;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import static javafx.scene.input.KeyCode.S;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import niti.NitKlijent;

/**
 *
 * @author KORISNIK
 */
public class ModelTabeleKlijenti extends AbstractTableModel {

    List<NitKlijent> klijentii = new ArrayList<>();
    String[] kolone = {"r.b.", "kor.ime", "ip adresa", "broj zahteva", "odjava"};

    private static final long serialVersionUID = 1L;
    private static final String[] COLUMN_NAMES = new String[]{"r.b.", "kor.ime", "ip adresa", "broj zahteva", "odjava"};
    private static final Class<?>[] COLUMN_TYPES = new Class<?>[]{Integer.class, String.class, JButton.class, JButton.class};

    public void namestiListu(List<NitKlijent> klijenti) {

        klijentii = klijenti;

    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public int getRowCount() {
        if (klijentii == null) {
            return 0;
        }
        return klijentii.size();
    }

    @Override
    public String getColumnName(int columnIndex) {
        return COLUMN_NAMES[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return COLUMN_TYPES[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        NitKlijent klijent = klijentii.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return rowIndex + 1;
            case 1:
                return klijent.getKorisnickoIme();
            case 2:
                return klijent.getVezaSaKlijentom().getInetAddress();
            case 3:
                return klijent.getBrojZahteva();
            case 4:
                final JButton button = new JButton("odjavi");
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent arg0) {

                    }
                });
                return button;
            default:
                return "n/a";

        }

    }

    public void obrisiRed(int rowIndex) {

        klijentii.remove(rowIndex);

        fireTableDataChanged();

    }

    public void dodajKlijenta(NitKlijent klijent) {
        if (klijentii == null) {
            System.out.println("jeste null lista");
        }
        klijentii.add(klijent);
        fireTableDataChanged();
    }

}
