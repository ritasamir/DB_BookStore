package sample;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Controller {
    public void sellBook(String iSBN, int newQuantity) {
        try {
            java.sql.Connection con = model.Connection.getInstance();
            Statement st = con.createStatement();
            String sqlCommand ="UPDATE book SET Number_Of_Copies = "+newQuantity+" WHERE ISBN = \""+iSBN+"\";";
            PreparedStatement pstmt = con.prepareStatement(sqlCommand);
            pstmt.executeUpdate();
            st.close();
        } catch (Exception ex) {
            ex.printStackTrace();

        }

    }


    public String[][] search(String relation, String searchAttr, String searchVal) {
        String[][] result = null;
        try {
            java.sql.Connection con = model.Connection.getInstance();
            Statement st = con.createStatement();
            String sqlCommand=null;
            if(searchAttr=="null"){
                sqlCommand = "SELECT * FROM book_store." + relation +";";
            }else {
                sqlCommand = "SELECT * FROM book_store." + relation + " where " + searchAttr + " = \"" + searchVal + "\";";
            }
            ResultSet rs = st.executeQuery(sqlCommand);
            int columnsNumber = rs.getMetaData().getColumnCount();
            rs.last();
            int rowsNum = rs.getRow();
            rs.beforeFirst();
            //System.out.println(columnsNumber+"  "+rowsNum);
            result = new String[rowsNum][columnsNumber];
            int i=0;
            while (rs.next()) {
                for(int j=0;j<columnsNumber;j++)
                    result[i][j]=rs.getString(j+1);
                i++;
            }
            st.close();
            rs.close();
        } catch (Exception ex) {
            ex.printStackTrace();

        }
//        for(int i=0;i<result.length;i++) {
//            for(int j=0;j<result[0].length;j++) {
//                System.out.print(result[i][j]+"  ");
//
//            }
//            System.out.println();
//        }
        return result;
    }

}

