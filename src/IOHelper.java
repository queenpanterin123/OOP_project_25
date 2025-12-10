import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class IOHelper {
    static String getOutputInfo (FileContent fContent,
                                 String title){
        ArrayList<Readout> data=fContent.getData();
        int noInvRecords=fContent.getNoOfInvalidRecords();
        String sep="\n-----------------------\n";
        String txt=title+"\nMartyna Uranowska, 300979"+sep+"Data filename: "+fContent.getFileName()+ "\n"+
                "\nLength of the series: "+data.size()+
                "\nMax value: " + getMax(data)+
                "\nMin value: " + getMin(data) +
                String.format("\nMean value: %.3f", getMean(data)) +
                "\nMedian: " +  getMedian(data)+
                "\nNumber of central elements: "+noOfCE(data);
        if(noInvRecords != 0){
            txt+="\nNumber of invalid records: "+noInvRecords+sep;
        }
        return txt;
    }

    static FileContent readFile(String filename,
                                Logger logger) throws IOException {

        BufferedReader x = new BufferedReader(new FileReader("inputData/"+filename));
        ArrayList<Readout> data = new ArrayList<>();
        String line;
        int noInvalidRec=0;
        while ((line = x.readLine()) != null){
            try{
                String[] parts = line.split(" ");

                //dla jednej czesci
                if (parts.length==1){
                    double nr = Double.parseDouble(parts[0]);
                    data.add(new Readout(nr));
                }
                else if (parts.length==2){
                    double nr = Double.parseDouble(parts[0].strip());
                    String uuid = parts[1].substring(3).strip();
                    data.add(new ReadoutWithUuid(nr,uuid));
                }
            }catch(NumberFormatException e){
                noInvalidRec+=1;
                logger.log(Logger.Level.ERROR, "Faulty record in [data_c1.txt]: " + line);
            }
        }
        FileContent fc=new FileContent(data,noInvalidRec,filename);
        return fc;
    }
}
