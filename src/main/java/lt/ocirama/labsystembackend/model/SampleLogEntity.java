package lt.ocirama.labsystembackend.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "sample_log")
public class SampleLogEntity extends AbstractEntity implements Serializable {

    @Column(name = "sample_id", length = 50, nullable = false, unique = true)
    private String sampleId;

    @Column(name = "sample_weight", nullable = false)
    private double sampleWeight;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderLogEntity order;

    @OneToMany(mappedBy = "sample", cascade = CascadeType.ALL)
    private List<TrayLogEntity> trays;

    @OneToMany(mappedBy = "sample", cascade = CascadeType.ALL)
    private List<TotalMoistureEntity> totalTrays;

    public List<TotalMoistureEntity> getTotalTrays() {
        return totalTrays;
    }

    public void setTotalTrays(List<TotalMoistureEntity> totalTrays) {
        this.totalTrays = totalTrays;
    }

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    public double getSampleWeight() {
        return sampleWeight;
    }

    public void setSampleWeight(double sampleWeight) {
        this.sampleWeight = sampleWeight;
    }

    public OrderLogEntity getOrder() {
        return order;
    }

    public void setOrder(OrderLogEntity order) {
        this.order = order;
    }

    public List<TrayLogEntity> getTrays() {
        return trays;
    }

    public void setTrays(List<TrayLogEntity> trays) {
        this.trays = trays;
    }
}
