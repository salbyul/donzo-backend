package com.doneasy.don.service.user.organization;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrganizationImageService {

    // 단체회원 이미지 경로
    @Value("${admin.image.dir}") private String path;

    // TODO organization 검증하고 실패하면 저장 X
    public String save(List<MultipartFile> multipartFiles) throws IOException {
        MultipartFile imageFile = multipartFiles.get(0);
        imageFile.transferTo(new File(path+imageFile.getOriginalFilename()));
        return imageFile.getOriginalFilename();
    }
}
