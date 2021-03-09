package com.uniyaz.components;

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
        headerLayout.addStyleName("layout-with-border");

        addComponent(headerLayout);
        setExpandRatio(headerLayout,1f);

        bodyLayout=new BodyLayout();
        addComponent(bodyLayout);
        setExpandRatio(bodyLayout,8f);

        footerLayout = new FooterLayout();
        addStyleName("layout-with-border");

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
