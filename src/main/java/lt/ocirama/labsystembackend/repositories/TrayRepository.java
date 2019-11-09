package lt.ocirama.labsystembackend.repositories;

import lt.ocirama.labsystembackend.model.SampleLogEntity;
import lt.ocirama.labsystembackend.model.TrayLogEntity;
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
        SampleLogEntity sample = new SampleLogEntity();
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        System.out.println("Protokolas ?");
        String protocol = sc.nextLine();
        Session session = em.unwrap(Session.class);
        Query query = session.createQuery("Select ol.samples from OrderLogEntity ol where ol.protocolId=:protocol");
        query.setParameter("protocol", protocol);
        List<SampleLogEntity> samples = query.getResultList();
        Row row1 = null;
        for (SampleLogEntity sampleLogEntity : samples) {
            String sample1 = sampleLogEntity.getSampleId();
            System.out.println("Skenuokite padėklo barkodą mėginiui  " + sample1);
            String padeklas = sc.nextLine();
            List<TrayLogEntity> list = new ArrayList<>();
            TrayLogEntity tle = new TrayLogEntity();
            list.add(tle);
            tle.setTrayId(padeklas);
            tle.setSample(sampleLogEntity);
            em.merge(sampleLogEntity);
            em.persist(tle);
        }
        transaction.commit();
    }
}

