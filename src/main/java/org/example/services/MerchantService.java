package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.infrastructure.exception.CodedException;
import org.example.infrastructure.exception.ErrorCode;
import org.example.model.Merchant;
import org.example.model.Transaction;
import org.example.repositories.MerchantRepository;
import org.example.services.impl.MerchantServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MerchantService implements MerchantServiceImpl {

    private final MerchantRepository merchantRepository;

    @Override
    public List<Merchant> findAllMerchants() {
        return merchantRepository.findAll();
    }

    @Override
    public Merchant deleteMerchant(UUID id) throws CodedException {
        Merchant merchant = merchantRepository.findById(id).orElseThrow(() -> new CodedException(ErrorCode.ENTITY_NOT_FOUND, id));

        checkHasRelatedTransaction(id, merchant.getTransactionList());

        merchantRepository.deleteById(id);

        return merchant;
    }

    private void checkHasRelatedTransaction(UUID id, List<Transaction> merchant) throws CodedException {
        if (!merchant.isEmpty()) {
            throw new CodedException(ErrorCode.MERSHANT_TRANSACTION, id);
        }
    }
}
