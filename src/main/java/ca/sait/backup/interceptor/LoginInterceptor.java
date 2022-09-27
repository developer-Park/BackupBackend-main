package ca.sait.backup.interceptor;


import ca.sait.backup.utils.JWTUtils;
import ca.sait.backup.utils.JsonData;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class LoginInterceptor implements HandlerInterceptor {

    /**
     * before into controller class
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // DEBUG BYPASS - IBRAHIM

        if (false)
        try {


//            If it is OPTIONS, end the request
//            if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
//                response.setStatus(HttpStatus.NO_CONTENT.value());
//                response.setHeader("Access-Control-Allow-Origin", "*");
//                response.setHeader("Access-Control-Allow-Credentials", "true");
//                response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS");
//                response.setHeader("Access-Control-Max-Age", "86400");
//                response.setHeader("Access-Control-Allow-Headers", "*");
//                return false;
//            }


            String accessToken = request.getHeader("token");
            if (accessToken == null) {
                accessToken = request.getParameter("token");
            }

            if (StringUtils.isNotBlank(accessToken)) {
                Claims claims = JWTUtils.checkJWT(accessToken);
                if (claims == null) {
                    //your token is expired,please login again
                    sendJsonMessage(response, JsonData.buildError("your token is expired,please login again"));
                    return false;
                }

                Integer id = (Integer) claims.get("id");
                String name = (String) claims.get("name");

                request.setAttribute("user_id", id);
                request.setAttribute("name", name);

                return true;

            }

        }catch (Exception e){}


        return true;
//        sendJsonMessage(response, JsonData.buildError("your token is expired,please login again"));
//
//        return false;
    }




    /**
     * 响应json数据给前端
     * @param response
     * @param obj
     */
    public static void sendJsonMessage(HttpServletResponse response, Object obj){

        try{
            ObjectMapper objectMapper = new ObjectMapper();
            response.setContentType("application/json; charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.print(objectMapper.writeValueAsString(obj));
            writer.close();
            response.flushBuffer();
        }catch (Exception e){
            e.printStackTrace();
        }


    }




    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
