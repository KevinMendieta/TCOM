import java.io.*;

/**
 * @author KevinMendieta
 */
public class ParserD{

    /* BNF used:
     *C ::= E [(>|<|=)E]
     *E ::= T{(+ | −)T}
     *T ::= F{(∗ | ÷)F}
     *F ::= a | b | c
     */
    public static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
    public static String token, line;
    public static int index;
    public static boolean isCorrect;

    /*
     *Moves token through the line.
     */
    public static void nextToken(){
        if(index < line.length()) token = Character.toString(line.charAt(index));
        index++;
    }

    /*
     *Skips all the whites spaces until a non white space character apperas.
     */
    public static void skip(){
        while(token.equals(" ")){
            nextToken();
        }
    }

    /*
     *Compares the token with a given character t.
     */
    public static void expect(String t) throws Exception{
        if(token.equals(t)){
            nextToken();
        }else if (isCorrect){
            //out.write("Was expected " + t + " instead of "+token+"\n");
            if (t.equals("$")){
                out.write("Error: columna " + index + " , fin de entrada inesperado.\n");
            }else{
                out.write("Error: columna " + index + " , " + "se esperaba: " + t + "en vez de: " + token + "\n");
            }
            isCorrect = false;
        }
    }

    /*
     *C ::= E [(>|<|=)E]
     */
    public static void pC() throws Exception{
        pE();
        skip();
        if((token.equals("<") || token.equals(">") || token.equals("=")) && (!token.equals("$") && isCorrect)){
            nextToken();
            pE();
        }else if(!token.equals("$") && isCorrect){
            out.write("Error: columna " + index + ", " + token + " caracter inesperado.\n");
            //System.out.println("Was expected < > = instead of "+token+"\n");
            isCorrect = false;
        }
    }

    /*
     *E ::= T{(+ | −)T}
     */
    public static void pE() throws Exception{
        pT();
        skip();
        while((token.equals("+") || token.equals("-")) && (!token.equals("$") && isCorrect)){
            nextToken();
            pT();
            skip();
        }
    }

    /*
     *T ::= F{(∗ | ÷)F}
     */
    public static void pT() throws Exception{
        pF();
        skip();
        while((token.equals("*") || token.equals("/")) && (!token.equals("$") && isCorrect)){
            nextToken();
            pF();
            skip();
        }
    }

    /*
     *F ::= a | b | c
     */
    public static void pF() throws Exception{
        skip();
        if((token.equals("a") || token.equals("b") || token.equals("c")) &&  isCorrect){
            nextToken();
        }else if(isCorrect){
            out.write("Error: columna " + index + ", " + token + " caracter inesperado.\n");
            //System.out.println("Was expected a b c instead of "+token+"\n");
            isCorrect = false;
        }
    }

    public static void main(String[] args)throws Throwable{
        while(!in.ready()){
            index = 0;
            isCorrect = true;
            line = in.readLine();
            nextToken();
            pC();
            expect("$");
            if (isCorrect) out.write("Expresion correcta\n");
            //if (isCorrect) System.out.println("Expresion correcta\n");
        }
        out.close();
    }

}
