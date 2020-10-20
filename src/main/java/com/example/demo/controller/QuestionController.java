package com.example.demo.controller;

import com.example.demo.model.QuestionEntity;
import com.example.demo.model.RecordNotFoundException;
import com.example.demo.model.ReplyEntity;
import com.example.demo.service.QuestionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllQuestions() {
        List<QuestionEntity> list = questionService.getAllQuestions();

        ObjectMapper objectMapper = new ObjectMapper();
        String result;
        try {
            result = objectMapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("json to string error");
        }
        return new ResponseEntity<>(result, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionEntity> getQuestionById(@PathVariable("id") Long id)
            throws RecordNotFoundException {
        QuestionEntity questionEntity = questionService.getQuestion(id);

        return new ResponseEntity<>(questionEntity, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ObjectNode> addQuestion(@RequestBody QuestionEntity questionEntity) {
        QuestionEntity updated = questionService.createQuestion(questionEntity);
        ObjectMapper mapper = new ObjectMapper();
        // create a JSON object
        ObjectNode result = mapper.createObjectNode();
        result.put("id", updated.getId());
        result.put("author", updated.getAuthor());
        result.put("message", updated.getMessage());
        result.put("replies", "0");
        return new ResponseEntity<>(result, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/{id}/reply")
    public ResponseEntity<ReplyEntity> addResponse(@PathVariable("id") Long questionId, @RequestBody ReplyEntity replyEntity)
            throws RecordNotFoundException {
        questionService.addReply(questionId, replyEntity);
        return new ResponseEntity<>(replyEntity, new HttpHeaders(), HttpStatus.OK);
    }

}
