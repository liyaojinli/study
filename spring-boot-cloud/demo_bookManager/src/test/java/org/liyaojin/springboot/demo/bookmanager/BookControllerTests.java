package org.liyaojin.springboot.demo.bookmanager;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookmanagerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class BookControllerTests {
    private final String url = "http://localhost:8080/books/liyaojin";

    @Autowired
    private TestRestTemplate template;

    @Test
    public void testGetBooksByReader() {
        ResponseEntity<String> response = template.getForEntity(url, String.class, "liyaojin");
        System.out.println(response.getBody());
    }
}
