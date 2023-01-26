package by.belstu.it.lyskov.model;

import java.util.Objects;

public class NvidiaDriver {

    private String product;
    private String series;
    private String model;
    private String os;
    private String lang;
    private String type;

    public NvidiaDriver(String product, String series, String model, String os, String lang, String type) {
        this.product = product;
        this.series = series;
        this.model = model;
        this.os = os;
        this.lang = lang;
        this.type = type;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NvidiaDriver that = (NvidiaDriver) o;
        return Objects.equals(product, that.product)
                && Objects.equals(series, that.series)
                && Objects.equals(model, that.model)
                && Objects.equals(os, that.os)
                && Objects.equals(lang, that.lang)
                && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, series, model, os, lang, type);
    }

    @Override
    public String toString() {
        return "NvidiaDriver{" +
                "product='" + product + '\'' +
                ", series='" + series + '\'' +
                ", model='" + model + '\'' +
                ", os='" + os + '\'' +
                ", lang='" + lang + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
