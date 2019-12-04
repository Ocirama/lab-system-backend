package lt.ocirama.labsystembackend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

    @Entity
    @Table(name = "quality_control_log")
    public class QualityControlEntity extends AbstractEntity {

        @Column(name = "test_type", length = 50, nullable = false)
        private String testType;

        @Column(name = "quality_tray_id", length = 50, nullable = false)
        private String qualityTrayId;

        @Column(name = "quality_tray_weight")
        private double qualityTrayWeight;

        @Column(name = "quality_tray_weight_before")
        private double qualityTrayWeightBefore;

        @Column(name = "quality_tray_weight_after")
        private double qualityTrayWeightAfter;

        public String getTestType() {
            return testType;
        }

        public void setTestType(String testType) {
            this.testType = testType;
        }

        public String getQualityTrayId() {
            return qualityTrayId;
        }

        public void setQualityTrayId(String qualityTrayId) {
            this.qualityTrayId = qualityTrayId;
        }

        public double getQualityTrayWeightBefore() {
            return qualityTrayWeightBefore;
        }

        public void setQualityTrayWeightBefore(double qualityTrayWeightBefore) {
            this.qualityTrayWeightBefore = qualityTrayWeightBefore;
        }

        public double getQualityTrayWeightAfter() {
            return qualityTrayWeightAfter;
        }

        public void setQualityTrayWeightAfter(double qualityTrayWeightAfter) {
            this.qualityTrayWeightAfter = qualityTrayWeightAfter;
        }

        public double getQualityTrayWeight() {
            return qualityTrayWeight;
        }

        public void setQualityTrayWeight(double qualityTrayWeight) {
            this.qualityTrayWeight = qualityTrayWeight;
        }
    }
