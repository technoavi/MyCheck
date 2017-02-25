package com.techsource.mycheck.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techsource.mycheck.dao.EmployeeDao;
import com.techsource.mycheck.dao.QuarterDao;
import com.techsource.mycheck.dao.TargetBoardDao;
import com.techsource.mycheck.dao.TargetQuestionDao;
import com.techsource.mycheck.domain.EmpQuaterTargetboard;
import com.techsource.mycheck.domain.Employee;
import com.techsource.mycheck.domain.Quaters;
import com.techsource.mycheck.domain.TargetBoard;
import com.techsource.mycheck.domain.TargetQuestions;
import com.techsource.mycheck.domain.TargetboardTargetquestions;
import com.techsource.mycheck.utility.Constants;
import com.techsource.mycheck.vo.EmployeeVO;
import com.techsource.mycheck.vo.RequestTargetBoard;
import com.techsource.mycheck.vo.TargetBoardVO;
import com.techsource.mycheck.vo.TargetQuestionVO;

@Service("targetBoardService")
public class TargetBoardServiceImpl implements TargetBoardService {
	private final Logger logger = LoggerFactory.getLogger(TargetBoardServiceImpl.class);

	@Autowired
	private TargetBoardDao targetBoardDao;

	@Autowired
	private QuarterDao quarterDao;

	@Autowired
	private EmployeeDao employeeDao;

	@Autowired
	private TargetQuestionDao targetQuestionDao;

