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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.bets.constant.ErrorConstant.*;
import static com.epam.bets.constant.ErrorConstant.NewsError.UPDATE_NEWS_PICTURE_ERROR;
import static com.epam.bets.constant.ErrorConstant.UserError.UPDATE_USER_AVATAR_ERROR;
import static com.epam.bets.constant.RequestParamConstant.LoadParam.NEWS_UPLOAD_DIR;
import static com.epam.bets.constant.RequestParamConstant.LoadParam.UPLOADS;
import static com.epam.bets.constant.RequestParamConstant.LoadParam.USER_UPLOAD_DIR;
import static com.epam.bets.constant.RequestParamConstant.NewsParam.PARAM_NAME_NEWS_ID;
import static com.epam.bets.constant.RequestParamConstant.NewsParam.PARAM_NAME_PICTURE;
import static com.epam.bets.constant.RequestParamConstant.UserParam.PARAM_NAME_AVATAR_URL;
import static com.epam.bets.constant.RequestParamConstant.UserParam.PARAM_NAME_USER_ID;


public class LoadReceiverImpl implements LoadReceiver {

    @Override
    public void updateAvatar(RequestContent requestContent) throws ReceiverException {

        String uploadDir = requestContent.findParameterValue(UPLOADS) + USER_UPLOAD_DIR;
        int userId = (int)requestContent.findSessionAttribute(PARAM_NAME_USER_ID);
        List<String> errors = new ArrayList<>();
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
                    errors.add(UPDATE_USER_AVATAR_ERROR);
                    requestContent.insertRequestAttribute(ERROR_LIST_NAME, errors);
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
        int newsId = (int)requestContent.findRequestAttribute(PARAM_NAME_NEWS_ID);
        List<String> errors = new ArrayList<>();
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
                    errors.add(UPDATE_NEWS_PICTURE_ERROR);
                    requestContent.insertRequestAttribute(ERROR_LIST_NAME, errors);
                }
            }
        } catch (DaoException | IOException e) {
            manager.rollback();
            throw new ReceiverException(e);
        } finally {
            manager.close();
        }
    }

}