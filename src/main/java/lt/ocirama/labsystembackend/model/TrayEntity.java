package lt.ocirama.labsystembackend.model;

import javax.persistence.*;

@Entity
@Table(name = "tray_log")
public class TrayEntity extends AbstractEntity {

    @Column(name = "tray_id", length = 50)
    private String trayId;

    @OneToOne (mappedBy = "tray")
    private TotalMoistureEntity totalMoistureEntity;

    @OneToOne (mappedBy = "tray")
    private GeneralMoistureEntity generalMoistureEntity;

    @OneToOne (mappedBy = "tray")
    private AshEntity ashEntity;

    @ManyToOne
    @JoinColumn(name = "sample_id")
    private SampleEntity sample;


    public TotalMoistureEntity getTotalMoistureEntity() {
        return totalMoistureEntity;
    }

    public void setTotalMoistureEntity(TotalMoistureEntity totalMoistureEntity) {
        this.totalMoistureEntity = totalMoistureEntity;
    }

    public GeneralMoistureEntity getGeneralMoistureEntity() {
        return generalMoistureEntity;
    }

    public void setGeneralMoistureEntity(GeneralMoistureEntity generalMoistureEntity) {
        this.generalMoistureEntity = generalMoistureEntity;
    }

    public AshEntity getAshEntity() {
        return ashEntity;
    }

    public void setAshEntity(AshEntity ashEntity) {
        this.ashEntity = ashEntity;
    }

    public String getTrayId() {
        return trayId;
    }

    public void setTrayId(String trayId) {
        this.trayId = trayId;
    }

    public SampleEntity getSample() {
        return sample;
    }

    public void setSample(SampleEntity sample) {
        this.sample = sample;
    }
}
