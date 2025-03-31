import java.io.IoException;

public class EmptyFileException extends IOException {
   public EmptyFileException(String message) {
      super(message);
   }
}