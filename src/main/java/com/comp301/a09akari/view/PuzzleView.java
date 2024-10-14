package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Model;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class PuzzleView implements FXComponent {

  private Model model;
  private ClassicMvcController controller;

  public PuzzleView(ClassicMvcController controller, Model model) {
    this.model = model;
    this.controller = controller;
  }

  @Override
  public Parent render() {
    HBox vbox = new HBox();
    vbox.setPrefHeight(350);
    vbox.setPrefWidth(550);
    GridPane layout = new GridPane();
    layout.getStyleClass().add("board");
    for (int r = 0; r < model.getActivePuzzle().getHeight(); r++) {
      for (int c = 0; c < model.getActivePuzzle().getWidth(); c++) {
        layout.add(makeCell(r, c), r, c);
      }
    }
    vbox.getChildren().add(layout);
    return vbox;
  }

  private Button makeCell(int r, int c) {
    Button cell;
    if (model.getActivePuzzle().getCellType(r, c) == CellType.WALL) {
      cell = new Button();
      cell.getStyleClass().add("wall");
    } else if (model.getActivePuzzle().getCellType(r, c) == CellType.CLUE) {
      cell = new Button(String.valueOf(model.getActivePuzzle().getClue(r, c)));
      cell.getStyleClass().add("wall");
    } else {
      if (model.isLamp(r, c)) {
        cell = new Button("*");
        cell.getStyleClass().add("lit");
        if (model.isLampIllegal(r, c)) {
          cell.getStyleClass().add("bad-lamp");
        }
      } else if (model.isLit(r, c)) {
        cell = new Button();
        cell.getStyleClass().add("lit");
      } else {
        cell = new Button();
        cell.getStyleClass().add("not-lit");
      }
    }
    cell.getStyleClass().add("cell");
    cell.setOnAction(
        (ActionEvent e) -> {
          controller.clickCell(r, c);
        });
    return cell;
  }
}
