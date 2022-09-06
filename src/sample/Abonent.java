package sample;

public class Abonent {
    private String surname;
    private int year;
    private String number;
    private String address;

    public Abonent(String surname, int year, String number, String address) {
        this.surname = surname;
        this.year = year;
        this.number = number;
        this.address = address;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
