import java.util.*;
import java.io.*;

import com.opencsv.CSVReader;

public class BigFilter {
    public static void main(String[] args) throws IOException {
        String file_crawl = "D:\\eclipse-workspace\\Crawl1\\src\\main\\output\\crawl.csv";
        String file_searchterm = "D:\\eclipse-workspace\\Crawl1\\src\\main\\output\\searchTerm.csv";
        String file_bFilter = "D:\\eclipse-workspace\\Crawl1\\src\\main\\output\\bigFilter.csv";
        final String regEx = "[\n`~!@#$%^&*()+=|{}':;'\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        CSVReader reader;
        reader = new CSVReader(new FileReader(file_searchterm));
        BufferedWriter bw = new BufferedWriter(new FileWriter(file_bFilter, true));

        String[] line;
        int dx = 0, ix = 0, cx = 0, index = 0;
        while ((line = reader.readNext()) != null){
            // System.out.println(Arrays.toString(line));
            index += 1;
            boolean flag = false;
            for(String s : line){
                s = s.trim().replaceAll(regEx, "");
                // System.out.println(s);
                if(s.equals("data")){
                    bw.write("Data Science");
                    bw.newLine();
                    flag = true;
                    dx += 1;
                    break;
                }
                else if(s.equals("it") || s.equals("engineer") || s.equals("development") ||
                        s.equals("technology") || s.equals("technologies") || s.equals("engineering") || s.equals("software")){
                    bw.write("Information Technology");
                    bw.newLine();
                    flag = true;
                    ix += 1;
                    break;
                }
            }
            if(!flag){
                bw.write("Computer Science");
                bw.newLine();
                cx += 1;
            }
        }
        bw.close();
        System.out.println(index);
        System.out.println(dx);
        System.out.println(ix);
        System.out.println(cx);
    }
}
