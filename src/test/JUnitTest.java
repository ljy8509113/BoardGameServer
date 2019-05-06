package test;

import java.util.ArrayList;
import java.util.Random;

public class JUnitTest {

	public static void main(String[] args) {
//		Random r = new Random();
//		ArrayList<Integer> noArray = new ArrayList<>();
//		int maxCount = 3;
//		
//		while(noArray.size() < maxCount) {
//			int number = r.nextInt(maxCount) + 1;
//			if(noArray.size() == 0) {
//				noArray.add(number);
//			}else {
//				boolean isAdd = true;
//				for(int n : noArray) {
//					if(number == n) {
//						isAdd = false;
//						break;
//					}
//				}
//				if(isAdd) {
//					noArray.add(number);
//				}
//			}
//		}
//		
//		for(int value : noArray) {
//			System.out.print(value + " ");
//		}
		
//		ArrayList<Integer> noArray = new ArrayList<>();
//		noArray.add(0);
//		noArray.add(4);
//		noArray.add(6);
//		noArray.add(8);
//		noArray.add(12);
//		noArray.add(15);
//		
//		for(int i=0; i<noArray.size(); i++) {
//			if(noArray.get(i) > 10) {
//				noArray.add(i, 10);
//				break;
//			}
//		}
//		
//		System.out.println(noArray);
		
		ArrayList<String> array = new ArrayList<>();
//		array.add("일번");
//		array.add("번");
//		array.add("삼번");
//		array.add("사번");
		
		Random r = new Random();
		
		for(int i = 0; i<100; i++) {
			System.out.println("i : "+i);
			int index = r.nextInt(array.size());
			System.out.println("size : " + array.size());
			System.out.println("index : " + index);
			array.add(index, "추가");
			System.out.println("array : " + array);
			System.out.println("");
			array.remove("추가");
		}
		
	}

}
