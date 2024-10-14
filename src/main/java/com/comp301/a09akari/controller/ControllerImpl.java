package com.comp301.a09akari.controller;

import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Model;

public class ControllerImpl implements ClassicMvcController {
  Model model;

  public ControllerImpl(Model model) {
    this.model = model;
  }

  @Override
  public void clickNextPuzzle() {
    int index = model.getActivePuzzleIndex();
    if (index + 1 < model.getPuzzleLibrarySize()) {
      model.setActivePuzzleIndex(index + 1);
    }
  }

  @Override
  public void clickPrevPuzzle() {
    int index = model.getActivePuzzleIndex();
    if (index - 1 >= 0) {
      model.setActivePuzzleIndex(index - 1);
    }
  }

  @Override
  public void clickRandPuzzle() {
    int index = model.getActivePuzzleIndex();
    int randomIndex = (int) (Math.random() * model.getPuzzleLibrarySize());
    while (index == randomIndex) {
      randomIndex = (int) (Math.random() * model.getPuzzleLibrarySize());
    }
    model.setActivePuzzleIndex(randomIndex);
  }

  @Override
  public void clickResetPuzzle() {
    model.resetPuzzle();
  }

  @Override
  public void clickCell(int r, int c) {
    if (model.getActivePuzzle().getCellType(r, c) == CellType.CORRIDOR) {
      if (model.isLamp(r, c)) {
        model.removeLamp(r, c);
      } else {
        model.addLamp(r, c);
      }
    }
  }
}
