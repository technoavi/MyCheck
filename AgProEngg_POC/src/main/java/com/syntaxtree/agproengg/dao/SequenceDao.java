package com.syntaxtree.agproengg.dao;

import com.syntaxtree.agproengg.exception.SequenceException;

public interface SequenceDao {

	long getNextSequenceId(String key) throws SequenceException;

}