package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Modal;

public class PaymentHistory {

    private String serviceName,orderDate,orderAmount,paymentStatus,transactionId;

    public PaymentHistory(String serviceName, String orderDate, String orderAmount, String paymentStatus, String transactionId) {
        this.serviceName = serviceName;
        this.orderDate = orderDate;
        this.orderAmount = orderAmount;
        this.paymentStatus = paymentStatus;
        this.transactionId = transactionId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
