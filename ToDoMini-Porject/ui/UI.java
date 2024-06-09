package ui;

import java.util.*;
import java.io.*;


public class UI {

    // Predefined macros for color coding
  public class Colors {
      // Font Styles
      public static final String RESET = "\u001B[0m";
      public static final String BOLD = "\u001B[1m";
      public static final String FAINT = "\u001B[2m";
      public static final String ITALIC = "\u001B[3m";
      public static final String UNDERLINE = "\u001B[4m";
      public static final String BLINK_SLOW = "\u001B[5m";
      public static final String BLINK_RAPID = "\u001B[6m";
      public static final String REVERSE_VIDEO = "\u001B[7m";
      public static final String CONCEAL = "\u001B[8m";
      public static final String CROSSED_OUT = "\u001B[9m";
      public static final String DOUBLE_UNDERLINE = "\u001B[21m";
      public static final String NORMAL_INTENSITY = "\u001B[22m";
      public static final String NOT_ITALIC = "\u001B[23m";
      public static final String NOT_UNDERLINED = "\u001B[24m";
      public static final String BLINK_OFF = "\u001B[25m";
      public static final String REVERSE_OFF = "\u001B[27m";
      public static final String REVEAL = "\u001B[28m";
      public static final String NOT_CROSSED_OUT = "\u001B[29m";

      // Text Colors
      public static final String BLACK = "\u001B[30m";
      public static final String RED = "\u001B[31m";
      public static final String GREEN = "\u001B[32m";
      public static final String YELLOW = "\u001B[33m";
      public static final String BLUE = "\u001B[34m";
      public static final String MAGENTA = "\u001B[35m";
      public static final String CYAN = "\u001B[36m";
      public static final String WHITE = "\u001B[37m";

      // Background Colors
      public static final String BLACK_BACKGROUND = "\u001B[40m";
      public static final String RED_BACKGROUND = "\u001B[41m";
      public static final String GREEN_BACKGROUND = "\u001B[42m";
      public static final String YELLOW_BACKGROUND = "\u001B[43m";
      public static final String BLUE_BACKGROUND = "\u001B[44m";
      public static final String MAGENTA_BACKGROUND = "\u001B[45m";
      public static final String CYAN_BACKGROUND = "\u001B[46m";
      public static final String WHITE_BACKGROUND = "\u001B[47m";


      // Medical and Healthcare Emojis
      public static final String RED_HEART = "\uD83D\uDC97";
      public static final String SYRINGE = "\uD83D\uDC89";
      public static final String PILL = "\uD83D\uDC8A";
      public static final String THERMOMETER = "\uD83C\uDF21";
      public static final String ADHESIVE_BANDAGE = "\uD83E\uDD39";
      public static final String AMBULANCE = "\uD83D\uDE91";
      public static final String HOSPITAL = "\uD83C\uDFE5";
      public static final String FACE_WITH_MEDICAL_MASK = "\uD83D\uDE37";
      public static final String HAND_SANITIZER = "\uD83E\uDDE4";
      public static final String PALMS_UP_TOGETHER = "\uD83E\uDD32";
      public static final String STETHOSCOPE = "\uD83D\uDEE3";
      public static final String DROP_OF_BLOOD = "\uD83E\uDD18";
      public static final String DIGITAL_THERMOMETER = "\uD83D\uDD2B";
      public static final String ABO_BLOOD_TYPE = "\uD83E\uDD0E";
      public static final String SQUARED_SOS = "\uD83C\uDD98";
      public static final String CADUCEUS = "\u2694";
      public static final String APPOINTMENT_CALENDAR = "\uD83D\uDCC5";
      public static final String TEST_TUBE = "\uD83E\uDDEA";
      public static final String MICROBE = "\uD83E\uDDA0";
      public static final String HERB = "\uD83C\uDF3F";
      public static final String GREEN_APPLE = "\uD83C\uDF4F";
      public static final String TANGERINE = "\uD83C\uDF4A";
      public static final String GRAPES = "\uD83C\uDF47";
      public static final String STRAWBERRY = "\uD83C\uDF53";
      public static final String BROCCOLI = "\uD83E\uDD66";
      public static final String CARROT = "\uD83E\uDD55";
      public static final String MAN_WALKING = "\uD83D\uDEB6\u200D♂️";
      public static final String HOSPITAL_BUILDING = "\uD83C\uDFE5";
      public static final String PLUS_SYMBOL_EMOJI = "➕";
      public static final String EYE_EMOJI = "\uD83D\uDC41";
      public static final String EYES_EMOJI = "👀";
      public static final String CROSS_MARK_EMOJI = "❌";
      public static final String NEWSPAPER_EMOJI = "📰";


    
  }



