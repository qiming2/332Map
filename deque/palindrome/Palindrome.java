package deque.palindrome;

import deque.Deque;
import deque.LinkedDeque;

public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> deque = new LinkedDeque<>();
        for (int i = 0; i < word.length(); i++) {
            deque.addLast(word.charAt(i));
        }
        return deque;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> deque = wordToDeque(word);
        return helper(deque);
    }

    private static boolean helper(Deque<Character> deque) {
        int size = deque.size();
        if (size == 0 || size == 1) {
            return true;
        }
        boolean checkFrontEnd = deque.removeFirst() == deque.removeLast();
        return checkFrontEnd && helper(deque);
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> deque = wordToDeque(word);
        return helper2(deque, cc);
    }

    private static boolean helper2(Deque<Character> deque, CharacterComparator cc) {
        int size = deque.size();
        if (size == 1 || size == 0) {
            return true;
        }
        return cc.equalChars(deque.removeFirst(), deque.removeLast())
                && helper2(deque, cc);
    }
}
