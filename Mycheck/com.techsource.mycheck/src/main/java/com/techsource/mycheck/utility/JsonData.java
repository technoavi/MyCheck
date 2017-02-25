package com.techsource.mycheck.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonData {

	public static String returnJson() {
		StringBuffer buffer = new StringBuffer();

		JSONParser parser = new JSONParser();
		ClassLoader loader = null;
		File file = null;
		try {
			loader = Thread.currentThread().getContextClassLoader();
			file = new File(loader.getResource("targetBoardJSON.json").getFile());

			Object obj = parser.parse(new FileReader(file));

			JSONObject jsonObject = (JSONObject) obj;
			JSONArray msg = (JSONArray) jsonObject.get("Målkort");
			buffer.append("{\"Målkort\": [  ");
			Iterator<JSONObject> iterator = msg.iterator();
			while (iterator.hasNext()) {
				String sep1 = " ";
				buffer.append(sep1);
				buffer.append("  [ ");
				buffer.append("  { ");
				JSONObject factObj = (JSONObject) iterator.next();

				String id = (String) factObj.get("id");
				buffer.append(" \"MålkortId\":\" " + id + " \",");

				String name = (String) factObj.get("name");
				buffer.append("\"MålkortName\":\" " + name + " \",");

				String description = (String) factObj.get("description");
				buffer.append("\"MålkortDescription\":\" " + description + " \",");

				buffer.append(" \"MålkortQuestions\" : [ ");

				String sep = " ";
				buffer.append(sep);
				//buffer.append(sep);
				JSONArray msg2 = (JSONArray) factObj.get("MålkortQuestions");
				Iterator<JSONObject> iterator2 = msg2.iterator();
				while (iterator2.hasNext()) {
					buffer.append("  { ");
					JSONObject factObj2 = (JSONObject) iterator2.next();
					String tid = (String) factObj2.get("id");
					buffer.append("\"Qid\":\" " + tid + " \",");

					String tname = (String) factObj2.get("name");
					buffer.append("\"Qname\":\" " + tname + " \",");

					String tdescription = (String) factObj2.get("description");
					buffer.append("\"Qdescription\":\" " + tdescription + " \",");

					Object object = factObj2.get("state");
					// {"targetBoard":[{"id":"1","name":"new Target
					if (object instanceof Boolean) {
						Boolean state = (Boolean) factObj2.get("state");

						buffer.append("\"Qstate\":  " + state +" ");
					}
					buffer.append("  } ");
					sep = ", ";
					buffer.append(sep);
				}
				sep1 = ", ";
				buffer.append(sep1);
				
				buffer.append("  ]");
				buffer.append("  } ");
			//	buffer.append("  } ");
			}

			buffer.append("  }");
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return buffer.toString();

	}

	public static void main(String[] args) {
		String startDate="2016/09/07";
		String endDate="2016/09/08";
		String mdata = JsonData.returnJson();
		System.out.println("json in string " + mdata);
		 Date utilDate1 = null;
		    Date utilDate2 = null;
		
		        try {
					utilDate1 = new SimpleDateFormat("yyyy/MM/dd").parse(startDate);
					utilDate2 = new SimpleDateFormat("yyyy/MM/dd").parse(endDate);
					System.err.println("############################");
					System.out.println("start date is "+utilDate1);
					System.out.println("end date is "+utilDate2);
					System.err.println("############################");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    
	}

}