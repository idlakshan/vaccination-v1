package view.tm;


import javafx.scene.control.CheckBox;

public class VaccinationPatientTm {
   private String nic;
   private String name;
   private String address;
   private String nameOfVaccine;
   private CheckBox checkBox1;
   private CheckBox checkBox2;
   private CheckBox checkBox3;

    public VaccinationPatientTm() {
    }

    public VaccinationPatientTm(String nic, String name, String address, String nameOfVaccine, CheckBox checkBox1, CheckBox checkBox2, CheckBox checkBox3) {
        this.nic = nic;
        this.name = name;
        this.address = address;
        this.nameOfVaccine = nameOfVaccine;
        this.checkBox1 = checkBox1;
        this.checkBox2 = checkBox2;
        this.checkBox3 = checkBox3;
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

    public String getNameOfVaccine() {
        return nameOfVaccine;
    }

    public void setNameOfVaccine(String nameOfVaccine) {
        this.nameOfVaccine = nameOfVaccine;
    }

    public CheckBox getCheckBox1() {
        return checkBox1;
    }

    public void setCheckBox1(CheckBox checkBox1) {
        this.checkBox1 = checkBox1;
    }

    public CheckBox getCheckBox2() {
        return checkBox2;
    }

    public void setCheckBox2(CheckBox checkBox2) {
        this.checkBox2 = checkBox2;
    }

    public CheckBox getCheckBox3() {
        return checkBox3;
    }

    public void setCheckBox3(CheckBox checkBox3) {
        this.checkBox3 = checkBox3;
    }

    @Override
    public String toString() {
        return "VaccinationPatientTm{" +
                "nic='" + nic + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", nameOfVaccine='" + nameOfVaccine + '\'' +
                ", checkBox1=" + checkBox1 +
                ", checkBox2=" + checkBox2 +
                ", checkBox3=" + checkBox3 +
                '}';
    }
}
