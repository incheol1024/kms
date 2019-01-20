package com.devworker.kms.dto.base;

import java.util.ArrayList;
import java.util.List;

public class BasePageResDto<T> {
    private long total = 0;
    private List<T> list = new ArrayList();

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
