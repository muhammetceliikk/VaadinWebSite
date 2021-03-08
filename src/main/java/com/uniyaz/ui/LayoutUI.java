package com.uniyaz.ui;

import com.uniyaz.components.BodyLayout;
import com.uniyaz.components.FooterLayout;
import com.uniyaz.components.HeaderLayout;
import com.uniyaz.components.MainLayout;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import sun.applet.Main;

@Theme("mytheme")
@Widgetset("com.uniyaz.MyAppWidgetset")
public class LayoutUI extends UI {
    MainLayout mainLayout;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        buildMainLayout();
        setContent(mainLayout);
    }

    private void buildMainLayout() {

        final Page.Styles styles = Page.getCurrent().getStyles();

        String css = ".layout-with-border {\n" +
                "    border: 1px solid black;\n" +
                "}";

        styles.add(css);

        mainLayout = new MainLayout();
    }

    public MainLayout getMainLayout() {
        return mainLayout;
    }

    public void setMainLayout(MainLayout mainLayout) {
        this.mainLayout = mainLayout;
    }
}