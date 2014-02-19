package cn.pdc.calendar.utils;

public class Utils {

	public static String uid = "";
	public static String uname = "";

	public static final String IP = "http://192.168.0.126:9000";

	// login
	public static final String LOGIN = IP + "/user/login";

	// get a user's activities
	public static final String GET_ACTIVITY = IP
			+ "/indiv/get?classname=Activity&uid=";

	// add a activity
	public static final String ADD_ACTIVITY = IP + "/indiv/update";
}