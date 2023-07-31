package misc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class main_misc {

	
	public static int MAX = 1_000_000;
	
	public static List<List<Integer>> doc_list = new ArrayList<>();
	
	public static void main(String[] args) {
	
		
	
	//build_list();
	
	/*List<Integer> d0 = new ArrayList<>(Arrays.asList(14,15,100,9,3));
	List<Integer> d1 = new ArrayList<>(Arrays.asList(32,1,9,3,5));
	List<Integer> d2 = new ArrayList<>(Arrays.asList(15,29,2,6,8,7));
	List<Integer> d3 = new ArrayList<>(Arrays.asList(7,10));
	List<Integer> d4 = new ArrayList<>(Arrays.asList(101,102));
	
	doc_list = new ArrayList<>(Arrays.asList(d0,d1,d2,d3,d4));*/
		
	build_list();
	
	sparse_similarity();
	
	
		
		
	}
	
	//1) Find a way to test 
	
	/*To test this algorithm in a decent way i will make a random integer list generator,i will make the size of the list
	 * up to a certain value,and the range of each element will be bounded by a range
	 */
	
	private static int rand_bounded(int lower,int upper){
		
		Random r = new Random();
		
		return r.nextInt(upper+1)+lower;
		
	}
	
	
	private static List<Integer> build_doc(int max_size,int lower,int upper){
		
		//random size,random elements but they must be distinct
		
		int size = rand_bounded(0,max_size);
		
		List<Integer> doc = new ArrayList<>();
		
		HashMap<Integer,Boolean> in_list = new HashMap<>();
		
		int c = 0;
		int num_el = 0;
		while(num_el <= size && c <= MAX) {
			
			int element = rand_bounded(lower,upper);
			
			if(in_list.containsKey(element)){
				c++;
				continue;
			}
			doc.add(element);
			in_list.put(element,true);
			num_el++;
			c++;
		}
		return doc;
	}
	
	private static void build_list(){
		
		for(int i = 0;i < MAX/1000;i++){
			doc_list.add(build_doc(10,0,MAX));
		}
		
	}
	
		
	//2) solve the problem itself
	
	//I can predict the intersections using the fact that each doc is a list of distinct elements
	//this will be usefull because they are sparse
	
	
	private static double calc_similarity(List<Integer> a,List<Integer> b){
		
		//1) intersection
		
		int intersection = 0;
		
		HashMap<Integer,Boolean> map = new HashMap<>();
		
		int len_a = a.size();
		int len_b = b.size();
		
		for(int i = 0;i < len_a;i++){
			map.put(a.get(i),true);
		}
		for(int i = 0;i < len_b;i++){
			if(map.containsKey(b.get(i))){
				intersection++;
			}
		}
		
		//because the elements on the doc are distinct:
		
		return (double)intersection/(len_a+len_b-intersection);
		
	}
	
	private static void sparse_similarity(){
		
		List<List<Integer>> similar = new ArrayList<>();
		
		
		int quant = 0;
		HashMap<Integer,Integer> elements = new HashMap<>(); 
		
		for(int i = 0;i < doc_list.size();i++){
			
			List<Integer> doc = doc_list.get(i);
			
			for(int j = 0;j < doc.size();j++){
				
				int x = doc.get(j);
				
				if(elements.containsKey(x)){
					elements.replace(x,elements.get(x)+1);
					quant++;
				}
				else {
					elements.put(x,1);
				}
			}
		}
		
		int c = 0;
		for(int i = 0;i < doc_list.size() && c <= quant ;i++){
			
			List<Integer> doc = doc_list.get(i);
			
			for(int j = 0;j < doc.size();j++){
				
				int x = doc.get(j);
				
				int qnt = elements.get(x);
				
				if(qnt > 1){
					similar.add(doc);
					c++;
					break;
				}
				
			}
			
		}
		
		
		
		//System.out.println(similar);
		
		//Because they are sparse i now that : doc_list.size() >> similar.size()
		
		System.out.println("ID1 ,ID2 : SIMILARITY");
		for(int i = 0;i < similar.size();i++){
			
			for(int j = i+1;j < similar.size();j++){
				
				double sim = calc_similarity(similar.get(i),similar.get(j));
				if(sim != 0){//I dont especifically with ho a doc is similar,i just now that there is at least one
					System.out.println(i+" ,"+j + "	: " + sim);
				}
				
			}
			
		}
		
		
	}
	
}
