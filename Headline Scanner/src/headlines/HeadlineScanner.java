package headlines;

import java.io.*;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// News API key: 3c27ffde4f164328b95f92e4e80bdc67

public class HeadlineScanner {
	private static File HISTORY_STORAGE = new File("bin/history.txt");

	public ArrayList<String> getHistory() throws IOException {
		FileInputStream fileInputStream = new FileInputStream(HISTORY_STORAGE);
		ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

		try {
			ArrayList<String> history = (ArrayList<String>) objectInputStream.readObject();

			objectInputStream.close();
			return history;
		} catch (Exception e) {
			objectInputStream.close();
			if (e instanceof ClassCastException) {
				throw new IOException("File empty");
			}
			throw new IOException("File Not Found");
		}
	}
	
	private void saveToHistory(String keyword) throws IOException {
		/* Can't append with ObjectOutputStream
		 * File probably won't be that big so just read it all in then append in memory */
		ArrayList<String> oldFileContents;
		try {
			 oldFileContents = getHistory();
		} catch(IOException e) {
			oldFileContents = new ArrayList<>();
		}
		oldFileContents.add(0, keyword);
		
		HISTORY_STORAGE.getParentFile().mkdirs();
		
		FileOutputStream fileOutputStream = new FileOutputStream(HISTORY_STORAGE);
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

		objectOutputStream.writeObject(oldFileContents);
		objectOutputStream.close();
	}
	
	public ArrayList<String> search(String keywords) {
		
		// Save to history
		try {
			saveToHistory(keywords);
		} catch (IOException e) {
			
		}
		
		// Interact with API
		// Example source: cnn
		// Add desired sources to source parameter
		keywords = keywords.replaceAll("[ ]", "+");
		String params = "q=" + keywords
				+ "&sources=cnn"
				+ "&searchIn=title,description"
				+ "&from=" + LocalDate.now().minusDays(2)
				+ "&to=" + LocalDate.now().toString()
				+ "&sortBy=popularity"
				+ "&apiKey=3c27ffde4f164328b95f92e4e80bdc67";
		String jsonResponse = "no response";
		try {
			HttpRequest searchReq = HttpRequest.newBuilder()
					.uri(new URI("https://newsapi.org/v2/everything?" + params))
					.timeout(Duration.of(15, ChronoUnit.SECONDS))
					.GET()
					.build();
			
			// Response will be a JSON
			HttpResponse<String> response = HttpClient
					.newBuilder()
					.proxy(ProxySelector.getDefault())
					.followRedirects(HttpClient.Redirect.ALWAYS)
					.build()
					.send(searchReq, BodyHandlers.ofString());
			jsonResponse = response.body();
		} catch (Exception e) {
			if (e instanceof URISyntaxException) {
				System.out.println("bad request");
			} else if (e instanceof InterruptedException) {
				System.out.println("bad response");
			}
		}
		
		// process response
		
		if (jsonResponse.equals("no response")) {
			// get out
		}
		
		ArrayList<String> results = new ArrayList<>();
		
		Pattern re = Pattern.compile("\"totalResults\":(\\d+)");
		Matcher matcher = re.matcher(jsonResponse);
		boolean matchFound = matcher.find();
		int numResults = 0;
		
		if (matchFound) {
			numResults = Integer.parseInt(matcher.group(1));
		}
	
		// Setup regex matchers
		Pattern namePat = Pattern.compile("\"name\":\"(\\w+)\"");
		Matcher nameMatcher = namePat.matcher(jsonResponse);

		Pattern titlePat = Pattern.compile("\"title\":\"(.+?)\",\"description");
		Matcher titleMatcher = titlePat.matcher(jsonResponse);
		
		for (int i = 0; i < numResults; i++) {
			
			// name of source and title
			String entry = "";
			
			if (nameMatcher.find() && titleMatcher.find()) {
				entry += nameMatcher.group(1) + ": ";
				String title = titleMatcher.group(1);
				entry += title;
				
				results.add(entry);
			}
		}
		
		return results;
	}
	
	public void clearHistory() {
		if (!HISTORY_STORAGE.delete()) {
			//could not delete
		}
	}
}
