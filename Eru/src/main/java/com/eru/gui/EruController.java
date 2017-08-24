package com.eru.gui;

import com.eru.entities.Project;
import com.eru.entities.TreeElementsGroup;
import com.eru.gui.about.About;
import com.eru.gui.preferences.EruPreferences;
import com.eru.persistence.ProjectLoaderService;
import com.eru.persistence.ProjectSaverService;
import com.eru.util.TagLinksManager;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.extern.log4j.Log4j;

/**
 * Created by mtrujillo on 8/23/17.
 */
@Log4j
public class EruController {

    public enum ScadaAction {
        ACTIVATE_TAGS,
        DEACTIVATE_TAGS
    }

    public enum DBAction {
        LOAD,
        SAVE
    }

    public enum PopupAction {
        SHOW_PREFERENCES,
        SHOW_ABOUT
    }

    private final SimpleObjectProperty<TreeElementsGroup> selectedTreeItemProperty
            = new SimpleObjectProperty<>(null);

    private final SimpleObjectProperty<Project> project
            = new SimpleObjectProperty<>();

    private final TagLinksManager tagLinksManager = new TagLinksManager();

    public EruController() {
    }

    public void performScadaAction(ScadaAction scadaAction){
        switch (scadaAction) {
            case ACTIVATE_TAGS:
                tagLinksManager.setTags(project.get().getTags());
                project.get().getTags().forEach(tagLinksManager::installLink);
                break;
            case DEACTIVATE_TAGS:
                project.get().getTags().forEach(tagLinksManager::removeLink);
                break;
        }
    }

    public void performPopupAction(PopupAction popupAction){
        switch (popupAction) {
            case SHOW_PREFERENCES:
                Stage preferencesStage = new Stage();
                preferencesStage.setScene(new Scene(new EruPreferences()));
                preferencesStage.showAndWait();
                break;
            case SHOW_ABOUT:
                Stage aboutStage = new Stage();
                aboutStage.setScene(new Scene(new About()));
                aboutStage.showAndWait();
                break;
        }
    }

    public void performDBAction(DBAction dbAction){
        switch (dbAction) {
            case LOAD:
                final Stage plsStage            = new Stage(StageStyle.TRANSPARENT);
                final Preloader preloaderWindow = new Preloader();
                final ProjectLoaderService pls  = new ProjectLoaderService();

                preloaderWindow.getProgressBar().progressProperty().bind(pls.progressProperty());
                preloaderWindow.getStatusLabel().textProperty().bind(pls.messageProperty());
                pls.setOnSucceeded(event -> {
                    this.project.set((Project) event.getSource().getValue());
                    plsStage.close();
                });
                pls.start();
                plsStage.setScene(new Scene(preloaderWindow));
                plsStage.show();
                break;
            case SAVE:
                final Alert alertWindow = new Alert(Alert.AlertType.INFORMATION);
                ProjectSaverService pss = new ProjectSaverService();
                alertWindow.setContentText(null);
                alertWindow.headerTextProperty().bind(pss.messageProperty());
                pss.setProject(this.project.get());
                pss.setOnSucceeded(event -> {
                    this.project.set((Project) event.getSource().getValue());
                    alertWindow.close();
                });
                pss.start();
                alertWindow.show();
                break;
        }
    }

    public Project getProject() {
        return project.get();
    }

    public SimpleObjectProperty<Project> projectProperty() {
        return project;
    }

    public void setProject(Project project) {
        this.project.set(project);
    }

    public TreeElementsGroup getSelectedTreeItemProperty() {
        return selectedTreeItemProperty.get();
    }

    public SimpleObjectProperty<TreeElementsGroup> selectedTreeItemProperty() {
        return selectedTreeItemProperty;
    }

    public void setSelectedTreeItemProperty(TreeElementsGroup selectedTreeItemProperty) {
        this.selectedTreeItemProperty.set(selectedTreeItemProperty);
    }
}