package model;

public class Customer {
    private static int idCounter = 1;
    private int customerId;
    private String name;
    private String address;
    private String phone;
    private String email;

    public Customer(String name, String address, String phone, String email) {
        this.customerId = idCounter++;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String toString() {
        return "Customer #" + customerId + ": " + name + ", " + address + ", " + phone + ", " + email;
    }
}