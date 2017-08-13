package com.epam.bets.receiver.impl;

import com.epam.bets.exception.ReceiverException;
import com.epam.bets.receiver.LoadReceiver;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;


public class LoadReceiverImpl implements LoadReceiver {
    @Override
    public boolean loadPicture(Part part, String savePath) throws ReceiverException {
        try {
            if (!part.getSubmittedFileName().isEmpty()) {
                File fileSaveDir = new File(savePath);
                if (!fileSaveDir.exists()) {
                    fileSaveDir.mkdir();
                }
                part.write(savePath + File.separator + part.getSubmittedFileName());
            }
            return true;
        } catch (IOException e) {
            throw new ReceiverException(e);
        }
    }
}
