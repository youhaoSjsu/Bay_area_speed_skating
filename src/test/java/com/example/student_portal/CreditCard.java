package com.example.student_portal;

interface  CreditCard {


    String getHolderName();
    String getCardNumber();
    double getAccountBalance();
    String getCardType();
    boolean isCardAcceptable(String cardType);
    default void payBill(double bill){
        double currentBalence = getAccountBalance();
        if(currentBalence>=bill){
            System.out.println("payment success");
            currentBalence -= bill;
            setAccountBalance(currentBalence);
        }
    }

    static void refund(double amount, CreditCard creditCard) {
        double newBalance = creditCard.getAccountBalance() + amount;
        creditCard.setAccountBalance(newBalance);
        System.out.println("Refunded $" + amount + ". New balance: $" + newBalance);

    }


    void setAccountBalance(double currentBalence);

}
class VisaCard implements CreditCard{

    private String holderName;
    private String cardNumber;
    private String cardType;
    private double accountBalance;

    public VisaCard(String holderName, String cardNumber, String cardType, double accountBalance) {
        this.holderName = holderName;
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.accountBalance = accountBalance;
    }


    @Override
    public String getHolderName() {
        return holderName;
    }

    @Override
    public String getCardNumber() {
        return cardNumber;
    }

    @Override
    public double getAccountBalance() {
        return accountBalance;
    }

    @Override
    public String getCardType() {
        return cardType;
    }

    @Override
    public boolean isCardAcceptable(String merchatType) {
        if(merchatType.equals("onsite"))
        {
            return true;
        }
        return false;
    }

    @Override
    public void setAccountBalance(double currentBalence) {
            accountBalance = currentBalence;
    }
}
class  MasterCard implements CreditCard{

    private String holderName;
    private String cardNumber;
    private String cardType;
    private double accountBalance;
    public MasterCard(String holderName, String cardNumber, String cardType, double accountBalance) {
        this.holderName = holderName;
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.accountBalance = accountBalance;
    }



    @Override
    public String getHolderName() {
        return holderName;
    }

    @Override
    public String getCardNumber() {
        return cardNumber;
    }

    @Override
    public double getAccountBalance() {
        return accountBalance;
    }

    @Override
    public String getCardType() {
        return cardType;
    }

    @Override
    public boolean isCardAcceptable(String merchatType) {
        if(merchatType.equals("online"))
        {
            return true;
        }
        return false;
    }

    @Override
    public void setAccountBalance(double currentBalence) {
        accountBalance = currentBalence;
    }
}

