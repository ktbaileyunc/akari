package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.ModelObserver;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class View implements FXComponent, ModelObserver {
  private Stage stage;
  private ControlView control;
  private MessageView message;
  private PuzzleView puzzle;

  public View(Model model, ClassicMvcController controller, Stage stage) {
    this.stage = stage;
    control = new ControlView(controller, model);
    message = new MessageView(controller, model);
    puzzle = new PuzzleView(controller, model);
    model.addObserver(this);
  }

  @Override
  public void update(Model model) {
    Scene scene = new Scene(this.render(), 550, 550);
    scene.getStylesheets().add("main.css");
    stage.setScene(scene);
  }

  @Override
  public Parent render() {
    VBox view = new VBox();
    view.setSpacing(10);
    view.getChildren().add(control.render());
    view.getChildren().add(puzzle.render());
    view.getChildren().add(message.render());

    return view;
  }
}
