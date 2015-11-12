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
        return IdNode.STRUCTDECL;
    }

    public SymTable getSymtb(){
        return symtb;
    }
}

class StructSym extends SemSym{
    private StructDeclSym sds;
    private String name;// name of the struct

    public StructSym(StructDeclSym sds, String name){
        super("");
        this.sds = sds;
        this.name = name;
    }

    public int getKind(){
        return IdNode.STRUCTVAR;
    }

    public SymTable getSymtb(){
        return sds.getSymtb();
    }

    public SemSym lookup(String name) throws Exception{
        SymTable symtb = getSymtb();
        return symtb.lookupLocal(name);
    }

    public String toString() {
        return "struct "+name;
    }

    public String getType() {
        return name;
    }
}
