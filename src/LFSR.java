import java.util.ArrayList;
import java.util.List;

public class LFSR {
    private final List<Integer> currentRegister;
    private final Polynomial polynomial;

    public LFSR(List<Integer> initialRegister, Polynomial polynomial){
        this.currentRegister = new ArrayList<>(initialRegister);
        this.polynomial = polynomial;
    }

    public int clock(){
        int result = polynomial.getResult(currentRegister);
        int firstBit=currentRegister.get(0);
        currentRegister.remove(0);
        currentRegister.add(result);
        return firstBit;
    }
}