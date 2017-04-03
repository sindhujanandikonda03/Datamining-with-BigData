package com.mycompany.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Properties;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;

import com.mycompany.vo.SearchVo;

public class TwitterRestCall {
	static String consumerKeyStr = "";
	static String consumerSecretStr = "";
	static String accessTokenStr = "";
	static String accessTokenSecretStr = "";
	OAuthConsumer consumer;
	private JsonConverter jsonConverter;

	public TwitterRestCall(String filePath) {
		jsonConverter = new JsonConverter();
		consumerKeyStr = readProperties(filePath, "consumerKey");
		consumerSecretStr = readProperties(filePath, "consumerSecret");
		accessTokenStr = readProperties(filePath, "accessToken");
		accessTokenSecretStr = readProperties(filePath, "accessTokenSecret");
		consumer = new CommonsHttpOAuthConsumer(consumerKeyStr,
				consumerSecretStr);

		consumer.setTokenWithSecret(accessTokenStr, accessTokenSecretStr);
	}

	private String readProperties(String filePath, String key) {
		String url = "";
		try {
			Properties properties = new Properties();
			FileInputStream fileInputStream = new FileInputStream(new File(
					filePath));
			properties.load(fileInputStream);
			url = properties.getProperty(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}

	public List<SearchVo> getTweetSearch(String filePath, String queryName) {
		try {
			String url = readProperties(filePath, "search");
			queryName = queryName.replace("#", "");
			queryName = queryName.replace(" ", "+");
			url += "=" + queryName + "&count=1000";
			System.out.println(url);
			HttpGet request = new HttpGet(url);
			consumer.sign(request);
			HttpClient client = new DefaultHttpClient();
			HttpHost proxy = new HttpHost("www.proxy.ericsson.se", 8080, "http");
			client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
				proxy);
			HttpResponse response = client.execute(request);
			int statusCode = response.getStatusLine().getStatusCode();
			System.out.println(statusCode + ":"
					+ response.getStatusLine().getReasonPhrase());
			filePath = filePath.replaceAll("config.properties",
					"result/result.json");
			File file=new File(
					filePath);
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			fileOutputStream.write(IOUtils.toString(
					response.getEntity().getContent()).getBytes());
			List<SearchVo> searchDetails = jsonConverter
					.convertSearchValue(filePath);
			fileOutputStream.flush();
			fileOutputStream.close();
			
			/*FileInputStream fileInputStream=new FileInputStream(new File(filePath));
			byte[] byt=new byte[fileInputStream.available()];
			fileInputStream.read(byt);
			fileInputStream.close();
			String outPutDir=new File(new File(file.getParent()).getParent()).getParent()+"/result";
			FileOutputStream fileOutputStream2=new FileOutputStream(new File(outPutDir+"/"+new File(filePath).getName()));
			fileOutputStream2.write(byt);
			fileOutputStream2.close();*/
			
			return searchDetails;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
