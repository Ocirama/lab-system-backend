package lt.ocirama.labsystembackend.repositories;

import lt.ocirama.labsystembackend.model.ReferenceTrayEntity;
import lt.ocirama.labsystembackend.services.FileControllerService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.Scanner;

public class ReferenceTrayRepository {
    Scanner sc = new Scanner(System.in);
    private final EntityManagerFactory entityManagerFactory;

    public ReferenceTrayRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }


    public void ReferenceTrayLogGenerate() {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            System.out.println("Skenuokite padėklą");
            String padeklas = sc.nextLine();
            ReferenceTrayEntity rte = new ReferenceTrayEntity();
            rte.setReferenceTrayId(padeklas);

            System.out.println("Sverkitę" + padeklas + "padėklą");
            Double trayWeight = FileControllerService.sverimoPrograma();
            rte.setReferenceTrayWeightBefore(trayWeight);

            em.persist(rte);

            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
