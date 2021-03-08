package com.uniyaz.components;

import com.uniyaz.ui.LayoutUI;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class HeaderLayout extends HorizontalLayout {

    public HeaderLayout() {

        setSizeFull();
        createMenu();
    }


    private void createMenu() {

        final MenuBar.Command mycommand = selectedItem -> ((LayoutUI) UI.getCurrent()).getMainLayout().getBodyLayout().getContentLayout().ContentLayoutFillBy(selectedItem.getText());

        MenuBar itemMenu = new MenuBar();
        itemMenu.setSizeUndefined();
        addComponent(itemMenu);
        setComponentAlignment(itemMenu,Alignment.TOP_LEFT);

        MenuBar.MenuItem category = itemMenu.addItem("Category", null, null);
        MenuBar.MenuItem addCategory = category.addItem("Add Category", null, mycommand);
        MenuBar.MenuItem deleteCategory = category.addItem("Delete Category", null, mycommand);

        MenuBar.MenuItem content = itemMenu.addItem("Content", null, null);
        MenuBar.MenuItem addContent = content.addItem("Add Content", null, mycommand);
        MenuBar.MenuItem deleteContent = content.addItem("Delete Content", null, mycommand);

    }
}