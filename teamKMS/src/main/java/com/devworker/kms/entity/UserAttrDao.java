package com.devworker.kms.entity;

import com.devworker.kms.entity.common.DocDao;

import javax.persistence.*;

@Entity
@Table(name = "KMS_USER_ATTR")
public class UserAttrDao {

    @Id
    @Column(name = "id")
    private long userAttrId;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserDao userId;

    @Column(name = "user_avatar")
    private boolean userAvatar;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "doc_id")
    private DocDao docId;

    public UserDao getUserId() {
        return userId;
    }

    public void setUserId(UserDao userId) {
        this.userId = userId;
    }

    public boolean isUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(boolean userAvatar) {
        this.userAvatar = userAvatar;
    }

    public DocDao getDocId() {
        return docId;
    }

    public void setDocId(DocDao docId) {
        this.docId = docId;
    }


}
