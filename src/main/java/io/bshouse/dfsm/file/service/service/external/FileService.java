package io.bshouse.dfsm.file.service.service.external;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.bshouse.dfsm.file.service.dto.CreateAttachmentRequestDTO;
import io.bshouse.dfsm.file.service.service.external.file.FileExternalService;
import io.bshouse.dfsm.file.service.service.external.file.dto.FileResponseDTO;
import io.bshouse.dfsm.file.service.service.external.token.TokenOnOffOnBoardingService;
import io.bshouse.dfsm.file.service.service.external.token.dto.TokenResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import static io.bshouse.dfsm.file.service.constants.GeneralConstants.FILE_STORAGE_CONTAINER;
import static io.bshouse.dfsm.file.service.constants.GeneralConstants.FILE_STORAGE_PATH;
import static io.bshouse.dfsm.file.service.service.external.TokeService.BEARER_AUTHENTICATION;

@Service
public class FileService {
    private final Logger logger = LoggerFactory.getLogger(FileService.class);
    private final FileExternalService fileExternalService;
    private final TokeService tokeService;

    public FileService(FileExternalService fileExternalService, TokeService tokeService) {
        this.fileExternalService = fileExternalService;
        this.tokeService = tokeService;
    }

    public FileResponseDTO createFile(MultipartFile file) {
        TokenResponseDTO tokenResponseDTO = this.tokeService.getToken();
        String authentication = BEARER_AUTHENTICATION + tokenResponseDTO.getAccesToken();
        logger.info("Calling File external service ");
        FileResponseDTO fileResponseDTO =  fileExternalService.add(authentication, file, FILE_STORAGE_CONTAINER, FILE_STORAGE_PATH);
        logger.info("Response File External service  " + fileResponseDTO);
        return fileResponseDTO;
    }
}
