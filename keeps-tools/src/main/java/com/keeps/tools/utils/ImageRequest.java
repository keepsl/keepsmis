package com.keeps.tools.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageRequest {

	public static void main(String[] args) {
		//scale2("c:/QR/1040517595.jpg", "c:/QR/IMG_0004_SCALE.JPG", 300, 300, true);
		saveImage("https://gd3.alicdn.com/imgextra/i3/TB1uRQqPpXXXXXJXpXXXXXXXXXX_!!0-item_pic.jpg_400x400.jpg", "C:/test","ddd.jpg");
	}
	public static byte[] getImageBatyByUrl(String imageurl) {
		try {
			URL url = new URL(imageurl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5 * 1000);
			InputStream inStream = conn.getInputStream();
			byte[] data = readInputStream(inStream);
			return data;
		} catch (Exception e) {    
            e.printStackTrace();    
        }
		return null;
	}
	
	
	public static boolean saveImage(String imageurl,String dist,String filename){
		byte[] data = getImageBatyByUrl(imageurl);
		FileOutputStream outStream = null;
		try {
			File file=new File(dist);
			if (!file.exists()) {
				file.mkdirs();
            }
			file=new File(dist,filename);
			outStream = new FileOutputStream(file);
			outStream.write(data);
			return true;
		} catch (Exception e) {
			e.printStackTrace();    
		} finally {
			IoUtils.close(outStream);
		}
		return false;
	}
	private static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		try {
			while ((len = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IoUtils.close(outStream);
		}
		return outStream.toByteArray();
	}
}
