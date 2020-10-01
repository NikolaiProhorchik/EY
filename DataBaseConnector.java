import java.io.*;
import java.sql.*;

public class DataBaseConnector {
    private static final String url = "jdbc:mysql://localhost:3306/DATABASE1?serverTimezone=Europe/Minsk";
    private static final String user = "root";
    private static final String password = "49MyData";

    private static Connection con;
    private static Statement stmt;
    private static String query;

    public static void importFiles(String path, int numOfFiles, int numOfLines) throws IOException, SQLException{
        BufferedReader reader;
        String line;
        String[] dataFromLine;
        String[] date;
        int counter = 0;
        int totalNumberOfLines = numOfLines * numOfFiles;

        System.out.println(String.format("Строк импортировано/осталось импортировать: 0/%d.", totalNumberOfLines));

        con = DriverManager.getConnection(url, user, password);
        stmt = con.createStatement();

        for(int i = 0; i < numOfFiles; i++){
            reader = new BufferedReader(new FileReader(path + String.format("File%d.txt", i + 1)));

            //считываем строку, разделённую сепараторами; учитываем, что дата имеет точку-сепаратор
            for(int j = 0; j < numOfLines; j++){
                line = reader.readLine();
                dataFromLine = line.split("//");
                date = dataFromLine[0].split("\\.");
                query = String.format("INSERT INTO DATA1 VALUES('%s-%s-%s', '%s', '%s', %s, %s);",
                        date[2], date[1], date[0], dataFromLine[1], dataFromLine[2], dataFromLine[3], dataFromLine[4]);
                stmt.executeUpdate(query);
                counter++;
                System.out.println(
                        String.format("Строк импортировано/осталось импортировать: %d/%d.",
                                counter, totalNumberOfLines - counter));
            }

            reader.close();
        }

        con.close();
        stmt.close();
    }

    public static long countSum() throws SQLException{
        ResultSet rs;

        con = DriverManager.getConnection(url, user, password);
        stmt = con.createStatement();

        query = "SELECT SUM(INT_NUMBER) FROM DATA1";
        stmt.executeQuery(query);
        rs = stmt.executeQuery(query);
        rs.next();
        long sum = rs.getLong(1);

        con.close();
        stmt.close();
        rs.close();

        return sum;
    }

    public static float countMean() throws SQLException{
        ResultSet rs;

        con = DriverManager.getConnection(url, user, password);
        stmt = con.createStatement();

        query = "SELECT AVG(FLOAT_NUMBER) FROM DATA1";
        stmt.executeQuery(query);
        rs = stmt.executeQuery(query);
        rs.next();
        float mean =  rs.getFloat(1);

        con.close();
        stmt.close();
        rs.close();

        return mean;
    }
}
