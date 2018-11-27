package com.github.jrry.blog.configuration;

import com.github.jrry.blog.entity.ArticleEntity;
import com.github.jrry.blog.entity.TagEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.github.jrry.blog.forms.ArticleForm;

import javax.sql.DataSource;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@EnableConfigurationProperties(DatabaseProperties.class)
@RequiredArgsConstructor
public class AppConfig implements WebMvcConfigurer {

    private final DatabaseProperties databaseProperties;

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        Converter<Set<TagEntity>, String> setToStringComma =
                source -> source.getSource().stream().map(TagEntity::getName).collect(Collectors.joining(","));

        modelMapper.typeMap(ArticleEntity.class, ArticleForm.class)
                .addMappings(mapper -> {
                    mapper.using(setToStringComma).map(ArticleEntity::getTags, ArticleForm::setTags);
                    mapper.map(articleEntity -> Long.toString(articleEntity.getCategory().getId()), ArticleForm::setCategory);
                    mapper.map(articleEntity -> articleEntity.getImage().getId(), ArticleForm::setImageId);
                });

        return modelMapper;
    }

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url(databaseProperties.getUrl())
                .username(databaseProperties.getUsername())
                .password(databaseProperties.getPassword())
                .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //FIXME: cache control, security
        registry.addResourceHandler("/static/**").addResourceLocations("file:/opt/files/");
//        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
//        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
    }
}
