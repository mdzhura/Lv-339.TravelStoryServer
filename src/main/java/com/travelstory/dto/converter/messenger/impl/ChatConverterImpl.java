package com.travelstory.dto.converter.messenger.impl;

import com.travelstory.dto.converter.messenger.ChatConverter;
import com.travelstory.dto.messenger.ChatDTO;
import com.travelstory.dto.messenger.MessageDTO;
import com.travelstory.entity.messenger.Chat;
import com.travelstory.repositories.MessageRepository;
import com.travelstory.utils.ModelMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ChatConverterImpl implements ChatConverter {

    private ModelMapperUtils modelMapperUtils;
    private MessageRepository messageRepository;

    @Autowired
    public ChatConverterImpl(ModelMapperUtils modelMapperUtils, MessageRepository messageRepository) {
        this.modelMapperUtils = modelMapperUtils;
        this.messageRepository = messageRepository;
    }

    @Override
    public ChatDTO convertToDto(Chat chat) {
        ChatDTO chatDTO = modelMapperUtils.map(chat, ChatDTO.class);

        chatDTO.setLastMessage(
                modelMapperUtils.map(messageRepository.findTopByChatOrderByCreatedAt(chat), MessageDTO.class));

        return chatDTO;
    }

    @Override
    public Chat convertToEntity(ChatDTO chatDTO) {
        Chat chat = modelMapperUtils.map(chatDTO, Chat.class);

        // User creator = userRepository.findById(creatorToFind.getId())
        // .orElseThrow(() -> new EntityNotFoundException("User isn't found in the DB." +
        // " Exception occurred while converting from ChatDTO to Chat", "sdf", ChatConverter.class));

        return chat;
    }

    @Override
    public List<ChatDTO> convertToDtos(List<Chat> chats) {
        List<ChatDTO> chatDTOS = new ArrayList<>();

        for (Chat chat : chats) {
            chatDTOS.add(convertToDto(chat));
        }

        return chatDTOS;
    }

    @Override
    public List<Chat> convertToEntities(List<ChatDTO> chatDTOs) {
        List<Chat> chats = new ArrayList<>();

        for (ChatDTO chatDTO : chatDTOs) {
            chats.add(convertToEntity(chatDTO));
        }

        return chats;
    }

}
