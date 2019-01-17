import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/*
 * The XML to JSON converter.
 * Apply with the external library from github.com/stleary/JSON-java.
 * @author Sathira Kittisukmongkol
 */
public class Main {

	public static int PRETTY_PRINT_INDENT_FACTOR = 4;

	/*
	 * Find the input file and change it into JSON format.
	 * @param inputFielName , name of file that want to change.
	 * @return result , the string of file in json format.
	 */
	public static String changeToJSONFormat(String inputFileName) {
		String result = "";
		File file = new File(inputFileName);
		 try {
			 FileInputStream fstream = new FileInputStream(file);
			 BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			 String line = br.readLine();
			 String XMLFile = "";
			 while(line != null) {
				 XMLFile += line;
				 line = br.readLine();
			 }
			 JSONObject xmlJSONObj = XML.toJSONObject(XMLFile);
			 xmlJSONObj = xmlJSONObj.getJSONObject("current");
			 String jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
			 result = jsonPrettyPrintString;
		 } catch(Exception ex) {
			 ex.printStackTrace();
		 }
		return result;
	}

	/*
	 * Export json file.
	 * @param context , the detail to write into the file.
	 * @param fileName , the name of exported file.
	 */
	public static void exportFileJSON(String context, String fileName) {
		PrintWriter writer;
		try {
			String destFileName = fileName.replace(".xml", "") + ".json";
			writer = new PrintWriter(destFileName, "UTF-8");
			writer.println(context);
			writer.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	/*
	 * Main method to run the program.
	 * @param args , the arrays of file name.
	 */
	public static void main(String[] args) {
		for(String fileName: args) {
			String result = changeToJSONFormat(fileName);
			exportFileJSON(result, fileName);
		}
	}

}
