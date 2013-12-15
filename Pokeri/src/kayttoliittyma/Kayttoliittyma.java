package kayttoliittyma;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.ChangeListener;
import kortit.Kasi;
import kortit.Korttipakka;
import voittovalinta.Jakaja;
import voittovalinta.Voitot;

/**
 *
 * Käyttöliittymäluokka toimii rajapintana ohjelman ja pelaajan välillä. Int
 * pelivaihe muodostaa pelin etenemisen rungon ja logiikan. Pelin edetessä
 * siirrytään vaiheesta toiseen, ja vaihe rajaa pelaajan mahdollisia toimintoja
 * ja vaikuttaa siihen, miten eri toiminnot vaikuttavat. Muut komponentit ovat
 * käyttöliittymäkomponentteja ja pelilogiikan muodostavat käsi, pakka, jakaja
 * ja voitot
 */
public class Kayttoliittyma implements Runnable {

    private Kasi käsi;
    private Korttipakka pakka;
    private Jakaja jakaja;
    private Voitot voitot;
    private JFrame frame;
    private JTextArea voittolista, viesti;
    private JButton rahanappi, panosnappi, jakonappi, ok;
    private JTextField viimeisinVoitto, panos, voittorahat, rahat, eka, toka, kolmas, neljäs, viides;
    private ArrayList<JTextField> korttikentät;
    private ArrayList<JButton> lukitusnapit;
    private int pelivaihe; //0=alkutilanne, 1=alkukäsi on jaettu, 2=lopullinen käsi on jaettu, voitto on selvillä 

    public Kayttoliittyma() {
    }

    /**
     * Luo tarpeelliset komponentit ja käynnistää pelin pelaa()-metodilla.
     */
    @Override
    public void run() {
        //Luodaan peliä varten tarvittavat oliot sekä JFrame, jossa kaikki tapahtuu.
        this.käsi = new Kasi();
        this.pakka = new Korttipakka();
        this.pakka.sekoitaPakka();
        this.jakaja = new Jakaja();
        this.voitot = new Voitot(0);
        frame = new JFrame("Pokerikone");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        luoKomponentit(frame.getContentPane());
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        //Aloitetaan peli
        this.pelivaihe = 0;
        for (JButton j : this.lukitusnapit) {
            j.setEnabled(false);
        }
        pelaa();

    }

