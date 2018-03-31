package com.cioc.greendao;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;


public class MyGenerator {
    public static void main(String[] args) {
        Schema schema = new Schema(1, "com.cioc.mygreendao.db"); // Your app package name and the (.db) is the folder where the DAO files will be generated into.
        schema.enableKeepSectionsByDefault();

        addTables(schema);

        try {
            new DaoGenerator().generateAll(schema,"../app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addTables(final Schema schema) {
        addLocationEntities(schema);
        // addPhonesEntities(schema);
    }

    // This is use to describe the colums of your table
    private static Entity addLocationEntities(final Schema schema) {
        Entity location = schema.addEntity("GPSLocation");
        location.addIdProperty().primaryKey().autoincrement();
//        user.addIntProperty("user_id").notNull();
        location.addStringProperty("longitude_value");
        location.addStringProperty("latitude_value");
        location.addStringProperty("date_time");
//        user.addStringProperty("email");
        return location;
    }

    //    private static Entity addPhonesEntities(final Schema schema) {
    //        Entity phone = schema.addEntity("Phone");
    //        phone.addIdProperty().primaryKey().autoincrement();
    //        phone.addIntProperty("user_id").notNull();
    //        phone.addStringProperty("number");
    //        return phone;
    //    }
}

