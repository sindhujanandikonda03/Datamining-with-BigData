package com.mycompany.logic;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.mycompany.vo.HashTagVo;
import com.mycompany.vo.LocationVo;
import com.mycompany.vo.RetweetCountVo;

public class ClusteringLogic {
	public List<LocationVo> LocationServices(String filePath) {

		List<LocationVo> locationList = new ArrayList<LocationVo>();
		try {
			System.out.println(filePath);
			HashMap<String, LocationVo> duplicateLocationMap = new HashMap<String, LocationVo>();
			// List<String> duplicateLocationList = new ArrayList<String>();
			JSONParser parser = new JSONParser();

			FileInputStream fileInputStream = new FileInputStream(new File(
					filePath + "/" + "result.json"));

			/*
			 * FileInputStream fileInputStream = new FileInputStream(new File(
			 * "result.json"));
			 */
			byte[] byt = new byte[fileInputStream.available()];
			fileInputStream.read(byt);
			String s = new String(byt);
			Object obj = parser.parse(s);
			JSONObject jsonObject = (JSONObject) obj;
			JSONArray trends = (JSONArray) jsonObject.get("statuses");
			for (int i = 0; i < trends.size(); i++) {
				JSONObject jsObject = (JSONObject) trends.get(i);
				JSONObject jObject = (JSONObject) jsObject.get("user");
				String location = jObject.get("location").toString().trim()
						.toUpperCase();
				if (!location.equals("")
						&& !duplicateLocationMap.containsKey(location)) {

					LocationVo locationVo = new LocationVo();
					locationVo.setIndex(String.valueOf(i));
					locationVo.setLocation(location);
					locationVo.setId(jsObject.get("id").toString());
					duplicateLocationMap.put(location, locationVo);
				} else {
					if (!location.equals("")) {
						LocationVo locationVo = duplicateLocationMap
								.get(location);
						String id = locationVo.getId() + ","
								+ jsObject.get("id").toString();
						locationVo.setId(id);
						duplicateLocationMap.remove(location);
						duplicateLocationMap.put(location, locationVo);
					}

				}

			}
			Iterator iter = duplicateLocationMap.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry pairs = (Map.Entry) iter.next();
				locationList.add((LocationVo) pairs.getValue());
			}
			for (LocationVo locationVo : locationList) {
				System.out.println(locationVo.getId());
			}
			fileInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return locationList;
	}

	public List<HashTagVo> HashTagServices(String filePath) {
		List<HashTagVo> hashTagList = new ArrayList<HashTagVo>();
		HashMap<String, HashTagVo> duplicateHashTagMap = new HashMap<String, HashTagVo>();
		try {

			JSONParser parser = new JSONParser();
			FileInputStream fileInputStream = new FileInputStream(new File(
					filePath + "/" + "result.json"));
			byte[] byt = new byte[fileInputStream.available()];
			fileInputStream.read(byt);
			String s = new String(byt);
			Object obj = parser.parse(s);
			JSONObject jsonObject = (JSONObject) obj;
			JSONArray trends = (JSONArray) jsonObject.get("statuses");
			for (int i = 0; i < trends.size(); i++) {
				JSONObject jsObject = (JSONObject) trends.get(i);
				JSONObject jObject = (JSONObject) jsObject.get("entities");
				JSONArray hash_trends = (JSONArray) jObject.get("hashtags");

				for (int hash_index = 0; hash_index < hash_trends.size(); hash_index++) {

					JSONObject hashObject = (JSONObject) hash_trends
							.get(hash_index);
					if (!hashObject.get("text").toString().equals("")
							&& !duplicateHashTagMap.containsKey(hashObject.get(
									"text").toString())) {
						HashTagVo hashTagVo = new HashTagVo();
						hashTagVo.setHashTag(hashObject.get("text").toString());
						hashTagVo.setId(jsObject.get("id").toString());
						// hashTagList.add(hashTagVo);
						duplicateHashTagMap.put(hashObject.get("text")
								.toString(), hashTagVo);
					} else {
						if (!hashObject.get("text").toString().equals("")) {
							HashTagVo hashTagVo = duplicateHashTagMap
									.get(hashObject.get("text").toString());
							String id = hashTagVo.getId() + ","
									+ jsObject.get("id").toString();
							hashTagVo.setId(id);
							duplicateHashTagMap.remove(hashObject.get("text")
									.toString());
							duplicateHashTagMap.put(hashObject.get("text")
									.toString(), hashTagVo);
						}

					}

				}
			}
			fileInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Iterator iter = duplicateHashTagMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry pairs = (Map.Entry) iter.next();
			hashTagList.add((HashTagVo) pairs.getValue());
		}

		return hashTagList;
	}

	public List<RetweetCountVo> retweetCountServices(String filePath) {
		List<RetweetCountVo> retweetList = new ArrayList<RetweetCountVo>();
		HashMap<String, RetweetCountVo> duplicateRetweetMap = new HashMap<String, RetweetCountVo>();
		try {
			JSONParser parser = new JSONParser();
			FileInputStream fileInputStream = new FileInputStream(new File(
					filePath + "/" + "result.json"));
			byte[] byt = new byte[fileInputStream.available()];
			fileInputStream.read(byt);
			String s = new String(byt);
			Object obj = parser.parse(s);
			JSONObject jsonObject = (JSONObject) obj;
			JSONArray trends = (JSONArray) jsonObject.get("statuses");
			for (int i = 0; i < trends.size(); i++) {
				JSONObject jsObject = (JSONObject) trends.get(i);
				String retweetCount = jsObject.get("retweet_count").toString();
				String id = jsObject.get("id").toString();
				if (!duplicateRetweetMap.containsKey(retweetCount)) {
					RetweetCountVo retweetCountVo=new RetweetCountVo();
					retweetCountVo.setId(id);
					retweetCountVo.setRetweetCount(retweetCount);
					duplicateRetweetMap.put(retweetCount, retweetCountVo);
				} else {
					RetweetCountVo retweetCountVo = duplicateRetweetMap
							.get(jsObject.get("retweet_count").toString());
					id = retweetCountVo.getId() + ","
							+ jsObject.get("id").toString();
					retweetCountVo.setId(id);
					retweetCountVo.setRetweetCount(retweetCount);
					duplicateRetweetMap.remove(jsObject.get("retweet_count").toString());
					duplicateRetweetMap.put(jsObject.get("retweet_count").toString(), retweetCountVo);
				}
			}
			fileInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Iterator iter = duplicateRetweetMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry pairs = (Map.Entry) iter.next();
			retweetList.add((RetweetCountVo) pairs.getValue());
		}
		
		return retweetList;
	}

	public static void main(String[] args) {
		ClusteringLogic clusteringLogic = new ClusteringLogic();
		clusteringLogic.HashTagServices("");
	}
}
