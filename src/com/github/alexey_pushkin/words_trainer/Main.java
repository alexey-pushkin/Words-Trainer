package com.github.alexey_pushkin.words_trainer;

import java.io.*;
import java.util.*;

public class Main {

    private static enum Mode {
        DIRECT,
        REVERSE
    }

    private static Mode mode;

    private static Scanner in = new Scanner(System.in);

    private static void setLanguageMode() {
        System.out.print("Hello! Please insert 1 or 2 to determine language sequence: ");

        String s = in.nextLine();
        boolean isValidInput = s.equals("1") || s.equals("2");
        while (!isValidInput) {
            System.out.print("Wrong input! Please insert 1 or 2 to determine language sequence: ");
            s = in.nextLine();
            isValidInput = s.equals("1") || s.equals("2");
        }

        switch (s) {
            case "1":
                mode = Mode.DIRECT;
                break;
            case "2":
                mode = Mode.REVERSE;
                break;
        }
    }

    private static int getQuestionIndex() {
        switch (mode) {
            case DIRECT:
                return 0;
            case REVERSE:
                return 1;
            default:
                return -1;
        }
    }

    private static int getAnswerIndex() {
        switch (mode) {
            case DIRECT:
                return 1;
            case REVERSE:
                return 0;
            default:
                return -1;
        }
    }

    public static void main(String[] args) {

        setLanguageMode();


        File file = new File("words.txt");
        BufferedReader reader = null;
        int rightCount = 0;
        int totalCount = 0;

        try {
            reader = new BufferedReader(new FileReader(file));

            String line;
            String[] splittedStr;
            String question;
            String answer;
            String input;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("//")) {
                    continue;
                }

                splittedStr = line.split("-");
                System.out.println(line);
                System.out.println(splittedStr);

                question = splittedStr[getQuestionIndex()].trim();
                System.out.print(question.trim() + " - ");

                answer = splittedStr[getAnswerIndex()].trim();


                String[] splittedAnswer = answer.split(",");
                Set<String> answerSet = new HashSet(Arrays.asList(splittedAnswer));
                for (int i = 0; i < splittedAnswer.length; i++) {
                    answerSet.add(splittedAnswer[i].trim());
                }


                input = in.next();

                if (answerSet.contains(input)) {
                    rightCount++;
                } else {
                    System.out.println("No: " + answer);
                }
                totalCount++;

            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.out.println(e);
            }
        }

        //System.out.println("+++rightCount="+rightCount);
        //System.out.println("+++totalCount="+totalCount);
        //System.out.println("+++rightCount / totalCount * 100="+(rightCount / totalCount) * 100);


        System.out.println("\nYour score is: " + rightCount + " / " + totalCount + " = " + Math.round(Double.valueOf(rightCount) / totalCount * 100) + "%");
    }
}