	@Override
	@Transactional
	public boolean insertTargetBoard(RequestTargetBoard requestTargetBoard) {

		// Check if target board name is same for empId and quarter id
		// insert target board
		// insert target questions

		// update the employee,quarter and targetBoard relation

		try {
			TargetBoardVO targetBoardVO = requestTargetBoard.getTargetBoard();
			TargetBoard targetBoard = new TargetBoard();
			targetBoard.setName(targetBoardVO.getName());
			targetBoard.setDescription(targetBoardVO.getDescription());
			targetBoard.setStatus(Constants.ACTIVE);
			targetBoard.setCreated(new Date());
			targetBoard.setModified(new Date());
			targetBoard.setDeleted(new Date());
			targetBoard.setState("OPEN");

			// Set of target board questions
			List<TargetQuestionVO> targetQuestionsVO = requestTargetBoard.getTargetQuestions();
			Set<TargetboardTargetquestions> targetboardTargetquestions = new HashSet<TargetboardTargetquestions>();
			for (TargetQuestionVO targetQuestionVO : targetQuestionsVO) {
				// target board questions
				TargetQuestions targetQuestion = new TargetQuestions();
				targetQuestion.setCreated(new Date());
				targetQuestion.setModified(new Date());
				targetQuestion.setDeleted(new Date());
				targetQuestion.setDescription(targetQuestionVO.getDescription());
				targetQuestion.setName(targetQuestionVO.getName());
				targetQuestion.setStatus((byte) targetQuestionVO.getStatus());
				targetQuestionDao.insertTargetQuestions(targetQuestion);
				// relation of target board and target board questions
				TargetboardTargetquestions targetboardTargetquestions2 = new TargetboardTargetquestions();
				targetboardTargetquestions2.setTargetQuestions(targetQuestion);
				targetboardTargetquestions2.setTargetBoard(targetBoard);
				targetboardTargetquestions2.setCreated(new Date());
				targetboardTargetquestions2.setModified(new Date());
				targetboardTargetquestions2.setDeleted(new Date());

				targetboardTargetquestions.add(targetboardTargetquestions2);
				targetQuestion.setTargetboardTargetquestionses(targetboardTargetquestions);

			}

			targetBoard.setTargetboardTargetquestionses(targetboardTargetquestions);
			targetBoardDao.insertTargetBoard(targetBoard);
			Set<EmpQuaterTargetboard> empQuaterTargetboards = new HashSet<EmpQuaterTargetboard>();
			// employee,quarter and targetBoard relation
			EmpQuaterTargetboard empQuaterTargetboard = new EmpQuaterTargetboard();
			empQuaterTargetboard.setStatus(Constants.ACTIVE);
			empQuaterTargetboard.setState("OPEN");
			empQuaterTargetboards.add(empQuaterTargetboard);
			// get quarter info
			Quaters quarter = quarterDao.getRowById(requestTargetBoard.getQuaterId());
			empQuaterTargetboard.setQuaters(quarter);
			quarter.setEmpQuaterTargetboards(empQuaterTargetboards);

			// get employee info
			Employee employee = employeeDao.getById(requestTargetBoard.getEmpId());
			empQuaterTargetboard.setEmployee(employee);
			employee.setEmpQuaterTargetboards(empQuaterTargetboards);

			// targetBoard
			empQuaterTargetboard.setTargetBoard(targetBoard);
			targetBoard.setEmpQuaterTargetboards(empQuaterTargetboards);

			return true;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public List<TargetBoardVO> getList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TargetBoardVO> getListByEmployeeId(String empId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TargetBoardVO getRowById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateRow(TargetBoardVO targetBoard) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteRow(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	@Transactional
	public List<TargetBoardVO> getTargetByEmpIdAndQtrId(int empid, int qtrId) {
		// TODO Auto-generated method stub
		return (List<TargetBoardVO>) targetBoardDao.getTargetByEmpIdAndQtrId(empid, qtrId);
	}

	@Override
	@Transactional
	public List<TargetBoardVO> getTargetbyEmpIdAndYearAndQtr(int empid, String year, String qtr) {
		// TODO Auto-generated method stub
		return (List<TargetBoardVO>) targetBoardDao.getTargetbyEmpIdAndYearAndQtr(empid, year, qtr);
	}

	@Override
	@Transactional
	public TargetBoardVO getTargetBdDescpAndTgtBdQstnByQtrIdAndTgBdId(int qtrId, int tgtBdId) {
		// TODO Auto-generated method stub
		return targetBoardDao.getTargetBdDescpAndTgtBdQstnByQtrIdAndTgBdId(qtrId, tgtBdId);
	}

	/*
	 * @Override
	 * 
	 * @Transactional public TargetBoardVO
	 * getTargetBdDescpAndTgtBdQstnByQtrIdAndTgBdId(int qtrId, int tgtBdId) { //
	 * TODO Auto-generated method stub return
	 * targetBoardDao.getTargetBdDescpAndTgtBdQstnByQtrIdAndTgBdId(qtrId,
	 * tgtBdId); }
	 */
	/****
	 * copyTargetBoardNextQuarter
	 */

	@Override
	@Transactional
	public String copyTargetBoardNextQuarter(int empId, int quarterId) {
		System.out.println("empId ::" + empId);
		try {
			// validate the employee has quarter or not
			boolean flag = quarterDao.getQuartersExistsByEmpId(empId);
			if (flag) {
				// employee have at least one quarter
				Quaters quaters = quarterDao.getNextQuarterDetails(empId, true);
				if (quaters == null) { // employee occupied current quarter
					System.out.println("quaters == null called");
					return null;
				} else { // employee have one more quarter left.
					System.out.println("quaters != null called");
					List<EmpQuaterTargetboard> empQuaterTargetboardList = targetBoardDao
							.getCopiedQuarterSourceDetails(empId, quarterId);
					if (empQuaterTargetboardList.size() > 0) {
						System.out.println("empQuaterTargetboardList ::" + empQuaterTargetboardList.size());
						for (EmpQuaterTargetboard empQuaterTargetboard : empQuaterTargetboardList) {
							TargetBoard targetBoard = empQuaterTargetboard.getTargetBoard();

							TargetBoardVO targetBoard2 = new TargetBoardVO();
							targetBoard2.setName(targetBoard.getName());
							targetBoard2.setDescription(targetBoard.getDescription());

							List<TargetQuestionVO> targetQuestionsList = new ArrayList<TargetQuestionVO>();
							for (TargetboardTargetquestions targetboardTargetquestions : targetBoard
									.getTargetboardTargetquestionses()) {

								TargetQuestions targetQuestions = targetboardTargetquestions.getTargetQuestions();

								TargetQuestionVO targetQuestions2 = new TargetQuestionVO();
								targetQuestions2.setName(targetQuestions.getName());
								targetQuestions2.setStatus(targetQuestions.getStatus());
								targetQuestionsList.add(targetQuestions2);
							}

							RequestTargetBoard requestTargetBoard = new RequestTargetBoard();
							requestTargetBoard.setEmpId(empId);
							requestTargetBoard.setQuaterId(quaters.getId());
							requestTargetBoard.setTargetBoard(targetBoard2);
							requestTargetBoard.setTargetQuestions(targetQuestionsList);

							this.insertTargetBoard(requestTargetBoard);
							return "Legend kopierats";

						}
					} else {
						Quaters quaters2 = quarterDao.getNextQuarterDetails(empId, false);

						String json = "{\"empId\":" + empId + ",\"quarterId\":" + quaters2.getId()
								+ ",\"targetBoard\":{\"id\":1,\"name\":\"Avs\",\"description\":\"hi ghfhgfhfg \","
								+ "\"targetQuestions\":[{\"id\":3,\"name\":\"gfhfgh\",\"description\":\"hi ghfhgfhfg\",\"status\":false},"
								+ "{\"id\":5,\"name\":\"hfghfg\",\"description\":\" highfhgfhfg \",\"status\":false}]}}";
						System.out.println("json ::" + json);

						RequestTargetBoard requestTargetBoard = prepareJSonObject(json, false);
						this.insertTargetBoard(requestTargetBoard);
						return "Legend kopierats";
					}

				}

			} else {
				System.out.println("Else called");
				// // employee doesn't have any quarter
				Quaters quaters = quarterDao.getNextQuarterDetails(empId, false);

				String json = "{\"empId\":" + empId + ",\"quarterId\":" + quaters.getId()
						+ ",\"targetBoard\":{\"id\":1,\"name\":\"Avs\",\"description\":\"hi ghfhgfhfg \","
						+ "\"targetQuestions\":[{\"id\":3,\"name\":\"gfhfgh\",\"description\":\"hi ghfhgfhfg\",\"status\":false},"
						+ "{\"id\":5,\"name\":\"hfghfg\",\"description\":\" highfhgfhfg \",\"status\":false}]}}";
				System.out.println("json ::" + json);

				RequestTargetBoard requestTargetBoard = prepareJSonObject(json, false);
				this.insertTargetBoard(requestTargetBoard);
				return "Legend kopierats";

			}

		} catch (Exception exception) {
			exception.printStackTrace();
			return null;

		}
		return null;
	}

	private RequestTargetBoard prepareJSonObject(String jsonData, boolean updateFlag) {
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
			requestTargetBoard.setTargetBoard(targetBoardVO);

			// target questions info
			JSONArray jsonArray = jsonObject2.getJSONArray("targetQuestions");
			List<TargetQuestionVO> datas = new LinkedList<TargetQuestionVO>();
			for (int i = 0; i < jsonArray.length(); i++) {
				TargetQuestionVO targetQuestions = new TargetQuestionVO();
				JSONObject jsonObject3 = jsonArray.getJSONObject(i);
				String q_name = jsonObject3.get("name").toString();
				targetQuestions.setId(jsonObject3.getInt("id"));
				targetQuestions.setName(q_name);
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

	/****
	 * updateTargetQuestions
	 */
	@Override
	@Transactional
	public boolean updateTargetQuestions(RequestTargetBoard requestTargetBoard) {
		try {
			TargetBoardVO targetBoardVO = requestTargetBoard.getTargetBoard();
			// List<TargetQuestionVO> targetQuestionVOs =
			// requestTargetBoard.getTargetQuestions();
			TargetBoard targetBoard = targetBoardDao.getRowById(targetBoardVO.getId());
			targetBoard.setName(targetBoardVO.getName());
			targetBoard.setDescription(targetBoardVO.getDescription());
			targetBoard.setModified(new Date());
			targetBoardDao.updateRow(targetBoard);
			for (TargetQuestionVO targetQuestionVO : requestTargetBoard.getTargetQuestions()) {
				TargetQuestions targetQuestions = targetQuestionDao.getRowById(targetQuestionVO.getId());
				System.out.println("targetQuestionVO - " + targetQuestionVO.getStatus());
				targetQuestions.setStatus((byte) targetQuestionVO.getStatus());
				targetQuestions.setModified(new Date());
				targetQuestions.setName(targetQuestionVO.getName());
				targetQuestions.setDescription(targetQuestionVO.getDescription());
				targetQuestionDao.updateRow(targetQuestions);
			}
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	@Transactional
	public boolean updateTargetQuestionsByTgtId(RequestTargetBoard requestTargetBoard) {
		try {
			TargetBoardVO targetBoardVO = requestTargetBoard.getTargetBoard();
			TargetBoard targetBoard = targetBoardDao.getRowById(targetBoardVO.getId());
			if (targetBoard == null) {
				return false;
			}
			// targetBoard.setName(targetBoardVO.getName());
			// targetBoard.setDescription(targetBoardVO.getDescription());
			// targetBoard.setStatus(Constants.ACTIVE);
			// targetBoard.setCreated(new Date());
			targetBoard.setModified(new Date());
			// targetBoard.setDeleted(new Date());

			// Set of target board questions
			List<TargetQuestionVO> targetQuestionsVO = requestTargetBoard.getTargetQuestions();
			Set<TargetboardTargetquestions> targetboardTargetquestions = new HashSet<TargetboardTargetquestions>();
			for (TargetQuestionVO targetQuestionVO : targetQuestionsVO) {
				// target board questions
				TargetQuestions targetQuestion = new TargetQuestions();
				targetQuestion.setCreated(new Date());
				targetQuestion.setModified(new Date());
				targetQuestion.setDeleted(new Date());
				targetQuestion.setDescription(targetQuestionVO.getDescription());
				targetQuestion.setName(targetQuestionVO.getName());
				targetQuestion.setStatus((byte) targetQuestionVO.getStatus());
				targetQuestionDao.insertTargetQuestions(targetQuestion);
				// relation of target board and target board questions
				TargetboardTargetquestions targetboardTargetquestions2 = new TargetboardTargetquestions();
				targetboardTargetquestions2.setTargetQuestions(targetQuestion);
				targetboardTargetquestions2.setTargetBoard(targetBoard);
				targetboardTargetquestions2.setCreated(new Date());
				targetboardTargetquestions2.setModified(new Date());
				targetboardTargetquestions2.setDeleted(new Date());

				targetboardTargetquestions.add(targetboardTargetquestions2);
				targetQuestion.setTargetboardTargetquestionses(targetboardTargetquestions);

			}

			targetBoard.setTargetboardTargetquestionses(targetboardTargetquestions);
			targetBoardDao.updateRow(targetBoard);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	@Transactional
	public boolean archiveSelectedTargetBoardByEmpIdAndQtrId(int empId, int qtrId) {
		try {
			List<TargetBoardVO> boardVOs = targetBoardDao.getTargetByEmpIdAndQtrId(empId, qtrId);
			if (boardVOs.size() > 0) {

				for (TargetBoardVO targetBoardVO : boardVOs) {

					TargetBoard targetBoard = targetBoardDao.getRowById(targetBoardVO.getId());
					targetBoard.setState("CLOSE");
					targetBoard.setModified(new Date());
					Set<EmpQuaterTargetboard> empQuaterTargetboards = targetBoard.getEmpQuaterTargetboards();
					for (EmpQuaterTargetboard empQuaterTargetboard : empQuaterTargetboards) {
						empQuaterTargetboard.setState("CLOSE");
					}

					targetBoardDao.updateRow(targetBoard);

				}

			}

			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		// return flag;
	}

	@Override
	@Transactional
	public List<TargetBoardVO> getTargetBdByLnmngrId(int lnId) {
		// TODO Auto-generated method stub
		try {
			EmployeeVO employeeVObj = employeeDao.getEmployeeDetailsById(lnId);
			if (employeeVObj != null) {
				// System.out.println(employeeVOs.getFname());
				List<EmployeeVO> employeeVOs = employeeDao.getEmpByEmpId(employeeVObj.getId(),
						employeeVObj.getDepartmentId(), employeeVObj.getDivisionId(), employeeVObj.getGroupId(), true);
				StringBuffer rString = new StringBuffer();

				int count = 1;
				String sep = ",";
				for (EmployeeVO employeeVO : employeeVOs) {
					if (count > 1) {
						rString.append(sep).append(employeeVO.getId());
					} else {
						rString.append(employeeVO.getId());
					}
					count++;
				}
				if (rString.length() >= 1) {
					System.out.println("comma separated data ::" + rString);

					return (List<TargetBoardVO>) targetBoardDao.getTargetBdByLnmngrId(rString.toString());
				}
			} else {
				System.out.println("else called");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	@Override
	@Transactional
	public List<TargetBoard> getTargetBdDescpAndTgtBdQstnByQtrIdAndEmpId(int empId, int qtrId) {
		// TODO Auto-generated method stub
		return targetBoardDao.getTargetBdDescpAndTgtBdQstnByQtrIdAndEmpId(empId, qtrId);
	}

	@Override
	@Transactional
	public List<TargetBoardVO> getRecentTargetBdByEmpId(int empId) {
		// TODO Auto-generated method stub getRecentQstnPrcntgByLnmngrId
		return targetBoardDao.getRecentTargetBdByEmpId(empId);
	}
	// not working i have to remove....
	/*
	 * @Override
	 * 
	 * @Transactional public List<TargetQuestionVO>
	 * getRecentQstnPrcntgByLnmngrId(int lnmngrId) { // TODO Auto-generated
	 * method stub getRecentQstnPrcntgByLnmngrId return
	 * targetBoardDao.getTargetQstnByEmpId(lnmngrId); }
	 */

}
