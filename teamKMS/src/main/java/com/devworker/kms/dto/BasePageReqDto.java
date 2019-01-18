package com.devworker.kms.dto;

import org.springframework.data.domain.Pageable;

public class BasePageReqDto<T> {
    T data;
    Pageable pageable;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Pageable getPageable() {
        return pageable;
    }

    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }
}
