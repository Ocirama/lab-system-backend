package lt.ocirama.labsystembackend.repositories;

import lt.ocirama.labsystembackend.model.GeneralMoistureEntity;
import lt.ocirama.labsystembackend.model.TrayEntity;
import lt.ocirama.labsystembackend.services.ExcelService;
import lt.ocirama.labsystembackend.services.FileControllerService;
import lt.ocirama.labsystembackend.services.UserInputService;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class GeneralMoistureRepository {

    private final EntityManagerFactory entityManagerFactory;

    public GeneralMoistureRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void GeneralMoistureLogGenerate() {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            String padeklas;
            int laikas;
            System.out.println("Prieš kiek dienų atliktas pirmas Visuminės drėgmės svėrimas ?");
            laikas = Integer.parseInt(UserInputService.NumberInput());
            for (int i = 1; i < 5000; i++) {
                System.out.println("Skenuokitę padėklą:");
                padeklas = UserInputService.NumberOrEndInput();
                if (!padeklas.equals("Baigta")) {
                    transaction.begin();
                    Session session = em.unwrap(Session.class);
                    Query query = session.createQuery("Select te from TrayEntity te where te.trayId=:tray AND te.date=current_date-:laikas ");
                    query.setParameter("tray", padeklas);
                    query.setParameter("laikas", laikas);
                    TrayEntity tray = (TrayEntity) query.getSingleResult();
                    String indukas;
                    List<GeneralMoistureEntity> list = new ArrayList<>();
                    GeneralMoistureEntity gme;
                    for (int j = 1; j <= 2; j++) {
                        gme = new GeneralMoistureEntity();
                        System.out.println("Bendrosios drėgmės svėrimas mėginiui : " + tray.getSample().getSampleId());
                        System.out.println("Skenuokite " + j + "-ojo induko barkodą mėginiui  " + tray.getSample().getSampleId());
                        indukas = UserInputService.NumberInput();
                        gme.setTray(tray);
                        list.add(gme);
                        System.out.println("Sverkite induką " + indukas);
                        gme.setJarId(indukas);
                        Double jarWeight = FileControllerService.sverimoPrograma("On");
                        gme.setJarWeight(jarWeight);
                        em.persist(gme);
                    }
                    for (int k = 0; k <= 1; k++) {
                        gme = list.get(k);
                        System.out.println("Įdėkitę " + gme.getTray().getSample().getSampleId() + " mėginį į " + gme.getJarId() + " induką ir sverkite:");
                        Double trayWeight2 = FileControllerService.sverimoPrograma("On");
                        gme.setJarAndSampleWeightBefore(trayWeight2);
                        em.persist(gme);
                        ExcelService.GeneralMoistureExcelUpdate(gme, gme.getTray().getSample().getOrder().getProtocolId(), 1);
                    }
                    transaction.commit();
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void GeneralMoistureSecondGenerate() {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            for (int i = 1; i < 5000; i++) {
                System.out.println("Skenuokitę induką:");
                String padeklas = UserInputService.NumberInput();
                if (!padeklas.equals("Baigta")) {
                    transaction.begin();
                    Session session = em.unwrap(Session.class);
                    Query query = session.createQuery("from GeneralMoistureEntity gme where gme.jarId=:padeklas AND gme.date=current_date-:laikas ");
                    query.setParameter("padeklas", padeklas);
                    query.setParameter("laikas", 0);
                    GeneralMoistureEntity gme = (GeneralMoistureEntity) query.getSingleResult();
                    System.out.println("Sverkite  induką po džiovinimo: ");
                    Double trayWeight = FileControllerService.sverimoPrograma("On");
                    gme.setJarAndSampleWeightAfter(trayWeight);
                    double x = FileControllerService.getRandomNumberInRange(0.0001, 0.0003);
                    gme.setJarAndSampleWeightAfterPlus(trayWeight - x);
                    em.persist(gme);
                    ExcelService.GeneralMoistureExcelUpdate(gme, gme.getTray().getSample().getOrder().getProtocolId(), 2);
                    transaction.commit();
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

