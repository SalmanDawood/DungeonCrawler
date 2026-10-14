package edu.unc.comp301.view;

import edu.unc.comp301.controller.Controller;
import edu.unc.comp301.model.Model;
import edu.unc.comp301.model.Observer;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;

public class View implements FXComponent, Observer {

  private final Controller controller;
  private final Model model;
  private final Stage stage;
  private final double width;
  private final double height;

  private final TitleScreenView titleView;
  private final GameView gameView;
  private final PathChoiceView pathView;

  public View(Controller controller, Model model, Stage stage, double width, double height) {
    this.controller = controller;
    this.model = model;
    this.stage = stage;
    this.width = width;
    this.height = height;

    this.titleView = new TitleScreenView(controller, model);
    this.gameView = new GameView(controller, model);
    this.pathView = new PathChoiceView(controller, model);

    model.addObserver(this);

    update();
  }

  @Override
  public Parent render() {
    Model.STATUS st = model.getStatus();
    if (st == Model.STATUS.END_GAME) {
      return titleView.render();
    } else if (st == Model.STATUS.CHOOSE_PATH) {
      return pathView.render();
    } else {
      return gameView.render();
    }
  }

  @Override
  public void update() {
    Parent root = render();
    Scene scene = new Scene(root, width, height);
    scene.getStylesheets().add(getClass().getResource("/dungeon.css").toExternalForm());

    scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
      if (model.getStatus() != Model.STATUS.IN_PROGRESS) {
        return;
      }

      switch (event.getCode()) {
        case UP:
        case W:
          controller.moveUp();
          break;
        case DOWN:
        case S:
          controller.moveDown();
          break;
        case LEFT:
        case A:
          controller.moveLeft();
          break;
        case RIGHT:
        case D:
          controller.moveRight();
          break;
        default:
          return;
      }

      event.consume();
    });


    stage.setScene(scene);
    stage.show();
  }
}
