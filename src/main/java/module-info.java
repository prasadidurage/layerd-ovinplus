module lk.ijse.gdse72.layerdovinplus {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;
    requires net.sf.jasperreports.core;
   // requires mysql.connector.j;

    // requires java.mail;
  //  requires net.sf.jasperreports.core;

    opens lk.ijse.gdse72.layerdovinplus.controller to javafx.fxml;
    exports lk.ijse.gdse72.layerdovinplus.controller;
    exports lk.ijse.gdse72.layerdovinplus.dto;
    exports lk.ijse.gdse72.layerdovinplus.entity;
    exports lk.ijse.gdse72.layerdovinplus.dao;
    exports lk.ijse.gdse72.layerdovinplus.dao.custom;

    opens lk.ijse.gdse72.layerdovinplus.dto.tm to javafx.base;
    opens lk.ijse.gdse72.layerdovinplus to javafx.fxml;
    opens lk.ijse.gdse72.layerdovinplus.dto to javafx.fxml;
    exports lk.ijse.gdse72.layerdovinplus;
}