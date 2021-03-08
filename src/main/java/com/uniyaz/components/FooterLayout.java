package com.uniyaz.components;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class FooterLayout extends VerticalLayout {

    public FooterLayout() {

        setSizeFull();

        Label label = new Label("@COPYRIGHT 2021");
        label.setSizeUndefined();
        label.addStyleName(ValoTheme.LABEL_LARGE);
        label.addStyleName(ValoTheme.LABEL_BOLD);
        label.addStyleName(ValoTheme.LABEL_H4);
        addComponent(label);

        setComponentAlignment(label, Alignment.MIDDLE_CENTER);
    }
}