package com.devworker.kms.dto.common;

import java.io.File;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.devworker.kms.entity.common.CommentDao;
import com.devworker.kms.entity.common.DocDao;

public class CommentDto {

    private long boardId;

    private long cmtId;

    private String cmtContents;

    private String cmtCode;

    private String cmtUserId;

    private LocalDateTime cmtDate;

    private long cmtLike;

//    private Map<Long, String> docEntry; //docId, docName

    private List<DocDto> docDtos;


    private String fileTransactKey;

    private int fileCount;

    public CommentDto() {
    }

    public CommentDto(CommentDao comment) {
        setUpCommentDto(comment);

    }

/*
    private void setUpDocs(List<DocDao> docDaos) {
        for (DocDao docDao : docDaos) {
            docEntry.put(docDao.getDocId(), docDao.getDocName());
        }
    }
*/

    private void setUpCommentDto(CommentDao comment) {
        this.cmtId = comment.getCmtId();
        this.cmtContents = comment.getCmtContents();
        this.cmtCode = comment.getCmtCode();
        this.cmtUserId = comment.getCmtUserId();
        this.cmtDate = comment.getCmtDate();
        this.cmtLike = comment.getCmtLike();
    }

    private String bringDocName(String docPath) {
        int lastIndex = docPath.lastIndexOf(File.separator);

        if (lastIndex == -1)
            return docPath;
        return docPath.substring(lastIndex + 1);
    }

    public long getBoardId() {
        return boardId;
    }

    public void setBoardId(long boardId) {
        this.boardId = boardId;
    }

    public long getCmtId() {
        return cmtId;
    }

    public void setCmtId(long cmtId) {
        this.cmtId = cmtId;
    }

    public String getCmtContents() {
        return cmtContents;
    }

    public void setCmtContents(String cmtContents) {
        this.cmtContents = cmtContents;
    }

    public String getCmtCode() {
        return cmtCode;
    }

    public void setCmtCode(String cmtCode) {
        this.cmtCode = cmtCode;
    }

    public String getCmtUserId() {
        return cmtUserId;
    }

    public void setCmtUserId(String cmtUserId) {
        this.cmtUserId = cmtUserId;
    }

    public LocalDateTime getCmtDate() {
        return cmtDate;
    }

    public void setCmtDate(LocalDateTime cmtDate) {
        this.cmtDate = cmtDate;
    }

    public long getCmtLike() {
        return cmtLike;
    }

    public void setCmtLike(long cmtLike) {
        this.cmtLike = cmtLike;
    }

    public String getFileTransactKey() {
        return fileTransactKey;
    }

    public void setFileTransactKey(String fileTransactKey) {
        this.fileTransactKey = fileTransactKey;
    }

    public int getFileCount() {
        return fileCount;
    }

    public void setFileCount(int fileCount) {
        this.fileCount = fileCount;
    }

    public List<DocDto> getDocDtos() {
        return docDtos;
    }

    public void setDocDtos(List<DocDto> docDtos) {
        this.docDtos = docDtos;
    }

    public CommentDto addDoc(DocDto docDto) {
        docDtos.add(docDto);
        return this;
    }

    public boolean addDoc() {


        return false;
    }


    @Override
    public String toString() {
        return "CommentDto{" +
                "boardId=" + boardId +
                ", cmtId=" + cmtId +
                ", cmtContents='" + cmtContents + '\'' +
                ", cmtCode='" + cmtCode + '\'' +
                ", cmtUserId='" + cmtUserId + '\'' +
                ", cmtDate=" + cmtDate +
                ", cmtLike=" + cmtLike +
                ", fileTransactKey='" + fileTransactKey + '\'' +
                ", fileCount=" + fileCount +
                '}';
    }


}
