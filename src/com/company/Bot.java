package com.company;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Bot extends Cellule implements MouseListener {
    static String colorBot;

    /**
     *
     * @param color Color choosed by the player
     */
    public Bot(String color){
        System.out.println("color : "+ color);
        if(color.equals("N")){
            this.colorBot = "B";
            //Bot.mooveBot();
        }else {
            this.colorBot = "N";
        }
    }

    public static void mooveBot(){
        int mooveX;
        int mooveY;
        String actualColor = "P"+ getTurn();
        boolean taked = false;
        ArrayList<ArrayList<Integer>> arrayPieces = new ArrayList<ArrayList<Integer> >();
        if(getTurn().equals("N")) {
            arrayPieces = Cellule.pionNoir;

        } else if (getTurn().equals("B")){
            arrayPieces = Cellule.pionBlanc;
        }
        int max = arrayPieces.size() - 1;
        int min = 0;
        int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
        currentPion = new Piece(arrayPieces.get(random_int).get(0), arrayPieces.get(random_int).get(1),actualColor);
        String caseVerif = currentPion.ifCanTake("P"+getTurn());
        System.out.println("CASEVERIF " + caseVerif);
        if(caseVerif.equals("erreur") || caseVerif.equals("vide")) {
            for (int i = arrayPieces.size() - 1; i > 1; i--){
                System.out.println("iteration : "+i);
                currentPion =  new Piece(arrayPieces.get(i).get(0), arrayPieces.get(i).get(1),actualColor);
                System.out.println("COORDONNE  : "+arrayPieces.get(i).get(0) + " " + arrayPieces.get(i).get(1));
                String validCase = verifCaseValide(arrayPieces.get(i).get(0) - 1, arrayPieces.get(i).get(1) + 1);
                if(validCase.equals("VIDE")) {
                    String verifPrise = currentPion.verifPrise(arrayPieces.get(i).get(0) - 1, arrayPieces.get(i).get(1) + 1);
                    System.out.println("verifPrise : "+verifPrise);
                    if (verifPrise.equals("VIDE")) {
                        currentPion.deplacement(arrayPieces.get(i).get(0) - 1,arrayPieces.get(i).get(1) + 1);
                        break;
                    }
                }

            }
        }else {
            String tempX = caseVerif.substring(0,1);
            String tempY = caseVerif.substring(1,2);

            int InttempX = Integer.parseInt(tempX);
            int InttempY = Integer.parseInt(tempY);

            currentPion =  new Piece(InttempX,InttempY ,actualColor);
            String verifPriseBot = Bot.verifPriseBot(InttempX,InttempY);
            if (verifPriseBot.equals("PRISE_PB_G")){
                taked = currentPion.prise(InttempX + 1, InttempY + 1,InttempX, InttempY);
                currentPion.deplacement(InttempX,InttempY);
            } else if (verifPriseBot.equals("PRISE_PB_D")){
                taked = currentPion.prise(InttempX - 1, InttempY + 1,InttempX, InttempY);
                currentPion.deplacement(InttempX,InttempY);
            } else if (verifPriseBot.equals("PRISE_PN_G")){
                taked = currentPion.prise(InttempX - 1, InttempY + 1,InttempX, InttempY);
                currentPion.deplacement(InttempX - 2,InttempY + 2);
            } else if (verifPriseBot.equals("PRISE_PN_D")){
                taked = currentPion.prise(InttempX + 1, InttempY + 1,InttempX, InttempY);
                currentPion.deplacement(InttempX + 2,InttempY + 2);
            }else if (verifPriseBot.equals("PRISE_D")){
                taked = currentPion.prise(Piece.getPieceTaked().get(0).get(0), Piece.getPieceTaked().get(0).get(1),InttempX, InttempY);
                currentPion.deplacement(InttempX,InttempY);
                Piece.pieceTaked.clear();
            }
            if (taked) {
                Piece.pieceTaked.clear();
                //swapTurn(false);
            }
        }
    }

    public static String verifPriseBot(int x,int y){
        String verifPrise = "";
        if(currentPion.getCouleur().equals("PN")){
            verifPrise =  currentPion.verifPrise(x + 2, y + 2);
            if(!(verifPrise.equals("erreur") || verifPrise.equals("vide"))) {
                return verifPrise;
            } else {
                verifPrise =  currentPion.verifPrise(x - 2, y + 2);
            }
        } else {
            verifPrise =  currentPion.verifPrise(x + 2, y - 2);
            if(!(verifPrise.equals("erreur") || verifPrise.equals("vide"))) {
                return verifPrise;
            } else {
                verifPrise =  currentPion.verifPrise(x - 2, y - 2);
            }
        }
        return verifPrise;
    }
}
