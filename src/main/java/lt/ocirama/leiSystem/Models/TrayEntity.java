package lt.ocirama.leiSystem.Models;

import javax.persistence.*;

@Entity
@Table(name = "TrayLog")
public class TrayEntity extends AbstractEntity {

    @Column(name = "trayId", length = 50, nullable = false,unique = true)
    private String trayId;
    @Column(name = "trayWeight")
    private double trayWeight;

    @ManyToOne
    @JoinColumn(name = "sample_id", referencedColumnName = "sampleId")
    private SampleEntity sample;


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

    public SampleEntity getSample() {
        return sample;
    }

    public void setSample(SampleEntity sample) {
        this.sample = sample;
    }
}
