package com.example.brodcast_receiver_implementation;

public class Contact {

    public Contact(int id,String number)
    {
        this.id=id;
        this.number=number;
    }
        private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    private String number;


}
