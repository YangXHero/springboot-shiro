package com.liuzw.springbootshiro.config.shiro;

import com.liuzw.springbootshiro.constants.RedisConstants;
import com.liuzw.springbootshiro.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

/**
 * shiro session 存放到redis中
 *
 * @author liuzw
 */
@Slf4j
public class RedisSessionDao extends AbstractSessionDAO {

    @Autowired
    private RedisService redisService;
  
    @Override
    public void update(Session session) {
        log.info("-------> 更新seesion,id=[{}]", getKey(session.getId()));
         try {
             redisService.set(getKey(session.getId()), session, 1800);
            } catch (Exception e) {
             log.info("-------> 更新seesion错误[{}]", e.getMessage());
            }    
    }  
  
    @Override  
    public void delete(Session session) {  
        log.info("-------> 删除seesion  id=[{}]", getKey(session.getId()));
         try {
             redisService.del(getKey(session.getId()));
            } catch (Exception e) {
             log.error("-------> 删除seesion错误：{}", e.getMessage());
            }
          
    }  
  
    @Override  
    public Collection<Session> getActiveSessions() {  
        log.info("-------> 获取存活的session");
        return Collections.emptySet();
    }  
  
    @Override  
    protected Serializable doCreate(Session session) {  
        Serializable sessionId = generateSessionId(session);    
        assignSessionId(session, sessionId);    
        log.info("-------> 创建seesion,id=[{}]", getKey(session.getId()));
        try {
            redisService.set(getKey(session.getId()),  session);
        } catch (Exception e) {    
            log.error("-------> 创建seesion错误：{}", e.getMessage());
        }    
        return sessionId;    
    }  
  
    @Override  
    protected Session doReadSession(Serializable sessionId) {  
        log.info("-------> 获取seesion,id=[{}]", getKey(sessionId));
        Session session = null;    
        try {
            session = (Session) redisService.get(getKey(sessionId));
        } catch (Exception e) {
            log.error("-------> 获取seesion错误：{}",e.getMessage());
        }    
        return session;    
    }

    private String getKey(Serializable sessionId) {
        return RedisConstants.REDIS_SHIRO_TOKEN + sessionId.toString();
    }
}  