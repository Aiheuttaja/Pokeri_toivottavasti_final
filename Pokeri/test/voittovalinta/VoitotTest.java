/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package voittovalinta;

import kortit.Kasi;
import kortit.Kortti;
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
public class VoitotTest {

    Kasi käsi;
    Kortti herttaässä;
    Kortti herttakuningas;
    Kortti herttakuningatar;
    Kortti herttajätkä;
    Kortti herttakymppi;
    Kortti ruutukymppi;
    Kortti ristikymppi;
    Kortti patakymppi;
    Kortti ruutujätkä;
    Kortti herttaysi;
    Voitot tarkastaja;

    public VoitotTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        käsi=new Kasi();
        herttaässä=new Kortti(0,12);
        herttakuningas=new Kortti(0,11);
        herttakuningatar=new Kortti(0,10);
        herttajätkä=new Kortti(0,9);
        herttakymppi=new Kortti(0,8);
        ruutukymppi=new Kortti(1,8);
        ristikymppi=new Kortti(3,8);
        patakymppi=new Kortti(2,8);
        ruutujätkä=new Kortti(1,9);
        herttaysi=new Kortti(0,7);
        tarkastaja=new Voitot(100);
    }

    @After
    public void tearDown() {
    }
    
    @Test
    public void panosAlussaOikein(){
        assertEquals(20,tarkastaja.getPanos());
    }
    
    @Test
    public void voitotAlussaOikein(){
        assertEquals(0,tarkastaja.getVoitot());
    }
    
    @Test
    public void viimeisinVoittoAlussaOikein(){
        assertEquals(0,tarkastaja.getViimeisinVoitto());
    }
    
    @Test
    public void onkoKuningasVariSuora(){
        käsi.otaKateen(herttaässä);
        käsi.otaKateen(herttakuningas);
        käsi.otaKateen(herttakuningatar);
        käsi.otaKateen(herttajätkä);
        käsi.otaKateen(herttakymppi);
        assertEquals(1000,tarkastaja.tulikoVoittoa(käsi));
    }
    
    @Test
    public void onkoVariSuora(){
        käsi.otaKateen(herttakymppi);
        käsi.otaKateen(herttakuningas);
        käsi.otaKateen(herttakuningatar);
        käsi.otaKateen(herttajätkä);
        käsi.otaKateen(herttaysi);
        assertEquals(800,tarkastaja.tulikoVoittoa(käsi));
    }
    
    @Test
    public void onkoNeloset(){
        käsi.otaKateen(herttakymppi);
        käsi.otaKateen(ruutukymppi);
        käsi.otaKateen(ristikymppi);
        käsi.otaKateen(patakymppi);
        käsi.otaKateen(ruutujätkä);
        assertEquals(400,tarkastaja.tulikoVoittoa(käsi));
    }
    
    @Test
    public void onkoTayskasi(){
        käsi.otaKateen(herttakymppi);
        käsi.otaKateen(ruutukymppi);
        käsi.otaKateen(ristikymppi);
        käsi.otaKateen(herttajätkä);
        käsi.otaKateen(ruutujätkä);
        assertEquals(200,tarkastaja.tulikoVoittoa(käsi));
    }
    
    @Test
    public void onkoVari(){
        käsi.otaKateen(herttaässä);
        käsi.otaKateen(herttakuningas);
        käsi.otaKateen(herttakuningatar);
        käsi.otaKateen(herttajätkä);
        käsi.otaKateen(herttaysi);
        assertEquals(100,tarkastaja.tulikoVoittoa(käsi));
    }
    
    @Test
    public void onkoSuora(){
        käsi.otaKateen(ruutukymppi);
        käsi.otaKateen(herttaässä);
        käsi.otaKateen(herttakuningas);
        käsi.otaKateen(herttakuningatar);
        käsi.otaKateen(herttajätkä);
        assertEquals(80,tarkastaja.tulikoVoittoa(käsi));
    }
    
    @Test
    public void onkoKolmoset(){
        käsi.otaKateen(herttakymppi);
        käsi.otaKateen(ruutukymppi);
        käsi.otaKateen(patakymppi);
        käsi.otaKateen(herttaässä);
        käsi.otaKateen(ruutujätkä);
        assertEquals(40,tarkastaja.tulikoVoittoa(käsi));
    }
    
    @Test
    public void onkoKaksiParia(){
        käsi.otaKateen(herttakymppi);
        käsi.otaKateen(ruutukymppi);
        käsi.otaKateen(herttajätkä);
        käsi.otaKateen(ruutujätkä);
        käsi.otaKateen(herttaässä);
        assertEquals(40,tarkastaja.tulikoVoittoa(käsi));
    }
    
    @Test
    public void onkoMitaan(){
        käsi.otaKateen(herttakymppi);
        käsi.otaKateen(ruutukymppi);
        käsi.otaKateen(herttajätkä);
        käsi.otaKateen(herttaässä);
        käsi.otaKateen(herttaysi);
        assertEquals(0,tarkastaja.tulikoVoittoa(käsi));
    }
    
    @Test
    public void panosOikein(){
        tarkastaja.kasvataPanosta();
        tarkastaja.kasvataPanosta();
        tarkastaja.kasvataPanosta();
        tarkastaja.kasvataPanosta();
        //Palataan takaisin 0.20€ panokseen seuraavalla kasvatuksella.
        tarkastaja.kasvataPanosta();
        tarkastaja.kasvataPanosta();
        assertEquals(40,tarkastaja.getPanos());
    }
    
    @Test
    public void viimeisinVoittoOikein(){
        käsi.otaKateen(herttaässä);
        käsi.otaKateen(herttakuningas);
        käsi.otaKateen(herttakuningatar);
        käsi.otaKateen(herttajätkä);
        käsi.otaKateen(herttakymppi);
        tarkastaja.tulikoVoittoa(käsi);
        assertEquals(1000,tarkastaja.getViimeisinVoitto());
    }
    
    @Test
    public void voitotOikein(){
        käsi.otaKateen(herttaässä);
        käsi.otaKateen(herttakuningas);
        käsi.otaKateen(herttakuningatar);
        käsi.otaKateen(herttajätkä);
        käsi.otaKateen(herttakymppi);
        tarkastaja.tulikoVoittoa(käsi);
        tarkastaja.tulikoVoittoa(käsi);
        tarkastaja.tulikoVoittoa(käsi);
        assertEquals(3000,tarkastaja.getVoitot());
    }
    
    @Test
    public void rahatOikein(){
        assertEquals(100,tarkastaja.getRahat());
    }
    
    @Test
    public void rahanOttoToimii(){
        tarkastaja.otaRahaa();
        assertEquals(80,tarkastaja.getRahat());
    }
    
    @Test
    public void voitoistaOttoToimii(){
        käsi.otaKateen(herttakymppi);
        käsi.otaKateen(ruutukymppi);
        käsi.otaKateen(herttajätkä);
        käsi.otaKateen(ruutujätkä);
        käsi.otaKateen(herttaässä);
        tarkastaja.tulikoVoittoa(käsi);
        tarkastaja.otaRahaa();
        tarkastaja.otaRahaa();
        tarkastaja.otaRahaa();
        //Pelaajalla nyt voittoja 40 senttiä, rahaa 40 senttiä. pelaamalla 60 sentin panoksella tulee 40-60 erotukseksi -20,
        //jolloin tämä voitoista otettaessa pelaajalle jää voittoihin 20 senttiä.
        tarkastaja.kasvataPanosta();
        tarkastaja.kasvataPanosta();
        tarkastaja.otaVoitoista(-20);
        assertEquals(20,tarkastaja.getVoitot());
    }
    
    @Test
    public void eiIsompaaPanostaKuinOnRahaa(){
        tarkastaja.otaRahaa();
        tarkastaja.kasvataPanosta();
        tarkastaja.kasvataPanosta();
        tarkastaja.kasvataPanosta();
        //Rahaa oli jäljellä koneessa vain 80 senttiä, joten seuraavalla kasvatuksella palataan takaisin 20 senttiin
        tarkastaja.kasvataPanosta();
        
        assertEquals(20,tarkastaja.getPanos());
    }
    
    @Test
    public void eiIsompaaPanostaKuinOnRahaaJaVoittoja(){        
        tarkastaja.otaRahaa();
        tarkastaja.otaRahaa();
        tarkastaja.otaRahaa();
        tarkastaja.otaRahaa();
        käsi.otaKateen(herttakymppi);
        käsi.otaKateen(ruutukymppi);
        käsi.otaKateen(herttajätkä);
        käsi.otaKateen(ruutujätkä);
        käsi.otaKateen(herttaässä);
        tarkastaja.tulikoVoittoa(käsi);
        tarkastaja.kasvataPanosta();
        tarkastaja.kasvataPanosta();
        tarkastaja.kasvataPanosta();
        assertEquals(20,tarkastaja.getPanos());
    }
    
    @Test
    public void panoksenNollaus(){
        tarkastaja.kasvataPanosta();
        tarkastaja.kasvataPanosta();
        tarkastaja.nollaaPanos();
        assertEquals(20,tarkastaja.getPanos());
    }
    
}
