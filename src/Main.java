import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;

public class Main {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        StringBuilder randomKey = new StringBuilder();
        for(int i = 0; i < 3 * 128; i++){
            randomKey.append((int)(Math.round(Math.random()*10)) % 2);
        }
        ASG alternatingStepGenerator = new ASG(randomKey.toString());
        System.out.println(alternatingStepGenerator.GenerateKeyStream(10000));
        long endTime = System.currentTimeMillis();

        float duration = (float)(endTime - startTime)/1000;
        System.out.println("Execution time: "+duration+"s");



        RC4(true);
        biasTest();


    }

    public static int RC4(boolean print){
        int temp;
        String plaintext="hello, this is the text";
        String key= "";
        int secondByte=0;
        int keyLength;
        int []s=new int[256];
        int []k=new int[256];
        keyLength=5 +(int)(Math.random() * 10) % 12;
        for(int i=0;i<keyLength*8;i++){
            key=key+((int) (Math.round(Math.random() * 10)) % 2);
        }

        char []plaintextChar=plaintext.toCharArray();
        char []keyChar= key.toCharArray();
        int[]cipher=new int[plaintext.length()];
        int[]plain=new int[plaintext.length()];
        int []plaintextInt=new int[plaintext.length()];
        int []keyInt=new int[key.length()];

        for(int i=0;i<plaintext.length();i++)
        {
            plaintextInt[i]=plaintextChar[i];
        }
        for(int i=0;i<key.length();i++)
        {
            keyInt[i]=keyChar[i];
        }

        //Init
        for(int i=0;i<255;i++)
        {
            s[i]=i; k[i]=keyInt[i%key.length()];
        }
        int j=0;
        for(int i=0;i<255;i++)
        {
            j=(j+s[i]+k[i])%256;
            temp=s[i];
            s[i]=s[j];
            s[j]=temp;
        }
        j=0;
        int i,z;

        //Transition
        for(int l=0;l<plaintext.length();l++)
        {
            i=(l+1)%256;
            j=(j+s[i])%256;
            temp=s[i];
            s[i]=s[j];
            s[j]=temp;
            z=s[(s[i]+s[j])%256];
            if(l==1)
                secondByte=z;
            cipher[l]=z^plaintextInt[l];
            plain[l]=z^cipher[l];
        }
        if(print){
            System.out.print("\n\nENCRYPTED:\t\t");
            display(cipher);
            System.out.print("\n\nDECRYPTED:\t\t");
            display(plain);
        }
        return secondByte;


}
    static void display(int []disp)
    {
        char []convert=new char[disp.length];
        for(int i=0;i<disp.length;i++)
        {
            convert[i]=(char)disp[i];
            System.out.print(convert[i]);
        }
    }
    static void biasTest() {
        int nr=0;
        int numberOfTests=10000;
        for (int i = 0; i < numberOfTests; i++) {
            int secondByte=RC4(false);
            if(secondByte==0)
                nr++;
        }
        //   1/128=0.0078125
        System.out.println("\n\nProbability of second byte to be 0x00 is: "+(float)nr/numberOfTests);
    }


}




