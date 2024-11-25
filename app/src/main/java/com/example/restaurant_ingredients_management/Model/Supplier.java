package com.example.restaurant_ingredients_management.Model;

public class Supplier {
    private int id;               // ID nhà cung cấp (Primary Key)
    private String name;          // Tên nhà cung cấp
    private String contactInfo;   // Thông tin liên hệ
    private String address;       // Địa chỉ nhà cung cấp

    public Supplier(int id, String name, String contactInfo, String address) {
        this.id = id;
        this.name = name;
        this.contactInfo = contactInfo;
        this.address = address;
    }
    public Supplier(){

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return
                "id: " + id + "\n" +
                "Tên nhà cung cấp: '" + name + '\n' +
                "Thông tin liên hệ: " + contactInfo + '\n' +
                "Địa chỉ: " + address + '\n';
    }
}
