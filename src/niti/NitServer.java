/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package niti;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import komponente.tabela.model.ModelTabeleKlijenti;

public class NitServer extends Thread {

    private ServerSocket serverSocket;
    private ModelTabeleKlijenti model;

    public NitServer(ServerSocket serverSocket, ModelTabeleKlijenti model) {
        this.serverSocket = serverSocket;
        this.model = model;
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {

                Socket klijentSocket = serverSocket.accept();

                NitKlijent nit = new NitKlijent(klijentSocket, model);
                nit.start();

            }
        } catch (Exception ex) {
            try {
                serverSocket.close();
            } catch (IOException ex1) {
                ex1.printStackTrace();
            }

        }
    }
}
