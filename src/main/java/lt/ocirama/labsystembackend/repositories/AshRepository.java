package lt.ocirama.labsystembackend.repositories;

import lt.ocirama.labsystembackend.model.AshEntity;
import lt.ocirama.labsystembackend.model.TrayEntity;
import lt.ocirama.labsystembackend.services.ExcelService;
import lt.ocirama.labsystembackend.services.FileControllerService;
import lt.ocirama.labsystembackend.services.UserInputService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AshRepository {

    private final EntityManagerFactory entityManagerFactory;

    public AshRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void AshLogGenerate() {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        System.out.println("Prieš kiek dienų atliktas pirmas Visuminės drėgmės svėrimas ?");
        int  laikas = Integer.parseInt(UserInputService.NumberInput());
        try {
            String padeklas;
            for (int i = 1; i < 5000; i++) {
                System.out.println("Skenuokitę padėklą:");
                padeklas = UserInputService.NumberInput();
                if (!padeklas.equals("Baigta")) {
                    transaction.begin();
                    Session session = em.unwrap(Session.class);
                    Query query = session.createQuery("Select te from TrayEntity te where te.trayId=:tray AND te.date=current_date-:laikas ");
                    query.setParameter("tray", padeklas);
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    query.setParameter("laikas", laikas);
                    TrayEntity tray = (TrayEntity) query.getSingleResult();
                    String indukas;

                    List<AshEntity> list = new ArrayList<>();
                    AshEntity ae;
                    for (int j = 1; j <= 2; j++) {
                        ae = new AshEntity();

                        System.out.println("Peleningumo svėrimas mėginiui : " + tray.getSample().getSampleId());
                        System.out.println("Skenuokite " + j + "-ojo induko barkodą mėginiui  " + tray.getSample().getSampleId());
                        indukas = UserInputService.NumberInput();
                        ae.setTray(tray);
                        list.add(ae);
                        System.out.println("Sverkite induką " + indukas);
                        ae.setDishId(indukas);
                        Double dishWeight = FileControllerService.sverimoPrograma("On");
                        ae.setDishWeight(dishWeight);
                        em.persist(ae);
                    }
                    for (int k = 0; k <= 1; k++) {
                        ae = list.get(k);
                        System.out.println("Įdėkitę " + ae.getTray().getSample().getSampleId() + " mėginį į " + ae.getDishId() + " induką ir sverkite:");
                        Double trayWeight2 = FileControllerService.sverimoPrograma("On");
                        ae.setDishAndSampleWeightBefore(trayWeight2);
                        em.persist(ae);
                        ExcelService.AshExcelUpdate(ae, ae.getTray().getSample().getOrder().getProtocolId(), 1);
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

    public void AshSecondGenerate() {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            for (int i = 1; i < 5000; i++) {
                System.out.println("Skenuokitę induką:");
                String padeklas = UserInputService.NumberOrEndInput();
                if (!padeklas.equals("Baigta")) {
                    transaction.begin();
                    Session session = em.unwrap(Session.class);
                    Query query = session.createQuery("from AshEntity ae where ae.dishId=:padeklas AND ae.date=current_date-:laikas ");
                    query.setParameter("padeklas", padeklas);
                    query.setParameter("laikas", 0);
                    AshEntity ae = (AshEntity) query.getSingleResult();
                    System.out.println("Sverkite induką po džiovinimo: ");
                    Double trayWeight = FileControllerService.sverimoPrograma("On");
                    ae.setDishAndSampleWeightAfter(trayWeight);
                    em.persist(ae);
                   ExcelService.AshExcelUpdate(ae, ae.getTray().getSample().getOrder().getProtocolId(), 2);
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

