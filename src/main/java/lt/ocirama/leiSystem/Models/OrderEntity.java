package lt.ocirama.leiSystem.Models;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "OrderLog")
public class OrderEntity extends AbstractEntity implements Serializable {
    @Column(name = "protocolId", length = 50, nullable = false, unique = true)
    private String protocolId;

    @Column(name = "customer", length = 50, nullable = false)
    private String customer;

    @Column(name = "test", length = 50, nullable = false)
    private String test;

    @Column(name = "sampleType", length = 50, nullable = false)
    private String sampleType;

    @Column(name = "orderAmount", nullable = false)
    private int orderAmount;

    @Column(name = "date", nullable = false)
    private Date date;

    @OneToMany(targetEntity = SampleEntity.class, mappedBy = "order", cascade = CascadeType.ALL)
    private List<SampleEntity> samples;

    public List<SampleEntity> getSamples() {
        return samples;
    }

    public void setSamples(List<SampleEntity> samples) {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "protocolId='" + protocolId + '\'' +
                ", customer='" + customer + '\'' +
                ", test=" + test + '\'' +
                ", sampleType='" + sampleType + '\'' +
                ", orderAmount='" + orderAmount +
                '}';
    }

}
