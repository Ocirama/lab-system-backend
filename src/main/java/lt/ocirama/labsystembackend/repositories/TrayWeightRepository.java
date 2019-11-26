package lt.ocirama.labsystembackend.repositories;

import lt.ocirama.labsystembackend.model.TrayWeightEntity;
import lt.ocirama.labsystembackend.services.FileControllerService;
import lt.ocirama.labsystembackend.services.UserInputService;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.Scanner;

public class TrayWeightRepository {

    Scanner sc = new Scanner(System.in);
    private final EntityManagerFactory entityManagerFactory;

    public TrayWeightRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void TrayWeightAsign() {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        for (int i = 1; i < 5000; i++) {
            System.out.println("Skenuokitę padėklą:");
            String padeklas = UserInputService.NumberOrEndInput();
            if (!padeklas.equals("Baigta")) {


                TrayWeightEntity twe = new TrayWeightEntity();
                twe.setTrayId(padeklas);

                System.out.println("Sverkite padėklą " + twe.getTrayId() + ":");
                Double trayWeight = FileControllerService.sverimoPrograma("On");
                twe.setTrayWeight(trayWeight);
                transaction.begin();
                em.persist(twe);
                transaction.commit();
            } else {
                break;
            }
        }
    }

    public void TrayWeightCalibrate() {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        for (int i = 1; i < 5000; i++) {
            System.out.println("Skenuokitę padėklą kalibravimui:");
            String padeklas = UserInputService.NumberOrEndInput();
            if (!padeklas.equals("Baigta")) {
                transaction.begin();
                Session session = em.unwrap(Session.class);
                Query query = session.createQuery("Select twe.trayId from TrayWeightEntity twe where twe.trayId=:padeklas");
                query.setParameter("padeklas", padeklas);
                TrayWeightEntity twe = (TrayWeightEntity) query.getSingleResult();
                System.out.println("Sverkite " + twe.getTrayId() + "padėklą kalibravimui");
                Double trayWeight = FileControllerService.sverimoPrograma("Om");
                twe.setTrayWeight(trayWeight);
                em.merge(twe);
                em.persist(twe);
                transaction.commit();
            } else {
                break;
            }
        }
    }}

