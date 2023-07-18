package io.bshouse.dfsm.file.service.service.external.file;
import feign.Headers;
import io.bshouse.dfsm.file.service.config.FeignConfiguration;
import io.bshouse.dfsm.file.service.service.external.file.dto.FileResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@FeignClient(name="file.service", url="${file.url.base}", configuration = FeignConfiguration.class)
public interface FileExternalService {
    @PostMapping(path = "/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    FileResponseDTO add(
                        @RequestHeader("Authorization") String token ,
                        @RequestPart("file") MultipartFile file,
                        @RequestPart("contenedor") String contenedor,
                        @RequestPart("path") String path);
}
