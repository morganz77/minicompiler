import java.io.*;
import java.util.*;

// **********************************************************************
// The ASTnode class defines the nodes of the abstract-syntax tree that
// represents a Mini program.
//
// Internal nodes of the tree contain pointers to children, organized
// either in a list (for nodes that may have a variable number of 
// children) or as a fixed set of fields.
//
// The nodes for literals and ids contain line and character number
// information; for string literals and identifiers, they also contain a
// string; for integer literals, they also contain an integer value.
//
// Here are all the different kinds of AST nodes and what kinds of children
// they have.  All of these kinds of AST nodes are subclasses of "ASTnode".
// Indentation indicates further subclassing:
//
//     Subclass            Kids
//     --------            ----
//     ProgramNode         DeclListNode
//     DeclListNode        linked list of DeclNode
//     DeclNode:
//       VarDeclNode       TypeNode, IdNode, int
//       FnDeclNode        TypeNode, IdNode, FormalsListNode, FnBodyNode
//       FormalDeclNode    TypeNode, IdNode
//       StructDeclNode    IdNode, DeclListNode
//
//     FormalsListNode     linked list of FormalDeclNode
//     FnBodyNode          DeclListNode, StmtListNode
//     StmtListNode        linked list of StmtNode
//     ExpListNode         linked list of ExpNode
//
//     TypeNode:
//       IntNode           -- none --
//       BoolNode          -- none --
//       VoidNode          -- none --
//       StructNode        IdNode
//
//     StmtNode:
//       AssignStmtNode      AssignNode
//       PostIncStmtNode     ExpNode
//       PostDecStmtNode     ExpNode
//       ReadStmtNode        ExpNode
//       WriteStmtNode       ExpNode
//       IfStmtNode          ExpNode, DeclListNode, StmtListNode
//       IfElseStmtNode      ExpNode, DeclListNode, StmtListNode,
//                                    DeclListNode, StmtListNode
//       WhileStmtNode       ExpNode, DeclListNode, StmtListNode
//       CallStmtNode        CallExpNode
//       ReturnStmtNode      ExpNode
//
//     ExpNode:
//       IntLitNode          -- none --
//       StrLitNode          -- none --
//       TrueNode            -- none --
//       FalseNode           -- none --
//       IdNode              -- none --
//       DotAccessNode       ExpNode, IdNode
//       AssignNode          ExpNode, ExpNode
//       CallExpNode         IdNode, ExpListNode
//       UnaryExpNode        ExpNode
//         UnaryMinusNode
//         NotNode
//       BinaryExpNode       ExpNode ExpNode
//         PlusNode     
//         MinusNode
//         TimesNode
//         DivideNode
//         AndNode
//         OrNode
//         EqualsNode
//         NotEqualsNode
//         LessNode
//         GreaterNode
//         LessEqNode
//         GreaterEqNode
//
// Here are the different kinds of AST nodes again, organized according to
// whether they are leaves, internal nodes with linked lists of kids, or
// internal nodes with a fixed number of kids:
//
// (1) Leaf nodes:
//        IntNode,   BoolNode,  VoidNode,  IntLitNode,  StrLitNode,
//        TrueNode,  FalseNode, IdNode
//
// (2) Internal nodes with (possibly empty) linked lists of children:
//        DeclListNode, FormalsListNode, StmtListNode, ExpListNode
//
// (3) Internal nodes with fixed numbers of kids:
//        ProgramNode,     VarDeclNode,     FnDeclNode,     FormalDeclNode,
//        StructDeclNode,  FnBodyNode,      StructNode,     AssignStmtNode,
//        PostIncStmtNode, PostDecStmtNode, ReadStmtNode,   WriteStmtNode   
//        IfStmtNode,      IfElseStmtNode,  WhileStmtNode,  CallStmtNode
//        ReturnStmtNode,  DotAccessNode,   AssignExpNode,  CallExpNode,
//        UnaryExpNode,    BinaryExpNode,   UnaryMinusNode, NotNode,
//        PlusNode,        MinusNode,       TimesNode,      DivideNode,
//        AndNode,         OrNode,          EqualsNode,     NotEqualsNode,
//        LessNode,        GreaterNode,     LessEqNode,     GreaterEqNode
//
// **********************************************************************

// **********************************************************************
// ASTnode class (base class for all other kinds of nodes)
// **********************************************************************

abstract class ASTnode { 
    // every subclass must provide an unparse operation
    abstract public void unparse(PrintWriter p, int indent);

    // this method can be used by the unparse methods to do indenting
    protected void doIndent(PrintWriter p, int indent) {
        for (int k=0; k<indent; k++) p.print(" ");
    }

    public void analyze(){
    }

    public void analyze(SymTable symtb){
    }

}

// **********************************************************************
// ProgramNode,  DeclListNode, FormalsListNode, FnBodyNode,
// StmtListNode, ExpListNode
// **********************************************************************

