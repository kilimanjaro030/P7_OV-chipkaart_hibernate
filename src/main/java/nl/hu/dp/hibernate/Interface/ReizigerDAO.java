package nl.hu.dp.hibernate.Interface;

import nl.hu.dp.hibernate.model.Reiziger;

import java.sql.Date;
import java.util.List;

public interface ReizigerDAO {
    public void save(Reiziger reiziger);
    public void update(Reiziger reiziger);
    public void delete(Reiziger reiziger);
    public Reiziger findByid(int id);
    public List<Reiziger> findByGbdatum(Date datum);
    public List<Reiziger> findAll();
}