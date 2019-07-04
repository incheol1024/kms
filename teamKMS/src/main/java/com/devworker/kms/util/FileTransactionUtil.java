package com.devworker.kms.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.devworker.kms.exception.board.FileMemoryNotContainsKeyException;
import com.devworker.kms.exception.board.FileMemoryNotRemovedException;
import com.devworker.kms.exception.board.FileTransactionException;

/**
 * @author Hwang In Cheol
 * @version 1.0 파일에 대한 트랜잭션을 처리하기 위해 파일 정보를 임시로 저장하기 위한 FileTransactionUtil 클래스
 *          입니다.
 */
public class FileTransactionUtil {

	/**
	 * HashMap field is storing fileData and UUID key which converted to String
	 * Object
	 */
	private static final Map<String, List<Long>> fileData = new HashMap<String, List<Long>>();

	/**
	 * FileTransactionUtil class do not allow create object, because it is UTIL
	 * class
	 */
	private FileTransactionUtil() {

	}

	/**
	 * @author Hwang In Cheol
	 * @param key   We use java.util.UUID for HashMap key, so parameter should be
	 *              converted to String
	 * @param docId value, is considered File Id
	 * @return
	 */
	public static String putFileInfo(String key, long docId) {

		if (key != null && fileData.containsKey(key)) {
			List<Long> fileList = fileData.get(key);
			fileList.add(docId);
			return key;
		}

		String newKey = UUID.randomUUID().toString();
		List<Long> fileList = new ArrayList<Long>();
		fileList.add(docId);
		fileData.put(newKey, fileList);
		return newKey;
	}

	/**
	 * @author Hwang In Cheol
	 * @param key We use java.util.UUID for HashMap key, so parameter should be
	 *            converted to String
	 * @return
	 */
	public static int getFileCount(String key) {
		return fileData.get(key).size();
	}

	/**
	 * @author Hwang In Cheol
	 * @param key We use java.util.UUID for HashMap key, so parameter should
	 *               be converted to String
	 */
	public static List<Long> getFileList(String key) {
		return fileData.get(key);
	}

	/**
	 * @author Hwang In Cheol
	 * @param key               Comment Id value
	 * @param expectedFileCount Excpected file count, it will be checked equivalent
	 *                          to actual file count
	 * @return If Expected value equals to actual value, then return true, if not
	 *         return false
	 * @throws FileTransactionException
	 */
	private static boolean isSameTransaction(String key, int expectedFileCount) throws FileTransactionException {
		if (!fileData.containsKey(key))
			throw new FileTransactionException("File Transact key is not found.");

		return expectedFileCount == getFileCount(key) ? true : false;
	}

	/**
	 * @author Hwang In Cheol
	 * @param key               TransactionKey generated when doc(file) is stored
	 * @param expectedFileCount Excpected file count, it will be checked equivalent
	 *                          to actual file count
	 * @return If exist same transactionKey return doc id list, if not
	 *         throw FileTransactionException
	 */
	public static List<Long> findSameTransaction(String key, int expectedFileCount) {

		if(isSameTransaction(key, expectedFileCount))
			return getFileList(key);

		throw new FileTransactionException("File count is not same.");
	}

	/**
	 * @author Hwang In Cheol
	 * @param key We use java.util.UUID for HashMap key, so parameter should be
	 *            converted to String
	 * @throws FileMemoryNotRemovedException
	 */
	public static void removeFileInfoMemory(String key) throws FileMemoryNotRemovedException {
		if (!fileData.containsKey(key))
			throw new FileMemoryNotContainsKeyException();

		List<Long> returnFileData = fileData.remove(key);
		if (returnFileData != null) {
			returnFileData.clear();
			boolean isClear = returnFileData.isEmpty();

			if (!isClear)
				throw new FileMemoryNotRemovedException("File Data Refs is not clear.");

			returnFileData = null;
			return;
		}
		throw new FileMemoryNotRemovedException();
	}

}
