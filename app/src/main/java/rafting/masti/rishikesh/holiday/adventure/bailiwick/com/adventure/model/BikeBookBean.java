package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.model;

public class BikeBookBean {

    private String id;
    private String bikeName;
    private String bikePrice;
    private String bikeMaxQuantity;
    private String quantity;
    private String totalPrice;

    public BikeBookBean(String id, String bikeName, String bikePrice, String bikeMaxQuantity,String quantity,String totalPrice) {
        this.id = id;
        this.bikeName = bikeName;
        this.bikePrice = bikePrice;
        this.bikeMaxQuantity = bikeMaxQuantity;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBikeName() {
        return bikeName;
    }

    public void setBikeName(String bikeName) {
        this.bikeName = bikeName;
    }

    public String getBikePrice() {
        return bikePrice;
    }

    public void setBikePrice(String bikePrice) {
        this.bikePrice = bikePrice;
    }

    public String getBikeMaxQuantity() {
        return bikeMaxQuantity;
    }

    public void setBikeMaxQuantity(String bikeMaxQuantity) {
        this.bikeMaxQuantity = bikeMaxQuantity;
    }
}
