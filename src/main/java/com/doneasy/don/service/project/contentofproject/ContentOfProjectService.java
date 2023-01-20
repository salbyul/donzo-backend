package com.doneasy.don.service.project.contentofproject;

import com.doneasy.don.repository.project.ContentOfProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ContentOfProjectService {

    private final ContentOfProjectRepository contentOfProjectRepository;

    @Value("${admin.image.dir}") private String path;

    public List<byte[]> getByteArray(List<String> imageList) throws IOException {

        byte[] array;
        List<byte[]> list = new ArrayList<>();

        for (String s : imageList) {
            File file = new File(getFullPath(s));
            array = Files.readAllBytes(file.toPath());
            list.add(array);
        }

        return list;
    }
    private String getFullPath(String image_name) {
        return path + image_name;
    }
}
