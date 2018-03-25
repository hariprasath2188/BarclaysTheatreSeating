package com.barclay.card.theatre;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.barclay.card.theatre.api.BarclaycardTheatreController;
import com.barclay.card.theatre.request.model.RequestModel;
import com.barclay.card.theatre.response.model.ResponseModelWrapper;
import com.barclay.card.theatre.utils.TestCaseOneUtils;
import com.barclay.card.theatre.utils.TestCaseThreeUtils;
import com.barclay.card.theatre.utils.TestCaseTwoUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
@ComponentScan(basePackages = "com.barclay.card.theatre")
@SpringBootTest(classes = BarclaycardTheatreController.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BarclaycardTheatreSeatingApplicationTests {
    
    @LocalServerPort
    private int port;
    
    /**
     * Test Case with Input and Output as given in the Sample Problem
     */
    @Test
    public void testCaseOne() {

	String uri = "http://localhost:" + port + "/api/v1/theatre/barclays/seating";
	TestRestTemplate restTemplate = new TestRestTemplate();
	RequestModel model = TestCaseOneUtils.createDefaultRequest();
	try {
	    HttpEntity<RequestModel> entity = new HttpEntity<RequestModel>(
		    model);
	    ResponseEntity<ResponseModelWrapper> response = restTemplate
		    .exchange(uri, HttpMethod.POST, entity,
			    ResponseModelWrapper.class);
	    assertEquals(HttpStatus.OK, response.getStatusCode());
	    assertEquals(response.getBody(), TestCaseOneUtils.expectedResult());
	} catch (Exception e) {
	    assertTrue(false);
	}
    }

    /**
     * 
     */
    @Test
    public void testCaseTwo() {
	String uri = "http://localhost:" + port + "/api/v1/theatre/barclays/seating";
	TestRestTemplate restTemplate = new TestRestTemplate();
	RequestModel model = TestCaseTwoUtils.createDefaultRequest();
	try {
	    HttpEntity<RequestModel> entity = new HttpEntity<RequestModel>(
		    model);
	    ResponseEntity<ResponseModelWrapper> response = restTemplate
		    .exchange(uri, HttpMethod.POST, entity,
			    ResponseModelWrapper.class);
	    assertEquals(HttpStatus.OK, response.getStatusCode());
	    assertEquals(response.getBody(), TestCaseTwoUtils.expectedResult());
	} catch (Exception e) {
	    assertTrue(false);
	}
    }
    
    @Test
    public void testCaseThree() {
	String uri = "http://localhost:" + port + "/api/v1/theatre/barclays/seating";
	TestRestTemplate restTemplate = new TestRestTemplate();
	RequestModel model = TestCaseThreeUtils.createDefaultRequest();
	try {
	    HttpEntity<RequestModel> entity = new HttpEntity<RequestModel>(
		    model);
	    ResponseEntity<ResponseModelWrapper> response = restTemplate
		    .exchange(uri, HttpMethod.POST, entity,
			    ResponseModelWrapper.class);
	    assertEquals(HttpStatus.OK, response.getStatusCode());
	    assertEquals(response.getBody(), TestCaseThreeUtils.expectedResult());
	} catch (Exception e) {
	    assertTrue(false);
	}
    }
}
