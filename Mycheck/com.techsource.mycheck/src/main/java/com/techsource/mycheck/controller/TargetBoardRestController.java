/**
 * 
 */
package com.techsource.mycheck.controller;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.visualization.datasource.DataSourceHelper;
import com.google.visualization.datasource.DataSourceRequest;
import com.google.visualization.datasource.base.DataSourceException;
import com.google.visualization.datasource.base.ReasonType;
import com.google.visualization.datasource.base.ResponseStatus;
import com.google.visualization.datasource.base.StatusType;
import com.google.visualization.datasource.base.TypeMismatchException;
import com.google.visualization.datasource.datatable.ColumnDescription;
import com.google.visualization.datasource.datatable.DataTable;
import com.google.visualization.datasource.datatable.value.ValueType;
import com.techsource.mycheck.AppController;
import com.techsource.mycheck.domain.TargetBoard;
import com.techsource.mycheck.domain.TargetQuestions;
import com.techsource.mycheck.service.QuaterService;
import com.techsource.mycheck.service.TargetBoardService;
import com.techsource.mycheck.service.TargetQuestionService;
import com.techsource.mycheck.service.report.ReportService;
import com.techsource.mycheck.utility.Constants;
import com.techsource.mycheck.vo.QuarterVO;
import com.techsource.mycheck.vo.RequestTargetBoard;
import com.techsource.mycheck.vo.TargetBoardVO;
import com.techsource.mycheck.vo.TargetQuestionVO;

/**
 * @author technoavi
 *
 */
@Controller
@RequestMapping("/targetBoard")
public class TargetBoardRestController extends AppController {
	private final Logger logger = LoggerFactory.getLogger(TargetBoardRestController.class);

	@Autowired
	private QuaterService quaterService;

	@Autowired
	private TargetBoardService targetBoardService;

	@Autowired
	TargetQuestionService targetQuestionService;
	@Autowired
	private ReportService reportService;

	/*
	 * @Autowired TargetBoardQuestionService targetBoardQuestionService;
	 */

