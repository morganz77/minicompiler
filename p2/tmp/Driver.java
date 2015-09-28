import java.util.*;
import java.io.*;
public class Driver{
    public static void main(String[] args) throws Exception{
        File f = new File( "in.txt" );
        FileInputStream fis = new FileInputStream(f);
        //Yylex scanner = new Yylex(System.in);
        Yylex scanner = new Yylex(fis);
        while (true){
            scanner.next_token();
        }
    }
}
