package com.ken.kenuserservice.interceptor;

import com.ken.kenuserservice.utils.ReflectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Description:请求工具类
 *
 * @author kenzhao
 * @date 2019/4/8 15:56
 */
public class RequestUtils {
    private static final String REGEX = "-";

    private static final String REGEX_SECOND = "_";

    private static final String HEADER = "x-forwarded-for";

    private static final String REAL_IP_HEADER = "X-Real-IP";

    private static final String REFER_HEADER = "Referer";

    /**
     * 解析HTTP请求头
     *
     * @param request
     * @return
     */
    public static AppRequestHeader parseRequestHeader(HttpServletRequest request) {
        AppRequestHeader appRequestHeader = new AppRequestHeader();
        Enumeration<String> headers = request.getHeaderNames();
        Map<String, String> header = new HashMap<>();
        while (headers.hasMoreElements()) {
            String name = headers.nextElement();
            String value = request.getHeader(name);
            header.put(name, value);
            String humpName = convertDelimited2Hump(name, REGEX);
            humpName = convertDelimited2Hump(humpName, REGEX_SECOND);
            ReflectionUtils.setPropertyValue(appRequestHeader, humpName, value);
        }
        appRequestHeader.setHeader(header);
        return appRequestHeader;
    }

    /**
     * @param request 获取远程ip
     *
     */
    public static String getRemoteAddr(HttpServletRequest request) {
        String ip = request.getHeader(HEADER);
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader(REAL_IP_HEADER);
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }


    /**
     * 获取当前访问的接口
     * @param request
     * @return
     */
    public static String getCurrentApi(HttpServletRequest request) {
        return request.getRequestURI().replace(request.getContextPath() + request.getServletPath(), "");
    }

    /**
     * 获取请求API
     * @param request
     * @return
     */
    public static String getRequestAPI(HttpServletRequest request) {
        return request.getRequestURI().replaceFirst(request.getContextPath(),"");
    }

    /**
     * 获取上一页的URI
     * @param request
     * @return
     */
    public static String getReferUri(HttpServletRequest request){
        String refer = request.getHeader(REFER_HEADER);
        if(StringUtils.isBlank(refer) || refer.length() < 2){
            return "";
        }
        String ctx = request.getContextPath();
        return refer.substring(refer.indexOf(ctx) + ctx.length());
    }


    /**
     * 把带"-"的属性转成驼峰
     *
     * @param originalName
     * @return
     */
    public static String convertDelimited2Hump(String originalName, String regex) {
        if (originalName.contains(regex)) {
            String[] names = originalName.split(regex);
            List<String> nameList = Arrays.stream(names)
                    .filter(name -> !org.springframework.util.StringUtils.isEmpty(name))
                    .collect(Collectors.toList());
            StringBuilder builder = new StringBuilder(nameList.get(0));
            nameList.stream().skip(1)
                    .forEach(name ->
                            builder.append(name.substring(0, 1).toUpperCase())
                                    .append(name.substring(1))
                    );
            return builder.toString();

        }
        return originalName;
    }
}
