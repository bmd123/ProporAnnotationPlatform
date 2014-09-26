package br.usp.properties;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author posdoc
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Properties {

    java.util.Properties prop = new java.util.Properties();
    InputStream input = null;

    public Properties() {
        try {
            prop.load(Thread.currentThread().
                    getContextClassLoader().getResourceAsStream("Properties.properties"));
        } catch (IOException ex) {
            Logger.getLogger(Properties.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(Properties.class.getName()).log(Level.SEVERE, null, 
                    getClass().getResourceAsStream("Properties.properties"));
        }
    }

    public String returnPropertyValue(String key) {

        String returnValue = prop.getProperty(key);
        return returnValue;

    }
}
