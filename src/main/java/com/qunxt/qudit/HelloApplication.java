package com.qunxt.qudit;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.event.EventHandler;
import javafx.scene.control.*;

import java.awt.image.BufferedImage;
import java.io.*;

import java.util.*;

import javafx.util.Callback;
import org.apache.commons.lang3.StringUtils;

import javax.swing.filechooser.FileSystemView;

public class HelloApplication extends Application {
    enum TAB {
        PROJECT ("Project"),
        BOOKMARK ("Bookmark"),
        STRUCTURE ("Structure");

        private String value;
        TAB(String value){
            this.value = value;
        }
    }
    DirectoryChooser directoryChooser = new DirectoryChooser();

    private Tab projectTab;
    private Tab bookmarkTab;
    private Tab structureTab;

    static Map<String, File> nameToFile = new HashMap<>();
    static List<String> ls = new ArrayList<>();

    VBox buildListView(File[] files) {
        ListView<String> listView = new ListView<>();

        Arrays.stream(files).forEach(file -> {
            try {
                nameToFile.put(file.getName(), file);
                ls.add(file.getName());
            } catch (Exception e) {
            }
        });
        ObservableList<String> data = FXCollections.observableList(ls);

        listView.setMinHeight(800);
        listView.setMaxWidth(200);

        //Creating the layout
        VBox layout = new VBox();
        layout.getChildren().addAll(listView);
        VBox.setVgrow(listView, Priority.ALWAYS);

        listView.setItems(data);
        listView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> list) {
                return new AttachmentListCell();
            }
        });

        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("clicked on " + listView.getSelectionModel().getSelectedItem());
                File selectedFile = nameToFile.getOrDefault(listView.getSelectionModel().getSelectedItem(), null);
                if(selectedFile == null)    return;

                if(selectedFile.isDirectory()) {
                    int idx = ls.indexOf(selectedFile.getName());
                    List<String> tlist = new ArrayList<>();
                    Arrays.stream(selectedFile.listFiles()).sequential().forEach(file -> {
                        tlist.add(file.getName());
                        nameToFile.put(file.getName() ,file);
                    });
                    ls.addAll(idx+1, tlist);
                    listView.setItems(FXCollections.observableList(ls));
                }
            }
        });
        return layout;
    }

    private static class AttachmentListCell extends ListCell<String> {
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
                setText(null);
            } else {
                Image fxImage = getFileIcon(item);
                ImageView imageView = new ImageView(fxImage);
                setGraphic(imageView);
                setText(item);
            }
        }
    }

    static HashMap<String, Image> mapOfFileExtToSmallIcon = new HashMap<String, Image>();

    private static String getFileExt(String fname) {
        String ext = ".";
        int p = fname.lastIndexOf('.');
        if (p >= 0) {
            ext = fname.substring(p);
        }
        return ext.toLowerCase();
    }

    private static javax.swing.Icon getJSwingIconFromFileSystem(File file) {
        FileSystemView view = FileSystemView.getFileSystemView();
        return view.getSystemIcon(file);
    }

    private static Image getFileIcon(String fname) {
        final String ext = getFileExt(fname);
        Image fileIcon = mapOfFileExtToSmallIcon.get(ext);

        if (fileIcon == null) {
            File file = nameToFile.getOrDefault(fname, new File(fname));
            javax.swing.Icon jswingIcon = getJSwingIconFromFileSystem(file);

            if (jswingIcon != null) {
                fileIcon = jswingIconToImage(jswingIcon);
                mapOfFileExtToSmallIcon.put(ext, fileIcon);
            }
        }

        return fileIcon;
    }

    private static Image jswingIconToImage(javax.swing.Icon jswingIcon) {
        BufferedImage bufferedImage = new BufferedImage(jswingIcon.getIconWidth(), jswingIcon.getIconHeight(),
                BufferedImage.TYPE_INT_ARGB);
        jswingIcon.paintIcon(null, bufferedImage.getGraphics(), 0, 0);
        return SwingFXUtils.toFXImage(bufferedImage, null);
    }

    public MenuBar buildMenuBar(Stage stage) {
        // create a menu
        Menu file = new Menu("File");

        // create menuitems
        MenuItem m1 = new MenuItem("New");
        MenuItem m2 = new MenuItem("Open");
        m2.setOnAction(e -> {
            File selectedDirectory = directoryChooser.showDialog(stage);
            File[] files = selectedDirectory.listFiles();
            projectTab.setContent(buildListView(files));
            System.out.println(selectedDirectory.getAbsolutePath());
        });

        MenuItem m3 = new MenuItem("Recent Projects");
        MenuItem m4 = new MenuItem("Close Projects");

        // add menu items to menu
        file.getItems().add(m1);
        file.getItems().add(m2);
        file.getItems().add(m3);
        file.getItems().add(m4);

        // create a menu
        Menu edit = new Menu("Edit");
        MenuItem e1 = new MenuItem("Edit");
        edit.getItems().add(e1);

        // create a menu
        Menu view = new Menu("View");
        MenuItem v1 = new MenuItem("View");
        view.getItems().add(v1);

        // create a menubar
        MenuBar mb = new MenuBar();
        // add menu to menubar
        mb.getMenus().add(file);
        mb.getMenus().add(edit);
        mb.getMenus().add(view);

        return mb;
    }
    EventHandler<Event> replaceBackgroundColorHandler = event -> {
        Tab currentTab = (Tab) event.getTarget();
        System.out.println(currentTab.getText()+"" +currentTab.isSelected());
    };
    public Tab buildTab(TAB tabType) {
        Tab tab = new Tab();
        tab.setClosable(false);
                                
        tab.setText(tabType.value);
        switch (tabType) {
            case PROJECT:
                tab.setOnSelectionChanged(replaceBackgroundColorHandler);
//                tab.setContent(new Rectangle(200, 800, Color.DARKGRAY));
                break;
            case BOOKMARK:
                tab.setOnSelectionChanged(replaceBackgroundColorHandler);
//                tab.setContent(new Rectangle(200, 800, Color.DARKGRAY));
                break;
            case STRUCTURE:
                tab.setOnSelectionChanged(replaceBackgroundColorHandler);
//                tab.setContent(new Rectangle(200, 800, Color.DARKGRAY));
                break;
        }
        return tab;
    }
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Qudit");

//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
//        Parent parent = fxmlLoader.load();

//         create a VBox
        VBox vb = new VBox(buildMenuBar(stage));

        TabPane tabPane = new TabPane();
        tabPane.setSide(Side.LEFT);

        projectTab = buildTab(TAB.PROJECT);
        bookmarkTab = buildTab(TAB.BOOKMARK);
        structureTab = buildTab(TAB.STRUCTURE);

        tabPane.getTabs().add(projectTab);
        tabPane.getTabs().add(bookmarkTab);
        tabPane.getTabs().add(structureTab);

        vb.getChildren().add(tabPane);
        Scene scene = new Scene(vb, 480, 500);
//        Scene scene = new Scene(parent, 480, 500);

        stage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("/styles/global.css").toExternalForm());

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}