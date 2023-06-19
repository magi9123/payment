package org.example.file;

import lombok.Getter;
import org.example.file.parser.CustomerParser;
import org.example.file.parser.MerchantParser;
import org.example.file.parser.TransactionParser;
import org.example.file.parser.TransactionsParser;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.example.file.parser.ElementParser.*;

public class TransactionHandler extends DefaultHandler {

    @Getter
    private TransactionsParser transactionsParser;
    private TransactionParser transactionParser;

    private StringBuilder elementValue;

    @Override
    public void characters(char[] ch, int start, int length) {
        if (elementValue == null) {
            elementValue = new StringBuilder();
        } else {
            elementValue.append(ch, start, length);
        }
    }

    @Override
    public void startDocument() {
        transactionsParser = new TransactionsParser();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        switch (qName) {
            case TRANSACTIONS:
                transactionsParser.setArticleList(new ArrayList<>());
                break;
            case TRANSACTION:
                initTransaction(attributes);
                break;
            case CUSTOMER:
                transactionParser.setCustomer(new CustomerParser());
                break;
            case MERCHANT:
                initMerchant(attributes);
                break;
            case AMOUNT:
            case TYPE:
            case CUSTOMER_EMAIL:
            case PHONE:
            case NAME:
            case DESCRIPTION:
            case MERCHANT_EMAIL:
            case STATUS:
            case REFERENCE_TRANSACTION:
            case BANK_ACCOUNT_SUM_CUSTOMER:
            case BANK_ACCOUNT_SUM_MERCHANT:
                elementValue = new StringBuilder();
                break;
            default:
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        switch (qName) {
            case AMOUNT:
                latestArticle().setAmount(BigDecimal.valueOf(Double.parseDouble(elementValue.toString())));
                break;
            case TYPE:
                latestArticle().setType(Integer.parseInt(elementValue.toString()));
                break;
            case REFERENCE_TRANSACTION:
                latestArticle().setReferenceTransaction(elementValue.toString());
                break;
            case CUSTOMER_EMAIL:
                latestArticle().getCustomer().setEmail(elementValue.toString());
                break;
            case PHONE:
                latestArticle().getCustomer().setPhone(elementValue.toString());
                break;
            case NAME:
                latestArticle().getMerchant().setName(elementValue.toString());
                break;
            case DESCRIPTION:
                latestArticle().getMerchant().setDescription(elementValue.toString());
                break;
            case MERCHANT_EMAIL:
                latestArticle().getMerchant().setEmail(elementValue.toString());
                break;
            case STATUS:
                latestArticle().getMerchant().setStatus(Integer.parseInt(elementValue.toString()));
                break;
            case BANK_ACCOUNT_SUM_CUSTOMER:
                latestArticle().getCustomer().setBankAccountSum(BigDecimal.valueOf(Double.parseDouble(elementValue.toString())));
                break;
            case BANK_ACCOUNT_SUM_MERCHANT:
                latestArticle().getMerchant().setBankAccountSum(BigDecimal.valueOf(Double.parseDouble(elementValue.toString())));
                break;
            default:
                break;
        }
    }

    private TransactionParser latestArticle() {
        List<TransactionParser> articleList = transactionsParser.getArticleList();
        int latestArticleIndex = articleList.size() - 1;
        return articleList.get(latestArticleIndex);
    }

    private void initMerchant(Attributes attributes) {
        transactionParser.setMerchant(new MerchantParser());
        transactionParser.getMerchant().setUuid(attributes.getValue(0));
    }

    private void initTransaction(Attributes attributes) {
        transactionParser = new TransactionParser();
        transactionsParser.getArticleList().add(transactionParser);
        transactionParser.setUuid(attributes.getValue(0));
    }
}
