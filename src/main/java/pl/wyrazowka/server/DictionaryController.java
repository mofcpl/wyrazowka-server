package pl.wyrazowka.server;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Controller
public class DictionaryController {

    private final List<String> dictionary;

    public DictionaryController() throws IOException {
        ClassPathResource resource = new ClassPathResource("dictionary.txt");
        dictionary = Files.readAllLines(resource.getFile().toPath());
    }

    @GetMapping("/words/")
    @ResponseBody
    List<String> getMatchingWords(@RequestParam(value="letters") List<Character> letters) {
        return dictionary.stream()
                .filter(word -> word.length() == letters.size())
                .filter(word -> compareWords(word, letters))
                .toList();
    }

    private boolean compareWords(String dictWord, List<Character> givenWord) {
        for(int i = 0; i < givenWord.size(); i++) {
            Character letter = givenWord.get(i);
            if(letter == null) continue;
            if(dictWord.charAt(i) != letter) return false;
        }
        return true;
    }
}
