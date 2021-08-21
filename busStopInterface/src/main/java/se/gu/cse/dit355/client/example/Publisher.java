package se.gu.cse.dit355.client.example;

import Models.Location;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import sun.net.www.http.HttpClient;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



public class Publisher {

	private final static String TOPIC = "test";

	private final static String BROKER = "tcp://localhost:1883";

	private final static String USER_ID = "test-publisher";

	private final IMqttClient middleware;

	//StringBuilder content;
	String key="114160d9-72af-42f3-b458-0761f01c050e";



	private HttpURLConnection connection;




	public Publisher() throws MqttException {
		middleware = new MqttClient(BROKER, USER_ID);
		middleware.connect();

	}

	public static void main(String[] args) throws MqttException {
		Publisher p = new Publisher();
		try {
			String intendedMessage = p.getLocations();
			//System.out.println(locations);
			p.sendMessage(intendedMessage);
		} catch (IOException e) {
			e.printStackTrace();
		}

		p.close();
		try {
			p.getLocations();
			//System.out.println(locations);
		} catch (IOException e) {
			e.printStackTrace();
		}


	}


	private void close() throws MqttException {
		middleware.disconnect();
		middleware.close();
	}

	private void sendMessage(String payLoad) throws MqttPersistenceException, MqttException {
		MqttMessage message = new MqttMessage();
		message.setPayload(payLoad.getBytes());
		middleware.publish(TOPIC, message);
	}

//	public String getKey() {
//
//
//
//		try {
//			BufferedReader bfReader = new BufferedReader(new FileReader("Api_Key ")); // you need to replace Api_Key to your Apikey bath
//
//			StringBuilder stringBuilder = new StringBuilder();
//			String line = bfReader.readLine();
//
//			while (line != null) {
//				stringBuilder.append(line);
//				stringBuilder.append(System.lineSeparator());
//				line = bfReader.readLine();
//			}
//
//			 key = stringBuilder.toString();
//
//
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return key;
//	}


	public String getLocations() throws IOException {


		//String key = getKey();

		CloseableHttpClient httpClient = HttpClients.createDefault();

		try {

			HttpGet request = new HttpGet("https://api.resrobot.se/v2/location.name.json?key="+key+"&input=Göteborg");
			System.out.println("Http Request Type: " + request.getMethod());

			request.addHeader("accept", "application/json");


			HttpResponse response = httpClient.execute(request);

			int statusCode = response.getStatusLine().getStatusCode();

			System.out.println(statusCode);

			if (statusCode != 200) {
				throw new RuntimeException("Failed to GET. Status Code: " + statusCode);

			}

			HttpEntity httpEntity = response.getEntity();
			String outPut = EntityUtils.toString(httpEntity);
/*
			String aray = gson.toJson(outPut);
			//System.out.println(outPut);

			*/

			return outPut;







		}

		finally {
			httpClient.getConnectionManager().shutdown();
		}



	}



	}
