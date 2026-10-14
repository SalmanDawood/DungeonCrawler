package edu.unc.comp301.view;

import edu.unc.comp301.controller.Controller;
import edu.unc.comp301.model.Model;
import edu.unc.comp301.model.ModelImpl;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class TitleScreenView implements FXComponent {

  private final Controller controller;
  private final Model model;

  public TitleScreenView(Controller controller, Model model) {
    this.controller = controller;
    this.model = model;
  }

  @Override
  public Parent render() {
    VBox root = new VBox(20);
    root.setAlignment(Pos.CENTER);
    root.setStyle(
      model.getTheme() == ModelImpl.Theme.LIGHT
        ? "-fx-background-color: lightgray;"
        : "-fx-background-color: linear-gradient(to bottom, #222, #555);");
    root.setPadding(new Insets(40));

    Label title = new Label("DUNGEON CRAWLER");
    title.setFont(Font.font("Arial", FontWeight.BOLD, 48));
    title.setTextFill(model.getTheme() == ModelImpl.Theme.LIGHT ? Color.DARKSLATEGRAY : Color.GOLD);

    Label hiScore = new Label("High Score: " + model.getHighScore());
    hiScore.setFont(Font.font(24));
    hiScore.setTextFill(Color.WHITE);

    Label lastScore = new Label("Last Score: " + model.getCurScore());
    lastScore.setFont(Font.font(24));
    lastScore.setTextFill(Color.WHITE);

    ToggleGroup tg = new ToggleGroup();

    RadioButton easy = new RadioButton("Easy");
    easy.setToggleGroup(tg);
    easy.setTextFill(model.getTheme() == ModelImpl.Theme.DARK ? Color.WHITE : Color.DARKSLATEGRAY);
    easy.setOnAction(e -> controller.setHardMode(false));

    RadioButton hard = new RadioButton("Hard");
    hard.setToggleGroup(tg);
    hard.setTextFill(model.getTheme() == ModelImpl.Theme.DARK ? Color.WHITE : Color.DARKSLATEGRAY);
    hard.setOnAction(e -> controller.setHardMode(true));

    Button start = new Button("Start New Game");
    start.setFont(Font.font(20));
    start.setPadding(new Insets(10, 20, 10, 20));
    start.setOnAction(e -> controller.startGame());

    ToggleButton themeBtn = new ToggleButton("☀/🌙");
    themeBtn.setOnAction(e -> controller.toggleTheme());

    Label credit = new Label("By Salman");
    credit.setFont(Font.font(14));
    credit.setTextFill(
      model.getTheme() == ModelImpl.Theme.LIGHT ? Color.DARKSLATEGRAY : Color.LIGHTGRAY);

    root.getChildren()
      .addAll(title, hiScore, lastScore, new HBox(10, easy, hard), start, themeBtn, credit);
    return root;
  }
}
