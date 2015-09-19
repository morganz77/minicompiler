import java.util.*;
public class Driver{
    public static void main(String[] args) throws Exception{
        Yylex scanner = new Yylex(System.in);
        while (true){
            scanner.next_token();
        }
    }
}
