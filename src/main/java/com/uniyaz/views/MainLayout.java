package com.uniyaz.views;

import com.vaadin.ui.VerticalLayout;

public class MainLayout extends VerticalLayout {

    private HeaderLayout headerLayout;
    private BodyLayout bodyLayout;
    private FooterLayout footerLayout;

    public MainLayout(){
        setSizeFull();

        buildMainLayout();
    }

    public void buildMainLayout() {
        removeAllComponents();

        headerLayout = new HeaderLayout();
        addComponent(headerLayout);
        setExpandRatio(headerLayout,1f);

        bodyLayout=new BodyLayout();
        addComponent(bodyLayout);
        setExpandRatio(bodyLayout,8f);

        footerLayout = new FooterLayout();
        addComponent(footerLayout);
        setExpandRatio(footerLayout,1f);
    }

    public BodyLayout getBodyLayout() {
        return bodyLayout;
    }

    public void setBodyLayout(BodyLayout bodyLayout) {
        this.bodyLayout = bodyLayout;
    }
}