class ProgramNode extends ASTnode {
    public ProgramNode(DeclListNode L) {
        myDeclList = L;
    }

    public void analyze(){
        SymTable symtb = new SymTable();
        globalStructSym = new HashMap<String,SemSym>();
        analyze(symtb);
    }

    public void analyze(SymTable symtb){
        myDeclList.analyze(symtb);
    }

    public void unparse(PrintWriter p, int indent) {
        myDeclList.unparse(p, indent);
    }

    // 1 kid
    private DeclListNode myDeclList;
    //
    public static HashMap<String,SemSym> globalStructSym;
}

class DeclListNode extends ASTnode {
    public DeclListNode(List<DeclNode> S) {
        myDecls = S;
    }

    public void analyze(SymTable symtb){
        Iterator it = myDecls.iterator();
        try {
            while (it.hasNext()) {
                ((DeclNode)it.next()).analyze(symtb);
            }
        } catch (NoSuchElementException ex) {
            System.err.println("unexpected NoSuchElementException in DeclListNode.analyze");
            System.exit(-1);
        }
    }

    public void unparse(PrintWriter p, int indent) {
        Iterator it = myDecls.iterator();
        try {
            while (it.hasNext()) {
                ((DeclNode)it.next()).unparse(p, indent);
            }
        } catch (NoSuchElementException ex) {
            System.err.println("unexpected NoSuchElementException in DeclListNode.print");
            System.exit(-1);
        }
    }

    // list of kids (DeclNodes)
    private List<DeclNode> myDecls;
}

class FormalsListNode extends ASTnode {
    public FormalsListNode(List<FormalDeclNode> S) {
        myFormals = S;
    }

    public void analyze(SymTable symtb){
        Iterator it = myFormals.iterator();
        try {
            while (it.hasNext()) {
                ((FormalDeclNode)it.next()).analyze(symtb);
            }
        } catch (NoSuchElementException ex) {
            System.err.println("unexpected NoSuchElementException in FormalsListNode.analyze");
            System.exit(-1);
        }
    }

    public ArrayList<String> getFormals(){
        ArrayList<String> formals = new ArrayList<String>();
        Iterator<FormalDeclNode> it = myFormals.iterator();
        while (it.hasNext()) {  // print the rest of the list
            formals.add(it.next().getType());
        }
        return formals;
    }

    public void unparse(PrintWriter p, int indent) {
        Iterator<FormalDeclNode> it = myFormals.iterator();
        if (it.hasNext()) { // if there is at least one element
            it.next().unparse(p, indent);
            while (it.hasNext()) {  // print the rest of the list
                p.print(", ");
                it.next().unparse(p, indent);
            }
        } 
    }

    // list of kids (FormalDeclNodes)
    private List<FormalDeclNode> myFormals;
}

class FnBodyNode extends ASTnode {
    public FnBodyNode(DeclListNode declList, StmtListNode stmtList) {
        myDeclList = declList;
        myStmtList = stmtList;
    }

    public void analyze(SymTable symtb){
        myDeclList.analyze(symtb);
        myStmtList.analyze(symtb);
    }

    public void unparse(PrintWriter p, int indent) {
        myDeclList.unparse(p, indent);
        myStmtList.unparse(p, indent);
    }

    // 2 kids
    private DeclListNode myDeclList;
    private StmtListNode myStmtList;
}

class StmtListNode extends ASTnode {
    public StmtListNode(List<StmtNode> S) {
        myStmts = S;
    }

    public void analyze(SymTable symtb){
        Iterator it = myStmts.iterator();
        try {
            while (it.hasNext()) {
                ((StmtNode)it.next()).analyze(symtb);
            }
        } catch (NoSuchElementException ex) {
            System.err.println("unexpected NoSuchElementException in DeclListNode.print");
            System.exit(-1);
        }
    }

    public void unparse(PrintWriter p, int indent) {
        Iterator<StmtNode> it = myStmts.iterator();
        while (it.hasNext()) {
            it.next().unparse(p, indent);
        }
    }

    // list of kids (StmtNodes)
    private List<StmtNode> myStmts;
}

class ExpListNode extends ASTnode {
    public ExpListNode(List<ExpNode> S) {
        myExps = S;
    }

    public void analyze(SymTable symtb){
        Iterator it = myExps.iterator();
        try {
            while (it.hasNext()) {
                ((ExpNode)it.next()).analyze(symtb);
            }
        } catch (NoSuchElementException ex) {
            System.err.println("unexpected NoSuchElementException in ExpListNode.analyze");
            System.exit(-1);
        }
    }

    public void unparse(PrintWriter p, int indent) {
        Iterator<ExpNode> it = myExps.iterator();
        if (it.hasNext()) { // if there is at least one element
            it.next().unparse(p, indent);
            while (it.hasNext()) {  // print the rest of the list
                p.print(", ");
                it.next().unparse(p, indent);
            }
        } 
    }

