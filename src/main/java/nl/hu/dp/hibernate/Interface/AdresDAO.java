package nl.hu.dp.hibernate.Interface;

import nl.hu.dp.hibernate.model.Adres;
import nl.hu.dp.hibernate.model.Reiziger;

import java.util.List;

public interface AdresDAO {
    public void save(Adres adres);
    public void update(Adres adres);
    public void delete(Adres adres);
    public Adres findByReiziger(Reiziger reiziger);
    public List<Adres> findAll();
}
