/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package start;

import forme.FGlavnaServer;
import forme.JPanelServer;
import java.awt.BorderLayout;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import komponente.tabela.model.ModelTabeleKlijenti;

/**
 *
 * @author KORISNIK
 */
public class Start {

    public static void main(String[] args) throws IOException {
        ModelTabeleKlijenti model = new ModelTabeleKlijenti();
        JFrame fGlavnaServer = new FGlavnaServer(model);
        JPanel jPanel = new JPanelServer(model);
        fGlavnaServer.setLayout(new BorderLayout());
        JScrollPane jScroll = new JScrollPane(jPanel);
        fGlavnaServer.add(jScroll, BorderLayout.CENTER);
        fGlavnaServer.pack();
        fGlavnaServer.setLocationRelativeTo(null);
        fGlavnaServer.setResizable(false);
        fGlavnaServer.setVisible(true);

    }
}
