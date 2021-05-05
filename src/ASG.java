import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ASG {
    private LFSR lfsr1, lfsr2,lfsr3;

    public ASG(String key){
        List<Integer> firstRegister = new ArrayList<>();
        List<Integer> secondRegister = new ArrayList<>();
        List<Integer> thirdRegister= new ArrayList<>();

        for(int i = 0; i < 3*128; i++){
            if(i<128)
                firstRegister.add(key.charAt(i) - '0');
            else if(i<255)
                secondRegister.add(key.charAt(i) - '0');
            else
                thirdRegister.add(key.charAt(i) - '0');

        }
        Polynomial firstPolynomial = new Polynomial(Arrays.asList(119, 117, 110, 6,0));
        Polynomial secondPolynomial = new Polynomial(Arrays.asList(124, 107, 106, 1, 0));
        Polynomial thirdPolynomial = new Polynomial(Arrays.asList(106, 64, 62, 1,0));
        lfsr1 = new LFSR(firstRegister, firstPolynomial);
        lfsr2 = new LFSR(secondRegister, secondPolynomial);
        lfsr3 = new LFSR(thirdRegister, thirdPolynomial);
    }

    public String GenerateKeyStream(int bitsLength){
        StringBuilder keyStream = new StringBuilder();

        int resultLFSR1=0, resultLFSR2 = 0, resultLFSR3,nr1=0,nr0=0;

        for(int i = 0; i < bitsLength; ++i){
            resultLFSR3 = lfsr3.clock();
            if(resultLFSR3 == 1){
                resultLFSR1 = lfsr1.clock();
            }else{
                resultLFSR2 = lfsr2.clock();
            }
            int resultedBit = resultLFSR1 ^ resultLFSR2;
            keyStream.append(resultedBit);
            if(resultedBit==1)
                nr1++;
            else nr0++;
        }
        System.out.println("Number of 0: "+nr0);
        System.out.println("Number of 1: "+nr1);
        return keyStream.toString();
    }


}