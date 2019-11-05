package lt.ocirama.labsystembackend.repositories;

import lt.ocirama.labsystembackend.model.GeneralMoistureEntity;
import lt.ocirama.labsystembackend.model.SampleEntity;
import lt.ocirama.labsystembackend.model.TotalMoistureEntity;
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

public class GeneralMoistureRepository {

    Scanner sc = new Scanner(System.in);
    private final EntityManagerFactory entityManagerFactory;

    public GeneralMoistureRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
    public void GeneralMoistureLogGenerate() {
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

                List<GeneralMoistureEntity> list = new ArrayList<>();
                GeneralMoistureEntity gme;
                for (int j = 1; j <= 2; j++) {
                    gme = new GeneralMoistureEntity();
                    System.out.println("Bendrosios drėgmės svėrimas mėginiui : " + tray.getSample().getSampleId());
                    System.out.println("Skenuokite " + j + "-ojo induko barkodą mėginiui  " + tray.getSample().getSampleId());
                    indukas = sc.nextLine();
                    gme.setTray(tray);
                    list.add(gme);
                    System.out.println("Sverkite induką " + indukas);
                    gme.setJarId(indukas);
                    Double jarWeight = FileControllerService.sverimoPrograma();
                    gme.setJarWeight(jarWeight);
                    em.persist(gme);
                }
                for (int k = 0; k <= 1; k++) {
                    gme = list.get(k);
                    System.out.println("Įdėkitę " + gme.getTray().getSample().getSampleId() + " mėginį į " + tray.getSample().getSampleId() + " padėklą ir sverkite:");
                    Double trayWeight2 = FileControllerService.sverimoPrograma();
                    gme.setJarAndSampleWeightBefore(trayWeight2);
                    em.persist(gme);
                    //TotalMoistureExcel(tme.getTray(),tme, protocol);
                }
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
