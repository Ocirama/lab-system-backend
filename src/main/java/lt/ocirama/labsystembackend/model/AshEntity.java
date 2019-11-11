package lt.ocirama.labsystembackend.model;

import javax.persistence.*;

@Entity
@Table(name = "ash_log")
public class AshEntity extends AbstractEntity {
    @Column(name = "dish_id",length = 50, nullable = false)
    private String dishId;
    @Column(name = "dish_weight", nullable = false)
    private double dishWeight;
    @Column(name = "total_dish_weight_before", nullable = false)
    private double dishAndSampleWeightBefore;
    @Column(name = "total_dish_weight_after", nullable = false)
    private double dishAndSampleWeightAfter;

    @ManyToOne
    @JoinColumn(name = "tray_id")
    private TrayEntity tray;

    public String getDishId() {
        return dishId;
    }

    public void setDishId(String dishId) {
        this.dishId = dishId;
    }

    public double getDishWeight() {
        return dishWeight;
    }

    public void setDishWeight(double dishWeight) {
        this.dishWeight = dishWeight;
    }

    public double getDishAndSampleWeightBefore() {
        return dishAndSampleWeightBefore;
    }

    public void setDishAndSampleWeightBefore(double dishAndSampleWeightBefore) {
        this.dishAndSampleWeightBefore = dishAndSampleWeightBefore;
    }

    public double getDishAndSampleWeightAfter() {
        return dishAndSampleWeightAfter;
    }

    public void setDishAndSampleWeightAfter(double dishAndSampleWeightAfter) {
        this.dishAndSampleWeightAfter = dishAndSampleWeightAfter;
    }

    public TrayEntity getTray() {
        return tray;
    }

    public void setTray(TrayEntity tray) {
        this.tray = tray;
    }
}