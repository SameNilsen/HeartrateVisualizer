package heartratevisualizer.app;

public class CodePair {

    String code;
    String scope;

    public CodePair(String code, String scope) {
        this.code = code;
        this.scope = scope;
    }

    public CodePair() {

    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getScope() {
        return this.scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
    
}
