package pl.romczaj.multidomain.domain.mediafile;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import pl.romczaj.multidomain.output.database.MediaFileEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
abstract class MediaFileDomainMapper {

    @Autowired
    BlobProperty blobProperty;

    abstract MediaFileEntity toEntity(MediaFile mediaFile);

    @Mapping(target = "blobPrefix", expression = "java(blobProperty.getBlobPrefix())")
    abstract MediaFile toDomain(MediaFileEntity mediaFileEntity);

    abstract void update(@MappingTarget MediaFileEntity mediaFileEntity, MediaFile mediaFile);
}
