package guru.springframework.springairag.services;

import guru.springframework.springairag.models.Answer;
import guru.springframework.springairag.models.Question;

public interface OpenAIService {

    Answer getAnswer(Question question);
}
