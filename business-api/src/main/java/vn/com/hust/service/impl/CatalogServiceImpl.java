package vn.com.hust.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.core.common.generics.impl.GenericServiceImpl;
import vn.com.core.common.utils.DataUtil;
import vn.com.hust.common.Constants;
import vn.com.hust.data.CatalogData;
import vn.com.hust.entity.Catalog;
import vn.com.hust.repository.CatalogRepository;
import vn.com.hust.service.CatalogService;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CatalogServiceImpl extends GenericServiceImpl<Catalog, Long> implements CatalogService {
    @Autowired
    private CatalogRepository repo;

    @Override
    public Object getAllByIdNode(Catalog catalog) {
        List<Catalog> catalogList;
        if(catalog.getId() == Constants.ROOT_ID) {
            catalogList = repo.findListRootNode(catalog.getStatus());
        }
        else {
            catalogList = repo.findAllById(catalog.getId(), catalog.getStatus());
        }
        return catalogList;
    }

    @Override
    public Object getTreeCatalog(Catalog catalog) {
        List<Catalog> catalogList = repo.findAllByStatus(catalog.getStatus());

        List<CatalogData> catalogDataList = new ArrayList<>();
        for(Catalog item : catalogList) {
            CatalogData data = CatalogData.from(item);
            catalogDataList.add(data);
        }
        return DataUtil.parseToTree(catalogDataList);
    }
}
