package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MessageView implements FXComponent {
  ClassicMvcController controller;
  Model model;

  public MessageView(ClassicMvcController controller, Model model) {
    this.controller = controller;
    this.model = model;
  }

  @Override
  public Parent render() {
    VBox vbox = new VBox();
    vbox.setSpacing(5);
    Button reset = new Button("Reset board");
    reset.setOnAction(
        (ActionEvent e) -> {
          controller.clickResetPuzzle();
        });
    vbox.getChildren().add(reset);
    Label msg = new Label(determineLabel());

    msg.getStyleClass().add("msg");
    vbox.getChildren().add(msg);
    return vbox;
  }

  private String determineLabel() {

    if (model.isSolved()) {
      return "Puzzle is solved! Stupendous!!";
    } else {
      return "";
    }
  }
}
