package ro.ubb.movie_catalog.web.converter;

import ro.ubb.movie_catalog.core.domain.entities.BaseEntity;
import ro.ubb.movie_catalog.web.dto.BaseDto;

public interface Converter<Model extends BaseEntity<Long>, Dto extends BaseDto> {

    Model convertDtoToModel(Dto dto);

    Dto convertModelToDto(Model model);

}
