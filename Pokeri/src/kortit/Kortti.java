/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kortit;

/**
 * Sisältää yhdessä pelikortissa olevan oleellisen informaation, kuten kortin
 * maan, arvon ja sen, onko kortti lukittu (voidaanko sen tilalle jakaa uutta
 * kättä jaettaessa uusi kortti vai ei)
 *
 */
public class Kortti {

    private int arvo;
    private int maa;
    private boolean lukitus;
    public final String[] maat = {"♥", "♦", "♠", "♣"};
    public final String[] arvot = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

    public Kortti(int maa, int arvo) {
        this.arvo = arvo;
        this.maa = maa;
        this.lukitus = false;
    }

    /**
     * palauttaa kortin arvon
     *
     * @return kortin arvo numerona (J=10, Q=11, K=12, A=13)
     */
    public int haeArvo() {
        return this.arvo;
    }

    /**
     * palauttaa kortin maan
     *
     * @return maan numero (1=♥, 2=♦, 3=♠, 4=♣)
     */
    public int haeMaa() {
        return this.maa;
    }

    /**
     * lukitsee kortin (=se jää käteen, kun jaetaan uusi käsi, eli sen tilalle
     * ei jaeta uutta korttia)
     */
    public void lukitse() {
        this.lukitus = true;
    }

    /**
     * poistaa lukituksen.
     */
    public void avaa() {
        this.lukitus = false;
    }

    /**
     * kertoo, onko tämä kortti lukittu
     *
     * @return true tai false, riippuen siitä onko kortti lukittu
     */
    public boolean olenkoLukittu() {
        return this.lukitus;
    }

    /**
     * tulostaa kortin visuaalisen esityksen = kortin maan ja arvon
     *
     * @return kortin maa ja arvo tekstinä
     */
    @Override
    public String toString() {
        return this.maat[this.maa] + this.arvot[this.arvo];
    }
}
