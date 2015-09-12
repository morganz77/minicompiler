///////////////////////////////////////////////////////////////////////////////
//// Main Class File:  P1.java 
//// File:             SymTable.java
//// Semester:         cs536 Fall 2015
////
//// Author:           morgan zhang
///////////////////////////////////////////////////////////////////////////////

/**
 * This class is used for testing methods and exceptions implemented in Sym
 * and SymTable classes
 *
 * @author morgan zhang
 * @version 9/12/2015
 */
public class P1{
    public static void main(String[] args) throws Exception{
        SymTable st = new SymTable();
        st.addScope();

        Sym symd = new Sym("double");
        Sym symi = new Sym("int");
        Sym syms = new Sym("str");
        String v1 = "v1";
        String v2 = "v2";
        String v3 = "v3";

        //test looking up symbol in an empty table, symbol in the table, 
        //symbol not in an non-empty table
        if ( st.lookupLocal(v1) != null || st.lookupGlobal(v2) != null){
            System.out.println("found symbol in an empty list!");
        }

        st.addDecl(v1, symd);
        st.addDecl(v2, symi);
        st.addDecl(v3, syms);

        if ( st.lookupLocal(v1) == null || st.lookupGlobal(v2) == null){
            System.out.println("symbol in the list, but no found!");
        }
        if ( st.lookupLocal("v4") != null ){
            System.out.println("symbol v4 should not be here!");
        }

        //print the result
        st.print();

        //add duplicate symbol to the list
        try{
            st.addDecl(v1, symi);
            System.out.println("no exception thrown on attampt to "
                    + "add duplicate symbols");
        }catch (DuplicateSymException dse){
        }

        //lookup at global scope vs local scope
        String v4 = "v4";
        Sym symf = new Sym("float");
        st.addScope();
        st.addDecl(v4, symf);
        //st.print();
        if ( st.lookupLocal(v1) != null || st.lookupGlobal(v1) == null){
            System.out.println("either lookupLocal or lookupGlobal is not "
                    + "working");
        }
        
        //remove local scope
        st.removeScope();
        if ( st.lookupGlobal(v4) != null ){
            System.out.println("symbol "+ v4 +" should not be here!");
        }

        //remove the remaining scope, access the SymTable when its list is empty
        st.removeScope();
        try{
            st.lookupLocal(v1);
            System.out.println("no exception thrown on attampt to "
                    + "lookup a local symbol in an empty list");
        }catch (EmptySymTableException este){
        }
        try{
            st.lookupGlobal(v1);
            System.out.println("no exception thrown on attampt to "
                    + "lookup a global symbol in an empty list");
        }catch (EmptySymTableException este){
        }
        try{
            st.addDecl(v1, symd);
            System.out.println("no exception thrown on attampt to "
                    + "add a symbol to an empty list");
        }catch (EmptySymTableException este){
        }catch (DuplicateSymException dse){
            System.out.println("exception thrown on attampt to "
                    +"add non duplicate symbol");
        }
    }
}
