package com.idacchi.cloudautomator;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Cloud Automator HTTP Kicker.
 * 
 * @author yasuhiko
 *
 */
public class CloudAutomatorHTTP {

	/** Setting file name */
	public static final String SETTING_FILE = "setting.ini";

	/**
	 * CUI Kick HTTP Trigger.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		Kick();
	}

	/**
	 * Kick HTTP Trigger.
	 * 
	 * @throws Exception
	 */
	protected static void Kick() throws Exception {
		CloudAutomatorHTTP http = new CloudAutomatorHTTP();
		SettingCA setting = http.readSetting();
		http.sendPost(setting);
	}

	/**
	 * read setting file.
	 * 
	 * @return setting info for Cloud Automator HTTP Post
	 */
	private SettingCA readSetting() {
		SettingCA setting = new SettingCA();

		try {
			// read file
			FileReader fr = new FileReader(SETTING_FILE);
			BufferedReader br = new BufferedReader(fr);

			String line;
			while ((line = br.readLine()) != null) {
				// URL
				if (line.startsWith("curl https://")) {
					// example:
					// curl
					// https://manager.cloudautomator.com/trigger/xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
					// \
					setting.setUrl(line.split(" ")[1]);

				}
				// CAAuth
				if (line.startsWith("  -H \"Authorization:")) {
					// example:
					// -H "Authorization: CAAuth
					// xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" \
					setting.setAuth(line.split(" ")[5].split("\"")[0]);

				}
			}

			br.close();
			fr.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return setting;
	}

	/**
	 * Send Post Request.
	 * 
	 * @param setting
	 *            setting info for Cloud Automator HTTP Post
	 * @throws Exception
	 */
	private void sendPost(SettingCA setting) throws Exception {

		String url = setting.getUrl();
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		// add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("Authorization", "CAAuth " + setting.getAuth());
		con.setRequestProperty("Content-Length", "0");

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("Sending 'POST' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		System.out.println(response.toString());

	}

}
