package com.uniyaz.views;

import com.vaadin.ui.HorizontalSplitPanel;

public class BodyLayout extends HorizontalSplitPanel {
    private SideBarLayout sideBarLayout;
    private ContentLayout contentLayout;

    public BodyLayout() {
        setSizeFull();

        setSplitPosition(8f);

        sideBarLayout=new SideBarLayout();
        setFirstComponent(sideBarLayout);

        contentLayout=new ContentLayout();
        setSecondComponent(contentLayout);
    }

    public SideBarLayout getSideBarLayout() {
        return sideBarLayout;
    }

    public ContentLayout getContentLayout() {
        return contentLayout;
    }

}
