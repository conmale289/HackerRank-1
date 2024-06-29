package net.crash.dmmtiktok.service.mapper;

import net.crash.dmmtiktok.domain.Product;
import net.crash.dmmtiktok.service.dto.ProductDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Product} and its DTO {@link ProductDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {}
