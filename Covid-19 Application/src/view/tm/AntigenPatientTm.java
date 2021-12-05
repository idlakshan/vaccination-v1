package view.tm;

import java.time.LocalDate;

public class AntigenPatientTm {
    private String nic;
    private String name;
    private String address;
    private LocalDate date;
    private String result;

    public AntigenPatientTm() {
    }

    public AntigenPatientTm(String nic, String name, String address, LocalDate date, String result) {
        this.nic = nic;
        this.name = name;
        this.address = address;
        this.date = date;
        this.result = result;
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "AntigenPatientTm{" +
                "nic='" + nic + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", date=" + date +
                ", result='" + result + '\'' +
                '}';
    }
}
