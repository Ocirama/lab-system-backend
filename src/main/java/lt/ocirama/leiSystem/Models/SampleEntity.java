package lt.ocirama.leiSystem.Models;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "SampleLog")
public class SampleEntity extends AbstractEntity {
    @Column(name = "sampleId", length = 50, nullable = false,unique = true)
    private String SampleId;
    @Column(name = "sampleWeight")
    private double SampleWeight;

    @ManyToOne
    @JoinColumn(name = "protocol_id", referencedColumnName = "protocolId")
    private OrderEntity order;

    @OneToMany(targetEntity = TrayEntity.class, mappedBy = "sample", cascade = CascadeType.ALL)
    private List<SampleEntity> trays;

    public List<SampleEntity> getTrays() {
        return trays;
    }

    public void setTrays(List<SampleEntity> trays) {
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

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }
}
