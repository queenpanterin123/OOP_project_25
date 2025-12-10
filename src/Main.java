import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Main {


    public static void main(String[] args) throws IOException {
        // task I.1
        processOneFile("data_a1.txt","data_a.log","Task I.1");
        // task I.2
        processOneFile("data_b1.txt","data_b.log","Task I.2");
    // task II.2
        processOneFile("data_c1.txt","data_c.log","Task II.2");

        //PRZED TASK III
        /* //wczytanie a
        BufferedReader a = new BufferedReader(new FileReader("inputData/data_a1.txt"));
        ArrayList<Readout> dataA = new ArrayList<>();
        int noInvRecA=0;
        String linea;
        while ((linea = a.readLine()) != null) {
            double nr = Double.parseDouble(linea);
            dataA.add(new Readout(nr));
        }
        FileContent fcA=new FileContent(dataA,noInvRecA,"data_a1.txt");
        System.out.println(getOutputInfo(fcA,"Task I.1"));

       //wczytanie b
        Logger logger_b = new LoggerFile("data_b.log");
        BufferedReader b = new BufferedReader(new FileReader("inputData/data_b1.txt"));
        ArrayList<Readout> dataB = new ArrayList<>();
        String lineb;
        int noInvalidRecB=0;
        while ((lineb = b.readLine()) != null){
            try{
                double nr = Double.parseDouble(lineb);
                dataB.add(new Readout(nr));
            }catch(NumberFormatException e){
                noInvalidRecB+=1;
                logger_b.log(Logger.Level.ERROR, "Faulty record in [data_b1.txt]: " + lineb);

            }
        }
        FileContent fcB=new FileContent(dataB,noInvalidRecB,"data_b1.txt");
        System.out.println(getOutputInfo(fcB,"Task I.2"));

        //System.out.println(getOutputInfo(dataB,noInvalidRec,"Task I.2","data_b1.txt"));
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
        FileContent fcC=new FileContent(dataC,noInvalidRecC,"data_c1.txt");
        System.out.println(getOutputInfo(fcC,"Task II.1"));

        //System.out.println(getOutputInfo(dataC,noInvalidRecC,"Task II.1","data_c1.txt"));
        logger_c.flush();

         */
    }


        //od Task III
        static void processOneFile(String filename, String logFilename,
                                   String title) throws IOException{
            Logger logger = new LoggerFile(logFilename);
            FileContent fContent = IOHelper.readFile(filename, logger);
            System.out.println(IOHelper.getOutputInfo(fContent,title));
            logger.flush();
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




