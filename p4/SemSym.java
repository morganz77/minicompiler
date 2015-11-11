import java.util.*;

public class SemSym {
    private String type;
    
    public SemSym(String type) {
        this.type = type;
    }
    
    public String getType() {
        return type;
    }
    
    public String toString() {
        return type;
    }

    public int getKind(){
        return IdNode.VAR;
    }
}

class FnSym extends SemSym{
    private String retType;
    private ArrayList<String> formals;

    public FnSym(ArrayList<String> formals, String retType){
        super("");
        this.formals = formals;
        this.retType = retType;
    }

    public String getFormals(){
        if (formals==null || formals.size()==0) {return "";}
        String ret = formals.get(0);
        for (int i=1; i<formals.size(); i++){
            ret += ("," + formals.get(i) );
        }
        return ret;
    }

    public String getRet(){
        return retType;
    }

    public int getKind(){
        return IdNode.FUN;
    }
}

class StructDeclSym extends SemSym{
    private SymTable symtb;

    public StructDeclSym(SymTable symtb){
        super("");
        this.symtb = symtb;
    }

    public SemSym lookup(String name) throws Exception{
        return symtb.lookupLocal(name);
    }

    public int getKind(){
        return IdNode.STRUCT;
    }

    public SymTable getSymtb(){
        return symtb;
    }
}

//class StructSym extends SemSym{
//    public int getKind(){
//        return 2;
//    }
//}
