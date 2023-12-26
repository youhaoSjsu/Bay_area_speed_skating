package com.example.student_portal;

import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;

public class ParkingLot {
private Map<Integer,Car> parkSpace;
private static ParkingLot instance;
private int capacity;
private ParkingLot(int capacity){
    this.capacity = capacity;
    parkSpace = new HashMap<Integer,Car>();
}
public static synchronized ParkingLot getInstance(int capacity){
    if(instance == null)
    {
        instance = new ParkingLot(capacity);
    }
    return instance;
}
public String parkCar(int spaceNumber, Car car)
{
    synchronized (parkSpace){
        if(parkSpace.size()==capacity){
            return "can not park more car, please wait";
        }else{
            if(parkSpace.containsKey(spaceNumber)){
                return "error, this space is occupied";
            }else {
                parkSpace.put(spaceNumber,car);
                return "success parked current car number is "+ parkSpace.size();
            }
        }
    }

}
public String removeCar(int spaceNumber)
{
    synchronized (parkSpace){
        if(parkSpace.containsKey(spaceNumber))
        {
            parkSpace.remove(spaceNumber);
            return "success cleared this space, current car number is "+ parkSpace.size();
        }
        else {
            return "error, this space is empty";
        }
    }

}

}
class Car{
    private String plateNum;

    public Car(String plateNum) {
        this.plateNum = plateNum;
    }

    public String getPlateNum() {
        return plateNum;
    }

    public void setPlateNum(String plateNum) {
        this.plateNum = plateNum;
    }
}