    // Title
    public static void printTitle(char symbol, String text) {
        int screenWidth = getTerminalWidth();
        int textWidth = text.length();
        int remainingWidth = screenWidth - textWidth;
        int symbolsOnEachSide = remainingWidth / 2;

        for (int i = 0; i < symbolsOnEachSide; i++) {
            System.out.print(Colors.MAGENTA + symbol + Colors.RESET);
        }

        System.out.print(Colors.BOLD + text + Colors.RESET);

        for (int i = 0; i < symbolsOnEachSide; i++) {
            System.out.print(Colors.MAGENTA + symbol + Colors.RESET);
        }

        System.out.println();
    }
  
  public static boolean askClearScreen() {
      Scanner scanner = new Scanner(System.in);
      System.out.print("Do you want to go to the Main Menu ? (y/n): ");
      String userInput = scanner.nextLine().toLowerCase();
      return userInput.equals("y");
  }
  
  public static void sleep(int milliseconds) {
      try {
          Thread.sleep(milliseconds);
      } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          e.printStackTrace();
      }
  }
    // Center justify
    private static void printCenteredText(String text) {
        int screenWidth = getTerminalWidth();
        int textWidth = text.length();
        int paddingWidth = (screenWidth - textWidth) / 2;

        for (int i = 0; i < paddingWidth; i++) {
            System.out.print(" ");
        }

        System.out.println(text);
    }

    // Function to get terminal width
    private static int getTerminalWidth() {
        ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", "tput cols");
        try {
            Process process = processBuilder.start();
            Scanner scanner = new Scanner(process.getInputStream());
            return scanner.nextInt();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception or provide a default value
            return 80; // Default value if unable to get terminal width
        }
    }


  public static void clearScreen() {
      try {
          final String os = System.getProperty("os.name");

          if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
              // Unix/Linux/MacOS
              new ProcessBuilder("bash", "-c", "clear").inheritIO().start().waitFor();
          } else if (os.contains("win")) {
              // Windows
              new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
          }
      } catch (IOException | InterruptedException e) {
          e.printStackTrace();
      }
  }


      static void printRedCross() {
          printRow();
          printCenteredRow();
          printRow();
      }

      static void printRow() {
          System.out.print("  ");
          System.out.print("\u001B[41m \u001B[0m");
          System.out.println("\u001B[41m \u001B[0m");
      }

      static void printCenteredRow() {
          System.out.print("\u001B[41m \u001B[0m");
          System.out.print("\u001B[41m \u001B[0m");
          System.out.print("\u001B[41m \u001B[0m");
          System.out.print("\u001B[41m \u001B[0m");
          System.out.print("\u001B[41m \u001B[0m");
          System.out.println("\u001B[41m \u001B[0m" + " Your Personalised HealthCare is a Click away!");
      }

  static void printHeader(String symbol) {
      try {
          ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", "tput cols");
          Process process = processBuilder.start();
          int screenWidth = Integer.parseInt(new String(process.getInputStream().readAllBytes()).trim());

          System.out.println();  // Move to the next line
          for (int i = 0; i < screenWidth; i++) {
              System.out.print(symbol);
          }
          System.out.println();  // Move to the next line
      } catch (IOException e) {
          e.printStackTrace();
      }
  }


}