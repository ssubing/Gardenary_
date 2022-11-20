package com.gardenary.domain.flower.mapper;

import com.gardenary.domain.flower.dto.QuestionAnswerDto;
import com.gardenary.domain.flower.entity.QuestionAnswer;
import com.gardenary.global.common.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface QuestionAnswerMapper extends EntityMapper<QuestionAnswerDto, QuestionAnswer> {
    QuestionAnswerMapper mapper = Mappers.getMapper(QuestionAnswerMapper.class);

    @Override
    @Mapping(source = "myFlower.flower.id", target = "flowerId")
    @Mapping(target = "userId", ignore = true)
    @Mapping(source = "myFlower.id", target = "myFlowerId")
    @Mapping(source = "question.id", target = "questionId")
    @Mapping(target = "isOver", ignore = true)
    QuestionAnswerDto toDto(final QuestionAnswer entity);

    @Override
    @Mapping(source = "flowerId", target = "myFlower.flower.id")
    @Mapping(source = "userId", target = "myFlower.user.id")
    @Mapping(source = "myFlowerId", target = "myFlower.id")
    @Mapping(source = "questionId", target = "question.id")
    QuestionAnswer toEntity(final QuestionAnswerDto dto);
}
