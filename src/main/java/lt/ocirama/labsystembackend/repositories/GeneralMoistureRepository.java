package lt.ocirama.labsystembackend.repositories;

import lt.ocirama.labsystembackend.model.GeneralMoistureEntity;
import lt.ocirama.labsystembackend.model.TrayEntity;
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
                        System.out.println("Įdėkitę " + gme.getTray().getSample().getSampleId() + " mėginį į " + gme.getJarId() + " padėklą ir sverkite:");
                        Double trayWeight2 = FileControllerService.sverimoPrograma("On");
                        gme.setJarAndSampleWeightBefore(trayWeight2);
                        em.persist(gme);
                        GeneralMoistureExcelUpdate(gme, gme.getTray().getSample().getOrder().getProtocolId(), 1);
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
                    GeneralMoistureExcelUpdate(gme, gme.getTray().getSample().getOrder().getProtocolId(), 2);
                    transaction.commit();
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void GeneralMoistureExcelUpdate(GeneralMoistureEntity gme, String protocol, int sverimoNumeris) {

        XSSFSheet sheet;
        XSSFWorkbook workbook;
        String path = "C:\\Users\\lei12\\Desktop\\Output\\" + LocalDate.now() + " (BendrojiDregme).xlsx";
        File file = new File(path);
        try {
            if (file.exists()) {
                FileInputStream fsip = new FileInputStream(path);
                workbook = new XSSFWorkbook(fsip);
                if (workbook.getSheet(protocol) == null) {
                    sheet = workbook.createSheet(protocol);
                } else
                    sheet = workbook.getSheet(protocol);
            } else {
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet(protocol);
            }
            Row rowhead = sheet.createRow(0);
            rowhead.createCell(0).setCellValue("Užsakymo Nr.");
            rowhead.createCell(1).setCellValue("Mėginio Nr.");
            rowhead.createCell(2).setCellValue("Induko Nr.");
            rowhead.createCell(3).setCellValue("Tuščio induko masė, g");
            rowhead.createCell(4).setCellValue("Tuščio induko ir ėminio masė PRIEŠ bandymą, g");
            rowhead.createCell(5).setCellValue("Tuščio induko ir ėminio masė PO bandymo, g");
            rowhead.createCell(6).setCellValue("Tuščio induko ir ėminio masė PO bandymo+n val, g");
            rowhead.createCell(7).setCellValue("Data");
            Row row;
            if (sverimoNumeris == 1) {
                int sheetNumber = sheet.getLastRowNum() + 1;
                row = sheet.createRow(sheetNumber);
                row.createCell(0).setCellValue(protocol);
                row.createCell(1).setCellValue(gme.getTray().getSample().getSampleId());
                row.createCell(2).setCellValue(gme.getJarId());
                row.createCell(3).setCellValue(gme.getJarWeight());
                row.createCell(4).setCellValue(gme.getJarAndSampleWeightBefore());
            } else if (sverimoNumeris == 2) {
                row = sheet.getRow(FileControllerService.findRow(workbook, gme.getJarId()));
                row.createCell(5).setCellValue(gme.getJarAndSampleWeightAfter());
                row.createCell(6).setCellValue(gme.getJarAndSampleWeightAfterPlus());
            }

            FileOutputStream fileOut = new FileOutputStream(path);
            workbook.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

