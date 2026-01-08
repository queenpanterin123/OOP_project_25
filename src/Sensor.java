import java.util.ArrayList;
import java.util.Collections;

public class Sensor implements Comparable<Sensor> {
    private String name;
    private ArrayList<Readout> data=new ArrayList<>();
    public Sensor(String name){
        this.name=name;
    }
    public String getName() {
        return name;
    }
    public void addReadout(Readout readout){
        data.add(readout);
    }
    public ArrayList<Readout> getData(){
        return data;
    }
    public int getLengthOfData(){
        return data.size();
    }

    @Override
    public int compareTo(Sensor other){
        return this.name.compareTo(other.name);
    }

    public Readout getMax(Logger logger) {
        Readout max = data.get(0);
        for (Readout number : data) {
            if (number.getValue() > max.getValue())
                max = number;
        }
        logger.log(Logger.Level.MAX_ELEM,"Max. element for sensor [" + name + "]: "+max);
        return max;
    }

    public Readout getMin(Logger logger) {
        Readout min = data.get(0);
        for (Readout number : data) {
            if (number.getValue() < min.getValue())
                min = number;
        }
        logger.log(Logger.Level.MIN_ELEM,"Min. element for sensor [" + name + "]: "+min);
        return min;
    }

    public double getMean() {
        double sum = 0;
        for (Readout number : data) {
            sum += number.getValue();
        }
        double mean = sum / data.size();
        return mean;
    }
    //zmiana getMedian tak aby tylko wybierała odpowiednie elementy
    public MedianWrapper getMedian(){
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

    public int noOfCE(Logger logger){
        ArrayList<Double> central = new ArrayList<>();
        double mean = getMean();
        double eps = (getMax().getValue() - getMin().getValue()) / 100;
        double low = mean - eps;
        double high = mean + eps;
        int count = 0;
        for(Readout number: data){
            if(number.getValue() >low & number.getValue() <high){
                logger.log(Logger.Level.CENTRAL_ELEM,"Central element for sensor [" + name + "]: " + number);
                count++;
            }
        }
        return count;
    }
}
