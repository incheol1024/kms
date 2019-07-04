package com.devworker.kms.entity.common;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.devworker.kms.util.CommonUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;

import com.devworker.kms.dto.common.CommentDto;
import com.devworker.kms.repo.common.BoardRepo;
import org.springframework.data.annotation.CreatedDate;

import static java.time.Instant.ofEpochMilli;
import static java.time.LocalDateTime.ofInstant;
import static java.time.ZoneId.systemDefault;
import static java.util.Objects.isNull;

@Entity
@Table(name = "KMS_COMMENT")
public class CommentDao {

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "board_id", nullable = false)
    private BoardDao boardId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cmt_id")
    private long cmtId;

    @Column(name = "cmt_contents")
    private String cmtContents;

    @Column(name = "cmt_code")
    private String cmtCode;

    @Column(name = "cmt_user_id")
    private String cmtUserId;

    @Column(name = "cmt_date")
    @Temporal(value = TemporalType.TIMESTAMP)
    @CreatedDate
    private Date cmtDate;
    //private LocalDateTime cmtDate;

    @Column(name = "cmt_like")
    private long cmtLike;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "KMS_COMMENT_DOC",
            joinColumns = @JoinColumn(name = "cmt_id"),
            inverseJoinColumns = @JoinColumn(name = "doc_id"))
    private List<DocDao> docDaos = new ArrayList<>();

    public CommentDao() {
        //this.cmtDate = LocalDateTime.now();
    }

    public CommentDao(BoardDao boardId, String cmtContents) {
        this.boardId = boardId;
        this.cmtContents = cmtContents;
    }

    public BoardDao getBoardId() {
        return boardId;
    }

    public void setBoardId(BoardDao boardId) {
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
        return getLocalDateTimeFrom(cmtDate);
    }

    public void setCmtDate(@NotNull Date cmtDate) {
        this.cmtDate = cmtDate;
    }

    public long getCmtLike() {
        return cmtLike;
    }

    public void setCmtLike(long cmtLike) {
        this.cmtLike += cmtLike;
    }

    public List<DocDao> getDocDaos() {
        return docDaos;
    }

    public void setDocDaos(List<DocDao> docDaos) {
        this.docDaos = docDaos;
    }

    public void addDoc(DocDao docDao) {

        if(!docDaos.contains(docDao))
            docDaos.add(docDao);
    }
    private LocalDateTime getLocalDateTimeFrom(Date date) {
        return isNull(date) ? null : ofInstant(ofEpochMilli(date.getTime()), systemDefault());
    }

    private Date getDateFrom(LocalDateTime localDateTime) {
        return isNull(localDateTime) ? null : Date.from(localDateTime.atZone(systemDefault()).toInstant());
    }

    @Override
    public String toString() {
        return CommonUtil.toJson(this);
    }

    public void setUpEntity(CommentDto commentDto) {

        if (!isNotEmpty(commentDto))
            throw new RuntimeException();

        if (isNotEmpty(commentDto.getBoardId()))
            this.boardId = new BoardDao(commentDto.getBoardId());

        if (isNotEmpty(commentDto.getCmtContents()))
            this.cmtContents = commentDto.getCmtContents();

        if (isNotEmpty(commentDto.getCmtCode()))
            this.cmtCode = commentDto.getCmtCode();

        if (isNotEmpty(commentDto.getCmtDate()))
            this.cmtDate = getDateFrom(commentDto.getCmtDate());

        if (isNotEmpty(commentDto.getCmtId()))
            this.cmtId = commentDto.getCmtId();

        if (isNotEmpty(commentDto.getCmtLike()))
            this.cmtLike = commentDto.getCmtLike();

        if (isNotEmpty(commentDto.getCmtUserId()))
            this.cmtUserId = commentDto.getCmtUserId();
    }

    public <T> boolean isNotEmpty(T type) {
        Optional<T> optional = Optional.ofNullable(type);
        return optional.isPresent();
    }

}
