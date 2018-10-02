package com.travelstory.controllers.messenger;

import com.travelstory.dto.messenger.MessageDTO;
import com.travelstory.services.messenger.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messenger")
public class MessageController {

    MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("chat/{chatId}/messages")
    public List<MessageDTO> getCurrentMessages(@PathVariable Long chatId, @RequestParam("pageNumber") int pageNumber) {

        return messageService.getNext30Messages(chatId, pageNumber);
    }

}
