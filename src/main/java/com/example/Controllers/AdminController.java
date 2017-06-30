package com.example.Controllers;

import com.example.Objects.Entities.MemadClientObject;
import com.example.Services.GeneralManager;
import com.example.Services.UserManager;
import com.example.Utils.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static org.springframework.util.StringUtils.hasText;

/**
 * Created by Sigal on 6/12/2016.
 */

@Controller
public class AdminController {

    @Autowired
    private Utils utils;

    @Autowired
    private UserManager userManager;


    @Autowired
    private GeneralManager generalManager;




    @RequestMapping({"/admin"})
    public String admin( HttpServletRequest request, HttpServletResponse response, Model model) {
          boolean error = false;
          try {

//            //    if (uid != null) {
//            UserObject user = userManager.loadUser(uid);
//            //          if(user.getUserType()==USER_TYPE_ADMIN) {
//
              utils.setDefaultParameters(request, response, model);
              List<MemadClientObject> clients = generalManager.getAllClients();
              model.addAttribute("pageName","admin");

//
              model.addAttribute("clients",clients);

              model.addAttribute("pageName","admin");

//            //}
//            //   else {
//            //     response.sendRedirect("/dashboard");
//            ;
////                }
//
//            // } else {
//            //   response.sendRedirect("/home");
//            // error = true;
//            //}
//
//
        } catch (Exception e) {
              error = true;
          }
          if (error) {
            return "tmpl_home";
           } else {
              return "tmpl_admin";
   }
  }

     @RequestMapping("/add-new-client.json")
    public void addNewClient(HttpServletRequest request, HttpServletResponse response, @ModelAttribute MemadClientObject memadClientObject) throws Exception {
        boolean error = false;
        try {
            generalManager.updateMemdadClientObject(memadClientObject);

        } catch (Exception e) {
            error = true;
        }

        response.setContentType("application/json; charset=UTF-8");
        JSONObject JObject = new JSONObject();
        JObject.put("error", error);
        response.getWriter().print(JObject);
    }

    @RequestMapping("/update-client-details.json")
    public void updateClientDetails(HttpServletResponse response, String name,  String terminal,  String address,  String zipCode,  String hp,
                                    String telephoneNum, String faxNum,  String email,  String show, String oid, String fullName) throws Exception{
        boolean error = false;
        try {
            MemadClientObject memadClientObject = generalManager.loadMemadClientObject(Integer.valueOf(oid));
            if (memadClientObject != null) {
                memadClientObject.setName(name);
                memadClientObject.setTerminal(terminal);
                memadClientObject.setAddress(address);
                memadClientObject.setZipCode(Integer.valueOf(zipCode));
                memadClientObject.setHp(hp);
                memadClientObject.setTelephoneNum(telephoneNum);
                memadClientObject.setFaxNum(faxNum);
                memadClientObject.setMail(email);
                memadClientObject.setFullName(fullName);
                memadClientObject.setToShow(Boolean.valueOf(show));
                generalManager.updateMemdadClientObject(memadClientObject);

            } else {
                error = true;
            }
        } catch (Exception e) {
            error = true;
        }

        response.setContentType("application/json; charset=UTF-8");
        JSONObject JObject = new JSONObject();
        JObject.put("error", error);
        response.getWriter().print(JObject);

    }


//    @RequestMapping("/set_active.json")
//    public void setActive(@CookieValue("uid") Integer uid, HttpServletRequest request, HttpServletResponse response, Model model, String pendingUserUid) throws Exception {
//        boolean error = false;
//        try {
//            if (uid != null) {
//                UserObject user = userManager.loadUser(uid);
//                UserObject pendingUser = userManager.loadUser(Integer.valueOf(pendingUserUid));
//                pendingUser.setActive(true);
//                userManager.updateUser(pendingUser);
//            }
//        } catch (Exception e) {
//            error = true;
//        }
//        long countPendingUsers = userManager.getAllPendingUsers().size();
//
//        response.setContentType("application/json; charset=UTF-8");
//        JSONObject JObject = new JSONObject();
//        JObject.put("error", error);
//        JObject.put("uid", pendingUserUid);
//        JObject.put("numOfPendingUsers",countPendingUsers);
//        response.getWriter().print(JObject);
//
//    }
//
//
//    @RequestMapping({"/contact_us"})
//    public String contactUs(@CookieValue("uid") Integer uid, HttpServletRequest request, HttpServletResponse response, Model model) {
//        boolean error = false;
//        try {
//            //    if (uid != null) {
//            UserObject user = userManager.loadUser(uid);
//            utils.setDefaultParameters(request, response, model);
//            model.addAttribute("user", user);
//            model.addAttribute("pageName", "contact_us");
//
//            // } else {
//            //   response.sendRedirect("/home");
//            // error = true;
//            //}
//
//
//        } catch (Exception e) {
//            error = true;
//        }
//        if (error) {
//            return "tmpl_dashboard";
//        } else {
//            return "tmpl_contact_us";
//        }
//    }
//
//
//    @RequestMapping({"/message_sent"})
//    public String messageSent(@CookieValue("uid") Integer uid, HttpServletRequest request, HttpServletResponse response, Model model, String title, String message) {
//        boolean error = false;
//        try {
//            if (uid != null) {
//                UserObject user = userManager.loadUser(uid);
//                utils.setDefaultParameters(request, response, model);
//                model.addAttribute("user", user);
//
//                generalManager.addNewMessage(user, message, title);
//
//            } else {
//                response.sendRedirect("/home");
//                error = true;
//            }
//
//
//        } catch (Exception e) {
//            error = true;
//        }
//        if (error) {
//            return "tmpl_dashboard";
//        } else {
//            return "tmpl_contact_us";
//        }
//    }
//
//    @RequestMapping({"/mark_as_read.json"})
//    public void markAsRead(@CookieValue("uid") Integer uid, HttpServletRequest request, HttpServletResponse response, Model model, String messageId) throws Exception {
//        boolean error = false;
//        try {
//            SentMessageObject message = generalManager.loadSentMessageObject(Integer.valueOf(messageId));
//            generalManager.markMessageAsRead(message);
//
//        } catch (Exception e) {
//            error = true;
//        }
//       // int countUnreadMessages = generalManager.getUnreadMessages().size();
//     //   int num = Integer.valueOf(numOfUnreadMessages)-1;
//
//        response.setContentType("application/json; charset=UTF-8");
//        JSONObject JObject = new JSONObject();
//        JObject.put("error", error);
//        JObject.put("oid", messageId);
//       // JObject.put("numOfUnreadMessages",num);
//        response.getWriter().print(JObject);
//    }


}
