package edu.unc.comp301.view;

import edu.unc.comp301.controller.Controller;
import edu.unc.comp301.controller.ControllerImpl;
import edu.unc.comp301.model.Model;
import edu.unc.comp301.model.ModelImpl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppLauncher extends Application {
  private static final int WINDOW_WIDTH = 600;
  private static final int WINDOW_HEIGHT = 700;

  @Override
  public void start(Stage stage) {
    stage.setTitle("Salman's Dungeon Crawler");

    Model model = new ModelImpl(10, 10);
    Controller controller = new ControllerImpl(model);
    View view = new View(controller, model, stage, WINDOW_WIDTH, WINDOW_HEIGHT);

    Scene scene = new Scene(view.render(), WINDOW_WIDTH, WINDOW_HEIGHT);
    scene.getStylesheets().add("dungeon.css");
    stage.setScene(scene);
    stage.show();
  }
}
