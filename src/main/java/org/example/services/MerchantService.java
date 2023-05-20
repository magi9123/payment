package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.model.Merchant;
import org.example.repositories.MerchantRepository;
import org.example.services.impl.MerchantServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class MerchantService implements MerchantServiceImpl {

    private final MerchantRepository merchantRepository;

    @Override
    public List<Merchant> findAllMerchants() {
        return merchantRepository.findAll();
    }
}
