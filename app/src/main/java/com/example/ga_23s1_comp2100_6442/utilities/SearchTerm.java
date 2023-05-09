/**
import java.time.Duration;
import java.util.*;
import java.io.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import com.opencsv.CSVReader;

public class SearchTerm {
    public static void main(String[] args) throws IOException {
        String file_input = "D:\\eclipse-workspace\\Crawl1\\src\\main\\output\\crawl.csv";
        String file_output = "D:\\eclipse-workspace\\Crawl1\\src\\main\\output\\searchTerm2.csv";
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
        // System.out.println(stopword);

        List<String> author = new ArrayList<>();
        List<String> title = new ArrayList<>();
        List<String> skills = new ArrayList<>();

        {
            try {
                reader = new CSVReader(new FileReader(file_input));
                String[] line;
                while ((line = reader.readNext()) != null) {
                    author.add(line[0]);
                    title.add(line[1]);
                    skills.add(line[2]);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // System.out.println(author.size());
        // System.out.println(title.size());
        // System.out.println(skills.size());

        File output_file = new File(file_output);
        BufferedWriter bw = new BufferedWriter(new FileWriter(output_file, true));

        for(int i = 0; i < 2607; i++){
            String[] origin_author = author.get(i).toLowerCase().split(" ");
            String[] origin_title = title.get(i).toLowerCase().split(" ");
            String[] origin_skills = skills.get(i).toLowerCase().split(" ");

            StringBuilder author_builder = new StringBuilder();
            StringBuilder title_builder = new StringBuilder();
            StringBuilder skills_builder = new StringBuilder();

            for(String word : origin_author) {
                if(!stopword.contains(word)) {
                    author_builder.append(word);
                    author_builder.append(' ');
                }
            }
            for(String word : origin_title) {
                if(!stopword.contains(word)) {
                    title_builder.append(word);
                    title_builder.append(' ');
                }
            }
            for(String word : origin_skills) {
                if(!stopword.contains(word)) {
                    skills_builder.append(word);
                    skills_builder.append(' ');
                }
            }
            String author_result = author_builder.toString().trim().replaceAll(regEx, " ");
            String title_result = title_builder.toString().trim().replaceAll(regEx, " ");
            String skills_result = skills_builder.toString().trim().replaceAll(regEx, "");
            // System.out.println(author.get(i));
            // System.out.println(author_result);
            // System.out.println(title.get(i));
            // System.out.println(title_result);
            // System.out.println(skills.get(i));
            // System.out.println(skills_result);
            String[] author_split = author_result.split(" ");
            String[] title_split = title_result.split(" ");
            TreeSet<String> terms = new TreeSet<String>();
            for (String s : author_split){
                if(!s.isEmpty()){
                    terms.add(s);
                }
            }
            for (String s : title_split){
                if(!s.isEmpty()){
                    terms.add(s);
                }
            }
            String[] search_term = terms.stream().toArray(String[]::new);
            // System.out.println(Arrays.toString(search_term));
            bw.write(Arrays.toString(search_term));
            bw.newLine();
        }
        bw.close();
    }
}
 **/