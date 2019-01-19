package com.devworker.kms.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.devworker.kms.exception.FileMemoryNotRemovedException;

/**
 * @author Hwang In Cheol
 * @version 1.0 파일에 대한 트랜잭션을 처리하기 위해 파일 정보를 임시로 저장하기 위한 FileTransactionUtil 클래스
 *          입니다.
 */
public class FileTransactionUtil {

	private static Map<Integer, List<Integer>> fileData = new HashMap<Integer, List<Integer>>();

	/**
	 * @author Hwang In Cheol
	 * @param Comment Id value
	 * @param Doc     Id value, is considered File Id
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
	 * @param Comment   Id value
	 * @param Excpected file count, it will be checked equivalent to actual file
	 *                  count
	 * @return If Expected value equals to actual value, then return true, if not
	 *         return false
	 */
	public static boolean isSameTransaction(int cmtId, int expectedFileCount) {
		return expectedFileCount == getFileCount(cmtId) ? true : false;
	}

	/**
	 * @author Hwang In Cheol
	 * @param Comment Id value
	 * @return 
	 * @throws Exception 
	 */
	public static boolean removeFileInfoMemory(int cmtId) throws Exception {
		List<Integer> returnFileData = fileData.remove(cmtId);
		if (returnFileData != null) {
			returnFileData = null;
			return true;
		}
		throw new FileMemoryNotRemovedException();
		//return false;
	}

}
