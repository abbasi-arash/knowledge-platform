package com.io.ksp.batch;

import com.io.ksp.domain.TedTalkEntity;
import com.io.ksp.repository.TedTalkRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class DatabaseWriter implements ItemWriter<TedTalkEntity> {

    private TedTalkRepository tedTalkRepository;

    public DatabaseWriter(TedTalkRepository tedTalkRepository) {
        this.tedTalkRepository = tedTalkRepository;
    }

    @Override
    public void write(List<? extends TedTalkEntity> tedTalks) throws Exception {
        log.info("Ted talks read from CVS: " + tedTalks);
        tedTalkRepository.saveAll(tedTalks);
    }
}
