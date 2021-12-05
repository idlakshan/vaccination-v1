package model;

import java.time.LocalDate;

public class ToolKit {
    private String id;
    private String name;
    private LocalDate date;
    private Double price;
    private int qtyOnHand;

    public ToolKit() {
    }

    public ToolKit(String id, String name, LocalDate date, Double price, int qtyOnHand) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.price = price;
        this.qtyOnHand = qtyOnHand;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    @Override
    public String toString() {
        return "ToolKit{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", price=" + price +
                ", qtyOnHand=" + qtyOnHand +
                '}';
    }
}
