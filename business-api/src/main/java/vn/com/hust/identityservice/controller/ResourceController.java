package vn.com.hust.identityservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import vn.com.hust.identityservice.data.SiteMapData;
import vn.com.hust.identityservice.message.response.SecGroupResponse;
import vn.com.hust.identityservice.message.response.SiteMapResponse;
import vn.com.hust.identityservice.services.ResourceService;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    @Qualifier("corpResourceService")
    ResourceService resourceService;

    @GetMapping("/sitemap")
    @Transactional(readOnly = true)
    public ResponseEntity<List<SiteMapResponse>> getSiteMaps(@RequestHeader(name = "Authorization") String accessToken, @RequestParam @NotNull @NotEmpty String appCode) {
        List<SiteMapData> siteMapDataList = resourceService.getSiteMaps(appCode, accessToken);
        List<SiteMapResponse> siteMapResponseList = siteMapDataList.stream().map(x -> SiteMapResponse.from(x)).collect(Collectors.toList());
        return new ResponseEntity<>(siteMapResponseList, HttpStatus.OK);
    }

    @GetMapping("/getListGroupByPositionUser")
    @Transactional(readOnly = true)
    public ResponseEntity<SecGroupResponse> getGroupByPosition(@RequestParam @NotNull @NotEmpty Long userId, @RequestParam @NotNull @NotEmpty String appCode) {
        SecGroupResponse secGroupResponse = SecGroupResponse.from(resourceService.getGroupByPositionUser(userId, appCode));
        return ResponseEntity.ok(secGroupResponse);
    }

    @GetMapping("/sitemapHidden")
    @Transactional(readOnly = true)
    public ResponseEntity<List<String>> getSiteMapsHidden(@RequestHeader(name = "Authorization") String accessToken, @RequestParam @NotNull @NotEmpty String appCode) {
        List<String> siteMapDataList = resourceService.getSiteMapsHidden(appCode, accessToken);
        return new ResponseEntity<>(siteMapDataList, HttpStatus.OK);
    }
}
