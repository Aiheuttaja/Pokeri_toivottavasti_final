/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package voittovalinta;

import kortit.Kasi;
import kortit.Korttipakka;
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
public class JakajaTest {

    Kasi käsi;
    Korttipakka pakka;
    Jakaja jakaja;

    public JakajaTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        käsi = new Kasi();
        pakka = new Korttipakka();
        jakaja = new Jakaja();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void uudessaKadessaViisiKorttia() {
        jakaja.uusiKasi(käsi, pakka);
        assertEquals(5, käsi.listaaKortit().size());
        assertEquals(47, pakka.mitkaKortit().size());
    }

    @Test
    public void toinenUusiKasiJossaLukittujaKorttejaMaaratOikein() {
        jakaja.uusiKasi(käsi, pakka);
        käsi.lukitseKortti(0);
        käsi.lukitseKortti(1);
        jakaja.uusiKasi(käsi, pakka);
        assertEquals(5, käsi.listaaKortit().size());
        assertEquals(47,pakka.mitkaKortit().size());
    }
}
