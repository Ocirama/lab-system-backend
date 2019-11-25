package lt.ocirama.labsystembackend.repositories;

import lt.ocirama.labsystembackend.model.SampleEntity;
import lt.ocirama.labsystembackend.model.TotalMoistureEntity;
import lt.ocirama.labsystembackend.model.TrayEntity;
import lt.ocirama.labsystembackend.model.TrayWeightEntity;
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

public class TotalMoistureRepository {


    private final EntityManagerFactory entityManagerFactory;

    public TotalMoistureRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void TotalMoistureLogGenerate() {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            for (int i = 1; i < 5000; i++) {
                System.out.println("Naujo protokolo visuminės drėgmės svėrimas: Taip/Ne");
                if (UserInputService.YesOrNoInput().equals("Taip")) {
                    transaction.begin();
                    System.out.println("Užsakymo numeris ?");
                    String protocol = UserInputService.NumberInput();
                    Session session = em.unwrap(Session.class);
                    Query query = session.createQuery("Select ol.samples from OrderEntity ol where ol.protocolId=:protocol");
                    query.setParameter("protocol", protocol);
                    List<SampleEntity> samples = query.getResultList();
                    for (SampleEntity sampleEntity : samples) {
                        String padeklas;
                        String sampleName = sampleEntity.getSampleId();
                        System.out.println("Visuminės drėgmės svėrimas mėginiui : " + sampleName);
                        List<TotalMoistureEntity> list = new ArrayList<>();
                        TotalMoistureEntity tme;
                        TrayEntity te;
                        for (int j = 1; j <= 2; j++) {
                            tme = new TotalMoistureEntity();
                            te = new TrayEntity();
                            System.out.println("Skenuokite " + j + "-ojo padėklo barkodą mėginiui  " + sampleName);
                            padeklas = UserInputService.NumberInput();
                            te.setSample(sampleEntity);
                            te.setTrayId(padeklas);

                            list.add(tme);
                            tme.setTray(te);

                            session = em.unwrap(Session.class);
                            query = session.createQuery("Select twe from TrayWeightEntity twe where twe.trayId=:padeklas");
                            query.setParameter("padeklas", padeklas);
                            TrayWeightEntity twe = (TrayWeightEntity) query.getSingleResult();
                            tme.setTrayWeight(twe.getTrayWeight());
                            em.persist(te);
                            em.persist(tme);
                        }
                        for (int k = 0; k <= 1; k++) {
                            tme = list.get(k);
                            System.out.println("Įdėkitę " + tme.getTray().getSample().getSampleId() + " mėginį į " + tme.getTray().getTrayId() + " padėklą ir sverkite:");
                            Double trayWeight2 = FileControllerService.sverimoPrograma("Off");
                            tme.setTrayAndSampleWeightBefore(trayWeight2);
                            em.merge(sampleEntity);
                            em.persist(tme);
                            TotalMoistureExcel(tme.getTray(), tme, protocol, 1, null);
                        }
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

    public void TotalMoistureSecondGenerate() {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            String padeklas;
            int laikas;
            System.out.println("Prieš kiek dienų atliktas pirmas Visuminės drėgmės svėrimas ?");
            laikas = Integer.parseInt(UserInputService.NumberInput());
            for (int i = 1; i < 5000; i++) {
                System.out.println("Fiksuokite padėklo numerį ir svorį:");
                padeklas = UserInputService.NumberInput();
                if (!padeklas.equals("Baigta")) {
                    transaction.begin();
                    Session session = em.unwrap(Session.class);
                    Query query = session.createQuery("Select te from TrayEntity te where te.trayId=:padeklas AND te.date=current_date-:laikas");
                    query.setParameter("padeklas", padeklas);
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    query.setParameter("laikas", laikas);

                    TrayEntity te = (TrayEntity) query.getSingleResult();
                    System.out.println("Padėklo svoris po džiovinimo: ");
                    Double trayWeight = FileControllerService.sverimoPrograma("Off");
                    List<TotalMoistureEntity> tmeList = te.getTotalMoistureEntities();
                    for (TotalMoistureEntity tme : tmeList) {
                        tme.setTrayAndSampleWeightAfter(trayWeight);
                        double x = FileControllerService.getRandomNumberInRange(0.05, 0.30);
                        tme.setTrayAndSampleWeightAfterPlus(trayWeight + x);
                        em.persist(tme);
                        String protocol = tme.getTray().getSample().getOrder().getProtocolId();
                        TotalMoistureExcel(tme.getTray(), tme, protocol, 2, tme.getTray().getTrayId());
                        if (te.getId() % 2 == 0) {
                            System.out.println("Galite išpilti mėginį");
                        } else if (te.getId() % 2 != 0) {
                            System.out.println("Dėkite mėginį prie " + te.getSample().getOrder().getProtocolId() + " protokolo");
                        }
                        transaction.commit();

                    }
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void TotalMoistureExcel(TrayEntity tray, TotalMoistureEntity tme, String protocol, int sverimoNumeris, String trayId) {
        XSSFSheet sheet;
        XSSFWorkbook workbook;
        String path = "C:\\Users\\lei12\\Desktop\\Output\\" + LocalDate.now() + " (VisumineDregme).xlsx";
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
            rowhead.createCell(2).setCellValue("Padėklo Nr.");
            rowhead.createCell(3).setCellValue("Tuščio padėklo masė, g");
            rowhead.createCell(4).setCellValue("Tuščio padėklo ir ėminio masė PRIEŠ bandymą, g");
            rowhead.createCell(5).setCellValue("Tuščio padėklo ir ėminio masė PO bandymo, g");
            rowhead.createCell(6).setCellValue("Tuščio padėklo ir ėminio masė PO bandymo+n val, g");
            rowhead.createCell(7).setCellValue("Data");
            Row row;
            if (sverimoNumeris == 1) {
                int sheetNumber = sheet.getLastRowNum() + 1;
                row = sheet.createRow(sheetNumber);
                row.createCell(0).setCellValue(protocol);
                row.createCell(1).setCellValue(tray.getSample().getSampleId());
                row.createCell(2).setCellValue(tray.getTrayId());
                row.createCell(3).setCellValue(tme.getTrayWeight());
                row.createCell(4).setCellValue(tme.getTrayAndSampleWeightBefore());
            } else if (sverimoNumeris == 2) {
                row = sheet.getRow(FileControllerService.findRow(workbook, trayId));
                row.createCell(5).setCellValue(tme.getTrayAndSampleWeightAfter());
                row.createCell(6).setCellValue(tme.getTrayAndSampleWeightAfterPlus());
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
