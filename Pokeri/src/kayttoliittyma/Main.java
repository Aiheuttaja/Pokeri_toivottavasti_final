/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma;

import kortit.Kasi;
import kortit.Korttipakka;
import voittovalinta.Jakaja;
import javax.swing.SwingUtilities;

/**
 *
 * @author Janne
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Kayttoliittyma kayttis = new Kayttoliittyma();
        SwingUtilities.invokeLater(kayttis);
        
    }
}
