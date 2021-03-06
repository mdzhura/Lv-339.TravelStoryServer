package com.travelstory.services.messenger;

import com.travelstory.dto.messenger.MessageDTO;
import com.travelstory.entity.messenger.Chat;
import com.travelstory.entity.messenger.Message;
import com.travelstory.exceptions.ResourceNotFoundException;
import com.travelstory.exceptions.codes.ExceptionCode;
import com.travelstory.repositories.messenger.MessageRepository;
import com.travelstory.utils.modelmapper.ModelMapperDecorator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private MessageRepository messageRepository;
    private ModelMapperDecorator modelMapperDecorator;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, ModelMapperDecorator modelMapperDecorator) {
        this.messageRepository = messageRepository;
        this.modelMapperDecorator = modelMapperDecorator;
    }

    @Override
    public long save(MessageDTO messageDTO, Long chatId) {
        Message message = modelMapperDecorator.map(messageDTO, Message.class);
        Chat chat = new Chat();
        chat.setId(chatId);
        message.setChat(chat);
        return messageRepository.save(message).getId();
    }

    @Override
    public void delete(MessageDTO messageDTO) {
        Message message = modelMapperDecorator.map(messageDTO, Message.class);

        message.setDeleted(true);
        message.setDeletedAt(LocalDateTime.now());

        messageRepository.save(message);
    }

    @Override
    public MessageDTO get(Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Message with id " + messageId + " not found in the db during getting it in messageService",
                        ExceptionCode.MESSAGE_NOT_FOUND));

        return modelMapperDecorator.map(message, MessageDTO.class);
    }

    @Override
    public List<MessageDTO> getNext30Messages(long chatId, int pageNumber) { // max number of messages =)
        Chat chat = new Chat();
        chat.setId(chatId);
        List<Message> messages = messageRepository.findAllByChatOrderByCreatedAtDesc(chat,
                PageRequest.of(pageNumber, 30));

        return modelMapperDecorator.mapAll(messages, MessageDTO.class);
    }
}
