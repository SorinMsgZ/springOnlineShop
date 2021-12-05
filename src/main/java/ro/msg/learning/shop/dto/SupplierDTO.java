package ro.msg.learning.shop.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.msg.learning.shop.entities.Supplier;

@Data
@Builder
@NoArgsConstructor
public class SupplierDTO {
    private String name;

    public SupplierDTO( String name) {
        this.name = name;
    }

    public Supplier toEntity() {
        Supplier result = new Supplier();
        result.setName(name);
        this.copyToEntity(result);
        return result;
    }

    public void copyToEntity(Supplier supplier) {
        supplier.setName(name);
    }

    public static SupplierDTO of(Supplier entity) {
        return SupplierDTO.builder()
                .name(entity.getName())
                .build();
    }
}

