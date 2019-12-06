package lt.ocirama.labsystembackend.repositories;

import lt.ocirama.labsystembackend.model.ReferenceTrayEntity;
import lt.ocirama.labsystembackend.services.ExcelService;
import lt.ocirama.labsystembackend.services.FileControllerService;
import lt.ocirama.labsystembackend.services.UserInputService;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReferenceTrayRepository {

    private final EntityManagerFactory entityManagerFactory;

    public ReferenceTrayRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void ReferenceTrayLogGenerate() {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            System.out.println(">>>>> Fiskuokite padėklo numerį ir svorį <<<<<");
            String padeklas = UserInputService.NumberInput();
            ReferenceTrayEntity rte = new ReferenceTrayEntity();
            rte.setReferenceTrayId(padeklas);

            Double trayWeight = FileControllerService.sverimoPrograma("Off");
            rte.setReferenceTrayWeightBefore(trayWeight);
            em.persist(rte);
            transaction.commit();
            ExcelService.ReferenceTrayExcelUpdate(rte, 1,null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ReferenceTrayLogSecondGenerate() {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        Date date = FileControllerService.dateInput();
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        String  ndate = sdf2.format(date);
        try {
            String padeklas;
            transaction.begin();
            System.out.println(">>>>> Fiskuokite padėklo numerį ir svorį: <<<<<");
            padeklas = UserInputService.NumberInput();
            Session session = em.unwrap(Session.class);
            Query query = session.createQuery("from ReferenceTrayEntity  where referenceTrayId=:padeklas");
            query.setParameter("padeklas", padeklas);
            ReferenceTrayEntity rte = (ReferenceTrayEntity) query.getSingleResult();
            System.out.println(">>>>> Padėklo svoris po džiovinimo: <<<<<");
            Double trayWeight = FileControllerService.sverimoPrograma("Off");
            rte.setReferenceTrayWeightAfter(trayWeight);
            em.persist(rte);
            ExcelService.ReferenceTrayExcelUpdate(rte, 2, ndate);
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

