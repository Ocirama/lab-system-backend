package lt.ocirama.labsystembackend.model;

import javax.persistence.*;

@Entity
@Table(name = "general_moisture_log")
public class GeneralMoistureEntity extends AbstractEntity {

    @Column(name = "jar_id",length = 50)
    private String jarId;
    @Column(name = "jar_weight")
    private double jarWeight;
    @Column(name = "total_jar_weight_before")
    private double jarAndSampleWeightBefore;
    @Column(name = "total_jar_weight_after")
    private double jarAndSampleWeightAfter;
    @Column(name = "total_jar_weight_after_plus")
    private double jarAndSampleWeightAfterPlus;

    @ManyToOne
    @JoinColumn(name = "tray_id")
    private TrayEntity tray;


    public String getJarId() {
        return jarId;
    }

    public void setJarId(String jarId) {
        this.jarId = jarId;
    }

    public double getJarWeight() {
        return jarWeight;
    }

    public void setJarWeight(double jarWeight) {
        this.jarWeight = jarWeight;
    }

    public double getJarAndSampleWeightBefore() {
        return jarAndSampleWeightBefore;
    }

    public void setJarAndSampleWeightBefore(double jarAndSampleWeightBefore) {
        this.jarAndSampleWeightBefore = jarAndSampleWeightBefore;
    }

    public double getJarAndSampleWeightAfter() {
        return jarAndSampleWeightAfter;
    }

    public void setJarAndSampleWeightAfter(double jarAndSampleWeightAfter) {
        this.jarAndSampleWeightAfter = jarAndSampleWeightAfter;
    }

    public double getJarAndSampleWeightAfterPlus() {
        return jarAndSampleWeightAfterPlus;
    }

    public void setJarAndSampleWeightAfterPlus(double jarAndSampleWeightAfterPlus) {
        this.jarAndSampleWeightAfterPlus = jarAndSampleWeightAfterPlus;
    }

    public TrayEntity getTray() {
        return tray;
    }

    public void setTray(TrayEntity tray) {
        this.tray = tray;
    }
}

