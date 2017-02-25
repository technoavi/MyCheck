package com.syntaxtree.agproengg.dao;

import com.syntaxtree.agproengg.model.UploadDataVO;
import com.syntaxtree.agproengg.model.UploadedData;

public interface UploadDataDao {
	
	public boolean saveDatas(UploadedData data);
	public boolean saveData(UploadDataVO data);// 2nd testing method
}
