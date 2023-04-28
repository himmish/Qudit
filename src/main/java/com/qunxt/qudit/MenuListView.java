package com.qunxt.qudit;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import javax.swing.filechooser.FileSystemView;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

import static com.qunxt.qudit.ScreenUtil.getMenuWidth;
import static com.qunxt.qudit.ScreenUtil.screenHeight;
import static com.qunxt.qudit.TextAreaView.loadFileToTextArea;

public class MenuListView {

    static Map<String, File> nameToFile = new HashMap<>();
    static List<String> ls = new ArrayList<>();

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

        listView.setMinHeight(screenHeight);
        listView.setMaxWidth(getMenuWidth());

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
                else {
                    loadFileToTextArea(selectedFile);
                }
            }
        });
        return layout;
    }
}
