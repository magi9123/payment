package org.example.services;

import org.example.infrastructure.exception.CodedException;
import org.example.model.Merchant;

import java.util.List;
import java.util.UUID;

public interface MerchantService {

    List<Merchant> findAllMerchants();

    Merchant updateMerchant(Merchant entity) throws CodedException;

    Merchant deleteMerchant(UUID id) throws CodedException;
}
