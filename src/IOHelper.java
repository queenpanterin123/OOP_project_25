import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class IOHelper {
    static String getOutputInfo (FileContent fContent,
                                 String title){
        ArrayList<Sensor> sensors=fContent.getSensors();
        String sep="\n-----------------------\n";
        String sep2="\n----\n";
        String txt=title+"\nMartyna Uranowska, 300979"+sep+"Data filename: "+fContent.getFileName()+ "\n";
        for (Sensor sensor: fContent.getSensors()){
            txt+=sep2+"Sensor: " + sensor.getName();
            txt+="\nLength of the series: "+sensor.getLengthOfData()+
                    "\nMax value: " + sensor.getMax()+
                    "\nMin value: " + sensor.getMin() +
                    String.format("\nMean value: %.3f", sensor.getMean()) +
                    "\nMedian: " +  sensor.getMedian()+
                    "\nNumber of central elements: "+sensor.noOfCE();
        }
        int noInvRecords=fContent.getNoOfInvalidRecords();
        if(noInvRecords != 0){
            txt+="\nNumber of invalid records: "+noInvRecords+sep;
        }
        return txt;
    }

    static FileContent readFile(String filename,
                                Logger logger) throws IOException {

        BufferedReader x = new BufferedReader(new FileReader("inputData/"+filename));
        ArrayList<Sensor> sensors = new ArrayList<>();
        String line;
        int noInvalidRec=0;
        while ((line = x.readLine()) != null){
            try{
                String[] parts = line.split(" ");

                //dla jednej czesci
                if (parts.length==1){
                    double nr = Double.parseDouble(parts[0]);
                    addReadoutToSensor(sensors,"<N/A>",new Readout(nr));
                }
                else if (parts.length==2){
                    double nr = Double.parseDouble(parts[0].strip());
                    String uuid = parts[1].substring(3).strip();
                    addReadoutToSensor(sensors,"<N/A>",new ReadoutWithUuid(nr,uuid));
                }
                else if(parts.length==3){
                    double nr = Double.parseDouble(parts[0].strip());
                    String uuid = parts[1].substring(3).strip();
                    String sensorName=parts[2];
                    addReadoutToSensor(sensors,sensorName,new ReadoutWithUuid(nr,uuid));
                }
            }catch(NumberFormatException | ArrayIndexOutOfBoundsException e){
                noInvalidRec+=1;
                logger.log(Logger.Level.ERROR, "Faulty record in " + filename + line);
            }
        }
        return new FileContent(sensors,noInvalidRec,filename);
    }
    private static void addReadoutToSensor(ArrayList<Sensor> sensorList,
                                    String sensorName, Readout readout) {
        boolean isInSensorList = false;
        for (Sensor sensor : sensorList) {
            if (Objects.equals(sensor.getName(), sensorName)) {
                sensor.addReadout(readout);
                isInSensorList = true;
                break;
            }
        }
        if (isInSensorList == false){
            Sensor newSensor = new Sensor(sensorName);
            sensorList.add(newSensor);
            newSensor.addReadout(readout);

        };
    }
}
