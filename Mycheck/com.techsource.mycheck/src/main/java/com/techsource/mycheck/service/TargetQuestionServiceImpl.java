package com.techsource.mycheck.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techsource.mycheck.dao.EmployeeDao;
import com.techsource.mycheck.dao.TargetQuestionDao;
import com.techsource.mycheck.domain.TargetBoard;
import com.techsource.mycheck.domain.TargetQuestions;
import com.techsource.mycheck.vo.EmployeeVO;
import com.techsource.mycheck.vo.TargetBoardVO;
import com.techsource.mycheck.vo.TargetQuestionVO;

@Service("targetQuestionService")
public class TargetQuestionServiceImpl implements TargetQuestionService {
	private final Logger logger = LoggerFactory.getLogger(TargetQuestionServiceImpl.class);

	@Autowired
	TargetQuestionDao targetQuestionDao;

	@Autowired
	private EmployeeDao employeeDao;

	@Override
	@Transactional
	public boolean insertTargetQuestion(TargetQuestions targetQuestion) {
		if (targetQuestion != null) {
			logger.info("insertTargetQuestion dao called");
			boolean flag = targetQuestionDao.insertTargetQuestions(targetQuestion);
			if (flag) {

				System.out.println("inserted into db " + flag);
				return true;
			}
		}
		return false;
	}

	@Override
	public List<TargetQuestionVO> getList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TargetQuestionVO> getListByTargetBoardId(String targetBoardId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TargetQuestionVO getRowById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateRow(TargetQuestionVO targetQuestion) {
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
	public List<TargetQuestionVO> getTargetQstnbyEmpId(int id) {
		// TODO Auto-generated method stub
		return (List<TargetQuestionVO>) targetQuestionDao.getTargetQstnbyEmpId(id);
	}

	@Override
	@Transactional
	public List<TargetQuestionVO> getTargetQstnbyTargetId(int id) {
		// TODO Auto-generated method stub
		return (List<TargetQuestionVO>) targetQuestionDao.getTargetQstnbyTargetId(id);
	}

	@Override
	@Transactional
	public List<TargetQuestionVO> getTargetQstnByQtrId(int id) {
		// TODO Auto-generated method stub
		return (List<TargetQuestionVO>) targetQuestionDao.getTargetQstnByQtrId(id);
	}

	@Override
	@Transactional
	public List<TargetQuestionVO> getquestionsByTgtIdId(int tgtBdId) {
		// TODO Auto-generated method stub
		return (List<TargetQuestionVO>) targetQuestionDao.getquestionsByTgtIdId(tgtBdId);
	}

	@Override
	@Transactional
	public boolean updateTargetQuestion(TargetQuestions targetQuestion) {
		logger.info("updateTargetQuestion service  called");
		if (targetQuestion != null) {
			/*
			 * TargetQuestionVO targetQuestionVO= new TargetQuestionVO();
			 * targetQuestionVO.setId(targetQuestion.getId());
			 * targetQuestionVO.setName(targetQuestion.getName());
			 * targetQuestionVO.setDescription(targetQuestion.getDescription());
			 * 
			 * 
			 * targetQuestionVO.setStatus(targetQuestion.getStatus());
			 * targetQuestionVO.setCreated(new Date());
			 * targetQuestionVO.setModified(new Date());
			 * targetQuestionVO.setDeleted(new Date());
			 */
			boolean flag = targetQuestionDao.updateTargetQuestions(targetQuestion);
			if (flag) {

				System.out.println("inserted into db " + flag);
				return true;
			}
		}
		return false;
	}

	@Override
	@Transactional
	public boolean checkQstnIdExists(int qstId) {
		logger.info("updateTargetQuestion service  called");

		boolean flag = targetQuestionDao.checkQstnIdExists(qstId);
		if (flag) {

			System.out.println("inserted into db " + flag);
			return true;

		}
		return false;
	}

	@Override
	@Transactional
	public List<TargetQuestions> addTargetquestions(TargetQuestionVO targetQuestion) {
		TargetQuestions targetQuestions = new TargetQuestions();
		targetQuestions.setId(targetQuestion.getId());
		targetQuestions.setName(targetQuestion.getName());
		targetQuestions.setDescription(targetQuestion.getDescription());

		targetQuestions.setStatus((byte) targetQuestion.getStatus());
		boolean flag = targetQuestionDao.insertTargetQuestions(targetQuestions);
		/*
		 * targetQuestionVO.setCreated(new Date());
		 * targetQuestionVO.setModified(new Date());
		 * targetQuestionVO.setDeleted(new Date());
		 */
		return null;
	}

	@Override
	@Transactional
	public boolean deleteQuestionById(int qstnId) {

		return targetQuestionDao.deleteQuestionById(qstnId);
	}
	//for employee..
	@Override
	@Transactional
	public List<TargetQuestionVO> getTargetQstnByEmpIdAndQtrId(String lnId, String qtrId,boolean flag) {
		// TODO Auto-generated method stub
		try {
			

					return (List<TargetQuestionVO>) targetQuestionDao.getTargetQstnByEmpIdAndQtrId(Integer.parseInt(lnId),Integer.parseInt(qtrId));
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	//for line manager..
	@Override
	@Transactional
	public List<TargetQuestionVO> getTargetQstnByLnmngrId(String lnId,boolean flag) {
		// TODO Auto-generated method stub
		try {
			EmployeeVO employeeVObj = employeeDao.getEmployeeDetailsById(Integer.parseInt(lnId));
			if (employeeVObj != null) {
				// System.out.println(employeeVOs.getFname());
				List<EmployeeVO> employeeVOs = employeeDao.getEmpByEmpId(employeeVObj.getId(),
						employeeVObj.getDepartmentId(), employeeVObj.getDivisionId(), employeeVObj.getGroupId(), flag);
				StringBuilder rString = new StringBuilder();
				for (int i = 0; i < employeeVOs.size(); i++) {
					
					System.out.println("emp id "+employeeVOs.get(i).getId());
				}
				int count = 1;
				for (EmployeeVO employeeVO : employeeVOs) {
					String sep = ",";
					if (count > 1) {
						rString.append(sep).append(employeeVO.getId());
					} else {
						rString.append(employeeVO.getId());
					}
					count++;
				}
				if (rString.length() >= 1) {
					System.out.println("comma separated data ::" + rString);

					return (List<TargetQuestionVO>) targetQuestionDao.getTargetQstnByLnmngrId(rString.toString());
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
	public boolean updateTargetQstnStatus(int qstnId, String status) {
		try {

			Boolean flag = Boolean.parseBoolean(status);
			int i = 0;
			if (flag) {
				i = 1;
			}
			return targetQuestionDao.updateStatus(qstnId, (byte) i);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

}
