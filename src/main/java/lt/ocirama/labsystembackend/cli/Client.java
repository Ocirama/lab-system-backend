package lt.ocirama.labsystembackend.cli;

import lt.ocirama.labsystembackend.model.OrderLogEntity;
import lt.ocirama.labsystembackend.repositories.*;
import lt.ocirama.labsystembackend.utils.HibernateUtils;

import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        HibernateUtils.init(args[0], args[1], args[2]);

        GenericRepository<OrderLogEntity> gr = new GenericRepository<>(OrderLogEntity.class, HibernateUtils.getSessionFactory());
        System.out.println(gr.findAll());
        OrderLogRepository olr = new OrderLogRepository(HibernateUtils.getSessionFactory());
        WeightLogRepository wlr = new WeightLogRepository(HibernateUtils.getSessionFactory());
        TrayRepository tr = new TrayRepository(HibernateUtils.getSessionFactory());
        TotalMoistureRepository tmr = new TotalMoistureRepository(HibernateUtils.getSessionFactory());

        //olr.OrderLogGenerate();
        //wlr.WeightLogGenerate();
        //tr.TrayAssign();
        tmr.TotalMoistureLogGenerate();

    }
}
