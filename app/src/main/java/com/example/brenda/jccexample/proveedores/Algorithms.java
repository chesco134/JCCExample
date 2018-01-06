package com.example.brenda.jccexample.proveedores;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Algorithms {

    private String str1;
    private String str2;
    private static int commonChars = 0;

    public Algorithms(){}

    public Algorithms(String str1, String str2){
        this.str1 = str1;
        this.str2 = str2;
    }

	public String getStr1() {
		return str1;
	}

	public void setStr1(String str1) {
		this.str1 = str1;
	}

	public String getStr2() {
		return str2;
	}

	public void setStr2(String str2) {
		this.str2 = str2;
	}

	public double doHamming(){
        int maxLength = str1.length() > str2.length() ? str1.length() : str2.length();
        int minLength = str1.length() == maxLength ? str2.length() : str1.length();
        int differences = 0;
        for(int i=0; i< minLength; i++)
            if( str1.charAt(i) != str2.charAt(i) )
                differences++;
        return 1 - ((double)(differences+(maxLength-minLength))/(double)maxLength);
    }

    public int strawberry() {
    	String shortString = str1.length() < str2.length() ? str1 : str2;
    	String longString = shortString.equals(str1) ? str2 : str1;
    	List<CharElement> shortList = new ArrayList<>();
    	List<CharElement> longList = new ArrayList<>();
    	Map<Character, Integer> shortStringChars = new TreeMap<>();
    	Map<Character, Integer> longStringChars = new TreeMap<>();
    	for(int i=0; i< shortString.length(); i++) {
    		shortList.add(new CharElement(str1.charAt(i),i+1));
    		shortStringChars.put(shortString.charAt(i), shortStringChars.get(shortString.charAt(i)) == null ? 1 : shortStringChars.get(shortString.charAt(i)) +1);
    	}
    	for(int i=0; i< longString.length(); i++) {
    		longList.add(new CharElement(str2.charAt(i), i+1));
    		longStringChars.put(longString.charAt(i), longStringChars.get(longString.charAt(i)) == null ? 1 : longStringChars.get(longString.charAt(i)) +1);
    	}
    	Map<Character, Integer> commonChars = new TreeMap<>();
    	int count = 0;
    	for(Character c : shortStringChars.keySet())
    		if(longStringChars.containsKey(c)) {
    			commonChars.put(c, shortStringChars.get(c));
    			count+=shortStringChars.get(c);
    		}
    	return count;
    }
    
    public int minDistance() {
    	String word1 = str1;
    	String word2 = str2;
    	int len1 = word1.length();
    	int len2 = word2.length();
     
    	// len1+1, len2+1, because finally return dp[len1][len2]
    	int[][] dp = new int[len1 + 1][len2 + 1];
     
    	for (int i = 0; i <= len1; i++) {
    		dp[i][0] = i;
    	}
     
    	for (int j = 0; j <= len2; j++) {
    		dp[0][j] = j;
    	}
     
    	//iterate though, and check last char
    	for (int i = 0; i < len1; i++) {
    		char c1 = word1.charAt(i);
    		for (int j = 0; j < len2; j++) {
    			char c2 = word2.charAt(j);
     
    			//if last two chars equal
    			if (c1 == c2) {
    				//update dp value for +1 length
    				dp[i + 1][j + 1] = dp[i][j];
    			} else {
    				int replace = dp[i][j] + 1;
    				int insert = dp[i][j + 1] + 1;
    				int delete = dp[i + 1][j] + 1;
     
    				int min = replace > insert ? insert : replace;
    				min = delete > min ? min : delete;
    				dp[i + 1][j + 1] = min;
    			}
    		}
    	}
     
    	return dp[len1][len2];
    }
    
    private class CharBlock{
    	private int start;
    	private int end;
    	
    	public CharBlock(int start, int end) {
    		this.start = start;
    		this.end = end;
    	}
    }
    
    private class CharElement{
    	private int position;
    	private Character c;
    	private Character prevC;
    	private Character postC;
    	public CharElement(Character c, int position) {
    		this.c = c;
    		this.position = position;
    	}
    }
    
    public double doEditInformatica() {
    	String shortString;
    	String largeString;
    	shortString = str1.length() < str2.length() ? str1 : str2;
    	largeString = shortString.equals(str1) ? str2 : str1;
    	return 1d - (double)minDistance()/(double)largeString.length();
    }
    /**
     * A similarity algorithm indicating the percentage of matched characters between two character sequences.
     *
     * <p>
     * The Jaro measure is the weighted sum of percentage of matched characters
     * from each file and transposed characters. Winkler increased this measure
     * for matching initial characters.
     * </p>
     *
     * <p>
     * This implementation is based on the Jaro Winkler similarity algorithm
     * from <a href="http://en.wikipedia.org/wiki/Jaro%E2%80%93Winkler_distance">
     * http://en.wikipedia.org/wiki/Jaro%E2%80%93Winkler_distance</a>.
     * </p>
     *
     * <p>
     * This code has been adapted from Apache Commons Lang 3.3.
     * </p>
     *
     * @since 1.0
     */
        /**
         * The default prefix length limit set to four.
         */
        private static final int PREFIX_LENGTH_LIMIT = 4;
        /**
         * Represents a failed index search.
         */
        public static final int INDEX_NOT_FOUND = -1;

        public Double apply() {
        	String left = str1;
        	String right = str2;
            final double defaultScalingFactor = 0.0;
            final double percentageRoundValue = 100.0;
            if (left == null || right == null) {
                throw new IllegalArgumentException("Strings must not be null");
            }

            int[] mtp = matches(left, right);
            double m = mtp[0];
            if (m == 0) {
                return 0D;
            }
            double j = ((m / left.length() + m / right.length() + (m - mtp[1]) / m)) / 3;
            double jw = j < 0.7D ? j : j + Math.min(defaultScalingFactor, 1D / mtp[3]) * mtp[2] * (1D - j);
            return (jw * percentageRoundValue) / percentageRoundValue;
        }

        /**
         * This method returns the Jaro-Winkler string matches, transpositions, prefix, max array.
         *
         * @param first the first string to be matched
         * @param second the second string to be machted
         * @return mtp array containing: matches, transpositions, prefix, and max length
         */
        protected static int[] matches(final CharSequence first, final CharSequence second) {
            CharSequence max, min;
            if (first.length() > second.length()) {
                max = first;
                min = second;
            } else {
                max = second;
                min = first;
            }
            int range = Math.max(max.length() / 2 - 1, 0);
            int[] matchIndexes = new int[min.length()];
            Arrays.fill(matchIndexes, -1);
            boolean[] matchFlags = new boolean[max.length()];
            int matches = 0;
            for (int mi = 0; mi < min.length(); mi++) {
                char c1 = min.charAt(mi);
                for (int xi = Math.max(mi - range, 0), xn = Math.min(mi + range + 1, max.length()); xi < xn; xi++) {
                    if (!matchFlags[xi] && c1 == max.charAt(xi)) {
                        matchIndexes[mi] = xi;
                        matchFlags[xi] = true;
                        matches++;
                        break;
                    }
                }
            }
            char[] ms1 = new char[matches];
            char[] ms2 = new char[matches];
            for (int i = 0, si = 0; i < min.length(); i++) {
                if (matchIndexes[i] != -1) {
                    ms1[si] = min.charAt(i);
                    si++;
                }
            }
            for (int i = 0, si = 0; i < max.length(); i++) {
                if (matchFlags[i]) {
                    ms2[si] = max.charAt(i);
                    si++;
                }
            }
            int transpositions = 0;
            for (int mi = 0; mi < ms1.length; mi++) {
                if (ms1[mi] != ms2[mi]) {
                    transpositions++;
                }
            }
            int prefix = 0;
            for (int mi = 0; mi < min.length(); mi++) {
                if (first.charAt(mi) == second.charAt(mi)) {
                    prefix++;
                } else {
                    break;
                }
            }
            return new int[] { matches, transpositions / 2, prefix, max.length() };
        }
    
    public double doBigramInformatica() {
    	Map<String, Integer> values = new TreeMap<>();
    	Integer aux;
    	for(String pair : grabBigramPieces(str1))
    		values.put(pair, (aux=values.get(pair)) == null ? 1 : aux+1);
    	for(String pair : grabBigramPieces(str2))
    		values.put(pair, (aux=values.get(pair)) == null ? 1 : aux+1);
    	int coincidences = 0;
    	int pairs = 0;
    	for(String pair : values.keySet())
    		coincidences += ((pairs += aux=values.get(pair)) > 1 ? aux % 2 == 0 ? aux : (aux-1) : 0);
    	return (double)coincidences/(double)pairs;
    }
    
    private String[] grabBigramPieces(String input) {
    	List<String> pieces = new ArrayList<>();
    	for(int i=0; i<input.length()-1; i++) {
    		pieces.add(String.valueOf(input.charAt(i)) + String.valueOf(input.charAt(i+1)));
    	}
    	return pieces.toArray(new String[] {});
    }
    
    public void blueberry() {
    	// System.out.println();
    	String shortString = str1.length() < str2.length() ? str1 : str2;
    	String longString = shortString.equals(str1) ? str2 : str1;
    	int window = (int)(((float)shortString.length())/(2f))+1;
//    	System.out.println(window);
    	short[][] booleanMtx = new short[shortString.length()+1][shortString.length()+1];
    	int i;
    	int j;
//    	System.out.print("  ");
//    	for(i=0; i<shortString.length(); i++)
//    		System.out.print(String.valueOf(shortString.charAt(i)) + " ");
//    	System.out.println();
    	for(i=1; i<=shortString.length(); i++) {
			if(shortString.charAt(i-1) == longString.charAt(i-1)) {
				booleanMtx[i][i] = 1;
			}else {
				booleanMtx[i][i] = 0;
	    		for(j=i+1; j< (i+window < shortString.length()+1 ? i+window : shortString.length()+1);j++) {
	    			if(shortString.charAt(i-1) == longString.charAt(j-1)) {
	    				booleanMtx[j][i] = 1;
	    			}else {
	    				booleanMtx[j][i] = 0;
	    			}
	    		}
	    		for(j = i-1; j >= (i-window >= 1 ? i-window : 1); j--) {
	    			if(shortString.charAt(i-1) == longString.charAt(j-1)) {
	    				booleanMtx[j][i] = 1;
	    			}else {
	    				booleanMtx[j][i] = 0;
	    			}
	    		}
			}
    	}
//    	for(i=1; i<=shortString.length(); i++) {
//    		System.out.print(String.valueOf(longString.charAt(i-1)) + " ");
//    		for(j=1; j<=shortString.length(); j++)
//    			System.out.print(booleanMtx[i][j] + " ");
//    		System.out.println();
//    	}
    }
    
    public double doJaroInformatica() {
    	commonChars = 0;
    	String shortString = str1.length() < str2.length() ? str1 : str2;
    	String largeString = str1.equals(shortString) ? str2 : str1;
    	int comparisonWindow = (int)(shortString.length()/2 + 1);
    	final float penalty = 0.2f;
    	int penaltyPosition = 0;
    	float finalPenalty;
    	boolean foundPenalty = false;
    	for(penaltyPosition = 0; penaltyPosition < (shortString.length() < 4 ? shortString.length() : 4); penaltyPosition++)
    		if( foundPenalty = shortString.charAt(penaltyPosition) != largeString.charAt(penaltyPosition)) 
    			break;
    	finalPenalty = (float) (foundPenalty ? (double)penalty/(double)Math.pow((double)2,(double)(penaltyPosition)) : 0d);
    	MyBooleanCell[] cells = new MyBooleanCell[shortString.length()];
    	boolean found = false;
    	boolean innerFound = false;
    	char c1;
    	char c2;
    	for(int i=0; i<shortString.length(); i++) {
    		c1 = shortString.charAt(i);
    		c2 = largeString.charAt(i);
			MyBooleanCell cell = new MyBooleanCell();
    		if(c1 == c2) {
    			for(int k=0; k<i; k++) 
					if(innerFound = (cells[k].positionY == i+1)) {
						cell.setOn(true);
						break;
					}
				if(!innerFound) {
					cell.setDiagonal(true);
	    			cell.setOn(true);
	    			cell.setPositionX(i+1);
	    			cell.setPositionY(i+1);
				} else {
					innerFound = false;
				}
    		} else {
    			for(int j=i+1; j<=(shortString.substring(i+1, shortString.length()).length() > comparisonWindow ? comparisonWindow -1 : shortString.length() - 1); j++) {
    				if( found = c1 == largeString.charAt(j) ) {
    					for(int k=0; k<i; k++) 
    						if(innerFound = (cells[k].positionY == j+1)) {
    							cell.setOn(true);
    							break;
    						}
    					if(!innerFound) {
	    	    			cell.setOn(true);
	    	    			cell.setPositionX(i+1);
	    	    			cell.setPositionY(j+1);
    					}else
    						innerFound = false;
    					break;
    	    		}
    			}
    			if(!found) {
//    				System.out.println((largeString.substring(0,i).length() > comparisonWindow-1 ? i - comparisonWindow + 2: 0));
        			for(int j=i-1; j >= (largeString.substring(0,i).length() > comparisonWindow-1 ? i - comparisonWindow + 1: 0); j--) {
//        				System.out.println(shortString.charAt(i) + " vs " + largeString.charAt(j));
    					if( found = shortString.charAt(i) == largeString.charAt(j)) {
//    						System.out.println("Lo encontré abajo: " + largeString.charAt(j) + " (" + (i+1) + ")");
        					for(int k=0; k<i; k++) {
//        						System.out.print(cells[k].positionX + " " + cells[k].positionY+"|");
        						if(innerFound = (cells[k].positionX == j+1)) {
//        							System.out.println("Found.");
        							cell.setOn(true);
        							break;
        						}
        					}
//        					System.out.println();
        					if(!innerFound) {
	        	    			cell.setOn(true);
	        	    			cell.setPositionX(i+1);
	        	    			cell.setPositionY(j+1);
        					}else
        						innerFound = false;
        					break;
        	    		}
    				}
    			}else
    				found = false;
    		}
			cells[i] = cell;
    	}
    	int transposes = 0;
    	for(int i=0; i<cells.length; i++) {
    		if(!cells[i].isDiagonal() && cells[i].isOn() && cells[i].positionX != 0 && cells[i].positionY != 0) {
	    		transposes++;
//	    		System.out.println("#" + (i+1) + " IsDiagonal? " + cells[i].isDiagonal() + ". IsOn? " + cells[i].isOn());
    		}
    	}
    	List<MyBooleanCell> myCells = new ArrayList<>();
    	Collections.addAll(myCells, cells);
//    	myCells.stream().filter(p -> p.isOn()).forEach(cell -> commonChars++);
		for( MyBooleanCell p : myCells)
			commonChars += p.isOn() ? 1 : 0;
    	transposes = (int)((float)transposes/2f);
//    	System.out.println();
//    	System.out.println("Length of short string: " + shortString.length());
//    	System.out.println("Length of large string: " + largeString.length());
//    	System.out.println("Comparison window: " + comparisonWindow);
//    	System.out.println("Penalty: " + finalPenalty);
//    	System.out.println("Transposes: " + transposes);
//    	System.out.println("Common chars: " + commonChars);
//    	System.out.println("Jaro: " + (((((double)commonChars/(double)shortString.length())+((double)commonChars/(double)largeString.length())+1d-((double)transposes/(double)commonChars))/3d-(double)finalPenalty)));
    	return (((((double)commonChars/(double)shortString.length())+((double)commonChars/(double)largeString.length())+1d-((double)transposes/(double)commonChars))/3d-(double)finalPenalty));
    }
    
    private class MyBooleanCell{
    	private boolean on = false;
    	private boolean diagonal = false;
    	private int positionX = 0;
    	private int positionY = 0;
    	
    	public boolean isOn() {
    		return on;
    	}
    	
    	public boolean isDiagonal() {
    		return diagonal;
    	}
    	
    	public int getPositionX() {
    		return positionX;
    	}
    	
    	public int getPositionY() {
    		return positionY;
    	}
    	
    	public void setOn(boolean on) {
    		this.on = on;
    	}
    	
    	public void setDiagonal(boolean diagonal) {
    		this.diagonal = diagonal;
    	}
    	
    	public void setPositionX(int positionX) {
    		this.positionX = positionX;
    	}
    	
    	public void setPositionY(int positionY) {
    		this.positionY = positionY;
    	}
    }
    
    public double doEdit(){
        String shortString;
        String largeString;
        if(str1.length() < str2.length()){
            shortString = str1;
            largeString = str2;
        }else{
            shortString = str2;
            largeString = str1;
        }
        int[][] valMatrix = new int[shortString.length()+2][largeString.length()+2];
        int substitutionCost = 0;
        for(int i=0; i<2; i++) valMatrix[0][i] = 0;
        for(int i=0; i<2; i++) valMatrix[i][0] = 0;
        for(int i=0; i<shortString.length(); i++) // The vertical contains the shortString.
            valMatrix[i+2][0] = shortString.charAt(i);
        for(int i=0; i<largeString.length(); i++) // The horizontal contains the largeString.
            valMatrix[0][i+2] = largeString.charAt(i);
        for(int i=0; i<shortString.length(); i++)
            for(int j=0; j<largeString.length(); j++) {
                substitutionCost = shortString.charAt(i) != largeString.charAt(j) ? 1 : 0;
                valMatrix[i+2][j+2] = minimum(valMatrix[i+1][j+2]+1,valMatrix[i+2][j+1]+1,valMatrix[i+1][j+1]+substitutionCost);
            }
        return ((double)valMatrix[shortString.length()+1][largeString.length()+1]/(double)largeString.length());
    }

    private int minimum(int a, int b, int c){
        if(a > b && b > c)
            return c;
        else if( b > a && a > c )
            return c;
        else if( c > a && a > b)
            return b;
        else if( a > c && c > b)
            return b;
        else if( b > c && c > a )
            return a;
        else if( c > b && b > a)
            return a;
        else if( a > b && b == c )
            return b;
        else if( b > a && a == c)
            return a;
        else if( c > a && a == b )
            return a;
        else
            return a;
    }
}
