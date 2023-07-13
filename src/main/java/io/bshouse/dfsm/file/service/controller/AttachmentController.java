package io.bshouse.dfsm.file.service.controller;
import io.bshouse.dfsm.file.service.dto.ResponseDTO;
import io.bshouse.dfsm.file.service.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static io.bshouse.dfsm.file.service.controller.AttachmentController.BASE_URL;

@RestController
@RequestMapping(BASE_URL)
public class AttachmentController {
    public static final String BASE_URL = "/api/v1/file";
    public static final String ENDPOINT_GET_ALL = "";
    public static final String ENDPOINT_GET_BY_ID = "/{elementId}";
    private final AttachmentService attachmentService;

    @Autowired
    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @GetMapping(ENDPOINT_GET_BY_ID)
    public ResponseEntity<ResponseDTO> getAttachmentById(@PathVariable("elementId") Long elementId) {
        return new ResponseEntity<>(
                ResponseDTO.builder()
                        .data(this.attachmentService.getByElementId(elementId))
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

}
