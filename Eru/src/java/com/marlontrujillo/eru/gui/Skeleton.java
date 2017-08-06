package com.marlontrujillo.eru.gui;

import com.marlontrujillo.eru.gui.toolbars.tree.ProjectTree;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Created by mtrujillo on 8/4/17.
 */
public class Skeleton extends VBox{

    @FXML
    private ProjectTree projectTree;
    @FXML
    private AnchorPane mainPane;

    public Skeleton() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Skeleton.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public ProjectTree getProjectTree() {
        return projectTree;
    }

    public AnchorPane getMainPane() {
        return mainPane;
    }
}
