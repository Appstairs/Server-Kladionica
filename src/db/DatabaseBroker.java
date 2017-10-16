/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import domen.Kvota;
import domen.OpstiDomenskiObjekat;
import domen.Radnik;
import domen.StavkaTiketa;
import domen.Tiket;
import domen.Tip;
import domen.UplatnoMesto;
import domen.Utakmica;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KORISNIK
 */
public class DatabaseBroker {

    private Connection connection;

    public void uspostaviKonekciju() throws Exception {

        String konekcioniString = DBBRSettings.vratiInstancu().vratiURL();
        String user = DBBRSettings.vratiInstancu().vratiUsername();
        String pass = DBBRSettings.vratiInstancu().vratiPassword();
        connection = DriverManager.getConnection(konekcioniString, user, pass);
        connection.setAutoCommit(false);
        System.out.println("USPESNA KONEKCIJA");
    }

    public void potvrdiTransakciju() throws SQLException {
        connection.commit();
    }

    public void ponistiTransakciju() throws SQLException {
        connection.rollback();
    }

    public void raskiniKonekciju() throws SQLException {
        connection.close();
        System.out.println("Uspesno raskinuta konekcija!");
    }

    public List<OpstiDomenskiObjekat> vratiSve(OpstiDomenskiObjekat odo) throws Exception {
        try {
            String sql = "SELECT " + odo.vratiSelekt() + " FROM " + odo.vratiNazivTabele();
            System.out.println(sql);
            Statement sqlStatement = connection.createStatement();
            ResultSet rs = sqlStatement.executeQuery(sql);
            return odo.ucitaj(rs, odo);
        } catch (Exception ex) {
            throw new Exception("Neuspesno ucitavanje objekata!", ex);
        }
    }

    public List<OpstiDomenskiObjekat> vratiSveSaUslovom(OpstiDomenskiObjekat odo) throws Exception {
        try {
            String sql = "SELECT " + odo.vratiSelektSaUslovom(odo.getParametar()) + " FROM " + odo.vratiNazivTabele() + " WHERE " + odo.vratiUslov(odo);
            System.out.println(sql);
            Statement sqlStatement = connection.createStatement();
            ResultSet rs = sqlStatement.executeQuery(sql);

            return odo.ucitajSaUslovom(rs, odo, odo.getParametar());
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Neuspesno ucitavanje objekata!", ex);
        }
    }

    public void obrisi(OpstiDomenskiObjekat odo) throws Exception {
        try {
            String upit = "DELETE FROM " + odo.vratiNazivZaObrisi() + " WHERE " + odo.vratiUslovZaObrisi(odo);
            System.out.println(upit);
            Statement sqlStatement = connection.createStatement();
            sqlStatement.execute(upit);
            sqlStatement.close();
        } catch (Exception e) {
            throw new Exception("Neuspesno brisanje objekata!");
        }
    }

    public void ubaci(OpstiDomenskiObjekat odo) throws Exception {

        try {
            String sql = "INSERT INTO " + odo.vratiNazivTabeleUbaci() + " ( " + odo.koloneUbaci(odo) + " ) VALUES ( " + odo.parametri(odo) + " ) ";
            System.out.println(sql);
            PreparedStatement sqlStatement = connection.prepareStatement(sql);
            PreparedStatement statement = odo.statementInsert(sqlStatement, odo);
            statement.executeUpdate();
            statement.close();

        } catch (Exception e) {
            throw new Exception("Neuspesno unosenje objekata! " + e);
        }

    }

    public void izmeni(OpstiDomenskiObjekat odo) throws Exception {

        try {
            String sql = "UPDATE " + odo.vratiNazivTabeleIzmena() + " SET " + odo.vratiKoloneIzmena(odo.getParametar()) + " WHERE " + odo.uslovIzmena(odo.getParametar());
            System.out.println(sql);
            PreparedStatement sqlStatement = connection.prepareStatement(sql);
            PreparedStatement statement = odo.statementIzmeni(sqlStatement, odo, odo.getParametar());
            statement.execute();
            statement.close();

        } catch (Exception e) {
            throw new Exception("Neuspesna izmena objekata! " + e);
        }

    }
}
