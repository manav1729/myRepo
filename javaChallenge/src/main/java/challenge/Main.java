package challenge;

public class Main {

	public static void main(String[] args) {
		String word = "reuonnoinfe";
		JumbledNumbers jn = new JumbledNumbers();
        String result = jn.calculateTotalValue(word);
        System.out.println("Result for "+word+" : "+result);
	}
}
