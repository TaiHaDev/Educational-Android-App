import com.opencsv.CSVReader;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class descriptionFilter {
    public static void main(String[] args) throws IOException {
        String file_descript = "D:\\eclipse-workspace\\Crawl1\\src\\main\\output\\description.csv";
        String file_dFilter = "D:\\eclipse-workspace\\Crawl1\\src\\main\\output\\descriptFilter.csv";
        final String regEx = "[\n`~!@#$%^&*()+=|{}':;'\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        CSVReader reader;
        reader = new CSVReader(new FileReader(file_descript));
        BufferedWriter bw = new BufferedWriter(new FileWriter(file_dFilter, true));

        String[] line;
        int index = 0, sax = 0, smlx = 0, ax = 0, sx = 0, adx = 0, ox = 0;
        while ((line = reader.readNext()) != null){
            // System.out.println(Arrays.toString(line));
            index += 1;
            // System.out.println(index);
            boolean flag = false;
            for(String s : line){
                s = s.trim().replaceAll(regEx, "");
                // System.out.println(s);
                if(s.equals("software architecture")){
                    bw.write("Software Architecture");
                    bw.newLine();
                    flag = true;
                    sax += 1;
                    break;
                }
                else if(s.equals("statistical machine learning")){
                    bw.write("Statistical Machine Learning");
                    bw.newLine();
                    flag = true;
                    smlx += 1;
                    break;
                }
                else if(s.equals("algorithms")){
                    bw.write("Algotithms");
                    bw.newLine();
                    flag = true;
                    ax += 1;
                    break;
                }
                else if(s.equals("sales")){
                    bw.write("Sales");
                    bw.newLine();
                    flag = true;
                    sx += 1;
                    break;
                }
                else if(s.equals("application development")){
                    bw.write("Application Development");
                    bw.newLine();
                    flag = true;
                    adx += 1;
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
        System.out.println(sax);
        System.out.println(smlx);
        System.out.println(ax);
        System.out.println(sx);
        System.out.println(adx);
        System.out.println(ox);
    }
}

