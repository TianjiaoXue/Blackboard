package com.my.black.controller;


import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.my.black.dao.MessageDAO;
import com.my.black.dao.UserDAO;
import com.my.black.pojo.Message;
import com.my.black.pojo.User;


@Controller
public class MessageController {
	 
	 
	@Autowired
	@Qualifier("userDao")
	UserDAO userDao;
	
	@Autowired
	@Qualifier("messageDao")
	MessageDAO messageDao;
	
	
    @RequestMapping(value = "/message", method = RequestMethod.GET)
    public ModelAndView MessagePage(HttpServletRequest request, HttpServletResponse response) {
            
        return new ModelAndView("message");
    }
    
    
    @RequestMapping(value = "/sendMessage.htm", method = RequestMethod.POST)
    public void sendMessage(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	 HttpSession session = request.getSession();
    	 User u=(User) session.getAttribute("user");
        System.out.println(u.getUsername());
        String content=request.getParameter("content");//send with url              
        Calendar ca=new GregorianCalendar();
        Date time=ca.getTime();
        Message message=new Message();              
        message.setContent(content);
        message.setSender(u.getUsername());
        message.setSendTime(time);
        message.setPrint(false);
        messageDao.createMessage(message);        
    }
    
    @RequestMapping(value = "/getMessage.htm", method = RequestMethod.POST)
    public void printMessage(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
        response.setContentType("text/xml;charset=utf-8");
        PrintWriter out = response.getWriter();
        StringBuilder a = new StringBuilder();            
        a.append("<date>"); 
        List<Message> message= messageDao.getAllMessage();  
        System.out.println("There are message "+message.size());
        Iterator<Message> it=message.iterator();
        while(it.hasNext()){
            Message m=it.next();
            System.out.print("if it's print" + m.isPrint());
            a.append("<msg mess=\""+m.getContent()+" \" sender=\""+m.getSender()+ " \" p=\""+m.getSendTime()+" \" />");
            it.remove();
        }
        a.append("</date>");
        out.print(a.toString());
        out.flush();
        out.close();
    }
}
