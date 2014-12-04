package multiplication;

public class multiplication_2 {

	public static void main(String[] args)  {   
		for (int i = 9;  i >= 1; i--)    {      
			for(int n = i;  n > 0 & n <= i; n--)      {        
				System.out.print( i + " x " + n + " = " + i * n + " ");    	
			}  
			System.out.println();  
			}
		}
	}