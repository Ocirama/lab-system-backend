package lt.ocirama.labsystembackend.repositories;

import lt.ocirama.labsystembackend.model.SampleEntity;
import lt.ocirama.labsystembackend.model.TotalMoistureEntity;
import lt.ocirama.labsystembackend.model.TrayEntity;
import lt.ocirama.labsystembackend.model.TrayWeightEntity;
import lt.ocirama.labsystembackend.services.ExcelService;
import lt.ocirama.labsystembackend.services.FileControllerService;
import lt.ocirama.labsystembackend.services.UserInputService;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
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
                System.out.println(">>>>> Naujo protokolo visuminės drėgmės svėrimas: Taip/Ne <<<<<");
                if (UserInputService.YesOrNoInput().equals("Taip")) {
                    transaction.begin();
                    System.out.println(">>>>> Užsakymo numeris: <<<<<");
                    String protocol = UserInputService.NumberInput();
                    Session session = em.unwrap(Session.class);
                    Query query = session.createQuery("Select ol.samples from OrderEntity ol where ol.protocolId=:protocol");
                    query.setParameter("protocol", protocol);
                    List<SampleEntity> samples = query.getResultList();
                    for (SampleEntity sampleEntity : samples) {
                        String padeklas;
                        String sampleName = sampleEntity.getSampleId();
                        System.out.println(">>>>> Visuminės drėgmės svėrimas mėginiui : " + sampleName + " <<<<<");
                        List<TotalMoistureEntity> list = new ArrayList<>();

                        for (int j = 0; j <= 1; j++) {
                            TotalMoistureEntity tme = new TotalMoistureEntity();
                             TrayEntity te = new TrayEntity();
                            System.out.println(">>>>> Įdėkitę " + tme.getTray().getSample().getSampleId() + " mėginį į padėklą ir sverkite: <<<<<");
                            padeklas = UserInputService.NumberInput();
                            if (padeklas.equals("Kitas")){
                                break;
                            }
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
                            tme = list.get(j);
                            Double trayWeight2 = FileControllerService.sverimoPrograma("Off");
                            tme.setTrayAndSampleWeightBefore(trayWeight2);
                            em.merge(sampleEntity);
                            em.persist(tme);
                            ExcelService.TotalMoistureExcel(tme.getTray(), tme, protocol, 1, null, null);
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
            Date date =FileControllerService.dateInput();
            for (int i = 1; i < 5000; i++) {
                System.out.println(">>>>> Fiksuokite padėklo numerį ir svorį: <<<<<");
                padeklas = UserInputService.NumberOrEndInput();
                if (!padeklas.equals("Baigta")) {
                    transaction.begin();
                    Session session = em.unwrap(Session.class);
                    Query query = session.createQuery("Select te from TrayEntity te where te.trayId=:padeklas AND te.date=:current_date");
                    query.setParameter("padeklas", padeklas);
                    query.setParameter("current_date", date);

                    TrayEntity te = (TrayEntity) query.getSingleResult();
                    System.out.println(">>>>> Padėklo svoris po džiovinimo: <<<<<");
                    Double trayWeight = FileControllerService.sverimoPrograma("Off");
                    List<TotalMoistureEntity> tmeList = te.getTotalMoistureEntities();
                    for (TotalMoistureEntity tme : tmeList) {
                        tme.setTrayAndSampleWeightAfter(trayWeight);
                        double x = FileControllerService.getRandomNumberInRange(0.05, 0.30);
                        tme.setTrayAndSampleWeightAfterPlus(trayWeight + x);
                        em.persist(tme);
                        String protocol = tme.getTray().getSample().getOrder().getProtocolId();
                        ExcelService.TotalMoistureExcel(tme.getTray(), tme, protocol, 2, tme.getTray().getTrayId(), date);
                        if (te.getId() % 2 == 0) {
                            System.out.println(">>>>> Galite išpilti mėginį <<<<<");
                        } else if (te.getId() % 2 != 0) {
                            System.out.println(">>>>> Dėkite mėginį prie " + te.getSample().getOrder().getProtocolId() + " protokolo <<<<<");
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


}
