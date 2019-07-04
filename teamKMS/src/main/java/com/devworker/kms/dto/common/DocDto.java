package com.devworker.kms.dto.common;

import com.devworker.kms.entity.common.BoardDao;
import com.devworker.kms.entity.common.CommentDao;
import com.devworker.kms.entity.common.DocDao;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class DocDto {

    private final long docId;

    private final BoardDao boardId;

    private final CommentDao cmtId;

    private final String docPath;

    private final String docName;

    private final String docExt;

    private final String docUserId;

    private final long docSize;

    private DocDto() {
        throw new AssertionError();
    }

    private DocDto(DocDtoBuilder docDtoBuilder) {

        this.docId = docDtoBuilder.docId;
        this.boardId = docDtoBuilder.boardId;
        this.cmtId = docDtoBuilder.cmtId;
        this.docPath = docDtoBuilder.docPath;
        this.docName = docDtoBuilder.docName;
        this.docExt = docDtoBuilder.docExt;
        this.docUserId = docDtoBuilder.docUserId;
        this.docSize = docDtoBuilder.docSize;
    }

    public long getDocId() {
        return docId;
    }

    public BoardDao getBoardId() {
        return boardId;
    }

    public CommentDao getCmtId() {
        return cmtId;
    }

    public String getDocPath() {
        return docPath;
    }

    public String getDocName() {
        return docName;
    }

    public String getDocExt() {
        return docExt;
    }

    public String getDocUserId() {
        return docUserId;
    }

    public long getDocSize() {
        return docSize;
    }

    public static class DocDtoBuilder {

        private long docId;

        private BoardDao boardId = null;

        private CommentDao cmtId = null;

        private String docPath;

        private String docName;

        private String docExt;

        private String docUserId;

        private long docSize;

        public DocDtoBuilder() {
        }

        public DocDtoBuilder setDocId(long docId) {
            this.docId = docId;
            return this;
        }

        public DocDtoBuilder setBoardId(BoardDao boardId) {
            this.boardId = boardId;
            return this;
        }

        public DocDtoBuilder setCmtId(CommentDao cmtId) {
            this.cmtId = cmtId;
            return this;
        }

        public DocDtoBuilder setDocPath(String docPath) {
            this.docPath = docPath;
            return this;
        }

        public DocDtoBuilder setDocName(String docName) {
            this.docName = docName;
            return this;
        }

        public DocDtoBuilder setDocExt(String docExt) {
            this.docExt = docExt;
            return this;
        }

        public DocDtoBuilder setDocUserId(String docUserId) {
            this.docUserId = docUserId;
            return this;
        }

        public DocDtoBuilder setDocSize(long docSize) {
            this.docSize = docSize;
            return this;
        }

        public DocDtoBuilder docDao(DocDao docDao) {

            return this.setDocId(docDao.getDocId())
                    .setDocName(docDao.getDocName())
                    .setDocExt(docDao.getDocName())
                    .setDocPath(docDao.getDocPath())
                    .setDocSize(docDao.getDocSize())
                    .setDocUserId(docDao.getDocUserId());
//                    .setBoardId(docDao.getBoardId())
//                    .setCmtId(docDao.getCmtId());
        }

        public DocDto build() {

            assertValue(this);
            return new DocDto(this);

        }


        private <T> void assertValue(T t) {

            String assertionMessage = "";

            if (!(t instanceof DocDtoBuilder))
                assertionMessage = "";

            DocDtoBuilder docDtoBuilder = (DocDtoBuilder) t;

            if (isNull(docDtoBuilder.boardId) && isNull(docDtoBuilder.cmtId))
                assertionMessage = "Either boardId or cmtId is must not be null.";

            if (docDtoBuilder.docId <= 0)
                assertionMessage = "docId must be larger than zero.";

            if (docDtoBuilder.docName.isEmpty())
                assertionMessage = "docName must not be null or empty string.";

            if (assertionMessage.isEmpty())
                return;

            throw new IllegalArgumentException(assertionMessage);
        }


    }

}
