package kz.kasya.realq.controllers;

import kz.kasya.realq.models.entities.Message;
import kz.kasya.realq.models.requests.MessageCode;
import kz.kasya.realq.services.CalculationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 * @author Assylkhan
 * on 13.02.2019
 * @project realq
 */
@Controller
public class SocketThreadController {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    private final SimpMessagingTemplate template;
    private CalculationService calculationService;


    @Autowired
    public SocketThreadController(SimpMessagingTemplate template, CalculationService calculationService) {
        this.template = template;
        this.calculationService = calculationService;
    }


    @MessageMapping("/socket")
    @SendTo("/thread/messages")
    public Message send(Message message) {
        logger.info(message.getSender() + ": " + message.getContent());
        System.out.println(message.getSender() + ": " + message.getContent());

        if(message.getMessageCode() == MessageCode.ADDED){
        }



        return message;
    }


}
