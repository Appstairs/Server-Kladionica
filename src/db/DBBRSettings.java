/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import sun.awt.FontConfiguration;

public class DBBRSettings {

    Properties podesavanja;

    private DBBRSettings() throws FileNotFoundException, IOException {
        loadProperties();
    }

    private static DBBRSettings instanca;
    // private String currentDB;

    public static DBBRSettings vratiInstancu() throws FileNotFoundException, IOException {
        if (instanca == null) {
            instanca = new DBBRSettings();
        }
        return instanca;
    }

    private void loadProperties() throws FileNotFoundException, IOException {

        FileInputStream fis = new FileInputStream("config.properties");
        podesavanja = new Properties();
        podesavanja.load(fis);
    }

    public String vratiURL() {
        return podesavanja.getProperty("url");
    }

    public String vratiUsername() {
        return podesavanja.getProperty("user");
    }

    public String vratiPassword() {
        return podesavanja.getProperty("password");
    }

}
