package utilities;

/**
 * @author varshac
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class DateUtils {
	private static final HashMap<String, String> DATE_FORMAT_REGEXPS = new HashMap<String, String>() {
		{
			put("^\\d{8}$", "yyyyMMdd");
			put("^\\d{1,2}-\\d{1,2}-\\d{4}$", "dd-MM-yyyy");
			put("^\\d{4}-\\d{1,2}-\\d{1,2}$", "yyyy-MM-dd");
			put("^\\d{1,2}/\\d{1,2}/\\d{4}$", "MM/dd/yyyy");
			put("^\\d{4}/\\d{1,2}/\\d{1,2}$", "yyyy/MM/dd");
			put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}$", "dd MMM yyyy");
			put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}$", "dd MMMM yyyy");
			put("^\\d{12}$", "yyyyMMddHHmm");
			put("^\\d{8}\\s\\d{4}$", "yyyyMMdd HHmm");
			put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}$",
					"dd-MM-yyyy HH:mm");
			put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}$",
					"yyyy-MM-dd HH:mm");
			put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}$",
					"MM/dd/yyyy HH:mm");
			put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}$",
					"yyyy/MM/dd HH:mm");
			put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}$",
					"dd MMM yyyy HH:mm");
			put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}$",
					"dd MMMM yyyy HH:mm");
			put("^\\d{14}$", "yyyyMMddHHmmss");
			put("^\\d{8}\\s\\d{6}$", "yyyyMMdd HHmmss");
			put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$",
					"dd-MM-yyyy HH:mm:ss");
			put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$",
					"yyyy-MM-dd HH:mm:ss");
			put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$",
					"MM/dd/yyyy HH:mm:ss");
			put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$",
					"yyyy/MM/dd HH:mm:ss");
			put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$",
					"dd MMM yyyy HH:mm:ss");
			put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$",
					"dd MMMM yyyy HH:mm:ss");
			//Add for (dot .) format too
			put("^\\d{1,2}\\.\\d{1,2}\\.\\d{4}$", "dd.MM.yyyy");
			put("^\\d{4}\\.\\d{1,2}\\.\\d{1,2}$", "yyyy.MM.dd");
			
			put("((?:(?:[0-1][0-9])|(?:[2][0-3])|(?:[0-9])):(?:[0-5][0-9])(?::[0-5][0-9])?(?:\\s?(?:am|AM|pm|PM)))", "HH:mm aa");


			/* Note : To support more Date/Time formats add a value to the HashMap in the following format
			 * 			puts(<regex for format>, <SimpleDateFormat pattern>
			 */
			
		}
	};

	public static String determineDateFormat(String dateString) {
		for (String regexp : DATE_FORMAT_REGEXPS.keySet()) {
			if (dateString.toLowerCase().matches(regexp)) {
				return DATE_FORMAT_REGEXPS.get(regexp);
			}
		}
		return null; // Unknown format.
	}

	public static Date parse(String dateString) throws ParseException {
	
		String dateFormat = determineDateFormat(dateString);
		if (dateFormat == null) {
			throw new ParseException("Unknown date format.", 0);
		}
		return parse(dateString, dateFormat);
	}

	public static Date parse(String dateString, String dateFormat)
			throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
		simpleDateFormat.setLenient(false); // Don't automatically convert
											// invalid date.

		if(dateFormat.contains("aa")){
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm aa");
			return sdf.parse(dateString);
		}
		return simpleDateFormat.parse(dateString);
	}
}
