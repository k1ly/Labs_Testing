package strings;

import java.util.ArrayList;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task3 {

    public static void main(String[] args) {
        String text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
        Matcher textMatcher = Pattern.compile("\\b[^.!?]+[.!?]+").matcher(text);
        var sentences = textMatcher.results().map(MatchResult::group).toList();
        Matcher firstSentenceMatcher = Pattern.compile("\\w+").matcher(sentences.get(0));
        var words = new ArrayList<>(firstSentenceMatcher.results().map(MatchResult::group).toList());
        for (int i = 1; i < sentences.size(); i++) {
            for (int j = 0; j < words.size(); j++) {
                Matcher wordMatcher = Pattern.compile(words.get(j)).matcher(sentences.get(i));
                if (wordMatcher.find())
                    words.remove(words.get(j));
            }
        }
        System.out.println("Слова из первого предложени, которых нет в других:");
        words.forEach(System.out::println);
    }
}
