package io.bshouse.dfsm.file.service.controller;

import io.bshouse.dfsm.file.service.dto.CreateAttachmentRequestDTO;
import io.bshouse.dfsm.file.service.dto.ResponseDTO;
import io.bshouse.dfsm.file.service.exception.AttachmentNotFoundException;
import io.bshouse.dfsm.file.service.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static io.bshouse.dfsm.file.service.controller.AttachmentController.BASE_URL;

@RestController
@RequestMapping(BASE_URL)
public class AttachmentController {
    public static final String BASE_URL = "/api/v1/file";
    public static final String ENDPOINT_GET_ALL = "";
    public static final String ENDPOINT_CREATE_ATTACHMENT = "";
    public static final String ENDPOINT_DELETE_ATTACHMENT = "/{attachmentId}";
    public static final String ENDPOINT_GET_BY_ELEMENT_ID = "/elements/{elementId}";
    public static final String ENDPOINT_GET_BY_ATTACHMENT_ID = "/{attachmentId}";
    private final AttachmentService attachmentService;

    @Autowired
    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @GetMapping(ENDPOINT_GET_BY_ELEMENT_ID)
    public ResponseEntity<ResponseDTO> getAttachmentById(@PathVariable("elementId") Long elementId) throws AttachmentNotFoundException {
        return new ResponseEntity<>(
                ResponseDTO.builder()
                        .data(this.attachmentService.getByElementId(elementId))
                        .status(true).build(),
                HttpStatus.OK);
    }
    @GetMapping(ENDPOINT_GET_BY_ATTACHMENT_ID)
    public ResponseEntity<ResponseDTO> getAttachmentByAttachmentId(@PathVariable("attachmentId") Long attachmentId) throws AttachmentNotFoundException {
        return new ResponseEntity<>(
                ResponseDTO.builder()
                        .data(this.attachmentService.getByAttachmentId(attachmentId))
                        .status(true).build(),
                HttpStatus.OK);
    }

    @GetMapping(ENDPOINT_GET_ALL)
    public ResponseEntity<ResponseDTO> getAllAttachment(
            @RequestParam(name = "page", required = false) Optional<Integer> page,
            @RequestParam(name = "pagesize", required = false) Optional<Integer> pagesize
    ) {
        return new ResponseEntity<>(this.attachmentService.getAll(page, pagesize),
                HttpStatus.OK);
    }

    @PostMapping(ENDPOINT_CREATE_ATTACHMENT)
    public ResponseEntity<ResponseDTO> createAttachment (@Valid @ModelAttribute CreateAttachmentRequestDTO createAttachmentRequestDTO) {
        this.attachmentService.createAttachment(createAttachmentRequestDTO);
        return new ResponseEntity<>(ResponseDTO.builder().status(true).build(), HttpStatus.OK);
    }

    @DeleteMapping(ENDPOINT_DELETE_ATTACHMENT)
    public ResponseEntity<ResponseDTO> deleteAttachment(@PathVariable("attachmentId") Long attachmentId) {
        this.attachmentService.deleteAttachment(attachmentId);
        return new ResponseEntity<>(ResponseDTO.builder().status(true).build(), HttpStatus.OK);
    }
}
