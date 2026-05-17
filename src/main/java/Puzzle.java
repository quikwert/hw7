import java.util.*;

/** Word puzzle.
 * @since 1.8
 */
public class Puzzle {

   /** Solve the word puzzle.
    * @param args three words (addend1 addend2 sum)
    */
   public static void main (String[] args) {
 	System.out.println("Puzzle to be solved:\n");
	Solve("SEND", "MORE", "MONEY");
	//System.out.println(args[1] + " + " + args[2] + " = " + args[3]);
   }

   static HashMap<Character, Integer> constructMap(String a, String b, String sum){
	HashMap<Character, Integer> map = new HashMap<>();
	
	for(String c: new String[]{a,b,sum}){
		for(int i = 0; i < c.length(); i ++){
			char letter = c.charAt(i);

			if(!map.containsKey(letter)) map.put(letter, 0);
			int current = map.get(letter);
			map.put(letter, current + ((c==sum)?-1:1)*(int)Math.pow(10,c.length() - i - 1));
		}
	}
	return map;
   }

   static void Solve(String a, String b, String sum){
	   if(a.length() > 18 || b.length() > 18 || sum.length() > 18) throw new RuntimeException("Expression constituents too long!");
	   HashMap<Character, Integer> coefMap = constructMap(a, b, sum);

	   for (Character i : coefMap.keySet()) {
		   System.out.println("key: " + i + " value: " + coefMap.get(i));
	   }	
	   char mostSignificantLetters[] = new char[]{a.charAt(0), b.charAt(0), sum.charAt(0)};
	   StringBuffer sb = new StringBuffer();
	   int solutions = backtrack(new byte[10], (byte)0, coefMap, 0, 0, mostSignificantLetters, sb);
	   System.out.println("Solutions: " + solutions);
   }

   static boolean contains(char[] arr, char letter){
	for(char c : arr){
		if(letter == c)return true;
	}
	return false;
   }
   static int backtrack(byte[] used, byte idx, HashMap<Character, Integer> coefMap, int sum, int solutions, char[] MSL, StringBuffer sb){
	if(idx == coefMap.keySet().size()){
		if( sum == 0 ){
			
			System.out.println(sb.toString()+"\n");       
			return solutions + 1;
		}
		return solutions;
	}
	char letter = (char)coefMap.keySet().toArray()[idx];
   	for(int i = 0; i < 10; i ++){
		if( i == 0 && contains(MSL, letter)) continue;	
		if(used[i] == 1) continue;
		used[i]=1;	
		sb.append(i);
		solutions = backtrack(used, (byte)(idx+1), coefMap, sum + coefMap.get(letter)*i, solutions, MSL,sb);
		sb.delete(sb.length()-1,sb.length());
		used[i]=0;
	}	
	return solutions;
   }
}

