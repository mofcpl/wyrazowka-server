package pl.wyrazowka.server;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@Controller
public class DictionaryController {

    private final List<String> dictionary;
    private final DictionaryParams dictionaryParams;
    public DictionaryController(Config config) throws IOException, EmptyDictionaryException {
        File file = new File(config.getPath());
        dictionary =  Files.readAllLines(file.toPath());
        if(dictionary.isEmpty()) {
            throw new EmptyDictionaryException("The dictionary is empty");
        }

        int minLength = dictionary.stream()
                .map(String::length)
                .min(Integer::compare)
                .get();

        int maxLength = dictionary.stream()
                .map(String::length)
                .max(Integer::compare)
                .get();

        dictionaryParams = new DictionaryParams(minLength, maxLength);
    }

    @GetMapping("/length/")
    @ResponseBody
     DictionaryParams getLength() {
        return dictionaryParams;
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
            if(!dictWord.substring(i, i + 1).equalsIgnoreCase(letter.toString())) return false;
        }
        return true;
    }
}
