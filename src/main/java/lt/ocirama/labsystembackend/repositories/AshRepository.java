package lt.ocirama.labsystembackend.repositories;

import lt.ocirama.labsystembackend.model.AshEntity;
import lt.ocirama.labsystembackend.model.GeneralMoistureEntity;
import lt.ocirama.labsystembackend.model.TrayEntity;
import lt.ocirama.labsystembackend.services.FileControllerService;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AshRepository {

    Scanner sc = new Scanner(System.in);
    private final EntityManagerFactory entityManagerFactory;

    public AshRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }


    public void AshLogGenerate() {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            //for (int i = 1;i<50;i++){
            System.out.println("Skenuokite padėklą:");

            String trayId = sc.nextLine();
            Session session = em.unwrap(Session.class);
            Query query = session.createQuery("Select te from TrayEntity te where te.trayId=:tray");
            query.setParameter("tray", trayId);
            TrayEntity tray = (TrayEntity) query.getSingleResult();
            String indukas;

            List<AshEntity> list = new ArrayList<>();
            AshEntity ae;
            for (int j = 1; j <= 2; j++) {
                ae = new AshEntity();
                System.out.println("Peleningumo svėrimas mėginiui : " + tray.getSample().getSampleId());
                System.out.println("Skenuokite " + j + "-ojo induko barkodą mėginiui  " + tray.getSample().getSampleId());
                indukas = sc.nextLine();
                ae.setTray(tray);
                list.add(ae);
                System.out.println("Sverkite induką " + indukas);
                ae.setDishId(indukas);
                Double dishWeight = FileControllerService.sverimoPrograma();
                ae.setDishWeight(dishWeight);
                em.persist(ae);
            }
            for (int k = 0; k <= 1; k++) {
                ae = list.get(k);
                System.out.println("Įdėkitę " + ae.getTray().getSample().getSampleId() + " mėginį į " + tray.getSample().getSampleId() + " induką ir sverkite:");
                Double trayWeight2 = FileControllerService.sverimoPrograma();
                ae.setDishAndSampleWeightBefore(trayWeight2);
                em.persist(ae);
                //TotalMoistureExcel(tme.getTray(),tme, protocol);
            }
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

