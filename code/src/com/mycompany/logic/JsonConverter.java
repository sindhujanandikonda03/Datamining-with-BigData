package com.mycompany.logic;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.mycompany.vo.ResponseVo;
import com.mycompany.vo.SearchVo;

public class JsonConverter {
	public void convertTrendsValue(String fileName) {
		try {
			JSONParser parser = new JSONParser();
			FileInputStream fileInputStream = new FileInputStream(new File(
					fileName));
			byte[] byt = new byte[fileInputStream.available()];
			fileInputStream.read(byt);
			String s = new String(byt);
			try {
				Object obj = parser.parse(s);
				JSONArray array = (JSONArray) obj;
				JSONObject obj2 = (JSONObject) array.get(0);
				JSONArray trends = (JSONArray) obj2.get("trends");
				for (int i = 0; i < trends.size(); i++) {
					JSONObject jsonObject = (JSONObject) trends.get(i);
					Vector<String> rowData = new Vector<String>();
					rowData.add(jsonObject.get("name").toString());
					rowData.add(jsonObject.get("url").toString());
					/*
					 * MainForm.defaultTableModel.addRow(rowData);
					 * MainForm.jComboBox.addItem(jsonObject.get("name")
					 * .toString());
					 */
				}
			} catch (Exception pe) {
				pe.printStackTrace();
			} finally {
				fileInputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public List<SearchVo> convertSearchValue(String fileName) {
		List<SearchVo> searchDetails = new ArrayList<SearchVo>();
		try {

			JSONParser parser = new JSONParser();
			FileInputStream fileInputStream = new FileInputStream(new File(
					fileName));
			byte[] byt = new byte[fileInputStream.available()];

			fileInputStream.read(byt);
			String s = new String(byt);
			try {
				Object obj = parser.parse(s);
				JSONObject jsonObject = (JSONObject) obj;
				JSONArray trends = (JSONArray) jsonObject.get("statuses");
				System.out.println(trends.size());
				for (int i = 0; i < trends.size(); i++) {
					JSONObject jsObject = (JSONObject) trends.get(i);
					JSONObject jObject = (JSONObject) jsObject.get("user");
					SearchVo searchVo = new SearchVo();
					searchVo.setId(jObject.get("id").toString());
					searchVo.setName(jObject.get("name").toString());
					searchVo.setScreenName(jObject.get("screen_name")
							.toString());
					searchVo.setScreenName(jObject.get("screen_name")
							.toString());
					searchVo.setText(jsObject.get("text").toString());
					searchVo.setCreatedDate(jsObject.get("created_at")
							.toString());
					searchDetails.add(searchVo);
				}

			} catch (Exception pe) {
				pe.printStackTrace();
			} finally {
				fileInputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return searchDetails;
	}

	public void convertAggregationValue(String fileName) {
		try {
			JSONParser parser = new JSONParser();
			FileInputStream fileInputStream = new FileInputStream(new File(
					fileName));
			byte[] byt = new byte[fileInputStream.available()];

			fileInputStream.read(byt);
			String s = new String(byt);
			try {
				Object obj = parser.parse(s);
				JSONObject jsonObject = (JSONObject) obj;
				JSONArray trends = (JSONArray) jsonObject.get("statuses");
				System.out.println(trends.size());
				/*
				 * JSONObject jsObject = (JSONObject) trends .get(trends.size()
				 * - 1); String inTime = jsObject.get("created_at").toString();
				 * jsObject = (JSONObject) trends.get(0); String outTime =
				 * jsObject.get("created_at").toString(); int retweet_count = 0;
				 */

			} catch (Exception pe) {
				pe.printStackTrace();
			} finally {
				fileInputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// public List<>
	public List<ResponseVo> getResponseVos(String filePath, String ids) {
		List<ResponseVo> responseList=new ArrayList<ResponseVo>();
		try {
			JSONParser parser = new JSONParser();
			FileInputStream fileInputStream = new FileInputStream(new File(
					filePath));
			byte[] byt = new byte[fileInputStream.available()];
			
			fileInputStream.read(byt);
			String s = new String(byt);

			try {
				Object obj = parser.parse(s);
				JSONObject jsonObject = (JSONObject) obj;
				JSONArray trends = (JSONArray) jsonObject.get("statuses");
				for (int i = 0; i < trends.size(); i++) {
					JSONObject jsObject = (JSONObject) trends.get(i);
					JSONObject jObject = (JSONObject) jsObject.get("user");
					JSONObject hashEntity = (JSONObject) jsObject
							.get("entities");
					JSONArray hash_trends = (JSONArray) hashEntity
							.get("hashtags");
					if (ids.contains(jsObject.get("id").toString())) {
						ResponseVo responseVo = new ResponseVo();
						responseVo.setId(jsObject.get("id").toString());
						responseVo.setText(jsObject.get("text").toString());
						responseVo.setName(jObject.get("name").toString());
						responseVo.setCreatedAt(jsObject.get("created_at")
								.toString());
						responseVo.setLocation(jObject.get("location")
								.toString());
						responseVo.setRetweetCount(jsObject
								.get("retweet_count").toString());
						String hashTag = "";
						for (int hash_index = 0; hash_index < hash_trends
								.size(); hash_index++) {
							JSONObject hashObject = (JSONObject) hash_trends
									.get(hash_index);
							if (hashTag.equals(""))
								hashTag = hashObject.get("text").toString();
							else {
								hashTag += ","
										+ hashObject.get("text").toString();
							}
						}
						responseVo.setHashTag(hashTag);
						responseList.add(responseVo);
					}
					
				}

			} catch (Exception pe) {
				pe.printStackTrace();
			} finally {
				fileInputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseList;
	}

	public static void main(String[] args) {
		JsonConverter jsonConverter = new JsonConverter();
		jsonConverter.convertAggregationValue("result.json");
	}
}
