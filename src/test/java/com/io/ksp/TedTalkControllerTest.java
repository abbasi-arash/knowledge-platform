package com.io.ksp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.io.ksp.domain.TedTalkEntity;
import com.io.ksp.dto.TedTalkRequest;
import com.io.ksp.service.TedTalkService;
import com.io.ksp.web.rest.TedTalkController;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.YearMonth;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TedTalkController.class)
public class TedTalkControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TedTalkService tedTalkService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void removeTedTalk_shouldReturnNoContent() throws Exception {
        doNothing().when(tedTalkService).removeTedTalk(anyLong());
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/ksp/v1/ted-talks/5442"))
                .andExpect(status().isNoContent());
    }

    @Test
    void modifyTedTalk_shouldReturnTedTalk() throws Exception {
        when(tedTalkService.modifyTedTalk(anyLong(), any())).thenReturn(new TedTalkEntity());
        mockMvc.perform(MockMvcRequestBuilders.put("/api/ksp/v1/ted-talks/5442")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(toJsonString(createRequest())))
                .andExpect(status().isOk());
    }

    @Test
    void getTedTalk_shouldReturnPageOfTedTalks() throws Exception {
        Page<TedTalkEntity> pageTedTalk = Mockito.mock(Page.class);
        when(tedTalkService.findTedTalk(anyString(),anyString(),anyLong(),anyLong(),anyInt(),anyInt())).thenReturn(pageTedTalk);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/ksp/v1/ted-talks?page=0&size=100"))
                .andExpect(status().isOk());
    }

    @Test
    void registerTedTalk_shouldReturnTedTalk() throws Exception {
        Page<TedTalkEntity> pageTedTalk = Mockito.mock(Page.class);
        when(tedTalkService.registerTedTalk(any())).thenReturn(new TedTalkEntity());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/ksp/v1/ted-talks")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(toJsonString(createRequest())))
                .andExpect(status().isCreated());
    }

    public String toJsonString(final Object obj) {
        try {
            String jsonContent =  objectMapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private TedTalkRequest createRequest(){
        return TedTalkRequest.builder()
                .title("my-Title")
                .author("author")
                .date(YearMonth.of(2022, 6))
                .likes(100L)
                .views(200L)
                .link("http://google.com")
                .build();
    }

}
