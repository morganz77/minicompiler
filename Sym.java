///////////////////////////////////////////////////////////////////////////////
//// Main Class File:  P1.java 
//// File:             Sym.java
//// Semester:         cs536 Fall 2015
////
//// Author:           morgan zhang
///////////////////////////////////////////////////////////////////////////////

/**
 * The Sym class is used to represent a symbol in symbol table
 *
 * @see SymTable
 * @author morgan zhang
 * @version 9/12/2015
 */
public class Sym{
    /** the type of the symbol */
    private String type;

    /**
     * Initilizes the Sym to have the given type
     *
     * @param type The type of the symbol
     */
    public Sym(String type){
        this.type = type;
    }

    /**
     * Return the type of this symbol
     */
    public String getType(){
        return this.type;
    }

    /**
     * Return the type of this symbol (will update later)
     */
    public String toString(){
        return this.type;
    }
}

