package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.ResultSetMetaData;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by smile on 14-Mar-16.
 */


public class DataTransfer {

    private Connection connection = null;
    private int columns = 0;

    public DataTransfer(){
        String url = "jdbc:postgresql://127.0.0.1:5432/postgres";
        String name = "postgres";
        String password = "1111";
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, name, password);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<String> getContent(){
        try {
            Statement statement = null;
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM studentInfo");
            List list = new ArrayList<String>();
            ResultSetMetaData rm = result.getMetaData();
            columns = rm.getColumnCount();
            while (result.next()){
                for (int k = 1; k <= columns; k++) {
                    list.add(result.getString(k));
                }
            }
            return list;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public int getColumns(){
        return this.columns;
    }

    public void addContent(List<String> listContent){
        try{
            PreparedStatement st = connection.prepareStatement("INSERT INTO studentInfo (\"Name\", \"Surname\"," +
                    " \"Fathername\",\"Birth_date\", \"Form\", \"Phone_number\") VALUES (?, ?,?,?,?,?);");
            st.setString(1,listContent.get(0));
            st.setString(2,listContent.get(1));
            st.setString(3,listContent.get(2));
            SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
            Date d = format.parse(listContent.get(3));
            long h = d.getTime();
            java.sql.Date date = new java.sql.Date(h);
            st.setDate(4, date);
            st.setString(5,listContent.get(4));
            st.setString(6,listContent.get(5));
            st.executeUpdate();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void changeContent(List<String> personList, List<String> changeList){
        try{
            String where = "WHERE \"Name\"='"+ personList.get(0)+"' AND \"Surname\"='"+ personList.get(1)+
                "' AND \"Fathername\"='"+ personList.get(2) + "';";
             if(!changeList.get(0).trim().equals("")){
                 PreparedStatement st = connection.prepareStatement("UPDATE studentInfo SET \"Birth_date\" = ?"
                         + where);
                 SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
                 Date d = format.parse(changeList.get(0));
                 long h = d.getTime();
                 java.sql.Date date = new java.sql.Date(h);
                 st.setDate(1, date);
                 st.executeUpdate();
             }
            if(!changeList.get(1).trim().equals("")){
                PreparedStatement st = connection.prepareStatement("UPDATE studentInfo SET \"Form\" = ?"
                        + where);
                st.setString(1, changeList.get(1));
                st.executeUpdate();
            }
            if(!changeList.get(2).trim().equals("")){
                PreparedStatement st = connection.prepareStatement("UPDATE studentInfo SET \"Phone_number\" = ?"
                        + where);
                st.setString(1, changeList.get(2));
                st.executeUpdate();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void deleteContent(List<String> personList){
        try{
            String where = "WHERE \"Name\"='"+ personList.get(0)+"' AND \"Surname\"='"+ personList.get(1)+
                    "' AND \"Fathername\"='"+ personList.get(2) + "';";
                PreparedStatement st = connection.prepareStatement("DELETE FROM studentInfo " + where);
                st.executeUpdate();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
