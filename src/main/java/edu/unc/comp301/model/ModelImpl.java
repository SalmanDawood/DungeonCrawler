package edu.unc.comp301.model;

import edu.unc.comp301.model.board.Board;
import edu.unc.comp301.model.board.BoardImpl;
import edu.unc.comp301.model.board.Posn;
import edu.unc.comp301.model.pieces.CollisionResult;
import edu.unc.comp301.model.pieces.Piece;
import java.util.ArrayList;
import java.util.List;

public class ModelImpl implements Model {

  private final Board board;
  private int curScore;
  private int highScore;
  private int level;
  private STATUS status;
  private final List<Observer> observers = new ArrayList<>();

  public enum Theme {
    LIGHT,
    DARK
  };

  private Theme theme = Theme.DARK;

  public ModelImpl(int width, int height) {
    this.board = new BoardImpl(width, height);
    this.curScore = 0;
    this.highScore = 0;
    this.level = 0;
    this.status = STATUS.END_GAME;
  }

  public ModelImpl(Board board) {
    this.board = board;
    this.curScore = 0;
    this.highScore = 0;
    this.level = 1;
    this.status = STATUS.IN_PROGRESS;
  }

  @Override
  public int getWidth() {
    return board.getWidth();
  }

  @Override
  public int getHeight() {
    return board.getHeight();
  }

  @Override
  public Piece get(Posn p) {
    return board.get(p);
  }

  @Override
  public int getCurScore() {
    return curScore;
  }

  @Override
  public int getHighScore() {
    return highScore;
  }

  @Override
  public int getLevel() {
    return level;
  }

  @Override
  public STATUS getStatus() {
    return status;
  }

  @Override
  public Theme getTheme() {
    return theme;
  }

  @Override
  public void startGame() {
    curScore = 0;
    level = 1;
    status = STATUS.IN_PROGRESS;
    board.init(level + 1, 2, 2);
    notifyObservers();
  }

  @Override
  public void endGame() {
    status = STATUS.END_GAME;
    if (curScore > highScore) {
      highScore = curScore;
    }
    notifyObservers();
  }

  @Override
  public void moveUp() {
    moveHero(-1, 0);
  }

  @Override
  public void moveDown() {
    moveHero(1, 0);
  }

  @Override
  public void moveLeft() {
    moveHero(0, -1);
  }

  @Override
  public void moveRight() {
    moveHero(0, 1);
  }

  private void moveHero(int row, int col) {
    if (status != STATUS.IN_PROGRESS) {
      return;
    }
    CollisionResult res = board.moveHero(row, col);
    curScore += res.getPoints();
    switch (res.getResults()) {
      case NEXT_LEVEL:
        status = STATUS.CHOOSE_PATH;
        notifyObservers();
        return;
      case GAME_OVER:
        endGame();
        return;
      case CONTINUE:
      default:
        break;
    }
    notifyObservers();
  }

  @Override
  public void addObserver(Observer o) {
    observers.add(o);
  }

  private void notifyObservers() {
    for (Observer o : observers) {
      o.update();
    }
  }

  @Override
  public void setHardMode(boolean hard) {
    ((BoardImpl) board).setHardMode(hard);
  }

  @Override
  public void toggleTheme() {
    theme = (theme == Theme.DARK) ? Theme.LIGHT : Theme.DARK;
    notifyObservers();
  }

  @Override
  public void choosePathLeft() {
    level++;
    board.init(level, 1, 1);
    status = STATUS.IN_PROGRESS;
    notifyObservers();
  }

  @Override
  public void choosePathRight() {
    level++;
    board.init(level + 2, 3, 2);
    status = STATUS.IN_PROGRESS;
    notifyObservers();
  }
}

