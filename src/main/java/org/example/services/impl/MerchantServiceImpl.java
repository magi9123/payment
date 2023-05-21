package org.example.services.impl;

import org.example.infrastructure.exception.CodedException;
import org.example.model.Merchant;

import java.util.List;
import java.util.UUID;

public interface MerchantServiceImpl {

    List<Merchant> findAllMerchants();

    Merchant deleteMerchant(UUID id) throws CodedException;
}
