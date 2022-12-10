package com.example.student_portal.module;

public class UserConsumption extends User{

    private double consumption ;
    public UserConsumption(Integer id, String name) {
        super(id, name);
    }

    public double getConsumption() {
        return consumption;
    }

    public void setConsumption(double consumption) {
        this.consumption = consumption;
    }

    public UserConsumption()
    {

    }
    public UserConsumption(int id, String name, double consumption)
    {
        super();
        super.setId(id);
        super.setName(name);
        this.consumption = consumption;

    }

}
