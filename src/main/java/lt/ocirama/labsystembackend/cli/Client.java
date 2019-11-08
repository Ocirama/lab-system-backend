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
        ReferenceTrayRepository rtr = new ReferenceTrayRepository(HibernateUtils.getSessionFactory());
        AshRepository ar = new AshRepository(HibernateUtils.getSessionFactory());

        //Užsakymo registravimas
        //olr.OrderLogGenerate();

        //Gauto mėginio masės svėrimas
        //wlr.WeightLogGenerate();

        //Padėklo priskyrimas mėginiui
        //tr.TrayAssign();

        //Visuminės drėgmės matavimas
        //tmr.TotalMoistureLogGenerate();
        //tmr.TotalMoistureSecondGenerate();

        //Pamatinio padėklo registravimas
        //rtr.ReferenceTrayLogGenerate();
        rtr.ReferenceTrayLogSecondGenerate();

        //Bendrosios drėgmės matavimas
        //gmr.GeneralMoistureLogGenerate();

        //Peleningumo matavimas
        //ar.AshLogGenerate();
    }
}
