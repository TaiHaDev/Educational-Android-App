import java.util.*;
import java.io.*;

import com.opencsv.CSVReader;

public class Description {
    public static void main(String[] args) throws IOException {
        String file_input = "D:\\eclipse-workspace\\Crawl1\\src\\main\\output\\crawl.csv";
        String file_output = "D:\\eclipse-workspace\\Crawl1\\src\\main\\output\\description.csv";
        String stop_words = "D:\\eclipse-workspace\\Crawl1\\src\\main\\java\\english_stopwords";
        final String regEx = "[\n`~!@#$%^&*()+=|{}':;'\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";

        CSVReader reader;
        BufferedReader br1 = new BufferedReader(new FileReader(stop_words));
        String string1 = null;
        ArrayList<String> stopword = new ArrayList();
        while ((string1 = br1.readLine()) != null) {
            stopword.add(string1);
        }
        br1.close();
        List<String> skills = new ArrayList<>();
        {
            try {
                reader = new CSVReader(new FileReader(file_input));
                String[] line;
                while ((line = reader.readNext()) != null) {
                    skills.add(line[2]);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        File output_file = new File(file_output);
        BufferedWriter bw = new BufferedWriter(new FileWriter(output_file, true));

        TreeSet<String> descripts = new TreeSet<String>();
        Map<String, Integer> des_map = new HashMap<>();
        for(int i = 0; i < 2607; i++) {
            String[] origin_skills = skills.get(i).toLowerCase().split(" ");

            StringBuilder skills_builder = new StringBuilder();

            for (String word : origin_skills) {
                if (!stopword.contains(word)) {
                    skills_builder.append(word);
                    skills_builder.append(' ');
                }
            }
            String skills_result = skills_builder.toString().trim().replaceAll(regEx, "");
            String[] skill_split = skills_result.split(",");
            TreeSet<String> terms = new TreeSet<String>();
            for (String s : skill_split){
                s = s.trim();
                if(!s.isEmpty()){
                    terms.add(s);
                }
                // System.out.println(s);
                if(des_map.containsKey(s)) {
                    int v = des_map.get(s);
                    des_map.put(s, v+1);
                }
                else {
                    des_map.put(s, 1);
                }
                descripts.add(s);
            }
            String[] descrip_term = terms.stream().toArray(String[]::new);
            // System.out.println(Arrays.toString(search_term));
            /*
            bw.write(Arrays.toString(descrip_term));
            bw.newLine();
             */
        }
        bw.close();
        String[] description = descripts.stream().toArray(String[]::new);
        // System.out.println(Arrays.toString(description));
        System.out.println(description.length);
        System.out.println(des_map.size());
        Set<Map.Entry<String, Integer>>  entrySet  = des_map.entrySet();


        for (Map.Entry<String, Integer> eset : entrySet){
            String key = eset.getKey();
            int value = eset.getValue();
            if(value > 400 && value < 600){
                System.out.print(key+" "+value+ "\n");
            }
        }

    }
}
