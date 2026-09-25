package com.deep.docmind.service;

import com.deep.docmind.config.AppProperties;
import com.deep.docmind.dto.*;
import com.deep.docmind.entity.Conversation;
import com.deep.docmind.entity.User;
import com.deep.docmind.repository.ConversationRepository;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RagService {
    private static final Logger log = LoggerFactory.getLogger(RagService.class);

    private final VectorStore vectorStore;
    private final AppProperties appProperties;
    private final ChatClient chatClient;

    private final ConversationRepository conversationRepository;


    //to ask any thing related to document
    public ChatResponseDto askQuestion(ChatRequestDto request, User user) {

        long startTime = System.currentTimeMillis();


        String conversationId = request.getConversationId() != null ? request.getConversationId() : UUID.randomUUID().toString();

        ensureConversationExists(conversationId, user, request.getQuestion());

        log.info("Processing query: '{}', scoped documentId: {}", request.getQuestion(), request.getDocumentId());


        List<Document> similarDocuments = this.retrieveRelevantDocuments(
                request.getQuestion(),
                request.getDocumentId(),
                request.getTopK(),
                request.getMinSimilarity(),
                user
        );


        List<CitationDto> citationDtos = similarDocuments.stream().map(this::mapToCitation).toList();

        String contextText = buildContextString(similarDocuments);

//        String prompt = buildPrompt(request.getQuestion(), contextText);

        //you have to use conversationId to remember the conversation
        //ChatMemory
        //ChatMemoryRepository
        String answer = this.chatClient
                .prompt()
                .system(s -> s.param("doc_context", contextText))
                .user(request.getQuestion())
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, request.getConversationId()))
                .call().content();
        long responseTime = System.currentTimeMillis() - startTime;
        log.info("Completed Q&A in {} ms with {} citations", responseTime, citationDtos.size());
        return ChatResponseDto.builder().answer(answer).conversationId(request.getConversationId() != null ? request.getConversationId() : UUID.randomUUID().toString()).citations(citationDtos).responseTimeMs(responseTime).build();


    }


}