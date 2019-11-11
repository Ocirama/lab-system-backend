package lt.ocirama.labsystembackend.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tray_log")
public class TrayEntity extends AbstractEntity {

    @Column(name = "tray_id", length = 50)
    private String trayId;

    @OneToMany(mappedBy = "tray", cascade = CascadeType.ALL)
    private List<TotalMoistureEntity> totalMoistureEntities;

    @OneToMany(mappedBy = "tray", cascade = CascadeType.ALL)
    private List<GeneralMoistureEntity> generalMoistureEntities;

    @OneToMany(mappedBy = "tray", cascade = CascadeType.ALL)
    private List<AshEntity> ashEntities;

    @ManyToOne
    @JoinColumn(name = "sample_id")
    private SampleEntity sample;

    public List<TotalMoistureEntity> getTotalMoistureEntities() {
        return totalMoistureEntities;
    }

    public void setTotalMoistureEntities(List<TotalMoistureEntity> totalMoistureEntities) {
        this.totalMoistureEntities = totalMoistureEntities;
    }

    public List<GeneralMoistureEntity> getGeneralMoistureEntities() {
        return generalMoistureEntities;
    }

    public void setGeneralMoistureEntities(List<GeneralMoistureEntity> generalMoistureEntities) {
        this.generalMoistureEntities = generalMoistureEntities;
    }

    public List<AshEntity> getAshEntities() {
        return ashEntities;
    }

    public void setAshEntities(List<AshEntity> ashEntities) {
        this.ashEntities = ashEntities;
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
