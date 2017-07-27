package com.dao;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class MyClass {
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "com.chy.qrcode.dao");
        addNote(schema);
        new DaoGenerator().generateAll(schema, "D:/AndroidWork/AndroidStudioProjectsLocal/QRcode/app/src/main/java");
    }

    private static void addNote(Schema schema) {
        Entity note = schema.addEntity("SWEEP_RECORD");
        // 你也可以重新给表命名
        // note.setTableName("NODE");
        // greenDAO 会自动根据实体类的属性值来创建表字段，并赋予默认值
        // 接下来你便可以设置表中的字段：
        note.addIdProperty();
        note.addStringProperty("context").notNull();
        note.addIntProperty("tpye");
        // 与在 Java 中使用驼峰命名法不同，默认数据库中的命名是使用大写和下划线来分割单词的。
        // For example, a property called “creationDate” will become a database column “CREATION_DATE”.
        note.addStringProperty("create_time");

    }
}
