package com.devworker.kms.dto.common;

import lombok.*;

import java.io.File;


@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class FileDto {

    private final File file;

    private final String key;

    private final long fileSize;

    private final String fileName;

    private final String fileExt;

    private final String contentType;

}
