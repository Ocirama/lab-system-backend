package lt.ocirama.labsystembackend.repositories;

import com.fazecast.jSerialComm.SerialPort;
import lt.ocirama.labsystembackend.model.SampleLogEntity;
import lt.ocirama.labsystembackend.services.ScaleService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.io.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class WeightLogRepository {

    Scanner sc = new Scanner(System.in);
    ScaleService ss = new ScaleService();
    private final EntityManagerFactory entityManagerFactory;

    public WeightLogRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void WeightLogGenerate() {
        SampleLogEntity sample = new SampleLogEntity();
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            System.out.println("Protokolas ?");
            String protocol = sc.nextLine();


            Session session = em.unwrap(Session.class);
            //select o from orderentitiy o where o.protocol = protocol
            Query query = session.createQuery("Select ol.samples from OrderLogEntity ol where ol.protocolId=:protocol");
            query.setParameter("protocol", protocol);
            List<SampleLogEntity> samples = query.getResultList();
            for (SampleLogEntity sampleLogEntity : samples) {
                System.out.println("Sverkite mėginį : " + sampleLogEntity.getSampleId());
                SerialPort serialPort = ss.SvarstykliuJungtis();
                sampleLogEntity.setSampleWeight(ss.Pasverti(serialPort));
                //sampleLogEntity.setSampleWeight(sc.nextDouble());
                ss.ClosePort(serialPort);
                em.merge(sampleLogEntity);

            }
            transaction.commit();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createExcel() {
        XSSFSheet sheet;
        XSSFWorkbook workbook;
        String path = "C:\\Users\\lei12\\Desktop\\Output\\" + LocalDate.now() + "(Svoriai).xlsx";
        File file = new File(path);
        if (file.exists()) {
            FileInputStream fsip = new FileInputStream(path);
            workbook = new XSSFWorkbook(fsip);
            sheet = workbook.createSheet(protocol);
        } else {
            workbook = new XSSFWorkbook();
            sheet = workbook.createSheet(protocol);
        }
        Row rowhead = sheet.createRow(0);
        rowhead.createCell(0).setCellValue("Mėginio Nr.");
        rowhead.createCell(1).setCellValue("Svoris, g");
        rowhead.createCell(2).setCellValue("Data");
        Row row1 = null;
        for (int i = 1; i <= samples.size(); i++) {
            row1 = sheet.createRow(i);
            row1.createCell(0).setCellValue(sampleLogEntity.getSampleId());
            row1.createCell(1).setCellValue(sampleLogEntity.getSampleWeight());

        }
        row1.createCell(2).setCellValue(String.valueOf(LocalDate.now()));
        FileOutputStream fileOut = new FileOutputStream(path);
        workbook.write(fileOut);
        fileOut.flush();
        fileOut.close();
    }
}


