package nl.hu.dp.hibernate.main;

import nl.hu.dp.hibernate.Interface.*;
import nl.hu.dp.hibernate.model.Adres;
import nl.hu.dp.hibernate.model.OVChipkaart;
import nl.hu.dp.hibernate.model.Product;
import nl.hu.dp.hibernate.model.Reiziger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.xml.bind.SchemaOutputResolver;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Testklasse - deze klasse test alle andere klassen in deze package.
 *
 * System.out.println() is alleen in deze klasse toegestaan (behalve voor exceptions).
 *
 * @author tijmen.muller@hu.nl
 */

public class Main {
    // Creëer een factory voor Hibernate sessions.
    private static final SessionFactory factory;

    static {
        try {
            // Create a Hibernate session factory
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Retouneer een Hibernate session.
     *
     * @return Hibernate session
     * @throws HibernateException
     */
    private static Session getSession() throws HibernateException {
        return factory.openSession();
    }

    public static void main(String[] args) throws SQLException {
        System.out.println("start_test");
        ReizigerDAO reizigerDAO = new ReizigerDAOHibernate(factory);
        AdresDAO adresDAO = new AdresDAOHibernate(factory);
        OVChipkaartDAO ovchipkaartDAO = new OVChipkaartDAOHibernate(factory);
        ProductDAO productDAO = new ProductDAOHibernate(factory);

        testDAOHibernate(reizigerDAO,adresDAO);
        testfindbyID(reizigerDAO);
        testDAOHibernate2();
        testReizigerDAO(reizigerDAO);
        testAdresDAO(adresDAO,reizigerDAO);
        testOVChipkaartDAO(ovchipkaartDAO,reizigerDAO);
        testProductDAO(productDAO,ovchipkaartDAO);
        System.out.println("end_test");

        System.out.println("start_test_fetch");

        testDAOHibernate(reizigerDAO,adresDAO);
        testFetchAll();
        System.out.println("end_test");
    }

    /**
     * P6. Haal alle (geannoteerde) entiteiten uit de database.
     */
    private static void testFetchAll() {
        Session session = getSession();
        try {
            Metamodel metamodel = session.getSessionFactory().getMetamodel();
            for (EntityType<?> entityType : metamodel.getEntities()) {
                Query query = session.createQuery("from " + entityType.getName());

                System.out.println("[Test] Alle objecten van type " + entityType.getName() + " uit database:");
                for (Object o : query.list()) {
                    System.out.println("  " + o);
                }
                System.out.println();
            }
        } finally {
            session.close();
        }
    }
    private static void testDAOHibernate(ReizigerDAO rdao, AdresDAO adao) {
        Reiziger reiziger = new Reiziger();
        reiziger.setId(80);
        reiziger.setAchternaam("Jensen");
        reiziger.setVoorletters("Robert");
        reiziger.setTussenvoegsel(null);
        reiziger.setGeboortedatum(java.sql.Date.valueOf(LocalDate.now()));

        Adres adres = new Adres();
        adres.setId(400);
        adres.setHuisnummer("31");
        adres.setPostcode("6969AQ");
        adres.setWoonplaats("Utrecht");
        adres.setStraat("hanssenstraat");
        adres.setReiziger(reiziger);

        reiziger.setAdres(adres);

        rdao.save(reiziger);
        adao.save(adres);
        adao.delete(adres);
        rdao.delete(reiziger);
    }

    private static void testfindbyID(ReizigerDAO reizigerDAO) {
        Reiziger reiziger = reizigerDAO.findByid(20);
        System.out.println(reiziger);
    }

    private static void testDAOHibernate2() {
        Session session = getSession();

        Reiziger reiziger = new Reiziger();
        reiziger.setId(31);
        reiziger.setAchternaam("Pieter");
        reiziger.setTussenvoegsel(null);
        reiziger.setVoorletters("K");
        reiziger.setGeboortedatum(java.sql.Date.valueOf(LocalDate.now()));

        Adres adres = new Adres();
        adres.setId(5000);
        adres.setHuisnummer("31");
        adres.setPostcode("7080");
        adres.setStraat("Rickastleystraat");
        adres.setWoonplaats("Utrecht");
        adres.setReiziger(reiziger);

        reiziger.setAdres(adres);

        session.beginTransaction();
        session.save(reiziger);
        session.save(adres);
        System.out.println(session.get(Reiziger.class, 31));
        session.delete(adres);
        session.delete(reiziger);
        session.getTransaction().commit();
    }
    private static void testReizigerDAO(ReizigerDAO rdao){
        System.out.println("\n---------- Test ReizigerDAO -------------");
        System.out.println();

        // Haal alle reizigers op uit de database
        System.out.println("\n---------- Haal alle reizigers op uit de database -------------");
        System.out.println();
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        System.out.println("\n---------- Maak een nieuwe reiziger aan en persisteer deze in de database -------------");
        System.out.println();
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(90, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // verwijder reiziger
        System.out.println("\n---------- verwijder reiziger -------------");
        System.out.println();

        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.delete(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // update reiziger
        System.out.println("\n---------- update reiziger -------------");
        System.out.println();
        rdao.update(new Reiziger(90, "M", "", "Boers", java.sql.Date.valueOf(gbdatum)));
        System.out.println();
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        // findbyid reiziger
        System.out.println("\n---------- findbyid reiziger -------------");
        System.out.println();
        System.out.println(rdao.findByid(6));

        // findByGbdatum gbdatum
        System.out.println("\n---------- findByGbdatum reiziger -------------");
        System.out.println();
        reizigers = rdao.findByGbdatum(Date.valueOf("1998-08-11"));

        for(Reiziger reiziger : reizigers){
            System.out.println(reiziger);
        }
    }

    private static void testAdresDAO(AdresDAO adao, ReizigerDAO rdao){
        // alle Adressen op uit de database
        System.out.println("\n---------- alle Adressen op uit de database -------------");
        System.out.println();
        List<Adres> adressen = adao.findAll();
        System.out.println("[Test] AdresDAO.findAll() geeft de volgende reizigers:");
        for (Adres a : adressen) {
            System.out.println(a);
        }
        System.out.println();

        // nieuwe adres aanmaken
        System.out.println("\n---------- nieuwe adres aanmaken -------------");
        System.out.println();

        String mehmetBD = "1999-11-10";
        Reiziger mehmet = new Reiziger(79, "M.A", "", "Bayram", java.sql.Date.valueOf(mehmetBD));
        rdao.save(mehmet);
        System.out.println("Eerst " + adressen.size() + " adressen, na adao.save() ");
        Adres padualaan = new Adres(12,"3535SB","31","Padualaan","Utrecht");

        padualaan.setReiziger(mehmet);
        adao.save(padualaan);
        adressen = adao.findAll();
        System.out.println(adressen.size() + " adressen.\n");

        System.out.println("\n---------- update adres -------------");
        System.out.println();
        System.out.println("Padualaan voor update:"  );
        adressen = adao.findAll();
        for(Adres adres : adressen){
            System.out.println(adres);
        }
        padualaan = new Adres(87, "3535SB", "69", "Padualaan", "Utrecht");
        adao.update(padualaan);
        System.out.println();
        System.out.println("Padualaan na update:");

        adressen = adao.findAll();

        for(Adres adres: adressen){
            System.out.println(adres);
        }

        System.out.println();

        System.out.println("\n---------- FindByReiziger -------------");
        System.out.println();
        String hansBD = "1945-08-01";
        Reiziger hans = new Reiziger(5,"H.","","Klaasen",java.sql.Date.valueOf(hansBD));
        Adres add = adao.findByReiziger(hans);
        System.out.println(add);

        System.out.println("\n---------- delete adres -------------");
        adressen = adao.findAll();
        System.out.println("Aantal adressen voor delete = " + adressen.size());
        adao.delete(padualaan);
        adressen = adao.findAll();
        System.out.println("Aantal adressen na delete = " + adressen.size());

        rdao.delete(mehmet);

        System.out.println();
    }

    private static void testOVChipkaartDAO(OVChipkaartDAO odao, ReizigerDAO rdao){

        System.out.println("\n---------- alle ovchipkaarten op uit de database -------------");
        System.out.println();
        List<OVChipkaart> ovchipkaarten = odao.findAll();

        for(OVChipkaart ovChipkaart : ovchipkaarten){
            System.out.println(ovChipkaart);
        }
        System.out.println("\n---------- nieuwe ovchipkaart aanmaken -------------");
        System.out.println();

        Reiziger mehmet = new Reiziger(79, "M.A", "", "Bayram", java.sql.Date.valueOf("1999-11-10"));
        rdao.save(mehmet);

        ovchipkaarten = odao.findAll();

        System.out.println("Eerst " + ovchipkaarten.size() + " ovchipkaarten, na odao.save() ");

        OVChipkaart ovchipkaart = new OVChipkaart(31691,java.sql.Date.valueOf("2031-12-31"),1,25);

        ovchipkaart.setReiziger(mehmet);
        odao.save(ovchipkaart);

        ovchipkaarten = odao.findAll();
        System.out.println(ovchipkaarten.size() + " ovchipkaarten.\n");

        System.out.println();


        System.out.println("\n---------- update adres -------------");
        ovchipkaarten = odao.findAll();
        System.out.println("OVChipkaart voor de update:");
        for(OVChipkaart ovChipkaart: ovchipkaarten){
            System.out.println(ovChipkaart);
        }

        ovchipkaart = new OVChipkaart(31691,java.sql.Date.valueOf("2031-12-31"),2,25);
        odao.update(ovchipkaart);
        System.out.println("OVChipkaart na update");
        ovchipkaarten = odao.findAll();
        for(OVChipkaart ovChipkaart: ovchipkaarten){
            System.out.println(ovChipkaart);
        }


        System.out.println();

        System.out.println("\n---------- FindByReiziger -------------");
        Reiziger reiziger = rdao.findByid(79);
        ovchipkaarten = odao.findByReiziger(reiziger);

        for(OVChipkaart ovChipkaart : ovchipkaarten){
            System.out.println(ovChipkaart);
        }
        System.out.println("\n---------- delete ovchipkaart -------------");

        ovchipkaarten = odao.findAll();
        System.out.println("Eerst " + ovchipkaarten.size() + " ovchipkaarten");
        odao.delete(ovchipkaart);

        ovchipkaarten = odao.findAll();

        System.out.println("Na delete " + ovchipkaarten.size() + "ovchipkaarten");
        rdao.delete(mehmet);

    }

    private static void testProductDAO(ProductDAO pdao, OVChipkaartDAO odao){
        System.out.println("\n---------- ALle producten ophalen van de database met findAll -------------");
        System.out.println();
        List<Product> producten = pdao.findAll();

        for(Product product : producten){
            System.out.println(product);
        }

        System.out.println();

        System.out.println("\n---------- Product toevoegen in de database -------------");
        System.out.println();

        producten = pdao.findAll();
        System.out.print("Aantal producten in de database voor het creeren van een product: " + producten.size());
        Product product = new Product(10, "StudentenOV","OV product voor studenten",00.00);

        OVChipkaart ovChipkaart = odao.findByid(79625);

        product.AddOVChipkaart(ovChipkaart);

        pdao.save(product);

        producten = pdao.findAll();
        System.out.println(producten.size() + " Producten\n");

        System.out.println();

        System.out.println("\n---------- Product aanpassen in de database -------------");
        System.out.println();

        System.out.println("Product voordat de aanpassing zich plaatsvond:");
        Product productnr10 = pdao.findById(10);
        System.out.println(productnr10);

        System.out.println();

        Product newProductnr10 = new Product(10,"StudentenOV","Nieuwe wettelijke regeling StudentenOV is vanaf nu per jaar 10 euro",10.00);
        pdao.update(newProductnr10);

        System.out.println("Product na de aanpassingen: ");
        newProductnr10 = pdao.findById(10);
        System.out.println(newProductnr10);

        System.out.println();

        System.out.println("\n---------- Product verwijderen van de database -------------");
        System.out.println();

        producten = pdao.findAll();

        System.out.println("Aantal producten in de database voor het verwijderen van een product: " + producten.size());

        product.deleteOVChipkaart(ovChipkaart);
        pdao.delete(product);

        producten = pdao.findAll();
        System.out.println(producten.size() + " OVChipkaarten\n");

        System.out.println();

        System.out.println("\n---------- selecteer producten die bij een ovchipkaart horen. -------------");
        System.out.println();
        System.out.println("functie findbyovchipkaart geeft de volgende product: ");

        OVChipkaart oneOVChipkaart = odao.findByid(90537);
        System.out.println(oneOVChipkaart);
        List<Product> products = pdao.findByOVChipkaart(oneOVChipkaart);

        for (Product p : products){
            System.out.println(p);
        }
        System.out.println();

        System.out.println("\n---------- selecteer ovchipkaarten met een product -------------");
        System.out.println();

        System.out.println("functie findbyproduct geeft de volgende product: ");

        Product oneProduct = pdao.findById(3);
        List<OVChipkaart> ovChipkaarten = odao.findByProduct(oneProduct);

        for(OVChipkaart ov : ovChipkaarten){
            System.out.println(ov);
        }
        System.out.println();
    }
}