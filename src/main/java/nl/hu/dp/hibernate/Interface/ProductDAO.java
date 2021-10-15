package nl.hu.dp.hibernate.Interface;

import nl.hu.dp.hibernate.model.OVChipkaart;
import nl.hu.dp.hibernate.model.Product;

import java.util.List;

public interface ProductDAO {
    public void save(Product product);
    public void update(Product product);
    public void delete(Product product);
    public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart);
    public List<Product> findAll();
    public Product findById(int id);
}
