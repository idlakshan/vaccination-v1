package view.tm;

public class CartTm {
    private String itemCode;
    private String description;
    private Integer qty;
    private Double price;

    public CartTm(String itemCode, String description, Integer qty, Double price) {
        this.itemCode = itemCode;
        this.description = description;
        this.qty = qty;
        this.price = price;
    }


    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "CartTm{" +
                "itemCode='" + itemCode + '\'' +
                ", description='" + description + '\'' +
                ", qty='" + qty + '\'' +
                ", price=" + price +
                '}';
    }
}
