package com.comp301.a09akari.model;

import java.util.ArrayList;
import java.util.List;

public class ModelImpl implements Model {
  int activePuzzleIndex;
  Puzzle activePuzzle;
  PuzzleLibrary library;
  int[][] lamps;
  List<ModelObserver> observers;

  public ModelImpl(PuzzleLibrary library) {
    this.library = library;
    activePuzzleIndex = 0;
    activePuzzle = library.getPuzzle(activePuzzleIndex);
    lamps = new int[activePuzzle.getHeight()][activePuzzle.getWidth()];
    this.observers = new ArrayList<>();
  }

  @Override
  public void addLamp(int r, int c) {
    if (activePuzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }
    if (lamps[r][c] != 1) {
      lamps[r][c] = 1;
      notifyObservers(this);
    }
  }

  @Override
  public void removeLamp(int r, int c) {
    if (activePuzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }
    if (lamps[r][c] != 0) {
      lamps[r][c] = 0;
      notifyObservers(this);
    }
  }

  @Override
  public boolean isLit(int r, int c) {
    if (activePuzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }
    if (this.isLamp(r, c)) {
      return true;
    }

    return checkForLamp(r, c);
  }

  @Override
  public boolean isLamp(int r, int c) {
    if (activePuzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }
    return (lamps[r][c] == 1);
  }

  @Override
  public boolean isLampIllegal(int r, int c) {
    if (!isLamp(r, c)) {
      throw new IllegalArgumentException();
    }
    return checkForLamp(r, c);
  }

  private boolean checkForLamp(int r, int c) {
    // check for wall/lamp btwn space and bottom
    for (int i = r + 1; i < activePuzzle.getHeight(); i++) {
      if (lamps[i][c] == 1) {
        return true;
      } else if (activePuzzle.getCellType(i, c) != CellType.CORRIDOR) {
        break; // nvm theres a wall/clue
      }
    }

    // check btwn space and top
    for (int i = r - 1; i >= 0; i--) {
      if (lamps[i][c] == 1) {
        return true;
      } else if (activePuzzle.getCellType(i, c) != CellType.CORRIDOR) {
        break;
      }
    }

    // check btwn space and left
    for (int i = c - 1; i >= 0; i--) {
      if (lamps[r][i] == 1) {
        return true;
      } else if (activePuzzle.getCellType(r, i) != CellType.CORRIDOR) {
        break;
      }
    }

    // check btwn space and right side
    for (int i = c + 1; i < activePuzzle.getWidth(); i++) {
      if (lamps[r][i] == 1) {
        return true;
      } else if (activePuzzle.getCellType(r, i) != CellType.CORRIDOR) {
        break;
      }
    }
    return false;
  }

  @Override
  public Puzzle getActivePuzzle() {
    return activePuzzle;
  }

  @Override
  public int getActivePuzzleIndex() {
    return activePuzzleIndex;
  }

  @Override
  public void setActivePuzzleIndex(int index) {
    activePuzzleIndex = index;
    activePuzzle = library.getPuzzle(index);
    lamps = new int[activePuzzle.getHeight()][activePuzzle.getWidth()];
    notifyObservers(this);
  }

  @Override
  public int getPuzzleLibrarySize() {
    return library.size();
  }

  @Override
  public void resetPuzzle() {
    for (int i = 0; i < lamps.length; i++) {
      for (int j = 0; j < lamps[0].length; j++) {
        lamps[i][j] = 0;
      }
    }
    notifyObservers(this);
  }

  @Override
  public boolean isSolved() {
    // algorithm to check that (1) all corridors in the board are lit,
    // (2) all clues are satisfied, and (3) no lamps are illegally placed.
    for (int r = 0; r < activePuzzle.getHeight(); r++) {
      for (int c = 0; c < activePuzzle.getWidth(); c++) {
        if (activePuzzle.getCellType(r, c) == CellType.CORRIDOR) {
          if (!this.isLit(r, c)) {
            return false; // corridor cell is lit
            // case lamp!
          } else if (this.isLamp(r, c)) {
            if (this.isLampIllegal(r, c)) {
              return false; // lamp cell is not illegal
            }
          }
        } else if (activePuzzle.getCellType(r, c) == CellType.CLUE) {
          if (!this.isClueSatisfied(r, c)) {
            return false; // clue cell is satisfied
          }
        }
      }
    }
    return true;
  }

  @Override
  public boolean isClueSatisfied(int r, int c) {
    if (activePuzzle.getCellType(r, c) != CellType.CLUE) {
      throw new IllegalArgumentException();
    }
    int clue = activePuzzle.getClue(r, c);
    int clueCount = 0;
    // r c+1
    if (c + 1 < activePuzzle.getWidth()) {
      if (lamps[r][c + 1] == 1) {
        clueCount++;
      }
    }
    // r c-1
    if (c - 1 >= 0) {
      if (lamps[r][c - 1] == 1) {
        clueCount++;
      }
    }
    // r-1 c
    if (r - 1 >= 0) {
      if (lamps[r - 1][c] == 1) {
        clueCount++;
      }
    }
    // r+1 c
    if (r + 1 < activePuzzle.getHeight()) {
      if (lamps[r + 1][c] == 1) {
        clueCount++;
      }
    }
    return (clueCount == clue);
  }

  @Override
  public void addObserver(ModelObserver observer) {
    observers.add(observer);
  }

  @Override
  public void removeObserver(ModelObserver observer) {
    observers.remove(observer);
  }

  private void notifyObservers(Model model) {
    for (ModelObserver o : this.observers) {
      o.update(model);
    }
  }
}
