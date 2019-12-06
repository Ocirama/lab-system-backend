package lt.ocirama.labsystembackend.cli;

import com.sun.java_cup.internal.runtime.Scanner;
import lt.ocirama.labsystembackend.services.FileControllerService;
import lt.ocirama.labsystembackend.services.MenuService;
import lt.ocirama.labsystembackend.services.ScaleService;
import lt.ocirama.labsystembackend.utils.HibernateUtils;


public class Client {

    public static void main(String[] args) {
        //HibernateUtils.init(args[0], args[1], args[2]);
       // MenuService ms = new MenuService();
FileControllerService.dateInput();
       // ms.MenuSelect();
    }
}


