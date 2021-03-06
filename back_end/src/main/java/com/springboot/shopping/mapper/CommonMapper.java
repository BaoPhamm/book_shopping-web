package com.springboot.shopping.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CommonMapper {

	private final ModelMapper modelMapper;

	public CommonMapper(ModelMapper modelMapper) {
		super();
		this.modelMapper = modelMapper;
	}

	public <T, S> S convertToEntity(T data, Class<S> type) {
		return modelMapper.map(data, type);
	}

	public <T, S> S convertToResponse(T data, Class<S> type) {
		return modelMapper.map(data, type);
	}

	public <T, S> List<S> convertToResponseList(List<T> lists, Class<S> type) {
		return lists.stream().map(list -> convertToResponse(list, type)).collect(Collectors.toList());
	}

}
