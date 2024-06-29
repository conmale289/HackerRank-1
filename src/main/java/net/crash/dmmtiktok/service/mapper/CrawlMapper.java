package net.crash.dmmtiktok.service.mapper;

import net.crash.dmmtiktok.domain.Crawl;
import net.crash.dmmtiktok.domain.Product;
import net.crash.dmmtiktok.service.dto.CrawlDTO;
import net.crash.dmmtiktok.service.dto.ProductDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Crawl} and its DTO {@link CrawlDTO}.
 */
@Mapper(componentModel = "spring")
public interface CrawlMapper extends EntityMapper<CrawlDTO, Crawl> {
    @Mapping(target = "product", source = "product", qualifiedByName = "productId")
    CrawlDTO toDto(Crawl s);

    @Named("productId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductDTO toDtoProductId(Product product);
}
