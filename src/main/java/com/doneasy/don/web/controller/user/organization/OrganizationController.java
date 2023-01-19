package com.doneasy.don.web.controller.user.organization;

import com.doneasy.don.domain.user.Organization;
import com.doneasy.don.dto.user.UserLoginDto;
import com.doneasy.don.dto.user.organization.OrganizationDetailDto;
import com.doneasy.don.dto.user.organization.OrganizationGetModifyDto;
import com.doneasy.don.dto.user.organization.OrganizationSavedto;
import com.doneasy.don.repository.user.OrganizationRepository;
import com.doneasy.don.service.user.organization.OrganizationImageService;
import com.doneasy.don.service.user.organization.OrganizationService;
import com.doneasy.don.web.jwt.JwtTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/organization")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;
    private final OrganizationImageService organizationImageService;
    private final OrganizationRepository organizationRepository;
    private final JwtTokenService jwtTokenService;

    @GetMapping("/detail")
    public ResponseEntity<OrganizationDetailDto> getDetail(HttpServletRequest request) throws IOException {
        String organizationId = (String) request.getAttribute("organizationId");
        OrganizationDetailDto organizationDetailDto = organizationService.getOrganizationDetail(organizationId);
        return new ResponseEntity<>(organizationDetailDto, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity saveOrganization(@RequestPart("organization") OrganizationSavedto organizationSavedto
            , @RequestPart(value = "image") List<MultipartFile> multipartFiles) throws IOException {
        String imageName = organizationImageService.save(multipartFiles);
        organizationService.save(organizationSavedto, imageName);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/modify")
    public ResponseEntity modifyOrganization(@RequestPart("organization") OrganizationSavedto organizationSavedto
            , @RequestPart(value = "image_name", required = false) List<MultipartFile> multipartFiles) throws IOException {
        organizationService.modifyOrganization(organizationSavedto, multipartFiles);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<List<String>> login(@RequestBody UserLoginDto userLoginDto) {
        log.info("organizationId: {}", userLoginDto.getMemberId());
        String target = userLoginDto.getTarget();
        if (organizationService.login(userLoginDto)) {
            String token;
            if (userLoginDto.getTarget().equals("organization")) {
                token = jwtTokenService.publishAccessToken(null, userLoginDto.getMemberId());
            } else {
                throw new RuntimeException("ERROR!!!");
            }
            List<String> result = new ArrayList<>();
            result.add(token);
            result.add(target);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/group/get")
    public ResponseEntity getGroup(HttpServletRequest request) throws IOException {
        String organizationId = (String) request.getAttribute("organizationId");
        OrganizationGetModifyDto modifyDto = organizationService.getModifyDto(organizationId);
        return new ResponseEntity(modifyDto, HttpStatus.OK);
    }

}
