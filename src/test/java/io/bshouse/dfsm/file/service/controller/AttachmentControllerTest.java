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
import io.bshouse.dfsm.file.service.service.external.file.FileExternalService;
import io.bshouse.dfsm.file.service.service.external.file.dto.FileResponseDTO;
import io.bshouse.dfsm.file.service.service.external.token.TokenOnOffOnBoardingService;
import io.bshouse.dfsm.file.service.service.external.token.dto.TokenResponseDTO;
import io.bshouse.dfsm.file.service.util.FileMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.token.TokenService;
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
    @MockBean
    TokenOnOffOnBoardingService tokenOnOffOnBoardingService;
    @MockBean
    FileExternalService fileExternalService;

    @BeforeAll
    void setup() {

        attachmentRepository.save(Attachment.builder()
                .mimeType("application/rtf")
                .creationDate(new Date())
                .fileSize(19000L)
                .createdByUserId(1L)
                .originalName("file1")
                .urlSource("http://local")
                .typeFile(TypeFile.MAIL_SHOT)
                .build());
        attachmentRepository.save(Attachment.builder()
                .mimeType("application/rtf")
                .creationDate(new Date())
                .fileSize(190001L)
                .createdByUserId(2L)
                .urlSource("http://local")
                .originalName("file2")
                .typeFile(TypeFile.NOTIFICATIONS)
                .build());

        when(authService.validateToken(anyString())).thenReturn(ResponseVerifyTokenDTO.builder().status(true).build());
    }

    @Test
    void givenAnAttachmentToCreateBodyRequestWhenBOdyIsValidThenItShouldReturnOk() throws Exception {
        String name = "test1.pdf";
        String body = this.fileMapper.loadTestJsonToString("create/createAttachmentRequestDTO.json").replace("{{name}}", name);

        TokenResponseDTO tokenResponseDTO = (TokenResponseDTO) this.fileMapper.loadTestJson("token/tokenResponseDTO.json", TokenResponseDTO.class);
        when(tokenOnOffOnBoardingService.getToken(anyString(), any())).thenReturn(tokenResponseDTO);
        FileResponseDTO fileResponseDTO = (FileResponseDTO) this.fileMapper.loadTestJson("file/fileResponseDTO.json", FileResponseDTO.class);
        when(fileExternalService.add(anyString(), any(), anyString(), anyString())).thenReturn(fileResponseDTO);
        String response = mvc.perform(post(AttachmentController.BASE_URL + AttachmentController.ENDPOINT_CREATE_ATTACHMENT)
                        .contentType("multipart/form-data")
                        .param("originalName", name)
                        .param("mimeType","application/pdf")
                        .param("elementId", String.valueOf(1))
                        .param("typeFile", "MAIL_SHOT")
                        .param("fileSize", "12121212")
                )
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        ResponseDTO responseDTO = objectMapper.readValue(response, ResponseDTO.class);
        assertTrue(responseDTO.isStatus());
        Attachment attachment = attachmentRepository.findByOriginalName(name).orElse(null);
        assertNotNull(attachment);
        assertEquals("application/pdf", attachment.getMimeType());
        assertEquals(TypeFile.MAIL_SHOT, attachment.getTypeFile());
        assertEquals(1L, attachment.getCreatedByUserId());
        assertEquals(12121212, attachment.getFileSize());
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

    @Test
    void givenAnRequestWhenCallToGetmeAttachmentThenItShouldReturnOk() throws Exception {
        String response = mvc.perform(get((AttachmentController.BASE_URL + AttachmentController.ENDPOINT_GET_BY_ATTACHMENT_ID.replace("{attachmentId}","1")))
                        .contentType("application/json"))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        ResponseDTO responseDTO = objectMapper.readValue(response, ResponseDTO.class);
        assertTrue(responseDTO.isStatus());
        assertNotNull(responseDTO.getData());
        byte[] json = objectMapper.writeValueAsBytes(responseDTO.getData());
        TypeReference<AttachmentResponseDTO> typeRef = new TypeReference<>() {
        };
        AttachmentResponseDTO attachmentResponseDTO = objectMapper.readValue(json, typeRef);
        assertEquals("application/rtf", attachmentResponseDTO.getMimeType());
        assertEquals("file1", attachmentResponseDTO.getOriginalName());
        assertEquals("http://local", attachmentResponseDTO.getUrlSource());
        assertEquals(TypeFile.MAIL_SHOT, attachmentResponseDTO.getTypeFile());
        assertEquals(19000, attachmentResponseDTO.getFileSize());
    }
}