	@RequestMapping(value = "/getYearsbyEmpId.htm", method = RequestMethod.POST)
	public @ResponseBody String getYearsbyEmpId(@RequestParam("empId") String empId,
			@RequestParam("state") String state, HttpServletRequest request, HttpServletResponse response) {
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\",\"ResponseData\":\"Something went wrong. Please check.\"}";
		try {
			List<QuarterVO> quaterVO = quaterService.getYearsbyEmpId(Integer.parseInt(empId), state);
			if (quaterVO != null) {
				Map<String, List<QuarterVO>> map = new HashMap<String, List<QuarterVO>>();
				for (QuarterVO quaterVO2 : quaterVO) {
					if (map.size() > 0) {
						if (map.containsKey(quaterVO2.getYear())) {
							List<QuarterVO> maps = map.get(quaterVO2.getYear());
							maps.add(quaterVO2);
							// System.out.println("map.size()>0 called");
						} else {
							List<QuarterVO> quaterVOs2 = new LinkedList<QuarterVO>();
							quaterVOs2.add(quaterVO2);
							map.put(quaterVO2.getYear(), quaterVOs2);
						}

					} else {
						List<QuarterVO> quaterVOs2 = new LinkedList<QuarterVO>();
						quaterVOs2.add(quaterVO2);
						map.put(quaterVO2.getYear(), quaterVOs2);
					}
				}
				Type type = new TypeToken<Map<String, List<QuarterVO>>>() {
				}.getType();
				String data = new Gson().toJson(map, type);

				result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":" + data + "}";
			} else {
				result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"" + Constants.STATUS_MESSAGE_FAILED
						+ "\",\"ResponseData\":\"" + Constants.EMPLOYEE_NOT_EXISTS + "\"}";
			}

		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;

	}

	@RequestMapping(value = "/getTargetByEmpIdAndQtrId.htm", method = RequestMethod.POST)
	public @ResponseBody String getTargetByEmpIdAndQtrId(@RequestParam("empId") String empId,
			@RequestParam("qtrId") String qtrId, HttpServletRequest request, HttpServletResponse response) {
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\",\"ResponseData\":\"Something went wrong. Please check.\"}";
		try {
			List<TargetBoardVO> targetBoardVO = targetBoardService.getTargetByEmpIdAndQtrId(Integer.parseInt(empId),
					Integer.parseInt(qtrId));
			if (targetBoardVO != null) {
				result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":"
						+ new Gson().toJson(targetBoardVO) + "}";
			} else {
				result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"" + Constants.STATUS_MESSAGE_FAILED
						+ "\",\"ResponseData\":\"" + Constants.EMPLOYEE_NOT_EXISTS + "\"}";
			}

		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;

	}

	@RequestMapping(value = "/getTargetQstnbyEmpId.htm", method = RequestMethod.POST)
	public @ResponseBody String getTargetQstnbyEmpId(@RequestParam("empId") String empId, HttpServletRequest request,
			HttpServletResponse response) {
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\",\"ResponseData\":\"Something went wrong. Please check.\"}";
		try {
			List<TargetQuestionVO> targetQuestionVO = targetQuestionService
					.getTargetQstnbyEmpId(Integer.parseInt(empId));
			if (targetQuestionVO != null) {
				result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":"
						+ new Gson().toJson(targetQuestionVO) + "}";
			} else {
				result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"" + Constants.STATUS_MESSAGE_FAILED
						+ "\",\"ResponseData\":\"" + Constants.EMPLOYEE_NOT_EXISTS + "\"}";
			}

		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";

		}

		return result;

	}

	@RequestMapping(value = "/getTargetQstnbyEmpIdAndQtrid.htm", method = RequestMethod.POST)
	public @ResponseBody String getTargetQstnbyEmpIdAndQtrid(@RequestParam("empId") String empId,
			@RequestParam("qtrId") String qtrId, HttpServletRequest request, HttpServletResponse response) {
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\",\"ResponseData\":\"Something went wrong. Please check.\"}";
		try {
			List<TargetBoardVO> targetBoardVO = targetBoardService.getTargetByEmpIdAndQtrId(Integer.parseInt(empId),
					Integer.parseInt(qtrId));
			List<RequestTargetBoard> requestTargetBoards = new ArrayList<RequestTargetBoard>();
			if (targetBoardVO != null) {
				int qtrids = 0;
				for (TargetBoardVO targetBoardVO2 : targetBoardVO) {
					qtrids = targetBoardVO2.getId();
					List<TargetQuestionVO> targetQuestionVO3 = targetQuestionService.getTargetQstnbyTargetId(qtrids);
					if (targetQuestionVO3.size() > 0) {
						RequestTargetBoard requestTargetBoard = new RequestTargetBoard();
						requestTargetBoard.setEmpId(Integer.parseInt(empId));
						requestTargetBoard.setQuaterId(Integer.parseInt(qtrId));
						requestTargetBoard.setTargetBoard(targetBoardVO2);
						requestTargetBoard.setTargetQuestions(targetQuestionVO3);
						requestTargetBoards.add(requestTargetBoard);
					}
				}

				result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":"
						+ new Gson().toJson(requestTargetBoards) + "}";
			} else {
				result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"" + Constants.STATUS_MESSAGE_FAILED
						+ "\",\"ResponseData\":\"" + Constants.EMPLOYEE_NOT_EXISTS + "\"}";
			}

		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;

	}

	@RequestMapping(value = "/getYearAndQtrbyEmpId.htm", method = RequestMethod.POST)
	public @ResponseBody String getYearAndQtrbyEmpId(@RequestParam("empId") String empId,
			@RequestParam("year") String year, HttpServletRequest request, HttpServletResponse response) {
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\",\"ResponseData\":\"Something went wrong. Please check.\"}";
		try {
			List<QuarterVO> quaterVO = quaterService.getYearAndQtrbyEmpId(Integer.parseInt(empId), year);
			if (quaterVO != null) {
				result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":"
						+ new Gson().toJson(quaterVO) + "}";
				System.out.println("1" + quaterVO);
			} else {
				result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"" + Constants.STATUS_MESSAGE_FAILED
						+ "\",\"ResponseData\":\"" + Constants.EMPLOYEE_NOT_EXISTS + "\"}";
				System.out.println("2" + quaterVO);
			}

		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;

	}

	@RequestMapping(value = "/getTargetbyEmpIdAndYearAndQtr.htm", method = RequestMethod.POST)
	public @ResponseBody String getTargetbyEmpIdAndYearAndQtr(@RequestParam("empId") String empId,
			@RequestParam("year") String year, @RequestParam("qtr") String qtr, HttpServletRequest request,
			HttpServletResponse response) {
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\",\"ResponseData\":\"Something went wrong. Please check.\"}";
		try {
			List<TargetBoardVO> targetBoardVO = targetBoardService
					.getTargetbyEmpIdAndYearAndQtr(Integer.parseInt(empId), year, qtr);
			if (targetBoardVO != null) {
				result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":"
						+ new Gson().toJson(targetBoardVO) + "}";
				System.out.println("1" + targetBoardVO);
			} else {
				result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"" + Constants.STATUS_MESSAGE_FAILED
						+ "\",\"ResponseData\":\"" + Constants.EMPLOYEE_NOT_EXISTS + "\"}";
				System.out.println("2" + targetBoardVO);
			}

		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;

	}

	@RequestMapping(value = "/copyTargetBoardNextQuarter.htm", method = RequestMethod.POST)
	public @ResponseBody String copyTargetBoardNextQuarter(@RequestParam("empId") String empId,
			@RequestParam("qtrId") String qtrId, HttpServletRequest request, HttpServletResponse response) {
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\",\"ResponseData\":\"Innevarande  kvartal har ockuperat , Kontrollera med admin.\"}";
		try {
			String responseData = targetBoardService.copyTargetBoardNextQuarter(Integer.parseInt(empId),
					Integer.parseInt(qtrId));
			if (responseData != null) {
				result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":\"" + responseData
						+ "\"}";
			}

		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;

	}

	@RequestMapping(value = "/getTargetQstnByQtrId.htm", method = RequestMethod.POST)
	public @ResponseBody String getTargetQstnByQtrId(@RequestParam("qtrId") String qtrId, HttpServletRequest request,
			HttpServletResponse response) {
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\",\"ResponseData\":\"Something went wrong. Please check.\"}";
		try {
			List<TargetQuestionVO> targetQuestionVOQtr = targetQuestionService
					.getTargetQstnByQtrId(Integer.parseInt(qtrId));
			if (targetQuestionVOQtr != null) {
				result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":"
						+ new Gson().toJson(targetQuestionVOQtr) + "}";
				System.out.println("1" + targetQuestionVOQtr);
			} else {
				result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"" + Constants.STATUS_MESSAGE_FAILED
						+ "\",\"ResponseData\":\"" + Constants.EMPLOYEE_NOT_EXISTS + "\"}";
				System.out.println("2" + targetQuestionVOQtr);
			}

		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;

	}

	class ResponseTemp {

		public ResponseTemp() {

		}

		private int id;
		private String description;
		private String name;
		private List<TargetQuestionVO> questions;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public List<TargetQuestionVO> getQuestions() {
			return questions;
		}

		public void setQuestions(List<TargetQuestionVO> questions) {
			this.questions = questions;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

	@RequestMapping(value = "/getTargetBdDescpAndTgtBdQstnByQtrIdAndTgBdId.htm", method = RequestMethod.POST)
	public @ResponseBody String getTargetBdDescpAndTgtBdQstnByQtrIdAndTgBdId(@RequestParam("qtrId") String qtrId,
			@RequestParam("tgtbdId") String tgtbdId, HttpServletRequest request, HttpServletResponse response) {
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\",\"ResponseData\":\"Something went wrong. Please check.\"}";

		try {
			TargetBoardVO targetBoardVOVOQtr = targetBoardService
					.getTargetBdDescpAndTgtBdQstnByQtrIdAndTgBdId(Integer.parseInt(qtrId), Integer.parseInt(tgtbdId));
			if (targetBoardVOVOQtr != null) {
				List<TargetQuestionVO> targetQuestionVO = targetQuestionService
						.getquestionsByTgtIdId(Integer.parseInt(tgtbdId));
				ResponseTemp temp = new ResponseTemp();
				temp.setId(targetBoardVOVOQtr.getId());
				temp.setDescription(targetBoardVOVOQtr.getDescription());
				temp.setName(targetBoardVOVOQtr.getName());
				temp.setQuestions(targetQuestionVO);
				result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":"
						+ new Gson().toJson(temp) + "}";

			} else {
				result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"" + Constants.STATUS_MESSAGE_FAILED
						+ "\",\"ResponseData\":\"" + Constants.EMPLOYEE_NOT_EXISTS + "\"}";
			}

		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;

	}

	//
	@RequestMapping(value = "/getTargetBdDescpAndTgtBdQstnByQtrId.htm", method = RequestMethod.POST)
	public @ResponseBody String getTargetBdDescpAndTgtBdQstnByQtrId(@RequestParam("empId") String empId,
			@RequestParam("qtrId") String qtrId, HttpServletRequest request, HttpServletResponse response) {
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\",\"ResponseData\":\"Something went wrong. Please check.\"}";

		try {
			List<TargetBoard> targetBoardVOVOQtr = targetBoardService
					.getTargetBdDescpAndTgtBdQstnByQtrIdAndEmpId(Integer.parseInt(empId), Integer.parseInt(qtrId));
			if (targetBoardVOVOQtr != null) {

				result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":"
						+ new Gson().toJson(targetBoardVOVOQtr) + "}";

			} else {
				result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"" + Constants.STATUS_MESSAGE_FAILED
						+ "\",\"ResponseData\":\"" + Constants.EMPLOYEE_NOT_EXISTS + "\"}";
			}

		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;

	}

	@RequestMapping(value = "/insertTargetBoard.htm", method = RequestMethod.POST)
	public @ResponseBody String insertTargetBoard(@RequestParam("jsonData") String jsonData, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("insertTargetBoard method started");
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\",\"ResponseData\":\"Something went wrong. Please check.\"}";
		try {
			RequestTargetBoard requestTargetBoard = this.prepareJSonObject(jsonData, false);
			if (requestTargetBoard != null) {
				boolean flag = targetBoardService.insertTargetBoard(requestTargetBoard);
				if (flag) {
					result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":\"Target questions updated successfully.\"}";
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;

	}

	@RequestMapping(value = "/updateTargetDetails.htm", method = RequestMethod.POST)
	public @ResponseBody String updateTargetDetails(@RequestParam("jsonData") String jsonData,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("insertTargetBoard method started");
		System.out.println("updated json ::" + jsonData);
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\",\"ResponseData\":\"Something went wrong. Please check.\"}";
		try {
			RequestTargetBoard requestTargetBoard = this.prepareJSonObject(jsonData, true);
			if (requestTargetBoard != null) {
				boolean flag = targetBoardService.updateTargetQuestions(requestTargetBoard);
				if (flag) {
					result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":\"Target questions updated successfully.\"}";
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;

	}

	public RequestTargetBoard prepareJSonObject(String jsonData, boolean updateFlag) {
		try {
			JSONObject jsonObject = new JSONObject(jsonData);
			RequestTargetBoard requestTargetBoard = new RequestTargetBoard();

			int eId = Integer.parseInt(jsonObject.get("empId").toString());
			requestTargetBoard.setEmpId(eId);
			int quaterId = Integer.parseInt(jsonObject.get("quarterId").toString());
			requestTargetBoard.setQuaterId(quaterId);
			JSONObject jsonObject2 = jsonObject.getJSONObject("targetBoard");
			TargetBoardVO targetBoardVO = new TargetBoardVO();

			String name = jsonObject2.get("name").toString();
			targetBoardVO.setName(name);
			if (updateFlag) {
				int targetId = jsonObject2.getInt("id");
				targetBoardVO.setId(targetId);
			}

			String description = jsonObject2.get("description").toString();
			targetBoardVO.setDescription(description);
			targetBoardVO.setState("OPEN");
			requestTargetBoard.setTargetBoard(targetBoardVO);

			// target questions info
			List<TargetQuestionVO> datas = new LinkedList<TargetQuestionVO>();
			JSONArray jsonArray = jsonObject2.getJSONArray("targetQuestions");
			for (int i = 0; i < jsonArray.length(); i++) {
				TargetQuestionVO targetQuestions = new TargetQuestionVO();
				JSONObject jsonObject3 = jsonArray.getJSONObject(i);
				String q_name = jsonObject3.get("name").toString();
				targetQuestions.setId(jsonObject3.getInt("id"));
				targetQuestions.setDescription(jsonObject3.getString("description"));
				targetQuestions.setName(q_name);
				Object object = jsonObject3.get("status");
				int statusFlag = 0;

				if (object instanceof Boolean) {
					Boolean status = jsonObject3.getBoolean("status");
					if (status) {
						statusFlag = 1;
					}
					System.out.println("Boolean called");
				} else if (object instanceof Integer) {
					int status = jsonObject3.getInt("status");
					if (status == 1) {
						statusFlag = 1;
					}
					System.out.println("Integer called");
				}
				System.out.println("statusFlag ::" + statusFlag);
				targetQuestions.setStatus((byte) statusFlag);
				datas.add(targetQuestions);

			}
			requestTargetBoard.setTargetQuestions(datas);
			return requestTargetBoard;
		} catch (NumberFormatException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = "/insertTargetQuestions.htm", method = RequestMethod.POST)
	public @ResponseBody String insertTargetQuestions(@RequestParam("name") String name,
			@RequestParam("description") String description, @RequestParam("status") String status,
			HttpServletRequest request, HttpServletResponse response) {
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\",\"ResponseData\":\"Something went wrong. Please check.\"}";
		try {
			TargetQuestions targetQuestions = new TargetQuestions();
			targetQuestions.setName(name);
			targetQuestions.setDescription(description);

			String statusString = status;
			byte statusByte = Byte.valueOf(statusString);

			targetQuestions.setStatus(statusByte);
			targetQuestions.setCreated(new Date());
			targetQuestions.setModified(new Date());
			targetQuestions.setDeleted(new Date());

			boolean flag = targetQuestionService.insertTargetQuestion(targetQuestions);
			String statusMessage = Constants.STATUS_MESSAGE_FAILED;
			String statusCode = Constants.STATUS_CODE_FAILED;
			if (flag) {

				statusMessage = Constants.STATUS_MESSAGE_SUCCESS;
				statusCode = Constants.STATUS_CODE_SUCCESS;

			}

			result = "{\"StatusCode\":\"" + statusCode + "\",\"StatusMessage\":\"" + statusMessage
					+ "\",\"ResponseData\":\"" + flag + "\"Target Questions added successfully\":\"" + "\"}";

		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;

	}

	@RequestMapping(value = "/updateTargetQuestions.htm", method = RequestMethod.POST)
	public @ResponseBody String updateTargetQuestions(@RequestParam("id") String id, @RequestParam("name") String name,
			@RequestParam("description") String description, @RequestParam("status") String status,
			HttpServletRequest request, HttpServletResponse response) {
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\",\"ResponseData\":\"Something went wrong. Please check.\"}";
		boolean flag = false;
		try {
			TargetQuestions targetQuestions = new TargetQuestions();
			boolean flag1 = targetQuestionService.checkQstnIdExists(Integer.parseInt(id));
			if (flag1) {
				System.out.println("rest " + flag1);
				targetQuestions.setId(Integer.parseInt(id));
				targetQuestions.setName(name);
				targetQuestions.setDescription(description);

				String statusString = status;
				byte statusByte = Byte.valueOf(statusString);

				targetQuestions.setStatus(statusByte);
				targetQuestions.setCreated(new Date());
				targetQuestions.setModified(new Date());
				targetQuestions.setDeleted(new Date());

				flag = targetQuestionService.updateTargetQuestion(targetQuestions);
			}
			String statusMessage = Constants.STATUS_MESSAGE_FAILED;
			String statusCode = Constants.STATUS_CODE_FAILED;
			if (flag) {

				statusMessage = Constants.STATUS_MESSAGE_SUCCESS;
				statusCode = Constants.STATUS_CODE_SUCCESS;

			}

			result = "{\"StatusCode\":\"" + statusCode + "\",\"StatusMessage\":\"" + statusMessage
					+ "\",\"ResponseData\":\"" + flag + "\"Target Questions Updated successfully\":\"" + "\"}";

		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + flag + "\""
					+ e.getMessage() + "\"}";
		}

		return result;

	}

	@RequestMapping(value = "/addTargetQuestionsByTargetId.htm", method = RequestMethod.POST)
	public @ResponseBody String addTargetQuestionsByTargetId(@RequestParam("jsonData") String jsonData,
			HttpServletRequest request, HttpServletResponse response) {
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\",\"ResponseData\":\"Something went wrong. Please check.\"}";

		try {
			RequestTargetBoard requestTargetBoard = this.prepareJSonObject1(jsonData);
			if (requestTargetBoard != null) {
				boolean flag = targetBoardService.updateTargetQuestionsByTgtId(requestTargetBoard);
				if (flag) {
					result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":\"Target questions added successfully.\"}";
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"" + e.getMessage()
					+ "\"}";
		}

		return result;

	}

	public RequestTargetBoard prepareJSonObject1(String jsonData) {
		try {
			JSONObject jsonObject = new JSONObject(jsonData);
			RequestTargetBoard requestTargetBoard = new RequestTargetBoard();

			int eId = Integer.parseInt(jsonObject.get("empId").toString());
			requestTargetBoard.setEmpId(eId);
			int quaterId = Integer.parseInt(jsonObject.get("quarterId").toString());
			requestTargetBoard.setQuaterId(quaterId);
			JSONObject jsonObject2 = jsonObject.getJSONObject("targetBoard");
			TargetBoardVO targetBoardVO = new TargetBoardVO();
			int targetId = jsonObject2.getInt("id");
			targetBoardVO.setId(targetId);
			requestTargetBoard.setTargetBoard(targetBoardVO);

			// target questions info
			JSONArray jsonArray = jsonObject2.getJSONArray("targetQuestions");
			List<TargetQuestionVO> datas = new LinkedList<TargetQuestionVO>();
			for (int i = 0; i < jsonArray.length(); i++) {
				TargetQuestionVO targetQuestions = new TargetQuestionVO();
				JSONObject jsonObject3 = jsonArray.getJSONObject(i);
				String q_name = jsonObject3.get("name").toString();
				// targetQuestions.setId(jsonObject3.getInt("id"));
				targetQuestions.setDescription(jsonObject3.getString("description"));
				targetQuestions.setName(q_name);
				Object object = jsonObject3.get("status");
				int statusFlag = 0;

				if (object instanceof Boolean) {
					Boolean status = jsonObject3.getBoolean("status");
					if (status) {
						statusFlag = 1;
					}
					System.out.println("Boolean called");
				} else if (object instanceof Integer) {
					int status = jsonObject3.getInt("status");
					if (status == 1) {
						statusFlag = 1;
					}
					System.out.println("Integer called");
				}
				System.out.println("statusFlag ::" + statusFlag);
				targetQuestions.setStatus((byte) statusFlag);
				datas.add(targetQuestions);

			}
			requestTargetBoard.setTargetQuestions(datas);
			return requestTargetBoard;
		} catch (NumberFormatException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = "/deleteQstnById.htm", method = RequestMethod.POST)
	public @ResponseBody String deleteQstnById(@RequestParam("qstnId") String qstnId, HttpServletRequest request,
			HttpServletResponse response) {
		// String result =
		// "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\",\"ResponseData\":\"Something
		// went wrong. Please check.\"}";
		String result = null;
		try {
			boolean flag = targetQuestionService.deleteQuestionById(Integer.parseInt(qstnId));

			if (flag) {
				result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":\"Target questions deleted successfully.\"}";

			} else {
				result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":\"No record Found for the current Question Id :"
						+ qstnId + "\"}";

			}

		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"No record Found for the current Question Id :"
					+ qstnId + "\"}";
		}

		return result;
	}

	// sudo iptables -I INPUT 1 -i eth0 -p tcp --dport 8080 -j ACCEPT
	// iptables-save
	@RequestMapping(value = "/archivedByEmpIdAndQtrId.htm", method = RequestMethod.POST)
	public @ResponseBody String archivedByEmpIdAndQtrId(@RequestParam("empId") String empId,
			@RequestParam("qtrId") String qtrId, HttpServletRequest request, HttpServletResponse response) {

		String result = null;
		boolean flag = false;
		try {
			flag = targetBoardService.archiveSelectedTargetBoardByEmpIdAndQtrId(Integer.parseInt(empId),
					Integer.parseInt(qtrId));
			System.out.println(flag);
			if (flag) {
				List<TargetBoardVO> boardVOs = targetBoardService.getTargetByEmpIdAndQtrId(Integer.parseInt(empId),
						Integer.parseInt(qtrId));

				if (boardVOs != null) {
					result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":"
							+ new Gson().toJson(boardVOs) + "}";
				}

			} else {
				result = "{\"StatusCode\":\"200\",\"StatusMessage\":\"success\",\"ResponseData\":\"No record Found for the current TargetBoard Id :"
						+ empId + "\"}";

			}

		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"No record Found for the current Question Id :"
					+ e.getMessage() + "\"}";
		}

		return result;
	}

	/***
	 * 
	 * Generate google charts info for getTargetsByEmpId
	 * 
	 * @param empId
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */

	@RequestMapping(value = "/getTargetsByEmpId.htm", method = RequestMethod.POST)
	public @ResponseBody String getTargetsByEmpId(@RequestParam("empId") String empId,
			@RequestParam("qtrId") String qtrId, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		DataSourceRequest dsRequest = null;

		try {
			DataTable data = generateTargetBoardReportByEmpId(empId, qtrId);
			if (data != null) {
				// Extract the datasource request parameters.
				dsRequest = new DataSourceRequest(request);

				// Apply the query to the data table.
				DataTable newData = DataSourceHelper.applyQuery(dsRequest.getQuery(), data, dsRequest.getUserLocale());
				System.out.println(new Gson().toJson(newData));
				// Set the response.
				DataSourceHelper.setServletResponse(data, dsRequest, response);
			} else {
				ResponseStatus status = new ResponseStatus(StatusType.ERROR, ReasonType.INTERNAL_ERROR, "error");
				if (dsRequest == null) {
					dsRequest = DataSourceRequest.getDefaultDataSourceRequest(request);
				}
				DataSourceHelper.setServletErrorResponse(status, dsRequest, response);
			}
		} catch (Exception e) {
			ResponseStatus status = new ResponseStatus(StatusType.ERROR, ReasonType.INTERNAL_ERROR, e.getMessage());
			if (dsRequest == null) {
				dsRequest = DataSourceRequest.getDefaultDataSourceRequest(request);
			}
			DataSourceHelper.setServletErrorResponse(status, dsRequest, response);
		}
		return null;

	}

	/****
	 * Prepare data table
	 * 
	 * @param empId
	 * @return
	 */
	private DataTable generateTargetBoardReportByEmpId(String empId, String qtrId) {
		try {
			// Create a data table
			// DataTable data = new DataTable();
			// ArrayList<ColumnDescription> cd = new
			// ArrayList<ColumnDescription>();
			// cd.add(new ColumnDescription("name", ValueType.TEXT, "Project
			// name"));
			// cd.add(new ColumnDescription("employees", ValueType.NUMBER,
			// "Employees"));
			// cd.add(new ColumnDescription("total", ValueType.NUMBER, "Total
			// Amount"));
			//
			// data.addColumns(cd);
			//
			// // Fill the data table.
			// try {
			// data.addRowFromValues("project1", 20, 100);
			// data.addRowFromValues("project2", 20, 100);
			// } catch (TypeMismatchException e) {
			// System.out.println("Invalid type!");
			// }

			return reportService.getTargetBoardReportByEmpId(Integer.parseInt(empId), Integer.parseInt(qtrId));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@RequestMapping(value = "/updateTargtQstnStatusbyQstnId.htm", method = RequestMethod.POST)
	public @ResponseBody String updateTargtQstnStatusbyqstnId(@RequestParam("qstnId") String qstnId,
			@RequestParam("status") String status, HttpServletRequest request, HttpServletResponse response) {
		String result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"failed\",\"ResponseData\":\"Something went wrong. Please check.\"}";
		String statusMessage = Constants.STATUS_MESSAGE_SUCCESS;
		String statusCode = Constants.STATUS_CODE_SUCCESS;
		boolean flag = false;
		try {
			boolean flag1 = targetQuestionService.checkQstnIdExists(Integer.parseInt(qstnId));
			if (flag1) {

				flag = targetQuestionService.updateTargetQstnStatus(Integer.parseInt(qstnId), status);

				result = "{\"StatusCode\":\"" + statusCode + "\",\"StatusMessage\":\"" + statusMessage
						+ "\",\"ResponseData\":\"Target Questions Updated successfully\"}";
			} else {
				statusMessage = Constants.STATUS_MESSAGE_FAILED;
				statusCode = Constants.STATUS_CODE_FAILED;
				result = "{\"StatusCode\":\"" + statusCode + "\",\"StatusMessage\":\"" + statusMessage
						+ "\",\"ResponseData\":\"No record Found for the current TargetQuestions Id :" + qstnId + "\"}";

			}

		} catch (Exception e) {
			result = "{\"StatusCode\":\"400\",\"StatusMessage\":\"expetion\",\"ResponseData\":\"No record Found for the current Question Id :"
					+ e.getMessage() + "\"}";
		}

		return result;

	}

}
