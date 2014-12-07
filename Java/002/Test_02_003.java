
public class Test_003 {

	 public static void main(String[] args) {
		    for (int i = 100; i <= 999; i++) {
	            int a, b, c;
	            a = i / 100;
	            b = (i - a * 100) / 10;
	            c = i - a * 100 - b * 10;
            	if (i == Math.pow(c,3) + Math.pow(b,3) + Math.pow(a,3)) {
                System.out.println(i);
		            }
		        }
		    }

}
