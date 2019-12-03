package lt.ocirama.labsystembackend.repositories;

import lt.ocirama.labsystembackend.model.SampleEntity;
import lt.ocirama.labsystembackend.services.ExcelService;
import lt.ocirama.labsystembackend.services.FileControllerService;
import lt.ocirama.labsystembackend.services.UserInputService;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class WeightLogRepository {
    private final EntityManagerFactory entityManagerFactory;

    public WeightLogRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void WeightLogGenerate() {

        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            for (int i = 1; i < 5000; i++) {
                System.out.println("Naujo protokolo svėrimas: Taip/Ne");
                if (UserInputService.YesOrNoInput().equals("Taip")) {
                    transaction.begin();
                    System.out.println("Užsakymo numeris ?");
                    String protocol = UserInputService.NumberInput();
                    Session session = em.unwrap(Session.class);
                    Query query = session.createQuery("Select ol.samples from OrderEntity ol where ol.protocolId=:protocol");
                    query.setParameter("protocol", protocol);
                    List<SampleEntity> samples = query.getResultList();
                    for (SampleEntity sampleEntity : samples) {
                        System.out.println("Sverkite mėginį : " + sampleEntity.getSampleId());
                        if (sampleEntity.getSampleWeight() != 0) {
                            System.out.println("Mėginys jau pasvertas. Ar norite papildyti reikšmę ? TAIP/NE ");
                            if (UserInputService.YesOrNoInput().equals("Taip")) {
                                Double sampleWeight = FileControllerService.sverimoPrograma("On");
                                sampleEntity.setSampleWeight(sampleWeight);
                            } else {
                                continue;
                            }
                        } else {
                            Double sampleWeight = FileControllerService.sverimoPrograma("On");
                            sampleEntity.setSampleWeight(sampleWeight);
                        }
                        em.merge(sampleEntity);
                        ExcelService.WeightLogExcelUpdate(sampleEntity, protocol);
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


}


