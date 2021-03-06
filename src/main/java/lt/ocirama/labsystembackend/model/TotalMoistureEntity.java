package lt.ocirama.labsystembackend.model;

import javax.persistence.*;

@Entity
@Table(name = "total_moisture_log")
public class TotalMoistureEntity extends AbstractEntity {

    @Column(name = "tray_weight", nullable = false)
    private double trayWeight;
    @Column(name = "total_tray_weight_before", nullable = false)
    private double trayAndSampleWeightBefore;
    @Column(name = "total_tray_weight_after", nullable = false)
    private double trayAndSampleWeightAfter;
    @Column(name = "total_tray_weight_after_plus", nullable = false)
    private double trayAndSampleWeightAfterPlus;

    @ManyToOne
    @JoinColumn(name = "tray_id")
    private TrayEntity tray;


    public double getTrayWeight() {
        return trayWeight;
    }

    public void setTrayWeight(double trayWeight) {
        this.trayWeight = trayWeight;
    }

    public double getTrayAndSampleWeightBefore() {
        return trayAndSampleWeightBefore;
    }

    public void setTrayAndSampleWeightBefore(double trayAndSampleWeightBefore) {
        this.trayAndSampleWeightBefore = trayAndSampleWeightBefore;
    }

    public double getTrayAndSampleWeightAfter() {
        return trayAndSampleWeightAfter;
    }

    public void setTrayAndSampleWeightAfter(double trayAndSampleWeightAfter) {
        this.trayAndSampleWeightAfter = trayAndSampleWeightAfter;
    }

    public double getTrayAndSampleWeightAfterPlus() {
        return trayAndSampleWeightAfterPlus;
    }

    public void setTrayAndSampleWeightAfterPlus(double trayAndSampleWeightAfterPlus) {
        this.trayAndSampleWeightAfterPlus = trayAndSampleWeightAfterPlus;
    }


    public TrayEntity getTray() {
        return tray;
    }

    public void setTray(TrayEntity tray) {
        this.tray = tray;
    }
}

