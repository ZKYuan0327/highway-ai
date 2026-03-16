package com.example.highwayai.Controller;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import com.example.highwayai.Service.AgentService;
import com.example.highwayai.Service.LoggingAdvisor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

@RestController
@CrossOrigin
@Slf4j
public class AiController {
    private final ChatClient chatClient;

    public AiController(ChatClient.Builder chatClientBuilder,
                            VectorStore vectorStore,
                            ChatMemory chatMemory,
                            AgentService agentService) {
        this.chatClient = chatClientBuilder
                .defaultSystem("""
					   您是“中铁科研院”公司的客户聊天支持代理。请以友好、乐于助人且愉快的方式来回复。
                       您正在通过在线聊天系统与用户互动。
                       您主要完成的业务功能是用户会传给你一条高速公路的路段信息，你根据这段信息为用户分析出适用的路价值提升策略。
                       关于路价值提升策略，因为我们所设计的路价值提升策略只有13中，所以你的回答只能是“收费策略类”、“道路相关软硬件设施设备改造类（针对高速公路综合服务能力的提升策略）”、“针对司机的精准引流措施（面向人）”、“多式联运策略”、“物流园区相关策略”、“沿线经济联动策略（工业园）”、“路网沿线旅游文化联动发展策略”、“区域路网综合服务能力协同提升策略”、“绿色低碳及高科技示范策略”、“服务区改造升级”、“广告引入”、“闲置空间利用改造（某一段、部分区域）”、“高速公路沿线经济发展策略”。中的一种。
                       根据你给出的分类进行function-call。
                       请讲中文。
                       今天的日期是 {current_date}.
                       在进行function-call前，请先询问用户并等用户回复"确定"之类的答复后之后才进行function-call。 
					""")
                .defaultAdvisors(
                        new PromptChatMemoryAdvisor(chatMemory),
//      @Tool报错                  new QuestionAnswerAdvisor(vectorStore, SearchRequest.builder().query("1").build()),
                        new LoggingAdvisor())
                .defaultTools(agentService)
                //.defaultFunctions("getBookingDetails", "changeBooking", "cancelBooking") // FUNCTION CALLING
                .build();
    }

    @Autowired
    private VectorStore vectorStore;


    @CrossOrigin
    @PostMapping(value = "/ai/generateStreamAsString", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> generateStreamAsString(@RequestParam(value = "message", defaultValue = "讲个笑话") @RequestBody String message) {

        log.info("message: {}", message);

        Flux<String> content = chatClient.prompt()
                .system(s -> s.param("current_date", LocalDate.now().toString()))
                //.advisors(a -> a.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId).param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 100))
                .advisors(a -> a.param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 100))
                .user(message)
                .advisors(new QuestionAnswerAdvisor(vectorStore,

                        SearchRequest.builder().query(message)
                                .similarityThreshold(0.6)
                                .build()))
                .options(DashScopeChatOptions.builder().withTemperature(0.2).build())
                .stream()
                .content();

        return  content
                .concatWith(Flux.just("[complete]"));

    }
}
