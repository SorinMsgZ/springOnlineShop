package ro.msg.learning.shop.dto;

import lombok.Builder;
import lombok.Data;
import ro.msg.learning.shop.entities.Supplier;

@Data
@Builder

public class SupplierDTO {
    private int id;
    private String name;

    public SupplierDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Supplier toEntity() {
        Supplier result = new Supplier();
        result.setId(id);
        result.setName(name);
        this.copyToEntity(result);
        return result;
    }

    public void copyToEntity(Supplier supplier) {
        supplier.setId(id);
        supplier.setName(name);
    }

    public static SupplierDTO of(Supplier entity) {
        return SupplierDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}

