package com.devworker.kms.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Hwang In Cheol
 * @version 1.0 파일에 대한 트랜잭션을 처리하기 위해 파일 정보를 임시로 저장하기 위한 FileTransactionUtil 클래스
 *          입니다.
 */
public class FileTransactionUtil {

	private static Map<Integer, List<Integer>> fileData = new HashMap<Integer, List<Integer>>();

	/**
	 * @author Hwang In Cheol
	 * @param  Comment Id value
	 * @param Doc Id value, is considered File Id
	*/
	public static void putFileInfo(int cmtId, int docId) {
		List<Integer> oriFileList = null;

		if ((oriFileList = fileData.get(cmtId)) != null) {
			oriFileList.add(docId);
			return;
		}

		List<Integer> fileList = new ArrayList<Integer>();
		fileList.add(docId);
		fileData.put(cmtId, fileList);
		
	}

	/**
	 * @author Hwang In Cheol
	 * @param Comment Id value
	*/
	public static int getFileCount(int cmtId) {
		return fileData.get(cmtId).size();
	}

	/**
	 * @author Hwang In Cheol
	 * @param Comment Id value
	 * @param Excpected file count, it will be checked equivalent to actual file count 
	*/
	public static boolean isSameTransaction(int cmtId, int expectedFileCount) {
		return expectedFileCount == getFileCount(cmtId) ? true : false;
	}

}
