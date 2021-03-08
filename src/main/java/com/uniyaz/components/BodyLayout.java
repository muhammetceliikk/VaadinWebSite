package com.uniyaz.components;

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

    public void setSideBarLayout(SideBarLayout sideBarLayout) {
        this.sideBarLayout = sideBarLayout;
    }

    public ContentLayout getContentLayout() {
        return contentLayout;
    }

    public void setContentLayout(ContentLayout contentLayout) {
        this.contentLayout = contentLayout;
    }

}
