package com.io.ksp.config;

import com.io.ksp.domain.TedTalkEntity;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Configuration
@EnableBatchProcessing
public class BachConfig {
    private final Environment environment;

    public BachConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory,
                   StepBuilderFactory stepBuilderFactory,
                   ItemReader<TedTalkEntity> itemReader,
                   ItemWriter<TedTalkEntity> itemWriter
    ) {

        Step step = stepBuilderFactory.get("csv-file-load")
                .<TedTalkEntity, TedTalkEntity>chunk(100)
                .reader(itemReader)
                .writer(itemWriter)
                .faultTolerant()
                .skipLimit(10)
                .skip(FlatFileParseException.class)
                .skip(IllegalArgumentException.class)
                .build();


        return jobBuilderFactory.get("file-Load")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }

    @Bean
    public FlatFileItemReader<TedTalkEntity> itemReader() {
        FlatFileItemReader<TedTalkEntity> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new ClassPathResource(Objects.requireNonNull(environment.getProperty("csv.file-path"))));
        flatFileItemReader.setName("csv-Reader");
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setLineMapper(lineMapper());
        return flatFileItemReader;
    }

    @Bean
    public LineMapper<TedTalkEntity> lineMapper() {

        DefaultLineMapper<TedTalkEntity> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("title", "author", "date", "views", "likes", "link");

        BeanWrapperFieldSetMapper<TedTalkEntity> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(TedTalkEntity.class);
        fieldSetMapper.setConversionService(conversionService());
        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;
    }

    @Bean
    public ConversionService conversionService() {
        DefaultConversionService conversionService = new DefaultConversionService();
        DefaultConversionService.addDefaultConverters(conversionService);
        conversionService.addConverter(new Converter<String, YearMonth>() {
            @Override
            public YearMonth convert(String text) {
                return YearMonth.parse(text, DateTimeFormatter.ofPattern("MMMM yyyy"));
            }
        });

        return conversionService;
    }
}
