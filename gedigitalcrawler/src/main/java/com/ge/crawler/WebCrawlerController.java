package com.ge.crawler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.ge.crawler.model.Page;


/**
 * 
 * For the purposes of this project, we define the “Internet” 
 * as the test JSON file included and a web crawler as software that requests pages
 * from the “Internet”, parses the content to extract all the links in the page,
 * and visits the links to crawl those pages, to an infinite depth
 * 
 * @author Rajesh Ranjan
 * 
 * 04/27/2019
 *
 */

@Controller
@RequestMapping("/gedigital")
public class WebCrawlerController {
	
	private static ArrayList<Page> net;
    private static final String NET_ONE=readFile("internet_1.json");
    
    
    /*
     * read json file as string
     */
    public static String readFile(String filename) {
        String result = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            result = sb.toString();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
	  @RequestMapping("/crawler")
	  public void crawlGeDigitalPage() throws IOException {
	   
	        prompt("TEST 1");
	        run(NET_ONE);
		  
		/*
		*we can add further resource to parallel execution
		*run(NET_two);
		*run(NET_three);
		*/
	  }
	  
	  
	  
	  private static void prompt(String n) {
	        System.out.print("\nPRESS <ENTER> TO RUN " + n);
	        new Scanner(System.in).nextLine();
	  }
	  
	

	    private static void run(String json) throws IOException {
	        net = new ArrayList<>();
	        parse(json);
	        crawl();
	    }

	    private static void parse(String json) throws IOException {
	        Page p = new Page();
	        JsonParser parser = new JsonFactory().createJsonParser(json);
	        parser.nextToken();
	        while (parser.hasCurrentToken()) {
	            String tok = parser.getCurrentName();
	            if ("address".equals(tok)) {
	                parser.nextToken();
	                p.setAddress(parser.getText());
	            }
	            if ("links".equals(tok)) {
	                parser.nextToken();
	                ObjectMapper mapper = new ObjectMapper();
	                ArrayNode node = mapper.readTree(parser);
	                Iterator<JsonNode> iterator = node.elements();
	                String[] array = new String[node.size()];
	                for (int i = 0; i < node.size(); i++) {
	                    if (iterator.hasNext()) {
	                        array[i] = iterator.next().asText();
	                    }
	                }
	                p.setLinks(array);
	                net.add(p);
	                p = new Page();
	            }
	            parser.nextToken();
	        }
	    }

	    private static void crawl() throws IOException {
	        ArrayList<String> valid = new ArrayList<>();
	        ArrayList<String> links = new ArrayList<>();
	        for (Page p : net) {
	            String a = p.getAddress();
	            valid.add(a);
	            Collections.addAll(links, p.getLinks());
	        }
	        TreeSet<String> visited = new TreeSet<>();
	        TreeSet<String> skipped = new TreeSet<>();
	        TreeSet<String> invalid = new TreeSet<>();
	        visited.add(valid.get(0));
	        for (String l : links) {
	            if (valid.contains(l)) {
	                if (!visited.contains(l)) {
	                    visited.add(l);
	                } else {
	                    skipped.add(l);
	                }
	            } else {
	                invalid.add(l);
	            }
	        }
	        System.out.println("\nSUCCESS:");
	        for (String s : visited) {
	            System.out.println(s);
	        }
	        System.out.println("\nSKIPPED:");
	        if (skipped.isEmpty()) {
	            System.out.println("none");
	        } else {
	            for (String s : skipped) {
	                System.out.println(s);
	            }
	        }
	        System.out.println("\nERRORS:");
	        if (invalid.isEmpty()) {
	            System.out.println("none");
	        } else {
	            for (String s : invalid) {
	                System.out.println(s);
	            }
	        }
	        System.out.println();
	}
	  
}
