package org.kofi.creditex;

public class DateProvider {
    public static java.util.Date nowUtil(){
        return new java.util.Date();
    }
    public static java.sql.Date now(){
        return new java.sql.Date(nowUtil().getTime());
    }
}
