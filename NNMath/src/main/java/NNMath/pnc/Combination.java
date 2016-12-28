package NNMath.pnc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Combination {


	private void combiner(List<String> arr, String s) {
		for (int i = 0; i < arr.size(); i++) {
			arr.set(i, s + arr.get(i));
		}
	}
	private int removeAll(List<Character> arr,Character s)
	{
		int j=0;
		Iterator<Character> iterator = arr.iterator();
		while(iterator.hasNext())
		{
			Character c= iterator.next();
			if(c.equals(s))
			{
				iterator.remove();
				j++;
			}
		}
		return j;
	}
	private void addNtimes(List<Character> arr,Character s,int n)
	{
		for(int i=0;i<n;i++)
		{
			arr.add(s);
		}
	}

	private String multiplyNtimes(Character c,int n)
	{
		StringBuilder builder = new StringBuilder();
		for(int i=0;i<n;i++)
		{
			builder.append(c);
		}
		return builder.toString();
	}
	public List<String> combinations(String s, int len) {
		List<Character> list = new ArrayList<Character>();
		Set<Character> set = new HashSet<Character>();
		for (Character c : s.toCharArray()) {
			list.add(c);
			set.add(c);
		}
		return combinator(list, set, len);
	}
	private List<String> combinator(List<Character> list, Set<Character> set, int len) {
		
		List<String> listToAdd = null;
		List<String> arr=new ArrayList<String>();
		
		if(list.size()<len || len<0)
		{
			return Collections.emptyList();
		}
		else if(list.size()==len)
		{
			StringBuilder builder = new StringBuilder();
			for(Character c:list)
			{
				builder.append(c);
			}
			return Arrays.asList(builder.toString());
		}
		
		if(len==0)
		{
			return Arrays.asList("");
		}else if(len==1)
		{
			for(Character c:set)
			{
				arr.add(c.toString());
			}
		}
		else{
			Character c=list.get(0);
			int j=0;
			while(list.contains(c))
			{
				int k=0;
				list.remove(c);
				j++;
				k=removeAll(list, c);
				set.remove(c);
				listToAdd=combinator(list, set, len-j);
				combiner(listToAdd, multiplyNtimes(c, j));
				arr.addAll(listToAdd);
				set.add(c);
				addNtimes(list, c, k);
			}
			set.remove(c);
			listToAdd=combinator(list, set, len);
			set.add(c);
			arr.addAll(listToAdd);
			addNtimes(list, c, j);
		}
		
		return arr;
	}

}
