package edu.unc.comp301.view;

import edu.unc.comp301.controller.Controller;
import edu.unc.comp301.model.Model;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class PathChoiceView implements FXComponent {
  private final Controller ctrl;
  private final Model model;

  public PathChoiceView(Controller c, Model m) {
    this.ctrl = c;
    this.model = m;
  }

  @Override
  public Parent render() {
    HBox root = new HBox(20);
    root.setAlignment(Pos.CENTER);

    Text prompt = new Text("Choose your path:");
    prompt.setFont(Font.font(24));

    Button left = new Button("Left Door");
    left.setOnAction(e -> ctrl.choosePathLeft());
    Button right = new Button("Right Door");
    right.setOnAction(e -> ctrl.choosePathRight());

    root.getChildren().addAll(prompt, left, right);
    return root;
  }
}