    // list of kids (ExpNodes)
    private List<ExpNode> myExps;
}

// **********************************************************************
// DeclNode and its subclasses
// **********************************************************************

abstract class DeclNode extends ASTnode {
}

class VarDeclNode extends DeclNode {
    public VarDeclNode(TypeNode type, IdNode id, int size) {
        myType = type;
        myId = id;
        mySize = size;
    }

    //public void analyze(SymTable symtb, String 
    public void analyze(SymTable symtb){
        String typeStr = myType.toString();
        if (typeStr.equals("struct")){
            String structName = ((StructNode)myType).getIdName();
            myId.analyzeVarStructDecl(symtb, structName, ((StructNode)myType).getId());
        }else {
            myId.analyzeDecl(symtb, typeStr, IdNode.VAR, null );
        }
        //symtb.print();
    }

    public void unparse(PrintWriter p, int indent) {
        doIndent(p, indent);
        myType.unparse(p, 0);
        p.print(" ");
        myId.unparse(p, 0);
        p.println(";");
    }

    // 3 kids
    private TypeNode myType;
    private IdNode myId;
    private int mySize;  // use value NOT_STRUCT if this is not a struct type

    public static int NOT_STRUCT = -1;
}

class FnDeclNode extends DeclNode {
    public FnDeclNode(TypeNode type,
            IdNode id,
            FormalsListNode formalList,
            FnBodyNode body) {
        myType = type;
        myId = id;
        myFormalsList = formalList;
        myBody = body;
    }

    public void analyze(SymTable symtb){
        //check, then add new scope
        //1. collecting formals, check fn name; 2. new scope start from formal; 3. remove scope
        ArrayList<String> formals = myFormalsList.getFormals();
        String retType = myType.toString();
        myId.analyzeFnDecl(symtb, formals, retType);

        symtb.addScope();//new scope, including formalList and body; id is upper scope
        myFormalsList.analyze(symtb);
        myBody.analyze(symtb);

        try{
            symtb.removeScope();
        }catch (EmptySymTableException ete){
            System.err.println("no scope to remove!");
        }
    }

    public void unparse(PrintWriter p, int indent) {
        doIndent(p, indent);
        myType.unparse(p, 0);
        p.print(" ");
        myId.unparse(p, 0);
        p.print("(");
        myFormalsList.unparse(p, 0);
        p.println(") {");
        myBody.unparse(p, indent+4);
        p.println("}\n");
    }

    // 4 kids
    private TypeNode myType;
    private IdNode myId;
    private FormalsListNode myFormalsList;
    private FnBodyNode myBody;
}

class FormalDeclNode extends DeclNode {
    public FormalDeclNode(TypeNode type, IdNode id) {
        myType = type;
        myId = id;
    }

    public void analyze(SymTable symtb){
        String typeStr = myType.toString();
        myId.analyzeDecl(symtb, typeStr, IdNode.VAR, null );
    }

    public void unparse(PrintWriter p, int indent) {
        myType.unparse(p, 0);
        p.print(" ");
        myId.unparse(p, 0);
    }

    public String getType(){
        return myType.toString();
    }

    // 2 kids
    private TypeNode myType;
    private IdNode myId;
}

class StructDeclNode extends DeclNode {
    public StructDeclNode(IdNode id, DeclListNode declList) {
        myId = id;
        myDeclList = declList;
    }

    public void analyze(SymTable symtb){
        SymTable symtbs = new SymTable();
        myDeclList.analyze(symtbs);
        myId.analyzeDecl(symtb, "", IdNode.STRUCTDECL, symtbs);// empty string should be fine
    }

    public void unparse(PrintWriter p, int indent) {
        doIndent(p, indent);
        p.print("struct ");
        myId.unparse(p, 0);
        p.println("{");
        myDeclList.unparse(p, indent+4);
        doIndent(p, indent);
        p.println("};\n");

    }

    // 2 kids
    private IdNode myId;
    private DeclListNode myDeclList;
}

// **********************************************************************
// TypeNode and its Subclasses
// **********************************************************************

abstract class TypeNode extends ASTnode {
}

class IntNode extends TypeNode {
    public IntNode() {
    }

    public String toString(){
        return "int";
    }

    public void unparse(PrintWriter p, int indent) {
        p.print("int");
    }
}

class BoolNode extends TypeNode {
    public BoolNode() {
    }

    public String toString(){
        return "bool";
    }

    public void unparse(PrintWriter p, int indent) {
        p.print("bool");
    }
}

class VoidNode extends TypeNode {
    public VoidNode() {
    }

    public String toString(){
        return "void";
    }

    public void unparse(PrintWriter p, int indent) {
        p.print("void");
    }
}

class StructNode extends TypeNode {
    public StructNode(IdNode id) {
        myId = id;
    }

