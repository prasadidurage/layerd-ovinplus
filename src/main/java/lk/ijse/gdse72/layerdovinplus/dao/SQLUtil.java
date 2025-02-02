package lk.ijse.gdse72.layerdovinplus.dao;


import lk.ijse.gdse72.layerdovinplus.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLUtil {

    public static <T>T execute(String sql,Object... obj) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        for (int i=0;i<obj.length;i++){
            pstm.setObject((i+1),obj[i]);
        }

        if (sql.startsWith("select") || sql.startsWith("SELECT")){
            ResultSet resultSet = pstm.executeQuery();
            return (T) resultSet;

        }else {
            int i = pstm.executeUpdate();


            boolean isSaved = i >0;

            return (T) ((Boolean) isSaved);

        }
    }
}







