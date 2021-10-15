package nl.hu.dp.hibernate.Interface;

import nl.hu.dp.hibernate.model.Adres;
import nl.hu.dp.hibernate.model.Reiziger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class AdresDAOHibernate implements AdresDAO {
    private SessionFactory factory;

    public AdresDAOHibernate(SessionFactory factory){
        this.factory = factory;
    }

    public void save(Adres adres){
        Session session = factory.openSession();
        session.beginTransaction();
        session.save(adres);
        session.getTransaction().commit();
    }

    public void delete(Adres adres){
        Transaction transaction = null;
        try(Session session = factory.openSession()){
            transaction = session.beginTransaction();
            session.delete(adres);
            transaction.commit();
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
        }
    }

    public void update(Adres adres){
        Transaction transaction = null;
        try(Session session = factory.openSession()){
            transaction = session.beginTransaction();
            session.update(adres);
            transaction.commit();
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
        }

    }

    public List<Adres> findAll(){
        Transaction transaction = null;
        List<Adres> adressen = new ArrayList<>();
        try(Session session = factory.openSession()){
            transaction = session.beginTransaction();
            adressen = session.createQuery("SELECT a from Adres a", Adres.class).getResultList();
            transaction.commit();
        }
        return adressen;

    }

    public Adres findByReiziger(Reiziger reiziger){
        Transaction transaction = null;
        Adres adres = null;
        try(Session session = factory.openSession()){
            transaction = session.beginTransaction();
            adres = session.get(Adres.class, reiziger.getId());
            transaction.commit();
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
        }
        return adres;

    }
}
