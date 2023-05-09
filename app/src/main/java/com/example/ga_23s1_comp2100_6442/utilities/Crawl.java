import java.time.Duration;
import java.util.*;
import java.io.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Crawl {
    public static void main(String[] args) throws IOException, InterruptedException {
        String file_address = "D:\\eclipse-workspace\\Crawl1\\src\\main\\output\\crawl_IT.csv";
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Meghann\\Downloads\\chromedriver_win32\\chromedriver.exe");
        for (int index = 1; index <= 84; index++) {
            String test_url = String.format(
                    "https://www.coursera.org/search?page="+index+"&index=prod_all_products_term_optimization_language_filter&topic=Information%%20Technology");
            File csv = new File(file_address);
            BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true));

            ChromeOptions options = new ChromeOptions();
            options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
            options.setExperimentalOption("useAutomationExtension", false);
            options.addArguments("user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.198 Safari/537.36");
            options.addArguments("--disable-blink-features=AutomationControlled");
            WebDriver driver = new ChromeDriver();

            driver.manage()
                    .timeouts()
                    .implicitlyWait(Duration.ofSeconds(20));
            driver.get(test_url);

            try {
                List<WebElement> author = driver.findElements(By.cssSelector(".cds-ProductCard-partnerNames"));
                List<WebElement> title = driver.findElements(By.cssSelector(".cds-CommonCard-title"));
                List<WebElement> skills = driver.findElements(By.cssSelector(".cds-CommonCard-bodyContent"));
                List<WebElement> pics = driver.findElements(By.cssSelector(".cds-CommonCard-previewImage img"));

                if(author.size() != title.size() || author.size() != skills.size() || author.size() != pics.size()){
                    bw.close();
                    driver.quit();
                    continue;
                }

                System.out.println(index);
                System.out.println(test_url);
                System.out.println(author.size());
                System.out.println(title.size());
                System.out.println(skills.size());
                System.out.println(pics.size());

                for (int i = 0; i < author.size(); i++) {
                    String a = author.get(i).getText();
                    //System.out.println(a);
                    String t = title.get(i).getText();
                    //System.out.println(t);
                    String s = skills.get(i).getText().substring(8);
                    //System.out.println(pics.get(i));
                    String p = pics.get(i).getAttribute("src");
                    //System.out.println(p);
                    bw.write("\""+a+"\""+",");
                    bw.write("\""+t+"\""+",");
                    bw.write("\""+s+"\""+",");
                    bw.write("\""+p+"\"");
                    bw.newLine();
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                bw.close();
                driver.quit();
            }
        }
    }
}
