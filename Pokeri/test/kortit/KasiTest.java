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
import voittovalinta.Jakaja;

/**
 *
 * @author jilli
 */
public class KasiTest {

    Kasi käsi;
    Jakaja jakaja;
    Korttipakka pakka;

    public KasiTest() {
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
        jakaja = new Jakaja();
        pakka = new Korttipakka();
        jakaja.uusiKasi(käsi, pakka);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void jaonJalkeenKadessaViisiKorttia() {

        assertEquals(5, käsi.listaaKortit().size());
    }

    @Test
    public void tulostuuOikein() {
        assertEquals("♥2 ♥3 ♥4 ♥5 ♥6 ", käsi.toString());
    }

    @Test
    public void kunOtetaanKorttiPoisNiinKateenJaaNelja() {
        käsi.poistaKadesta(0);
        assertEquals(4, käsi.listaaKortit().size());
    }

    @Test
    public void eiNostetaKuudettaKorttia() {
        käsi.otaKateen(pakka.PaallimmaisinKortti());
        assertEquals(5, käsi.listaaKortit().size());
    }

    @Test
    public void kortinNostoToimii() {
        käsi.poistaKadesta(0);
        käsi.poistaKadesta(0);
        käsi.otaKateen(pakka.PaallimmaisinKortti());
        assertEquals(4, käsi.listaaKortit().size());
    }
    
    @Test
    public void lukotusToimii(){
        käsi.lukitseKortti(0);
        käsi.lukitseKortti(1);
        käsi.avaaKortti(1);
        assertEquals(true,käsi.getKortti(0).olenkoLukittu());
        assertEquals(false,käsi.getKortti(1).olenkoLukittu());
        assertEquals(false,käsi.getKortti(2).olenkoLukittu());
    }
    
    @Test
    public void nollausToimii(){
        käsi.lukitseKortti(0);
        käsi.lukitseKortti(1);
        käsi.lukitseKortti(2);
        käsi.nollaus();
        for(Kortti k:käsi.listaaKortit()){
            assertEquals(false,k.olenkoLukittu());
        }
    }
}