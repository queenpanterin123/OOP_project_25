import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Main {


    public static void main(String[] args) throws IOException {

        //wczytanie a
        BufferedReader a = new BufferedReader(new FileReader("inputData/data_a1.txt"));
        ArrayList<Readout> dataA = new ArrayList<>();
        String linea;
        while ((linea = a.readLine()) != null) {
            double nr = Double.parseDouble(linea);
            dataA.add(new Readout(nr));
        }
        System.out.println(getOutputInfo(dataA,0,"Task I.1","data_a1.txt"));

       //wczytanie b
        Logger logger_b = new LoggerFile("data_b.log");
        BufferedReader b = new BufferedReader(new FileReader("inputData/data_b1.txt"));
        ArrayList<Readout> dataB = new ArrayList<>();
        String lineb;
        int noInvalidRec=0;
        while ((lineb = b.readLine()) != null){
            try{
                double nr = Double.parseDouble(lineb);
                dataB.add(new Readout(nr));
            }catch(NumberFormatException e){
                noInvalidRec+=1;
                logger_b.log(Logger.Level.ERROR, "Faulty record in [data_b1.txt]: " + lineb);

            }
        }

        System.out.println(getOutputInfo(dataB,noInvalidRec,"Task I.2","data_b1.txt"));
        logger_b.flush();

        //wczytanie c
        Logger logger_c = new LoggerFile("data_c.log");

        BufferedReader c = new BufferedReader(new FileReader("inputData/data_c1.txt"));
        ArrayList<Readout> dataC = new ArrayList<>();
        String linec;
        int noInvalidRecC=0;
        while ((linec = c.readLine()) != null){
            try{
                String[] parts = linec.split(" ");
                double nr = Double.parseDouble(parts[0].strip());
                String uuid = parts[1].substring(3).strip();
                dataC.add(new ReadoutWithUuid(nr,uuid));
            }catch(NumberFormatException e){
                noInvalidRecC+=1;
                logger_c.log(Logger.Level.ERROR, "Faulty record in [data_c1.txt]: " + linec);
            }
        }

        System.out.println(getOutputInfo(dataC,noInvalidRecC,"Task II.1","data_c1.txt"));
        logger_c.flush();
    }





//funkcje moje
    static String getOutputInfo (ArrayList<Readout> data,
                                 int noInvRecords,
                                 String title,
                                 String filename){
        String sep="\n-----------------------\n";
        String txt=title+"\nMartyna Uranowska, 300979"+sep+"Data filename: "+filename+ "\n"+
                "\nLength of the series: "+data.size()+
                "\nMax value: " + getMax(data)+
                "\nMin value: " + getMin(data) +
                String.format("\nMean value: %.3f", getMean(data)) +
                "\nMedian: " +  getMedian(data)+
                "\nNumber of central elements: "+noOfCE(data);
        if(noInvRecords != 0){
            txt+="Number of invalid records: "+noInvRecords+sep;
        }
            return txt;
    }

    static Readout getMax(ArrayList<Readout> data) {
        Readout max = data.get(0);
        for (Readout number : data) {
            if (number.getValue() > max.getValue())
                max = number;
        }
        ;
        return max;
    }

    static Readout getMin(ArrayList<Readout> data) {
        Readout min = data.get(0);
        for (Readout number : data) {
            if (number.getValue() < min.getValue())
                min = number;
        }
        return min;
    }

    static double getMean(ArrayList<Readout> data) {
        double sum = 0;
        for (Readout number : data) {
            sum += number.getValue();
        }
        double mean = sum / data.size();
        return mean;
    }
//zmiana getMedian tak aby tylko wybierała odpowiednie elementy
    static MedianWrapper getMedian(ArrayList<Readout> data){
        ArrayList<Readout> s_data = new ArrayList<>(data);//kopiuję arraylist, zeby nie zmieniac naszej pierwotnej
        Collections.sort(s_data);

        int size = s_data.size();
        if( size%2!=0){
            int i = size/2;
            return new MedianWrapper(s_data.get(i));
        }else {
            int i = size / 2;
            return new MedianWrapper(s_data.get(i - 1),s_data.get(i));
        }
    }

    public static int noOfCE(ArrayList<Readout> data){
        double mean = getMean(data);
        double max= getMax(data).getValue();
        double min = getMin(data).getValue();
        double eps = (max-min)/100;
        ArrayList<Double> central = new ArrayList<>();
        double low = mean - eps;
        double high = mean + eps;
        for(Readout number: data){
            if(number.getValue() >low & number.getValue() <high){
                central.add(number.getValue());
            }
        }
        return central.size();
     }
}




