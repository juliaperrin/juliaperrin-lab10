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
      
      if (!stopwordFound) {
         throw new InvalidStopwordException(stopword);
      }
      if (count < 5) {
         throw new TooSmallText(count);
      }
      
      return count;
   }
   
   public static StringBuffer processFile(String path) throws EmptyFileException {
      File file = new File(path);
      Scanner scanner = null;
      
         