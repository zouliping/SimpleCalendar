package cn.pdc.calendar.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StringUtil {

	/**
	 * convert inputstream to string
	 * 
	 * @param is
	 * @return
	 */
	public static String inputStream2String(InputStream is) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int len = 0;
		String result = "";

		if (is != null) {
			try {
				while ((len = is.read(data)) != -1) {
					baos.write(data, 0, len);
				}
				result = new String(baos.toByteArray(), "UTF8");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return result;
		}
		return null;
	}
}
