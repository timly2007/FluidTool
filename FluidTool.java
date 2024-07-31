package com.example.demo;

// imports
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.scene.shape.Circle;
import java.io.IOException;
import java.util.Arrays;
import javafx.scene.control.Button;

public class FluidTool extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        // radius of particles.
        float radius = 5;

        // amount of particles (only a majority will show up on screen)
        int ParticleAmount = 1340;

        //the amount of points (max) possible on an object
        int totalObjects = 8;
        int drawingFrames = 800;

        // the radius of impact of the forces between particles.
        float densityRadius = (float) (1.5 * 1080 / Math.pow(ParticleAmount * (1080f/2300f) * (1.73 / 2), 0.5));

        // dampening with wall
        float dampeningCoefficient = 0.4f;

        // gravity (unused atm)
        float gravity = 0f;

        //black bg
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: black");
        Scene scene = new Scene(root, 1440, 900);
        stage.setScene(scene);
        stage.show();

        // bg lines

        Line[] bgLine = new Line[26];

        for (int i = 0; i < 26; i++) {
            if(i < 10) {
                bgLine[i] = new Line(0, 0, 1440, 0);
                bgLine[i].setTranslateY(-400 + 90 * i);
            } else {
                bgLine[i] = new Line(0, 0, 0, 900);
                bgLine[i].setTranslateX(-670 + 90 * (i - 10));
            }
            bgLine[i].setStroke(Color.rgb(20, 20, 20));
            bgLine[i].setStrokeWidth(5);
            root.getChildren().add(bgLine[i]);
        }

        for (int i = 0; i < 9; i++) {
            Line line = new Line(0, 850, 270, 850);
            line.setStroke(Color.WHITE);
            line.setStrokeWidth(5);
            root.getChildren().add(line);
            line.setTranslateX(-540);
            line.setTranslateY(-300);
            line.setOpacity(0);
        }

        //create circle
        Circle[] circle = new Circle[ParticleAmount];
        Circle[] testingCircle = new Circle[ParticleAmount];

        //create testing lines
        Line[] testingLine = new Line[ParticleAmount];

        // create "particleamount" particles.
        for (int i = 0; i < ParticleAmount; i++) {
            circle[i] = new Circle(radius, Color.WHITE);
            circle[i].setTranslateX((1770 - 2 * radius) * Math.random() - (1000 - radius)); // find out how random works
            circle[i].setTranslateY((1000 - 2 * radius) * Math.random() - (500 - radius));
            root.getChildren().add(circle[i]);

            testingCircle[i] = new Circle(radius, Color.WHITE);
            testingCircle[i].setOpacity(0);
            root.getChildren().add(testingCircle[i]);

            testingLine[i] = new Line(-8, 0, 8, 0);
            testingLine[i].setStroke(Color.WHITE);
            testingLine[i].setStrokeWidth(3);
            testingLine[i].setOpacity(0);
            root.getChildren().add(testingLine[i]);
        }

        // create initial point circle
        Circle[] initialPoint = new Circle[drawingFrames];
        for (int s = 0; s < drawingFrames; s++) {
            initialPoint[s] = new Circle(15, Color.TRANSPARENT);
            root.getChildren().add(initialPoint[s]);
        }

        // create sliders

        Polygon settingsBG = new Polygon(0, 0, 360, 0, 360, 900, 0, 900);
        root.getChildren().add(settingsBG);
        settingsBG.setTranslateX(-540);
        settingsBG.setFill(Color.BLACK);
        settingsBG.setOpacity(0);

        Line line = new Line(0, 850, 270, 850);
        line.setStroke(Color.WHITE);
        line.setStrokeWidth(5);
        root.getChildren().add(line);
        line.setTranslateX(-540);
        line.setTranslateY(-300);
        line.setOpacity(0);

        Line line2 = new Line(0, 850, 270, 850);
        line2.setStroke(Color.WHITE);
        line2.setStrokeWidth(5);
        root.getChildren().add(line2);
        line2.setTranslateX(-540);
        line2.setTranslateY(-200);
        line2.setOpacity(0);

        Line line3 = new Line(0, 850, 270, 850);
        line3.setStroke(Color.WHITE);
        line3.setStrokeWidth(5);
        root.getChildren().add(line3);
        line3.setTranslateX(-540);
        line3.setTranslateY(-100);
        line3.setOpacity(0);

        Line line4 = new Line(0, 850, 270, 850);
        line4.setStroke(Color.WHITE);
        line4.setStrokeWidth(5);
        root.getChildren().add(line4);
        line4.setTranslateX(-540);
        line4.setTranslateY(0);
        line4.setOpacity(0);

        Polygon slider1 = new Polygon(20, 20, 0, 20, 0, 0, 20, 0);
        root.getChildren().add(slider1);
        slider1.setTranslateX(-640);
        slider1.setTranslateY(-300);
        slider1.setFill(Color.WHITE);
        slider1.setStroke(Color.BLACK);
        slider1.setStrokeWidth(3);
        slider1.setOpacity(0);

        Polygon slider2 = new Polygon(20, 20, 0, 20, 0, 0, 20, 0);
        root.getChildren().add(slider2);
        slider2.setTranslateX(-640);
        slider2.setTranslateY(-200);
        slider2.setFill(Color.WHITE);
        slider2.setStroke(Color.BLACK);
        slider2.setStrokeWidth(3);
        slider2.setOpacity(0);

        Polygon slider3 = new Polygon(20, 20, 0, 20, 0, 0, 20, 0);
        root.getChildren().add(slider3);
        slider3.setTranslateX(-640);
        slider3.setTranslateY(-100);
        slider3.setFill(Color.WHITE);
        slider3.setStroke(Color.BLACK);
        slider3.setStrokeWidth(3);
        slider3.setOpacity(0);

        Polygon slider4 = new Polygon(20, 20, 0, 20, 0, 0, 20, 0);
        root.getChildren().add(slider4);
        slider4.setTranslateX(-640);
        slider4.setTranslateY(0);
        slider4.setFill(Color.WHITE);
        slider4.setStroke(Color.BLACK);
        slider4.setStrokeWidth(3);
        slider4.setOpacity(0);

        // settings button & interface bg

        Button settings = new Button("⚙");
        Font font2 = Font.font("Courier New", FontWeight.BOLD, 36);
        settings.setFont(font2);
        root.getChildren().add(settings);
        settings.setTranslateX(670);
        settings.setTranslateY(-400);
        settings.setStyle("-fx-text-fill: white; -fx-background-color: black; -fx-border-width: 3px; -fx-border-color: white");
        settings.setOpacity(0.75);

        // button for deleting all objects

        Button deleteButton = new Button("Delete");
        Font font = Font.font("Georgia", FontWeight.BOLD, 24);
        deleteButton.setFont(font);
        root.getChildren().add(deleteButton);
        deleteButton.setTranslateX(-636);
        deleteButton.setTranslateY(400);
        deleteButton.setStyle("-fx-text-fill: white; -fx-border-color: white; -fx-background-color: black; -fx-border-width: 3px");
        deleteButton.setOpacity(0);

        // button for creating a CFD

        Button cfdButton = new Button("+ Generate CFD");;
        cfdButton.setFont(font);
        root.getChildren().add(cfdButton);
        cfdButton.setTranslateX(-503);
        cfdButton.setTranslateY(330);
        cfdButton.setStyle("-fx-text-fill: white; -fx-border-color: white; -fx-background-color: black; -fx-border-width: 3px");
        cfdButton.setOpacity(0);

        // button for exiting a CFD

        Button exitButton = new Button("×");
        Font font3 = Font.font("Arial", FontWeight.NORMAL, 48);
        exitButton.setFont(font3);
        root.getChildren().add(exitButton);
        exitButton.setTranslateX(-690);
        exitButton.setTranslateY(-420);
        exitButton.setStyle("-fx-text-fill: white; -fx-border-color: transparent; -fx-background-color: transparent; -fx-border-width: 3px");
        exitButton.setOpacity(0);

        // CFD visuals (progress)
        Rectangle cfdBG = new Rectangle(1440, 900);
        cfdBG.setStyle("-fx-border-color: transparent; -fx-background-color: black");
        cfdBG.setTranslateX(2000);

        Rectangle progressBar = new Rectangle(266, 57);
        progressBar.setFill(Color.WHITE);
        progressBar.setTranslateX(2000);

        Rectangle progressBar2 = new Rectangle(256, 47);
        progressBar2.setFill(Color.BLACK);
        progressBar2.setTranslateX(2000);

        Rectangle progressBar3 = new Rectangle(0, 31);
        progressBar3.setFill(Color.WHITE);
        progressBar3.setTranslateX(2000);

        // object polygons
        Polygon[] polygon = new Polygon[100];

        // button for direction lines

        Button directionButton = new Button("→");
        directionButton.setFont(font);
        root.getChildren().add(directionButton);
        directionButton.setTranslateX(-667);
        directionButton.setTranslateY(330);
        directionButton.setStyle("-fx-text-fill: white; -fx-border-color: white; -fx-background-color: black; -fx-border-width: 3px");
        directionButton.setOpacity(0);

        // button for night mode lol

        Button nightButton = new Button("✸/☽");
        nightButton.setFont(font);
        root.getChildren().add(nightButton);
        nightButton.setTranslateX(-430);
        nightButton.setTranslateY(400);
        nightButton.setStyle("-fx-text-fill: white; -fx-border-color: white; -fx-background-color: black; -fx-border-width: 3px");
        nightButton.setOpacity(0);

        //  label sliders

        Button labelRadius = new Button("Radius");
        labelRadius.setFont(font);
        root.getChildren().add(labelRadius);
        labelRadius.setTranslateX(-540);
        labelRadius.setTranslateY(-350);
        labelRadius.setStyle("-fx-text-fill: white; -fx-border-color: transparent; -fx-background-color: transparent; -fx-border-width: 3px");
        labelRadius.setOpacity(0);

        Button labelSpeed = new Button("Air Speed");
        labelSpeed.setFont(font);
        root.getChildren().add(labelSpeed);
        labelSpeed.setTranslateX(-540);
        labelSpeed.setTranslateY(-250);
        labelSpeed.setStyle("-fx-text-fill: white; -fx-border-color: transparent; -fx-background-color: transparent; -fx-border-width: 3px");
        labelSpeed.setOpacity(0);

        Button wallShear = new Button("Wall Shear");
        wallShear.setFont(font);
        root.getChildren().add(wallShear);
        wallShear.setTranslateX(-540);
        wallShear.setTranslateY(-150);
        wallShear.setStyle("-fx-text-fill: white; -fx-border-color: transparent; -fx-background-color: transparent; -fx-border-width: 3px");
        wallShear.setOpacity(0);

        Button viscosityText = new Button("Viscosity (WIP)");
        viscosityText.setFont(font);
        root.getChildren().add(viscosityText);
        viscosityText.setTranslateX(-540);
        viscosityText.setTranslateY(-50);
        viscosityText.setStyle("-fx-text-fill: white; -fx-border-color: transparent; -fx-background-color: transparent; -fx-border-width: 3px");
        viscosityText.setOpacity(0);

        Button particleVisibility = new Button("◉");
        particleVisibility.setFont(font);
        root.getChildren().add(particleVisibility);
        particleVisibility.setTranslateX(-525);
        particleVisibility.setTranslateY(400);
        particleVisibility.setStyle("-fx-text-fill: white; -fx-border-color: white; -fx-background-color: black; -fx-border-width: 3px");
        particleVisibility.setOpacity(0);

        Button pause = new Button("Pause");
        pause.setFont(font);
        root.getChildren().add(pause);
        pause.setTranslateX(660);
        pause.setTranslateY(410);
        pause.setStyle("-fx-text-fill: white; -fx-border-color: white; -fx-background-color: black; -fx-border-width: 3px");
        pause.setOpacity(0.5);

        // radius of the CFD. you probably want something > 50, but beware as the radius increases there are more computations
        float cfdRadius = 80;

        // counter for frames, higher means more accuracy as it's time-averaged.
        int totalFrames = 170;

        // pixel size for CFD, smaller means more accuracy but also more computations
        int pixelSize = 7;

        // cfd particles
        Rectangle[][] cfdPixel = new Rectangle[1440 / pixelSize][900 / pixelSize];

        for (int j = 0; j < 1440 / pixelSize; j++) {
            for (int k = 0; k < 900 / pixelSize; k++) {
                cfdPixel[j][k] = new Rectangle(pixelSize, pixelSize);
                root.getChildren().add(cfdPixel[j][k]);
                cfdPixel[j][k].setTranslateX(-720 + (Math.floor(pixelSize/2)) + pixelSize * j);
                cfdPixel[j][k].setOpacity(0);

                cfdPixel[j][k].setTranslateY(900 / pixelSize);
            }
        }

        // defining all variables yay
        float[] centerX = new float[ParticleAmount];
        float[] centerY = new float[ParticleAmount];
        float[] velocityY = new float[ParticleAmount];
        float[] velocityX = new float[ParticleAmount];
        float[] distanceY = new float[ParticleAmount];
        float[] distanceX = new float[ParticleAmount];
        float[] distance = new float[ParticleAmount];
        float[] forceMagnitude = new float[ParticleAmount];
        float[] forceNetY = new float[ParticleAmount];
        float[] forceNetX = new float[ParticleAmount];
        float[] velocityDifference = new float[ParticleAmount];
        float[] velocityMapped = new float[ParticleAmount];
        int[] variableColor1 = new int[ParticleAmount];
        int[] variableColor2 = new int[ParticleAmount];
        int[] variableColor3 = new int[ParticleAmount];
        float[] objectChecker = new float[ParticleAmount];
        float[] centerX1 = new float[ParticleAmount];
        float[] centerX2 = new float[ParticleAmount];
        float[] centerY1 = new float[ParticleAmount];
        float[] centerY2 = new float[ParticleAmount];
        float[] particleSlope = new float[ParticleAmount];
        float[] particleInverseSlope = new float[ParticleAmount];
        float[] particleDistanceX = new float[ParticleAmount];
        float[] particleDistanceY = new float[ParticleAmount];
        float[] finalCenterX = new float[ParticleAmount];
        float[] finalCenterY = new float[ParticleAmount];
        float[] finalDistanceX = new float[ParticleAmount];
        float[] finalDistanceY = new float[ParticleAmount];
        // variables to reduce square root functions
        float[] distance2 = new float[ParticleAmount];
        float[] distanceMouse2 = new float[ParticleAmount];
        float[] particleSlope2 = new float[ParticleAmount];
        float[] magnitudeFinal = new float[ParticleAmount];
        float[] velocityX2 = new float[ParticleAmount];
        float[] velocityY2 = new float[ParticleAmount];

        float[][] objectCenterX1 = new float[totalObjects][drawingFrames];
        float[][] objectCenterX2 = new float[totalObjects][drawingFrames];
        float[][] objectDistanceX = new float[totalObjects][drawingFrames];
        float[][] objectCenterY1 = new float[totalObjects][drawingFrames];
        float[][] objectCenterY2 = new float[totalObjects][drawingFrames];
        float[][] objectDistanceY = new float[totalObjects][drawingFrames];
        float[][] objectSlope = new float[totalObjects][drawingFrames];
        float[][] objectInverseSlope = new float[totalObjects][drawingFrames];
        float[][] intersectionCenterX = new float[totalObjects][drawingFrames];
        float[][] intersectionCenterY = new float[totalObjects][drawingFrames];
        float[][] mouseX = new float[totalObjects][drawingFrames];
        float[][] mouseY = new float[totalObjects][drawingFrames];
        float[][] particleIntDistanceX = new float[totalObjects][drawingFrames];
        float[][] particleIntDistanceY = new float[totalObjects][drawingFrames];
        float[] slopeVariable = new float[ParticleAmount];
        float[][] centerPX1 = new float[1440 / pixelSize][ParticleAmount];
        float[][] centerPY1 = new float[1440 / pixelSize][ParticleAmount];
        float[][] velocityPMapped1 = new float[1440 / pixelSize][ParticleAmount];
        float[][] totalAverageVelocity = new float[1440 / pixelSize][900 / pixelSize];
        float[][] totalVelocityCount = new float[1440 / pixelSize][900 / pixelSize];
        int[][] frameCounter = new int[1440 / pixelSize][900 / pixelSize];
        float[][] totalVelocity = new float[1440 / pixelSize][900 / pixelSize];
        float[][] averageVelocity = new float[1440 / pixelSize][900 / pixelSize];
        float[][] velocityCount = new float[1440 / pixelSize][900 / pixelSize];
        float[] velocityWeight = new float[ParticleAmount];
        int[][] pixelColor1 = new int[1440 / pixelSize][900 / pixelSize];
        int[][] pixelColor3 = new int[1440 / pixelSize][900 / pixelSize];
        int[][] pixelColor2 = new int[1440 / pixelSize][900 / pixelSize];
        float[] pixelX = new float[1440 / pixelSize];
        float[] pixelY = new float[900 / pixelSize];
        float[] distancePixel2 = new float[ParticleAmount];
        float[] centerPX = new float[ParticleAmount];
        float[] centerPY = new float[ParticleAmount];
        float[] viscosityX = new float[ParticleAmount];
        float[] viscosityY = new float[ParticleAmount];
        float[] viscosityMagnitude = new float[ParticleAmount];
        float[] velocityPMapped = new float [ParticleAmount];
        int[] usableParticleH = new int[1440 / pixelSize];
        float xPixel = 1440 / pixelSize;
        float yPixel = 900 / pixelSize;

        for (int j = 0; j < xPixel; j++) {
            for (int k = 0; k < yPixel; k++) {
                frameCounter[j][k] = 0;
                totalAverageVelocity[j][k] = 0;
            }
        }
        // take position of all particles (j, k)

        for (int j = 0; j < xPixel; j++) {
            pixelX[j] = (float) (-720 + (Math.floor(pixelSize / 2)) + pixelSize * j);
        }
        for (int k = 0; k < yPixel; k++) {
            pixelY[k] = (float) (-450 + Math.floor(pixelSize / 2) + pixelSize * k);
        }

        // startup

        Rectangle startBG = new Rectangle(0, 0, 1440, 900);
        root.getChildren().add(startBG);
        startBG.setFill(Color.BLACK);
        startBG.setOpacity(1);

        Button startButton = new Button("FluidSim ⨀");
        Font font4 = Font.font("Georgia", FontWeight.BOLD, 180);
        startButton.setFont(font4);
        root.getChildren().add(startButton);
        startButton.setTranslateX(0);
        startButton.setTranslateY(-0);
        startButton.setStyle("-fx-text-fill: white; -fx-background-color: transparent");
        startButton.setOpacity(1);

        Button startButton2 = new Button("by Tim Ly");
        Font font5 = Font.font("Georgia", FontWeight.BOLD, 70);
        startButton2.setFont(font5);
        root.getChildren().add(startButton2);
        startButton2.setTranslateX(240);
        startButton2.setTranslateY(170);
        startButton2.setStyle("-fx-text-fill: white; -fx-background-color: transparent");
        startButton2.setOpacity(1);

        //animation
        AnimationTimer animationTimer = new AnimationTimer() {

            float startOpacity = 1;
            float[] startFrames = new float[1];
            float airfoilSpeed = 8;
            // radius of particles, trade-off between flow and velocity.
            int animationRadius = (int) radius;
            // between 0 and 1, applied stress
            float shear = 0.9f;
            float slider1X = 0;
            float slider2X = 0;
            float slider3X = 0;
            float slider4X = 0;
            int cfdFrames = 0;
            float viscosityStrength = 0;
            Boolean slider1Check = Boolean.FALSE;
            Boolean slider2Check = Boolean.FALSE;
            Boolean slider3Check = Boolean.FALSE;
            Boolean slider4Check = Boolean.FALSE;
            Boolean frameCounted = Boolean.FALSE;
            float firstMousePointX = 0;
            float firstMousePointY = 0;

            int usableParticle = 0;
            float sumMouseX = 0;
            float sumMouseY = 0;
            int[] mouseState = new int [totalObjects];
            float[] distanceMouseY = new float[drawingFrames];
            float[] distanceMouseX = new float[drawingFrames];
            float[] distanceMouse = new float[drawingFrames];
            float[] forceMagnitudeMouse = new float[drawingFrames];
            double[] pointArray = new double[0];
            int objectAmount = 0;
            boolean objectState = Boolean.TRUE;
            Boolean simulationState = Boolean.TRUE;
            Boolean cfdState = Boolean.FALSE;
            Boolean settings2 = Boolean.TRUE;
            Boolean settingsState = Boolean.TRUE;
            Boolean settingsState2 = Boolean.TRUE;

            Boolean settingsState3 = Boolean.TRUE;

            Boolean nightState = Boolean.TRUE;
            Boolean directionOpacity = Boolean.FALSE;
            Boolean check1 = Boolean.TRUE;
            Boolean check2 = Boolean.TRUE;
            Boolean check3 = Boolean.TRUE;
            Boolean check4 = Boolean.TRUE;
            Boolean intersection = Boolean.TRUE;
            Boolean tangent = Boolean.FALSE;
            Boolean intersection2 = Boolean.TRUE;
            // toggle on if mouse forces are to be calculated.
            Boolean forceMouse = Boolean.FALSE;
            Boolean addState = Boolean.FALSE;
            Boolean exitAdded = Boolean.FALSE;
            Boolean cfdReset = Boolean.FALSE;
            Boolean setStart = Boolean.FALSE;
            float settingsOpacity = 0;
            float progressOpacity = 0;
            float cfdOpacity = 0;

            int settingsColor = 0;

            float colorMultiplier = 1;
            float viscosity = (float) 0.5f;
            float viscosityToggle = 0;

            private void cfdLoop() {

                for (int j = 0; j < xPixel; j++) {
                    for (int k = 0; k < yPixel; k++) {
                        for (int i = 0; i < usableParticleH[j]; i++) {
                            if ((Math.abs(centerPY1[j][i] - pixelY[k]) < cfdRadius)) {
                                distancePixel2[i] = (float) (((Math.pow(centerPX1[j][i] - pixelX[j], 2)) + (Math.pow(centerPY1[j][i] - pixelY[k], 2))) / (Math.pow(cfdRadius, 2)));
                                if (distancePixel2[i] < 1) {
                                    // weighted average depending on the distance it is away.
                                    velocityWeight[i] = (float) (2 * Math.pow(distancePixel2[i], 3) - 3 * Math.pow(distancePixel2[i], 2) + 1);
                                    velocityCount[j][k] += velocityWeight[i];
                                    totalVelocity[j][k] += velocityPMapped1[j][i] * velocityWeight[i];
                                    if (!frameCounted) {
                                        frameCounter[j][k] += 1;
                                    }
                                }
                            }
                        }
                        frameCounted = Boolean.FALSE;
                        averageVelocity[j][k] = totalVelocity[j][k] / velocityCount[j][k];
                        totalAverageVelocity[j][k] += averageVelocity[j][k];
                    }
                }

                if (progressOpacity <= 1) {
                    progressOpacity = progressOpacity + 0.07f;
                }

                progressBar.setOpacity(progressOpacity);
                progressBar2.setOpacity(progressOpacity);
                progressBar3.setOpacity(progressOpacity);

                cfdFrames = cfdFrames + 1;
                progressBar3.setWidth((cfdFrames * 240 )/ totalFrames);
                progressBar3.setTranslateX(-120 + (cfdFrames * 120 / totalFrames));

                // prevent other buttons from working.
                pause.setOpacity(0);
                settings.setOpacity(0);
                settings.setOnAction((ActionEvent actionEvent2) -> {
                });
            }

            private void colorLoop() {
                // place all dots.

                for (int j = 0; j < xPixel; j++) {
                    for (int k = 0; k < yPixel; k++) {
                        cfdPixel[j][k].setOpacity(cfdOpacity);
                        cfdPixel[j][k].setTranslateY(-450 + (Math.floor(pixelSize / 2)) + pixelSize * k);

                        if (frameCounter[j][k] == 0) {
                            totalVelocityCount[j][k] = 0;
                        } else {
                            totalVelocityCount[j][k] = totalAverageVelocity[j][k] / frameCounter[j][k];
                        }

                        pixelColor1[j][k] = (int) (Math.abs(762 * averageVelocity[j][k]) - 254);
                        pixelColor2[j][k] = (int) (-Math.abs(762 * -averageVelocity[j][k] + 254) + 504);
                        pixelColor3[j][k] = (int) (-Math.abs(762 * -averageVelocity[j][k] - 254) + 504);

                        // check if out of bounds for rgb function
                        pixelColor1[j][k] = Math.min(255, pixelColor1[j][k]);
                        pixelColor1[j][k] = Math.max(0, pixelColor1[j][k]);
                        pixelColor2[j][k] = Math.min(255, pixelColor2[j][k]);
                        pixelColor2[j][k] = Math.max(0, pixelColor2[j][k]);
                        pixelColor3[j][k] = Math.min(255, pixelColor3[j][k]);
                        pixelColor3[j][k] = Math.max(0, pixelColor3[j][k]);

                        cfdPixel[j][k].setFill(Color.rgb(pixelColor1[j][k], pixelColor2[j][k], pixelColor3[j][k]));
                    }
                }
            }

            private void resetLoop() {
                for (int j = 0; j < xPixel; j++) {
                    for (int k = 0; k < yPixel; k++) {
                        // variables and pixels are reset.
                        totalVelocity[j][k] = 0;
                        velocityCount[j][k] = 0;
                        totalVelocityCount[j][k] = 0;
                        totalAverageVelocity[j][k] = 0;
                        frameCounter[j][k] = 0;

                        cfdPixel[j][k].setOpacity(cfdOpacity);

                        if (cfdOpacity <= 0.07) {
                            cfdPixel[j][k].setTranslateY(900);
                        }
                    }
                }

                exitButton.setOnAction((ActionEvent actionEvent3) -> {
                });
            }

            private void cfdPrepLoop() {
                usableParticle = 0;
                for (int i = 0; i < ParticleAmount; i++) {

                    if (!((centerX[i] - 720) > cfdRadius || (centerY[i] - 450) > cfdRadius || (centerX[i] + 720) < -cfdRadius || (centerY[i] + 450) < -cfdRadius)) {
                        centerPX[usableParticle] = centerX[i];
                        centerPY[usableParticle] = centerY[i];
                        velocityPMapped[usableParticle] = velocityMapped[i];
                        usableParticle = usableParticle + 1;
                    }
                }

                // horizontal scanning (each column)
                for (int j = 0; j < xPixel; j++) {
                    usableParticleH[j] = 0;
                    for (int i = 0; i < usableParticle; i++) {
                        if (Math.abs(centerPX[i] - pixelX[j]) < cfdRadius) {
                            centerPX1[j][usableParticleH[j]] = centerPX[i];
                            centerPY1[j][usableParticleH[j]] = centerPY[i];
                            velocityPMapped1[j][usableParticleH[j]] = velocityPMapped[i];
                            usableParticleH[j] = usableParticleH[j] + 1;
                        }
                    }
                }
            }

            private void delete() {
                for (int h = 1; h <= objectAmount; h++) {
                    for (int o = 1; o < mouseState[h]; o++) {
                        mouseX[h][o] = 10000;
                        mouseY[h][o] = 10000;
                    }
                    mouseState[h] = 0;
                    polygon[h].setFill(Color.TRANSPARENT);
                    polygon[h].setStroke(Color.TRANSPARENT);
                }
                objectAmount = 0;
            }

            public float calculateForceMagnitude(float a, float b) {

                float magnitude = 0;

                // calculates the angle of the force applied (to split up later), and the force itself.
                if (b <= (densityRadius * densityRadius)) {
                    // if distance is less than the radius, repel. (decreasing high density)
                    magnitude = (float) (120 * (Math.pow((densityRadius * densityRadius - b), 3)) / (Math.pow(densityRadius, 6)));
                } else if (3 * densityRadius > a && a > densityRadius) {
                    // if the distance is between 1 - 2, apply force that pulls (increasing lower density)
                    magnitude = (float) (-2 * Math.pow((a - densityRadius) / (a + 1), 2));
                }

                return magnitude;
            }

            public float calculateViscosityMagnitude(float a, float b) {

                float magnitude = 0;

                // calculates the angle of the force applied (to split up later), and the force itself.
                if (b <= (densityRadius * densityRadius)) {

                    // if the distance is between 1 - 2, apply force that pulls (increasing lower density)
                    magnitude = (float) (viscosityStrength / (Math.pow(a, 2) + 1));
                }

                return magnitude;
            }

            public float sine(float a) {
                float dummyVariable = (float) (a - Math.pow(a, 3)/6 + Math.pow(a, 5)/120 - Math.pow(a, 7)/5040 + Math.pow(a, 9)/362880);
                return dummyVariable;
            }
            public float cosine(float b) {
                float dummyVariable2 = (float) (1 - Math.pow(b, 2)/2 + Math.pow(b, 4)/24 - Math.pow(b, 6)/720 + Math.pow(b, 8)/40320);
                return dummyVariable2;
            }

            public String grayScale(int c) {
                int dummyVariable3 = c / 16;
                int dummyVariable4 = c % 16;

                String dummyVariable5 = Integer.toString(dummyVariable3);
                String dummyVariable6 = Integer.toString(dummyVariable4);

                if (dummyVariable3 == 10) {
                    dummyVariable5 = "A";
                } else if (dummyVariable3 == 11) {
                    dummyVariable5 = "B";
                } else if (dummyVariable3 == 12) {
                    dummyVariable5 = "C";
                } else if (dummyVariable3 == 13) {
                    dummyVariable5 = "D";
                } else if (dummyVariable3 == 14) {
                    dummyVariable5 = "E";
                } else if (dummyVariable3 == 15) {
                    dummyVariable5 = "F";
                }

                if (dummyVariable4 == 10) {
                    dummyVariable6 = "A";
                } else if (dummyVariable4 == 11) {
                    dummyVariable6 = "B";
                } else if (dummyVariable4 == 12) {
                    dummyVariable6 = "C";
                } else if (dummyVariable4 == 13) {
                    dummyVariable6 = "D";
                } else if (dummyVariable4 == 14) {
                    dummyVariable6 = "E";
                } else if (dummyVariable4 == 15) {
                    dummyVariable6 = "F";
                }

                String hexValue = ("#" + dummyVariable5 + dummyVariable6 + dummyVariable5 + dummyVariable6 + dummyVariable5 + dummyVariable6);

                return hexValue;
            }

            public void handle(long now) {

                if (!setStart) {
                    startFrames[0] = 0f;
                    setStart = Boolean.TRUE;
                }

                // only if CFD is not generated.

                if (simulationState) {
                    //center changes by velocity per frame.
                    for (int i = 0; i < ParticleAmount; i++) {
                        // reset force (for calculations) for every frame.
                        objectChecker[i] = 1;
                        forceNetY[i] = 0;
                        forceNetX[i] = 0;
                        finalDistanceX[i] = 0;
                        finalDistanceY[i] = 0;
                        finalCenterX[i] = 0;
                        finalCenterY[i] = 0;

                        // check density between each particle and every other particle.
                        for (int j = 0; j < ParticleAmount; j++) {
                            //distance between every particle (j) and chosen particle (i). if j belongs to the object, calculate center differently.

                            distanceY[j] = centerY[i] - centerY[j];
                            distanceX[j] = centerX[i] - centerX[j];

                            if (Math.abs(distanceY[j]) < densityRadius && Math.abs(distanceX[j]) < densityRadius) {

                                distance2[j] = distanceY[j] * distanceY[j] + distanceX[j] * distanceX[j];
                                distance[j] = (float) Math.sqrt(distance2[j]);

                                forceMagnitude[j] = calculateForceMagnitude(distance[j], distance2[j]);

                                if (distance2[j] != 0 && distance2[j] < (9 * Math.pow(densityRadius, 2))) {

                                    if (Math.random() < viscosity) {
                                        viscosityMagnitude[j] = 100 * viscosityToggle * calculateViscosityMagnitude(distance[j], distance2[j]);
                                        viscosityY[j] = (distanceY[j] / distance[j]) * viscosityMagnitude[j];
                                        viscosityX[j] = (distanceX[j] / distance[j]) * viscosityMagnitude[j];
                                    } else {
                                        viscosityY[j] = 0;
                                        viscosityX[j] = 0;
                                    }

                                    // define net force, use absolute value + signum due to arctan only taking positive values and range of cosine being only positive.
                                    // outputs the net "force" in component directions.
                                    forceNetY[i] = (forceNetY[i] + viscosityY[j] + (distanceY[j] / distance[j]) * forceMagnitude[j]);
                                    forceNetX[i] = (forceNetX[i] + viscosityX[j] + (distanceX[j] / distance[j]) * forceMagnitude[j]);
                                }
                            }
                        }

                        // velocity updater
                        velocityX[i] = (float) (airfoilSpeed + (0.2 * forceNetX[i] + velocityX[i]) / 1.8);
                        velocityY[i] = (float) ((0.2 * forceNetY[i] + velocityY[i] + gravity) / 1.8);


                        centerX1[i] = (float) (circle[i].getTranslateX());
                        centerY1[i] = (float) (circle[i].getTranslateY());

                        centerX2[i] = (float) (circle[i].getTranslateX() + velocityX[i]);
                        centerY2[i] = (float) (circle[i].getTranslateY() + velocityY[i]);

                        particleDistanceX[i] = centerX2[i] - centerX1[i];
                        particleDistanceY[i] = centerY2[i] - centerY1[i];

                        particleSlope[i] = particleDistanceY[i] / particleDistanceX[i];
                        particleSlope2[i] = (float) Math.pow(particleSlope[i], 2);
                        particleInverseSlope[i] = 1 / particleSlope[i];

                        // calculate for intersections.
                        for (int h = 0; h <= objectAmount; h++) {
                            for (int o = 1; o <= mouseState[h]; o++) {
                                objectCenterX1[h][o] = 0;
                                objectCenterX2[h][o] = 0;
                                objectCenterY1[h][o] = 0;
                                objectCenterY2[h][o] = 0;

                                objectDistanceX[h][o] = 0;
                                objectDistanceY[h][o] = 0;

                                objectSlope[h][o] = 0;
                                objectInverseSlope[h][o] = 0;

                                intersectionCenterX[h][o] = 0;
                                intersectionCenterY[h][o] = 0;

                                particleIntDistanceX[h][o] = 0;
                                particleIntDistanceY[h][o] = 0;

                                objectCenterX1[h][o] = mouseX[h][o];
                                objectCenterY1[h][o] = mouseY[h][o];
                                if (o == 1) {
                                    // exception
                                    objectCenterX2[h][o] = mouseX[h][mouseState[h]];
                                    objectCenterY2[h][o] = mouseY[h][mouseState[h]];
                                } else {
                                    // point 2 = different point.
                                    objectCenterX2[h][o] = mouseX[h][o - 1];
                                    objectCenterY2[h][o] = mouseY[h][o - 1];
                                }

                                objectDistanceX[h][o] = objectCenterX2[h][o] - objectCenterX1[h][o];
                                objectDistanceY[h][o] = objectCenterY2[h][o] - objectCenterY1[h][o];

                                // calculate intersection points
                                objectSlope[h][o] = objectDistanceY[h][o] / objectDistanceX[h][o];
                                objectInverseSlope[h][o] = 1 / objectSlope[h][o];

                                intersectionCenterX[h][o] = (centerY1[i] - objectCenterY2[h][o] + objectSlope[h][o] * objectCenterX2[h][o] - particleSlope[i] * centerX1[i]) / (objectSlope[h][o] - particleSlope[i]);
                                intersectionCenterY[h][o] = (centerX1[i] - objectCenterX2[h][o] + objectInverseSlope[h][o] * objectCenterY2[h][o] - particleInverseSlope[i] * centerY1[i]) / (objectInverseSlope[h][o] - particleInverseSlope[i]);

                                if (objectCenterX2[h][o] - objectCenterX1[h][o] == 0) {
                                    intersectionCenterX[h][o] = objectCenterX1[h][o];
                                } else if (objectCenterY2[h][o] - objectCenterY1[h][o] == 0) {
                                    intersectionCenterY[h][o] = objectCenterY1[h][o];
                                }

                                check1 = (intersectionCenterX[h][o] < Math.max(objectCenterX1[h][o], objectCenterX2[h][o])) && (intersectionCenterX[h][o] > Math.min(objectCenterX1[h][o], objectCenterX2[h][o]));
                                check2 = (intersectionCenterY[h][o] < Math.max(objectCenterY1[h][o], objectCenterY2[h][o])) && (intersectionCenterY[h][o] > Math.min(objectCenterY1[h][o], objectCenterY2[h][o]));

                                check3 = (intersectionCenterX[h][o] < Math.max(centerX1[i], centerX2[i])) && (intersectionCenterX[h][o] > Math.min(centerX1[i], centerX2[i]));
                                check4 = (intersectionCenterY[h][o] < Math.max(centerY1[i], centerY2[i])) && (intersectionCenterY[h][o] > Math.min(centerY1[i], centerY2[i]));


                                // checking if the particle distance is after or before the current velocity vector
                                particleIntDistanceX[h][o] = (centerX1[i] - intersectionCenterX[h][o]);
                                particleIntDistanceY[h][o] = (centerY1[i] - intersectionCenterY[h][o]);

                                // checks if intersects on the line
                                if (check1 || check2) {
                                    // if checks pass, multiply by a counter.
                                    if ((particleIntDistanceX[h][o] * velocityX[i]) > 0 || (particleIntDistanceY[h][o] * velocityY[i]) > 0) {
                                        objectChecker[i] = objectChecker[i] * -1;
                                    }
                                }

                                //calcluate new intersections
                                if ((check1 || check2) && (check3 || check4)) {

                                    // make sure it's the first intersection
                                    if ((Math.abs(finalDistanceX[i]) < Math.abs(particleIntDistanceX[h][o])) || (Math.abs(finalDistanceY[i]) < Math.abs(particleIntDistanceY[h][o]))) {

                                        // modify variables
                                        finalDistanceX[i] = (intersectionCenterX[h][o] - centerX1[i]);
                                        finalDistanceY[i] = (intersectionCenterY[h][o] - centerY1[i]);

                                        tangent = (Math.abs(finalDistanceX[i]) < 7 && Math.abs(finalDistanceX[i]) > 0) || (Math.abs(finalDistanceY[i]) < 7 && Math.abs(finalDistanceY[i]) > 0);

                                        finalCenterX[i] = intersectionCenterX[h][o];
                                        finalCenterY[i] = intersectionCenterY[h][o];

                                        objectDistanceX[h][o] = objectDistanceX[h][o];
                                        objectDistanceY[h][o] = objectDistanceY[h][o];

                                        if (tangent) {

                                            magnitudeFinal[i] = (float) ((particleDistanceX[i] * objectDistanceX[h][o] + particleDistanceY[i] * objectDistanceY[h][o]) / (Math.pow(objectDistanceX[h][o], 2) + Math.pow(objectDistanceY[h][o], 2)));

                                            // velocityX2[i] = (float) (Math.signum(objectSlope[h][o]) * magnitudeFinal[i] / (Math.sqrt(1 + objectSlope[h][o] * objectSlope[h][o])));
                                            // velocityY2[i] = (float) (Math.signum(objectSlope[h][o]) * magnitudeFinal[i] / (Math.sqrt(1 + objectInverseSlope[h][o] * objectInverseSlope[h][o])));
                                            velocityX2[i] = magnitudeFinal[i] * objectDistanceX[h][o];
                                            velocityY2[i] = magnitudeFinal[i] * objectDistanceY[h][o];
                                        }

                                    }
                                    intersection = Boolean.TRUE;
                                }

                                // calculate new velocities
                                if (intersection) {
                                    velocityX[i] = (float) (finalDistanceX[i]);
                                    velocityY[i] = (float) (finalDistanceY[i]);

                                    if (tangent) {
                                        velocityX[i] = shear * velocityX2[i];
                                        velocityY[i] = shear * velocityY2[i];
                                    }
                                }

                                if (intersection && forceMouse) {
                                    // to make it go away from the wall a bit
                                    if (velocityX[i] > 0) {
                                        if ((objectSlope[h][o] > 0) && (particleSlope[i] > 0)) {
                                            if (objectSlope[h][o] > particleSlope[i]) {
                                                if (Math.abs(velocityY[i]) > Math.abs(velocityX[i])) {
                                                    velocityY[i] = velocityY[i] + 0.5f;
                                                } else {
                                                    velocityX[i] = velocityX[i] - 0.5f;
                                                }
                                            } else {
                                                if (Math.abs(velocityY[i]) > Math.abs(velocityX[i])) {
                                                    velocityY[i] = velocityY[i] - 0.5f;
                                                } else {
                                                    velocityX[i] = velocityX[i] + 0.5f;
                                                }
                                            }
                                        }

                                        if ((objectSlope[h][o] < 0) && (particleSlope[i] < 0)) {
                                            if (objectSlope[h][o] > particleSlope[i]) {
                                                if (Math.abs(velocityY[i]) > Math.abs(velocityX[i])) {
                                                    velocityY[i] = velocityY[i] + 0.5f;
                                                } else {
                                                    velocityX[i] = velocityX[i] + 0.5f;
                                                }
                                            } else {
                                                if (Math.abs(velocityY[i]) > Math.abs(velocityX[i])) {
                                                    velocityY[i] = velocityY[i] - 0.5f;
                                                } else {
                                                    velocityX[i] = velocityX[i] - 0.5f;
                                                }
                                            }
                                        }

                                        if ((objectSlope[h][o] > 0) && (particleSlope[i] < 0)) {
                                            if (Math.abs(velocityY[i]) > Math.abs(velocityX[i])) {
                                                velocityY[i] = velocityY[i] + 0.5f;
                                            } else {
                                                velocityX[i] = velocityX[i] - 0.5f;
                                            }
                                        }

                                        if ((objectSlope[h][o] < 0) && (particleSlope[i] > 0)) {
                                            if (Math.abs(velocityY[i]) > Math.abs(velocityX[i])) {
                                                velocityY[i] = velocityY[i] - 0.5f;
                                            } else {
                                                velocityX[i] = velocityX[i] - 0.5f;
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        // do again in case the shape is convex.
                        if (tangent) {
                            centerX1[i] = (float) (circle[i].getTranslateX());
                            centerY1[i] = (float) (circle[i].getTranslateY());

                            centerX2[i] = (float) (circle[i].getTranslateX() + velocityX[i]);
                            centerY2[i] = (float) (circle[i].getTranslateY() + velocityY[i]);

                            particleDistanceX[i] = centerX2[i] - centerX1[i];
                            particleDistanceY[i] = centerY2[i] - centerY1[i];

                            particleSlope[i] = particleDistanceY[i] / particleDistanceX[i];
                            particleSlope2[i] = (float) Math.pow(particleSlope[i], 2);
                            particleInverseSlope[i] = 1 / particleSlope[i];

                            for (int h = 0; h <= objectAmount; h++) {
                                for (int o = 1; o <= mouseState[h]; o++) {
                                    objectCenterX1[h][o] = 0;
                                    objectCenterX2[h][o] = 0;
                                    objectCenterY1[h][o] = 0;
                                    objectCenterY2[h][o] = 0;

                                    objectDistanceX[h][o] = 0;
                                    objectDistanceY[h][o] = 0;

                                    objectSlope[h][o] = 0;
                                    objectInverseSlope[h][o] = 0;

                                    intersectionCenterX[h][o] = 0;
                                    intersectionCenterY[h][o] = 0;

                                    particleIntDistanceX[h][o] = 0;
                                    particleIntDistanceY[h][o] = 0;

                                    objectCenterX1[h][o] = mouseX[h][o];
                                    objectCenterY1[h][o] = mouseY[h][o];

                                    if (o == 1) {
                                        // exception
                                        objectCenterX2[h][o] = mouseX[h][mouseState[h]];
                                        objectCenterY2[h][o] = mouseY[h][mouseState[h]];
                                    } else {
                                        // point 2 = different point.
                                        objectCenterX2[h][o] = mouseX[h][o - 1];
                                        objectCenterY2[h][o] = mouseY[h][o - 1];
                                    }

                                    objectDistanceX[h][o] = objectCenterX2[h][o] - objectCenterX1[h][o];
                                    objectDistanceY[h][o] = objectCenterY2[h][o] - objectCenterY1[h][o];

                                    // calculate intersection points
                                    objectSlope[h][o] = objectDistanceY[h][o] / objectDistanceX[h][o];
                                    objectInverseSlope[h][o] = 1 / objectSlope[h][o];

                                    intersectionCenterX[h][o] = (centerY1[i] - objectCenterY2[h][o] + objectSlope[h][o] * objectCenterX2[h][o] - particleSlope[i] * centerX1[i]) / (objectSlope[h][o] - particleSlope[i]);
                                    intersectionCenterY[h][o] = (centerX1[i] - objectCenterX2[h][o] + objectInverseSlope[h][o] * objectCenterY2[h][o] - particleInverseSlope[i] * centerY1[i]) / (objectInverseSlope[h][o] - particleInverseSlope[i]);

                                    if (objectCenterX2[h][o] - objectCenterX1[h][o] == 0) {
                                        intersectionCenterX[h][o] = objectCenterX1[h][o];
                                    } else if (objectCenterY2[h][o] - objectCenterY1[h][o] == 0) {
                                        intersectionCenterY[h][o] = objectCenterY1[h][o];
                                    }

                                    // check if within domain, add 1 for a range of tolerance.
                                    check1 = (intersectionCenterX[h][o] < (Math.max(objectCenterX1[h][o], objectCenterX2[h][o]) + 0)) && (intersectionCenterX[h][o] > (Math.min(objectCenterX1[h][o], objectCenterX2[h][o]) - 0));
                                    check2 = (intersectionCenterY[h][o] < (Math.max(objectCenterY1[h][o], objectCenterY2[h][o]) + 0)) && (intersectionCenterY[h][o] > (Math.min(objectCenterY1[h][o], objectCenterY2[h][o]) - 0));

                                    check3 = (intersectionCenterX[h][o] < Math.max(centerX1[i], centerX2[i])) && (intersectionCenterX[h][o] > Math.min(centerX1[i], centerX2[i]));
                                    check4 = (intersectionCenterY[h][o] < Math.max(centerY1[i], centerY2[i])) && (intersectionCenterY[h][o] > Math.min(centerY1[i], centerY2[i]));


                                    // checking if the particle distance is after or before the current velocity vector
                                    particleIntDistanceX[h][o] = (centerX1[i] - intersectionCenterX[h][o]);
                                    particleIntDistanceY[h][o] = (centerY1[i] - intersectionCenterY[h][o]);

                                    //calcluate new intersections
                                    if ((check1 || check2) && (check3 || check4)) {

                                        // make sure it's the first intersection
                                        if ((Math.abs(finalDistanceX[i]) < Math.abs(particleIntDistanceX[h][o])) || (Math.abs(finalDistanceY[i]) < Math.abs(particleIntDistanceY[h][o]))) {

                                            // modify variables
                                            finalDistanceX[i] = (intersectionCenterX[h][o] - centerX1[i]);
                                            finalDistanceY[i] = (intersectionCenterY[h][o] - centerY1[i]);

                                            finalCenterX[i] = intersectionCenterX[h][o];
                                            finalCenterY[i] = intersectionCenterY[h][o];

                                            objectDistanceX[h][o] = objectDistanceX[h][o];
                                            objectDistanceY[h][o] = objectDistanceY[h][o];

                                        }
                                        intersection2 = Boolean.TRUE;
                                    }

                                    // calculate new velocities
                                    if (intersection2) {
                                        velocityX[i] = (float) 0.85 * (finalDistanceX[i]);
                                        velocityY[i] = (float) 0.85 * (finalDistanceY[i]);

                                        // slw down velocities near boundary conditions
                                        // extra decmials to ensure continuity

                                        if (Math.abs(velocityX[i]) <= 0.125) {
                                            velocityX[i] = (float) (64 * (Math.signum(velocityX[i]) * Math.pow(velocityX[i], 5)));
                                        } else if (Math.abs(velocityX[i]) <= 0.25) {
                                            velocityX[i] = (float) (8 * (Math.signum(velocityX[i]) * Math.pow(velocityX[i], 4)));
                                        } else if (Math.abs(velocityX[i]) <= 0.5) {
                                            velocityX[i] = (float) (2 * (Math.signum(velocityX[i]) * Math.abs(Math.pow(velocityX[i], 3))));
                                        } else if (Math.abs(velocityX[i]) <= 1) {
                                            velocityX[i] = (float) (Math.signum(velocityX[i]) * Math.abs(Math.pow(velocityX[i], 2)));
                                        }

                                        if (Math.abs(velocityY[i]) <= 0.125) {
                                            velocityY[i] = (float) (64 * (Math.signum(velocityY[i]) * Math.pow(velocityY[i], 5)));
                                        } else if (Math.abs(velocityY[i]) <= 0.25) {
                                            velocityY[i] = (float) (8 * (Math.signum(velocityY[i]) * Math.pow(velocityY[i], 4)));
                                        } else if (Math.abs(velocityY[i]) <= 0.5) {
                                            velocityY[i] = (float) (2 * (Math.signum(velocityY[i]) * Math.abs(Math.pow(velocityY[i], 3))));
                                        } else if (Math.abs(velocityY[i]) <= 1) {
                                            velocityY[i] = (float) (Math.signum(velocityY[i]) * Math.abs(Math.pow(velocityY[i], 2)));
                                        }
                                    }
                                }
                            }
                        }


                        if (intersection) {
                            // change center if intersects, a bit less so it won't go in the object.
                            centerX[i] = (float) (circle[i].getTranslateX() + 0.95 * velocityX[i]);
                            centerY[i] = (float) (circle[i].getTranslateY() + 0.95 * velocityY[i]);
                        } else {
                            // calculate new position.if no intersection
                            centerY[i] = (float) (circle[i].getTranslateY() + velocityY[i]);
                            centerX[i] = (float) (circle[i].getTranslateX() + velocityX[i]);

                        }

                        // delete objects that are inside
                        if (objectChecker[i] == -1 && intersection && (Math.abs(finalDistanceX[i]) >= 12) || (Math.abs(finalDistanceY[i]) >= 12)) {
                            //set surrounded objects to tart
                            centerX[i] = -1200 + (float) (Math.random() * 100);
                            centerY[i] = (float) (Math.random() * 900) - 450;

                            velocityX[i] = 2;
                            velocityY[i] = 0;

                        } else if (objectChecker[i] == -1 && !intersection) {
                            centerX[i] = -1200 + (float) (Math.random() * 100);
                            centerY[i] = (float) (Math.random() * 900) - 450;

                            velocityX[i] = 2;
                            velocityY[i] = 0;
                        }

                        intersection = Boolean.FALSE;
                        intersection2 = Boolean.FALSE;

                        // reset
                        objectChecker[i] = 1;

                        // boundary checker
                        if (centerY[i] + velocityY[i] >= 540) {
                            // velocityY[i] = -dampeningCoefficient * velocityY[i] - 1; (use later if need)
                            centerY[i] = 540;
                        } else if (centerY[i] + velocityY[i] <= -540) {
                            // velocityY[i] = -dampeningCoefficient * velocityY[i] + 1;
                            centerY[i] = -540;
                        }
                        if (centerX[i] + velocityX[i] >= 1100) {
                            // velocityX[i] = -dampeningCoefficient * velocityX[i] - 1;
                            centerX[i] = -1200 + (float) (Math.random() * 100);
                            centerY[i] = (float) (Math.random() * 900) - 450;
                            velocityX[i] = 2;
                            velocityY[i] = 0;
                        } else if (centerX[i] + velocityX[i] <= -1100) {
                            // velocityX[i] = -dampeningCoefficient * velocityX[i] + 1;
                            centerX[i] = -1200 + (float) (Math.random() * 100);
                            centerY[i] = (float) (Math.random() * 900) - 450;
                            velocityX[i] = 2;
                            velocityY[i] = 0;
                        }

                        // position updater
                        // translate
                        circle[i].setTranslateY(centerY[i]);
                        circle[i].setTranslateX(centerX[i]);

                        if (directionOpacity) {
                            // vector lines and slopes if button is activated
                            testingLine[i].setTranslateX(centerX[i]);
                            testingLine[i].setTranslateY(centerY[i]);

                            slopeVariable[i] = (float) Math.sqrt(1 + particleSlope2[i]);

                            testingLine[i].setStartY(-2 * animationRadius * particleSlope[i] / slopeVariable[i]);
                            testingLine[i].setEndY(2 * animationRadius * particleSlope[i] / slopeVariable[i]);
                            testingLine[i].setStartX(-2 * animationRadius / slopeVariable[i]);
                            testingLine[i].setEndX(2 * animationRadius / slopeVariable[i]);
                            testingLine[i].setStrokeWidth(0.8 * animationRadius);
                        }

                        velocityDifference[i] = (float) (-7 * airfoilSpeed + Math.sqrt(velocityX[i] * velocityX[i] + velocityY[i] * velocityY[i]));
                        if (velocityDifference[i] <= 0) {
                            velocityMapped[i] = (float) (-0.75 * Math.abs(velocityDifference[i] / (7 * airfoilSpeed)) + 0.25);
                        } else if (velocityDifference[i] > 7 * airfoilSpeed) {
                            // max velocity
                            velocityMapped[i] = 0.75f;
                        } else {
                            velocityMapped[i] = (float) (0.75 * Math.abs(velocityDifference[i] / (7 * airfoilSpeed)) + 0.25);
                        }

                        // color code (and old code)

                        //variableColor1[i] = (int) (Math.abs(127 * 6 * velocityMapped[i]) - 127 * 2);
                        //variableColor2[i] = (int) (-Math.abs(127 * 6 * (-velocityMapped[i] + 0.333)) + 127 * 4);
                        //variableColor3[i] = (int) (-Math.abs(127 * 6 * (-velocityMapped[i] - 0.333)) + 127 * 4);

                        variableColor1[i] = (int) (Math.abs(762 * velocityMapped[i]) - 254);
                        variableColor2[i] = (int) (-Math.abs(762 * -velocityMapped[i] + 254) + 504);
                        variableColor3[i] = (int) (-Math.abs(762 * -velocityMapped[i] - 254) + 504);

                        // testing Circle is unused atm
                        testingCircle[i].setOpacity(0);
                        testingCircle[i].setTranslateX(720);
                        testingCircle[i].setTranslateY(450);

                        // check if out of bounds for rgb function
                        if (variableColor1[i] > 255) {
                            variableColor1[i] = 255;
                        } else if (variableColor1[i] < 0) {
                            variableColor1[i] = 0;
                        }
                        if (variableColor2[i] > 255) {
                            variableColor2[i] = 255;
                        } else if (variableColor2[i] < 0) {
                            variableColor2[i] = 0;
                        }
                        if (variableColor3[i] > 255) {
                            variableColor3[i] = 255;
                        } else if (variableColor3[i] < 0) {
                            variableColor3[i] = 0;
                        }

                        variableColor1[i] = (int) (colorMultiplier * variableColor1[i]);
                        variableColor2[i] = (int) (colorMultiplier * variableColor2[i]);
                        variableColor3[i] = (int) (colorMultiplier * variableColor3[i]);

                        // actual filling function

                        circle[i].setFill(Color.rgb(variableColor1[i], variableColor2[i], variableColor3[i]));
                        testingLine[i].setStroke(Color.rgb(variableColor1[i], variableColor2[i], variableColor3[i]));
                        circle[i].setOpacity(0.5);

                        circle[i].setRadius(animationRadius);
                    }
                }

                // prevent drawing if CFD is being run.
                if (!cfdState && simulationState) {

                    // check if mouse is pressed/dragged (take location while it is dragged)
                    root.setOnMouseDragged(e -> {
                        if (settingsState2) {
                            firstMousePointX = (float) (e.getX() - 720);
                            // slightly less due to weird mouse
                            firstMousePointY = (float) (e.getY() - 440);
                            settingsState2 = Boolean.FALSE;
                        }
                        if (!settingsState) {
                            firstMousePointX = (float) (e.getX() - 720);

                            // prevent sliders from going outside of the comfortable X range
                            if ((Math.abs(firstMousePointY - slider1.getTranslateY())) < 17) {
                                slider1.setTranslateX(firstMousePointX);
                            } else if ((Math.abs(firstMousePointY - slider2.getTranslateY())) < 17) {
                                slider2.setTranslateX(firstMousePointX);
                            } else if ((Math.abs(firstMousePointY - slider3.getTranslateY())) < 17) {
                                slider3.setTranslateX(firstMousePointX);
                            } else if ((Math.abs(firstMousePointY - slider4.getTranslateY())) < 17) {
                                slider4.setTranslateX(firstMousePointX);
                            }

                            if (slider1.getTranslateX() > -405) {
                                slider1.setTranslateX(-405);
                            } else if (slider1.getTranslateX() < -675) {
                                slider1.setTranslateX(-675);
                            }
                            if (slider2.getTranslateX() > -405) {
                                slider2.setTranslateX(-405);
                            } else if (slider2.getTranslateX() < -675) {
                                slider2.setTranslateX(-675);
                            }
                            if (slider3.getTranslateX() > -405) {
                                slider3.setTranslateX(-405);
                            } else if (slider3.getTranslateX() < -675) {
                                slider3.setTranslateX(-675);
                            }
                            if (slider4.getTranslateX() > -405) {
                                slider4.setTranslateX(-405);
                            } else if (slider4.getTranslateX() < -675) {
                                slider4.setTranslateX(-675);
                            }
                        }
                        slider1Check = (Math.abs(firstMousePointX - slider1.getTranslateX()) < 17) && (Math.abs(firstMousePointY - slider1.getTranslateY()) < 17);
                        slider2Check = (Math.abs(firstMousePointY - slider2.getTranslateY()) < 17) && (Math.abs(firstMousePointX - slider2.getTranslateX()) < 17);
                        slider3Check = (Math.abs(firstMousePointY - slider3.getTranslateY()) < 17) && (Math.abs(firstMousePointX - slider3.getTranslateX()) < 17);
                        slider4Check = (Math.abs(firstMousePointY - slider4.getTranslateY()) < 17) && (Math.abs(firstMousePointX - slider4.getTranslateX()) < 17);

                        if (!settings2 && (slider1Check || slider2Check || slider3Check || slider4Check)) {
                            settingsState = Boolean.FALSE;
                        } else if (settingsState) {
                            if (objectState) {
                                objectAmount = objectAmount + 1;
                                objectState = Boolean.FALSE;
                            }
                            mouseState[objectAmount] = mouseState[objectAmount] + 1;
                            mouseX[objectAmount][mouseState[objectAmount]] = (float) (e.getX() - 720);
                            mouseY[objectAmount][mouseState[objectAmount]] = (float) (e.getY() - 450);

                            // testing System.out.println(mouseX[mouseState]);
                            // testing System.out.println(mouseY[mouseState]);

                            initialPoint[mouseState[objectAmount]].setTranslateX(mouseX[objectAmount][mouseState[objectAmount]]);
                            initialPoint[mouseState[objectAmount]].setTranslateY(mouseY[objectAmount][mouseState[objectAmount]]);
                            if (mouseState[objectAmount] == 1) {
                                // highlight initial point
                                initialPoint[1].setFill(Color.rgb(128, 128, 128));
                                initialPoint[1].setRadius(30);
                            } else {
                                if (nightState) {
                                    initialPoint[mouseState[objectAmount]].setFill(Color.WHITE);
                                } else {
                                    initialPoint[mouseState[objectAmount]].setFill(Color.BLACK);
                                }
                            }

                        }
                    });

                    // once released, draw polygon. points are taken as regular particles but are not able to be moved
                    root.setOnMouseReleased(e -> {
                        settingsState = Boolean.TRUE;
                        settingsState2 = Boolean.TRUE;

                        float maxMouseX[] = new float[100];
                        float maxMouseY[] = new float[100];
                        float minMouseX[] = new float[100];
                        float minMouseY[] = new float[100];
                        float centerMouseX[] = new float[100];
                        float centerMouseY[] = new float[100];
                        if (!objectState && settingsState) {

                            sumMouseX = 0;
                            sumMouseY = 0;

                            // create new array for polygon
                            double[] pointArray2 = Arrays.copyOf(pointArray, pointArray.length + 2 * mouseState[objectAmount]);

                            for (int k = 1; k <= mouseState[objectAmount]; k++) {

                                // all points go into array
                                pointArray2[2 * k - 2] = mouseX[objectAmount][k];
                                pointArray2[2 * k - 1] = mouseY[objectAmount][k];

                                // centering
                                if (k == 1) {
                                    maxMouseX[objectAmount] = mouseX[objectAmount][k];
                                    maxMouseY[objectAmount] = mouseY[objectAmount][k];
                                    minMouseX[objectAmount] = mouseX[objectAmount][k];
                                    minMouseY[objectAmount] = mouseY[objectAmount][k];
                                }
                                maxMouseX[objectAmount] = Math.max(mouseX[objectAmount][k], maxMouseX[objectAmount]);
                                minMouseX[objectAmount] = Math.min(mouseX[objectAmount][k], minMouseX[objectAmount]);
                                maxMouseY[objectAmount] = Math.max(mouseY[objectAmount][k], maxMouseY[objectAmount]);
                                minMouseY[objectAmount] = Math.min(mouseY[objectAmount][k], minMouseY[objectAmount]);

                                // erase drawing points.
                                initialPoint[k].setFill(Color.TRANSPARENT);
                            }

                            // centering

                            centerMouseX[objectAmount] = (maxMouseX[objectAmount] + minMouseX[objectAmount]) / 2;
                            centerMouseY[objectAmount] = (maxMouseY[objectAmount] + minMouseY[objectAmount]) / 2;

                            // create polygon with points taken while pressed
                            polygon[objectAmount] = new Polygon(pointArray2);
                            root.getChildren().add(polygon[objectAmount]);
                            polygon[objectAmount].setTranslateX(centerMouseX[objectAmount]);
                            polygon[objectAmount].setTranslateY(centerMouseY[objectAmount]);
                            polygon[objectAmount].setStrokeWidth(3);
                            polygon[objectAmount].setOpacity(0.5);

                            objectState = Boolean.TRUE;
                        }
                    });
                } else {
                    // do nothing if either of these are true
                    root.setOnMouseDragged(e -> {
                    });
                }

                // polygon coloring according to night mode

                if (nightState && objectState) {
                    for (int i = 1; i <= objectAmount; i++) {
                        polygon[i].setFill(Color.BLACK);
                        polygon[i].setStroke(Color.WHITE);
                    }
                } else if (objectState) {
                    for (int i = 1; i <= objectAmount; i++) {
                        polygon[i].setFill(Color.WHITE);
                        polygon[i].setStroke(Color.BLACK);
                    }
                }

                // button actions


                settings.setOnAction(actionEvent ->  {
                    if(settings2) {

                        deleteButton.setOnAction((ActionEvent actionEvent2) -> {
                            delete();
                        });

                        // change transparency of direction lines
                        directionButton.setOnAction((ActionEvent actionEvent2) -> {
                            if (directionOpacity) {
                                for (int i = 0; i < ParticleAmount; i++) {
                                    testingLine[i].setOpacity(0);
                                }
                                directionOpacity = Boolean.FALSE;
                            } else {
                                for (int i = 0; i < ParticleAmount; i++) {
                                    testingLine[i].setOpacity(0.34);
                                }
                                directionOpacity = Boolean.TRUE;
                            }
                        });

                        nightButton.setOnAction((ActionEvent actionEvent2) -> {
                            if (nightState) {
                                root.setStyle("-fx-background-color: white");

                                for (int u = 0; u < 26; u++) {
                                    bgLine[u].setStroke(Color.rgb(235, 235, 235));
                                }

                                settings.setStyle("-fx-border-color: white;");
                                colorMultiplier = 0.5f;

                                progressBar.setFill(Color.BLACK);
                                progressBar2.setFill(Color.WHITE);
                                progressBar3.setFill(Color.BLACK);
                                exitButton.setStyle("-fx-text-fill: black; -fx-border-color: transparent; -fx-background-color: transparent; -fx-border-width: 3px");

                                nightState = Boolean.FALSE;
                            } else {
                                root.setStyle("-fx-background-color: black");

                                for (int u = 0; u < 26; u++) {
                                    bgLine[u].setStroke(Color.rgb(20, 20, 20));
                                }

                                colorMultiplier = 1;

                                nightState = Boolean.TRUE;

                                settings.setStyle("-fx-border-color: black;");

                                progressBar.setFill(Color.WHITE);
                                progressBar2.setFill(Color.BLACK);
                                progressBar3.setFill(Color.WHITE);
                                exitButton.setStyle("-fx-text-fill: white; -fx-border-color: transparent; -fx-background-color: transparent; -fx-border-width: 3px");
                            }
                        });

                        settings2 = Boolean.FALSE;
                        settingsState3 = Boolean.FALSE;

                        cfdButton.setOnAction((ActionEvent actionEvent2) -> {

                            cfdState = Boolean.TRUE;
                            settings2 = Boolean.TRUE;
                            settingsState3 = Boolean.TRUE;
                            if (!addState) {

                                root.getChildren().add(progressBar);
                                root.getChildren().add(progressBar2);
                                root.getChildren().add(progressBar3);
                                root.getChildren().add(cfdBG);
                                addState = Boolean.TRUE;
                                root.getChildren().remove(exitButton);

                            }

                            cfdBG.setOpacity(0.4);
                            cfdBG.setTranslateX(0);

                            progressBar.setTranslateX(0);
                            progressBar2.setTranslateX(0);
                            progressBar2.setTranslateX(0);

                        });

                    } else {

                        deleteButton.setOnAction((ActionEvent actionEvent2) -> {
                        });

                        nightButton.setOnAction((ActionEvent actionEvent2) -> {
                        });

                        directionButton.setOnAction((ActionEvent actionEvent2) -> {
                        });

                        cfdButton.setOnAction((ActionEvent actionEvent2) -> {
                        });

                        settings2 = Boolean.TRUE;
                        settingsState3 = Boolean.TRUE;
                    }
                });

                if (settingsState3) {

                    if (settingsOpacity >= 0) {
                        settingsOpacity = settingsOpacity - 0.07f;
                    }

                    if (settingsColor < 255) {
                        settingsColor = settingsColor + 36;
                        if (settingsColor > 255) {
                            settingsColor = 255;
                        }
                    }

                    deleteButton.setOpacity(settingsOpacity);
                    directionButton.setOpacity(settingsOpacity);
                    cfdButton.setOpacity(settingsOpacity);
                    line.setOpacity(settingsOpacity);
                    line2.setOpacity(settingsOpacity);
                    line3.setOpacity(settingsOpacity);
                    line4.setOpacity(settingsOpacity);
                    slider1.setOpacity(2 * settingsOpacity);
                    slider2.setOpacity(2 * settingsOpacity);
                    slider3.setOpacity(2 * settingsOpacity);
                    slider4.setOpacity(2 * settingsOpacity);
                    settingsBG.setOpacity(settingsOpacity);
                    labelRadius.setOpacity(settingsOpacity);
                    labelSpeed.setOpacity(settingsOpacity);
                    wallShear.setOpacity(settingsOpacity);
                    viscosityText.setOpacity(settingsOpacity);
                    nightButton.setOpacity(settingsOpacity);
                    particleVisibility.setOpacity(settingsOpacity);

                    settings.setStyle("-fx-text-fill:" + grayScale(settingsColor) + "; -fx-background-color:" + grayScale(255 -settingsColor) + "; -fx-border-width: 3px");

                } else {

                    if (settingsOpacity <= 0.5) {
                        settingsOpacity = settingsOpacity + 0.07f;
                    }

                    if (settingsColor > 0) {
                        settingsColor = settingsColor - 36;
                        if (settingsColor < 0) {
                            settingsColor = 0;
                        }
                    }

                    deleteButton.setOpacity(settingsOpacity);
                    directionButton.setOpacity(settingsOpacity);
                    cfdButton.setOpacity(settingsOpacity);
                    line.setOpacity(settingsOpacity);
                    line2.setOpacity(settingsOpacity);
                    line3.setOpacity(settingsOpacity);
                    line4.setOpacity(settingsOpacity);
                    slider1.setOpacity(2 * settingsOpacity);
                    slider2.setOpacity(2 * settingsOpacity);
                    slider3.setOpacity(2 * settingsOpacity);
                    slider4.setOpacity(2 * settingsOpacity);
                    settingsBG.setOpacity(settingsOpacity);
                    labelRadius.setOpacity(settingsOpacity);
                    labelSpeed.setOpacity(settingsOpacity);
                    wallShear.setOpacity(settingsOpacity);
                    viscosityText.setOpacity(settingsOpacity);
                    nightButton.setOpacity(settingsOpacity);
                    particleVisibility.setOpacity(settingsOpacity);

                    settings.setStyle("-fx-text-fill:" + grayScale(settingsColor) + "; -fx-background-color:" + grayScale(255 - settingsColor) + "; -fx-border-color:" + grayScale(settingsColor) + "; -fx-border-width: 3px");

                }

                // slider settings

                slider1X = (float) (Math.abs(slider1.getTranslateX() + 675)/270);
                slider2X = (float) (Math.abs(slider2.getTranslateX() + 675)/270);
                slider3X = (float) (Math.abs(slider3.getTranslateX() + 675)/270);
                slider4X = (float) (Math.abs(slider4.getTranslateX() + 675)/270);

                animationRadius = (int) (slider1X * 30) + 2;
                airfoilSpeed = (float) (slider2X * 4.7 + 0.1);
                shear = (float) Math.cbrt(1 - slider3X);
                viscosityStrength = (float) slider4X * 100;

                if (cfdFrames == totalFrames) {
                    cfdBG.setOpacity(0);

                    if (progressOpacity >= 0) {
                        progressOpacity = progressOpacity- 0.07f;
                    }

                    progressBar.setOpacity(progressOpacity);
                    progressBar2.setOpacity(progressOpacity);
                    progressBar3.setOpacity(progressOpacity);

                    cfdState = Boolean.FALSE;
                    simulationState = Boolean.FALSE;
                    if (cfdOpacity <= 1) {
                        cfdOpacity = cfdOpacity + 0.07f;
                    }

                    exitButton.setOpacity(cfdOpacity);

                    // move progress stuff out of the way
                    cfdBG.setTranslateX(2000);
                    progressBar.setTranslateX(2000);
                    progressBar2.setTranslateX(2000);
                    progressBar3.setTranslateX(2000);

                    // remove all lines and particles from the screen.
                    for (int i = 0; i < ParticleAmount; i++) {
                        circle[i].setOpacity(0);
                    }

                    for (int i = 0; i < 26; i++) {
                        bgLine[i].setOpacity(0);
                    }

                    for (int h = 1; h <= objectAmount; h++) {
                        polygon[h].setOpacity(1);
                    }

                    if (!exitAdded) {
                        root.getChildren().add(exitButton);
                        exitAdded = Boolean.TRUE;
                    }

                    colorLoop();

                    // return to standard.

                    exitButton.setOnAction((ActionEvent actionEvent3) -> {
                        root.getChildren().remove(progressBar);
                        root.getChildren().remove(progressBar2);
                        root.getChildren().remove(progressBar3);
                        root.getChildren().remove(cfdBG);
                        addState = Boolean.FALSE;
                        exitAdded = Boolean.FALSE;
                        cfdFrames = 0;

                        // revert if exit button is pressed
                        pause.setOpacity(0.5);
                        settings.setOpacity(0.75);
                        simulationState = Boolean.TRUE;

                        for (int i = 0; i < ParticleAmount; i++) {
                            circle[i].setOpacity(0.5);
                        }

                        for (int i = 0; i < 26; i++) {
                            bgLine[i].setOpacity(1);
                        }

                        cfdReset = Boolean.TRUE;
                    });
                } else if (cfdState) {
                    // exempt particles from CFD to increase speed.
                    cfdPrepLoop();
                    cfdLoop();
                }

                if (cfdReset) {
                    if (cfdOpacity >= 0) {
                        cfdOpacity = cfdOpacity - 0.07f;
                    }

                    exitButton.setOpacity(cfdOpacity);
                    resetLoop();

                    if (cfdOpacity <= 0) {
                        cfdReset = Boolean.FALSE;
                        for (int h = 1; h <= objectAmount; h++) {
                            polygon[h].setOpacity(0.5);
                        }
                    }
                }

                // startup
                if (startFrames[0] < 30) {
                    startFrames[0] = startFrames[0] + 1;
                }

                if (startButton.getTranslateX() < 3000) {
                    if (startFrames[0] == 30 && startOpacity > 0) {
                        startOpacity = startOpacity - 0.04f;
                        startBG.setOpacity(startOpacity);
                        startButton.setOpacity(startOpacity);
                        startButton2.setOpacity(startOpacity);
                    } else if (startOpacity <= 0) {
                        startButton.setTranslateX(4000);
                        startButton2.setTranslateX(4000);
                        startBG.setTranslateX(4000);
                    }
                }
            }

        };
        int[] pauseVariable = {-1};

        animationTimer.start();
        pause.setOnAction((ActionEvent actionEvent3) -> {

            if(pauseVariable[0] == 1) {
                animationTimer.start();
                pauseVariable[0] = pauseVariable[0] * -1;
                pause.setText("Pause");
            } else {
                animationTimer.stop();
                pauseVariable[0] = pauseVariable[0] * -1;
                pause.setText("Play");
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}