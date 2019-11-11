package lt.ocirama.labsystembackend.services;

import lt.ocirama.labsystembackend.model.OrderEntity;
import lt.ocirama.labsystembackend.repositories.*;
import lt.ocirama.labsystembackend.utils.HibernateUtils;

public class SystemService {

    GenericRepository<OrderEntity> gr = new GenericRepository<>(OrderEntity.class, HibernateUtils.getSessionFactory());

    OrderLogRepository olr = new OrderLogRepository(HibernateUtils.getSessionFactory());
    WeightLogRepository wlr = new WeightLogRepository(HibernateUtils.getSessionFactory());
    TrayRepository tr = new TrayRepository(HibernateUtils.getSessionFactory());
    TotalMoistureRepository tmr = new TotalMoistureRepository(HibernateUtils.getSessionFactory());
    GeneralMoistureRepository gmr = new GeneralMoistureRepository(HibernateUtils.getSessionFactory());
    ReferenceTrayRepository rtr = new ReferenceTrayRepository(HibernateUtils.getSessionFactory());
    AshRepository ar = new AshRepository(HibernateUtils.getSessionFactory());

    public void menu (){


    }
}
