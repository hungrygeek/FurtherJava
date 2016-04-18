package com.ss2099.small_code_snippet;

public class Evernote3 {
	
	public static int[] exc_mult(int[] input) {
		int[] output = new int[input.length];
		int s=1;
		for(int i = 0;i<input.length;i++){
			s=s*input[i];
		}
		for(int j =0;j<input.length;j++){
			output[j] = s/input[j];
		}
		
		return output;
	}
	
	public void main(){
		
	}

}
