package com.uniyaz.components;

import com.uniyaz.domain.Content;
import com.uniyaz.domain.MyButton;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Image;
import com.vaadin.ui.VerticalLayout;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ImageContentLayout extends VerticalLayout {

    public ImageContentLayout(Content content) {
        StreamResource.StreamSource streamSource = new StreamResource.StreamSource() {
            public InputStream getStream()
            {
                return (content.getImage() == null) ? null : new ByteArrayInputStream(
                        content.getImage());
            }
        };

        Image image = new Image(null,new StreamResource(streamSource,"streamedSourceFromByteArray"));
        image.setWidth(200, Unit.PIXELS);
        image.setHeight(150, Unit.PIXELS);
        addComponent(image);

        MyButton button = new MyButton(content.getName());
        button.setData(content.getData());
        button.setStyleName("link");
        addComponent(button);
    }
}
