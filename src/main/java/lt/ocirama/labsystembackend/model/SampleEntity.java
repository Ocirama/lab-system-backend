package lt.ocirama.labsystembackend.model;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "sample_log")
public class SampleEntity extends AbstractEntity {

    @Column(name = "sample_id", length = 50, nullable = false)
    private String sampleId;
    @Column(name = "sample_weight", nullable = false)
    private double sampleWeight;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @OneToMany(mappedBy = "sample", cascade = CascadeType.ALL)
    private List<TrayEntity> trays;


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

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    public List<TrayEntity> getTrays() {
        return trays;
    }

    public void setTrays(List<TrayEntity> trays) {
        this.trays = trays;
    }


}
