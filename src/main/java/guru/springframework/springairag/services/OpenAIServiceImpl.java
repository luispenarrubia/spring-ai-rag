package guru.springframework.springairag.services;

import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import guru.springframework.springairag.models.Answer;
import guru.springframework.springairag.models.Question;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OpenAIServiceImpl implements OpenAIService {
    
    private final ChatModel chatModel;
    final SimpleVectorStore vectorStore;

    @Value("classpath:templates/rag-prompt-meta-template.st")
    private Resource ragPromptTemplate;

    @Override
    public Answer getAnswer(Question question) {
        List<Document> documents = vectorStore.similaritySearch(SearchRequest.builder()
                                                    .query(question.question())
                                                    .topK(4)
                                                    .build());
        List<String> contentList = documents.stream().map(Document::getText).toList();
        
        PromptTemplate promptTemplate = new PromptTemplate(ragPromptTemplate);
        Prompt prompt = promptTemplate.create(Map.of("input", question.question(), "documents", String.join("\n", contentList)));
        
        contentList.forEach(System.out::println);
        
        ChatResponse response = chatModel.call(prompt);
        
        return new Answer(response.getResult().getOutput().getContent());
    }


}
