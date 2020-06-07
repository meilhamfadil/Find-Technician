package id.kudzoza.findtechnician.model;

public class TypeModel {

    public TypeModel(String name, String description, int priceFrom, int priceTo) {
        this.name = name;
        this.description = description;
        this.priceFrom = priceFrom;
        this.priceTo = priceTo;
    }

    public String name;
    public String description;
    public int priceFrom;
    public int priceTo;
}
