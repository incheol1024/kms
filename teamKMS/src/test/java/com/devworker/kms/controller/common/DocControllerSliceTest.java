package com.devworker.kms.controller.common;

import com.devworker.kms.component.DocComponent;
import org.apache.http.entity.ContentType;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@WebMvcTest(controllers = DocController.class, useDefaultFilters = false, includeFilters = {@ComponentScan.Filter(DocController.class)})
@RunWith(SpringRunner.class)
public class DocControllerSliceTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    DocComponent docComponent;

    private static MockMultipartFile mockMultipartFile;

    @Before
    public void setUp() throws IOException {
        byte[] fileBytes = Files.readAllBytes(Paths.get("D:", "app/upload", "school.jpg" ));
        mockMultipartFile = new MockMultipartFile("multiPartFile", "school.jpg", ContentType.IMAGE_PNG.toString(), fileBytes );
    }


//    @WithMockUser("USER")
    @Test
    public void notNullTest() {
        Assertions.assertThat(mockMultipartFile).isNotNull();
        Assertions.assertThat(mockMvc).isNotNull();
        Assertions.assertThat(docComponent).isNotNull();
    }

    // /upload/comment
    @Test
    public void uploadFileOnComment() {

    }

    @Test
    public void uploadFileOnEtc() {

    }

    @Test
    public void downloadFile() {

    }

    @Test
    public void listFile() {

    }

    @Test
    public void updateFile() {

    }


}
