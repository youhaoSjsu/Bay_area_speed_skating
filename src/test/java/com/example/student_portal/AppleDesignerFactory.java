package com.example.student_portal;

public class AppleDesignerFactory {
    private static AppleDesignerFactory instance;
    private AppleDesignerFactory()
    {

    }
    public static AppleDesignerFactory getInstance(){
        if(instance==null){
            synchronized (AppleDesignerFactory.class){
                if(instance==null){
                    instance = new AppleDesignerFactory();
                }
            }
        }
        return instance;
    }


}
