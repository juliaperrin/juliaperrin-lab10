import java.util.Scanner;
import java.util.regex.*;
import java.io.*;

public class WordCounter {
   private final static String REGEX = "[a-zA-Z0-9']+";
   
   public static int processText(StringBuffer text, String stopword) throws InvalidStopwordException, TooSmallText {
      Pattern pattern = Pattern.compile(REGEX);
      Matcher matcher = pattern.matcher(text);
      int count = 0;
      boolean stopwordFound;
      if (stopword == null) {
         stopwordFound = true;
      } else {
         stopwordFound = false;
      }
      
      while (matcher.find()) {
         count++;
         if (!stopwordFound && matcher.group().equals(stopword)) {
            stopwordFound = true;
            break;
         }
      }
     
      if (count < 5) {
         throw new TooSmallText(count);
      }
      
      if (!stopwordFound) {
         throw new InvalidStopwordException(stopword);
      }
      
      return count;
   }
   
   public static StringBuffer processFile(String path) throws EmptyFileException {
      File file = new File(path);
      Scanner scanner = null;
      
      while (true) {
         try {
            scanner = new Scanner(file);
            break;
         } catch (FileNotFoundException e) {
            System.out.println("File not found, enter a new file name: ");
            Scanner input = new Scanner(System.in);
            path = input.nextLine();
            file = new File(path);
         }
      }
      
      StringBuffer content = new StringBuffer();
      while (scanner.hasNextLine()) {
         content.append(scanner.nextLine()).append(" ");
      }
      scanner.close();
      
      if (content.toString().trim().isEmpty()) {
         throw new EmptyFileException(path + " was empty");
      }
      
      return new StringBuffer(content.toString().trim());
   }
   
   public static void main(String[] args) {
      Scanner input = new Scanner(System.in);
      int option = 0;
      
      while (option != 1 && option != 2) {
         System.out.println("Choose one of the following options: (1) to process a file, or (2) to process a text string: ";
         try {
            option = Integer.parseInt(input.nextLine());
         } catch (NumberFormatException e) {
            System.out.println("Invalid option. Try again. ");
         }
      }
      
      String stopword = null;
      if (args.length > 1) {
         stopword = args[1];
      }
      
      StringBuffer text = new StringBuffer();
      
      if (option == 1) {
         try {
            text = processFile(args[0]);
         } catch (EmptyFileException e) {
            System.out.println(e);
            text = new StringBuffer("");
         }
      } else {
         text = new StringBuffer(args[0]);
      }
      
      try {
         int wordCount = processText(text, stopword);
         System.out.println("Found " + wordCount + " words.");
      } catch (InvalidStopwordException e1) {
      
      
         
      
         