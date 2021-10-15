package nl.hu.dp.hibernate.model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="product", schema = "public")
public class Product {
    @Id
    @Column(name="product_nummer")
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int product_nummer;
    private String naam;
    private String beschrijving;
    private double prijs;

    @ManyToMany(mappedBy = "producten", targetEntity = OVChipkaart.class)
    private List<OVChipkaart> ovchipkaarten= new ArrayList<>();

    public Product() {
    }

    public Product(int product_nummer, String naam, String beschrijving, double prijs) {
        this.product_nummer = product_nummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
        this.ovchipkaarten = ovchipkaarten;
    }

    public void AddOVChipkaart(OVChipkaart ovchipkaart) {
        if(!ovchipkaarten.contains(ovchipkaart)){
            ovchipkaarten.add(ovchipkaart);
        }
    }

    public void deleteOVChipkaart(OVChipkaart ovchipkaart) {
        if(ovchipkaarten.contains(ovchipkaart)){
            ovchipkaarten.remove(ovchipkaart);
        }
    }

    public List<OVChipkaart> getOvchipkaarten() {
        return ovchipkaarten;
    }

    public void setOvchipkaarten(List<OVChipkaart> ovchipkaarten) {
        this.ovchipkaarten = ovchipkaarten;
    }

    public int getProduct_nummer() {
        return product_nummer;
    }

    public void setProduct_nummer(int product_nummer) {
        this.product_nummer = product_nummer;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }

    public String toString() {
        String s = " Product [" + product_nummer + ": naam: " + naam + " beschrijving: " + beschrijving + " prijs: " + prijs + "] ";
        return s;
    }
}