package lt.ocirama.labsystembackend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tray_weight_log")
public class TrayWeightEntity extends AbstractEntity {

    @Column(name = "tray_id", length = 50)
    private String trayId;

    @Column(name = "tray_weight", nullable = false)
    private double trayWeight;

    public String getTrayId() {
        return trayId;
    }

    public void setTrayId(String trayId) {
        this.trayId = trayId;
    }

    public double getTrayWeight() {
        return trayWeight;
    }

    public void setTrayWeight(double trayWeight) {
        this.trayWeight = trayWeight;
    }
}
