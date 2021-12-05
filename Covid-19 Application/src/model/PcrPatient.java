package model;

import java.time.LocalDate;

public class PcrPatient {
    private String nic;
    private String name;
    private String address;
    private LocalDate date;
    private String phone;

    public PcrPatient() {
    }

    public PcrPatient(String nic, String name, String address, LocalDate date, String phone) {
        this.nic = nic;
        this.name = name;
        this.address = address;
        this.date = date;
        this.phone = phone;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "PcrPatient{" +
                "nic='" + nic + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", date=" + date +
                ", phone='" + phone + '\'' +
                '}';
    }
}
