package com.example.demo.entity;


import com.example.demo.model.dto.CategoryInfo;
import com.example.demo.model.dto.ChartDto;
import lombok.*;

import javax.persistence.*;

@SqlResultSetMappings(
        value = {
                @SqlResultSetMapping(
                        name = "categoryInfo",
                        classes = @ConstructorResult(
                                targetClass = CategoryInfo.class,
                                columns = {
                                        @ColumnResult(name = "id", type = Integer.class),
                                        @ColumnResult(name = "name", type = String.class),
                                        @ColumnResult(name = "product_count", type = Integer.class)
                                }
                        )
                ),
                @SqlResultSetMapping(
                        name = "chartCategoryDTO",
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
        name = "getListCategoryAndProductCount",
        resultSetMapping = "categoryInfo",
        query = "SELECT category.id, category.name, \n" +
                "(\n" +
                "    SELECT count(product_id)\n" +
                "    FROM product_category\n" +
                "    WHERE product_category.category_id = category.id\n" +
                ") product_count \n" +
                "FROM category "
)
@NamedNativeQuery(
        name = "getProductOrderCategories",
        resultSetMapping = "chartCategoryDTO",
        query = "select  c.name as label, count(o.product_id) as value from category c " +
                "inner join product_category pc on pc.category_id = c.id " +
                "inner join product p on p.id = pc.product_id " +
                "inner join orders o on o.product_id = p.id " +
                "where o.status = 3 " +
                "group by c.id "
)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "category")
@Table(name = "category")
@ToString
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "name", nullable = false, length = 300)
    private String name;
}
