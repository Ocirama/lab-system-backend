package lt.ocirama.labsystembackend.cli;

import lt.ocirama.labsystembackend.services.MenuService;
import lt.ocirama.labsystembackend.utils.HibernateUtils;

import java.util.Scanner;


public class Client {

    public static void main(String[] args) {
        final Scanner sc = new Scanner(System.in);
        HibernateUtils.init(args[0], args[1], args[2]);
        MenuService ss = new MenuService();


         ss.MenuSelect();

    }
}


