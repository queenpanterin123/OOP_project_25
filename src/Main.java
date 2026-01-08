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
        //Task III.3
        processOneFile("data_d1.txt","data_b.log","Task III.3");
        //Task III.4
        processOneFile("data_e1.txt","data_e.log","Task III.3");
    }


        //od Task III
        static void processOneFile(String filename, String logFilename,
                                   String title) throws IOException{
            Logger logger = new LoggerFile(logFilename);
            FileContent fContent = IOHelper.readFile(filename, logger);
            System.out.println(IOHelper.getOutputInfo(fContent,title));
            logger.flush();
        }
}




