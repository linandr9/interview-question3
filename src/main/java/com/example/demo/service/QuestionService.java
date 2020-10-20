package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    ReplyRepository replyRepository;

    public QuestionEntity createQuestion(QuestionEntity questionEntity)
    {
        questionEntity = questionRepository.save(questionEntity);

        return questionEntity;
    }

    public ReplyEntity addReply(Long questionId, ReplyEntity replyEntity) throws RecordNotFoundException {
        Optional<QuestionEntity> questionEntity = questionRepository.findById(questionId);
        if(questionEntity.isPresent())
        {
            QuestionEntity newEntity = questionEntity.get();
            replyEntity.setQuestionId(questionId);
            replyEntity = replyRepository.save(replyEntity);
            ReplyEntity exampleEntity = new ReplyEntity();
            exampleEntity.setQuestionId(questionId);
            Example<ReplyEntity> example = Example.of(exampleEntity);
            List<ReplyEntity> replies = replyRepository.findAll(example);
            newEntity.setReplies(replies);
            questionRepository.save(newEntity);
            return  replyEntity;
        } else {
            throw new RecordNotFoundException("No question record exist for given id");
        }
    }

    public QuestionEntity getQuestion(long id) throws RecordNotFoundException {
        Optional<QuestionEntity> question = questionRepository.findById(id);

        if(question.isPresent()) {
            return question.get();
        } else {
            throw new RecordNotFoundException("No question record exist for given id");        }
    }

    public List<QuestionEntity> getAllQuestions()
    {
        List<QuestionEntity> questionsList = questionRepository.findAll();

        if(questionsList.size() > 0) {
            return questionsList;
        } else {
            return new ArrayList<QuestionEntity>();
        }
    }
}
