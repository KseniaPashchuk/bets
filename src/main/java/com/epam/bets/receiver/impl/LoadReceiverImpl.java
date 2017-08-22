package com.epam.bets.receiver.impl;

import com.epam.bets.dao.NewsDAO;
import com.epam.bets.dao.TransactionManager;
import com.epam.bets.dao.UserDAO;
import com.epam.bets.dao.impl.NewsDAOImpl;
import com.epam.bets.dao.impl.UserDAOImpl;
import com.epam.bets.exception.DaoException;
import com.epam.bets.exception.ReceiverException;
import com.epam.bets.receiver.LoadReceiver;
import com.epam.bets.request.RequestContent;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

import static com.epam.bets.constant.ErrorConstant.*;
import static com.epam.bets.constant.RequestParamConstant.LoadParam.NEWS_UPLOAD_DIR;
import static com.epam.bets.constant.RequestParamConstant.LoadParam.UPLOADS;
import static com.epam.bets.constant.RequestParamConstant.LoadParam.USER_UPLOAD_DIR;
import static com.epam.bets.constant.RequestParamConstant.NewsParam.PARAM_NAME_NEWS_ID;
import static com.epam.bets.constant.RequestParamConstant.NewsParam.PARAM_NAME_PICTURE;
import static com.epam.bets.constant.RequestParamConstant.UserParam.PARAM_NAME_AVATAR_URL;
import static com.epam.bets.constant.RequestParamConstant.UserParam.PARAM_NAME_USER_ID;


public class LoadReceiverImpl implements LoadReceiver {




    //    @Override
//    public boolean updateAvatar(int userId, Part part, String savePath) throws ReceiverException {
//        UserDAO userDAO = new UserDAOImpl();
//        TransactionManager manager = new TransactionManager();
//        String fileName = part.getSubmittedFileName();
//        try {
//            if (!fileName.isEmpty()) {
//                File fileSaveDir = new File(savePath);
//                if (!fileSaveDir.exists()) {
//                    fileSaveDir.mkdir();
//                }
//                String fileExpansion = fileName.substring(fileName.lastIndexOf("."));
//                String newFileName = userId + fileExpansion;
//                File file = new File(savePath + newFileName);
//                InputStream fileContext = part.getInputStream();
//                Files.copy(fileContext, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
//
//                manager.beginTransaction(userDAO);
//                if (userDAO.updateAvatar(userId, part.getSubmittedFileName())) {
//                    manager.commit();
//                    return true;
//                } else {
//                    manager.rollback();
//                }
//            }
//            return false;
//        } catch (DaoException | IOException e) {
//            manager.rollback();
//            throw new ReceiverException(e);
//        } finally {
//            manager.close();
//        }
//    }
//
//    @Override
//    public boolean updateNewsPicture(int newsId, Part part, String savePath) throws ReceiverException {
//        NewsDAO newsDAO = new NewsDAOImpl();
//        TransactionManager manager = new TransactionManager();
//        String fileName = part.getSubmittedFileName();
//        try {
//            if (!fileName.isEmpty()) {
//                File fileSaveDir = new File(savePath);
//                if (!fileSaveDir.exists()) {
//                    fileSaveDir.mkdir();
//                }
//                String fileExpansion = fileName.substring(fileName.lastIndexOf("."));
//                String newFileName = newsId + fileExpansion;
//                File file = new File(savePath + File.separator +newFileName);
//                InputStream fileContext = part.getInputStream();
//                Files.copy(fileContext, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
//
//                manager.beginTransaction(newsDAO);
//                if (newsDAO.updatePicture(newsId, newFileName)) {
//                    manager.commit();
//                    return true;
//                } else {
//                    manager.rollback();
//                }
//            }
//            return false;
//        } catch (DaoException | IOException e) {
//            manager.rollback();
//            throw new ReceiverException(e);
//        } finally {
//            manager.close();
//        }
//    }
//
//    @Override
//    public boolean loadNewsPicture(int newsId, Part part, String savePath) throws ReceiverException {
//
//        String fileName = part.getSubmittedFileName();
//        try {
//            if (!fileName.isEmpty()) {
//                File fileSaveDir = new File(savePath);
//                if (!fileSaveDir.exists()) {
//                    fileSaveDir.mkdir();
//                }
//                String fileExpansion = fileName.substring(fileName.lastIndexOf("."));
//                String newFileName = newsId + fileExpansion;
//                File file = new File(savePath + newFileName);
//                InputStream fileContext = part.getInputStream();
//                Files.copy(fileContext, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
//            }
//            return true;
//        } catch (IOException e) {
//            throw new ReceiverException(e);
//        }
//    }
    @Override
    public void updateAvatar(RequestContent requestContent) throws ReceiverException {

        String uploadDir = requestContent.findParameterValue(UPLOADS) + USER_UPLOAD_DIR;
        int userId = (int)requestContent.findSessionAttribute(PARAM_NAME_USER_ID);
        Map<String, String> errors = new HashMap<>();
        Part part = requestContent.findPart(PARAM_NAME_AVATAR_URL);
        UserDAO userDAO = new UserDAOImpl();
        TransactionManager manager = new TransactionManager();

        String fileName = part.getSubmittedFileName();
        try {
            if (!fileName.isEmpty()) {
                File fileSaveDir = new File(uploadDir);
                if (!fileSaveDir.exists()) {
                    fileSaveDir.mkdir();
                }
                String fileExpansion = fileName.substring(fileName.lastIndexOf("."));
                String newFileName = userId + fileExpansion;
                File file = new File(uploadDir + File.separator + newFileName);
                InputStream fileContext = part.getInputStream();
                Files.copy(fileContext, file.toPath(), StandardCopyOption.REPLACE_EXISTING);

                manager.beginTransaction(userDAO);
                if (userDAO.updateAvatar(userId, newFileName)) {
                    manager.commit();
                } else {
                    manager.rollback();
                    errors.put(ERROR, UPDATE_USER_AVATAR_ERROR_MESSAGE);
                    requestContent.insertRequestAttribute(ERROR_MAP_NAME, errors);
                }
            }
        } catch (DaoException | IOException e) {
            manager.rollback();
            throw new ReceiverException(e);
        } finally {
            manager.close();
        }
    }

