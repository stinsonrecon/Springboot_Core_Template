package vn.com.hust.admin.service;

import vn.com.hust.admin.entity.Catalog;

public interface CatalogService {
    Object getAllByIdNode(Catalog catalog);

    Object getTreeCatalog(Catalog catalog);
}
