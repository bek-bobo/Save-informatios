package com.example.saveinformation.common.mapper;

public abstract class GenericMapper<ENTITY, CREATE_DTO, RESPONSE_DTO, UPDATE_DTO> {
    public abstract ENTITY toEntity(CREATE_DTO createDto);
    public abstract RESPONSE_DTO toResponseDto(ENTITY entity);
    public abstract void updateEntity(UPDATE_DTO update_dto, ENTITY entity);
}
