package vn.com.hust.identityservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.com.hust.identityservice.model.SiteMapEx;

import java.util.List;

@Repository
public interface SiteMapExRepo extends JpaRepository<SiteMapEx, Long> {

    @Query(value = "with recursive\n" +
            "    sg (GROUP_ID, child_level, parent_group_id) as (select sg.GROUP_ID, 1 AS child_level, sg.parent_group_id\n" +
            "                                                    from SEC_GROUP sg\n" +
            "                                                    WHERE IFNULL(sg.status, 0) > 0\n" +
            "                                                      AND sg.GROUP_ID IN (SELECT sgu.GROUP_ID\n" +
            "                                                                          FROM SEC_GROUP_USER sgu\n" +
            "                                                                          WHERE sgu.USER_ID = 1\n" +
            "                                                                          UNION ALL\n" +
            "                                                                          SELECT GROUP_ID\n" +
            "                                                                          FROM SEC_GROUP_POSITION sgp\n" +
            "                                                                          WHERE position_id = (select position_id from SEC_USER where user_id = 1))\n" +
            "                                                    union all\n" +
            "                                                    select sg1.GROUP_ID, child_level + 1 AS LEVEL, sg1.parent_group_id\n" +
            "                                                    from SEC_GROUP sg1\n" +
            "                                                             inner join sg on sg1.parent_group_id = sg.GROUP_ID),\n" +
            "    smr (module_id, access_type, child_level) as (SELECT DISTINCT sgm.module_id, sgm.access_type, sg.child_level\n" +
            "                                                  FROM SEC_GROUP_MODULE sgm,\n" +
            "                                                       sg\n" +
            "                                                  WHERE sgm.GROUP_ID = sg.GROUP_ID),\n" +
            "    b (module_id) as (SELECT smr.module_id\n" +
            "                      FROM smr\n" +
            "                      GROUP BY smr.module_id\n" +
            "                      HAVING MOD(MIN(2 * smr.child_level + smr.access_type), 2) > 0),\n" +
            "    temp as (SELECT DISTINCT a.*\n" +
            "             FROM SEC_MODULE a,\n" +
            "                  b\n" +
            "             WHERE (EXISTS(SELECT 1 FROM b WHERE a.module_id = b.module_id) OR\n" +
            "                    EXISTS(SELECT 1 FROM SEC_USER_MODULE e WHERE e.user_id = 1 AND e.module_id = a.module_id))\n" +
            "               AND a.app_id = :appId\n" +
            "               AND module_type IN (1, 2)),\n" +
            "    temp1 (MODULE_ID, PARENT_ID, MODULE_TYPE, MODULE_NAME, PATH, DESCRIPTION, STATUS, ORD, INCLUDE_MENU, APP_ID, LEVEL)\n" +
            "        as (select sm1.*, 1 as LEVEL\n" +
            "            from SEC_MODULE sm1\n" +
            "            where sm1.module_type in ('1', '2')\n" +
            "              and sm1.include_menu = 1\n" +
            "              and sm1.status = 1\n" +
            "              AND sm1.app_id = :appId\n" +
            "              AND sm1.module_id IN (select temp.module_id from temp)\n" +
            "            union all\n" +
            "            select sm2.*, LEVEL + 1 as LEVEL\n" +
            "            from SEC_MODULE sm2\n" +
            "                     inner join temp1 on sm2.PARENT_ID = temp1.MODULE_ID),\n" +
            "    filter_temp1 as (SELECT MODULE_ID,\n" +
            "                            PARENT_ID,\n" +
            "                            MODULE_TYPE,\n" +
            "                            MODULE_NAME,\n" +
            "                            PATH,\n" +
            "                            DESCRIPTION,\n" +
            "                            STATUS,\n" +
            "                            ORD,\n" +
            "                            INCLUDE_MENU,\n" +
            "                            APP_ID,\n" +
            "                            MIN(LEVEL) as LEVEL\n" +
            "                     from temp1\n" +
            "                     WHERE temp1.module_type in ('1', '2')\n" +
            "                       and temp1.include_menu = 1\n" +
            "                       and temp1.status = 1\n" +
            "                       AND temp1.app_id = :appId\n" +
            "                     GROUP BY MODULE_ID, PARENT_ID, MODULE_TYPE, MODULE_NAME, PATH, DESCRIPTION, STATUS, ORD,\n" +
            "                              INCLUDE_MENU, APP_ID),\n" +
            "    filter_temp2 as (SELECT MODULE_ID,\n" +
            "                            PARENT_ID,\n" +
            "                            MODULE_TYPE,\n" +
            "                            MODULE_NAME,\n" +
            "                            PATH,\n" +
            "                            DESCRIPTION,\n" +
            "                            STATUS,\n" +
            "                            ORD,\n" +
            "                            INCLUDE_MENU,\n" +
            "                            APP_ID,\n" +
            "                            MIN(LEVEL) as LEVEL\n" +
            "                     from temp1\n" +
            "                     WHERE temp1.module_type in ('1', '2')\n" +
            "                       and temp1.include_menu = 1\n" +
            "                       and temp1.status = 1\n" +
            "                       AND temp1.app_id = :appId\n" +
            "                     GROUP BY MODULE_ID, PARENT_ID, MODULE_TYPE, MODULE_NAME, PATH, DESCRIPTION, STATUS, ORD,\n" +
            "                              INCLUDE_MENU, APP_ID\n" +
            "                     ORDER BY LEVEL, IFNULL(temp1.PARENT_ID, 0), temp1.ORD),\n" +
            "    temp2 (MODULE_ID, PARENT_ID, MODULE_TYPE, MODULE_NAME, PATH, DESCRIPTION, STATUS, ORD, INCLUDE_MENU, APP_ID, LEVEL)\n" +
            "        as (select t1.*\n" +
            "            from filter_temp1 t1\n" +
            "            where t1.parent_id IS null\n" +
            "               OR t1.parent_id = 0\n" +
            "            union all\n" +
            "            select t2.*\n" +
            "            from filter_temp1 t2\n" +
            "                     inner join temp2 on t2.PARENT_ID = temp2.MODULE_ID),\n" +
            "    finalReult as (SELECT temp2.* from temp2 ORDER BY temp2.LEVEL, IFNULL(temp2.PARENT_ID, 0), temp2.ORD),\n" +
            "    cte_normal_call_cte_1 as (SELECT * from sg)\n" +
            "select MODULE_ID,\n" +
            "       PARENT_ID,\n" +
            "       MODULE_TYPE,\n" +
            "       MODULE_NAME,\n" +
            "       PATH,\n" +
            "       DESCRIPTION,\n" +
            "       STATUS,\n" +
            "       ORD,\n" +
            "       INCLUDE_MENU,\n" +
            "       APP_ID\n" +
            "from finalReult ", nativeQuery = true)
    List<SiteMapEx> getSiteMapList(@Param("appId") Integer appId);

//    List<SiteMapEx> getSiteMapList(@Param("APP_ID") Integer appId, @Param("USER_ID") Long userId);
}
