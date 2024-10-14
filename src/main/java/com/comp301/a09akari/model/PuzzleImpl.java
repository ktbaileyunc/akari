package com.comp301.a09akari.model;

public class PuzzleImpl implements Puzzle {
  private int[][] board;

  public PuzzleImpl(int[][] board) {
    this.board = board;
  }

  @Override
  public int getWidth() {
    return board[0].length;
  }

  @Override
  public int getHeight() {
    return board.length;
  }

  @Override
  public CellType getCellType(int r, int c) {
    if (r >= this.getHeight() || c >= this.getWidth()) {
      throw new IndexOutOfBoundsException();
    }
    if (board[r][c] == 5) {
      return CellType.WALL;
    } else if (board[r][c] == 6) {
      return CellType.CORRIDOR;
    } else {
      return CellType.CLUE;
    }
  }

  @Override
  public int getClue(int r, int c) {
    if (r >= this.getHeight() || c >= this.getWidth()) {
      throw new IndexOutOfBoundsException();
    }
    if (this.getCellType(r, c) != CellType.CLUE) {
      throw new IllegalArgumentException();
    }
    return board[r][c];
  }
}
