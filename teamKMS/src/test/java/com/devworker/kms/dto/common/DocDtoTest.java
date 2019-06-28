package com.devworker.kms.dto.common;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Objects;

import static com.devworker.kms.dto.common.DocDto.*;

public class DocDtoTest {

    @Test(expected = IllegalArgumentException.class)
    public void DocDtoBuilderAssertionTest() {
        DocDto docDto =
                new DocDtoBuilder()
                        .setDocName("aa")
                        .setDocId(1)
                        .build();
    }

    @Test
    public void isNullTest() {

        Object object = new Object();
        Objects.nonNull(object);
        Assertions.assertThat(Objects.nonNull(object)).isTrue();
    }
}
