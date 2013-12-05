package org.kofi.creditex;

public class Dates {
    public static java.util.Date nowUtil(){
        return new java.util.Date();
    }
    public static java.sql.Date now(){
        return new java.sql.Date(nowUtil().getTime());
    }
    public static java.sql.Date now(int days_shift){
        return new java.sql.Date(nowUtil().getTime() + (long)days_shift*(24*3600*1000));
    }
}
