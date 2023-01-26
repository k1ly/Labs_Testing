package by.belstu.it.lyskov.model;

import java.util.Objects;

public class NvidiaDownload {

    private String productSeriesType;
    private String productSeries;
    private String productFamily;
    private String operatingSystem;
    private String downloadType;
    private String language;

    public NvidiaDownload(String productSeriesType, String productSeries, String productFamily, String operatingSystem, String downloadType, String language) {
        this.productSeriesType = productSeriesType;
        this.productSeries = productSeries;
        this.productFamily = productFamily;
        this.operatingSystem = operatingSystem;
        this.downloadType = downloadType;
        this.language = language;
    }

    public String getProductSeriesType() {
        return productSeriesType;
    }

    public void setProductSeriesType(String productSeriesType) {
        this.productSeriesType = productSeriesType;
    }

    public String getProductSeries() {
        return productSeries;
    }

    public void setProductSeries(String productSeries) {
        this.productSeries = productSeries;
    }

    public String getProductFamily() {
        return productFamily;
    }

    public void setProductFamily(String productFamily) {
        this.productFamily = productFamily;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getDownloadType() {
        return downloadType;
    }

    public void setDownloadType(String downloadType) {
        this.downloadType = downloadType;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NvidiaDownload that = (NvidiaDownload) o;
        return Objects.equals(productSeriesType, that.productSeriesType) && Objects.equals(productSeries, that.productSeries) && Objects.equals(productFamily, that.productFamily) && Objects.equals(operatingSystem, that.operatingSystem) && Objects.equals(downloadType, that.downloadType) && Objects.equals(language, that.language);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productSeriesType, productSeries, productFamily, operatingSystem, downloadType, language);
    }

    @Override
    public String toString() {
        return "NvidiaDownload{" +
                "productSeriesType='" + productSeriesType + '\'' +
                ", productSeries='" + productSeries + '\'' +
                ", productFamily='" + productFamily + '\'' +
                ", operatingSystem='" + operatingSystem + '\'' +
                ", downloadType='" + downloadType + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
}
