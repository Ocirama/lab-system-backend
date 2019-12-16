package lt.ocirama.labsystembackend.repositories;

import lt.ocirama.labsystembackend.model.QualityControlEntity;
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

public class QualityControlRepository {

    private final EntityManagerFactory entityManagerFactory;

    public QualityControlRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void QualityControlLogGenerate() {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            System.out.println(">>>>> Kokiam tyrimui daroma kokybės kontrolė ? D/P <<<<<");
            String tyrimas = UserInputService.TextInput();

            List<QualityControlEntity> list = new ArrayList<>();
            QualityControlEntity qce;
            for (int j = 1; j <= 2; j++) {
                qce = new QualityControlEntity();
                list.add(qce);
                qce.setTestType(tyrimas);
                System.out.println(">>>>> Skenuokite induko numerį <<<<<");
                String indukas = UserInputService.NumberInput();
                qce.setQualityTrayId(indukas);
                System.out.println(">>>>> Sverkite induką <<<<<");
                Double trayWeight = FileControllerService.sverimoPrograma("On");
                qce.setQualityTrayWeight(trayWeight);
                em.persist(qce);
            }
            for (int k = 0; k <= 1; k++) {
                qce = list.get(k);
                System.out.println(">>>>> Sverkite induką ir mėginį <<<<<");
                Double trayWeight2 = FileControllerService.sverimoPrograma("On");
                qce.setQualityTrayWeightBefore(trayWeight2);
                em.persist(qce);
                ExcelService.QualityControlExcelUpdate(qce, 1, tyrimas);
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void QualityControlLogSecondGenerate() {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            String padeklas;
            for (int i = 1; i < 5000; i++) {
                System.out.println(">>>>> Skenuokite induko barkodą: <<<<<");
                padeklas = UserInputService.NumberOrEndInput();
                if (!padeklas.equals("Baigta")) {
                    transaction.begin();
                    Session session = em.unwrap(Session.class);
                    Query query = session.createQuery("select qce from QualityControlEntity qce  where qce.qualityTrayId=:padeklas and qce.date=current_date ");
                    query.setParameter("padeklas", padeklas);
                    QualityControlEntity qce = (QualityControlEntity) query.getSingleResult();
                    System.out.println(">>>>> Sverkite induką po džiovinimo: <<<<<");
                    Double trayWeight = FileControllerService.sverimoPrograma("On");
                    qce.setQualityTrayWeightAfter(trayWeight);
                    em.persist(qce);
                    ExcelService.QualityControlExcelUpdate(qce, 2, qce.getTestType());
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

