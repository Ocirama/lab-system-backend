package lt.ocirama.labsystembackend.model;

import javax.persistence.*;

@Entity
@Table(name = "tray_log")
public class TrayLogEntity extends AbstractEntity {

    @Column(name = "tray_id", length = 50, nullable = false, unique = true)
    private String trayId;

    @ManyToOne
    @JoinColumn(name = "sample_id")
    private SampleLogEntity sample;


    public String getTrayId() {
        return trayId;
    }

    public void setTrayId(String trayId) {
        this.trayId = trayId;
    }

    public SampleLogEntity getSample() {
        return sample;
    }

    public void setSample(SampleLogEntity sample) {
        this.sample = sample;
    }
}
