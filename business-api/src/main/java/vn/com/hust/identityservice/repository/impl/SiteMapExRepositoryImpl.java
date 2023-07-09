package vn.com.hust.identityservice.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.com.hust.admin.model.SecGroup;
import vn.com.hust.identityservice.model.SiteMapEx;
import vn.com.hust.identityservice.repository.SiteMapExRepo;
import vn.com.hust.identityservice.repository.SiteMapExRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Component
public class SiteMapExRepositoryImpl implements SiteMapExRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    SiteMapExRepo siteMapExRepo;


    @Override
    public List<SiteMapEx> getListSiteMapExByUserIdAndAppCode(String userId, String appCode) {
        String sqlAppId = "select app_id from SEC_APP where app_code = ?";
        Query queryAppId = entityManager.createNativeQuery(sqlAppId);
        queryAppId.setParameter(1, appCode);
//        Long appId = ((BigDecimal) queryAppId.getSingleResult()).longValue();
        Integer appId = ((BigInteger) queryAppId.getSingleResult()).intValue();


        String sql2 = "with temp as  " +
                " (  " +
                " SELECT /*+ MATERIALIZE */ DISTINCT a.* " +
                " FROM SEC_MODULE a " +
                " WHERE (EXISTS " +
                "         (SELECT 1 " +
                "          FROM " +
                "            (SELECT smr.module_id " +
                "             FROM " +
                "               (SELECT DISTINCT sgm.module_id, " +
                "                                sgm.access_type, " +
                "                                sg.child_level " +
                "                FROM SEC_GROUP_MODULE sgm, " +
                "                  (SELECT sg.GROUP_ID, " +
                "                          LEVEL child_level " +
                "                   FROM SEC_GROUP sg " +
                "                   WHERE NVL (status, " +
                "                              0) > 0 " +
                "                     START WITH sg.GROUP_ID IN " +
                "                       (SELECT sgu.GROUP_ID " +
                "                        FROM SEC_GROUP_USER sgu " +
                "                        WHERE sgu.user_id = :USER_ID" +
                "						 UNION ALL" +
                "                        SELECT GROUP_ID FROM SEC_GROUP_POSITION sgp WHERE position_id = " +
                "						(select position_id from SEC_USER where user_id = :USER_ID) " +
                "	) CONNECT BY " +
                "                     PRIOR sg.parent_group_id = sg.GROUP_ID) sg " +
                "                WHERE sgm.GROUP_ID = sg.GROUP_ID) smr " +
                "             GROUP BY smr.module_id " +
                "             HAVING MOD (MIN (2 * smr.child_level + smr.access_type), 2) > 0) b " +
                "          WHERE a.module_id = b.module_id) " +
                "       OR EXISTS " +
                "         (SELECT 1 " +
                "          FROM SEC_USER_MODULE e " +
                "          WHERE e.user_id = :USER_ID " +
                "            AND e.module_id = a.module_id)) " +
                "  AND a.app_id = :APP_ID " +
                "  AND module_type IN (1,2)) " +
                " select module_id,  " +
                "       parent_id,  " +
                "       module_type,  " +
                "       module_name,  " +
                "       (CASE  " +
                "            WHEN path IS NOT NULL THEN path || '?urlPathId=' || module_id  " +
                "            ELSE NULL  " +
                "        END)  " +
                "            path,  " +
                "       description,  " +
                "       status,  " +
                "       ord,  " +
                "       include_menu,  " +
                "       app_id  from (select distinct a.* from SEC_MODULE a where module_type in ('1', '2') " +
                " 		and include_menu = 1 and status = 1  AND a.app_id = :APP_ID " +
                " 		start with module_id in  " +
                " (select module_id from temp) connect by prior parent_id = module_id) a  " +
                " start with (a.parent_id is null or a.parent_id = 0) connect by prior module_id = parent_id   " +
                " order by level, NVL (a.parent_id, 0), a.ord ";


        StringBuilder sb = new StringBuilder();

        sb.append(" WITH RECURSIVE temp AS (                                                                         ");
        sb.append("    SELECT DISTINCT a.*                                                                          ");
        sb.append("    FROM SEC_MODULE a                                                                            ");
        sb.append("    WHERE (                                                                                      ");
        sb.append("            EXISTS (                                                                             ");
        sb.append("                SELECT 1                                                                         ");
        sb.append("                FROM (                                                                           ");
        sb.append("                         SELECT smr.module_id                                                    ");
        sb.append("                         FROM (                                                                  ");
        sb.append("                                  SELECT DISTINCT sgm.module_id,                                 ");
        sb.append("                                                  sgm.access_type,                               ");
        sb.append("                                                  sg.child_level                                 ");
        sb.append("                                  FROM SEC_GROUP_MODULE sgm                                      ");
        sb.append("                                           INNER JOIN (                                          ");
        sb.append("                                      SELECT sg.GROUP_ID,                                        ");
        sb.append("                                             @level \\:= IFNULL(@level, 0) + 1 AS child_level      ");
        sb.append("                                      FROM SEC_GROUP sg                                          ");
        sb.append("                                      WHERE IFNULL(status, 0) > 0                                ");
        sb.append("                                        AND sg.GROUP_ID IN (                                     ");
        sb.append("                                          SELECT sgu.GROUP_ID                                    ");
        sb.append("                                          FROM SEC_GROUP_USER sgu                                ");
        sb.append("                                          WHERE sgu.user_id = :userId                           ");
        sb.append("                                                                                                 ");
        sb.append("                                      )                                                          ");
        sb.append("                                      ORDER BY sg.GROUP_ID                                       ");
        sb.append("                                  ) sg ON sgm.GROUP_ID = sg.GROUP_ID                             ");
        sb.append("                                  WHERE sg.group_id IN (                                         ");
        sb.append("                                      SELECT sgu.group_id                                        ");
        sb.append("                                      FROM SEC_GROUP_USER sgu                                    ");
        sb.append("                                      WHERE sgu.user_id = :userId                               ");
        sb.append("                                  )                                                              ");
        sb.append("                              ) smr                                                              ");
        sb.append("                         GROUP BY smr.module_id                                                  ");
        sb.append("                         HAVING MOD(MIN(2 * smr.child_level + smr.access_type), 2) > 0           ");
        sb.append("                     ) b                                                                         ");
        sb.append("                WHERE a.module_id = b.module_id                                                  ");
        sb.append("            )                                                                                    ");
        sb.append("            OR                                                                                   ");
        sb.append("        EXISTS (                                                                                 ");
        sb.append("            SELECT 1                                                                             ");
        sb.append("            FROM SEC_USER_MODULE e                                                               ");
        sb.append("            WHERE e.user_id = :userId                                                           ");
        sb.append("              AND e.module_id = a.module_id                                                      ");
        sb.append("        )                                                                                        ");
        sb.append("        )                                                                                        ");
        sb.append("      AND a.app_id = :appId                                                                     ");
        sb.append("      AND module_type IN (1, 2)                                                                  ");
        sb.append("),                                                                                               ");
        sb.append("               recursive_module AS (                                                             ");
        sb.append("                   SELECT                                                                        ");
        sb.append("                       module_id,                                                                ");
        sb.append("                       parent_id,                                                                ");
        sb.append("                       module_type,                                                              ");
        sb.append("                       module_name,                                                              ");
        sb.append("                       CASE                                                                      ");
        sb.append("                           WHEN path IS NOT NULL THEN CONCAT(path, '?urlPathId=', module_id)     ");
        sb.append("                           ELSE NULL                                                             ");
        sb.append("                           END AS path,                                                          ");
        sb.append("                       description,                                                              ");
        sb.append("                       status,                                                                   ");
        sb.append("                       ord,                                                                      ");
        sb.append("                       include_menu,                                                             ");
        sb.append("                       app_id,                                                                   ");
        sb.append("                       1 AS level    , ICON, TRANSLATE_KEY, PATH_FOLDER                                                            ");
        sb.append("                   FROM SEC_MODULE                                                               ");
        sb.append("                   WHERE module_type IN (1, 2)                                                   ");
        sb.append("                     AND include_menu = 1                                                        ");
        sb.append("                     AND status = 1                                                              ");
        sb.append("                     AND module_id IN (                                                          ");
        sb.append("                       SELECT module_id                                                          ");
        sb.append("                       FROM temp                                                                 ");
        sb.append("                   )                                                                             ");
        sb.append("               )                                                                                 ");
        sb.append("SELECT                                                                                           ");
        sb.append("    module_id,                                                                                   ");
        sb.append("    parent_id,                                                                                   ");
        sb.append("    module_type,                                                                                 ");
        sb.append("    module_name,                                                                                 ");
        sb.append("    path,                                                                                        ");
        sb.append("    description,                                                                                 ");
        sb.append("    status,                                                                                      ");
        sb.append("    ord,                                                                                         ");
        sb.append("    include_menu,                                                                                ");
        sb.append("    app_id         , ICON, TRANSLATE_KEY, PATH_FOLDER                                                                                 ");
        sb.append("FROM recursive_module                                                                            ");
        sb.append("ORDER BY level, IFNULL(parent_id, 0), ord; ");



                Query query = entityManager.createNativeQuery(sb.toString(), SiteMapEx.class);
        query.setParameter("appId", appId);
        query.setParameter("userId", userId);

        List<SiteMapEx> siteMapExList = query.getResultList();


        if (siteMapExList == null || siteMapExList.isEmpty())
            return new ArrayList<>();

        return siteMapExList;
    }

    @Override
    public List<String> getGroupNameAccess(Long userId, String appCode) {
        String sql = "WITH RECURSIVE group_hierarchy AS (\n" +
                "    SELECT group_id, group_name, parent_group_id\n" +
                "    FROM SEC_GROUP\n" +
                "    WHERE group_name = :APP_CODE\n" +
                "    UNION ALL\n" +
                "    SELECT g.group_id, g.group_name, g.parent_group_id\n" +
                "    FROM SEC_GROUP g\n" +
                "    INNER JOIN group_hierarchy gh ON g.parent_group_id = gh.group_id\n" +
                ")\n" +
                "SELECT tb2.group_name\n" +
                "FROM (\n" +
                "    SELECT group_id\n" +
                "    FROM SEC_GROUP_USER\n" +
                "    WHERE user_id = :USER_ID\n" +
                "    UNION ALL\n" +
                "    SELECT group_id\n" +
                "    FROM SEC_GROUP_POSITION\n" +
                "    WHERE position_id = (SELECT position_id FROM SEC_USER WHERE user_id = :USER_ID)\n" +
                ") tb1\n" +
                "JOIN group_hierarchy tb2 ON tb1.group_id = tb2.group_id\n" +
                "WHERE tb2.group_name != :APP_CODE;\n";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("USER_ID", userId);
        query.setParameter("APP_CODE", appCode);
        List<String> ListGroupName = query.getResultList();
        //List<String> ListGroupName = resultListAsNumber.stream().map(i -> i.longValue()).collect(Collectors.toList());
        if (ListGroupName == null || ListGroupName.isEmpty())
            return new ArrayList<>();
        return ListGroupName;
    }

    @Override
    public List<SecGroup> getListGroupByUserPosition(Long userId, String appCode) {
//		String sql = "select * " +
//				"from SEC_GROUP where group_id in " +
//				"(select group_id from SEC_GROUP_POSITION where position_id = " +
//				"(select position_id from SEC_USER where user_id = :userId)) and status = 1";
        String sql = "WITH RECURSIVE group_hierarchy AS (\n" +
                "    SELECT group_id, group_name, parent_group_id\n" +
                "    FROM SEC_GROUP\n" +
                "    WHERE group_name = :appCode\n" +
                "    UNION ALL\n" +
                "    SELECT g.group_id, g.group_name, g.parent_group_id\n" +
                "    FROM SEC_GROUP g\n" +
                "    INNER JOIN group_hierarchy gh ON g.parent_group_id = gh.group_id\n" +
                ")\n" +
                "SELECT *\n" +
                "FROM group_hierarchy\n" +
                "WHERE group_id IN (\n" +
                "    SELECT group_id\n" +
                "    FROM SEC_GROUP_POSITION\n" +
                "    WHERE position_id = (\n" +
                "        SELECT position_id\n" +
                "        FROM SEC_USER\n" +
                "        WHERE user_id = :userId\n" +
                "    )\n" +
                ")\n" +
                "AND group_name != 'ADMIN'";
        Query query = entityManager.createNativeQuery(sql, SecGroup.class);
        query.setParameter("userId", userId);
        query.setParameter("appCode", appCode);
        List<SecGroup> listGroup = query.getResultList();

        if (listGroup == null || listGroup.isEmpty())
            return new ArrayList<>();
        for (SecGroup group : listGroup) {
            group.setSecSchedule(new ArrayList<>());
        }
        return listGroup;
    }

    @Override
    public List<String> getListSiteMapHiddenByUserIdAndAppCode(String userId, String appCode) {
        String sqlAppId = "select app_id from SEC_APP where app_code = ?";
        Query queryAppId = entityManager.createNativeQuery(sqlAppId);
        queryAppId.setParameter(1, appCode);
        Integer appId = ((BigInteger) queryAppId.getSingleResult()).intValue();
        StringBuilder sb = new StringBuilder();
        sb.append(		"WITH RECURSIVE ");
        sb.append(      "    temp AS (SELECT DISTINCT a.* ");
        sb.append(      "             FROM SEC_MODULE a ");
        sb.append(      "             WHERE (EXISTS (SELECT 1 ");
        sb.append(      "                            FROM (SELECT smr.module_id ");
        sb.append(      "                                  FROM (SELECT DISTINCT sgm.module_id, sgm.access_type, sg.child_level ");
        sb.append(      "                                        FROM SEC_GROUP_MODULE sgm ");
        sb.append(      "                                                 INNER JOIN (SELECT sg.GROUP_ID, @level \\:= IFNULL(@level, 0) + 1 AS child_level ");
        sb.append(      "                                                             FROM SEC_GROUP sg ");
        sb.append(      "                                                             WHERE IFNULL(status, 0) > 0 ");
        sb.append(      "                                                               AND sg.GROUP_ID IN (SELECT sgu.GROUP_ID ");
        sb.append(      "                                                                                   FROM SEC_GROUP_USER sgu ");
        sb.append(      "                                                                                   WHERE sgu.user_id =  :userId) ");
        sb.append(      "                                                             ORDER BY sg.GROUP_ID) sg ON sgm.GROUP_ID = sg.GROUP_ID ");
        sb.append(      "                                        WHERE sg.group_id IN (SELECT sgu.group_id ");
        sb.append(      "                                                              FROM SEC_GROUP_USER sgu ");
        sb.append(      "                                                              WHERE sgu.user_id =  :userId)) smr ");
        sb.append(      "                                  GROUP BY smr.module_id ");
        sb.append(      "                                  HAVING MOD(MIN(2 * smr.child_level + smr.access_type), 2) > 0) b ");
        sb.append(      "                            WHERE a.module_id = b.module_id) OR ");
        sb.append(      "                    EXISTS (SELECT 1 FROM SEC_USER_MODULE e WHERE e.user_id =  :userId AND e.module_id = a.module_id)) ");
        sb.append(      "               AND a.app_id = :appId ");
        sb.append(      "               AND module_type IN (1, 2)), ");
        sb.append(      "    recursive_module AS (SELECT module_id, ");
        sb.append(      "                                parent_id, ");
        sb.append(      "                                module_type, ");
        sb.append(      "                                module_name, ");
        sb.append(      "                                path, ");
        sb.append(      "                                description, ");
        sb.append(      "                                status, ");
        sb.append(      "                                ord, ");
        sb.append(      "                                include_menu, ");
        sb.append(      "                                app_id, ");
        sb.append(      "                                1                 AS level, ");
        sb.append(      "                                ICON, ");
        sb.append(      "                                TRANSLATE_KEY, ");
        sb.append(      "                                PATH_FOLDER ");
        sb.append(      "                         FROM SEC_MODULE ");
        sb.append(      "                         WHERE module_type IN ( 2) ");
        sb.append(      "                           AND include_menu = 0 ");
        sb.append(      "                           AND status = 1 ");
        sb.append(      "                           AND (PATH like '%BTN%'  OR PATH like '%API%')");
        sb.append(      "                           AND module_id IN (SELECT module_id FROM temp)) ");
        sb.append(      "SELECT   ");
        sb.append(      "       path ");

        sb.append(      "FROM recursive_module ");
        sb.append(      "ORDER BY level, IFNULL(parent_id, 0), ord; ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("appId", appId);
        query.setParameter("userId", userId);

        List<String> siteMapExList = query.getResultList();

        if (siteMapExList == null || siteMapExList.isEmpty())
            return new ArrayList<>();

        return siteMapExList;
    }
}
