package io.bshouse.dfsm.file.service.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.bshouse.dfsm.file.service.DfsmFileServiceApplicationTests;
import io.bshouse.dfsm.file.service.dto.AttachmentResponseDTO;
import io.bshouse.dfsm.file.service.dto.ResponseDTO;
import io.bshouse.dfsm.file.service.model.Attachment;
import io.bshouse.dfsm.file.service.model.TypeFile;
import io.bshouse.dfsm.file.service.repository.AttachmentRepository;
import io.bshouse.dfsm.file.service.service.auth.AuthService;
import io.bshouse.dfsm.file.service.service.auth.dto.ResponseVerifyTokenDTO;
import io.bshouse.dfsm.file.service.util.FileMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(PER_CLASS)
@Transactional
@WithMockUser("1")
@ContextConfiguration
@ExtendWith(SpringExtension.class)
class AttachmentControllerTest extends DfsmFileServiceApplicationTests {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private MockMvc mvc;
    @MockBean
    AuthService authService;
    @Autowired
    AttachmentRepository attachmentRepository;

    @BeforeAll
    void setup() {

        attachmentRepository.save(Attachment.builder()
                .mimeType("application/rtf")
                .creationDate(new Date())
                .createdByUserId(1L)
                .originalName("file1")
                .typeFile(TypeFile.MAIL_SHOT)
                .build());
        attachmentRepository.save(Attachment.builder()
                .mimeType("application/rtf")
                .creationDate(new Date())
                .createdByUserId(2L)
                .originalName("file2")
                .typeFile(TypeFile.NOTIFICATIONS)
                .build());

        when(authService.validateToken(anyString())).thenReturn(ResponseVerifyTokenDTO.builder().status(true).build());
    }

    @Test
    void givenAnAttachmentToCreateBodyRequestWhenBOdyIsValidThenItShouldReturnOk() throws Exception {
        String name = "test1.pdf";
        String body = this.fileMapper.loadTestJsonToString("create/createAttachmentRequestDTO.json").replace("{{name}}", name);
        String response = mvc.perform(post(AttachmentController.BASE_URL + AttachmentController.ENDPOINT_CREATE_ATTACHMENT)
                        .contentType("application/json")
                        .content(body))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        ResponseDTO responseDTO = objectMapper.readValue(response, ResponseDTO.class);
        assertTrue(responseDTO.isStatus());
        Attachment attachment = attachmentRepository.findByOriginalName(name).orElse(null);
        assertNotNull(attachment);
        assertEquals("application/pdf", attachment.getMimeType());
        assertEquals(TypeFile.MAIL_SHOT, attachment.getTypeFile());
        assertEquals(1L, attachment.getCreatedByUserId());
    }

    @Test
    void givenAnAttachmentIdWhenCallDeleteRequestThenItShouldReturnOk() throws Exception {
        Long attachmentId = 1L;
        String response = mvc.perform(delete((AttachmentController.BASE_URL + AttachmentController.ENDPOINT_DELETE_ATTACHMENT.replace("{attachmentId}", attachmentId.toString())))
                        .contentType("application/json"))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        ResponseDTO responseDTO = objectMapper.readValue(response, ResponseDTO.class);
        assertTrue(responseDTO.isStatus());
        Attachment attachment = attachmentRepository.findById(1L).orElse(null);
        assertNull(attachment);
    }

    @Test
    void givenAnRequestWhenCallToGetAllAttachmentThenItShouldReturnOk() throws Exception {
        String response = mvc.perform(get((AttachmentController.BASE_URL + AttachmentController.ENDPOINT_GET_ALL))
                        .contentType("application/json"))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        ResponseDTO responseDTO = objectMapper.readValue(response, ResponseDTO.class);
        assertTrue(responseDTO.isStatus());
        assertNotNull(responseDTO.getData());
        assertNotNull(responseDTO.getPage());
        assertNotNull(responseDTO.getTotalPages());
        assertNotNull(responseDTO.getTotalElements());
        byte[] json = objectMapper.writeValueAsBytes(responseDTO.getData());
        TypeReference<ArrayList<AttachmentResponseDTO>> typeRef = new TypeReference<>() {
        };
        List<AttachmentResponseDTO> attachmentResponseDTOList = objectMapper.readValue(json, typeRef);
        assertTrue(attachmentResponseDTOList.size() > 0);
    }
}
