package com.devworker.kms.dto.base;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class BasePageReqDto<T> {
    private T data;
    private BasePage pageable;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public BasePage getPageable() { return pageable; }

    public void setPageable(BasePage pageable) {
        this.pageable = pageable;
    }

}
