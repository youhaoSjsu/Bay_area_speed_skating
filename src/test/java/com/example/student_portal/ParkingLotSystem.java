package com.example.student_portal;

public class ParkingLotSystem {
    public static void main(String[] args) {
        ParkingLot parkingLot = ParkingLot.getInstance(5);

        Car car1 = new Car("ABC123");
        Car car2 = new Car("XYZ789");
        Car car3 = new Car("MNO456");
        Car car4 = new Car("PQR789");
        Car car5 = new Car("SQR769");
        Car car6 = new Car("AQR729");
        System.out.println(parkingLot.parkCar(1, car1));
        System.out.println(parkingLot.parkCar(2, car2));
        System.out.println( parkingLot.parkCar(3, car3));
        System.out.println(parkingLot.parkCar(2, car4)); //attempt to park a car in occupied space;
        System.out.println(parkingLot.parkCar(4, car4));
        System.out.println(parkingLot.parkCar(5, car5));//full
        System.out.println(parkingLot.parkCar(5, car6));//full

        System.out.println(parkingLot.removeCar(2));
        System.out.println(parkingLot.removeCar(2));// Attempt to remove from an empty space
        System.out.println(parkingLot.parkCar(2, car6));// re park to the empty space
    }
}
