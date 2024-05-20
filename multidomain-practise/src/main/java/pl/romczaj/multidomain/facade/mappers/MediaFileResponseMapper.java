package pl.romczaj.multidomain.facade.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.romczaj.multidomain.domain.mediafile.MediaFile;
import pl.romczaj.multidomain.input.api.response.MediaFileDescriptionResponse;

@Mapper
public interface MediaFileResponseMapper {

    @Mapping(source = "mediaFileState", target = "state")
    MediaFileDescriptionResponse toResponse(MediaFile mediaFile);
}
