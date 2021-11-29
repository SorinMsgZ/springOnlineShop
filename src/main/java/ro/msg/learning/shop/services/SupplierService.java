package ro.msg.learning.shop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dto.ProductDTO;
import ro.msg.learning.shop.dto.SupplierDTO;
import ro.msg.learning.shop.entities.Product;
import ro.msg.learning.shop.entities.Supplier;
import ro.msg.learning.shop.exceptions.NotFoundException;
import ro.msg.learning.shop.repositories.SupplierRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
@RequiredArgsConstructor
public class SupplierService {
    private final SupplierRepository supplierRepository;

    public List<SupplierDTO> listSupplier() {
        return StreamSupport.stream(supplierRepository.findAll().spliterator(), false)
                .map(SupplierDTO::of)
                .collect(Collectors.toList());
    }

    public SupplierDTO readSingleSupplier(int id) {
        return supplierRepository.findById(id)
                .map(SupplierDTO::of)
                .orElseThrow(NotFoundException::new);
    }

    public SupplierDTO createSupplier(SupplierDTO input) {
        Supplier supplier = input.toEntity();


        return SupplierDTO.of(supplierRepository.save(supplier));
    }

    public void deleteSupplier(int id) {
        supplierRepository.deleteById(id);
    }

    public SupplierDTO updateSupplier(int id, SupplierDTO input) {
        Supplier supplier = supplierRepository.findById(id).orElseThrow(NotFoundException::new);
        input.copyToEntity(supplier);
        supplierRepository.save(supplier);
        return SupplierDTO.of(supplier);
    }
}
