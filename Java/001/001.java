package flower;

public class daffodil {

	public static void main(String[] args) {
	      System.out.println("水仙花数为：");

        int a = 0, b = 0, c = 0;

        for(int i = 100; i < 1000; i++){

            a = (i / 100) % 10;

            b = (i / 10) % 10;

            c = i % 10;

            if(i == a*a*a + b*b*b + c*c*c){

                System.out.print(i + " ");

            }

        }

    }

}