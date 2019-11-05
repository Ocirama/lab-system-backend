package lt.ocirama.labsystembackend.cli;

import lt.ocirama.labsystembackend.model.OrderEntity;
import lt.ocirama.labsystembackend.repositories.*;
import lt.ocirama.labsystembackend.utils.HibernateUtils;


public class Client {

    public static void main(String[] args) {

        HibernateUtils.init(args[0], args[1], args[2]);

        GenericRepository<OrderEntity> gr = new GenericRepository<>(OrderEntity.class, HibernateUtils.getSessionFactory());
        System.out.println(gr.findAll());
        OrderLogRepository olr = new OrderLogRepository(HibernateUtils.getSessionFactory());
        WeightLogRepository wlr = new WeightLogRepository(HibernateUtils.getSessionFactory());
        TrayRepository tr = new TrayRepository(HibernateUtils.getSessionFactory());
        TotalMoistureRepository tmr = new TotalMoistureRepository(HibernateUtils.getSessionFactory());
        GeneralMoistureRepository gmr = new GeneralMoistureRepository(HibernateUtils.getSessionFactory());

        //olr.OrderLogGenerate();
        //wlr.WeightLogGenerate();
        //tr.TrayAssign();
        //tmr.TotalMoistureLogGenerate();
        gmr.GeneralMoistureLogGenerate();
    }
}
