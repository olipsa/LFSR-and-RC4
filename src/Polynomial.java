import java.util.ArrayList;
import java.util.List;

public class Polynomial {
    private final List<Integer> exponents=new ArrayList<>();

    public Polynomial(List<Integer> exponents){
        this.exponents.addAll(exponents);
    }

    public int getResult(List<Integer> register){

        int result=0;
        for(int i = 0; i < register.size(); ++i){
            result += exponents.get(i%exponents.size())*register.get(i);
            result=result%2;
        }
        return result;
    }
}