package com.uniyaz.views;

import com.uniyaz.components.MyButton;
import com.uniyaz.ui.LayoutUI;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;

public class HeaderLayout extends HorizontalLayout {

    public HeaderLayout() {

        setSizeFull();
        createMenu();

        MyButton homePage = new MyButton();
        homePage.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                ((LayoutUI) UI.getCurrent()).getMainLayout().buildMainLayout();
            }
        });
        homePage.setIcon(FontAwesome.HOME);
        addComponent(homePage);
    }


    private void createMenu() {

        final MenuBar.Command mycommand = selectedItem -> ((LayoutUI) UI.getCurrent()).getMainLayout().getBodyLayout().getContentLayout().ContentLayoutFillBy(selectedItem.getText());

        MenuBar itemMenu = new MenuBar();
        itemMenu.setSizeUndefined();
        addComponent(itemMenu);
        setComponentAlignment(itemMenu,Alignment.TOP_LEFT);

        MenuBar.MenuItem category = itemMenu.addItem("Category", null, null);
        MenuBar.MenuItem addCategory = category.addItem("Add Category", null, mycommand);
        MenuBar.MenuItem listCategories = category.addItem("List Categories", null, mycommand);

        MenuBar.MenuItem content = itemMenu.addItem("Content", null, null);
        MenuBar.MenuItem addContent = content.addItem("Add Content", null, mycommand);
        MenuBar.MenuItem listContents = content.addItem("List Contents", null, mycommand);
    }
}