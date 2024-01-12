package stepDefinition;

import java.io.IOException;

import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import base.PredefinedActions;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class AutomationHooks {

	PredefinedActions predefinedActions = new PredefinedActions();
	public static String browserName;
	public static boolean useWebdriverManager;
	public static boolean runHeadless;
	@Before
	public void setUp() throws IOException, XmlPullParserException {
		browserName = System.getProperty("browserName", "defaultValue");
		useWebdriverManager = Boolean.parseBoolean(System.getProperty("useWebdriverManager", "defaultValue"));
		runHeadless = Boolean.parseBoolean(System.getProperty("runHeadless", "defaultValue"));
		predefinedActions.start();

	}

	@After
	public void tearDown(Scenario scenario) {
		if (scenario.isFailed()) {
			scenario.attach(predefinedActions.takeScreenshot(), "image/jpeg", "Screen Shot");
			//System.out.println("Scenario - "+scenario.getName());
			createJiraTicket(scenario.getName());
		}

		predefinedActions.tearDown();
	}

	private static void createJiraTicket(String issueSummary) {
		String jiraUrl = "https://vivekkumarqa.atlassian.net/rest/api/2/issue/";
		String username = "vivekkumarqa2991@gmail.com";
		String apiToken = "ATATT3xFfGF0NF_OC_EhwM2mQ8J6RQV7v7i-9XTFrr-kjQIVJ6197nLoG0iSdIuuLk6p9L7mbfkoHRpR-VzS9pY5sxTAYy7MAuAl6IHYdYJPkf2JEDrfM93FoLiqjmVFUQ9YzT_4ILNGvB5SMRJCHtutnA4dvsRaLv3lbIYDsCZ3IQB_W7NTfWY=C5879E96";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBasicAuth(username, apiToken);

		Map<String, Object> requestBody = new HashMap<>();
		Map<String, Object> fields = new HashMap<>();
		fields.put("project", Map.of("key", "PP"));
		fields.put("summary", issueSummary);
		fields.put("description", "Issue Description");
		fields.put("issuetype", Map.of("name", "Task"));

		requestBody.put("fields", fields);

		HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = restTemplate.postForEntity(jiraUrl, requestEntity, String.class);

		if (responseEntity.getStatusCode().is2xxSuccessful()) {
			System.out.println("Jira ticket created successfully!");
			System.out.println("Issue Key: " + responseEntity.getBody());
		} else {
			System.err.println("Failed to create Jira ticket. Status code: " + responseEntity.getStatusCodeValue());
			System.err.println("Response: " + responseEntity.getBody());
		}
	}
}
