/*
 * Copyright (c) 2018 Jarosław Pawłowski
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.github.jrry.blog.configuration;

import com.github.jrry.blog.entity.Article;
import com.github.jrry.blog.entity.Tag;
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

/**
 * @author Jarosław Pawłowski
 */
@Configuration
@EnableConfigurationProperties(DatabaseProperties.class)
@RequiredArgsConstructor
public class AppConfig implements WebMvcConfigurer {

    private final DatabaseProperties databaseProperties;

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        Converter<Set<Tag>, String> setToStringComma =
                source -> source.getSource().stream().map(Tag::getName).collect(Collectors.joining(","));

        modelMapper.typeMap(Article.class, ArticleForm.class)
                .addMappings(mapper -> {
                    mapper.using(setToStringComma).map(Article::getTags, ArticleForm::setTags);
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