    public String getIdName(){
        return myId.toString();
    }

    public IdNode getId(){
        return myId;
    }

    public String toString(){
        return "struct";
    }
    public void unparse(PrintWriter p, int indent) {
        p.print("struct ");
        myId.unparse(p, 0);
    }

    // 1 kid
    private IdNode myId;
}

// **********************************************************************
// StmtNode and its subclasses
// **********************************************************************

abstract class StmtNode extends ASTnode {
}

class AssignStmtNode extends StmtNode {
    public AssignStmtNode(AssignNode assign) {
        myAssign = assign;
    }

    public void analyze(SymTable symtb){
        myAssign.analyze(symtb);
    }

    public void unparse(PrintWriter p, int indent) {
        doIndent(p, indent);
        myAssign.unparse(p, -1); // no parentheses
        p.println(";");
    }

    // 1 kid
    private AssignNode myAssign;
}

class PostIncStmtNode extends StmtNode {
    public PostIncStmtNode(ExpNode exp) {
        myExp = exp;
    }

    public void analyze(SymTable symtb){
        myExp.analyze(symtb);
    }

    public void unparse(PrintWriter p, int indent) {
        doIndent(p, indent);
        myExp.unparse(p, 0);
        p.println("++;");
    }

    // 1 kid
    private ExpNode myExp;
}

class PostDecStmtNode extends StmtNode {
    public PostDecStmtNode(ExpNode exp) {
        myExp = exp;
    }

    public void analyze(SymTable symtb){
        myExp.analyze(symtb);
    }

    public void unparse(PrintWriter p, int indent) {
        doIndent(p, indent);
        myExp.unparse(p, 0);
        p.println("--;");
    }

    // 1 kid
    private ExpNode myExp;
}

class ReadStmtNode extends StmtNode {
    public ReadStmtNode(ExpNode e) {
        myExp = e;
    }

    public void analyze(SymTable symtb){
        myExp.analyze(symtb);
    }

    public void unparse(PrintWriter p, int indent) {
        doIndent(p, indent);
        p.print("cin >> ");
        myExp.unparse(p, 0);
        p.println(";");
    }

    // 1 kid (actually can only be an IdNode or an ArrayExpNode)
    private ExpNode myExp;
}

class WriteStmtNode extends StmtNode {
    public WriteStmtNode(ExpNode exp) {
        myExp = exp;
    }

    public void analyze(SymTable symtb){
        myExp.analyze(symtb);
    }

    public void unparse(PrintWriter p, int indent) {
        doIndent(p, indent);
        p.print("cout << ");
        myExp.unparse(p, 0);
        p.println(";");
    }

    // 1 kid
    private ExpNode myExp;
}

class IfStmtNode extends StmtNode {
    public IfStmtNode(ExpNode exp, DeclListNode dlist, StmtListNode slist) {
        myDeclList = dlist;
        myExp = exp;
        myStmtList = slist;
    }

    public void analyze(SymTable symtb){
        myExp.analyze(symtb);

        symtb.addScope();
        myDeclList.analyze(symtb);
        myStmtList.analyze(symtb);
        try{
            symtb.removeScope();
        }catch (EmptySymTableException ete){
            System.err.println("no scope to remove!");
        }
    }

    public void unparse(PrintWriter p, int indent) {
        doIndent(p, indent);
        p.print("if (");
        myExp.unparse(p, 0);
        p.println(") {");
        myDeclList.unparse(p, indent+4);
        myStmtList.unparse(p, indent+4);
        doIndent(p, indent);
        p.println("}");
    }

    // e kids
    private ExpNode myExp;
    private DeclListNode myDeclList;
    private StmtListNode myStmtList;
}

class IfElseStmtNode extends StmtNode {
    public IfElseStmtNode(ExpNode exp, DeclListNode dlist1,
            StmtListNode slist1, DeclListNode dlist2,
            StmtListNode slist2) {
        myExp = exp;
        myThenDeclList = dlist1;
        myThenStmtList = slist1;
        myElseDeclList = dlist2;
        myElseStmtList = slist2;
    }

    public void analyze(SymTable symtb){
        myExp.analyze(symtb);

        symtb.addScope();
        myThenDeclList.analyze(symtb);
        myThenStmtList.analyze(symtb);
        try{
            symtb.removeScope();
        }catch (EmptySymTableException ete){
            System.err.println("no scope to remove!");
        }

        symtb.addScope();
        myElseDeclList.analyze(symtb);
        myElseStmtList.analyze(symtb);
        try{
            symtb.removeScope();
        }catch (EmptySymTableException ete){
            System.err.println("no scope to remove!");
        }
    }

