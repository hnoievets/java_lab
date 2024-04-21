package edu.ntudp.fit.hnoievets.java_lab.packages.parser.model;

import java.util.Objects;

public class CarInfo {
    private String name;
    private float price;
    private String currency;
    private String generation;
    private int mileage;
    private String engine;
    private String akp;
    private String vinCode;
    private Boolean checkedVinCode;
    private Boolean afterAccident;
    private String location;
    private String link;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGeneration() {
        return generation;
    }

    public void setGeneration(String generation) {
        this.generation = generation;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getAkp() {
        return akp;
    }

    public void setAkp(String akp) {
        this.akp = akp;
    }

    public String getVinCode() {
        return vinCode;
    }

    public void setVinCode(String vinCode) {
        this.vinCode = vinCode;
    }

    public Boolean getCheckedVinCode() {
        return checkedVinCode;
    }

    public void setCheckedVinCode(Boolean checkedVinCode) {
        this.checkedVinCode = checkedVinCode;
    }

    public Boolean getAfterAccident() {
        return afterAccident;
    }

    public void setAfterAccident(Boolean afterAccident) {
        this.afterAccident = afterAccident;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarInfo carInfo = (CarInfo) o;
        return Float.compare(price, carInfo.price) == 0
                && Objects.equals(currency, carInfo.currency)
                && Objects.equals(generation, carInfo.generation)
                && Objects.equals(mileage, carInfo.mileage)
                && Objects.equals(engine, carInfo.engine)
                && Objects.equals(akp, carInfo.akp)
                && Objects.equals(vinCode, carInfo.vinCode)
                && Objects.equals(checkedVinCode, carInfo.checkedVinCode)
                && Objects.equals(afterAccident, carInfo.afterAccident)
                && Objects.equals(location, carInfo.location)
                && Objects.equals(link, carInfo.link)
                && Objects.equals(name, carInfo.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, currency, generation, mileage, engine, akp, vinCode, checkedVinCode,
                afterAccident, location, link);
    }

    @Override
    public String toString() {
        return "CarInfo{" +
                "name=" + name +
                "price=" + price +
                ", currency='" + currency + '\'' +
                ", generation='" + generation + '\'' +
                ", mileage=" + mileage +
                ", engine='" + engine + '\'' +
                ", akp='" + akp + '\'' +
                ", vinCode='" + vinCode + '\'' +
                ", checkedVinCode=" + checkedVinCode +
                ", afterAccident=" + afterAccident +
                ", location='" + location + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
