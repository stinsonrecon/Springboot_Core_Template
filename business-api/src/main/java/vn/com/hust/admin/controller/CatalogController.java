package vn.com.hust.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.core.common.config.EnableWrapResponse;
import vn.com.core.common.generics.controller.GenericController;
import vn.com.hust.admin.common.AppPath;
import vn.com.hust.admin.entity.Catalog;
import vn.com.hust.admin.service.CatalogService;

@Slf4j
@RestController
@EnableWrapResponse
@RequestMapping("/catalog")
public class CatalogController extends GenericController<Catalog, Long> {
    @Autowired
    CatalogService service;

    @PostMapping(AppPath.REQUEST_MAPPING.GET_ALL_CATALOG_BY_ID)
    public ResponseEntity getAllByIdNode(@RequestBody Catalog catalog) {
        return ResponseEntity.ok(service.getAllByIdNode(catalog));
    }

    @PostMapping(AppPath.REQUEST_MAPPING.GET_TREE_CATALOG)
    public ResponseEntity getTreeCatalog(@RequestBody Catalog catalog) {
        return ResponseEntity.ok(service.getTreeCatalog(catalog));
    }
}
