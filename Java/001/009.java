import java.util.Scanner;


public class Split_2 {

	private static Scanner s;

	public static void main(String[] args) {
			System.out.println("«Î ‰»Î“ª∂Œª∞£∫");
			s = new Scanner(System.in);
			String a = s.nextLine();
			if (a.contains(" ")||a.contains(", ")){
				String[] m = a.split(" |, ");
				for(int i= 0 ;i < m.length ; i++){
					System.out.println(m[i]);
				}
			}
			

	}

}
