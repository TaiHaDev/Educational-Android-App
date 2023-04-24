package com.example.ga_23s1_comp2100_6442.ultilities;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.net.*;


public class Crawl {
    public static void main(String[] args) {
        String test_url = "https://www.coursera.org/search?page=1&index=prod_all_products_term_optimization_language_filter&topic=Computer%20Science";
        try {
            URL url = new URL(test_url);
            URLConnection conn = url.openConnection();
            InputStream is = conn.getInputStream();
            System.out.println(conn.getContentEncoding());
            BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
            String line = null;
            while((line=br.readLine())!=null){
                System.out.println(line);
            }
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
