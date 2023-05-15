package vn.com.hust.service;

import vn.com.hust.entity.Catalog;

public interface CatalogService {
    Object getAllByIdNode(Catalog catalog);

    Object getTreeCatalog(Catalog catalog);
}
