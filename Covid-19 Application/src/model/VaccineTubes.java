package model;

import java.time.LocalDate;
import java.util.Date;

public class VaccineTubes extends VaccinePatient {
    private String code;
    private String vaccineName;
    private String description;
    private LocalDate date;
    private int qtyOnHand;

    public VaccineTubes() {
    }

    public VaccineTubes(String code, String vaccineName, String description, LocalDate date, int qtyOnHand) {
        this.code = code;
        this.vaccineName = vaccineName;
        this.description = description;
        this.date = date;
        this.qtyOnHand = qtyOnHand;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    @Override
    public String toString() {
        return "VaccineTubes{" +
                "code='" + code + '\'' +
                ", vaccineName='" + vaccineName + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", qtyOnHand=" + qtyOnHand +
                '}';
    }
}