    @Override
    public void updateNewsPicture(RequestContent requestContent) throws ReceiverException {
        String uploadDir = requestContent.findParameterValue(UPLOADS) + NEWS_UPLOAD_DIR;
        int newsId = Integer.parseInt(requestContent.findParameterValue(PARAM_NAME_NEWS_ID));
        Map<String, String> errors = new HashMap<>();
        Part part = requestContent.findPart(PARAM_NAME_PICTURE);
        NewsDAO newsDAO = new NewsDAOImpl();
        TransactionManager manager = new TransactionManager();
        String fileName = part.getSubmittedFileName();
        try {
            if (!fileName.isEmpty()) {
                File fileSaveDir = new File(uploadDir);
                if (!fileSaveDir.exists()) {
                    fileSaveDir.mkdir();
                }
                String fileExpansion = fileName.substring(fileName.lastIndexOf("."));
                String newFileName = newsId + fileExpansion;
                File file = new File(uploadDir + File.separator + newFileName);
                InputStream fileContext = part.getInputStream();
                Files.copy(fileContext, file.toPath(), StandardCopyOption.REPLACE_EXISTING);

                manager.beginTransaction(newsDAO);
                if (newsDAO.updatePicture(newsId, newFileName)) {
                    manager.commit();
                } else {
                    manager.rollback();
                    errors.put(ERROR, UPDATE_NEWS_PICTURE_ERROR_MESSAGE);
                    requestContent.insertRequestAttribute(ERROR_MAP_NAME, errors);
                }
            }
        } catch (DaoException | IOException e) {
            manager.rollback();
            throw new ReceiverException(e);
        } finally {
            manager.close();
        }
    }

    @Override
    public void loadNewsPicture(RequestContent requestContent) throws ReceiverException {
        String uploadDir = requestContent.findParameterValue(UPLOADS) + NEWS_UPLOAD_DIR;
        int newsId = Integer.parseInt(requestContent.findParameterValue(PARAM_NAME_NEWS_ID));
        Part part = requestContent.findPart(PARAM_NAME_PICTURE);
        String fileName = part.getSubmittedFileName();
        try {
            if (!fileName.isEmpty()) {
                File fileSaveDir = new File(uploadDir);
                if (!fileSaveDir.exists()) {
                    fileSaveDir.mkdir();
                }
                String fileExpansion = fileName.substring(fileName.lastIndexOf("."));
                String newFileName = newsId + fileExpansion;
                File file = new File(uploadDir + newFileName);
                InputStream fileContext = part.getInputStream();
                Files.copy(fileContext, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new ReceiverException(e);
        }
    }
}