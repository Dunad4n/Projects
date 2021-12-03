package com.company;

import com.company.entities.Card;
import com.company.entities.Croupier;
import com.company.entities.Player;
import com.company.entities.Table;
import com.company.services.Cell;
import com.company.services.Combination;
import com.company.services.RequestAction;
import com.company.services.Saver;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GUI extends Application {

    Logger logger = LoggerFactory.getLogger(GUI.class);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        logger.info("Программа запущена");
        mainMenu();
    }

    private void mainMenu() {
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.getIcons().add(new Image("/icons/mainIcon.png"));
        stage.setTitle("Poker");

        Group root = new Group();
        Scene scene = new Scene(root, 400, 400, Color.LIGHTGREEN);

        Button buttonStart = new Button("Новая игра");
        buttonStart.setLayoutX(100);
        buttonStart.setLayoutY(100);
        buttonStart.setPrefWidth(200);
        buttonStart.setPrefHeight(30);
        buttonStart.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            ArrayList<Player> players = new ArrayList<>();
            for (int i = 0; i < 6; i++) {
                players.add(new Player());
            }
            logger.info("Начата новая игра");
            players.get(0).setVisibleCards(true);
            playArea(new Stage(StageStyle.DECORATED), players, new Table(), new Croupier(), 6);
            stage.close();
        });

        Button buttonLoad = new Button("Загрузить игру");
        buttonLoad.setLayoutX(buttonStart.getLayoutX());
        buttonLoad.setLayoutY(buttonStart.getLayoutY() + 50);
        buttonLoad.setPrefWidth(buttonStart.getPrefWidth());
        buttonLoad.setPrefHeight(buttonStart.getPrefHeight());
        buttonLoad.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            try {
                Cell cell = new Saver().deserialize();
                playArea(new Stage(StageStyle.DECORATED), cell.getPlayers(), cell.getTable(),
                        cell.getCroupier(), cell.getPlayers().size());
                logger.info("Начата загрузка игры");
                stage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Button buttonDemo = new Button("Демонстрационная партия");
        buttonDemo.setLayoutX(buttonStart.getLayoutX());
        buttonDemo.setLayoutY(buttonLoad.getLayoutY() + 50);
        buttonDemo.setPrefWidth(buttonStart.getPrefWidth());
        buttonDemo.setPrefHeight(buttonStart.getPrefHeight());
        buttonDemo.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            logger.info("Запуск демонстрационной версии");
        });

        Button buttonExit = new Button("Выйти из игры");
        buttonExit.setLayoutX(buttonStart.getLayoutX());
        buttonExit.setLayoutY(buttonDemo.getLayoutY() + 50);
        buttonExit.setPrefWidth(buttonStart.getPrefWidth());
        buttonExit.setPrefHeight(buttonStart.getPrefHeight());
        buttonExit.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                logger.info("Выход из игры");
                stage.close();
                });

        root.getChildren().add(buttonStart);
        root.getChildren().add(buttonLoad);
        root.getChildren().add(buttonDemo);
        root.getChildren().add(buttonExit);

        stage.setResizable(false);
        stage.setFullScreen(false);
        stage.setScene(scene);
        stage.show();
    }

    public void playArea(Stage stagePlay, ArrayList<Player> players, Table table, Croupier croupier, int playersCount) {
        stagePlay.getIcons().add(new Image("/icons/mainIcon.png"));
        stagePlay.setTitle("Poker");
        Group group = new Group();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Scene scene = new Scene(group, screenSize.getWidth(), screenSize.getHeight() - 65, Color.WHEAT);

        for (int i = 1; i < players.size(); i++) {
            players.get(i).setVisibleCards(false);
        }
        if (croupier.getMaxBet() == 0) {
            croupier.distributeCardsToPlayers(players);
        }
        ArrayList<Player> otherPlayers = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getBank() == 0 && croupier.getMaxBet() == 0) {
                players.get(i).setFirstCard(null);
                players.get(i).setSecondCard(null);
            } else {
                otherPlayers.add(players.get(i));
            }
        }
        if (otherPlayers.size() == 1 && otherPlayers.get(0).equals(players.get(0))) {
            logger.info("Пользователь победил");
            gameResultWindow(stagePlay, "Вы победили!");
            return;
        } else if (players.get(0).getBank() == 0 && croupier.getMaxBet() == 0) {
            logger.info("Пользователь проиграл");
            gameResultWindow(stagePlay, "Вы проиграли :(");
            return;
        }
        drawPlayersCards(group, scene, players);
        drawTableCards(group, scene, table);

        Button buttonBet = new Button("Поставить");
        buttonBet.setLayoutX(scene.getWidth() / 2 + 80);
        buttonBet.setLayoutY(600);
        buttonBet.setPrefWidth(100);
        buttonBet.setPrefHeight(40);

        TextField textFieldBet = new TextField(Integer.toString(players.get(0).getBank() / 20));
        textFieldBet.setLayoutX(buttonBet.getLayoutX() + buttonBet.getPrefWidth());
        textFieldBet.setLayoutY(buttonBet.getLayoutY());
        textFieldBet.setPrefWidth(60);
        textFieldBet.setPrefHeight(buttonBet.getPrefHeight());

        Text textBank = new Text("Ваш Банк : " + players.get(0).getBank());
        textBank.setX(buttonBet.getLayoutX() + 3);
        textBank.setY(buttonBet.getLayoutY() + buttonBet.getPrefHeight() + 55);

        Combination cmb = new Combination();

        buttonBet.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if (Integer.parseInt(textFieldBet.getText()) > players.get(0).getBank()) {
                players.get(0).bet(croupier, players.get(0).getBank());
            } else {
                players.get(0).bet(croupier, Integer.parseInt(textFieldBet.getText()));
            }
            logger.info("Пользователь сделал ставку");
            textBank.setText("Ваш Банк : " + players.get(0).getBank());
            textFieldBet.setText(Integer.toString(croupier.getMaxBet()));
            for (int i = 1; i < otherPlayers.size(); i++) {
                new RequestAction().callAction(otherPlayers.get(i), table, croupier, cmb);
                logger.info("Игрок под номером " + (i + 1) + " сделал действие");
            }
            drawPlayersCards(group, scene, players);
            if (table.getTableCards().size() == 0) {
                croupier.distributeCardsToTable(table, true);
                drawTableCards(group, scene, table);
            } else if (table.getTableCards().size() == 5) {
                for (Player player : players) {
                    player.setVisibleCards(true);
                }
                drawPlayersCards(group, scene, players);
                drawTableCards(group, scene, table);
                double winner = cmb.findWinner(players, table);
                logger.info("Найден победитель");
                players.get((int) winner).addBank(croupier.getBank());
                textBank.setText("Ваш Банк : " + players.get(0).getBank());
                winnerWindow((int) winner + 1, croupier, stagePlay, players, playersCount);
                croupier.setBank(-croupier.getBank());
            } else {
                croupier.distributeCardsToTable(table, false);
                drawTableCards(group, scene, table);
            }
        });

        Button buttonFold = new Button("Сбросить карты");
        buttonFold.setLayoutX(buttonBet.getLayoutX());
        buttonFold.setLayoutY(buttonBet.getLayoutY() + buttonBet.getPrefHeight() + 5);
        buttonFold.setPrefWidth(buttonBet.getPrefWidth() + textFieldBet.getPrefWidth() - 40);
        buttonFold.setPrefHeight(30);
        buttonFold.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            players.get(0).fold();
            logger.info("Пользователь сбросил карты");
            if (table.getTableCards().size() == 0) {
                croupier.distributeCardsToTable(table, true);
                for (int i = 1; i < otherPlayers.size(); i++) {
                    new RequestAction().callAction(otherPlayers.get(i), table, croupier, cmb);
                    logger.info("Игрок под номером " + (i + 1) + " сделал действие");
                }
            }
            while (table.getTableCards().size() != 5) {
                croupier.distributeCardsToTable(table, false);
                for (int i = 1; i < otherPlayers.size(); i++) {
                    new RequestAction().callAction(players.get(i), table, croupier, cmb);
                }
            }
            for (Player player : players) {
                player.setVisibleCards(true);
            }
            drawPlayersCards(group, scene, players);
            drawTableCards(group, scene, table);
            double winner = cmb.findWinner(players, table);
            logger.info("Найден победитель");
            players.get((int) winner).addBank(croupier.getBank());
            winnerWindow((int) winner + 1, croupier, stagePlay, players, playersCount);
            croupier.setBank(-croupier.getBank());
        });

        Button buttonCheck = new Button("Чек");
        buttonCheck.setLayoutX(buttonBet.getLayoutX() + buttonFold.getPrefWidth());
        buttonCheck.setLayoutY(buttonFold.getLayoutY());
        buttonCheck.setPrefWidth(buttonBet.getPrefWidth() + textFieldBet.getPrefWidth() - buttonFold.getPrefWidth());
        buttonCheck.setPrefHeight(30);
        buttonCheck.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            players.get(0).check(croupier);
            logger.info("Пользователь сбросил карты");
            for (int i = 1; i < otherPlayers.size(); i++) {
                new RequestAction().callAction(otherPlayers.get(i), table, croupier, cmb);
                logger.info("Игрок под номером " + (i + 1) + " сделал действие");
            }
            drawPlayersCards(group, scene, players);
            if (table.getTableCards().size() == 0) {
                croupier.distributeCardsToTable(table, true);
                drawTableCards(group, scene, table);
            } else if (table.getTableCards().size() == 5) {
                for (Player player : players) {
                    player.setVisibleCards(true);
                }
                drawPlayersCards(group, scene, players);
                drawTableCards(group, scene, table);
                double winner = cmb.findWinner(players, table);
                logger.info("Найден победитель");
                players.get((int) winner).addBank(croupier.getBank());
                textBank.setText("Ваш Банк : " + players.get(0).getBank());
                winnerWindow((int) winner + 1, croupier, stagePlay, players, playersCount);
                croupier.setBank(-croupier.getBank());
            } else {
                croupier.distributeCardsToTable(table, false);
                drawTableCards(group, scene, table);
            }
        });

        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Меню");
        MenuItem menuItemSave = new MenuItem("Сохранить игру");
        menuItemSave.setOnAction(mouseEvent -> {
            try {
                new Saver().serialize(players, table, croupier);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        MenuItem menuItemExit = new MenuItem("Выйти в главное меню");
        menuItemExit.setOnAction(mouseEvent -> {
            stagePlay.close();
            mainMenu();
        });
        menuBar.getMenus().add(menu);
        menu.getItems().addAll(menuItemSave, menuItemExit);

        group.getChildren().addAll(menuBar, textFieldBet, textBank, buttonBet, buttonCheck, buttonFold);

        stagePlay.setScene(scene);
        stagePlay.centerOnScreen();
        stagePlay.show();
    }

    private void winnerWindow(int indexWinner, Croupier croupier, Stage stagePlayer, ArrayList<Player> players, int playersCount) {
        Stage stageWinner = new Stage(StageStyle.UNDECORATED);
        stageWinner.setTitle("Winner");
        stageWinner.getIcons().add(new Image("/icons/mainIcon.png"));
        stageWinner.initModality(Modality.APPLICATION_MODAL);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        stageWinner.setX(screenSize.getWidth() / 10);
        stageWinner.setY(screenSize.getHeight() / 10);
        Group group = new Group();
        Scene scene = new Scene(group, 300, 130);

        switch (indexWinner) {
            case (2):
                indexWinner = 4;
                break;
            case (3):
                indexWinner = 2;
                break;
            case (4):
                indexWinner = 6;
                break;
            case (5):
                indexWinner = 3;
                break;
            case (6):
                indexWinner = 5;
                break;
        }

        Text textWinner = new Text("Победил игрок под номером " + indexWinner + " и выиграл " + croupier.getBank());
        textWinner.setX(27);
        textWinner.setY(40);
        Text textChoose = new Text("Хотите продолжить игру ?");
        textChoose.setX(80);
        textChoose.setY(textWinner.getY() + 30);

        if (indexWinner == 1) {
            textWinner.setText("Вы победили и выиграли " + croupier.getBank() + "!");
            textWinner.setX(77);
        }

        Button buttonContinue = new Button("Продолжить");
        buttonContinue.setLayoutX(100);
        buttonContinue.setLayoutY(textChoose.getY() + 10);
        buttonContinue.setPrefWidth(100);
        buttonContinue.setPrefHeight(30);
        buttonContinue.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            playArea(stagePlayer, players, new Table(), new Croupier(), playersCount);
            stageWinner.close();
        });

        group.getChildren().add(textWinner);
        group.getChildren().add(textChoose);
        group.getChildren().add(buttonContinue);

        stageWinner.setScene(scene);
        stageWinner.setResizable(false);
        stageWinner.showAndWait();
    }

    private void gameResultWindow(Stage stagePlay, String str) {
        Stage stageResult = new Stage(StageStyle.UNDECORATED);
        stageResult.setTitle("Winner");
        stageResult.getIcons().add(new Image("/icons/mainIcon.png"));
        stageResult.initModality(Modality.APPLICATION_MODAL);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        stageResult.setX(screenSize.getWidth() / 10);
        stageResult.setY(screenSize.getHeight() / 10);
        Group group = new Group();
        Scene scene = new Scene(group, 300, 130);

        Text textLose = new Text(str);
        textLose.setX(110);
        textLose.setY(60);

        Button buttonExit = new Button("Выйти в меню");
        buttonExit.setLayoutX(95);
        buttonExit.setLayoutY(90);
        buttonExit.setPrefWidth(115);
        buttonExit.setPrefHeight(30);
        buttonExit.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            stageResult.close();
            stagePlay.close();
            mainMenu();
        });

        group.getChildren().add(textLose);
        group.getChildren().add(buttonExit);

        stageResult.setScene(scene);
        stageResult.setResizable(false);
        stageResult.centerOnScreen();
        stageResult.showAndWait();
    }

    public void drawPlayersCards(Group group, Scene scene, ArrayList<Player> players) {
        clearGroup(group);
        ArrayList<ImageView> visualCards = visualPlayersCards(players);
        int j = 0;
        int minus = 1;
        for (int i = 0; i < 6; i ++) {
            if (players.get(j).getFirstCard() != null) {
                int n = 0;
                if (players.get(j).isVisibleCards()) {
                    for (int k = 0; k < 2; k++) {
                        ImageView card = visualCards.get(k);
                        if (j < 2) {
                            card.setX(scene.getWidth() / 2.2 + 50 * n);
                            card.setY(600 - 500 * j);
                        } else {
                            card.setX(scene.getWidth() / 2.2 + minus * 400 + 50 * n);
                            card.setY(600 - 150 * (j / 2));
                        }
                        n++;
                        card.setFitWidth(100);
                        card.setFitHeight(card.getFitWidth() * 1.3);
                        card.setPreserveRatio(true);
                        group.getChildren().add(card);
                    }
                    visualCards.remove(0);
                    visualCards.remove(0);
                } else {
                    for (int k = i; k < i + 2; k++) {
                        ImageView card = new ImageView(new Image("/cards/0.0.png"));
                        if (j < 2) {
                            card.setX(scene.getWidth() / 2.2 + 50 * n);
                            card.setY(600 - 500 * j);
                        } else {
                            card.setX(scene.getWidth() / 2.2 + minus * 400 + 50 * n);
                            card.setY(600 - 150 * (j / 2));
                        }
                        n++;
                        card.setFitWidth(100);
                        card.setFitHeight(card.getFitWidth() * 1.3);
                        card.setPreserveRatio(true);
                        group.getChildren().add(card);
                    }
                }
            }
            minus = -minus;
            j++;
        }
    }

    private void clearGroup(Group group) {
        if (group.getChildren().size() == 0) {
            return;
        }
        for (int i = 0; i < group.getChildren().size(); i++) {
            if (group.getChildren().get(i).getClass().equals(ImageView.class)) {
                group.getChildren().remove(i);
                i--;
            }
        }
    }

    private void drawTableCards(Group group, Scene scene, Table table) {
        ArrayList<ImageView> cardsView = visualTableCards(table);
        int space = 0;
        for (ImageView cardView : cardsView) {
            cardView.setX(scene.getWidth() / 2.2 - 200 + space);
            cardView.setY(350);
            cardView.setFitWidth(100);
            cardView.setFitHeight(cardView.getFitWidth() * 1.3);
            cardView.setPreserveRatio(true);
            group.getChildren().add(cardView);
            space += 110;
        }
    }

    public ArrayList<ImageView> visualPlayersCards(ArrayList<Player> players) {
        File file = new File("src\\main\\resources\\cards");
        ArrayList<ImageView> cardsView = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getFirstCard() != null && players.get(i).isVisibleCards()) {
                cardsView.add(findVisual(file, players.get(i).getFirstCard()));
                cardsView.add(findVisual(file, players.get(i).getSecondCard()));
            }
        }
        return cardsView;
    }

    private ArrayList<ImageView> visualTableCards(Table table) {
        File file = new File("src\\main\\resources\\cards");
        ArrayList<ImageView> cardsView = new ArrayList<>();
        for (Card card : table.getTableCards()) {
            cardsView.add(findVisual(file, card));
        }
        return cardsView;
    }

    private ImageView findVisual(File rootFile, Card card) {
        File[] files = rootFile.listFiles();
        for (File file : files) {
            StringBuilder value = new StringBuilder(file.getName());
            int j = 0;
            while (file.getName().charAt(j) != '.') {
                j++;
            }
            if (value.substring(0, j).equals(Integer.toString(card.getValue().ordinal() + 2)) &&
                    value.substring(j + 1, j + 2).equals(Integer.toString(card.getKind().ordinal() + 1))) {
                return new ImageView(new Image(file.getAbsolutePath()));
            }
        }
        return null;
    }
}
