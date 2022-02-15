package hu.desktop;

import hu.desktop.controller.StartPageController;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class SceneManager {

    private static Stage mainWindow;

    private static Scene actualScene;

    private static final Map<String, Scene> scenes = new HashMap<>();

    public static void addScene(String sceneId, Scene scene){
        scenes.put(sceneId, scene);
    }

    public static void setActualScene(String sceneId){
        SceneManager.actualScene = scenes.get(sceneId);
        SceneManager.mainWindow.setScene(SceneManager.actualScene);

        if(sceneId == "start_page"){
            SceneManager.mainWindow.setTitle("Kezdőlap");
            StartPageController.updateItems();
        }
        else{
            SceneManager.mainWindow.setTitle("Sakk");
        }
    }

    public static void init(Stage mainWindow){
        SceneManager.mainWindow = mainWindow;
    }
}