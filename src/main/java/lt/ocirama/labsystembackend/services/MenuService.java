package lt.ocirama.labsystembackend.services;

import lt.ocirama.labsystembackend.model.OrderEntity;
import lt.ocirama.labsystembackend.repositories.*;
import lt.ocirama.labsystembackend.utils.HibernateUtils;

import java.util.Scanner;

public class MenuService {

    OrderLogRepository olr = new OrderLogRepository(HibernateUtils.getSessionFactory());
    WeightLogRepository wlr = new WeightLogRepository(HibernateUtils.getSessionFactory());
    TrayRepository tr = new TrayRepository(HibernateUtils.getSessionFactory());
    TotalMoistureRepository tmr = new TotalMoistureRepository(HibernateUtils.getSessionFactory());
    GeneralMoistureRepository gmr = new GeneralMoistureRepository(HibernateUtils.getSessionFactory());
    ReferenceTrayRepository rtr = new ReferenceTrayRepository(HibernateUtils.getSessionFactory());
    AshRepository ar = new AshRepository(HibernateUtils.getSessionFactory());
    TrayWeightRepository twr = new TrayWeightRepository(HibernateUtils.getSessionFactory());
    QualityControlRepository qcr = new QualityControlRepository(HibernateUtils.getSessionFactory());

    Scanner sc = new Scanner(System.in);

    public void MenuSelect() {
        int function;
        do {
            System.out.println(">>>>> Pasirinkite funkciją: <<<<<");
            System.out.println("---->  \033[1m1\033[0m - Užsakymo registravimas\n---->  \033[1m2\033[0m - Mėginių masės svėrimas\n---->  \033[1m3\033[0m - Padėklo priskyrimas mėginiui\n" +
                    "---->  \033[1m4\033[0m - Visuminės drėgmės matavimas\n---->  \033[1m5\033[0m - Pamatinio padėklo registravimas\n---->  \033[1m6\033[0m - Bendrosios drėgmės matavimas\n---->  \033[1m7\033[0m - Peleningumo matavimas\n" +
                    "---->  \033[1m8\033[0m - Antrasis dienos svėrimas\n---->  \033[1m9\033[0m - Padėklų svorio kalibracija\n----> \033[1m10\033[0m - Kokybės kontrolė\n----> \033[1m11\033[0m - Exit");

            function = sc.nextInt();
            switch (function) {
                case 1:
                    olr.OrderLogSave();
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
                    System.out.println(">>>>> Kurį bandymą atliekate? <<<<< \n----> \033[1m1\033[0m - Visumine Dregme\n----> \033[1m2\033[0m - Bendroji Dregme\n----> \033[1m3\033[0m - Peleningumas\n----> \033[1m4\033[0m - Pamatinis padeklas\n----> \033[1m5\033[0m - Kokybės kontrolė\n----> \033[1m6\033[0m - Exit");
                    int x = sc.nextInt();
                    if (x == 1) {
                        tmr.TotalMoistureSecondGenerate();
                    } else if (x == 2) {
                        gmr.GeneralMoistureSecondGenerate();
                    } else if (x == 3) {
                        ar.AshSecondGenerate();
                    } else if (x == 4) {
                        rtr.ReferenceTrayLogSecondGenerate();
                    } else if (x == 5) {
                        qcr.QualityControlLogSecondGenerate();
                    } else {
                        break;
                    }
                    break;
                case 9:
                    System.out.println(">>>>> Ką norite daryti? <<<<<\n----> \033[1m1\033[0m - Padėklų svorio priskyrimas\n----> \033[1m2\033[0m - Padėklų svorio kalibracija\n----> \033[1m3\033[0m - Exit");
                    int y = sc.nextInt();
                    if (y == 1) {
                        twr.TrayWeightAsign();
                    } else if (y == 2) {
                        twr.TrayWeightCalibrate();
                    } else {
                        break;
                    }
                    break;
                case 10:
                    qcr.QualityControlLogGenerate();
                    break;
            }

        } while (function != 11);
    }
}
