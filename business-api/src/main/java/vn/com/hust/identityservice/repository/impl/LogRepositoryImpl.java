package vn.com.hust.identityservice.repository.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import vn.com.hust.identityservice.message.request.LogRequest;
import vn.com.hust.identityservice.message.response.LogResponse;
import vn.com.hust.identityservice.repository.LogRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class LogRepositoryImpl implements LogRepository {
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<LogResponse> searchLog(LogRequest request) {
        Map<String , Object> params = new HashMap<>();

        StringBuilder sqlQuery = new StringBuilder();
        StringBuilder sqlCondition = new StringBuilder();
        sqlQuery.append("select * from ( select * from (SELECT\n" +
                "    ls.*,\n" +
                "    (\n" +
                "        SELECT\n" +
                "            name\n" +
                "        FROM\n" +
                "            API_PARTNER\n" +
                "        WHERE\n" +
                "            API_PARTNER_id = ls.API_PARTNER_id\n" +
                "    ) AS API_PARTNER_name,\n" +
                "    ( create_time + numtodsinterval(process_time / 1000,'SECOND') ) AS response_time\n" +
                "FROM\n" +
                "    LOG_SEND ls WHERE 1 = 1 ");
        if (StringUtils.isNotBlank(request.getResponseStatus())) {
            if ("TB9999".equals(request.getResponseStatus())) {
                sqlCondition.append(" and ls.RESPONSE_STATUS IS NULL ");
            } else {
                sqlCondition.append(" and ls.RESPONSE_STATUS = :responseStatus");
                params.put("responseStatus", request.getResponseStatus());
            }
        }
        if (StringUtils.isNotBlank(request.getFromDate())) {
            sqlCondition.append("  AND ls.CREATE_TIME >= TO_DATE (:fromDate, 'dd/MM/yyyy')  ");
            params.put("fromDate", request.getFromDate());
        }
        if (StringUtils.isNotBlank(request.getToDate())) {
            sqlCondition.append("  AND ls.CREATE_TIME < TO_DATE (:toDate, 'dd/MM/yyyy') + 1  ");
            params.put("toDate", request.getToDate());
        }
        if (StringUtils.isNotBlank(request.getApiPartnerId())) {
            sqlCondition.append("  AND ls.API_PARTNER_id = :apiPartnerId   ");
            params.put("apiPartnerId", request.getApiPartnerId());
        }
        if (StringUtils.isNotBlank(request.getMessageId())) {
            sqlCondition.append(" and UPPER(ls.MESSAGE_ID) like UPPER(:messageId)");
            params.put("messageId", "%" + request.getMessageId() + "%");
        }
        sqlCondition.append(") order by LOG_SEND_id desc )");
        sqlQuery.append(sqlCondition);
        sqlQuery.append(" OFFSET :page ROWS FETCH NEXT :size ROWS ONLY ");
        if (request.getPage() == 0) {
            params.put("page", 0);
        } else {
            params.put("page", (request.getPage() * request.getPageSize()));
        }
        params.put("size", request.getPageSize());

        Query query = entityManager.createNativeQuery(sqlQuery.toString(), LogResponse.class);
        params.forEach(query::setParameter);
        return query.getResultList();
    }

    @Override
    public Long countLog(LogRequest request) {
        StringBuilder sql = new StringBuilder();
        StringBuilder sqlCondition = new StringBuilder();
        Map<String, Object> params = new HashMap<String, Object>();

        sql.append(" SELECT COUNT(*) FROM\n" +
                "    LOG_SEND ls WHERE 1 = 1  and rownum<100000000 ");
        if (StringUtils.isNotBlank(request.getResponseStatus())){
            if("TB9999".equals(request.getResponseStatus())){
                sqlCondition.append(" and ls.RESPONSE_STATUS IS NULL ");
            }else {
                sqlCondition.append(" and ls.RESPONSE_STATUS = :responseStatus");
                params.put("responseStatus", request.getResponseStatus());
            }
        }
        if (StringUtils.isNotBlank(request.getFromDate())) {
            sqlCondition.append("  AND ls.CREATE_TIME >= TO_DATE (:fromDate, 'dd/MM/yyyy')  ");
            params.put("fromDate", request.getFromDate());
        }
        if (StringUtils.isNotBlank(request.getToDate())) {
            sqlCondition.append("  AND ls.CREATE_TIME < TO_DATE (:toDate, 'dd/MM/yyyy') + 1  ");
            params.put("toDate", request.getToDate());
        }
        if (StringUtils.isNotBlank(request.getApiPartnerId())) {
            sqlCondition.append("  AND ls.API_PARTNER_id = :apiPartnerId   ");
            params.put("apiPartnerId", request.getApiPartnerId());
        }
        if (StringUtils.isNotBlank(request.getMessageId())){
            sqlCondition.append(" and UPPER(ls.MESSAGE_ID) like UPPER(:messageId)");
            params.put("messageId", "%" + request.getMessageId() + "%");
        }
        sql.append(sqlCondition);

        Query query = entityManager.createNativeQuery(sql.toString());

        params.forEach(query::setParameter);

        return ((Number) query.getSingleResult()).longValue();
    }

    @Override
    public LogResponse exportLog(LogRequest request) {
        Map<String , Object> params = new HashMap<>();

        StringBuilder sqlQuery = new StringBuilder();
        StringBuilder sqlCondition = new StringBuilder();

        sqlQuery.append(" SELECT\n" +
                "    ls.*,\n" +
                "    (\n" +
                "        SELECT\n" +
                "            name\n" +
                "        FROM\n" +
                "            API_PARTNER\n" +
                "        WHERE\n" +
                "            API_PARTNER_id = ls.API_PARTNER_id\n" +
                "    ) AS API_PARTNER_name,\n" +
                "    ( create_time + numtodsinterval(process_time / 1000,'SECOND') ) AS response_time\n" +
                "FROM\n" +
                "    LOG_SEND ls WHERE 1 = 1 ");
        if (StringUtils.isNotBlank(request.getResponseStatus())){
            if("TB9999".equals(request.getResponseStatus())){
                sqlCondition.append(" and ls.RESPONSE_STATUS IS NULL ");
            }else {
                sqlCondition.append(" and ls.RESPONSE_STATUS = :responseStatus");
                params.put("responseStatus", request.getResponseStatus());
            }
        }
        if (StringUtils.isNotBlank(request.getFromDate())) {
            sqlCondition.append("  AND ls.CREATE_TIME >= TO_DATE (:fromDate, 'dd/MM/yyyy')  ");
            params.put("fromDate", request.getFromDate());
        }
        if (StringUtils.isNotBlank(request.getToDate())) {
            sqlCondition.append("  AND ls.CREATE_TIME < TO_DATE (:toDate, 'dd/MM/yyyy') + 1  ");
            params.put("toDate", request.getToDate());
        }
        if (StringUtils.isNotBlank(request.getApiPartnerId())) {
            sqlCondition.append("  AND ls.API_PARTNER_id = :apiPartnerId   ");
            params.put("apiPartnerId", request.getApiPartnerId());
        }
        sqlCondition.append( " order by ls.RESPONSE_STATUS,ls.CREATE_TIME desc");
        sqlQuery.append(sqlCondition);
        Query query = entityManager.createNativeQuery(sqlQuery.toString(),LogResponse.class);
        params.forEach(query::setParameter);
        List<LogResponse> objects = query.getResultList();
        LogResponse response = new LogResponse();
        response.setItems(objects);
        return response;
    }
}
