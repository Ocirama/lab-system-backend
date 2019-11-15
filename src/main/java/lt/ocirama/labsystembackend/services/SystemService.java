package lt.ocirama.labsystembackend.services;

import lt.ocirama.labsystembackend.model.OrderEntity;
import lt.ocirama.labsystembackend.repositories.*;
import lt.ocirama.labsystembackend.utils.HibernateUtils;

import java.util.Scanner;

public class SystemService {

    GenericRepository<OrderEntity> gr = new GenericRepository<>(OrderEntity.class, HibernateUtils.getSessionFactory());
    OrderLogRepository olr = new OrderLogRepository(HibernateUtils.getSessionFactory());
    WeightLogRepository wlr = new WeightLogRepository(HibernateUtils.getSessionFactory());
    TrayRepository tr = new TrayRepository(HibernateUtils.getSessionFactory());
    TotalMoistureRepository tmr = new TotalMoistureRepository(HibernateUtils.getSessionFactory());
    GeneralMoistureRepository gmr = new GeneralMoistureRepository(HibernateUtils.getSessionFactory());
    ReferenceTrayRepository rtr = new ReferenceTrayRepository(HibernateUtils.getSessionFactory());
    AshRepository ar = new AshRepository(HibernateUtils.getSessionFactory());
    Scanner sc = new Scanner(System.in);

    public void MenuSelect() {
        int function;
        do {
            System.out.println("Pasirinkite funkciją:");
            System.out.println("1 - Užsakymo registravimas\n2 - Mėginių masės svėrimas\n3 - Padėklo priskyrimas mėginiui\n" +
                    "4 - Visuminės drėgmės matavimas\n5 - Pamatinio padėklo registravimas\n6 - Bendrosios drėgmės matavimas\n7 - Peleningumo matavimas\n" +
                    "8 - Antrasis dienos svėrimas\n9 - Exit");

            function = sc.nextInt();
            switch (function) {
                case 1:
                    //olr.OrderLogGenerate();
                    break;
                case 2:
                    wlr.WeightLogGenerate();
                    break;
                case 3:
                    tr.TrayAssign();
                    break;
                case 4:
                    tmr.TotalMoistureLogGenerate();
                    break;
                case 5:
                    rtr.ReferenceTrayLogGenerate();
                    break;
                case 6:
                    gmr.GeneralMoistureLogGenerate();
                    break;
                case 7:
                    ar.AshLogGenerate();
                    break;
                case 8:
                    System.out.println("Kurį bandymą atliekate?\n1 - Visumine Dregme\n2 - Bendroji Dregme\n3 - Peleningumas\n4 - Pamatinis padeklas\n5 - Exit");
                    int x = sc.nextInt();
                    if (x == 1) {
                        tmr.TotalMoistureSecondGenerate();
                    } else if (x == 2) {
                        gmr.GeneralMoistureSecondGenerate();
                    } else if (x == 3) {
                        ar.AshSecondGenerate();
                    }else if (x==4){
                        rtr.ReferenceTrayLogSecondGenerate();
                    } else {
                        break;
                    }
            }

        } while (function != 9);
    }
}