    public void unparse(PrintWriter p, int indent) {
        doIndent(p, indent);
        p.print("if (");
        myExp.unparse(p, 0);
        p.println(") {");
        myThenDeclList.unparse(p, indent+4);
        myThenStmtList.unparse(p, indent+4);
        doIndent(p, indent);
        p.println("}");
        doIndent(p, indent);
        p.println("else {");
        myElseDeclList.unparse(p, indent+4);
        myElseStmtList.unparse(p, indent+4);
        doIndent(p, indent);
        p.println("}");        
    }

    // 5 kids
    private ExpNode myExp;
    private DeclListNode myThenDeclList;
    private StmtListNode myThenStmtList;
    private StmtListNode myElseStmtList;
    private DeclListNode myElseDeclList;
}

class WhileStmtNode extends StmtNode {
    public WhileStmtNode(ExpNode exp, DeclListNode dlist, StmtListNode slist) {
        myExp = exp;
        myDeclList = dlist;
        myStmtList = slist;
    }

    public void analyze(SymTable symtb){
        myExp.analyze(symtb);

        symtb.addScope();
        myDeclList.analyze(symtb);
        myStmtList.analyze(symtb);
        try{
            symtb.removeScope();
        }catch (EmptySymTableException ete){
            System.err.println("no scope to remove!");
        }

    }

    public void unparse(PrintWriter p, int indent) {
        doIndent(p, indent);
        p.print("while (");
        myExp.unparse(p, 0);
        p.println(") {");
        myDeclList.unparse(p, indent+4);
        myStmtList.unparse(p, indent+4);
        doIndent(p, indent);
        p.println("}");
    }

    // 3 kids
    private ExpNode myExp;
    private DeclListNode myDeclList;
    private StmtListNode myStmtList;
}

class CallStmtNode extends StmtNode {
    public CallStmtNode(CallExpNode call) {
        myCall = call;
    }

    public void analyze(SymTable symtb){
        myCall.analyze(symtb);
    }

    public void unparse(PrintWriter p, int indent) {
        doIndent(p, indent);
        myCall.unparse(p, indent);
        p.println(";");
    }

    // 1 kid
    private CallExpNode myCall;
}

class ReturnStmtNode extends StmtNode {
    public ReturnStmtNode(ExpNode exp) {
        myExp = exp;
    }

    public void analyze(SymTable symtb){
        if (myExp != null){
            myExp.analyze(symtb);
        }
    }

    public void unparse(PrintWriter p, int indent) {
        doIndent(p, indent);
        p.print("return");
        if (myExp != null) {
            p.print(" ");
            myExp.unparse(p, 0);
        }
        p.println(";");
    }

    // 1 kid
    private ExpNode myExp; // possibly null
}

// **********************************************************************
// ExpNode and its subclasses
// **********************************************************************

abstract class ExpNode extends ASTnode {
    public void analyze(SymTable symtb, int mode){}
    public SemSym getSym(){return null;}
    public boolean getFirstStatus(){return true;}
}

class IntLitNode extends ExpNode {
    public IntLitNode(int lineNum, int charNum, int intVal) {
        myLineNum = lineNum;
        myCharNum = charNum;
        myIntVal = intVal;
    }

    public void unparse(PrintWriter p, int indent) {
        p.print(myIntVal);
    }

    private int myLineNum;
    private int myCharNum;
    private int myIntVal;
}

class StringLitNode extends ExpNode {
    public StringLitNode(int lineNum, int charNum, String strVal) {
        myLineNum = lineNum;
        myCharNum = charNum;
        myStrVal = strVal;
    }

    public void unparse(PrintWriter p, int indent) {
        p.print(myStrVal);
    }

    private int myLineNum;
    private int myCharNum;
    private String myStrVal;
}

class TrueNode extends ExpNode {
    public TrueNode(int lineNum, int charNum) {
        myLineNum = lineNum;
        myCharNum = charNum;
    }

    public void unparse(PrintWriter p, int indent) {
        p.print("true");
    }

    private int myLineNum;
    private int myCharNum;
}

class FalseNode extends ExpNode {
    public FalseNode(int lineNum, int charNum) {
        myLineNum = lineNum;
        myCharNum = charNum;
    }

    public void unparse(PrintWriter p, int indent) {
        p.print("false");
    }

    private int myLineNum;
    private int myCharNum;
}

class IdNode extends ExpNode {
    public IdNode(int lineNum, int charNum, String strVal) {
        myLineNum = lineNum;
        myCharNum = charNum;
        myStrVal = strVal;
    }

    public void setSym(SemSym sym){
        mySym = sym;
    }

    public void analyze(SymTable symtb){//default is var analysis
        analyzeUse(symtb);
    }

