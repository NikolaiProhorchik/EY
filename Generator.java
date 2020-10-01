import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;
import java.io.FileWriter;

public class Generator{
    private static final String LAT_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstyvwxyz";
    private static final String RUS_ALPHABET = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдеёжзийклмнопрстуфхцчшщъыьэюя";

    private static void genDate(String[] date){
        GregorianCalendar calendar = new GregorianCalendar();

        calendar.set(GregorianCalendar.YEAR, rand(2015, 2019));
        calendar.set(GregorianCalendar.DAY_OF_YEAR, rand(1, calendar.getActualMaximum(GregorianCalendar.DAY_OF_YEAR)));


        date[0] = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        date[1] = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        date[2] = String.valueOf(calendar.get(Calendar.YEAR));
    }

    private static void genString(String alphabet, StringBuilder strBuilder){
        String ALPHABET;
        if (alphabet.equals("RUS")){
            ALPHABET = RUS_ALPHABET;
        }
        else{
            ALPHABET = LAT_ALPHABET;
        }

        for(int i = 0; i < 10; i++){
            strBuilder.append(ALPHABET.charAt(rand(0, ALPHABET.length() - 1)));
        }
    }

    private static String genInt(){
        return String.valueOf(rand(1, 100000000));
    }

    private static String genFloat(){
        Random random = new Random();
        return String.format("%.8f", rand(1, 19) + random.nextFloat()).replace(",", ".");
    }

    public static void genFile(String filename, int numOfLines) throws IOException{
        FileWriter writer = new FileWriter(filename);
        String[] date = new String[3];
        StringBuilder strBuilder = new StringBuilder();

        for(int i = 0; i < numOfLines; i++){
            Generator.genDate(date);
            writer.append(date[0])
                    .append(".")
                    .append(date[1])
                    .append(".")
                    .append(date[2])
                    .append("//");

            //заполняем StringBuilder строками из латинских и русских символов с разделителем
            Generator.genString("LAT", strBuilder);
            strBuilder.append("//");
            Generator.genString("RUS", strBuilder);
            writer.append(strBuilder);
            strBuilder.delete(0, strBuilder.length());

            writer.append("//")
                    .append(Generator.genInt())
                    .append("//")
                    .append(Generator.genFloat())
                    .append("//\n");
        }
        writer.close();
    }

    private static int rand(int begin, int end){
        Random random = new Random();
        return begin + random.nextInt(end - begin + 1);
    }
}
