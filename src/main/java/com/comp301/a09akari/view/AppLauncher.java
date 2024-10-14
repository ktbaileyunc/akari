package com.comp301.a09akari.view;

import com.comp301.a09akari.SamplePuzzles;
import com.comp301.a09akari.controller.*;
import com.comp301.a09akari.model.*;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class AppLauncher extends Application {
  @Override
  public void start(Stage stage) {
    PuzzleLibrary library = new PuzzleLibraryImpl();
    List<Puzzle> puzzleList = new ArrayList<>();
    puzzleList.add(new PuzzleImpl(SamplePuzzles.PUZZLE_01));
    puzzleList.add(new PuzzleImpl(SamplePuzzles.PUZZLE_02));
    puzzleList.add(new PuzzleImpl(SamplePuzzles.PUZZLE_03));
    puzzleList.add(new PuzzleImpl(SamplePuzzles.PUZZLE_04));
    puzzleList.add(new PuzzleImpl(SamplePuzzles.PUZZLE_05));
    for (Puzzle p : puzzleList) {
      library.addPuzzle(p);
    }

    Model model = new ModelImpl(library);
    ClassicMvcController controller = new ControllerImpl(model);
    View view = new View(model, controller, stage);

    view.update(model);

    stage.setTitle("Akari");
    stage.show();
  }
}
