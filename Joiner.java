import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

public class Joiner{
    private static File fileForReading;
    private static File fileForWriting;
    private static BufferedReader reader;
    private static BufferedWriter writer;
    private static BufferedWriter writerJoiner;
    private static String line;

    //объединение файлов с возможностью удаления
    public static int joinFiles(String path, String filename, String strToken, int numOfFiles, int numOfLines) throws IOException{
        writerJoiner = new BufferedWriter(new FileWriter(path + filename));

        int counter = 0;

        for(int i = 0; i < numOfFiles; i++){
            initReaderAndWriter(path, i + 1);

            //считываем строку; учитывая требуемую комбинацию, принимаем решение о добвлении
            for(int j = 0; j < numOfLines; j++){
                if (!(line = reader.readLine()).contains(strToken)){
                    writeInFiles();
                }
                else{
                    counter++;
                }
            }

            closeReaderAndWriter();
            renameFile();
        }
        writerJoiner.close();

        return counter;
    }

    //объединение фалов
    public static void joinFiles(String path, String filename, int numOfFiles, int numOfLines) throws IOException{
        writerJoiner = new BufferedWriter(new FileWriter(path + filename));

        for(int i = 0; i < numOfFiles; i++){
            initReaderAndWriter(path, i + 1);

            for(int j = 0; j < numOfLines; j++){
                line = reader.readLine();
                writeInFiles();
            }

            closeReaderAndWriter();
            renameFile();
        }
        writerJoiner.close();
    }

    private static void initReaderAndWriter(String path, int num) throws IOException{
        fileForReading = new File(path + String.format("File%d.txt", num));
        reader = new BufferedReader(new FileReader(fileForReading));

        fileForWriting  = new File(path + String.format("File(new)%d.txt", num));
        writer = new BufferedWriter(new FileWriter(fileForWriting));
    }

    private static void writeInFiles() throws IOException{
        writer.append(line).append("\n");
        writerJoiner.append(line).append("\n");
    }

    private static void closeReaderAndWriter() throws IOException{
        reader.close();
        writer.close();
    }

    private static void renameFile(){
        fileForReading.delete();
        fileForWriting.renameTo(fileForReading);
    }
}
