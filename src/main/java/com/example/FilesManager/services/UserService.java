package com.example.FilesManager.services;

import com.example.FilesManager.models.User;
import javax.servlet.http.Cookie;

import java.util.HashMap;
import java.util.Map;

public class UserService {
    private static final Map<String, User> loginToProfile = new HashMap<>();
    private static final Map<String, User> userBySession = new HashMap<>();
    public static void addUser(User userProfile) {
        loginToProfile.put(userProfile.getLogin(), userProfile);
    }

    public static User getUserByLogin(String login) {
        return loginToProfile.get(login);
    }

    public static User getUserBySession(String session) {
        return userBySession.get(session);
    }

    public static void addSession(String session, User userProfile) {
        userBySession.put(session, userProfile);
    }

    public static void deleteSession(String session) {
        userBySession.remove(session);
    }
    public static User getUserByCookies(Cookie[] cookies) {
        String session;
        User user;
        if ((session = CookieUtil.getValue(cookies, "JSESSIONID")) == null || (user = userBySession.get(session)) == null) {
            return null;
        }

        return user;
    }

}
