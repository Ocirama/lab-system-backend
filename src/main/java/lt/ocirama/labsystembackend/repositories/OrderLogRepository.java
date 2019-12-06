package lt.ocirama.labsystembackend.repositories;

import lt.ocirama.labsystembackend.model.OrderEntity;
import lt.ocirama.labsystembackend.model.SampleEntity;
import lt.ocirama.labsystembackend.services.ExcelService;
import lt.ocirama.labsystembackend.services.UserInputService;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
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
                System.out.println(">>>>> Registruoti naują protokolą: Taip/Ne <<<<<");
                if (UserInputService.YesOrNoInput().equals("Taip")) {
                    OrderEntity order = new OrderEntity();

                    System.out.println(">>>>> Užsakymo numeris: <<<<<");
                    String protokolas = UserInputService.NumberInput();
                    transaction.begin();
                    Session session = em.unwrap(Session.class);
                    Query query = session.createQuery("Select oe.protocolId from OrderEntity oe");
                    List<String> orders = query.getResultList();
                    if (orders.contains(protokolas)) {
                        System.out.println(">>>>> Toks protokolas jau užregistruotas <<<<<");
                        break;
                    } else {
                        order.setProtocolId(protokolas);

                        System.out.println(">>>>> Užsakovas: <<<<<");
                        order.setCustomer(UserInputService.TextInput());

                        System.out.println(">>>>> Užsakomi tyrimai: <<<<<");
                        String orderList = listOrders();
                        order.setTest(orderList);

                        System.out.println(">>>>> Kuro rūšis: <<<<<");
                        order.setSampleType(UserInputService.TextInput());

                        System.out.println(">>>>> Mėginių kiekis: <<<<<");
                        order.setOrderAmount(Integer.parseInt(UserInputService.NumberInput()));

                        System.out.println(">>>>> Mėginių Id: <<<<<");
                        List<SampleEntity> list = new ArrayList<>();
                        for (int j = 1; j <= order.getOrderAmount(); j++) {
                            SampleEntity se = new SampleEntity();
                            list.add(se);
                            se.setSampleId(UserInputService.BasicInput());
                            se.setOrder(order);
                        }
                        order.setSamples(list);
                        ExcelService.OrderLogExcelUpdate(order);
                        try {
                            em.persist(order);
                            transaction.commit();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            transaction.commit();
                        }
                    }
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String listOrders() {
        StringBuilder orderList = new StringBuilder();
        String tyrimas;
        do {
            tyrimas = UserInputService.BasicInput();
            orderList.append(tyrimas).append(", ");
        } while (!tyrimas.equals("Baigta"));
        orderList.delete(orderList.length() - 10, orderList.length() - 1);
        System.out.println(">>>>> " + orderList + " <<<<<");
        return orderList.toString();
    }
}