    public void analyze(SymTable symtb, int mode){
        try{
            if (mode == IdNode.STRUCTVAR){
                mySym = symtb.lookupGlobal(myStrVal); //link for unparsing
                if ( mySym == null ){
                    ErrMsg.fatal(myLineNum, myCharNum, "Undeclared identifier");
                } else if (mySym.getKind() != IdNode.STRUCTVAR){
                    ErrMsg.fatal(myLineNum, myCharNum, "Dot-access of non-struct type");
                    mySym = null;
                }
            } else if (mode == IdNode.FUN){
                mySym = symtb.lookupGlobal(myStrVal); //link for unparsing
                if ( mySym == null ){
                    ErrMsg.fatal(myLineNum, myCharNum, "Undeclared identifier");
                } 
                //no need to check type
                //else if (mySym.getKind() != IdNode.FUN){
                //    ErrMsg.fatal(myLineNum, myCharNum,  "Undeclared identifier");
                //    mySym = null;
                //}

            }
        }catch(Exception e){
            ErrMsg.fatal(myLineNum, myCharNum, "Exception occurs!");
        }
    }

    public void analyzeVarStructDecl(SymTable symtb, String structName, IdNode id){
        try{
            //check if structName is in symtb; if it is structDecl
            SemSym sym = symtb.lookupGlobal(structName);
            if (sym==null){sym = ProgramNode.globalStructSym.get(structName);}//for func and var, this is a subset of symtb; for struct, it should work
            //System.out.println(structName);
            if (  sym == null || sym.getKind()!=IdNode.STRUCTDECL){
                ErrMsg.fatal(id.myLineNum, id.myCharNum, "Invalid name of struct type");
                return;
            } 
            if ( symtb.lookupLocal(myStrVal) != null ){
                ErrMsg.fatal(myLineNum, myCharNum, "Multiply declared identifier");
            }else {
                symtb.addDecl(myStrVal, new StructSym((StructDeclSym)sym, structName));
            }
        }catch(Exception e){
            ErrMsg.fatal(myLineNum, myCharNum, "Exception occurs!");
        }
    }

    public void analyzeFnDecl(SymTable symtb, ArrayList<String> formals, String retType ){//fun seems to be complicated
        try{
            if ( symtb.lookupLocal(myStrVal) != null ){
                ErrMsg.fatal(myLineNum, myCharNum, "Multiply declared identifier");
            } else {
                symtb.addDecl(myStrVal, new FnSym(formals, retType));
            }
        }catch(Exception e){
            ErrMsg.fatal(myLineNum, myCharNum, "Exception occurs!");
        }
    }

    public void analyzeDecl(SymTable symtb, String typeStr, int declType, SymTable symtbs){//1 variable VAR, 2 STRUCTDECL
        try{
            if (declType == VAR){
                //symtb.print();
                if (typeStr.equals("void")){
                    ErrMsg.fatal(myLineNum, myCharNum, "Non-function declared void");
                }
                if ( symtb.lookupLocal(myStrVal) != null ){
                    ErrMsg.fatal(myLineNum, myCharNum, "Multiply declared identifier");
                } else {
                    symtb.addDecl(myStrVal, new SemSym(typeStr));
                }
            } else if (declType == STRUCTDECL){
                if ( symtb.lookupLocal(myStrVal) != null ){
                    ErrMsg.fatal(myLineNum, myCharNum, "Multiply declared identifier");
                } else {
                    //System.out.println("here " + myStrVal);
                    symtb.addDecl(myStrVal, new StructDeclSym(symtbs));
                    ProgramNode.globalStructSym.put(myStrVal, new StructDeclSym(symtbs));
                }
            } 
        }catch(Exception e){
            ErrMsg.fatal(myLineNum, myCharNum, "Exception occurs!");
        }
    }

    public void analyzeUse(SymTable symtb){
        try{
            mySym = symtb.lookupGlobal(myStrVal); //link?
            if ( mySym == null ){
                ErrMsg.fatal(myLineNum, myCharNum, "Undeclared identifier");
            } 
        }catch(Exception e){
            ErrMsg.fatal(myLineNum, myCharNum, "Exception occurs!");
        }
    }

    public String toString(){
        return myStrVal; // for struct
    }

    public SemSym getSym(){
        return mySym;
    }

    public int getLineNum(){return myLineNum;}
    public int getCharNum(){return myCharNum;}

    public void unparse(PrintWriter p, int indent) {
        p.print(myStrVal);
        if (mySym != null){
            int kind = mySym.getKind();
            if (kind == IdNode.VAR){
                p.print("("+mySym.getType()+")");
            }else if(kind == IdNode.STRUCTVAR){
                p.print("("+mySym.getType()+")");
            }else if(kind == IdNode.FUN){
                FnSym fs = (FnSym)mySym;
                p.print("("+fs.getFormals()+"->"+fs.getRet()+")");
            }
        }
    }

    private int myLineNum;
    private int myCharNum;
    private String myStrVal;
    ///
    private SemSym mySym;
    public static int VAR = 1;
    public static int STRUCTDECL = 2;
    public static int STRUCTVAR = 3;
    public static int FUN = 4;
}

