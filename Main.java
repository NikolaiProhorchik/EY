import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;

public class Main{
    public static void main(String args[ ]) throws IOException, SQLException{
        String path = Paths.get("src/Files").toAbsolutePath() + "\\";
        int numOfFiles = 100;
        int numOfLines = 100000;

        while(true) {
            for (int i = 0; i < numOfFiles; i++) {
                Generator.genFile(path + String.format("File%d.txt", i + 1), numOfLines);
            }
            int counter = Joiner.joinFiles(path, "JoinedFile.txt", "JMDdx", numOfFiles, numOfLines);
            System.out.println(String.format("Количество удалённых строк: %d.", counter));
            DataBaseConnector.importFiles(path, numOfFiles, numOfLines);
            System.out.println(String.format("Сумма целых чисел: %d.", DataBaseConnector.countSum()));
            System.out.println(String.format("Среднее дробных чисел: %f.", DataBaseConnector.countMean()));
        }
    }
}