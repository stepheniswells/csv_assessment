import com.opencsv.exceptions.CsvValidationException;
import org.junit.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class CSVCombinerTest {
    CSVCombiner csvCombiner = new CSVCombiner();
    private static final String invalidFileName1 = "aaaaa";
    private static final String invalidFileName2 = "nonExistentFile.pdf";
    private static final String emptyFileName = "emptyFile.txt";
    private static final String outputFileName = "output.txt";
    private static final String validFileName = "input.csv";
    private static final String validFileName2 = "input2.csv";
    private static final String mockCSV = "val1,val2,val3";
    private static final String mockCSV2 = "x,y,z";
    private static final String mockCSVWithCommas = "Wells\",\"Stephen,September,Dallas";

    @Test
    public void invalidFileNames(){
        assertThrows(CsvValidationException.class, () ->
            {csvCombiner.main(new String[]{invalidFileName1, invalidFileName2});});
    }

    @Test
    public void readFromEmptyFile() throws IOException {
        File emptyFile = new File(emptyFileName);
        clearFile(emptyFileName);
        csvCombiner.main(new String[]{emptyFileName, outputFileName});
        BufferedReader reader = new BufferedReader(new FileReader(outputFileName));
        assertTrue(reader.readLine() == null);
        clearFile(outputFileName);
    }

    @Test
    public void readFromSingleFile() throws IOException {
        File file = new File(validFileName);
        BufferedWriter writer = new BufferedWriter(new FileWriter(validFileName));
        writer.write(mockCSV);
        csvCombiner.main(new String[]{emptyFileName, outputFileName});
        BufferedReader reader = new BufferedReader(new FileReader(outputFileName));
        String output = reader.readLine();
        assertTrue(output.equals(mockCSV + "," + validFileName));
        clearFile(outputFileName);
    }

    @Test
    public void readFromFilesWhereSecondFileHasNoCSV() throws IOException {
        File file = new File(validFileName);
        BufferedWriter writer = new BufferedWriter(new FileWriter(validFileName));
        writer.write(mockCSV);

        File file2 = new File(validFileName2);
        writer = new BufferedWriter(new FileWriter(validFileName2));
        writer.write(mockCSV2);

        csvCombiner.main(new String[]{validFileName, validFileName2, outputFileName});
        BufferedReader reader = new BufferedReader(new FileReader(outputFileName));

        String output = reader.readLine();
        assertTrue(output.equals(mockCSV + "," + validFileName));

        output = reader.readLine();
        assertTrue(output.equals(null));

        clearFile(outputFileName);
    }

    @Test
    public void readFromMultipleFiles() throws IOException {
        File file = new File(validFileName);
        BufferedWriter writer = new BufferedWriter(new FileWriter(validFileName));
        writer.write(mockCSV);

        File file2 = new File(validFileName2);
        writer = new BufferedWriter(new FileWriter(validFileName2));
        writer.write(mockCSV2);
        writer.write(mockCSV2);

        csvCombiner.main(new String[]{validFileName, validFileName2, outputFileName});
        BufferedReader reader = new BufferedReader(new FileReader(outputFileName));

        String output = reader.readLine();
        assertTrue(output.equals(mockCSV + "," + validFileName));

        output = reader.readLine();
        assertTrue(output.equals(mockCSV2 + "," + validFileName2));

        clearFile(outputFileName);
    }

    @Test
    public void readFromFileWithValuesContainingCommas() throws IOException {
        File file = new File(validFileName);
        BufferedWriter writer = new BufferedWriter(new FileWriter(validFileName));
        writer.write(mockCSVWithCommas);
        csvCombiner.main(new String[]{emptyFileName, outputFileName});
        BufferedReader reader = new BufferedReader(new FileReader(outputFileName));
        String output = reader.readLine();
        assertTrue(output.equals(mockCSVWithCommas + "," + validFileName));
        clearFile(outputFileName);
    }

    private void clearFile(String filename) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(filename);
        writer.print("");
        writer.close();
    }
}