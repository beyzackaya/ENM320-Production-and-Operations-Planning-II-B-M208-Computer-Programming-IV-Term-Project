
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class ReadZtable {

    public static double calculateZ(double value) {

        String filePath = "./../zChart.tsv";
        String line;
        String delimiter = "\t";
        double min_z =0 ;
        double min_distance=15000;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(delimiter);
                    if((Math.abs(Double.parseDouble(columns[1])-value) < min_distance)){
                        min_z=Double.parseDouble(columns[0]);
                        min_distance=(Math.abs(Double.parseDouble(columns[1])-value));
                    }




            }
        } catch (IOException e) {
            System.out.println(filePath);
        }


        return min_z;
    }

    public static double findL(double z) {
        String filePath = "./../zChart.tsv";
        String line;
        String delimiter = "\t";
        double min_Lz =0 ;
        double min_distance=15000;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(delimiter);
                if((Math.abs(Double.parseDouble(columns[0])-z) < min_distance)){
                    min_Lz=Double.parseDouble(columns[2]);
                    min_distance=(Math.abs(Double.parseDouble(columns[0])-z));
                }




            }
        } catch (IOException e) {
            System.out.println(filePath);
        }


        return min_Lz;
    }


}