package com.sy.huangniao.controller.interceptor;


import com.sy.huangniao.common.bo.RespondBody;
import com.sy.huangniao.common.constant.Constant;
import com.sy.huangniao.common.enums.RespondMessageEnum;
import com.sy.huangniao.service.IRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 登录过滤器
 * @author huchao
 */
@Slf4j
@Component
public class LoginCheck implements HandlerInterceptor {

    @Autowired
    private IRedisService redisServiceImpl;
	
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
		log.info("url="+arg0.getRequestURI());
		log.info("param="+arg0.getParameterMap());
		String loginKey = arg0.getHeader("loginKey");
		arg1.setCharacterEncoding("utf-8");
		arg1.setContentType("application/json");
		if(loginKey == null){
			 loginKey = arg0.getParameter("loginKey");
		}
		
		if(loginKey==null || "".equals(loginKey)){
			PrintWriter  pw =arg1.getWriter();
			respondBody(pw,new RespondBody(RespondMessageEnum.NOINFO_LOGINKEY));
			return false;
		}
		String userId = arg0.getParameter("userId");
		
		if(userId==null || "".equals(userId)){
			
            PrintWriter  pw =arg1.getWriter();
			respondBody(pw,new RespondBody(RespondMessageEnum.NOINFO_USERID));
			return false;
		}
		String appCode = arg0.getParameter("appCode");
		if(appCode==null || "".equals(appCode)){

			PrintWriter  pw =arg1.getWriter();
			respondBody(pw,new RespondBody(RespondMessageEnum.NOINFO_APPCODE));
			return false;
		}
		String userLoginkey = redisServiceImpl.get(Constant.CACHELOGINKEY+appCode+userId,String.class);
		
		if(userLoginkey == null || "".equals(userLoginkey)){
			PrintWriter  pw =arg1.getWriter();
            respondBody(pw,new RespondBody(RespondMessageEnum.NO_LOGIN));
			return false;
		}
		
		if (loginKey.equals(userLoginkey)){
			log.info(userId+"===loginKey="+loginKey+"===校验登录成功!");
			return true;
		}else {
			log.info(userId+"===loginKey="+loginKey+"==="+userLoginkey+"无法匹配登录信息,登录校验不过");
            PrintWriter  pw =arg1.getWriter();
            respondBody(pw,new RespondBody(RespondMessageEnum.NO_LOGIN));
			return false;
		}
		
	}
	
	
	private void  respondBody(PrintWriter pw,RespondBody rb){
		if(rb==null)
            rb = new RespondBody(RespondMessageEnum.NO_LOGIN);
		pw.write(rb.toString());
		pw.flush();
		
		if (pw!=null) {
			pw.close();
		}
	}

}
