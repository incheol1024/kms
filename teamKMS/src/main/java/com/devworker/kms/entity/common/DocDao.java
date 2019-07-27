package com.devworker.kms.entity.common;

import javax.persistence.*;

import com.devworker.kms.dto.common.FileDto;
import com.devworker.kms.util.CommonUtil;

@Entity
@Table(name = "KMS_DOC")
public class DocDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doc_id")
    private long docId;

    @Column(name = "doc_path")
    private String docPath;

    @Column(name = "doc_name")
    private String docName;

    @Column(name = "doc_ext")
    private String docExt;

    @Column(name = "doc_user_id")
    private String docUserId;

    @Column(name = "doc_size")
    private long docSize;


    public DocDao() {
    }

    public DocDao(Long docId) {
        this.docId = docId;
    }

    public long getDocId() {
        return docId;
    }

    public void setDocId(long docId) {
        this.docId = docId;
    }

    public String getDocPath() {
        return docPath;
    }

    public void setDocPath(String docPath) {
        this.docPath = docPath;
    }

    public String getDocUserId() {
        return docUserId;
    }

    public void setDocUserId(String docUserId) {
        this.docUserId = docUserId;
    }

    public long getDocSize() {
        return docSize;
    }

    public void setDocSize(long docSize) {
        this.docSize = docSize;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocExt() {
        return docExt;
    }

    public void setDocExt(String docExt) {
        this.docExt = docExt;
    }

    @Override
    public String toString() {
        return CommonUtil.toJson(this);
    }

    public void setUpEntity(FileDto fileDto) {
        this.docExt = fileDto.getFileExt();
        this.docName = fileDto.getFileName();
        this.docPath = fileDto.getKey();
        this.docSize = fileDto.getFileSize();
    }

    public static DocDao valueOf(FileDto fileDto) {
        DocDao docDao = new DocDao();
        docDao.setUpEntity(fileDto);
        return docDao;
    }

}
