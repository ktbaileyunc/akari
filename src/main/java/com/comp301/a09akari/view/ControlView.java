package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;

public class ControlView implements FXComponent {
  private ClassicMvcController controller;
  private Model model;

  public ControlView(ClassicMvcController controller, Model model) {
    this.controller = controller;
    this.model = model;
  }

  @Override
  public Parent render() {
    VBox layout = new VBox();
    layout.setSpacing(5);
    layout.getStyleClass().add("top");
    Label title = new Label("Akari");
    title.getStyleClass().add("title");
    layout.getChildren().add(title);

    Label puzzleNum =
        new Label(
            "Puzzle " + (model.getActivePuzzleIndex() + 1) + " of " + model.getPuzzleLibrarySize());
    puzzleNum.getStyleClass().add("puzzle-num");
    layout.getChildren().add(puzzleNum);

    HBox buttons = new HBox();
    buttons.setSpacing(10);
    Button prev = new Button("Previous");
    prev.setOnAction(
        (ActionEvent e) -> {
          controller.clickPrevPuzzle();
        });
    buttons.getChildren().add(prev);
    Button random = new Button("Random Puzzle");
    random.setOnAction(
        (ActionEvent e) -> {
          controller.clickRandPuzzle();
        });
    buttons.getChildren().add(random);
    Button next = new Button("Next");
    next.setOnAction(
        (ActionEvent e) -> {
          controller.clickNextPuzzle();
        });
    buttons.getChildren().add(next);
    layout.getChildren().add(buttons);
    return layout;
  }
}
