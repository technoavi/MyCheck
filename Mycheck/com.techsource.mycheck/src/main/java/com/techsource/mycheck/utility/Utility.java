package com.techsource.mycheck.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum Utility {
	INSTANCE;
	private final Logger logger = LoggerFactory.getLogger(Utility.class);

	/****
	 * random 5 digits password
	 * 
	 * @return
	 */
	public String getRandomPassword() {
		Random r = new Random(System.currentTimeMillis());
		int data = ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
		return String.valueOf(data);
	}

	/****
	 * StringToDate
	 * 
	 * @param inputDate
	 * @return
	 */
	public Date stringToDate(String inputDate) {
		Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		try {
			date = formatter.parse(inputDate);
		} catch (ParseException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new MyException(e.getMessage(), e);
		}
		return date;
	}


	/****
	 * formatDate
	 * 
	 * @param date
	 * @param initDateFormat
	 * @param endDateFormat
	 * @return
	 * @throws ParseException
	 */

	public String formatDate(String date, String initDateFormat, String endDateFormat) throws ParseException {

		String parsedDate = null;
		try {
			Date initDate = new SimpleDateFormat(initDateFormat).parse(date);
			SimpleDateFormat formatter = new SimpleDateFormat(endDateFormat);
			parsedDate = formatter.format(initDate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return parsedDate;
	}

	public String convertCurrentdateToSqlDate(Date date) {
		String dateForMySql = "";
		if (date == null) {
			dateForMySql = null;
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			dateForMySql = sdf.format(date);
		}

		return dateForMySql;
	}
}
