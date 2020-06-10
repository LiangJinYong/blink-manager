package com.blink.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

public class FCMUtils {

	final static String API_KEY = "AAAAEIlWRLk:APA91bFMY4y31mcC3QLaQva7PTykXkbBHSXMoMf5e0DVSq21Am71193bSHlNZUJvDakKLKnnBepaBT5FiBwJVAFlvVe4oG2NFZr69aLzXAQ7SSt42iIHgMcStIqxotOR8WE5cv2FlKsq";

	public static void sendFCMAll(String title, String body, String type) throws JSONException {
		try {

			URL url = new URL("https://fcm.googleapis.com/fcm/send");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Authorization", "key=" + API_KEY);
			conn.setDoOutput(true);

			JSONObject info = new JSONObject();
			JSONObject msg = new JSONObject();

			msg.put("title", title);
			msg.put("body", body);
			msg.put("type", type);
			msg.put("click_action", type);
			info.put("data", msg);

			info.put("notification", msg);

			info.put("to", "/topics/ALL");

			OutputStream os;

			os = conn.getOutputStream();

			// 서버에서 날려서 한글 깨지는 사람은 아래처럼 UTF-8로 인코딩해서 날려주자
			os.write(info.toString().getBytes("UTF-8"));
			os.flush();
			os.close();

			int responseCode = conn.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + info.toString());
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}

			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void sendFCM(int userNo, String pushToken, String title, String body, String type) throws JSONException {
		try {
			URL url = new URL("https://fcm.googleapis.com/fcm/send");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Authorization", "key=" + API_KEY);
			conn.setDoOutput(true);

			JSONObject info = new JSONObject();
			JSONObject msg = new JSONObject();

			msg.put("title", title);
			msg.put("body", body);
			
			msg.put("userNo", userNo);
			msg.put("type", type);
			msg.put("click_action", "goalkeepin_push");
			info.put("data", msg);
			info.put("notification", msg);
			info.put("to", pushToken);

			OutputStream os = conn.getOutputStream();

			// 서버에서 날려서 한글 깨지는 사람은 아래처럼 UTF-8로 인코딩해서 날려주자
			System.out.println("===>" + info.toString().getBytes("UTF-8"));
			os.write(info.toString().getBytes("UTF-8"));
			os.flush();
			os.close();

			int responseCode = conn.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + info.toString());
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}

			in.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void sendFCM(int userNumber, String pushToken, String title, String body, int targetNumber, String type, String appMsg1, String appMsg2) throws JSONException
	   {
	      try
	      {
	    	  URL url = new URL(" https://fcm.googleapis.com/fcm/send");
	 	      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	 	         conn.setDoOutput(true);
	 	         conn.setRequestMethod("POST");
	 	         conn.setRequestProperty("Content-Type", "application/json");
	 	         conn.setRequestProperty("Authorization", "key=" + API_KEY);
	 	         conn.setDoOutput(true);

	 	         JSONObject info = new JSONObject();
	 	         JSONObject msg = new JSONObject();

	 	         msg.put("title", title);
	 	         msg.put("body", body);
	 	         msg.put("userNo", userNumber);
	 	         msg.put("targetNumber", targetNumber);
	 	         msg.put("type", type);
	 	         msg.put("sound", "default");
	 	         msg.put("click_action", type);
	 	         msg.put("appMsg1", appMsg1);
	 	         msg.put("appMsg2", appMsg2);
	 	         
	 	         info.put("data", msg);
	 	         
	 	         info.put("notification", msg);
	 	        
	 	         info.put("to", pushToken);
	 	         

	 	         OutputStream os;

	 	         os = conn.getOutputStream();

	 	         // 서버에서 날려서 한글 깨지는 사람은 아래처럼 UTF-8로 인코딩해서 날려주자
	 	         os.write(info.toString().getBytes("UTF-8"));
	 	         os.flush();
	 	         os.close();

	 	         int responseCode = conn.getResponseCode();
	 	         System.out.println("\nSending 'POST' request to URL : " + url);
	 	         System.out.println("Post parameters : " + info.toString());
	 	         System.out.println("Response Code : " + responseCode);

	 	         BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	 	         String inputLine;
	 	         StringBuffer response = new StringBuffer();
	 	         
	 	         while ((inputLine = in.readLine()) != null)
	 	         {
	 	            response.append(inputLine);
	 	         }

	 	         in.close();
	      } catch (IOException e)
	      {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      }

	      
	   }
}
