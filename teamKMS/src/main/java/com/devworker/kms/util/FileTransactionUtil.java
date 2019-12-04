package com.devworker.kms.util;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import com.devworker.kms.exception.board.FileMemoryNotContainsKeyException;
import com.devworker.kms.exception.board.FileMemoryNotRemovedException;
import com.devworker.kms.exception.board.FileTransactionException;
import com.google.common.collect.HashBasedTable;

/**
 * @author Hwang In Cheol
 * @version 1.0 파일에 대한 트랜잭션을 처리하기 위해 파일 정보를 임시로 저장하기 위한 FileTransactionUtil 클래스
 * 입니다.
 */
public final class FileTransactionUtil {

    /**
     * HashMap field is storing fileData and UUID key which converted to String
     * Object
     */
    private static final ConcurrentHashMap<String, Hashtable<String, List<Long>>> fileData = new ConcurrentHashMap<>();

    /**
     * FileTransactionUtil class do not allow create object, because it is UTIL
     * class
     */
    private FileTransactionUtil() {

    }

    /**
     * @param key    We use java.util.UUID for HashMap key, so parameter should be
     *               converted to String
     * @param docIds value, is considered File Ids
     * @return
     * @author Hwang In Cheol
     */
    public static String putFileInfo(String key, List<Long> docIds) {
        return putFileInfo(key, null, docIds);
    }

    public static String putFileInfo(String key, String userId, List<Long> docIds) {
        Hashtable<String, List<Long>> hashtable = new Hashtable();
        hashtable.put(userId, docIds);
        fileData.put(key, hashtable);
        return key;
    }

    /**
     * @param key We use java.util.UUID for HashMap key, so parameter should be
     *            converted to String
     * @return
     * @author Hwang In Cheol
     */
    public static int getFileCount(String key) {
        return fileData.get(key).get(CommonUtil.getCurrentUser()).size();
    }

    /**
     * @param key We use java.util.UUID for HashMap key, so parameter should
     *            be converted to String
     * @author Hwang In Cheol
     */
    public static List<Long> getFileList(String key) {
        return fileData.get(key).get(CommonUtil.getCurrentUser());
    }

    /**
     * @param key               Comment Id value
     * @param expectedFileCount Excpected file count, it will be checked equivalent
     *                          to actual file count
     * @return If Expected value equals to actual value, then return true, if not
     * return false
     * @throws FileTransactionException
     * @author Hwang In Cheol
     */
    private static boolean isSameTransaction(String key, String userId, int expectedFileCount) throws FileTransactionException {

        if (!fileData.containsKey(key))
            throw new FileTransactionException("File Transact key is not found.");

        if (!fileData.get(key).containsKey(userId))
            throw new FileTransactionException("UserId is not found.");

        return expectedFileCount == getFileCount(key) ? true : false;
    }

    /**
     * @param key               TransactionKey generated when doc(file) is stored
     * @param expectedFileCount Excpected file count, it will be checked equivalent
     *                          to actual file count
     * @return If exist same transactionKey return doc id list, if not
     * throw FileTransactionException
     * @author Hwang In Cheol
     */
    public static List<Long> findSameTransaction(String key, String userId, int expectedFileCount) {

        if (isSameTransaction(key, userId, expectedFileCount))
            return getFileList(key);

        throw new FileTransactionException("File count is not same.");
    }

    /**
     * @param key We use java.util.UUID for HashMap key, so parameter should be
     *            converted to String
     * @throws FileMemoryNotRemovedException
     * @author Hwang In Cheol
     */
    public static void removeFileInfoMemory(String key) throws FileMemoryNotRemovedException {
        if (!fileData.containsKey(key))
            throw new FileMemoryNotContainsKeyException();

        List<Long> returnFileData = fileData.get(key).get(CommonUtil.getCurrentUser());

        if (returnFileData != null) {
            returnFileData.clear();
            fileData.get(key).clear();
            boolean fileIsClear = returnFileData.isEmpty();
            Hashtable hashtable = fileData.remove(key);

            if (!fileIsClear || hashtable == null)
                throw new FileMemoryNotRemovedException("File Data Refs is not clear.");

            returnFileData = null;
            hashtable = null;
            return;
        }
        throw new FileMemoryNotRemovedException();
    }

}
