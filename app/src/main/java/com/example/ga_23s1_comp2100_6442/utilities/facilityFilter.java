import com.opencsv.CSVReader;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class facilityFilter {
    public static void main(String[] args) throws IOException {
        String file_crawl = "D:\\eclipse-workspace\\Crawl1\\src\\main\\output\\crawl.csv";
        String file_searchterm = "D:\\eclipse-workspace\\Crawl1\\src\\main\\output\\searchTerm.csv";
        String file_fFilter = "D:\\eclipse-workspace\\Crawl1\\src\\main\\output\\facilityFilter.csv";
        final String regEx = "[\n`~!@#$%^&*()+=|{}':;'\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        CSVReader reader;
        reader = new CSVReader(new FileReader(file_searchterm));
        BufferedWriter bw = new BufferedWriter(new FileWriter(file_fFilter, true));

        String[] line;
        int gx = 0, ix = 0, ux = 0, ox = 0, index = 0;
        while ((line = reader.readNext()) != null){
            // System.out.println(Arrays.toString(line));
            index += 1;
            boolean flag = false;
            for(String s : line){
                s = s.trim().replaceAll(regEx, "");
                // System.out.println(s);
                if(s.equals("google")){
                    bw.write("Google");
                    bw.newLine();
                    flag = true;
                    gx += 1;
                    break;
                }
                else if(s.equals("ibm")){
                    bw.write("IBM");
                    bw.newLine();
                    flag = true;
                    ix += 1;
                    break;
                }
                else if(s.equals("university")){
                    bw.write("University");
                    bw.newLine();
                    flag = true;
                    ux += 1;
                    break;
                }
            }
            if(!flag){
                bw.write("Others");
                bw.newLine();
                ox += 1;
            }
        }
        bw.close();
        System.out.println(index);
        System.out.println(gx);
        System.out.println(ix);
        System.out.println(ux);
        System.out.println(ox);
    }
}
