//package com.example.ga_23s1_comp2100_6442.ultilities;
//import java.time.Duration;
//import java.util.*;
//import java.io.*;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//
//public class Crawl {
//    public static void main(String[] args) throws IOException {
//        String file_address = "D:\\eclipse-workspace\\Crawl1\\src\\main\\output\\crawl.csv";
//        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Meghann\\Downloads\\chromedriver_win32\\chromedriver.exe");
//        for (int index = 1; index <= 84; index++) {
//            String test_url = String.format(
//                    "https://www.coursera.org/search?page=%d&index=prod_all_products_term_optimization_language_filter&topic=Information%%20Technology",
//                    index);
//            File csv = new File(file_address);
//            BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true));
//
//            WebDriver driver = new ChromeDriver();
//            driver.manage()
//                    .timeouts()
//                    .implicitlyWait(Duration.ofSeconds(30));
//            driver.get(test_url);
//
//            List<WebElement> author = driver.findElements(By.cssSelector(".css-2fzscr"));
//            List<WebElement> title = driver.findElements(By.cssSelector(".css-bku0rr"));
//            List<WebElement> skills = driver.findElements(By.cssSelector(".css-5or6ht"));
//
//            if(author.size() != title.size() || author.size() != skills.size()){
//                bw.close();
//                driver.quit();
//                continue;
//            }
//            System.out.println(index);
//            System.out.println(author.size());
//            System.out.println(title.size());
//            System.out.println(skills.size());
//            for (int i = 0; i < author.size(); i++) {
//                String a = author.get(i).getText();
//                // System.out.println(a);
//                String t = title.get(i).getText();
//                // System.out.println(t);
//                String s = skills.get(i).getText().substring(9);
//                // System.out.println(s);
//                bw.write("\""+a+"\""+",");
//                bw.write("\""+t+"\""+",");
//                bw.write("\""+s+"\"");
//                bw.newLine();
//            }
//            bw.close();
//            driver.quit();
//        }
//    }
//}
