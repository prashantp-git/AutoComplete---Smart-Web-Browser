package com.myproject.autocomplete;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import android.os.Environment;
import android.util.Log;

public class FileOperations {

	public static void write(String fname, String fcontent) {
		try {

			String fpath = "/sdcard/" + fname + ".txt";
			File file = new File(fpath);

			if (file.exists()) {
				//return false;
			} else {
				file.createNewFile();
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(fcontent);
				bw.close();
				Log.d("FileOperations", "File Write Success");
				//return true;
			}

		} catch (IOException e) {
			e.printStackTrace();
			//return false;
		}

	}

	public static String read(String fname) {

		BufferedReader br = null;
		String response = null;

		try {
			String fpath = Environment.getExternalStorageDirectory().getPath() + "/" +fname + ".txt";

			br = new BufferedReader(new FileReader(fpath));
			String line = "";
			while ((line = br.readLine()) != null) {
				
			}
			//response = output.toString();

		} catch (IOException e) {
			e.printStackTrace();
			return null;

		}
		return response;

	}

}
