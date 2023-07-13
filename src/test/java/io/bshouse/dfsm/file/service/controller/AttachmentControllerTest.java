package io.bshouse.dfsm.file.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.bshouse.dfsm.file.service.DfsmFileServiceApplicationTests;
import io.bshouse.dfsm.file.service.util.FileMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

class AttachmentControllerTest extends DfsmFileServiceApplicationTests {
    public static final String endpoint = "";
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private MockMvc mvc;
    @BeforeEach
    void init() {

    }
}
