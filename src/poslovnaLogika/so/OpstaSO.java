/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poslovnaLogika.so;

import db.DatabaseBroker;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Korisnik
 */
public abstract class OpstaSO {
    protected DatabaseBroker dbbr;
    
    public OpstaSO(){
        dbbr=new DatabaseBroker();
    }
    
    public void uspostaviKonekciju() throws Exception{
        dbbr.uspostaviKonekciju();
    }
    
    protected abstract void proveriPreduslove(Object obj) throws Exception;
    
    protected abstract void izvrsi(Object obj) throws Exception;
    
    private void potvrdiTransakciju() throws SQLException{
        dbbr.potvrdiTransakciju();
    }
    
    private void ponistiTransakciju() throws SQLException{
        dbbr.ponistiTransakciju();
    }
    
    private void raskiniKonekciju() throws SQLException{
        dbbr.raskiniKonekciju();
    }
    
    public final void opsteIzvrsenje(Object obj) throws Exception{
        try {
            uspostaviKonekciju();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        
        try{
            proveriPreduslove(obj);
            izvrsi(obj);
            potvrdiTransakciju();
        }catch(Exception e){
            e.printStackTrace();
            ponistiTransakciju();
            throw e;
        }finally{
            raskiniKonekciju();
        }
    }
}
