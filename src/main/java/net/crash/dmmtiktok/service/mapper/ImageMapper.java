package net.crash.dmmtiktok.service.mapper;

import net.crash.dmmtiktok.domain.Image;
import net.crash.dmmtiktok.domain.Product;
import net.crash.dmmtiktok.service.dto.ImageDTO;
import net.crash.dmmtiktok.service.dto.ProductDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Image} and its DTO {@link ImageDTO}.
 */
@Mapper(componentModel = "spring")
public interface ImageMapper extends EntityMapper<ImageDTO, Image> {
    @Mapping(target = "product", source = "product", qualifiedByName = "productId")
    ImageDTO toDto(Image s);

    @Named("productId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductDTO toDtoProductId(Product product);
}
