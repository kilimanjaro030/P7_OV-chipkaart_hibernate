package nl.hu.dp.hibernate.Interface;

import nl.hu.dp.hibernate.model.OVChipkaart;
import nl.hu.dp.hibernate.model.Product;
import nl.hu.dp.hibernate.model.Reiziger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOHibernate implements OVChipkaartDAO {
    private SessionFactory factory;

    public OVChipkaartDAOHibernate(SessionFactory factory){
        this.factory = factory;
    }

    public void save(OVChipkaart ovChipkaart){
        Transaction transaction = null;
        try(Session session = factory.openSession()){
            transaction = session.beginTransaction();
            session.save(ovChipkaart);
            transaction.commit();
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
        }
    }

    public void update(OVChipkaart ovChipkaart){
        Transaction transaction = null;
        try(Session session = factory.openSession()){
            transaction = session.beginTransaction();
            session.update(ovChipkaart);
            transaction.commit();
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
        }

    }

    public void delete(OVChipkaart ovChipkaart){
        Transaction transaction = null;
        try(Session session = factory.openSession()){
            transaction = session.beginTransaction();
            session.delete(ovChipkaart);
            transaction.commit();
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
        }
    }

    public List<OVChipkaart> findByReiziger(Reiziger reiziger){
        Transaction transaction = null;
        List<OVChipkaart> ovChipkaarten = new ArrayList<>();
        try(Session session = factory.openSession()){
            transaction = session.beginTransaction();
            ovChipkaarten = session.createQuery("from ov_chipkaart o WHERE o.reiziger.reiziger_id = '" +
                    reiziger.getId() + "'",OVChipkaart.class).getResultList();
            transaction.commit();
        }
        return ovChipkaarten;
    }
    public List<OVChipkaart> findAll(){
        Transaction transaction = null;
        List<OVChipkaart> ovChipkaarten = new ArrayList<>();
        try(Session session = factory.openSession()){
            transaction = session.beginTransaction();
            ovChipkaarten = session.createQuery("SELECT o from ov_chipkaart o", OVChipkaart.class).getResultList();
            transaction.commit();
        }
        return ovChipkaarten;

    }

    public OVChipkaart findByid(int id){
        Transaction transaction = null;
        OVChipkaart ovChipkaart = null;
        try(Session session = factory.openSession()){
            transaction = session.beginTransaction();
            ovChipkaart = (OVChipkaart) session.get(OVChipkaart.class,id);
            transaction.commit();
        }
        return ovChipkaart;
    }

    public List<OVChipkaart> findByProduct(Product product){
        Transaction transaction = null;
        List<OVChipkaart> ovChipkaarten = new ArrayList<>();
        try(Session session = factory.openSession()){
            transaction = session.beginTransaction();
            ovChipkaarten = session.createQuery("from ov_chipkaart  WHERE product.product_nummer = '" +
                    product.getProduct_nummer() + "'",OVChipkaart.class).getResultList();
            transaction.commit();
        }
        return ovChipkaarten;
    }
}
