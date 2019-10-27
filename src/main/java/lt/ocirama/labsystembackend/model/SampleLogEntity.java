package lt.ocirama.labsystembackend.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "sample_log")
public class SampleLogEntity extends AbstractEntity implements Serializable {

    @Column(name = "sample_id", length = 50, nullable = false, unique = true)
    private String SampleId;

    @Column(name = "sample_weight", nullable = false)
    private double SampleWeight;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderLogEntity order;

    @OneToMany(targetEntity = TrayLogEntity.class, mappedBy = "sample", cascade = CascadeType.ALL)
    private List<SampleLogEntity> trays;

    public List<SampleLogEntity> getTrays() {
        return trays;
    }

    public void setTrays(List<SampleLogEntity> trays) {
        this.trays = trays;
    }

    public String getSampleId() {
        return SampleId;
    }

    public void setSampleId(String sampleId) {
        SampleId = sampleId;
    }

    public double getSampleWeight() {
        return SampleWeight;
    }

    public void setSampleWeight(double sampleWeight) {
        SampleWeight = sampleWeight;
    }

    public OrderLogEntity getOrder() {
        return order;
    }

    public void setOrder(OrderLogEntity order) {
        this.order = order;
    }
}
