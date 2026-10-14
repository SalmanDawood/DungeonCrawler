package edu.unc.comp301.view;

import edu.unc.comp301.controller.Controller;
import edu.unc.comp301.model.Model;
import edu.unc.comp301.model.ModelImpl;
import edu.unc.comp301.model.board.Posn;
import edu.unc.comp301.model.pieces.Piece;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.IOException;
import java.io.InputStream;

public class GameView implements FXComponent {

  private final Controller controller;
  private final Model model;

  private static final int CELL_SIZE = 50;

  public GameView(Controller controller, Model model) {
    this.controller = controller;
    this.model = model;
  }

  @Override
  public Parent render() {
    BorderPane root = new BorderPane();
    root.setStyle(
      model.getTheme() == ModelImpl.Theme.LIGHT
        ? "-fx-background-color: beige;"
        : "-fx-background-color: black;");

    GridPane grid = new GridPane();

    grid.setGridLinesVisible(true);

    for (int r = 0; r < model.getHeight(); r++) {
      for (int c = 0; c < model.getWidth(); c++) {
        Posn pos = new Posn(r, c);
        Piece piece = model.get(pos);
        ImageView iv = new ImageView();
        iv.setFitWidth(CELL_SIZE);
        iv.setFitHeight(CELL_SIZE);

        if (piece != null) {
          try (InputStream is = getClass().getResourceAsStream("/" + piece.getResourcePath())) {
            if (is != null) {
              iv.setImage(new Image(is));
            } else {
              System.err.println("Could not load image: " + piece.getResourcePath());
            }
          } catch (IOException ex) {
            ex.printStackTrace();
          }
        }

        grid.add(iv, c, r);
      }
    }

    root.setCenter(grid);

    Label scoreLabel = new Label("Score: " + model.getCurScore());
    scoreLabel.setFont(Font.font(20));
    scoreLabel.setTextFill(Color.WHITE);
    scoreLabel.setPadding(new Insets(10));
    BorderPane.setAlignment(scoreLabel, Pos.CENTER);
    root.setTop(scoreLabel);

    GridPane controls = new GridPane();
    controls.setHgap(5);
    controls.setVgap(5);
    controls.setPadding(new Insets(10));
    controls.setAlignment(Pos.CENTER);

    Button up = new Button("↑");
    up.setOnAction(e -> controller.moveUp());
    Button down = new Button("↓");
    down.setOnAction(e -> controller.moveDown());
    Button left = new Button("←");
    left.setOnAction(e -> controller.moveLeft());
    Button right = new Button("→");
    right.setOnAction(e -> controller.moveRight());

    controls.add(up, 1, 0);
    controls.add(left, 0, 1);
    controls.add(down, 1, 1);
    controls.add(right, 2, 1);

    Label mech = new Label("✨ Portals teleport you randomly!");
    mech.setFont(Font.font(14));
    mech.setTextFill(Color.LIGHTGREEN);

    ToggleButton themeBtn = new ToggleButton("☀/🌙");
    themeBtn.setOnAction(e -> controller.toggleTheme());

    VBox bottom = new VBox(5, controls, mech, themeBtn);
    bottom.setAlignment(Pos.CENTER);
    root.setBottom(bottom);

    return root;
  }
}