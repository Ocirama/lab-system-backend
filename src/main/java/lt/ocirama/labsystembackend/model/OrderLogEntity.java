package lt.ocirama.labsystembackend.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "order_log")
public class OrderLogEntity extends AbstractEntity implements Serializable {

    @Column(name = "protocol_id", length = 50, nullable = false, unique = true)
    private String protocolId;

    @Column(name = "customer", length = 50, nullable = false)
    private String customer;

    @Column(name = "test", length = 50, nullable = false)
    private String test;

    @Column(name = "sample_type", length = 50, nullable = false)
    private String sampleType;



    @Column(name = "order_amount", nullable = false)
    private int orderAmount;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<SampleLogEntity> samples;

    public List<SampleLogEntity> getSamples() {
        return samples;
    }


    public void setSamples(List<SampleLogEntity> samples) {
        this.samples = samples;
    }


    public String getProtocolId() {
        return protocolId;
    }

    public void setProtocolId(String protocolId) {
        this.protocolId = protocolId;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }

    @Override
    public String toString() {
        return "OrderLogEntity{" +
                "protocolId='" + protocolId + '\'' +
                ", customer='" + customer + '\'' +
                ", test=" + test + '\'' +
                ", sampleType='" + sampleType + '\'' +
                ", orderAmount='" + orderAmount +
                '}';
    }

}
