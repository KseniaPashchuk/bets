package com.epam.bets.request;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

public class RequestContent {

    private static final String UPLOAD_DIR = "uploads.dir";
    private static final String UPLOADS = "uploadDir";
    private static final String MULTIPART = "multipart/form-data";
    private static final String REFERER = "Referer";
    private HashMap<String, Object> requestAttributes;
    private HashMap<String, String[]> requestParameters;
    private HashMap<String, Object> sessionAttributes;
    private ArrayList<String> removedSessionAttributes;
    private Collection<Part> requestParts;
    private String prevRequest;
    private static final Logger LOGGER = LogManager.getLogger(RequestContent.class);

    public RequestContent() {
        this.requestAttributes = new HashMap<>();
        this.requestParameters = new HashMap<>();
        this.sessionAttributes = new HashMap<>();
        this.removedSessionAttributes = new ArrayList<>();
    }

    public void extractValues(HttpServletRequest request) {
        HttpSession session = request.getSession();

        Enumeration requestParameterNames = request.getParameterNames();

        while (requestParameterNames.hasMoreElements()) {
            String paramName = (String) requestParameterNames.nextElement();
            String[] values = request.getParameterValues(paramName);
            requestParameters.put(paramName, values);
        }
        Enumeration<String> requestAttrNames = request.getAttributeNames();
        while (requestAttrNames.hasMoreElements()) {
            String attrName = requestAttrNames.nextElement();
            Object attrValue = request.getAttribute(attrName);
            this.requestAttributes.put(attrName, attrValue);
        }

        Enumeration<String> sessionAttrNames = session.getAttributeNames();

        while (sessionAttrNames.hasMoreElements()) {
            String attrName = sessionAttrNames.nextElement();
            Object attrValue = session.getAttribute(attrName);

            this.sessionAttributes.put(attrName, attrValue);
        }
        try {
            if (request.getContentType() != null && request.getContentType().toLowerCase().contains(MULTIPART)) {
                requestParts = request.getParts();
                this.requestParameters.put(UPLOADS, new String[]{request.getServletContext().getInitParameter(UPLOAD_DIR)});
            }
        } catch (ServletException | IOException e) {
            LOGGER.log(Level.ERROR, "Can't get multipart from request", e);
        }
        prevRequest = request.getHeader(REFERER).substring(21);
    }

    public void insertValues(HttpServletRequest request) {
        HttpSession session = request.getSession();

        for (Map.Entry<String, Object> entry : requestAttributes.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            request.setAttribute(key, value);
        }

        for (Map.Entry<String, Object> entry : sessionAttributes.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            session.setAttribute(key, value);
        }

        for (String key : removedSessionAttributes) {
            session.removeAttribute(key);
        }
    }

    public void insertRequestAttribute(String key, Object value) {
        this.requestAttributes.put(key, value);
    }

    public void insertSessionAttribute(String key, Object value) {
        this.sessionAttributes.put(key, value);
    }

    public Object findRequestAttribute(String key) {
        return this.requestAttributes.get(key);
    }

    public Object findSessionAttribute(String key) {
        return this.sessionAttributes.get(key);
    }

    public void removeSessionAttribute(String key) {
        this.sessionAttributes.remove(key);
        this.removedSessionAttributes.add(key);
    }

    public String[] findParameterValues(String paramName) {
        return this.requestParameters.get(paramName);
    }

    public String findParameterValue(String paramName) {
        return this.requestParameters.get(paramName)[0];
    }

    public Part findPart(String partName) {
        Part requiredPart = null;
        for (Part part : this.requestParts) {
            if (part.getName().equals(partName)) {
                requiredPart = part;
            }
        }
        return requiredPart;
    }

    public String getPrevRequest() {
        return prevRequest;
    }
}
