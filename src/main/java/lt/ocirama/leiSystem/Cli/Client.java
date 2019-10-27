package lt.ocirama.leiSystem.Cli;

import lt.ocirama.leiSystem.Repositories.OrderLogRepository;
import lt.ocirama.leiSystem.Repositories.WeightLogRepository;
import lt.ocirama.leiSystem.Utils.HibernateUtils;

import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        HibernateUtils.init(args[0], args[1], args[2]);
        OrderLogRepository olr = new OrderLogRepository(HibernateUtils.getSessionFactory());
        WeightLogRepository wlr = new WeightLogRepository(HibernateUtils.getSessionFactory());

        //olr.OrderLogGenerate();
        wlr.save(wlr.WeightLogGenerate());
        //wlr.findAll();
        }
    }
