/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kortit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Janne
 */
public class KorttipakkaTest {

    Korttipakka pakka;

    public KorttipakkaTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        pakka = new Korttipakka();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void paallimmaisinToimii() {
        //Pakan kortit luodaan järjestyksessä, jolloin ennen sekoitusta pakan ensiksi luotu (päällimmäisin) kortti on (0,0), eli herttakaksi.
        boolean tarkistus = false;

        Kortti paallimmaisin = pakka.PaallimmaisinKortti();

        if (paallimmaisin.haeMaa() == 0) {
            if (paallimmaisin.haeArvo() == 0) {
                tarkistus = true;
            }
        }
        assertEquals(true, tarkistus);
    }

    @Test
    public void oikeaMaaraKortteja() {
        assertEquals(52, pakka.paljonkoKortteja());

    }

    @Test
    public void poistoToimii() {
        //Poistetaan pakan päällimmäinen Kortti, ♥2 eli Kortti, jonka maa on 0 ja arvo on 0.
        pakka.PaallimmaisinKortti();
        boolean olikoSiella = false;

        //Tarkistetaan, onko pakassa sellaista korttia, jolla on maa 0 ja arvo 0, eli ♥2.
        for (Kortti k : pakka.mitkaKortit()) {
            if (k.haeMaa() == 0) {
                if (k.haeArvo() == 0) {
                    olikoSiella = true;
                }
            }
        }

        assertEquals(false, olikoSiella);

    }

    @Test
    public void kortinLisaysToimii() {
        Kortti poistettu = pakka.PaallimmaisinKortti();

        pakka.lisaaKortti(poistettu);
        boolean olikoSiella = false;

        for (Kortti k : pakka.mitkaKortit()) {
            if (k.haeMaa() == 0) {
                if (k.haeArvo() == 0) {
                    olikoSiella = true;
                }
            }
        }

        assertEquals(true, olikoSiella);
    }
    
    
}
