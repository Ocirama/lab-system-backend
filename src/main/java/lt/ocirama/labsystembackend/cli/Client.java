package lt.ocirama.labsystembackend.cli;

import lt.ocirama.labsystembackend.repositories.OrderLogRepository;
import lt.ocirama.labsystembackend.repositories.WeightLogRepository;
import lt.ocirama.labsystembackend.utils.HibernateUtils;

import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        HibernateUtils.init(args[0], args[1], args[2]);
        OrderLogRepository olr = new OrderLogRepository(HibernateUtils.getSessionFactory());
        WeightLogRepository wlr = new WeightLogRepository(HibernateUtils.getSessionFactory());

        olr.OrderLogGenerate();
        //wlr.WeightLogGenerate();
        //wlr.findAll();
    }
}
