package nl.hu.dp.hibernate.Interface;

import nl.hu.dp.hibernate.model.OVChipkaart;
import nl.hu.dp.hibernate.model.Product;
import nl.hu.dp.hibernate.model.Reiziger;

import java.util.List;

public interface OVChipkaartDAO {
    public void save(OVChipkaart ovChipkaart);
    public void update(OVChipkaart ovChipkaart);
    public void delete(OVChipkaart ovChipkaart);
    public List<OVChipkaart> findByReiziger(Reiziger reiziger);
    public List<OVChipkaart> findAll();
    public OVChipkaart findByid(int id);
    public List<OVChipkaart> findByProduct(Product product);
}