    /**
     * Luodaan pelin käyttöliittymän komponentit.
     *
     * @param container frame, johon komponentit lisätään
     */
    public void luoKomponentit(Container container) {

        //Luodaan JPaneleilla paikat komponenteille.
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        //Yläpaneeli.
        JPanel upper = new JPanel();
        upper.setLayout(new BorderLayout());
        container.add(upper);

        //Alapaneeli.
        JPanel lower = new JPanel();
        lower.setLayout(new BoxLayout(lower, BoxLayout.Y_AXIS));
        container.add(lower);

        //Ylävasemmalle tulee viimeisin voitto, voittorahat, pelirahat sekä getPanos.
        JPanel upperleft = new JPanel();
        upperleft.setLayout(new BoxLayout(upperleft, BoxLayout.Y_AXIS));

        //Yläoikealle tulee lista mahdollisista voittokäsistä ja niistä saatavista voittorahoista,
        //sekä pelaajalle kommunikoiva viestikenttä.
        JPanel upperright = new JPanel();
        upperright.setLayout(new BoxLayout(upperright, BoxLayout.Y_AXIS));

        //Lisätään molemmat upper-paneeliin.
        upper.add(upperleft, BorderLayout.WEST);
        upper.add(upperright, BorderLayout.EAST);

        //Alapaneelin ylimpään kolmannekseen tulee pelinhallintaan liittyvät painikkeet:
        //rahanlisäys, panoksen kasvatus sekä uuden käden jakaminen.
        JPanel lower1 = new JPanel();
        lower1.setLayout(new BoxLayout(lower1, BoxLayout.X_AXIS));
        lower.add(lower1);

        //Alapaneelin keskikolmannekseen tulee kädessä olevat kortit.
        JPanel lower2 = new JPanel();
        lower2.setLayout(new BoxLayout(lower2, BoxLayout.X_AXIS));
        lower.add(lower2);

        //Alapaneelin alimpaan kolmannekseen tulee lukituspainikkeet korttien lukitsemista varten.
        JPanel lower3 = new JPanel();
        lower3.setLayout(new BoxLayout(lower3, BoxLayout.X_AXIS));
        lower.add(lower3);

        //Luodaan lista voitoista.
        this.voittolista = new JTextArea();
        Font muutTekstit = new Font("Consolas", Font.CENTER_BASELINE, 12);
        Font muutTekstit2 = new Font("Consolas", Font.CENTER_BASELINE, 16);
        this.voittolista.setFont(muutTekstit);
        this.voittolista.setText("Kuningasvärisuora            " + (this.voitot.getPanos() * 50) / 100.0 + "0 € \n"
                + "Värisuora                    " + (this.voitot.getPanos() * 40) / 100.0 + "0 €" + "\n"
                + "Neloset                      " + (this.voitot.getPanos() * 20) / 100.0 + "0 € \n"
                + "Täyskäsi                     " + (this.voitot.getPanos() * 10) / 100.0 + "0 € \n"
                + "Väri                         " + (this.voitot.getPanos() * 5) / 100.0 + "0 € \n"
                + "Suora                        " + (this.voitot.getPanos() * 4) / 100.0 + "0 € \n"
                + "Kolmoset                     " + (this.voitot.getPanos() * 2) / 100.0 + "0 € \n"
                + "Kaksi paria                  " + (this.voitot.getPanos() * 2) / 100.0 + "0 €");
        this.voittolista.setEditable(false);
        upperright.add(this.voittolista);

        //Luodaan tekstikenttä käyttäjälle esitettäviä viestejä varten.
        this.viesti = new JTextArea();
        this.viesti.setForeground(Color.BLUE);
        this.viesti.setFont(new Font("Times New Roman", Font.CENTER_BASELINE, 14));
        upperright.add(this.viesti);

        //Luodaan tekstikenttä, joka ilmaisee viimeisimmän voiton.
        JLabel viimVoit = new JLabel("Viimeisin voitto");
        viimVoit.setVisible(true);
        this.viimeisinVoitto = new JTextField();
        this.viimeisinVoitto.setEditable(false);
        this.viimeisinVoitto.setText(String.valueOf((this.voitot.getViimeisinVoitto()) / 100.0) + "0");
        this.viimeisinVoitto.setFont(muutTekstit2);
        this.viimeisinVoitto.setBackground(Color.WHITE);
        upperleft.add(viimVoit);
        upperleft.add(this.viimeisinVoitto);

        //Luodaan tekstikenttä, joka ilmaisee panoksen.
        JLabel pan = new JLabel("Panos");
        pan.setVisible(true);
        this.panos = new JTextField();
        this.panos.setEditable(false);
        this.panos.setText(String.valueOf((this.voitot.getPanos()) / 100.0) + "0");
        this.panos.setFont(muutTekstit2);
        this.panos.setBackground(Color.WHITE);
        upperleft.add(pan);
        upperleft.add(this.panos);

        //Luodaan tekstikenttä, joka ilmaisee kertyneet voitot.
        JLabel voit = new JLabel("Voitot");
        voit.setVisible(true);
        this.voittorahat = new JTextField();
        this.voittorahat.setEditable(false);
        this.voittorahat.setText(String.valueOf((this.voitot.getVoitot()) / 100.0) + "0");
        this.voittorahat.setFont(muutTekstit2);
        this.voittorahat.setBackground(Color.WHITE);
        upperleft.add(voit);
        upperleft.add(this.voittorahat);

        //Luodaan tekstikenttä, joka ilmaisee, paljonko pelaajan syöttämistä rahoista on jäljellä
        //ts. paljonko pelirahaa on jäljellä.
        JLabel rahut = new JLabel("Pelit");
        rahut.setVisible(true);
        this.rahat = new JTextField();
        this.rahat.setEditable(false);
        this.rahat.setText(String.valueOf((this.voitot.getRahat()) / 100.0) + "0");
        this.rahat.setFont(muutTekstit2);
        this.rahat.setBackground(Color.WHITE);
        upperleft.add(rahut);
        upperleft.add(this.rahat);

        //Luodaan rahansyöttöpainike
        this.rahanappi = new JButton("Syötä rahaa");
        lower1.add(this.rahanappi);

        //Lisätään kuuntelija painikkeelle.
        this.rahanappi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                syotaRahaa();
            }
        });

        //Luodaan panoksenkasvatuspainike
        this.panosnappi = new JButton("Panos");
        lower1.add(this.panosnappi);

        //Lisätään kuuntelija painikkeelle.
        this.panosnappi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panoksenKasvatus();
            }
        });

        //Luodaan uuden käden jakopainike
        this.jakonappi = new JButton("Jaa");
        lower1.add(this.jakonappi);

        //Lisätään kuuntelija painikkeelle.
        this.jakonappi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uusKasi();
            }
        });
        this.jakonappi.setEnabled(false);

        this.ok = new JButton("Uusi peli");
        lower1.add(this.ok);
        this.ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pelaa();
            }
        });

        //Luodaan fonttiolio korttien tekstuaalista esitystä varten
        Font kortit = new Font("Times New Roman", Font.CENTER_BASELINE, 20);

        //Luodaan ensimmäisen kortin tekstikenttä
        this.eka = new JTextField();
        this.eka.setEditable(false);
        this.eka.setFont(kortit);
        this.eka.setText("P");
        this.eka.setHorizontalAlignment(JTextField.CENTER);
        this.eka.setForeground(Color.RED);
        this.eka.setBackground(Color.WHITE);
        lower2.add(this.eka);

        //Luodaan toisen kortin tekstikenttä
        this.toka = new JTextField();
        this.toka.setEditable(false);
        this.toka.setFont(kortit);
        this.toka.setText("O");
        this.toka.setHorizontalAlignment(JTextField.CENTER);
        this.toka.setForeground(Color.BLACK);
        this.toka.setBackground(Color.WHITE);
        lower2.add(this.toka);

        //Luodaan kolmannen kortin tekstikenttä
        this.kolmas = new JTextField();
        this.kolmas.setEditable(false);
        this.kolmas.setFont(kortit);
        this.kolmas.setText("K");
        this.kolmas.setHorizontalAlignment(JTextField.CENTER);
        this.kolmas.setForeground(Color.RED);
        this.kolmas.setBackground(Color.WHITE);
        lower2.add(this.kolmas);

        //Luodaan neljännen kortin tekstikenttä
        this.neljäs = new JTextField();
        this.neljäs.setEditable(false);
        this.neljäs.setFont(kortit);
        this.neljäs.setText("E");
        this.neljäs.setHorizontalAlignment(JTextField.CENTER);
        this.neljäs.setForeground(Color.BLACK);
        this.neljäs.setBackground(Color.WHITE);
        lower2.add(this.neljäs);

        //Luodaan viidennen kortin tekstikenttä
        this.viides = new JTextField();
        this.viides.setEditable(false);
        this.viides.setFont(kortit);
        this.viides.setText("R");
        this.viides.setHorizontalAlignment(JTextField.CENTER);
        this.viides.setBackground(Color.WHITE);
        this.viides.setForeground(Color.RED);
        lower2.add(this.viides);

        //Lisätään korttien tekstikentät korttikenttälistaan helppoa käsittelyä varten.
        this.korttikentät = new ArrayList<>();
        this.korttikentät.add(eka);
        this.korttikentät.add(toka);
        this.korttikentät.add(kolmas);
        this.korttikentät.add(neljäs);
        this.korttikentät.add(viides);

        //Luodaan viisi lukituspainiketta ja niille kuuntelijat.
        JButton lukitse1 = new JButton("Lukitse");
        lower3.add(lukitse1);

        lukitse1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lukitus(0);
            }
        });

        JButton lukitse2 = new JButton("Lukitse");
        lower3.add(lukitse2);

        lukitse2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lukitus(1);
            }
        });

        JButton lukitse3 = new JButton("Lukitse");
        lower3.add(lukitse3);

        lukitse3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lukitus(2);
            }
        });

        JButton lukitse4 = new JButton("Lukitse");
        lower3.add(lukitse4);

        lukitse4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lukitus(3);
            }
        });

        JButton lukitse5 = new JButton("Lukitse");
        lower3.add(lukitse5);

        lukitse5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lukitus(4);
            }
        });

        //Lisätään painikkeet listaan helppoa käsittelyä varten.
        this.lukitusnapit = new ArrayList<>();
        this.lukitusnapit.add(lukitse1);
        this.lukitusnapit.add(lukitse2);
        this.lukitusnapit.add(lukitse3);
        this.lukitusnapit.add(lukitse4);
        this.lukitusnapit.add(lukitse5);

    }

    public JFrame getFrame() {
        return frame;
    }

    /**
     * Pelin kulkua ohjaileva metodi. Jakautuu kolmeen osaan, pelivaiheisiin 0,
     * 1 ja 2, jotka määrittelevät, mitä ui-komponentteja on pelaajan
     * käytettävissä ja mitä pelaaja voi milloinkin tehdä.
     */
    private void pelaa() {
        int victory = 0;
        this.jakonappi.setVisible(true);
        this.panosnappi.setVisible(true);
        this.rahanappi.setVisible(true);
        this.ok.setVisible(false);

        if (this.pelivaihe > 2) {
            this.pelivaihe = 0;
        }

        if (this.pelivaihe == 0) {

            this.viesti.setText("Syötä rahaa, aseta panos ja paina \"Jaa\""
                    + "\n-näppäintä");
            this.käsi.nollaus();
            this.panosnappi.setEnabled(true);
            this.rahanappi.setEnabled(true);
            if (this.voitot.getRahat() + this.voitot.getVoitot() >= this.voitot.getPanos()) {
                this.jakonappi.setEnabled(true);
            } else {
                this.jakonappi.setEnabled(false);
                this.voitot.nollaaPanos();
                paivitaKaikki();
            }

        }

        if (this.pelivaihe == 1) {

            this.viesti.setText("Valitse kortit, jotka haluat pitää ja paina\n\"Jaa\"-näppäintä");
            for (JButton j : this.lukitusnapit) {
                j.setEnabled(true);
            }
            this.panosnappi.setEnabled(false);
            this.rahanappi.setEnabled(false);

        }

        if (this.pelivaihe == 2) {
            this.pelivaihe++;
            victory = this.voitot.tulikoVoittoa(this.käsi);
            if (victory != 0) {
                this.viesti.setText("Onneksi olkoon! Voitit " + (victory) / 100.0 + "0€!");
            } else {
                this.viesti.setText("Ei voittoa. Yritä uudelleen!");
            }
            paivitaKaikki();
            this.panosnappi.setVisible(false);
            this.rahanappi.setVisible(false);
            this.jakonappi.setVisible(false);
            for (JButton j : this.lukitusnapit) {
                j.setEnabled(false);
            }
            this.ok.setVisible(true);

        }
    }

    /**
     * Lisää rahaa -painikkeen kuuntelijan kutsuma metodi.
     */
    public void syotaRahaa() {
        this.voitot.lisaaRahaa();
        if (this.voitot.getRahat() + this.voitot.getVoitot() >= this.voitot.getPanos()) {
            this.jakonappi.setEnabled(true);
        }

        paivitaKaikki();
    }

    /**
     * Panos -painikkeen kuuntelijan kutsuma metodi.
     */
    public void panoksenKasvatus() {
        this.voitot.kasvataPanosta();
        if (this.voitot.getRahat() + this.voitot.getVoitot() >= this.voitot.getPanos()) {
            this.jakonappi.setEnabled(true);
        }
        paivitaKaikki();
    }

    /**
     * Lukituspainikkeiden kuuntelijoiden kutsuma metodi.
     * @param kortti Kuuntelija ottaa omasta indeksistään pelaajan kädestä vastaavan indeksin kortin ja antaa sen metodin käsiteltäväksi.
     */
    public void lukitus(int kortti) {
        if (this.käsi.getKortti(kortti).olenkoLukittu()) {
            this.käsi.avaaKortti(kortti);
            this.korttikentät.get(kortti).setBackground(Color.WHITE);
        } else {
            this.käsi.lukitseKortti(kortti);
            this.korttikentät.get(kortti).setBackground(Color.YELLOW);
        }
    }

    /**
     * Jaa -painikkeen kutsuma metodi. Toimii myös peliä eteenpäin kuljettavana metodina, sillä se siirtää pelivaihetta yhden eteenpäin.
     */
    public void uusKasi() {
        this.pelivaihe++;
        if (this.pelivaihe != 2) {
            if (this.voitot.getRahat() >= this.voitot.getPanos()) {
                this.voitot.otaRahaa();
            } else {
                int erotus = this.voitot.getRahat() - this.voitot.getPanos();
                this.voitot.otaVoitoista(erotus);
            }
        }
        paivitaKaikki();
        this.jakaja.uusiKasi(käsi, pakka);
        for (int i = 0; i < 5; i++) {
            this.korttikentät.get(i).setBackground(Color.WHITE);
            this.korttikentät.get(i).setText(this.käsi.getKortti(i).toString());
            if (this.käsi.getKortti(i).haeMaa() < 2) {
                this.korttikentät.get(i).setForeground(Color.RED);
            } else {
                this.korttikentät.get(i).setForeground(Color.BLACK);
            }
        }
        pelaa();


    }

    /**
     * Päivittää käyttöliittymän tekstikentät ajan tasalle.
     */
    public void paivitaKaikki() {
        this.panos.setText(String.valueOf((this.voitot.getPanos()) / 100.0) + "0");
        this.rahat.setText(String.valueOf((this.voitot.getRahat()) / 100.0) + "0");
        this.voittorahat.setText(String.valueOf((this.voitot.getVoitot()) / 100.0) + "0");
        this.viimeisinVoitto.setText(String.valueOf((this.voitot.getViimeisinVoitto()) / 100.0) + "0");
        this.voittolista.setText("Kuningasvärisuora            " + (this.voitot.getPanos() * 50) / 100.0 + "0 € \n"
                + "Värisuora                    " + (this.voitot.getPanos() * 40) / 100.0 + "0 €" + "\n"
                + "Neloset                      " + (this.voitot.getPanos() * 20) / 100.0 + "0 € \n"
                + "Täyskäsi                     " + (this.voitot.getPanos() * 10) / 100.0 + "0 € \n"
                + "Väri                         " + (this.voitot.getPanos() * 5) / 100.0 + "0 € \n"
                + "Suora                        " + (this.voitot.getPanos() * 4) / 100.0 + "0 € \n"
                + "Kolmoset                     " + (this.voitot.getPanos() * 2) / 100.0 + "0 € \n"
                + "Kaksi paria                  " + (this.voitot.getPanos() * 2) / 100.0 + "0 €");
    }
}
