package lt.ocirama.labsystembackend.cli;

import lt.ocirama.labsystembackend.services.SystemService;
import lt.ocirama.labsystembackend.utils.HibernateUtils;


public class Client {

    public static void main(String[] args) {

        HibernateUtils.init(args[0], args[1], args[2]);
        SystemService ss = new SystemService();

        ss.MenuSelect();

    }
}
