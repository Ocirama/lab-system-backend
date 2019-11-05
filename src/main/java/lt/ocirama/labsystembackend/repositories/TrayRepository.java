package lt.ocirama.labsystembackend.repositories;

import lt.ocirama.labsystembackend.model.SampleEntity;
import lt.ocirama.labsystembackend.model.TrayEntity;
import lt.ocirama.labsystembackend.services.ScaleService;
import org.apache.poi.ss.usermodel.Row;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TrayRepository {

    Scanner sc = new Scanner(System.in);
    ScaleService ss = new ScaleService();
    private final EntityManagerFactory entityManagerFactory;

    public TrayRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void TrayAssign() {
        SampleEntity sample = new SampleEntity();
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        System.out.println("Protokolas ?");
        String protocol = sc.nextLine();
        Session session = em.unwrap(Session.class);
        Query query = session.createQuery("Select ol.samples from OrderEntity ol where ol.protocolId=:protocol");
        query.setParameter("protocol", protocol);
        List<SampleEntity> samples = query.getResultList();
        Row row1 = null;
        for (SampleEntity sampleEntity : samples) {
            String sample1 = sampleEntity.getSampleId();
            System.out.println("Skenuokite padėklo barkodą mėginiui  " + sample1);
            String padeklas = sc.nextLine();
            List<TrayEntity> list = new ArrayList<>();
            TrayEntity tle = new TrayEntity();
            list.add(tle);
            tle.setTrayId(padeklas);
            tle.setSample(sampleEntity);
            em.merge(sampleEntity);
            em.persist(tle);
        }
        transaction.commit();
    }
}

