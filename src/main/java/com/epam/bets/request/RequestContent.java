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

/**
 * Class provides request parameters, parts, attributes and session attributes storage.
 *
 * @author Pashchuk Ksenia
 */
public class RequestContent {

    private static final String UPLOAD_DIR = "uploads.dir";
    private static final String UPLOADS = "uploadDir";
    private static final String MULTIPART = "multipart/form-data";
    private HashMap<String, Object> requestAttributes;
    private HashMap<String, String[]> requestParameters;
    private HashMap<String, Object> sessionAttributes;
    private ArrayList<String> removedSessionAttributes;
    private Collection<Part> requestParts;
    private static final Logger LOGGER = LogManager.getLogger(RequestContent.class);

    public RequestContent() {
        this.requestAttributes = new HashMap<>();
        this.requestParameters = new HashMap<>();
        this.sessionAttributes = new HashMap<>();
        this.removedSessionAttributes = new ArrayList<>();
    }

    /**
     * Provides extracting request params from {@link HttpServletRequest] to this object.
     *
     * @param request from user
     */

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
    }

    /**
     * Provides inserting request params from this object to {@link HttpServletRequest]
     *
     * @param request to return to controller
     */
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

    /**
     * Provides inserting request attribute.
     *
     * @param key   - attribute key
     * @param value - attribute value
     */
    public void insertRequestAttribute(String key, Object value) {
        this.requestAttributes.put(key, value);
    }

    /**
     * Provides inserting session attribute
     *
     * @param key   - attribute key
     * @param value - attribute value
     */
    public void insertSessionAttribute(String key, Object value) {
        this.sessionAttributes.put(key, value);
    }

    /**
     * Provides taking request attribute
     *
     * @param key - attribute key
     */
    public Object findRequestAttribute(String key) {
        return this.requestAttributes.get(key);
    }

    /**
     * Provides taking session attribute
     *
     * @param key - attribute key
     */
    public Object findSessionAttribute(String key) {
        return this.sessionAttributes.get(key);
    }

    /**
     * Provides removing session attribute
     *
     * @param key - attribute key
     */
    public void removeSessionAttribute(String key) {
        this.sessionAttributes.remove(key);
        this.removedSessionAttributes.add(key);
    }

    /**
     * Provides taking parameter values
     *
     * @param paramName - parameter name
     */
    public String[] findParameterValues(String paramName) {
        return this.requestParameters.get(paramName);
    }

    /**
     * Provides taking parameter value
     *
     * @param paramName - parameter name
     */
    public String findParameterValue(String paramName) {
        return this.requestParameters.get(paramName)[0];
    }

    /**
     * Provides taking request parts. Only for multipart/form-data requests.
     *
     * @param partName - part name
     */
    public Part findPart(String partName) {
        Part requiredPart = null;
        for (Part part : this.requestParts) {
            if (part.getName().equals(partName)) {
                requiredPart = part;
            }
        }
        return requiredPart;
    }
}
