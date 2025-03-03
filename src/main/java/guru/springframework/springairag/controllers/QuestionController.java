package guru.springframework.springairag.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import guru.springframework.springairag.models.Answer;
import guru.springframework.springairag.models.Question;
import guru.springframework.springairag.services.OpenAIService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class QuestionController {

    private final OpenAIService openAIService;

    @PostMapping("/ask")
    public Answer askQuestion(@RequestBody Question question) {
        return openAIService.getAnswer(question);
    }

}
