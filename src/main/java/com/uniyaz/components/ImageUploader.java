package com.uniyaz.components;

import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Image;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload;

import java.io.*;

public class ImageUploader implements Upload.Receiver, Upload.SucceededListener {
    public File file;
    Image image;

    public ImageUploader(Image image) {
        this.image = image;
    }

    public OutputStream receiveUpload(String filename,
                                      String mimeType) {

        // Create upload stream
        FileOutputStream fos = null; // Stream to write to
        try {
            // Open the file for writing.
            file = new File(filename);
            fos = new FileOutputStream(file);
        } catch (final java.io.FileNotFoundException e) {
            new Notification("Could not open file<br/>",
                    e.getMessage(),
                    Notification.Type.ERROR_MESSAGE)
                    .show(Page.getCurrent());
            return null;
        }
        return fos; // Return the output stream to write to
    }

    @Override
    public void uploadSucceeded(Upload.SucceededEvent succeededEvent) {/*
        try {
            FileResource fileResource = new FileResource(file);
            System.out.println(fileResource.getSourceFile().length());
            FileInputStream fin = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            fin.read(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        image.setSource(new FileResource(file));
    }

}
