package com.ge.crawler;

import static org.junit.Assert.*;


import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.junit.Test;


public class WebCrawlerControllerTest {
	ClassLoader classLoader = getClass().getClassLoader();
	@Test
	public void testReadFile() {
		
        ClassLoader classLoader = getClass().getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream("internrt_1.json")) {

            String result = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            System.out.println(result);

        } catch (IOException e) {
            e.printStackTrace();
        }
	    

	}

	@Test
	public void testCrawlGeDigitalPage() {
		fail("Not yet implemented");
	}

}
