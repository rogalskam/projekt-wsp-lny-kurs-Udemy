package com.example.file_service.fasada;

import com.example.file_service.entity.ImageResponse;
import com.example.file_service.mediator.ImageMediator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "api/v1/image")
@RequiredArgsConstructor
public class ImageController {
    private final ImageMediator imageMediator;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> saveFile(@RequestBody MultipartFile multipartFile) {
        return imageMediator.saveImage(multipartFile);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<ImageResponse> deleteFile(@RequestBody String uuid) {
        return imageMediator.deleteImage(uuid);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getFile(@RequestBody String uuid) throws IOException {
        return imageMediator.getImage(uuid);
    }

    @RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity<ImageResponse> activateImage(@RequestBody String uuid) throws IOException {
        return imageMediator.activateImage(uuid);
    }
}
