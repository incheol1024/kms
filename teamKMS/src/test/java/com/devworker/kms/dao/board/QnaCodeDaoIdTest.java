package com.devworker.kms.dao.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class QnaCodeDaoIdTest {

	static QnaCodeDaoId code1;
	static QnaCodeDaoId code2;
	static QnaCodeDaoId code3;
	
	@Before
	public void setUp() {
		code1 = new QnaCodeDaoId();
		code1.setBoardId(1);
		code1.setMenuId(1);
		
		code2 = new QnaCodeDaoId();
		code2.setBoardId(1);
		code2.setMenuId(1);
		
		code3 = new QnaCodeDaoId();
		code3.setBoardId(2);
		code3.setMenuId(2);
	}
	
	@Test
	public void notNullTest() {
		assertThat(code1).isNotNull();
		assertThat(code2).isNotNull();
		assertThat(code3).isNotNull();
	}
	
	@Test
	public void equalsTest() {
		assertThat(code1.equals(code2)).isEqualTo(true);
		assertThat(code1.equals(code3)).isEqualTo(false);
	}
	
	@Test
	public void hashCodeTest() {
		assertThat(code1.hashCode() == code2.hashCode()).isEqualTo(true);
		assertThat(code1.hashCode() == code3.hashCode()).isEqualTo(false);
	}
	

}
 