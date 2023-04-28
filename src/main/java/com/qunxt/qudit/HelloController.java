package com.qunxt.qudit;

import com.jfoenix.controls.JFXTabPane;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.TextAlignment;

public class HelloController {
    @FXML
    private JFXTabPane tabContainer;

    @FXML
    private Tab userProfileTab;

    @FXML
    private Tab settingsTab;

    @FXML
    private Tab logoutTab;

    /// 2.
    private double tabWidth = 90.0;
    public static int lastSelectedTabIndex = 0;

//    private void configureTabPane() {
//        /// 3.
//        tabContainer.setTabMinWidth(tabWidth);
//        tabContainer.setTabMaxWidth(tabWidth);
//        tabContainer.setTabMinHeight(tabWidth);
//        tabContainer.setTabMaxHeight(tabWidth);
//        tabContainer.setRotateGraphic(true);
//
//        /// 4.
//        configureTab(userProfileTab, "User\nProfile", "C:\\Users\\himan\\IdeaProjects\\Qudit\\src\\main\\resources\\images\\user-profile.png", replaceBackgroundColorHandler);
//        configureTab(settingsTab, "Settings", "C:\\Users\\himan\\IdeaProjects\\Qudit\\src\\main\\resources\\images\\settings.png", replaceBackgroundColorHandler);
//        configureTab(logoutTab, "Logout", "C:\\Users\\himan\\IdeaProjects\\Qudit\\src\\main\\resources\\images\\logout.png", logoutHandler);
//    }
//
//    private void configureTab(Tab tab, String title, String iconPath, EventHandler<Event> onSelectionChangedEvent) {
//        double imageWidth = 40.0;
//
//        /// 5.
//        ImageView imageView = new ImageView(new Image(iconPath));
//        imageView.setFitHeight(imageWidth);
//        imageView.setFitWidth(imageWidth);
//
//        Label label = new Label(title);
//        label.setMaxWidth(tabWidth - 20);
//        label.setPadding(new Insets(5, 0, 0, 0));
//        label.setStyle("-fx-text-fill: black; -fx-font-size: 8pt; -fx-font-weight: normal;");
//        label.setTextAlignment(TextAlignment.CENTER);
//
//        BorderPane tabPane = new BorderPane();
//        tabPane.setRotate(90.0);
//        tabPane.setMaxWidth(tabWidth);
//        tabPane.setCenter(imageView);
//        tabPane.setBottom(label);
//
//        /// 6.
//        tab.setText("");
//        tab.setGraphic(tabPane);
//        tab.setOnSelectionChanged(onSelectionChangedEvent);
//    }
//    /// 7.
//    EventHandler<Event> replaceBackgroundColorHandler = event -> {
//        lastSelectedTabIndex = tabContainer.getSelectionModel().getSelectedIndex();
//
//        Tab currentTab = (Tab) event.getTarget();
//        if (currentTab.isSelected()) {
//            currentTab.setStyle("-fx-background-color: -fx-focus-color;");
//        } else {
//            currentTab.setStyle("-fx-background-color: -fx-accent;");
//        }
//    };
//
//    /// 8.
//    EventHandler<Event> logoutHandler = event -> {
//        Tab currentTab = (Tab) event.getTarget();
//        if (currentTab.isSelected()) {
//            tabContainer.getSelectionModel().select(lastSelectedTabIndex);
//
//            // TODO: logout action
//            // good place to show Dialog window with Yes / No question
//            System.out.println("Logging out!");
//        }
//    };

//    @FXML
//    private Label welcomeText;
//
//    @FXML
//    protected void onHelloButtonClick() {
//        welcomeText.setText("Welcome to JavaFX Application!");
//    }
}