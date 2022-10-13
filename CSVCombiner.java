import com.opencsv.*;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVCombiner {
    public static void main(String[] args) throws FileNotFoundException {
        if(args.length < 2){
            System.out.println("Not enough arguments");
            return;
        }
        try{
            //Open writer
            ICSVWriter writer = new CSVWriterBuilder(new FileWriter(args[args.length-1])).build();

            //Write all CSV file contents
            for(int i = 0; i < args.length-1; i++){
                String inputFile = args[i];
                CSVReader reader = new CSVReaderBuilder(new FileReader(inputFile)).build();

                String[] nextLine;
                boolean firstLine = true;
                while ((nextLine = reader.readNext()) != null) {
                    if(firstLine){
                        if(i == 0){
                            writer.writeNext(appendToLine(nextLine, "filename"));
                        }
                        firstLine = false;
                        continue;
                    }
                    writer.writeNext(appendToLine(nextLine, inputFile));
                }

            }
            writer.close();
        }catch(IOException | CsvValidationException e){
            System.out.println(e.getMessage());
        }
    }

    private static String[] appendToLine(String[]line, String s){
        List<String>lineAsList = new ArrayList<String>(Arrays.asList(line));
        lineAsList.add(s);
        return lineAsList.toArray(new String[0]);
    }
}
