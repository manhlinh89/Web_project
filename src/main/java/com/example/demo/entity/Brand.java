package com.example.demo.entity;


import com.example.demo.model.dto.BrandInfo;
import com.example.demo.model.dto.ChartDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@SqlResultSetMappings(
        value = {
                @SqlResultSetMapping(
                        name = "brandInfo",
                        classes = @ConstructorResult(
                                targetClass = BrandInfo.class,
                                columns = {
                                        @ColumnResult(name = "id", type = Integer.class),
                                        @ColumnResult(name = "name", type = String.class),
                                        @ColumnResult(name = "thumbnail", type = String.class),
                                        @ColumnResult(name = "product_count", type = Integer.class)
                                }
                        )
                ),
                @SqlResultSetMapping(
                        name = "chartBrandDTO",
                        classes = @ConstructorResult(
                                targetClass = ChartDto.class,
                                columns = {
                                        @ColumnResult(name = "label",type = String.class),
                                        @ColumnResult(name = "value",type = Integer.class)
                                }
                        )
                )
        }
)
@NamedNativeQuery(
        name = "getListBrandAndProductCount",
        resultSetMapping = "brandInfo",
        query = "SELECT brand.id, brand.name, brand.thumbnail,\n" +
                "(\n" +
                "    SELECT count(product.id)\n" +
                "    FROM product\n" +
                "    WHERE product.brand_id = brand.id\n" +
                ") product_count \n" +
                "FROM brand "
)
@NamedNativeQuery(
        name = "getProductOrderBrands",
        resultSetMapping = "chartBrandDTO",
        query = "select b.name as label, count(o.product_id) as value  from brand b " +
                "inner join product p on p.brand_id = b.id " +
                "inner join orders o on p.id = o.product_id " +
                "where o.status = 3 " +
                "group by b.id"
)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "brand")
@Table(name = "brand")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "thumbnail")
    private String thumbnail;
}
