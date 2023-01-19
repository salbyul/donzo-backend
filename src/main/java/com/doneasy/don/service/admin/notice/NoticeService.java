package com.doneasy.don.service.admin.notice;

import com.doneasy.don.domain.admin.notice.ContentsOfNotice;
import com.doneasy.don.domain.admin.notice.Notice;
import com.doneasy.don.dto.admin.notice.*;
import com.doneasy.don.repository.admin.notice.IContentOfNoticeRepository;
import com.doneasy.don.repository.admin.notice.INoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeService {

    private final INoticeRepository noticeRepository;
    private final IContentOfNoticeRepository contentOfNoticeRepository;

    @Value("${admin.image.dir}")
    private String path;

    public List<Notice> getNoticeAsList() {
        return noticeRepository.findAll();
    }

    public void save(NoticeSaveDto noticeSaveDto, List<MultipartFile> multipartFileList) throws IOException {
        Notice notice = Notice.getNotice(noticeSaveDto);
        noticeRepository.save(notice);
        List<ContentOfNoticeDto> contextList = noticeSaveDto.getContextList();

        Notice recent = noticeRepository.findRecent();
        Long id = recent.getId();

        for (int i = 0; i < multipartFileList.size(); i++) {

            MultipartFile multipartFile = multipartFileList.get(i);

            String imageName = multipartFile.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();
            String storedName = uuid + imageName;
            log.info("imageName: {}", imageName);
            ContentsOfNotice contentsOfNotice = ContentsOfNotice.getContentsOfNotice(contextList.get(i), i + 1, storedName, id);

            contentOfNoticeRepository.save(contentsOfNotice);


            multipartFile.transferTo(new File(path + storedName));
        }
    }

    public NoticeDetailDto getDetail(Long id) throws IOException {
        Notice findNotice = noticeRepository.findById(id);
        String title = findNotice.getTitle();
        List<ContentsOfNotice> findContentOfNoticeList = contentOfNoticeRepository.findByNoticeId(id);
        List<ContentOfNoticeDetailDto> list = new ArrayList<>();
        for (ContentsOfNotice contentsOfNotice : findContentOfNoticeList) {
            String image_name = contentsOfNotice.getImage_name();
            byte[] imageByteArray = getImageByteArray(image_name);
            list.add(new ContentOfNoticeDetailDto(contentsOfNotice.getOrder_num(), contentsOfNotice.getSubtitle(), contentsOfNotice.getContent(), imageByteArray));
            log.info("contents: {}", contentsOfNotice.getContent());
        }
        return new NoticeDetailDto(title, list);
    }

    private byte[] getImageByteArray(String imageName) throws IOException {
        File file = new File(path + imageName);
        return Files.readAllBytes(file.toPath());
    }
}
