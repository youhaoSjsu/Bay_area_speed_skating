package com.example.student_portal;

public class SingletonPatternExample {
    public static void main(String[] args) {
        AppleDesignerFactory factory1 = AppleDesignerFactory.getInstance();
        AppleDesignerFactory factory2 = AppleDesignerFactory.getInstance();

        System.out.println(factory1 == factory2); // Should print "true" since both references point to the same instance
    }
}
interface Currency{
    String getCurrency();
}
class USDCurrency implements Currency{
    @Override
    public String getCurrency()
    {
        return "$";
    }
}
class YenCurrency implements Currency{
    @Override
    public String getCurrency(){
        return "￥";
    }
}class CurrencyExchange {
    public Currency getCurrency(String country) {
        if (country.equalsIgnoreCase("USA")) {
            return new USDCurrency();
        } else if (country.equalsIgnoreCase("Japan")) {
            return new YenCurrency();
        }
        return null; // Default case if the country is not recognized
    }
}
