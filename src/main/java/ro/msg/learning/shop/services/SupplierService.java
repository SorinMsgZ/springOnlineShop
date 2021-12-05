package ro.msg.learning.shop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dto.SupplierDTO;
import ro.msg.learning.shop.entities.Supplier;
import ro.msg.learning.shop.exceptions.NotFoundException;
import ro.msg.learning.shop.repositories.SupplierRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SupplierService {
    private final SupplierRepository supplierRepository;

    public List<SupplierDTO> listAll() {
        return supplierRepository.findAll().stream()
                .map(SupplierDTO::of)
                .collect(Collectors.toList());
    }

    public SupplierDTO readByName(String name) {
        return supplierRepository.findByName(name)
                .map(SupplierDTO::of)
                .orElseThrow(NotFoundException::new);
    }

    public SupplierDTO create(SupplierDTO input) {
        Supplier supplier = input.toEntity();
        return SupplierDTO.of(supplierRepository.save(supplier));
    }

    public void deleteByName(String name) {
        supplierRepository.deleteByName(name);
    }

    public SupplierDTO updateByName(String name, SupplierDTO input) {
        Supplier supplier = supplierRepository.findByName(name).orElseThrow(NotFoundException::new);
        input.copyToEntity(supplier);
        supplierRepository.save(supplier);
        return SupplierDTO.of(supplier);
    }
}
