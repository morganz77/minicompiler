///////////////////////////////////////////////////////////////////////////////
//// Main Class File:  P1.java 
//// File:             SymTable.java
//// Semester:         cs536 Fall 2015
////
//// Author:           morgan zhang
///////////////////////////////////////////////////////////////////////////////

/**
 * The SymTable class is used to represent a symbol table
 *
 * @author morgan zhang
 * @version 9/12/2015
 */
import java.util.*;
public class SymTable{
    /** a list of HashMaps which map the name of symbols to their types */
    private LinkedList<HashMap<String, Sym>> symList;

    /**
     * Initialize an empty list of hashmap for storing symbols
     */
    public SymTable(){
        symList = new LinkedList<HashMap<String, Sym>>();
        symList.add(new HashMap<String, Sym>());
    }

    /**
     * Add the given name and sym to the first HashMap in the list
     *
     * @param name the name of a symbol to be added
     * @param sym the symbol to be added
     * @throws EmptySymTableException if this SymTable's list is empty
     * @throws NullPointerException if either name or sym (or both) is null
     * @throws DuplicateSymException if the first HashMap in the list 
     *                               already contains the given name as a key
     */
    public void addDecl(String name, Sym sym) 
        throws DuplicateSymException, EmptySymTableException{
        if ( this.symList.isEmpty() ){
            throw new EmptySymTableException();
        } else if ( name==null || sym==null ){
            throw new NullPointerException();
        } else if ( this.symList.getFirst().containsKey(name) ){
            throw new DuplicateSymException();
        } else {
            this.symList.getFirst().put(name, sym);
        }
    }

    /**
     * Add a new, empty HashMap to the front of the list.
     */
    public void addScope(){
        this.symList.addFirst( new HashMap<String,Sym> ());
    }

    /**
     * If the first HashMap in the list contains name as a key, return 
     * the associated Sym; otherwise, return null
     *
     * @param name the name of the symbol to be looked up
     * @return the symbol associated with the name
     * @throws EmptySymTableException if this SymTable's list is empty
     */
    public Sym lookupLocal(String name) throws EmptySymTableException{
        if (this.symList.isEmpty() ){
            throw new EmptySymTableException();
        } else if ( this.symList.getFirst().containsKey(name) ){
            return (Sym)(this.symList.getFirst().get(name));
        } else {
            return null; 
        }
    }

    /**
     * If any HashMap in the list contains name as a key, return the 
     * first associated Sym (i.e., the one from the HashMap that is 
     * closest to the front of the list)
     *
     * @param name the name of the symbol to be looked up
     * @return the symbol associated with the name
     * @throws EmptySymTableException if this SymTable's list is empty
     */
    public Sym lookupGlobal(String name) throws EmptySymTableException{
        if (this.symList.isEmpty() ){
            throw new EmptySymTableException();
        } else {
            for (Map<String, Sym> m: this.symList) {
                if ( m.containsKey(name) ){
                    return m.get(name);
                }
            }
            return null;
        }
    }
    
    /**
     * Remove the HashMap from the front of the list
     *
     * @throws EmptySymTableException if this SymTable's list is empty
     */
    public void removeScope() throws EmptySymTableException{
        if (this.symList.isEmpty() ){
            throw new EmptySymTableException();
        } else {
            this.symList.removeFirst();
        }
    }

    /**
     * For debugging purpose
     */
    public void print(){
        System.out.println("\nSym Table");
        for ( Map<String, Sym> m : this.symList ){
            System.out.println(m.toString());
        }
        System.out.println();
    }
}
