package lt.ocirama.labsystembackend.repositories;

import lt.ocirama.labsystembackend.model.OrderEntity;
import lt.ocirama.labsystembackend.model.SampleEntity;
import lt.ocirama.labsystembackend.services.ExcelService;
import lt.ocirama.labsystembackend.services.UserInputService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;

public class OrderLogRepository {
    private final EntityManagerFactory entityManagerFactory;

    public OrderLogRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void OrderLogSave() {
        try {

            EntityManager em = entityManagerFactory.createEntityManager();
            EntityTransaction transaction = em.getTransaction();

            for (int i = 1; i < 5000; i++) {
                System.out.println("Registruoti naują protokolą: Taip/Ne");
                if (UserInputService.YesOrNoInput().equals("Taip")) {
                    OrderEntity order = new OrderEntity();

                    System.out.println("Užsakymo numeris:");
                    order.setProtocolId(UserInputService.NumberInput());

                    System.out.println("Užsakovas:");
                    order.setCustomer(UserInputService.TextInput());

                    System.out.println("Užsakomi tyrimai:");
                    StringBuilder s = new StringBuilder();
                    String tyrimas;
                    do {
                        tyrimas = UserInputService.BasicInput();
                        s.append(tyrimas).append(", ");
                    } while (!tyrimas.equals("Baigta"));
                    s.delete(s.length() - 10, s.length() - 1);
                    System.out.println(s);
                    order.setTest(s.toString());

                    System.out.println("Kuro rūšis:");
                    order.setSampleType(UserInputService.TextInput());

                    System.out.println("Mėginių kiekis:");
                    order.setOrderAmount(Integer.parseInt(UserInputService.NumberInput()));

                    System.out.println("Mėginių Id:");
                    List<SampleEntity> list = new ArrayList<>();
                    for (int j = 1; j <= order.getOrderAmount(); j++) {
                        SampleEntity se = new SampleEntity();
                        list.add(se);
                        se.setSampleId(UserInputService.BasicInput());
                        se.setOrder(order);
                    }
                    order.setSamples(list);
                    ExcelService.OrderLogExcelUpdate(order);
                    transaction.begin();
                    try {
                        em.persist(order);
                        transaction.commit();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        transaction.commit();
                    }
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}







