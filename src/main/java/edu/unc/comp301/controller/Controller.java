package edu.unc.comp301.controller;

public interface Controller {
  void moveUp();

  void moveDown();

  void moveLeft();

  void moveRight();

  void startGame();

  void setHardMode(boolean hard);

  void toggleTheme();

  void choosePathLeft();

  void choosePathRight();
}