class DotAccessExpNode extends ExpNode {
    public DotAccessExpNode(ExpNode loc, IdNode id) {
        myLoc = loc;	
        myId = id;
        //System.out.println("here2 "+myId.toString());
        firstVarDefined = true;//assumen the very first var in chain of dot accesses is defined
    }

    public SemSym getSym(){
        return mySym;
    }

    public boolean getFirstStatus(){
        return firstVarDefined;
    }

    public void analyze(SymTable symtb){
        try{
            if (String.valueOf(myLoc.getClass()).equals("class IdNode")){//base case for recursion
                myLoc.analyze(symtb, IdNode.STRUCTVAR);
                SemSym sym = myLoc.getSym();
                if (sym != null){
                    mySym = ((StructSym)sym).lookup(myId.toString());
                    myId.setSym(mySym);
                    if(mySym==null){
                        ErrMsg.fatal(myId.getLineNum(), myId.getCharNum(), "Invalid struct field name");
                        firstVarDefined = false;
                    }
                } else {
                    firstVarDefined = false;
                }
            }else{
                myLoc.analyze(symtb);
                SemSym sym = myLoc.getSym();
                if (!myLoc.getFirstStatus()){firstVarDefined=false;return;}
                if (sym == null){
                    ErrMsg.fatal(myId.getLineNum(), myId.getCharNum(), "Undeclared identifier");
                    firstVarDefined = false;
                }else {
                    int symKind = sym.getKind();
                    if (symKind != IdNode.STRUCTVAR){
                        ErrMsg.fatal(myId.getLineNum(), myId.getCharNum(), "Dot-access of non-struct type");
                        firstVarDefined = false;
                    } else {
                        //if id is in, update mySym to the one
                        //else error
                        mySym = ((StructSym)sym).lookup(myId.toString());
                        myId.setSym(mySym);
                        if(mySym==null){
                            ErrMsg.fatal(myId.getLineNum(), myId.getCharNum(), "Invalid struct field name");
                            firstVarDefined = false;
                        }
                    }
                }
            }
        }catch(Exception e){
            ErrMsg.fatal(myId.getLineNum(), myId.getCharNum(), "Exception occurs!");
        }
    }

    public void unparse(PrintWriter p, int indent) {
        myLoc.unparse(p, 0);
        p.print(".");
        myId.unparse(p, 0);
    }

    // 2 kids
    private ExpNode myLoc;	
    private IdNode myId;
    ///
    private SemSym mySym;
    private boolean firstVarDefined;
}

class AssignNode extends ExpNode {
    public AssignNode(ExpNode lhs, ExpNode exp) {
        myLhs = lhs;
        myExp = exp;
        //System.out.println("here3 "+lhs.getClass().toString());
    }

    public void analyze(SymTable symtb){
        myLhs.analyze(symtb);
        myExp.analyze(symtb);
    }

    public void unparse(PrintWriter p, int indent) {
        if (indent != -1)  p.print("(");
        myLhs.unparse(p, 0);
        p.print(" = ");
        myExp.unparse(p, 0);
        if (indent != -1)  p.print(")");
    }

    // 2 kids
    private ExpNode myLhs;
    private ExpNode myExp;
}

class CallExpNode extends ExpNode {
    public CallExpNode(IdNode name, ExpListNode elist) {
        myId = name;
        myExpList = elist;
    }

    public CallExpNode(IdNode name) {
        myId = name;
        myExpList = new ExpListNode(new LinkedList<ExpNode>());
    }

    public void analyze(SymTable symtb){
        myId.analyze(symtb, IdNode.FUN);
        myExpList.analyze(symtb);
    }

    // ** unparse **
    public void unparse(PrintWriter p, int indent) {
        myId.unparse(p, 0);
        p.print("(");
        if (myExpList != null) {
            myExpList.unparse(p, 0);
        }
        p.print(")");
    }

    // 2 kids
    private IdNode myId;
    private ExpListNode myExpList;  // possibly null
}

abstract class UnaryExpNode extends ExpNode {
    public UnaryExpNode(ExpNode exp) {
        myExp = exp;
    }

    public void analyze(SymTable symtb){
        myExp.analyze(symtb);
    }

    // one child
    protected ExpNode myExp;
}

abstract class BinaryExpNode extends ExpNode {
    public BinaryExpNode(ExpNode exp1, ExpNode exp2) {
        myExp1 = exp1;
        myExp2 = exp2;
    }

    public void analyze(SymTable symtb){
        myExp1.analyze(symtb);
        myExp2.analyze(symtb);
    }

    // two kids
    protected ExpNode myExp1;
    protected ExpNode myExp2;
}

// **********************************************************************
// Subclasses of UnaryExpNode
// **********************************************************************

class UnaryMinusNode extends UnaryExpNode {
    public UnaryMinusNode(ExpNode exp) {
        super(exp);
    }

