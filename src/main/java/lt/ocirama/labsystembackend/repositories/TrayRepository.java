package lt.ocirama.labsystembackend.repositories;

import lt.ocirama.labsystembackend.model.SampleEntity;
import lt.ocirama.labsystembackend.model.TrayEntity;
import lt.ocirama.labsystembackend.services.UserInputService;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;
import java.util.Scanner;

public class TrayRepository {

    private final EntityManagerFactory entityManagerFactory;

    public TrayRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void TrayAssign() {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        for (int i = 1; i < 5000; i++) {
            System.out.println("Naujo protokolo padėklų registravimas: Taip/Ne");
            if (UserInputService.YesOrNoInput().equals("Taip")) {
                transaction.begin();
                System.out.println("Užsakymo numeris ?");
                String protocol = UserInputService.TextInput();
                Session session = em.unwrap(Session.class);
                Query query = session.createQuery("Select ol.samples from OrderEntity ol where ol.protocolId=:protocol");
                query.setParameter("protocol", protocol);
                List<SampleEntity> samples = query.getResultList();

                for (SampleEntity sampleEntity : samples) {
                    String sample1 = sampleEntity.getSampleId();
                    System.out.println("Skenuokite padėklo barkodą mėginiui  " + sample1);
                    String padeklas = UserInputService.NumberInput();
                    TrayEntity tle = new TrayEntity();
                    tle.setTrayId(padeklas);
                    tle.setSample(sampleEntity);
                    em.merge(sampleEntity);
                    em.persist(tle);
                }
                transaction.commit();
            } else {
                break;
            }
        }
    }
}

