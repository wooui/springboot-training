package cn.wooui.base.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import cn.waka.framework.constants.PageConstants;
import cn.waka.framework.util.DateEditor;
import cn.wooui.base.common.Base;

public class BaseController extends Base{
	public static final String TEXT_UTF8 = "text/html;charset=UTF-8";
	public static final String JSON_UTF8 = "application/json;charset=UTF-8";
	public static final String XML_UTF8 = "application/xml;charset=UTF-8";

	public static final String LIST = "list";
	public static final String VIEW = "view";
	public static final String ADD = "add";
	public static final String SAVE = "save";
	public static final String EDIT = "edit";
	public static final String UPDATE = "update";
	public static final String DELETE = "delete";
	public static final String PAGE = "page";

	@Autowired
	protected HttpServletRequest request;
	
	@Autowired
	protected HttpServletResponse response;
	
	
	@InitBinder
    protected void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        webDataBinder.registerCustomEditor(Date.class, new DateEditor(true));
    }
	
	/**
     * 带参重定向
     *
     * @param path
     * @return
     */
    protected String redirect(String path) {
        return "redirect:" + path;
    }

	/**
	 * 格式化参数方式重定向
	 * @param format
	 * @param arguments
	 * @return
	 */
	public static String redirect(String format, Object... arguments) {
		return new StringBuffer("redirect:").append(MessageFormat.format(format, arguments)).toString();
	}

    /**
     * 不带参重定向
     *
     * @param response
     * @param path
     * @return
     */
    protected String redirect(HttpServletResponse response, String path) {
        try {
            response.sendRedirect(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
//    /**
//     * 获取分页请求
//     * @return
//     */
//    protected PageRequest getPageRequest(){
//    	int page = PageConstants.PAGE_NUM_FIRST;
//    	int size = PageConstants.PAGE_SIZE;
//    	Sort sort = null;
//    	try {
//    		String sortName = request.getParameter("sortName");
//    		String sortOrder = request.getParameter("sortOrder");
//    		if(StringUtils.isNoneBlank(sortName) && StringUtils.isNoneBlank(sortOrder)){
//    			if(sortOrder.equalsIgnoreCase("desc")){
//    				sort = new Sort(Direction.DESC, sortName);
//    			}else{
//    				sort = new Sort(Direction.ASC, sortName);
//    			}
//    		}
//    		page = Integer.parseInt(request.getParameter("pageNumber")) - 1;
//    		size = Integer.parseInt(request.getParameter("pageSize"));
//    	} catch (Exception e) {
//    		e.printStackTrace();
//    	}
//    	PageRequest pageRequest = new PageRequest(page, size, sort);
//    	return pageRequest;
//    }
//    
//    /**
//     * 获取分页请求
//     * @param sort 排序条件
//     * @return
//     */
//    protected PageRequest getPageRequest(Sort sort){
//    	int page = 0;
//    	int size = 10;
//    	try {
//    		String sortName = request.getParameter("sortName");
//    		String sortOrder = request.getParameter("sortOrder");
//    		if(StringUtils.isNoneBlank(sortName) && StringUtils.isNoneBlank(sortOrder)){
//    			if(sortOrder.equalsIgnoreCase("desc")){
//    				sort.and(new Sort(Direction.DESC, sortName));
//    			}else{
//    				sort.and(new Sort(Direction.ASC, sortName));
//    			}
//			}
//    		page = Integer.parseInt(request.getParameter("pageNumber")) - 1;
//    		size = Integer.parseInt(request.getParameter("pageSize"));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//    	PageRequest pageRequest = new PageRequest(page, size, sort);
//    	return pageRequest;
//    }
}
