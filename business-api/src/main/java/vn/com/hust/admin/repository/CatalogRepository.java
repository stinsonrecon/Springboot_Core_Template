package vn.com.hust.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.com.hust.admin.entity.Catalog;

import java.util.List;

@Repository
public interface CatalogRepository extends JpaRepository<Catalog, Long> {
    @Query(value = " select " +
            "    c.id, " +
            "    c.catalog_code, " +
            "    c.catalog_name, " +
            "    c.parent_id, " +
            "    c.status, " +
            "    c.created_at, " +
            "    c.updated_at, " +
            "    DATE_FORMAT(c.created_at, '%d/%m/%Y') createdAtString, " +
            "    DATE_FORMAT(c.updated_at, '%d/%m/%Y') updatedAtString " +
            " from catalog c " +
            " where " +
            "    c.parent_id is null " +
            " and " +
            "    c.status = :status "
            ,nativeQuery = true)
    List<Catalog> findListRootNode(@Param("status") Long status);

    @Query(value = " with recursive CTE_CATALOG " +
            "                   as (SELECT c.id, " +
            "                              c.catalog_code, " +
            "                              c.catalog_name, " +
            "                              c.parent_id, " +
            "                              c.status, " +
            "                              c.created_at, " +
            "                              c.updated_at, " +
            "                              DATE_FORMAT(c.created_at, '%d/%m/%Y') as createdAtString, " +
            "                              DATE_FORMAT(c.updated_at, '%d/%m/%Y') as updatedAtString, " +
            "                              1                                     AS level " +
            "                       from catalog c " +
            "                       where id = :id " +
            "                       union all " +
            "                       SELECT c1.id, " +
            "                              c1.catalog_code, " +
            "                              c1.catalog_name, " +
            "                              c1.parent_id, " +
            "                              c1.status, " +
            "                              c1.created_at, " +
            "                              c1.updated_at, " +
            "                              DATE_FORMAT(c1.created_at, '%d/%m/%Y') as createdAtString, " +
            "                              DATE_FORMAT(c1.updated_at, '%d/%m/%Y') as updatedAtString, " +
            "                              level + 1                              AS level " +
            "                       from catalog c1 " +
            "                                inner join CTE_CATALOG on c1.parent_id = CTE_CATALOG.id) " +
            " SELECT ct.* " +
            " FROM CTE_CATALOG ct " +
            " WHERE ct.status = :status "
            , nativeQuery = true)
    List<Catalog> findAllById(@Param("id") Long id, @Param("status") Long status);

    @Query(value = " with recursive CTE_CATALOG (id, catalog_code, catalog_name, parent_id, status, created_at, updated_at, createdAtString, " +
            "                             updatedAtString, level) " +
            "     as (SELECT c.id, " +
            "                c.catalog_code, " +
            "                c.catalog_name, " +
            "                c.parent_id, " +
            "                c.status, " +
            "                c.created_at, " +
            "                c.updated_at, " +
            "                DATE_FORMAT(c.created_at, '%d/%m/%Y') as createdAtString, " +
            "                DATE_FORMAT(c.updated_at, '%d/%m/%Y') as updatedAtString, " +
            "                1                                     AS level " +
            "         from catalog c " +
            "         where ( " +
            "                           c.status = 1 " +
            "                       and c.parent_id is null " +
            "                   ) " +
            "         union all " +
            "         SELECT c1.id, " +
            "                c1.catalog_code, " +
            "                c1.catalog_name, " +
            "                c1.parent_id, " +
            "                c1.status, " +
            "                c1.created_at, " +
            "                c1.updated_at, " +
            "                DATE_FORMAT(c1.created_at, '%d/%m/%Y') as createdAtString, " +
            "                DATE_FORMAT(c1.updated_at, '%d/%m/%Y') as updatedAtString, " +
            "                level + 1                              AS level " +
            "         from catalog c1 " +
            "                  inner join CTE_CATALOG on (CTE_CATALOG.id = c1.parent_id)) " +
            "    , max_level as (SELECT MAX(ct.LEVEL) max_l FROM CTE_CATALOG ct WHERE ct.status = :status) " +
            " SELECT ct.* " +
            " FROM CTE_CATALOG ct " +
            " WHERE ct.status = :status " +
            "   AND ct.level <= (SELECT max_l FROM max_level)"
            ,nativeQuery = true)
    List<Catalog> findAllByStatus(@Param("status") Long status);
}
