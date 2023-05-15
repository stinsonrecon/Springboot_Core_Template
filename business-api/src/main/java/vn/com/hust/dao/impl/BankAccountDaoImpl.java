package vn.com.hust.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.com.core.common.generics.impl.GenericDaoImpl;
import vn.com.core.common.utils.StringUtil;
import vn.com.hust.dao.BankAccountDao;
import vn.com.hust.dto.BankAccountDTO;
import vn.com.hust.entity.BankAccount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository("BankAccountDao")
public class BankAccountDaoImpl extends GenericDaoImpl<BankAccount, Long> implements BankAccountDao {
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<BankAccount> onSearch(BankAccountDTO request) {
        List<BankAccount> listResult;
        Map<String, Object> params = new HashMap<>();
        StringBuilder sql = new StringBuilder();
        sql.append(" select ba.id, ");
        sql.append("        ba.bank_name, ");
        sql.append("        ba.username, ");
        sql.append("        ba.department, ");
        sql.append("        ba.bank_number, ");
        sql.append("        ba.created_at, ");
        sql.append("        ba.updated_at, ");
        sql.append("        DATE_FORMAT(ba.created_at, '%d/%m/%Y') as createdAtString, ");
        sql.append("        DATE_FORMAT(ba.updated_at, '%d/%m/%Y') as updatedAtString ");
        sql.append(" from bank_account ba ");
        sql.append(" where 1 = 1 ");

        if(!StringUtil.stringIsNullOrEmty(request.getBankName())) {
            sql.append(" and ba.bank_name = :bankName ");
            params.put("bankName", request.getBankName());
        }
        if(!StringUtil.stringIsNullOrEmty(request.getUsername())) {
            sql.append(" and ba.username = :username ");
            params.put("username", request.getUsername());
        }
        if(!StringUtil.stringIsNullOrEmty(request.getDepartment())) {
            sql.append(" and ba.department = :department ");
            params.put("department", request.getDepartment());
        }
        if(!StringUtil.stringIsNullOrEmty(request.getBankNumber())) {
            sql.append(" and ba.bank_number = :bankNumber ");
            params.put("bankNumber", request.getBankNumber());
        }
        if(!StringUtil.stringIsNullOrEmty(request.getFromDate())) {
            sql.append(" and ba.created_at >= STR_TO_DATE(:fromDate, '%d/%m/%Y') ");
            params.put("fromDate", request.getFromDate());
        }
        if(!StringUtil.stringIsNullOrEmty(request.getToDate())) {
            sql.append(" AND ba.created_at <= STR_TO_DATE(:toDate, '%d/%m/%Y') + 1 ");
            params.put("toDate", request.getToDate());
        }

        sql.append(" order by ba.created_at desc  ");

        listResult = namedParameterJdbcTemplate.query(sql.toString(), params, new BeanPropertyRowMapper<>(BankAccount.class));

        return listResult;
    }
}
