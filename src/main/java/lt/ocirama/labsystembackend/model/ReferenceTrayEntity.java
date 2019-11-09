package lt.ocirama.labsystembackend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "reference_tray_log")
public class ReferenceTrayEntity extends AbstractEntity{

    @Column( name = "reference_tray_id",length = 50, nullable = false)
    private String referenceTrayId;

    @Column (name = "reference_tray_weight_before")
    private double referenceTrayWeightBefore;

    @Column (name = "reference_tray_weight_after")
    private double referenceTrayWeightAfter;

    public String getReferenceTrayId() {
        return referenceTrayId;
    }

    public void setReferenceTrayId(String referenceTrayId) {
        this.referenceTrayId = referenceTrayId;
    }

    public double getReferenceTrayWeightBefore() {
        return referenceTrayWeightBefore;
    }

    public void setReferenceTrayWeightBefore(double referenceTrayWeightBefore) {
        this.referenceTrayWeightBefore = referenceTrayWeightBefore;
    }

    public double getReferenceTrayWeightAfter() {
        return referenceTrayWeightAfter;
    }

    public void setReferenceTrayWeightAfter(double referenceTrayWeightAfter) {
        this.referenceTrayWeightAfter = referenceTrayWeightAfter;
    }
}
