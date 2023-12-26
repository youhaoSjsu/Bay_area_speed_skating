package com.example.student_portal;

import java.util.Scanner;

public class ExceptionHandler {

    public static void main(String[] args) {
        System.out.println("please enter card type");
        Scanner scanner = new Scanner(System.in);
        String cardType = scanner.nextLine();
        System.out.println("please enter card type");
        String adress = scanner.nextLine();
        String [] addressArr = adress.split(" ");

        try {
            if(cardType .equals("AMEX") ){
                throw new CardTypeException();
            }
            else if(!addressArr[addressArr.length-1].equals("US")){
                throw new AddressException();
            }
        }catch (CardTypeException e){
            System.out.println("CardTypeException: " + e.getMessage());
        } catch (AddressException e) {
            throw new RuntimeException(e);
        }

    }
}
