package NNMath.pnc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Permutation {

	public List<String> permutations(String s, int len) {
		List<Character> list = new ArrayList<Character>();
		Set<Character> set = new HashSet<Character>();
		for (Character c : s.toCharArray()) {
			list.add(c);
			set.add(c);
		}
		return permutator(list, set, len);
	}

	
	private List<String> permutator(List<Character> list, Set<Character> set, int len) {
		Set<Character> setClone = new HashSet<Character>();
		setClone.addAll(set);
		List<String> arr = new ArrayList<String>();
		List<String> listToAdd = null;
		if (len == 0) {
			return Arrays.asList("");
		} else if (len == 1) {
			for (Character s : set) {
				arr.add(s.toString());
			}
		} else {
			for (Character s : setClone) {
				list.remove(s);
				if (!list.contains(s)) {
					set.remove(s);
				}
				listToAdd = permutator(list, set, len - 1);
				combiner(listToAdd, s);
				arr.addAll(listToAdd);
				if (!list.contains(s)) {
					set.add(s);
				}
				list.add(s);
			}
		}
		return arr;
	}

	private void combiner(List<String> arr, Character s) {
		for (int i = 0; i < arr.size(); i++) {
			arr.set(i, s + arr.get(i));
		}
	}

}
