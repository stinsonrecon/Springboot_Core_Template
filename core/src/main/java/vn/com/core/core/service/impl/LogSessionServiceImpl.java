package vn.com.core.core.service.impl;//package com.fis.fw.core.service.impl;////import com.fis.fw.core.dao.LogSessionDao;//import com.fis.fw.core.entity.LogSession;//import com.fis.fw.core.repo.LogSessionRepo;//import org.springframework.beans.factory.annotation.Autowired;//import org.springframework.beans.factory.annotation.Qualifier;//import org.springframework.stereotype.Service;//import org.springframework.transaction.annotation.Transactional;//import com.fis.fw.common.generics.GenericDao;//import com.fis.fw.common.generics.impl.GenericServiceImpl;//import com.fis.fw.core.service.LogSessionService;////import java.util.List;////@Service//public class LogSessionServiceImpl extends GenericServiceImpl<LogSession,Integer >implements LogSessionService {////    @Override//    public List<LogSession> save(List<LogSession> lst) throws Exception{//        return ((LogSessionRepo)getRepository()).save(lst);//    }////    @Override//    public void updateSessionLog(Integer userId, Integer sessionId, String ipAddress, String accessToken) throws Exception{//        ((LogSessionRepo)getRepository()).updateSessionLog(userId, sessionId,ipAddress,accessToken);//    }//}