    public void unparse(PrintWriter p, int indent) {
        p.print("(-");
        myExp.unparse(p, 0);
        p.print(")");
    }
}

class NotNode extends UnaryExpNode {
    public NotNode(ExpNode exp) {
        super(exp);
    }

    public void unparse(PrintWriter p, int indent) {
        p.print("(!");
        myExp.unparse(p, 0);
        p.print(")");
    }
}

// **********************************************************************
// Subclasses of BinaryExpNode
// **********************************************************************

class PlusNode extends BinaryExpNode {
    public PlusNode(ExpNode exp1, ExpNode exp2) {
        super(exp1, exp2);
    }

    public void unparse(PrintWriter p, int indent) {
        p.print("(");
        myExp1.unparse(p, 0);
        p.print(" + ");
        myExp2.unparse(p, 0);
        p.print(")");
    }
}

class MinusNode extends BinaryExpNode {
    public MinusNode(ExpNode exp1, ExpNode exp2) {
        super(exp1, exp2);
    }

    public void unparse(PrintWriter p, int indent) {
        p.print("(");
        myExp1.unparse(p, 0);
        p.print(" - ");
        myExp2.unparse(p, 0);
        p.print(")");
    }
}

class TimesNode extends BinaryExpNode {
    public TimesNode(ExpNode exp1, ExpNode exp2) {
        super(exp1, exp2);
    }

    public void unparse(PrintWriter p, int indent) {
        p.print("(");
        myExp1.unparse(p, 0);
        p.print(" * ");
        myExp2.unparse(p, 0);
        p.print(")");
    }
}

class DivideNode extends BinaryExpNode {
    public DivideNode(ExpNode exp1, ExpNode exp2) {
        super(exp1, exp2);
    }

    public void unparse(PrintWriter p, int indent) {
        p.print("(");
        myExp1.unparse(p, 0);
        p.print(" / ");
        myExp2.unparse(p, 0);
        p.print(")");
    }
}

class AndNode extends BinaryExpNode {
    public AndNode(ExpNode exp1, ExpNode exp2) {
        super(exp1, exp2);
    }

    public void unparse(PrintWriter p, int indent) {
        p.print("(");
        myExp1.unparse(p, 0);
        p.print(" && ");
        myExp2.unparse(p, 0);
        p.print(")");
    }
}

class OrNode extends BinaryExpNode {
    public OrNode(ExpNode exp1, ExpNode exp2) {
        super(exp1, exp2);
    }

    public void unparse(PrintWriter p, int indent) {
        p.print("(");
        myExp1.unparse(p, 0);
        p.print(" || ");
        myExp2.unparse(p, 0);
        p.print(")");
    }
}

class EqualsNode extends BinaryExpNode {
    public EqualsNode(ExpNode exp1, ExpNode exp2) {
        super(exp1, exp2);
    }

    public void unparse(PrintWriter p, int indent) {
        p.print("(");
        myExp1.unparse(p, 0);
        p.print(" == ");
        myExp2.unparse(p, 0);
        p.print(")");
    }
}

class NotEqualsNode extends BinaryExpNode {
    public NotEqualsNode(ExpNode exp1, ExpNode exp2) {
        super(exp1, exp2);
    }

    public void unparse(PrintWriter p, int indent) {
        p.print("(");
        myExp1.unparse(p, 0);
        p.print(" != ");
        myExp2.unparse(p, 0);
        p.print(")");
    }
}

class LessNode extends BinaryExpNode {
    public LessNode(ExpNode exp1, ExpNode exp2) {
        super(exp1, exp2);
    }

    public void unparse(PrintWriter p, int indent) {
        p.print("(");
        myExp1.unparse(p, 0);
        p.print(" < ");
        myExp2.unparse(p, 0);
        p.print(")");
    }
}

class GreaterNode extends BinaryExpNode {
    public GreaterNode(ExpNode exp1, ExpNode exp2) {
        super(exp1, exp2);
    }

    public void unparse(PrintWriter p, int indent) {
        p.print("(");
        myExp1.unparse(p, 0);
        p.print(" > ");
        myExp2.unparse(p, 0);
        p.print(")");
    }
}

class LessEqNode extends BinaryExpNode {
    public LessEqNode(ExpNode exp1, ExpNode exp2) {
        super(exp1, exp2);
    }

    public void unparse(PrintWriter p, int indent) {
        p.print("(");
        myExp1.unparse(p, 0);
        p.print(" <= ");
        myExp2.unparse(p, 0);
        p.print(")");
    }
}

class GreaterEqNode extends BinaryExpNode {
    public GreaterEqNode(ExpNode exp1, ExpNode exp2) {
        super(exp1, exp2);
    }

    public void unparse(PrintWriter p, int indent) {
        p.print("(");
        myExp1.unparse(p, 0);
        p.print(" >= ");
        myExp2.unparse(p, 0);
        p.print(")");
    }
}
