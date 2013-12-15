package kortit;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
public class KorttiTest {
    
    Kortti herttaAssa;
    
    public KorttiTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        herttaAssa = new Kortti(0,12);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void arvoOikein(){
        assertEquals(12,herttaAssa.haeArvo());        
    }
    
    @Test
    public void maaOikein(){
        assertEquals(0,herttaAssa.haeMaa());
    }
    
    @Test
    public void tulostuuOikein(){
        assertEquals("♥A",herttaAssa.toString());
    }
    
    @Test
    public void lukitusOikein(){
        herttaAssa.lukitse();
        assertEquals(true,herttaAssa.olenkoLukittu());
    }
    
    @Test
    public void avausOikein(){
        herttaAssa.lukitse();
        herttaAssa.avaa();
        assertEquals(false,herttaAssa.olenkoLukittu());
    }
    
}
